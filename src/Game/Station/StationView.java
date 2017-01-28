package Game.Station;

import Game.Station.StationController;

import java.awt.*;

public class StationView {

    private StationController controller;
    private StationModel model;

    public StationView(StationModel model){
        this.model = model;
        controller = new StationController(this, model);
    }

    public void drawStation(Graphics g, double xCoeff, double yCoeff){
        g.drawImage(controller.getImage(), controller.getX(xCoeff), controller.getY(yCoeff), null);
    }

}
