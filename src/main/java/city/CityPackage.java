package city;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityPackage {

    public String getName() {
        return name;
    }

    public List<CityBuilding> getBuildings() {
        return buildings;
    }

    public List<CityPackage> getSubPackages() {
        return subPackages;
    }

    private String name;
    private List<CityBuilding> buildings;
    private List<CityPackage> subPackages;

    public CityPackage(String packageName, CityBuilding... buildings){
        this(packageName, Arrays.asList(buildings));
    }

    public CityPackage(String packageName, List<CityBuilding> buildings){
        this.name = packageName;
        this.buildings = buildings;
        this.subPackages = new LinkedList<>();
    }

    public void addSubPackage(CityPackage pack){
        this.subPackages.add(pack);
    }
}
