package Game;

import Game.Passenger.PassengerController;
import Game.Passenger.PassengerModel;
import Game.Passenger.PassengerView;
import Game.Segment.SegmentModel;
import Game.Segment.SegmentView;
import Game.Station.StationModel;
import Game.Station.StationView;
import Game.Vehicle.Locomotive;
import Game.Vehicle.Vehicle;
import Game.Vehicle.VehicleView;
import Game.Vehicle.Wagon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static Game.Game.addThread;
import static Game.Game.setScore;
import static oracle.jrockit.jfr.events.Bits.intValue;


public class Map extends JPanel {

    private BufferedImage img;
    private ArrayList<StationModel> stations = new ArrayList<>();
    private static ArrayList<PassengerModel> passengers = new ArrayList<>();
    private ArrayList<SegmentModel> segments = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private double width, height;
    private static double xCoeff, yCoeff; //this panel doesn't take the whole screen, need coefficients to calculate the real coordinates
    private String map;
    private boolean stationASelected, stationBSelected = false;
    private Line line;
    private boolean addSegment = false;
    private boolean removeSegment = false;
    private StationModel departureStation, arrivalStation;

    public Map(String map){
        this.map = map;
        try {
            img = ImageIO.read(new File(map + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mouseListener();
    }

    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        width = this.getSize().getWidth();
        xCoeff = width/1280;
        height = this.getSize().getHeight();
        yCoeff = height/720;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        drawSegments(g2);
        drawVehicles(g);
        drawStations(g);
        drawPassengers(g);
        repaint();
    }

    public void mouseListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(addSegment == false && removeSegment == false){
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, "Please choose the option new segment or " +
                                    "remove segment.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (addSegment == true && removeSegment == false) {
                        for (StationModel s : stations) {
                            if (e.getX() - (s.getX() * xCoeff) <= 30 && e.getX() - (s.getX() * xCoeff) >= -30
                                    && e.getY() - (s.getY() * yCoeff) <= 30 && e.getY() - (s.getY() * yCoeff) >= -30) {
                                if (stationASelected == false && stationBSelected == false) {
                                    departureStation = s;
                                    stationASelected = true;
                                } else if (stationASelected == true && stationBSelected == false) {
                                    if (departureStation != s) {
                                        arrivalStation = s;
                                        newSegment(departureStation, arrivalStation);
                                    }
                                } else {
                                    stationASelected = false;
                                    stationBSelected = false;
                                }
                            }
                        }
                    } else if (addSegment == false && removeSegment == true) {
                        for (StationModel s : stations) {
                            if (e.getX() - (s.getX() * xCoeff) <= 30 && e.getX() - (s.getX() * xCoeff) >= -30
                                    && e.getY() - (s.getY() * yCoeff) <= 30 && e.getY() - (s.getY() * yCoeff) >= -30) {
                                if (stationASelected == false && stationBSelected == false) {
                                    departureStation = s;
                                    stationASelected = true;
                                } else if (stationASelected == true && stationBSelected == false) {
                                    if (departureStation != s) {
                                        arrivalStation = s;
                                        deleteSegment(departureStation, arrivalStation);
                                    }
                                } else {
                                    stationASelected = false;
                                    stationBSelected = false;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void drawStations(Graphics g){
        for(StationModel s : stations){
            StationView stationView = new StationView(s);
            stationView.drawStation(g, xCoeff, yCoeff);
        }
    }

    public void drawPassengers(Graphics g){
        Iterator<PassengerModel> iter = passengers.iterator();
        while (iter.hasNext()) {
            PassengerModel p = iter.next();
            PassengerView passengerView = new PassengerView(p);
            passengerView.drawPassenger(g, xCoeff, yCoeff);
        }
    }

    public void drawSegments(Graphics2D g2){
        for(SegmentModel s : segments){
            SegmentView segmentView = new SegmentView(s);
            segmentView.drawSegment(g2, xCoeff, yCoeff);
        }
    }

    /**
     * If there's at least one segment we can draw a locomotive
     */
    public void drawVehicles(Graphics g){
        if(segments.size() > 0){
            for(Vehicle v : vehicles){
                VehicleView vehicleView = new VehicleView(v);
                vehicleView.drawVehicle(g);
            }
        }
    }

    /**
     * Method to create a new Station with coordinates generated randomly
     */
    public void newStation(){
        int x, y;
        Random r = new Random();
        do{
            x = r.nextInt(img.getWidth()-30);
            y = r.nextInt(img.getHeight()-30);
        }while(this.isBlue(x, y)==false);
        StationModel station = new StationModel(x, y);
        stations.add(station);
    }

    /**
     * To know if the coordinates x and y in param are not on a river
     */
    public boolean isBlue(int x, int y) {
        if(map.equals("New_York")){
            Color c = new Color(img.getRGB(x, y), true);
            return c.getRed()==243 && c.getGreen()==230 && c.getBlue()==211;
        }else{
            Color c = new Color(img.getRGB(x, y), true);
            return c.getRed()==243 && c.getGreen()==231 && c.getBlue()==209;
        }
    }

    /**
     * Method to create a new passenger with coordinates of a station chosen randomly
     */
    public void newPassenger(){
        int station;
        Random r = new Random();
        //A passenger should appear in a station whose shape is different than him
        PassengerModel p = new PassengerModel(0, 0);
        do{
            station = r.nextInt(stations.size());
        }
        while(stations.get(station).getShape() == p.getShape());
        //We give the passengers its coordinates
        p.setX(stations.get(station).getxPassenger());
        p.setY(stations.get(station).getyPassenger());
        stations.get(station).setxPassenger(16); //We change the coordinates of the first available place
        //stations.get(stationD).estPleine();
        passengers.add(p);
        stations.get(station).addPassenger(p);
    }

    public static void removePassenger(PassengerModel p){
        passengers.remove(p);
    }

    /**
     * Method to create a new segment
     */
    public void newSegment(StationModel departureStation, StationModel arrivalStation){
        if(line == null){
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "You must choose a line before creating a segment.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            SegmentModel s = new SegmentModel(departureStation, arrivalStation, line);
            segments.add(s);
            line.addSegment(s);
            stationASelected = false;
            stationBSelected = false;
            //If this is the first segment of a line, a new locomotive appears
            if(line.getSegments().size()==1){
                newLocomotive();
            }
        }
    }

    /**
     * Method to delete a segment
     */
    public void deleteSegment(StationModel departureStation, StationModel arrivalStation){
        for(SegmentModel s : segments) {
            if (s.getDepartureStation() == departureStation && s.getArrivalStation() == arrivalStation
                    && s.getLine() == line) {
                line.removeSegment(s);
                removeLocomotive(line);
                segments.remove(s);
            }else if ((s.getDepartureStation() == arrivalStation && s.getArrivalStation() == departureStation
                    && s.getLine() == line)) {
                line.removeSegment(s);
                removeLocomotive(line);
                segments.remove(s);
            }
        }
        stationASelected = false;
        stationBSelected = false;
    }

    /**
     * If a line has 0 segment we must delete the locomotive for this line
     */
    public void removeLocomotive(Line l){
        if(l.getSegments().size() == 0){
            for(Locomotive loc : l.getLocomotives()){
                for(Wagon w : loc.getWagons()){
                    vehicles.remove(w);
                }
                vehicles.remove(l);
            }
        }
    }

    public void newLocomotive(){
        int x = intValue(line.getSegments().get(0).getDepartureStation().getX()*xCoeff); //coordinates of the first station on the line
        int y = intValue(line.getSegments().get(0).getDepartureStation().getY()*yCoeff);
        Locomotive l = new Locomotive(x, y, line);
        vehicles.add(l);
        line.addLocomotive(l);
        l.setTrip(true, false, 0); //The locomotive begins at the fist station with an outward trip
        Thread t = new Thread(l);
        addThread(t); //We pass the thread to Game.java so it's able to interrupt it when game over
        t.start();
    }

    /**
     * A wagon is added to the locomotive which has the least wagons on the line selected
     */
    public void newWagon(){
        Locomotive l = line.getLocomotives().get(0);
        for(Locomotive l2 : line.getLocomotives()){
            if(l.getWagons().size() > l2.getWagons().size()){
                l = l2;
            }
        }
        Wagon w = new Wagon(l.getX()+53, l.getY(), line);
        l.addWagon(w);
    }

    public void setLine(Line line){
        this.line = line;
    }

    public void setAddSegment(boolean value){
        this.addSegment = value;
        this.removeSegment = !value;
    }

    public void setRemoveSegment(boolean value){
        this.removeSegment = value;
        this.addSegment = !value;
    }

    public static double getxCoeff(){
        return xCoeff;
    }

    public static double getyCoeff(){
        return yCoeff;
    }
}
