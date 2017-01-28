package Game.Vehicle;

import Game.Game;
import Game.Line;
import Game.Map;
import Game.Passenger.PassengerModel;
import Game.Segment.SegmentModel;
import Game.Station.StationModel;

import java.util.ArrayList;
import java.util.Iterator;

import static Game.Game.setScore;
import static java.lang.Thread.sleep;

public class Locomotive extends Vehicle implements Runnable{

    private ArrayList<Wagon> wagons = new ArrayList<>();
    public boolean outwardTrip;
    public boolean returnTrip;
    private StationModel departureStation;
    private StationModel arrivalStation;
    private SegmentModel segment;
    private int direction = 0; //1=right, 2=left, 3=up, 4=down
    private int segmentNumber = 0;

    public Locomotive(double x, double y, Line line){
        super(x, y, line);
    }

    public void addWagon(Wagon w){
        wagons.add(w);
    }

    public ArrayList<Wagon> getWagons(){
        return wagons;
    }

    public synchronized void stop() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Load and unload passengers
        unloadPassengers();
        loadPassengers();
        if(outwardTrip == true){
            segmentNumber++;
        }else if(returnTrip == true && segmentNumber > 0){
            segmentNumber--;
        }
        if(outwardTrip == true && segmentNumber < getLine().getSegments().size()) {
            getLine().outwardTrip(segmentNumber, this);
        }else if(outwardTrip == true && segmentNumber == getLine().getSegments().size()){
            setTrip(false, true, segmentNumber);
        }else if(returnTrip == true && segmentNumber > 0){
            getLine().returnTrip(segmentNumber, this);
        }else if(returnTrip == true && segmentNumber == 0){
            setTrip(true, false, 0);
        }
    }

    public void setTrip(boolean outwardTrip, boolean returnTrip, int segmentNumber){
        this.outwardTrip = outwardTrip;
        this.returnTrip = returnTrip;
        this.segmentNumber = segmentNumber;
        if(outwardTrip == true && returnTrip == false){
            getLine().outwardTrip(segmentNumber, this);
        }else if(outwardTrip == false && returnTrip == true){
            getLine().returnTrip(segmentNumber, this);
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                switch(direction){
                    case 1 :
                        goRight();
                        break;
                    case 2 :
                        goLeft();
                        break;
                    case 3 :
                        goUp();
                        break;
                    case 4 :
                        goDown();
                        break;
                }
                sleep(30);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moving the metro on a segment going right
     */
    public synchronized void goRight(){
        if(getX()-arrivalStation.getX()* Map.getxCoeff()<=2&&
                getX()-arrivalStation.getX()*Map.getxCoeff()>=-2){
            setX(arrivalStation.getX()*Map.getxCoeff());
            setY(arrivalStation.getY()*Map.getyCoeff());
            stop();
        }else{
            setX(getX()+1);
            setY(getY()+segment.getSlope());
            //We move the wagons
            int initialPosition = 53;
            for(Wagon w : wagons){
                w.setX((int)getX()-initialPosition);
                w.setY(getY());
                initialPosition += 53;
            }
            movePassengers();
        }
    }

    /**
     * Moving the metro on a segment going left
     */
    public synchronized void goLeft(){
        if(getX()-arrivalStation.getX()*Map.getxCoeff()<=2&&
                getX()-arrivalStation.getX()*Map.getxCoeff()>=-2){
            setX(arrivalStation.getX()*Map.getxCoeff());
            setY(arrivalStation.getY()*Map.getyCoeff());
            stop();
        }else{
            setX(getX()-1);
            setY(getY()-segment.getSlope());
            //We move the wagons
            int initialPosition = 53;
            for(Wagon w : wagons){
                w.setX((int)getX()+initialPosition);
                w.setY(getY());
                initialPosition += 53;
            }
            movePassengers();
        }
    }

    /**
     * Moving the metro on a segment going up
     */
    public synchronized void goUp(){
        if(getY()-arrivalStation.getY()*Map.getyCoeff()<=2&&
                getY()-arrivalStation.getY()*Map.getyCoeff()>=-2){
            setX(arrivalStation.getX()*Map.getxCoeff());
            setY(arrivalStation.getY()*Map.getyCoeff());
            stop();
        }else{
            setX(getX() + 1);
            setY(getY() + segment.getSlope());
            //We move the wagons
            int initialPosition = 37;
            for(Wagon w : wagons){
                w.setX(getX());
                w.setY(getY()+initialPosition);
                initialPosition += 37;
            }
            movePassengers();
        }
    }

    /**
     * Moving the metro on a segment going down
     */
    public synchronized void goDown(){
        if(getY()-arrivalStation.getY()*Map.getyCoeff()<=2&&
                getY()-arrivalStation.getY()*Map.getyCoeff()>=-20){
            setX(arrivalStation.getX()*Map.getxCoeff());
            setY(arrivalStation.getY()*Map.getyCoeff());
            stop();
        }else{
            setY(getY()-1);
            setX(getX()-segment.getSlope());
            //We move the wagons
            int initialPosition = 37;
            for(Wagon w : wagons){
                w.setX(getX());
                w.setY(getY()-initialPosition);
                initialPosition += 37;
            }
            movePassengers();
        }
    }

    public void setTripVariables(StationModel departureStation, StationModel arrivalStation,
                                 SegmentModel segment, int direction){
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.segment = segment;
        this.direction = direction;
    }

    public void loadPassengers(){
        ArrayList<PassengerModel> passengersLoaded = new ArrayList<>(); //All the passengers who boarded in the metro
        for(PassengerModel p : arrivalStation.getPassengers()){
            if(this.isFull()==false){
                this.addPassenger(p);
                passengersLoaded.add(p);
            }else{
                for(Wagon w : wagons){
                    if(w.isFull()==false){
                        w.addPassenger(p);
                        passengersLoaded.add(p);
                        break;
                    }
                }
            }
        }
        //Then we remove the passengers who loaded from the station in which they were waiting
        for(PassengerModel p : passengersLoaded){
            arrivalStation.removePassenger(p);
            //And the we add 5 points to the score for every passenger that boarded into the Metro
            setScore(5);
        }
    }

    public void unloadPassengers(){
        //We unload the passengers who can leave the locomotive and remove them from the list of passengers to draw in Map.java
        removePassengers(getPassengers());
        //We unload the passengers who can leave the wagons and remove them from the list of passengers to draw in Map.java
        for(Wagon wagon : wagons){
            removePassengers(wagon.getPassengers());
        }
    }

    public void removePassengers(ArrayList<PassengerModel> passengers){
        Iterator<PassengerModel> iter = passengers.iterator();
        while (iter.hasNext()) {
            PassengerModel p = iter.next();
            if (p.getShape() == arrivalStation.getShape()) {
                Map.removePassenger(p);
                iter.remove();
                setScore(8);
            }
        }
    }

    /**
     * Method to move all the passengers inside the metro when the metro moves
     */
    public void movePassengers(){
        int x = 0;
        int y = 0;
        ArrayList<PassengerModel> passengers = new ArrayList<>();
        passengers = getPassengers();
        for(int i = 0; i <= passengers.size()-1; i++){
            if(i != 3){
                passengers.get(i).setX((int) (getX()/Map.getxCoeff()+x));
                passengers.get(i).setY((int) (getY()/Map.getyCoeff()+y));
                x += 12;
            }else if(i==3){
                x=0;
                y=13;
                passengers.get(i).setX((int) (getX()/Map.getxCoeff()+x));
                passengers.get(i).setY((int) (getY()/Map.getyCoeff()+y));
                x=12;
            }
        }
        for(Wagon wagon : wagons){
            passengers = wagon.getPassengers();
            for(int i = 0; i <= passengers.size()-1; i++){
                if(i != 3){
                    passengers.get(i).setX((int) (getX()/Map.getxCoeff()+x));
                    passengers.get(i).setY((int) (getY()/Map.getyCoeff()+y));
                    x += 12;
                }else if(i==3){
                    x=0;
                    y=13;
                    passengers.get(i).setX((int) (getX()/Map.getxCoeff()+x));
                    passengers.get(i).setY((int) (getY()/Map.getyCoeff()+y));
                    x += 12;
                }
            }
        }
    }
}
