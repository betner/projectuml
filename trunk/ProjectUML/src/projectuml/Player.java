package projectuml;

/**
 * Player
 * 
 * Represents the player.
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Player {

    private int score;
    private int lives;

    /**
     * Create new instance of Player.
     */
    public Player() {
        lives = 3;
        score = 0;
    }

    /**
     * Add points to the players score
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Remove one life from player.
     */
    public void removeLife() {
        lives -= 1;
    }

    /**
     * Get number of lives.
     * 
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Get player's score.
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }
}
