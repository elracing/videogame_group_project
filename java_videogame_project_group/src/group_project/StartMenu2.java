package group_project;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


//insert missing package & backgroundscreen was added to github img folder

public class StartMenu2 extends Applet implements ActionListener {
	
	private String title; 
    private Font titleFont; 
    private Button startButton;
    private Button exitButton;
    private Image backgroundScreen;
    
    private enum GameState { MENU, PLAYING }
    private GameState currentState;
   
    
    public void init() {
    	
    	setLayout(null); 
        setSize(800, 600);
        currentState = GameState.MENU;
    	
        try {
        	backgroundScreen = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // creating title frame
        Frame title = (Frame)this.getParent().getParent();
        title.setTitle("Menu");
        
 
        // Initialize/ position/ listeners for start button
        startButton = new Button("Start Game");
        startButton.setBounds(350, 200, 100, 30);
        startButton.addActionListener(this);
        add(startButton);
        
        
     // Initialize/ position/ listeners for exit button
        exitButton = new Button("Exit");
        exitButton.setBounds(350, 250, 100, 30);
        exitButton.addActionListener(this);
        add(exitButton);  
     
    }

    
    public void paint(Graphics pen) {
        super.paint(pen);
        if (currentState == GameState.MENU){
        	pen.drawImage(backgroundScreen, 0, 0, getWidth(), getHeight(), this);
        	menuTitle(pen);
        } else if (currentState == GameState.PLAYING){
        	pen.drawString("Game is running...", 100, 150);//not needed just testing
        	startGame(pen);
        }
    }
    
    //menu title font/dem/info
    private void menuTitle(Graphics pen) {
        title = new String( "Game Title unknown");
    	titleFont = new Font("Arial", Font.BOLD, 45);
        pen.setFont(titleFont);
        FontMetrics fontMet = pen.getFontMetrics(titleFont);
        int titleWidth = fontMet.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2;
        pen.drawString(title, titleX, 80);
    }

    //replace for real game start function used for testing
    private void startGame(Graphics g){
        g.drawString("functional", 150, 50);
    }

    
    //once startbutton is selected currentstate gets updated to playing
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
        	currentState = GameState.PLAYING;
            repaint();
             System.out.println("Start Game!");
            // Start game logic here
            
            
            // exit the window 
        } else if (e.getSource() == exitButton) {
        	System.out.println("Exit game");
        	System.exit(0); 
        }
    }
    
} 