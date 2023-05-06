package mmf.piskunou.weather.domain;

public class Temperature {

    private double temperature;
    private TemperatureFormat temperatureFormat;

    public Temperature(double temperature, TemperatureFormat temperatureFormat) {
        this.temperature = temperature;
        this.temperatureFormat = temperatureFormat;
    }

    public void setTemperatureFormat(TemperatureFormat temperatureFormat) {
        this.temperatureFormat = temperatureFormat;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getFormattedTemperature() {
        return temperatureFormat.format(temperature);
    }
}
