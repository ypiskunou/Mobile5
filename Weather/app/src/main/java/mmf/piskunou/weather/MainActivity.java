package mmf.piskunou.weather;

/**
 * @author Yury Piskunou especially for Innowise Group by 06.05.23
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.Season;
import mmf.piskunou.weather.repository.CityRepository;
import mmf.piskunou.weather.repository.CityRepositoryImpl;
import mmf.piskunou.weather.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WeatherApp";
    private SQLiteDatabase db;

    private Spinner citySpinner;
    private Spinner seasonSpinner;
    private TextView temperatureTextView;
    private TextView cityTypeTextView;
    private CityRepository cityRepository;
    private City city;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to UI elements
        citySpinner = findViewById(R.id.city_spinner);
        seasonSpinner = findViewById(R.id.season_spinner);
        temperatureTextView = findViewById(R.id.temperature_text_view);
        cityTypeTextView = findViewById(R.id.city_type_text_view);

        SQLiteDatabaseHelper sqLiteDatabaseHelper = SQLiteDatabaseHelper
                .getInstance(getApplicationContext());

        db = sqLiteDatabaseHelper.getWritableDatabase();

        // Create a new instance of the CityRepository class
        cityRepository = new CityRepositoryImpl(db);

        // Populate the city spinner with data from the database
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cityRepository.getAllCityNames());
        citySpinner.setAdapter(cityAdapter);

        // Set a listener for the city spinner
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected city name
                String cityName = (String) parent.getItemAtPosition(position);

                // Get the corresponding City object
                city = cityRepository.getCity(cityName);

                // Display the city type
                cityTypeTextView.setText(city.getSizeType().toString());

                // Update the temperature text view
                updateTemperatureTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set a listener for the season spinner
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the temperature text view
                updateTemperatureTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


    private void updateTemperatureTextView() {

        String season = (String) seasonSpinner.getSelectedItem();
        weatherService = WeatherService.getInstance(this);
        String averageTemperature = "";

        // Get the average temperature for the city and season from the database
        try {
            averageTemperature = weatherService.getAverageTemperature(city,
                    Enum.valueOf(Season.class, season));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid season name: " + season, e);
            Snackbar.make(temperatureTextView, "Invalid season name: " + season, Snackbar.LENGTH_LONG).show();
        }

        temperatureTextView.setText(averageTemperature);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        db.close();
    }
}
