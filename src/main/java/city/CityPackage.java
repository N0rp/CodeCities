package city;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityPackage {

    public String getName() {
        return name;
    }

    public CityBuilding[] getBuildings() {
        return buildings;
    }

    private String name;
    private CityBuilding[] buildings;

    public CityPackage(String packageName, CityBuilding... buildings){
        this.name = packageName;
        this.buildings = buildings;
    }
}
