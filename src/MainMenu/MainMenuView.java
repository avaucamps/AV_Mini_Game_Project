package MainMenu;

import javax.swing.*;
import java.awt.*;
import Game.Game;

public class MainMenuView {
    private JPanel panel;
    private JButton newGameButton;
    private JLabel labelMap;
    private JRadioButton londonRadioButton;
    private JRadioButton newYorkRadioButton;
    private JRadioButton parisRadioButton;
    private JButton playButton;
    private ButtonGroup groupMaps;
    private String map;

    public MainMenuView(){
        JFrame frame = new JFrame("Mini Métro");
        frame.setContentPane(panel);
        //Create a group for the radio buttons
        groupMaps = new ButtonGroup();
        groupMaps.add(londonRadioButton);
        groupMaps.add(newYorkRadioButton);
        groupMaps.add(parisRadioButton);
        londonRadioButton.setSelected(true);
        //Map choice component not visible at the beginning
        setComponentsVisibility(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        //Put the window in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - (frame.getWidth() / 2),
                middle.y - (frame.getHeight() / 2));
        frame.setLocation(newLocation);
        NewGameListener();
        PlayListener();
        frame.setVisible(true);
    }

    public void setComponentsVisibility(boolean visibility) {
        labelMap.setVisible(visibility);
        parisRadioButton.setVisible(visibility);
        londonRadioButton.setVisible(visibility);
        newYorkRadioButton.setVisible(visibility);
        playButton.setVisible(visibility);
    }

    public void NewGameListener() {
        newGameButton.addActionListener((e) -> {
            newGameButton.setVisible(false);
            setComponentsVisibility(true);
        });
    }

    public void PlayListener() {
        playButton.addActionListener((e) -> {
            if (parisRadioButton.isSelected()) {
                map = "Paris";
            } else if (londonRadioButton.isSelected()) {
                map = "London";
            } else if (newYorkRadioButton.isSelected()) {
                map = "New_York";
            }
            Game g = new Game(map);
        });
    }
}
