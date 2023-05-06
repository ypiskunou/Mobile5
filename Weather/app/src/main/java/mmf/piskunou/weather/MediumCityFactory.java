package mmf.piskunou.weather;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.MediumCity;
import mmf.piskunou.weather.repository.CityRepository;

public class MediumCityFactory implements CityFactory {

    @Override
    public City createCity(String name, double[] temperatures) {
        return new MediumCity(name, temperatures);
    }
}
