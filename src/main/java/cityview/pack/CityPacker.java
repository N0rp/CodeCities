package cityview.pack;

import cityview.structure.Block;

/**
 * Created by Richard on 7/27/2015.
 */
public interface CityPacker {

    /**
     * Arrange just the buildings inside the block
     *
     * @param block
     * @param size
     */
    void fitBlockIntoSize(Block block, double size);
}
