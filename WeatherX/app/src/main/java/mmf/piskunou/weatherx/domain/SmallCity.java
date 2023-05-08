package mmf.piskunou.weatherx.domain;

public class SmallCity extends City{

    public SmallCity(String name, double[] temperatures) {
        super(name);
        sizeType = CitySize.SMALL;
    }
}
