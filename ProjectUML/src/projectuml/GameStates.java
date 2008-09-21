package projectuml;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.Stack;

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
    public void push(GameState state) {
        states.push(state);
        state.setManager(this);
    }
    
    /**
     * Goes back to previous game state. Will not
     * remove the last state leaving the stack empty
     **/
    private void pop() {
        if (states.size() > 1) {
            states.pop();
        } else {
            System.err.println("---  Trying to pop the last state!  ---");
        }
    }
    
    /**
     * Clears the state list and switch to
     * the new one
     *
     * @param state New state to switch to
     **/
    public void change(GameState state) {
        states.clear();
        push(state);
    }
    
    /**
     * Updates the active state
     *
     * @param player
     **/
    public void update(Player player) {
        //System.out.println("GameStates.update");
        if (!states.empty()) {
            states.peek().update(player);
        }
    }
    
    /**
     * Draws every state to the screen
     *
     * @param g
     **/
    public void draw(Graphics2D g) {
        //System.out.println("GameStates.draw");
        for (GameState state : states) {
            state.draw(g);
        }
    }
    
    /**
     * Sends a key event to the active state
     **/
    public void keyEvent(KeyEvent event) {
        if (!states.empty()) {
            states.peek().keyEvent(event);
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
