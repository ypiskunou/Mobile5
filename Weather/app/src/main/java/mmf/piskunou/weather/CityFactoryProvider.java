package mmf.piskunou.weather;

import java.util.HashMap;
import java.util.Map;

import mmf.piskunou.weather.domain.CitySize;

public class CityFactoryProvider {

    private static final Map<CitySize, CityFactory> factories = new HashMap<>();
    private static volatile CityFactoryProvider instance;

    private CityFactoryProvider() {
        factories.put(CitySize.SMALL, new SmallCityFactory());
        factories.put(CitySize.MEDIUM, new MediumCityFactory());
        factories.put(CitySize.LARGE, new LargeCityFactory());
    }

    public static synchronized CityFactoryProvider getInstance() {
        if (instance == null) {
            synchronized (CityFactoryProvider.class) {
                if (instance == null) {
                    instance = new CityFactoryProvider();
                }
            }
        }
        return instance;
    }

    public CityFactory getFactory(CitySize size) {
        CityFactory factory = factories.get(size);
        if (factory == null) {
            throw new IllegalArgumentException("There is no such city size: " + size);
        }
        return factory;
    }
}
