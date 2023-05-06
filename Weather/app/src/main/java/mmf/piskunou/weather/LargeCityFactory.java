package mmf.piskunou.weather;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.LargeCity;
import mmf.piskunou.weather.repository.CityRepository;

public class LargeCityFactory implements CityFactory {

    @Override
    public City createCity(String name, double[] temperatures) {
        return new LargeCity(name, temperatures);
    }
}
