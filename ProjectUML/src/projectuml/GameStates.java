package projectuml;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;

/**
 * Manages the different states in the game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class GameStates {
    
    private Stack<GameState> states;
    
    /**
     * Initiates the game state stack
     **/
    public GameStates() {
        states = new Stack<GameState>();
    }
    
    /**
     * Adds a new active state
     *
     * @param state New state to display
     **/
    public synchronized void push(GameState state) {
        if (!states.isEmpty()) {
            // Not empty, notify active state about
            // lost focus
            states.peek().lostFocus();
        }
        states.push(state);
        state.setGameStateManager(this);
        state.gainedFocus();
    }
    
    /**
     * Goes back to previous game state. Will not
     * remove the last state leaving the stack empty
     **/
    private synchronized void pop() {
        if (states.size() > 1) {
            GameState old = states.pop();
            old.lostFocus();
            states.peek().gainedFocus();
        } else {
            System.err.println("---  Trying to pop the last state!  ---");
        }
    }
    
    /**
     * Clears the state list and switch to
     * the new one
     *
     * @param state New state to switch to, or null if we
     *              just want to signal "lostFocus" to all
     *              active gamestates
     **/
    public synchronized void change(GameState state) {
        for (GameState s : states) {
            s.lostFocus();
        }
        states.clear();
        if (state != null) {
            push(state);
            state.gainedFocus();
        }
    }
    
    /**
     * Updates the active state
     *
     * @param player
     **/
    public synchronized void update(Player player) {
        if (!states.empty()) {
            states.peek().update(player);
            if (states.peek().canRemove()) {
                pop();
            }
        }
    }
    
    /**
     * Draws every state to the screen
     *
     * @param g
     **/
    public synchronized void draw(Graphics2D g) {
        for (GameState state : states) {
            state.draw(g);
        }
    }
    
    /**
     * Sends a key event to the active state
     * @param event Key event generated
     * @param down True if the key is down
     **/
    public void keyEvent(KeyEvent event, boolean down) {
        if (!states.empty()) {
            states.peek().keyEvent(event, down);
        }
    }
    
    /**
     * Sends a mouse event to the active state
     **/
    public void mouseEvent(MouseEvent event) {
        if (!states.empty()) {
            states.peek().mouseEvent(event);
        }
    }
}
