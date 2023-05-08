package mmf.piskunou.weatherx;

import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.SmallCity;

public class SmallCityFactory implements CityFactory {
    @Override
    public City createCity(String name, double[] temperatures) {
        return new SmallCity(name, temperatures);
    }
}
