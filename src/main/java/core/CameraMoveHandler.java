package core;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;

/**
 * Created by Richard on 7/5/2015.
 */
public class CameraMoveHandler implements EventHandler<MouseEvent>{
    @Override
    public void handle(MouseEvent event){
        PickResult res = event.getPickResult();
        setState(res);
        event.consume();
    }

    final void setState(PickResult result){
        if(result.getIntersectedNode() == null){
            Node intersectedNode = result.getIntersectedNode();
            System.out.println("Move Node");
        }else{
            System.out.println("Move camera");
        }
    }
}
