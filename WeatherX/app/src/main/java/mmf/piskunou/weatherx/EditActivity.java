package mmf.piskunou.weatherx;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.CitySize;
import mmf.piskunou.weatherx.domain.Month;
import mmf.piskunou.weatherx.domain.Season;
import mmf.piskunou.weatherx.repository.CityRepository;
import mmf.piskunou.weatherx.repository.CityRepositoryImpl;
import mmf.piskunou.weatherx.services.WeatherService;

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

        Spinner citySizeSpinner = findViewById(R.id.city_size_spinner);
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
