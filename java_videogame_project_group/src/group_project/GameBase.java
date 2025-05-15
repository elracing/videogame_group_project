package group_project;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;

public abstract class GameBase extends Applet implements Runnable, KeyListener {
    Image offScreen;
    Graphics offScreen_pen;
    Thread t;
    static boolean[] pressing = new boolean[1024];
    public static final int UP = 38;
    public static final int DN = 40;
    public static final int LT = 37;
    public static final int RT = 39;
    public static final int _A = 65;
    public static final int _B = 66;
    public static final int _C = 67;
    public static final int _D = 68;
    public static final int _E = 69;
    public static final int _F = 70;
    public static final int _G = 71;
    public static final int _H = 72;
    public static final int _I = 73;
    public static final int _J = 74;
    public static final int _K = 75;
    public static final int _L = 76;
    public static final int _M = 77;
    public static final int _N = 78;
    public static final int _O = 79;
    public static final int _P = 80;
    public static final int _Q = 81;
    public static final int _R = 82;
    public static final int _S = 83;
    public static final int _T = 84;
    public static final int _U = 85;
    public static final int _V = 86;
    public static final int _W = 87;
    public static final int _X = 88;
    public static final int _Y = 89;
    public static final int _Z = 90;
    public static final int _1 = 49;
    public static final int _2 = 50;
    public static final int _3 = 51;
    public static final int _4 = 52;
    public static final int _5 = 53;
    public static final int _6 = 54;
    public static final int _7 = 55;
    public static final int _8 = 56;
    public static final int _9 = 57;
    public static final int CTRL = 17;
    public static final int SHFT = 16;
    public static final int ALT = 18;
    public static final int SPACE = 32;
    public static final int COMMA = 44;
    public static final int PERIOD = 46;
    public static final int SLASH = 47;
    public static final int SEMICOLON = 59;
    public static final int COLON = 513;
    public static final int QUOTE = 222;
    public static final int F1 = 112;
    public static final int F2 = 113;
    public static final int F3 = 114;
    public static final int F4 = 115;
    public static final int F5 = 116;
    public static final int F6 = 117;
    public static final int F7 = 118;
    public static final int F8 = 119;
    public static final int F9 = 120;
    public static final int F10 = 121;
    public static final int F11 = 122;
    public static final int F12 = 123;

    public GameBase() {
    }

    public abstract void initialize();

    public void init() {
        this.offScreen = this.createImage(1920, 1080);
        this.offScreen_pen = this.offScreen.getGraphics();
        this.initialize();
        this.addKeyListener(this);
        this.requestFocus();
        this.t = new Thread(this);
        this.t.start();
    }

    public abstract void inGameLoop();

    public void run() {
        while(true) {
            this.inGameLoop();
            this.repaint();

            try {
                Thread.sleep(16L);
            } catch (Exception var2) {
            }
        }
    }

    public void update(Graphics pen) {
        this.offScreen_pen.clearRect(0, 0, 1920, 1080);
        this.paint(this.offScreen_pen);
        pen.drawImage(this.offScreen, 0, 0, (ImageObserver)null);
    }

    public void keyPressed(KeyEvent e) {
        pressing[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        pressing[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {
    }
    
    public Image getImage(String filename) {
    	return Toolkit.getDefaultToolkit().getImage(filename);
    }
}

