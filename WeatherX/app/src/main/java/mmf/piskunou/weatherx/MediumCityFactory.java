package mmf.piskunou.weatherx;

import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.MediumCity;

public class MediumCityFactory implements CityFactory {

    @Override
    public City createCity(String name, double[] temperatures) {
        return new MediumCity(name, temperatures);
    }
}
