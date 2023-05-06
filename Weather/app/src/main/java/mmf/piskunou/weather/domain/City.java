package mmf.piskunou.weather.domain;

public abstract class City {
    private String name;
    private String[] temperatures;
    protected CitySize sizeType;

    public City(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CitySize getSizeType() {
        return sizeType;
    }

    public String[] getTemperatures() {
        return temperatures;
    }
}
