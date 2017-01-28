package Game.Station;

import Game.Passenger.PassengerModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static Game.Game.gameOver;
import static Game.Game.warning;
import static oracle.jrockit.jfr.events.Bits.intValue;
import static oracle.jrockit.jfr.events.Bits.threadID;

public class StationModel {

    private int x;
    private int y;
    private int shape;
    private BufferedImage image;
    private int xPassenger, yPassenger; //coordinates of the first available place for a passenger to wait its metro
    private ArrayList<PassengerModel> passengers = new ArrayList<>();
    private int elapsedTime = 0;

    public StationModel(int x, int y){
        this.x=x;
        this.y=y;
        this.xPassenger = x - 20;
        this.yPassenger = y + 25;
        setImage();
    }

    /**
     * If there are more than 5 passengers waiting at a station, we start a timer. If that number doesn't go under
     * 6 during the following 30 seconds it's game over. If the number goes under 6 then the timer is stopped.
     * To check that every time a passenger is added to a station, we test if the station has more than 5 passengers.
     */
    public void isFull(){
        Timer t = new Timer();
        if(passengers.size()>=6 && elapsedTime == 0){
            warning();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    elapsedTime++;//amount of time passed
                    if (elapsedTime == 30) {
                        cancel();
                        gameOver();
                    }
                }
            }, 1000, 1000);
        }else if(passengers.size() < 6 && elapsedTime > 0){
            t.cancel();
        }
    }

    public BufferedImage getImage(){
        return image;
    }

    public void setImage(){
        Random r = new Random();
        shape = r.nextInt(3);
        try{
            switch(shape){
                case 0:
                    image = ImageIO.read(new File("TriangleStation.png"));
                    break;
                case 1:
                    image = ImageIO.read(new File("SquareStation.png"));
                    break;
                case 2:
                    image = ImageIO.read(new File("RoundStation.png"));
                    break;
                default:
                    image = ImageIO.read(new File("TriangleStation.png"));
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getShape(){
        return shape;
    }

    public int getxPassenger(){
        return xPassenger;
    }

    public void setxPassenger(int x){
        this.xPassenger += x;
    }

    public int getyPassenger(){
        return yPassenger;
    }

    public ArrayList<PassengerModel> getPassengers(){
        return passengers;
    }

    public void addPassenger(PassengerModel p){
        passengers.add(p);
        isFull();
    }

    public void removePassenger(PassengerModel p){
        passengers.remove(p);
        xPassenger -= 17;
    }
}
