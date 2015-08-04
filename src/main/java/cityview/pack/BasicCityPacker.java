package cityview.pack;

import cityview.structure.Block;
import cityview.structure.Building;
import cityview.structure.Structure;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityPacker implements CityPacker {

    private Block rootBlock;
    private double currentZ;

    public BasicCityPacker(Block block, double currentZ){
        this.rootBlock = block;
        this.currentZ = currentZ;
    }

    @Override
    public void fitBlock(){
        fitBlockIntoSize(rootBlock);
    }

    private void fitBlockIntoSize(Block block){
        double maxSize = block.findSumForMetric(block.getSizeMetricName());
        double maxWidth = Math.sqrt(maxSize);
        //fitSubBlocks(block.getBlocks(), maxSizeMetric);
        fitBuildings(block.getBuildings(), maxWidth);
    }

    private <T extends Structure> void fitStructures(List<T> structures, double maxWidth){
        List<T> remainingStructures = new LinkedList<>(structures);

        for(ListIterator<T> iterator = remainingStructures.listIterator(); iterator.hasNext();){
            T structure = iterator.next();
            iterator.remove();

            List<T> structuresInRow = removeStructuresThatFitIntoWidth(iterator, maxWidth - structure.getStructureWidth());
            structuresInRow.add(0, structure);
            // arrange and resize
            if(structure instanceof Block){
                fitBlocks(structures, maxWidth);
            }else if(structure instanceof Building){
                fitBuildings(structures, maxWidth);
            }

            //double maxDepth = rowPacker.getMaxDepth(structuresInRow);
            //currentDepth += maxDepth;
        }
    }


    private <T extends Structure> void fitBlocks(List<T> blocks, double maxWidth){
        for(T genericBlock : blocks){
            Block block = (Block)genericBlock;
            fitBlockIntoSize(block);
        }
    }

    private <T extends Structure> void fitBuildings(List<T> genericBuildings, double maxWidth){
        List<Building> buildings = new LinkedList<>();
        for(T genericBuilding : genericBuildings){
            buildings.add((Building)genericBuilding);
        }
        RowPacker rowPacker = new RowPacker(buildings, maxWidth, currentZ);
        rowPacker.resizeBuildings();
        rowPacker.arrangeBuildings();

        currentZ += rowPacker.getRowDepth();
    }

    private <T extends Structure> List<T> removeStructuresThatFitIntoWidth(ListIterator<T> followingStructures, double maxWidth){
        List<T> fittingStructures = new LinkedList<>();
        while(followingStructures.hasNext()){
            T nextStructure = followingStructures.next();

            double structureWidth = nextStructure.getStructureWidth();
            if(structureWidth <= maxWidth){
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
