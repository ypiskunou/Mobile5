package mmf.piskunou.weather;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.CitySize;
import mmf.piskunou.weather.domain.Month;
import mmf.piskunou.weather.domain.Season;
import mmf.piskunou.weather.repository.CityRepository;
import mmf.piskunou.weather.repository.CityRepositoryImpl;
import mmf.piskunou.weather.services.WeatherService;

public class EditActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditActivity activity = this;

    private EditText cityEditText, sizeEditText, temperatureEditText;
    private Spinner monthSpinner;
    private Button saveButton;

    private String selectedMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        SQLiteDatabaseHelper sqLiteDatabaseHelper = SQLiteDatabaseHelper
                .getInstance(getApplicationContext());

        db = sqLiteDatabaseHelper.getWritableDatabase();

        final CityRepository cityRepository = new CityRepositoryImpl(db);

        // Get references to the UI elements
        cityEditText = findViewById(R.id.city_editText);

        String[] citySizes = {getString(R.string.city_size_small),
                getString(R.string.city_size_medium), getString(R.string.city_size_large)};

        Spinner citySizeSpinner = findViewById(R.id.citySize_spinner);
        ArrayAdapter<String> citySizeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, citySizes);
        citySizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySizeSpinner.setAdapter(citySizeAdapter);
        citySizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCitySize = parent.getItemAtPosition(position).toString();
                // You can use the selectedCitySize value in your app as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        monthSpinner = findViewById(R.id.month_spinner);
        temperatureEditText = findViewById(R.id.temperature_editText);
        saveButton = findViewById(R.id.save_button);

        // Set up the spinner with the months
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        // Set up the onItemSelectedListener for the spinner
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Save the selected month
                selectedMonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up the onClickListener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String cityName = cityEditText.getText().toString();
                String size = sizeEditText.getText().toString();
                String temperature = temperatureEditText.getText().toString();

                // Validate the input values
                if (TextUtils.isEmpty(cityName) && TextUtils.isEmpty(size) || TextUtils.isEmpty(temperature)) {
                    Toast.makeText(EditActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the input values to the repository
                long city_id = cityRepository.addCity(cityName,
                        CitySize.valueOf(size));
                cityRepository.addTemperature(temperature,
                        Month.valueOf(selectedMonth), city_id);

                WeatherService weatherService = WeatherService.getInstance(activity);
                Season season = weatherService.seasonByMonth(Month.valueOf(selectedMonth));

                City city = cityRepository.getCity(cityName);

                // Calculate the average temperature for the season
                String averageTemperature = "Season not defined";
                if (completedSeason(city, Month.valueOf(selectedMonth), weatherService)) {
                    averageTemperature = weatherService
                            .getAverageTemperature(city, season);
                }

                // Return to the first screen with the average temperature
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.putExtra("averageTemperature", averageTemperature);
                startActivity(intent);
            }
        });
    }

    private boolean completedSeason(City city, Month lastMonth, WeatherService weatherService) {

        Season season = weatherService.seasonByMonth(lastMonth);

        int startMonth = season.ordinal() * 3;
        int endMonth = startMonth + 2;

        String[] allTemperatures = city.getTemperatures();
        String[] preparedTemperatures = new String[12];
        System.arraycopy(allTemperatures, 2, preparedTemperatures, 0, 10);
        preparedTemperatures[10] = allTemperatures[0];
        preparedTemperatures[11] = allTemperatures[1];

        String[] seasonTemperatures = Arrays.copyOfRange(preparedTemperatures,
                startMonth, endMonth + 1);

        for (String temperature : seasonTemperatures) {
            if (!(temperature != null && temperature.matches("-?\\d+(\\.\\d+)? (°[CF]|K)"))) {
                return false;
            }
        }

        return true;
    }
}
