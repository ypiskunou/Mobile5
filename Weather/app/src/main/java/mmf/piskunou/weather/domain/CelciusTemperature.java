package mmf.piskunou.weather.domain;

public class CelciusTemperature implements TemperatureFormat {

    @Override
    public String format(double temperature) {
        return String.format("%.2f °C", temperature);
    }
}
