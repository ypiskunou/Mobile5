package mmf.piskunou.weather.domain;

public class SmallCity extends City{

    public SmallCity(String name, double[] temperatures) {
        super(name);
        sizeType = CitySize.SMALL;
    }
}
