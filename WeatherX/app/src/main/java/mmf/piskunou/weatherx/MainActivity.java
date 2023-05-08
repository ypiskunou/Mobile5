package mmf.piskunou.weatherx;

/**
 * @author Yury Piskunou especially for Innowise Group by 06.05.23
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.Season;
import mmf.piskunou.weatherx.repository.CityRepository;
import mmf.piskunou.weatherx.repository.CityRepositoryImpl;
import mmf.piskunou.weatherx.services.WeatherService;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get references to UI elements
        citySpinner = findViewById(R.id.city_spinner);
        seasonSpinner = findViewById(R.id.season_spinner);
        temperatureTextView = findViewById(R.id.temperature_textView);
        cityTypeTextView = findViewById(R.id.city_type_textView);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            // Navigate to the edit activity
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
