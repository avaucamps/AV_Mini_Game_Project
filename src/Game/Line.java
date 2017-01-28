package Game;

import Game.Segment.SegmentModel;
import Game.Vehicle.Locomotive;
import Game.Vehicle.Vehicle;

import java.util.ArrayList;

public class Line {

    private static int staticColor = 0;
    private ArrayList<SegmentModel> segments = new ArrayList<>();
    private ArrayList<Locomotive> locomotives = new ArrayList<>();
    private int color = 0; //0 = yellow, 1 = red, 2 = green, 3 = blue, 4=cyan, 5=magenta

    public Line(int staticColor){
        color = staticColor;
        this.staticColor++;
    }

    /**
     * Trip from first station to last station
     */
    public void outwardTrip(int segmentNumber, Locomotive v){
        int directionX = 0;
        int directionY = 0;
        SegmentModel s = segments.get(segmentNumber);
        directionX = s.getArrivalStation().getX() - s.getDepartureStation().getX();
        directionY = s.getArrivalStation().getY() - s.getDepartureStation().getY();
        if(directionX > 2){
            v.setTripVariables(s.getDepartureStation(), s.getArrivalStation(), s, 1);
        }else if(directionX < -2){
            v.setTripVariables(s.getDepartureStation(), s.getArrivalStation(), s, 2);
        }else if(directionX < 2 && directionX > -2 && directionY > 2){
            v.setTripVariables(s.getDepartureStation(), s.getArrivalStation(), s, 3);
        }else if(directionX < 2 && directionX > -2 && directionY < 2){
            v.setTripVariables(s.getDepartureStation(), s.getArrivalStation(), s, 4);
        }
    }

    /**
     * Trip from last station to first station
     */
    public void returnTrip(int segmentNumber, Locomotive v){
        int directionX = 0;
        int directionY = 0;
        SegmentModel s = segments.get(segmentNumber-1);
        directionX = s.getArrivalStation().getX() - s.getDepartureStation().getX();
        directionY = s.getArrivalStation().getY() - s.getDepartureStation().getY();
        if(directionX > 2){ //The metro must go left
            v.setTripVariables(s.getArrivalStation(), s.getDepartureStation(), s, 2);
        }else if(directionX < -2){ //The metro must go right
            v.setTripVariables(s.getArrivalStation(), s.getDepartureStation(), s, 1);
        }else if(directionX < 2 && directionX > -2 && directionY > 2){ //The metro must go down
            v.setTripVariables(s.getArrivalStation(), s.getDepartureStation(), s, 4);
        }else if(directionX < 2 && directionX > -2 && directionY < 2){ //The metro must go up
            v.setTripVariables(s.getArrivalStation(), s.getDepartureStation(), s, 3);

        }
    }

    /**
     * Method for bonus to know if there are more lines to propose
     */
    public static boolean isLine(){
        return getStaticColor()<=5;
    }

    public static int getStaticColor(){ return staticColor; }

    public int getColor(){
        return color;
    }

    public ArrayList<SegmentModel> getSegments(){
        return segments;
    }

    public void addSegment(SegmentModel s){
        segments.add(s);
    }

    public void removeSegment(SegmentModel s){
        segments.remove(s);
    }

    public ArrayList<Locomotive> getLocomotives(){
        return locomotives;
    }

    public void addLocomotive(Locomotive l){
        locomotives.add(l);
    }


    public String toString(){
        if(color == 0){
            return "Yellow";
        }else if(color == 1){
            return "Red";
        }else if(color == 2){
            return "Blue";
        }else if(color == 3){
            return "Green";
        }else if(color == 4){
            return "Cyan";
        }else{
            return "Magenta";
        }
    }
}
