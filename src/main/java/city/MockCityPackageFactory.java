package city;

/**
 * Created by Richard on 7/5/2015.
 */
public class MockCityPackageFactory {

    public static CityPackage getSimplePackage(){
        CityBuilding building = new CityBuilding("foo", 700, 100, 0);
        CityBuilding building2 = new CityBuilding("bar", 400, 50, 50);
        CityBuilding building3 = new CityBuilding("gaa", 1000, 10, 100);
        CityPackage pack = new CityPackage("Something", building, building2, building3);
        return pack;
    }

    public static CityPackage getMediumSizePackage(){
        CityBuilding building1 = new CityBuilding("foo", 700, 100, 0);
        CityBuilding building2 = new CityBuilding("bar", 400, 50, 50);
        CityBuilding building3 = new CityBuilding("gaa", 1000, 10, 100);
        CityPackage subPack2 = new CityPackage("Something", building1, building2, building3);
        CityBuilding building = new CityBuilding("foo", 700, 100, 0);
        CityPackage subPack1 = new CityPackage("Something", building);
        subPack1.addSubPackage(subPack2);
        CityPackage rootPackage = new CityPackage("Something");
        rootPackage.addSubPackage(subPack1);
        return rootPackage;
    }
}
