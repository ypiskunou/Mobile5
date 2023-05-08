package mmf.piskunou.weatherx.domain;

public class MediumCity extends City{

    public MediumCity(String name, double[] temperatures) {
        super(name);
        sizeType = CitySize.MEDIUM;
    }
}
