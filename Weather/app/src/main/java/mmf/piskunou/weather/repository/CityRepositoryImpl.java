package mmf.piskunou.weather.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mmf.piskunou.weather.CityFactoryProvider;
import mmf.piskunou.weather.WeatherContract;
import mmf.piskunou.weather.WeatherContract.CityEntry;
import mmf.piskunou.weather.WeatherContract.TemperatureEntry;
import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.CitySize;
import mmf.piskunou.weather.domain.Month;

public class CityRepositoryImpl implements CityRepository {

    private static final String TAG = "CityRepository";

    private final SQLiteDatabase mDatabase;

    public CityRepositoryImpl(SQLiteDatabase database) {
        mDatabase = database;
    }

    public long addCity(String name, CitySize size) {

        ContentValues cityValues = new ContentValues();
        cityValues.put(CityEntry.COLUMN_CITY_NAME, name);
        cityValues.put(CityEntry.COLUMN_CITY_SIZE, size.toString());

        long cityId = mDatabase.insert(CityEntry.TABLE_NAME, null, cityValues);

        return cityId;
    }

    public long addTemperature(String temperature, Month month, long cityId) {

        ContentValues temperatureValues = new ContentValues();

        temperatureValues.put(TemperatureEntry.COLUMN_CITY_ID, cityId);
        temperatureValues.put(TemperatureEntry.COLUMN_MONTH, month.ordinal());
        temperatureValues.put(TemperatureEntry.COLUMN_TEMPERATURE, temperature);

        return mDatabase.insert(TemperatureEntry.TABLE_NAME, null, temperatureValues);
    }

    public List<String> getAllCityNames() {

        List<String> cityNames = new ArrayList<>();
        for (City city : getCities()) {
            cityNames.add(city.getName());
        }
        return cityNames;
    }

    private List<City> getCities() {

        City city;
        CitySize size;
        List<City> cities = new ArrayList<>();

        CityFactoryProvider provider = CityFactoryProvider.getInstance();

        String[] projection = {
                CityEntry._ID,
                CityEntry.COLUMN_CITY_NAME,
                CityEntry.COLUMN_CITY_SIZE
        };

        Cursor cursor = mDatabase.query(
                WeatherContract.CityEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int cityId = cursor.getInt(cursor.getColumnIndexOrThrow(WeatherContract.CityEntry._ID));
            String cityName = cursor.getString(cursor.getColumnIndexOrThrow(CityEntry.COLUMN_CITY_NAME));
            String sizeString = cursor.getString(cursor.getColumnIndex(CityEntry.COLUMN_CITY_SIZE));
            size = CitySize.valueOf(sizeString);

            String[] temperatureProjection = {
                    "GROUP_CONCAT(" + WeatherContract.TemperatureEntry.COLUMN_TEMPERATURE + ") AS temperatures"
            };

            String selection = WeatherContract.TemperatureEntry.COLUMN_CITY_ID + " = ?";
            String[] selectionArgs = {String.valueOf(cityId)};

            Cursor temperatureCursor = mDatabase.query(
                    WeatherContract.TemperatureEntry.TABLE_NAME,
                    temperatureProjection,
                    selection,
                    selectionArgs,
                    WeatherContract.TemperatureEntry.COLUMN_MONTH,
                    null,
                    null
            );

            String temperaturesString = temperatureCursor.moveToFirst() ? temperatureCursor.getString(
                    temperatureCursor.getColumnIndex("temperatures")) : "";

            String[] temperatureStrings = temperaturesString.split(",");
            double[] temperatures = new double[temperatureStrings.length];
            for (int i = 0; i < temperatureStrings.length; i++) {
                temperatures[i] = Double.parseDouble(temperatureStrings[i]);
            }

            city = provider.getFactory(size).createCity(cityName, temperatures);
            cities.add(city);

            temperatureCursor.close();
        }

        cursor.close();

        return cities;
    }


    public City getCity(String name) {
        List<City> cities = getCities();
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }
}
