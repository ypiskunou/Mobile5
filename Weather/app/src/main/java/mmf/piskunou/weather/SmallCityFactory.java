package mmf.piskunou.weather;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.SmallCity;
import mmf.piskunou.weather.repository.CityRepository;

public class SmallCityFactory implements CityFactory {
    @Override
    public City createCity(String name, double[] temperatures) {
        return new SmallCity(name, temperatures);
    }
}
