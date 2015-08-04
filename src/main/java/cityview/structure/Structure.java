package cityview.structure;

import javafx.beans.value.ObservableDoubleValue;

/**
 * Created by Richard on 7/29/2015.
 */
public interface Structure {

    String getName();

    double getStructureWidth();
    ObservableDoubleValue structureWidthProperty();

    double getStructureDepth();
    ObservableDoubleValue structureDepthProperty();
}
