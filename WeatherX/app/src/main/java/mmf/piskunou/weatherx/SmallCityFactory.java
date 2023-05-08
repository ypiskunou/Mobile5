package mmf.piskunou.weatherx;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.SmallCity;

public class SmallCityFactory implements CityFactory {
    @Override
    public City createCity(String name, double[] temperatures) {
        return new SmallCity(name, temperatures);
    }
}
