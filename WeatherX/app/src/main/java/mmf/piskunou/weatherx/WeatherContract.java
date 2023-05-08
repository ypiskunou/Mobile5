package mmf.piskunou.weatherx;

import android.provider.BaseColumns;

public class WeatherContract {

    public static final class CityEntry implements BaseColumns {

        private static final String DATABASE_NAME = "weather.db";
        private static final int SCHEMA = 1;

        public static final String TABLE_NAME = "city";
        // Column names
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_CITY_SIZE = "city_size";
    }

    public static final class TemperatureEntry implements BaseColumns {

        public static final String TABLE_NAME = "temperature";
        // Column names
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_TEMPERATURE = "temperature";
    }
}
