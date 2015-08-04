package cityview.pack;

import cityview.structure.Block;
import cityview.structure.Building;
import cityview.structure.Structure;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityPacker implements CityPacker {

    private Block rootBlock;
    private double currentX;
    private double currentZ;

    public BasicCityPacker(Block block, double currentZ){
        this.rootBlock = block;
        this.currentX = 0;
        this.currentZ = currentZ;
    }

    @Override
    public void fitBlock(){
        double maxSize = rootBlock.findSumForMetric(rootBlock.getSizeMetricName());
        double maxWidth = Math.sqrt(maxSize);
        fitBlock(rootBlock, maxWidth);
    }

    private void fitBlock(Block block, double maxWidth){
        List<RowStructures<Block>> blockRows = cutIntoRows(block.getBlocks(), maxWidth);

        for(RowStructures<Block> blockRow : blockRows){
            fitBlockStructures(blockRow, maxWidth);
            double z = blockRow.getFittingStructures().get(0).getStructureDepth();
            currentZ += z;
        }
        double blockDepth = block.getStructureDepth();
        fitBuildings(block.getBuildings(), maxWidth);
        blockDepth = block.getStructureDepth();
    }

    private void fitBlockStructures(RowStructures<Block> blockRow, double maxWidth){
        for(Block block : blockRow.getFittingStructures()){
            fitBlock(block, maxWidth);
        }
    }

    private void fitBuildings(List<Building> buildings, double maxWidth){
        List<RowStructures<Building>> buildingRows = cutIntoRows(buildings, maxWidth);

        for(RowStructures<Building> buildingRow : buildingRows){
            fitBuildingStructures(buildingRow, maxWidth);
        }
    }

    private void fitBuildingStructures(RowStructures<Building> blockRow, double maxWidth){
        RowPacker rowPacker = RowPackerFactory.Create()
                .buildingsInRow(blockRow).rowWidth(maxWidth).rowZ(currentZ).build();
        rowPacker.resizeBuildings();
        rowPacker.arrangeBuildings();

        double rowDepth = rowPacker.getRowDepth();
        currentZ += rowDepth;
    }

    private <T extends Structure> List<RowStructures<T>> cutIntoRows(List<T> structures, double maxWidth){
        List<RowStructures<T>> result = new LinkedList<>();
        List<T> remainingStructures = new LinkedList<>(structures);

        for(ListIterator<T> iterator = remainingStructures.listIterator(); iterator.hasNext();){
            RowStructures<T> blockRow = new RowStructures<>(
                    RowStructures.removeStructuresThatFitIntoWidth(iterator, maxWidth));
            result.add(blockRow);
        }
        return result;
    }

}
