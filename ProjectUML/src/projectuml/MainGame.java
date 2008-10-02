package projectuml;

import java.awt.event.*;
import java.awt.Graphics2D;
import java.util.*;

/**
 * Main startup for the most awesome game in
 * the known universe
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class MainGame implements KeyListener, MouseListener, MouseMotionListener, DrawListener, WindowListener {

    private GameWindow gamewindow;
    private GameStates gamestates;
    private Timer timer;
    private Player player;
    private final long LOGIC_INTERVAL = 34;

    /**
     * Startup method
     *
     * @param args
     **/
    public static void main(String[] args) {
        new MainGame();
    }

    /**
     * Initiates the game
     */
    public MainGame() {
        gamewindow = new GameWindow("Project U.M.L.");
        gamestates = new GameStates();
        timer = new Timer();

        // Add receivers
        gamewindow.addKeyListener(this);
        gamewindow.addMouseListener(this);
        //gamewindow.addMouseMotionListener(this);
        gamewindow.addDrawListener(this);

        // Push the first game state
        gamestates.change(new MainMenu());

        // Add logic - updates at a fixed interval
        timer.scheduleAtFixedRate(new LogicTask(), 0, LOGIC_INTERVAL);

        // Start the game
        gamewindow.run();
    }

    /**  Various events **/
    public void keyPressed(KeyEvent e) {
        gamestates.keyEvent(e, true);
    }

    public void keyReleased(KeyEvent e) {
        gamestates.keyEvent(e, false);
    }

    public void keyTyped(KeyEvent e) {
        //gamestates.keyEvent(e);
    }

    public void mouseClicked(MouseEvent e) {
        gamestates.mouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        //gamestates.mouseEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        //gamestates.mouseEvent(e);
    }

    /** Not used **/
    public void mouseExited(MouseEvent e) {
    }

    /** Not used **/
    public void mouseEntered(MouseEvent e) {
    }

    /** Not used **/
    public void mouseMoved(MouseEvent e) {
        //gamestates.mouseEvent(e);
    }

    /** Not used **/
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Redraws the states
     **/
    public synchronized void draw(Graphics2D g) {
        gamestates.draw(g);
    }

    public void windowOpened(WindowEvent e) {
    }

    /**
     * Notify all states that we're leaving
     * @param e
     **/
    public void windowClosing(WindowEvent e) {
        // This will cause the gamestate manager to
        // call lostFocus on all active gamestates
        gamestates.change(null);
    }

    /** Not used **/
    public void windowClosed(WindowEvent e) {
    }

    /** Not used **/
    public void windowIconified(WindowEvent e) {
    }

    /** Not used **/
    public void windowDeiconified(WindowEvent e) {
    }

    /** Not used **/
    public void windowActivated(WindowEvent e) {
    }

    /** Not used **/
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Updates the states at a fixed interval
     **/
    private class LogicTask extends TimerTask {
        public void run() {
            gamestates.update(player);
        }
    }
}
