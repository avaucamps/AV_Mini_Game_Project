package Game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by antoi on 27/01/2017.
 */
public class Time {

    private int hours = 0;
    private int days = 0;
    private int weeks = 0;
    private Timer t;

    public Time(Game g) {
        t = new Timer();
        Random r = new Random();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                g.setScore(12);
                g.setLabelTime(days);
                g.newPassenger(); //2 new passengers every 10 seconds
                g.newPassenger();
                if(hours < 12){
                    hours += 12;
                }else{
                    hours = 0;
                    g.newStation(); //New station every 20 seconds
                    if(days < 7){
                        days++;
                    }else{
                        days = 0;
                        weeks++; //New Bonus every week in the game
                        t.cancel();
                        Bonus b = new Bonus(g);
                    }
                }
            }
        }, 10000, 10000);
    }

    public Timer getTimer(){
        return t;
    }
}
