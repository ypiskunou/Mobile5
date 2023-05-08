package mmf.piskunou.weatherx;

import mmf.piskunou.weatherx.domain.City;

public interface CityFactory {

    City createCity(String name, double[] temperatures);
}
