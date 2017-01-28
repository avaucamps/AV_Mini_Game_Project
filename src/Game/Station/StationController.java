package Game.Station;

import java.awt.image.BufferedImage;

import static oracle.jrockit.jfr.events.Bits.intValue;


public class StationController {

    private StationView view;
    private StationModel model;

    public StationController(StationView view, StationModel model){
        this.view = view;
        this.model = model;
    }

    public BufferedImage getImage(){
        return model.getImage();
    }

    public int getX(double xCoeff){
        return intValue(model.getX()*xCoeff);
    }

    public int getY(double yCoeff){
        return intValue(model.getY()*yCoeff);
    }
}
