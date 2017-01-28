package Game.Vehicle;

import Game.Line;
import Game.Passenger.PassengerModel;

import java.util.ArrayList;

public class Vehicle {

    private double x, y;
    private Line line;
    private ArrayList<PassengerModel> passengers = new ArrayList<>();

    public Vehicle(double x, double y, Line line){
        this.x = x;
        this.y = y;
        this.line = line;
    }

    /**
     * Method used to know if there is still room for more passengers
     */
    public boolean isFull(){
        return passengers.size()==6;
    }

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public Line getLine(){
        return line;
    }

    public ArrayList<PassengerModel> getPassengers(){
        return passengers;
    }

    public void addPassenger(PassengerModel p){
        passengers.add(p);
    }

    public void removePassenger(PassengerModel p){
        passengers.remove(p);
    }
}

