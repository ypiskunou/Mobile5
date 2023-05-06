package mmf.piskunou.weather;

import mmf.piskunou.weather.domain.City;

public interface CityFactory {

    City createCity(String name, double[] temperatures);
}
