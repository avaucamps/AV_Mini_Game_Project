package Game.Segment;

import java.awt.*;
import java.awt.geom.Line2D;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class SegmentView {

    private SegmentController controller;
    private SegmentModel model;

    public SegmentView(SegmentModel model){
        this.model = model;
        controller = new SegmentController(this, model);
    }

    public void drawSegment(Graphics2D g2, double xCoeff, double yCoeff){
        controller.setColorSegment(g2);
        g2.draw(new Line2D.Float(controller.getDepartX(xCoeff)+15, controller.getDepartY(yCoeff)+15,
                controller.getArrivalX(xCoeff)+15, controller.getArrivalY(yCoeff)+15));
    }

}
