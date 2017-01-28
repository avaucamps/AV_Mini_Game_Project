package Game.Segment;

import Game.Line;
import Game.Map;
import Game.Station.StationModel;

public class SegmentModel {

    private StationModel departureStation, arrivalStation;
    private Line line;
    private double slope;

    public SegmentModel(StationModel departureStation, StationModel arrivalStation, Line line){
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.line = line;
        setSlope();
    }

    public StationModel getDepartureStation(){
        return departureStation;
    }

    public StationModel getArrivalStation(){
        return arrivalStation;
    }

    public Line getLine(){
        return line;
    }

    public double getSlope(){
        return slope;
    }

    /**
     * Calculation of the slope = (yb-ya)/(xb-xa)
     */
    public void setSlope(){
        double y = arrivalStation.getY()*Map.getyCoeff() - departureStation.getY()*Map.getyCoeff();
        double x = arrivalStation.getX()*Map.getxCoeff() - departureStation.getX()*Map.getxCoeff();
        slope = y/x;
    }
}
