package group_project;

import java.applet.Applet;
import java.awt.*;

public class MainApplet extends Applet {
    private CardLayout layout = new CardLayout() ;
    private StartMenu2 menu;
    private Game game;

    public void init() {
        //layout = new CardLayout();
        setLayout(layout);

        menu = new StartMenu2();
        
        game = new Game();
        
        menu.setController(this);

        add("Menu", menu);
        add("Game", game);

        menu.init();
        game.init();

        layout.show(this, "Menu");
    }

    public void showGame() {
        setSize(1500, 870);
        game.initialize();
        game.gameStarted = true;
    	layout.show(this,  "Game");
        game.requestFocus();
    }
}
