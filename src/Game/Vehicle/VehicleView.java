package Game.Vehicle;

import Game.Map;

import java.awt.*;

public class VehicleView {

    private VehicleController controller;
    private Vehicle model;

    public VehicleView(Vehicle model){
        this.model = model;
        controller = new VehicleController(this, model);
    }

    public void drawVehicle(Graphics g){
        g = controller.setColor(g);
        g.fillRect(controller.getX(), controller.getY(), 52, 35);
    }
}
