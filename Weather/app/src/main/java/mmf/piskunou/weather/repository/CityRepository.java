package mmf.piskunou.weather.repository;

import java.util.List;

import mmf.piskunou.weather.domain.City;
import mmf.piskunou.weather.domain.CitySize;
import mmf.piskunou.weather.domain.Month;

public interface CityRepository {

    City getCity(String name);
    List<String> getAllCityNames();
    long addCity(String name, CitySize size);
    long addTemperature(String temperature, Month month, long cityId);
}
