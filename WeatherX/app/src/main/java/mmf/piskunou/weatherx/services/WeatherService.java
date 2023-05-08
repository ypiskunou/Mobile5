package mmf.piskunou.weatherx.services;

import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import mmf.piskunou.weatherx.R;
import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.Month;
import mmf.piskunou.weatherx.domain.Season;

public class WeatherService {

    public static final String TAG = "WeatherService";
    private AppCompatActivity activity;

    private static volatile WeatherService instance;

    private WeatherService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static WeatherService getInstance(AppCompatActivity activity) {
        if (instance == null) {
            synchronized (WeatherService.class) {
                if (instance == null) {
                    instance = new WeatherService(activity);
                }
            }
        }
        return instance;
    }


    public Season seasonByMonth(Month month) {

        Season season = Season.WINTER;

        try {
            season = Season.values()[(month.ordinal()+1)/3 - 1];
        } catch (IndexOutOfBoundsException exc){
            return season;
        }

        return season;
    }


    public String getAverageTemperature(City city, Season season) {

        int startMonth = season.ordinal() * 3;
        int endMonth = startMonth + 2;

        String[] allTemperatures = city.getTemperatures();
        String[] preparedTemperatures = new String[12];
        System.arraycopy(allTemperatures, 2, preparedTemperatures, 0, 10);
        preparedTemperatures[10] = allTemperatures[0];
        preparedTemperatures[11] = allTemperatures[1];

        String[] seasonTemperatures = Arrays.copyOfRange(preparedTemperatures,
                startMonth, endMonth + 1);
        double sum = 0.0;
        int count = 0;

        for (String temperature : seasonTemperatures) {
            double value = 0;
            if (temperature != null && temperature.matches("-?\\d+(\\.\\d+)? (°[CF]|K)")) {
                value = Double.parseDouble(temperature.substring(0, temperature.indexOf(" ")));
                if (temperature.endsWith(" °F")) {
                    // Convert temperature from Fahrenheit to Celsius
                    value = (value - 32) * 5 / 9;
                } else if (temperature.endsWith(" K")) {
                    // Convert temperature from Kelvin to Celsius
                    value = value - 273.15;
                }
            } else {
                String errorMessage = "Invalid temperature: " + temperature;
                Log.e(TAG, errorMessage);
                Snackbar.make(activity.findViewById(R.id.temperature_textView),
                        errorMessage, Snackbar.LENGTH_LONG).show();
            }
            sum += value;
            count++;
        }

        if (count != 3) {
            throw new IllegalArgumentException("No temperature data available for the specified season.");
        }

        return String.format(Locale.getDefault(),"%.1f °C", sum / count);
    }
}
