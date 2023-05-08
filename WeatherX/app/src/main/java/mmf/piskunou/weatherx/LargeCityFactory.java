package mmf.piskunou.weatherx;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.LargeCity;

public class LargeCityFactory implements CityFactory {

    @Override
    public City createCity(String name, double[] temperatures) {
        return new LargeCity(name, temperatures);
    }
}
