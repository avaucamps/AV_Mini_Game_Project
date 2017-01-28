package Game.Vehicle;


import java.awt.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class VehicleController {

    private Vehicle model;
    private VehicleView view;

    public VehicleController(VehicleView view, Vehicle model){
        this.view = view;
        this.model = model;
    }

    public int getX(){
        return intValue(model.getX());
    }

    public int getY(){
        return intValue(model.getY());
    }

    public Graphics setColor(Graphics g){
        switch(model.getLine().getColor()){
            case 0 :
                g.setColor(Color.YELLOW);
                break;
            case 1 :
                g.setColor(Color.RED);
                break;
            case 2 :
                g.setColor(Color.GREEN);
                break;
            case 3 :
                g.setColor(Color.BLUE);
                break;
            case 4 :
                g.setColor(Color.CYAN);
                break;
            case 5 :
                g.setColor(Color.MAGENTA);
                break;
        }
        return g;
    }
}
