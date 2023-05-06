package mmf.piskunou.weather.domain;

public class KelvinTemperature implements TemperatureFormat {

    @Override
    public String format(double temperature) {
        return String.format("%.2f K", temperature + 273.15);
    }
}
