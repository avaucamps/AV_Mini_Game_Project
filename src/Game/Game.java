package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Game {

    private static Frame frame;
    private JPanel panelTop;
    private Map panelGame;
    private JButton addSegment;
    private JButton deleteSegment;
    private JLabel labelLines = new JLabel("lines : ");
    private JButton[] linesButtons = new JButton[6];
    private int line; //number of the last line to be enabled
    private JLabel labelTime = new JLabel("Day : ");
    private JButton locomotiveButton;
    private JButton wagonButton;
    private static JLabel labelScore = new JLabel("Score : ");
    private Time t;
    private static int score;
    private static ArrayList<Thread> listThreads = new ArrayList<>(); //ArrayList in which we put the threads of all the locomotives so when
    //it's game over we stop all the threads

    public Game(String map){
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Panel 1 top
        panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout());
        addSegment = new JButton("New segment");
        addSegment.addActionListener((e)->panelGame.setAddSegment(true));
        deleteSegment = new JButton("Remove segment");
        deleteSegment.addActionListener((e)->panelGame.setRemoveSegment(true));
        panelTop.add(addSegment);
        panelTop.add(deleteSegment);
        panelTop.add(labelLines);
        lineButtons();
        locomotiveButton = new JButton("0");
        locomotiveButton.setIcon(new ImageIcon("locomotive.png"));
        locomotiveButton.addActionListener((e) -> {
            if(Integer.parseInt(locomotiveButton.getText())>0){
                panelGame.newLocomotive();
                int nbLocomotives = Integer.parseInt(locomotiveButton.getText());
                locomotiveButton.setText(String.valueOf(nbLocomotives-1));
            }
        });
        wagonButton = new JButton("0");
        wagonButton.setIcon(new ImageIcon("wagon.png"));
        wagonButton.addActionListener((e) -> {
            if(Integer.parseInt(wagonButton.getText())>0){
                panelGame.newWagon();
                int nbWagons = Integer.parseInt(wagonButton.getText());
                wagonButton.setText(String.valueOf(nbWagons-1));
            }
        });
        panelTop.add(locomotiveButton);
        panelTop.add(wagonButton);
        panelTop.add(labelTime);
        panelTop.add(labelScore);
        setLabelTime(0);
        setLabelScore();
        frame.add(panelTop, BorderLayout.NORTH);
        //Panel 2 game
        panelGame = new Map(map);
        //At the beginning 3 stations must appear on the map
        panelGame.newStation();
        panelGame.newStation();
        panelGame.newStation();
        frame.add(panelGame);
        frame.setVisible(true);
        score = 0;
        t = new Time(this);
    }

    public void lineButtons(){
        for(int i = 0; i <= 5; i++) {
            Line l = new Line(Line.getStaticColor());
            JButton lineButton = new JButton(l.toString());
            linesButtons[i] = lineButton;
            if (i > 1) {
                lineButton.setEnabled(false);
            }
            lineButton.addActionListener((e) -> {
                panelGame.setLine(l);
            });
            panelTop.add(lineButton);
        }
        line = 2;
    }

    public void newStation(){
        panelGame.newStation();
    }

    public void newPassenger(){
        panelGame.newPassenger();
    }

    public void enableLine(){
        linesButtons[line].setEnabled(true);
        line++;
    }

    public void newLocomotive(){
        //Update the button and then create a new instance in Map.java
        int nbLocomotives = Integer.parseInt(locomotiveButton.getText());
        locomotiveButton.setText(String.valueOf(nbLocomotives+1));
    }

    public void newWagon(){
        //Update the button and then create a new instance in Map.java
        int nbWagons = Integer.parseInt(wagonButton.getText());
        wagonButton.setText(String.valueOf(nbWagons+1));
    }

    public static void setScore(int points){
        score += points;
        setLabelScore();
    }

    public static void setLabelScore(){
        labelScore.setText("Score : " + String.valueOf(score));
    }

    public void setLabelTime(int day){
        switch (day) {
            case 0:
                labelTime.setText("Day : Monday   ");
                break;
            case 1:
                labelTime.setText("Day : Tuesday   ");
                break;
            case 2:
                labelTime.setText("Day : Wednesday    ");
                break;
            case 3:
                labelTime.setText("Day : Thursday   ");
                break;
            case 4:
                labelTime.setText("Day : Friday    ");
                break;
            case 5:
                labelTime.setText("Day : Saturday  ");
                break;
            case 6:
                labelTime.setText("Day : Sunday    ");
                break;
        }
    }

    public static void warning(){
        JOptionPane jop = new JOptionPane();
        jop.showMessageDialog(null, "Warning ! One of your station has more than 5 passengers waiting.", "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void gameOver(){
        JOptionPane jop = new JOptionPane();
        jop.showMessageDialog(null, "You lost with a score of " + score + ". Well done !", "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
        for(Thread t : listThreads){
            t.interrupt();
        }
        System.exit(0);
    }

    public static void addThread(Thread t){
        listThreads.add(t);
    }
}
