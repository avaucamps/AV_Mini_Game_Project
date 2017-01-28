package Game.Passenger;


import java.awt.image.BufferedImage;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class PassengerController {

    private PassengerView view;
    private PassengerModel model;

    public PassengerController(PassengerView view, PassengerModel model){
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
