package Game.Passenger;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class PassengerModel {

    private BufferedImage image;
    private int x, y;
    private int shape;

    public PassengerModel(int x, int y){
        this.x = x;
        this.y = y;
        setImage();
    }

    public void setImage(){
        Random r = new Random();
        shape = r.nextInt(3);
        try{
            switch(shape){
                case 0:
                    image = ImageIO.read(new File("TrianglePassenger.png"));
                    break;
                case 1:
                    image = ImageIO.read(new File("SquarePassenger.png"));
                    break;
                case 2:
                    image = ImageIO.read(new File("RoundPassenger.png"));
                    break;
                default:
                    image = ImageIO.read(new File("TrianglePassenger.png"));
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){ this.x = x; }

    public int getY(){
        return y;
    }

    public void setY(int y){ this.y = y; }

    public int getShape(){
        return shape;
    }
}
