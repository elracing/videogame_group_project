

package group_project;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartMenu2 extends Applet implements ActionListener {
    private Button startButton;
    private Button exitButton;
    private Image backgroundScreen;
    private MainApplet controller;

    public void setController(MainApplet controller) {
        this.controller = controller;
    }

    public void init() {
        setLayout(null);
        setSize(800, 600);
        try {
            backgroundScreen = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton = new Button("Start Game");
        startButton.setBounds(350, 200, 100, 30);
        startButton.addActionListener(this);
        add(startButton);

        exitButton = new Button("Exit");
        exitButton.setBounds(350, 250, 100, 30);
        exitButton.addActionListener(this);
        add(exitButton);
    }

    public void paint(Graphics pen) {
        if (backgroundScreen != null) {
            pen.drawImage(backgroundScreen, 0, 0, getWidth(), getHeight(), this);
        }
        pen.setFont(new Font("Arial", Font.BOLD, 36));
        pen.drawString("Platformer Game", 260, 100);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton && controller != null) {
            controller.showGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}