package mmf.piskunou.weatherx;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static volatile SQLiteDatabaseHelper sInstance;
    private volatile SQLiteDatabase mDatabase;

    // Database name and version
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    private SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteDatabaseHelper getInstance(Context context) {
        if (sInstance == null) synchronized (SQLiteDatabaseHelper.class) {
            if (sInstance == null) {
                sInstance = new SQLiteDatabaseHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    public SQLiteDatabase getDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) synchronized (SQLiteDatabaseHelper.class) {
            if (mDatabase == null || !mDatabase.isOpen()) {
                mDatabase = getWritableDatabase();
            }
        }
        return mDatabase;
    }

    public static synchronized void closeDatabase() {
        if (sInstance != null) {
            sInstance.close();
            sInstance = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create City table
        final String SQL_CREATE_CITY_TABLE = "CREATE TABLE " +
                WeatherContract.CityEntry.TABLE_NAME + " (" +
                WeatherContract.CityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherContract.CityEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                WeatherContract.CityEntry.COLUMN_CITY_SIZE + " TEXT NOT NULL);";

        // Create Temperature table
        final String SQL_CREATE_TEMPERATURE_TABLE = "CREATE TABLE " +
                WeatherContract.TemperatureEntry.TABLE_NAME + " (" +
                WeatherContract.TemperatureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherContract.TemperatureEntry.COLUMN_CITY_ID + " INTEGER NOT NULL, " +
                WeatherContract.TemperatureEntry.COLUMN_MONTH + " INTEGER NOT NULL, " +
                WeatherContract.TemperatureEntry.COLUMN_TEMPERATURE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + WeatherContract.TemperatureEntry.COLUMN_CITY_ID + ") REFERENCES " +
                WeatherContract.CityEntry.TABLE_NAME + " (" + WeatherContract.CityEntry._ID + "));";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.CityEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.TemperatureEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
