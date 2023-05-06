package mmf.piskunou.weather.domain;

public class FarenheitTemperature implements TemperatureFormat{

    @Override
    public String format(double temperature) {
        return String.format("%.2f °F", temperature * 1.8 + 32);
    }
}
