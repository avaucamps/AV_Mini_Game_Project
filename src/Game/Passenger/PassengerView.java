package Game.Passenger;


import java.awt.*;

public class PassengerView {

    private PassengerController controller;
    private PassengerModel model;

    public PassengerView(PassengerModel model){
        this.model = model;
        controller = new PassengerController(this, model);
    }

    public void drawPassenger(Graphics g, double xCoeff, double yCoeff){
        g.drawImage(controller.getImage(), controller.getX(xCoeff), controller.getY(yCoeff), null);
    }

}
