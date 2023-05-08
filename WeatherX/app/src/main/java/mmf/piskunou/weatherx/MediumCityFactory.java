package mmf.piskunou.weatherx;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.MediumCity;

public class MediumCityFactory implements CityFactory {

    @Override
    public City createCity(String name, double[] temperatures) {
        return new MediumCity(name, temperatures);
    }
}
