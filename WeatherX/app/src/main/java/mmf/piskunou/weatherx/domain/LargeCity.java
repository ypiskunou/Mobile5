package mmf.piskunou.weatherx.domain;

public class LargeCity extends City {

    public LargeCity(String name, double[] temperatures) {
        super(name);
        sizeType = CitySize.LARGE;
    }
}