package Game.Segment;

import java.awt.*;
import java.awt.geom.Line2D;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class SegmentController {

    private SegmentModel model;
    private SegmentView view;

    public SegmentController(SegmentView view, SegmentModel model){
        this.view = view;
        this.model = model;
    }

    public int getDepartX(double xCoeff){
        return intValue(model.getDepartureStation().getX()*xCoeff);
    }

    public int getDepartY(double yCoeff){
        return intValue(model.getDepartureStation().getY()*yCoeff);
    }

    public int getArrivalX(double xCoeff){
        return intValue(model.getArrivalStation().getX()*xCoeff);
    }

    public int getArrivalY(double yCoeff){
        return intValue(model.getArrivalStation().getY()*yCoeff);
    }

    public Graphics2D setColorSegment(Graphics2D g2){
        switch(model.getLine().getColor()){
            case 0 :
                g2.setColor(Color.YELLOW);
                break;
            case 1 :
                g2.setColor(Color.RED);
                break;
            case 2 :
                g2.setColor(Color.GREEN);
                break;
            case 3 :
                g2.setColor(Color.BLUE);
                break;
            case 4 :
                g2.setColor(Color.CYAN);
                break;
            case 5 :
                g2.setColor(Color.MAGENTA);
                break;
        }
        return g2;
    }
}
