package city;

/**
 * Created by Richard on 7/5/2015.
 */
public class MockCityPackageFactory {

    public static CityPackage[] getSimplePackage(){
        CityBuilding building = new CityBuilding("foo", 700, 100, 0);
        CityBuilding building2 = new CityBuilding("bar", 400, 50, 50);
        CityBuilding building3 = new CityBuilding("gaa", 1000, 10, 100);
        CityPackage pack = new CityPackage("Root", building, building2, building3);
        return new CityPackage[]{pack};
    }

    public static CityPackage[] getSimpleDoublePackage(){
        CityBuilding buildingA1 = new CityBuilding("foo", 700, 100, 0);
        CityBuilding buildingA2 = new CityBuilding("bar", 400, 50, 50);
        CityBuilding buildingA3 = new CityBuilding("gaa", 1000, 10, 100);
        CityPackage packA = new CityPackage("Root", buildingA1, buildingA2, buildingA3);

        CityBuilding buildingB1 = new CityBuilding("foo", 700, 200, 100);
        CityBuilding buildingB2 = new CityBuilding("bar", 400, 70, 0);
        CityBuilding buildingB3 = new CityBuilding("gaa", 1000, 30, 50);
        CityPackage packB = new CityPackage("Root", buildingB1, buildingB2, buildingB3);

        return new CityPackage[]{packA, packB};
    }

    public static CityPackage[] getMediumSizePackage(){
        CityBuilding buildingA1 = new CityBuilding("Level A 1", 700, 100, 0);
        CityBuilding buildingA2 = new CityBuilding("Level A 2", 400, 50, 50);
        CityBuilding buildingA3 = new CityBuilding("Level A 3", 1000, 10, 100);
        CityPackage subPackA = new CityPackage("Level A", buildingA1, buildingA2, buildingA3);

        CityBuilding buildingB1 = new CityBuilding("Level B 1", 100, 100, 10);
        CityBuilding buildingB2 = new CityBuilding("Level B 2", 300, 200, 20);
        CityPackage subPackB = new CityPackage("Level B", buildingB1, buildingB2);

        CityBuilding buildingRoot1 = new CityBuilding("RootBuilding", 900, 100, 50);
        CityPackage rootPackage = new CityPackage("Root", buildingRoot1);

        subPackA.addSubPackage(subPackB);
        rootPackage.addSubPackage(subPackA);

        return new CityPackage[]{rootPackage};
    }
}
