package mmf.piskunou.weatherx.repository;

import java.util.List;

import mmf.piskunou.weatherx.domain.City;
import mmf.piskunou.weatherx.domain.CitySize;
import mmf.piskunou.weatherx.domain.Month;

public interface CityRepository {

    City getCity(String name);
    List<String> getAllCityNames();
    long addCity(String name, CitySize size);
    long addTemperature(String temperature, Month month, long cityId);
}
