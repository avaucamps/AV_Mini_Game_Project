package Game;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The class Bonus asks the user to choose one of two bonus chosen randomly between the 3 bonuses that are
 * a new line, a new locomotive or a new wagon
 */

public class Bonus extends JFrame {

    private Game game;
    private JPanel panel;
    private int bonus1, bonus2; //0 = line, 1 = locomotive, 2 = wagon
    private JButton bonus1Button, bonus2Button;

    public Bonus(Game game){
        this.game = game;
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel=new JPanel();
        Random r = new Random();
        bonus1Button = new JButton();
        bonus2Button = new JButton();
        if(Line.isLine()){
            setButton(1, bonus1Button);
            setButton(2, bonus2Button);
        }else{
            bonus1 = r.nextInt(3);
            do{
                bonus2 = r.nextInt(3);
            }while (bonus2==bonus1);
            setButton(bonus1, bonus1Button);
            setButton(bonus2, bonus2Button);
        }
        panel.add(bonus1Button);
        panel.add(bonus2Button);
        add(panel);
        setVisible(true);
    }

    public void setButton(int bonus, JButton button){
        switch(bonus){
            case 0:
                button.setText("Line");
                button.addActionListener((e -> {
                    game.enableLine();
                    dispose();
                }));
                break;
            case 1:
                button.setText("Locomotive");
                button.addActionListener((e) -> {
                    game.newLocomotive();
                    dispose();
                });
                break;
            case 2:
                button.setText("Wagon");
                button.addActionListener((e) -> {
                    game.newWagon();
                    dispose();
                });
                break;
            default:
                button.setText("Locomotive");
                button.addActionListener((e) -> {
                    game.newLocomotive();
                    dispose();
                });
                break;
        }
    }
}
