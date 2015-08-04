package cityview.pack;

import cityview.structure.Structure;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 8/4/2015.
 */
public class RowStructures<T extends Structure> {

    private List<T> fittingStructures;

    public RowStructures(List<T> fittingStructures){
        this.fittingStructures = fittingStructures;
    }

    public List<T> getFittingStructures(){
        return fittingStructures;
    }

    public static <T extends Structure> List<T> removeStructuresThatFitIntoWidth(ListIterator<T> followingStructures, double maxWidth){
        List<T> fittingStructures = new LinkedList<>();
        while(followingStructures.hasNext()){
            T nextStructure = followingStructures.next();

            double structureWidth = nextStructure.getStructureWidth();
            if(structureWidth <= maxWidth || fittingStructures.size() == 0){
                maxWidth -= structureWidth;
                fittingStructures.add(nextStructure);
                followingStructures.remove();
            }else{
                // reset iterator
                followingStructures.previous();
                break;
            }
        }

        return fittingStructures;
    }
}
