package projectuml;

/**
 * Player
 * 
 * Represents the player
 *
 * @author Jens Thuresson, Steve Eriksson
 */

public class Player {
    
    private int score;
    private int lives;

    public Player() {
        lives = 3;
        score = 0;
    }
    
    /**
     * Add points to the players score
     */
    public void addPoints(int points){
        score += points;
    }
    
    /**
     * Remove one life from player
     */
    public void removeLife(){
        lives -= 1;
    }
    
    /**
     * Setters and getters
     */
    
    public int getLives(){
        return lives;
    }
    
    public int getScore(){
        return score;
    }
}
