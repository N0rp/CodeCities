package city;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityBuilding {

    public String getName() {
        return name;
    }

    public int getLoc() {
        return loc;
    }

    public int getMcccabe() {
        return mcccabe;
    }

    public int getNl() {
        return nl;
    }

    private String name;
    private int loc;
    private int mcccabe;
    private int nl;

    public CityBuilding(String name, int loc, int mccabe, int nl){
        this.name = name;
        this.loc = loc;
        this.mcccabe = mccabe;
        this.nl = nl;
    }

}
