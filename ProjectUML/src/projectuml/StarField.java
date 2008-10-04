package projectuml;

import java.awt.*;
import java.io.Serializable;

/**
 * A floating star field
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class StarField extends Scenery {
    
    /**
     * A single star in space
     **/
    private class Star implements Serializable {
        
        private int x;
        private int y;
        private int deltax;
        private int deltay;
        private Color color;
        
        /**
         * Creates a star with a specified brightness
         *
         * @param x Start position in x
         * @param y Start position in y
         * @param deltax Traveling speed on x
         * @param deltay Traveling speed on y
         * @param brightness Brightness percentage (0.0 = black, 1.0 = bright white)
         **/
        public Star(int x, int y, int deltax, int deltay, float brightness) {
            this.x = x;
            this.y = y;
            this.deltax = deltax;
            this.deltay = deltay;
            color = new Color((int)(255*brightness), (int)(255*brightness), (int)(255*brightness));
        }
        
        /**
         * Color of this star
         **/
        public Color getColor() {
            return color;
        }
        
        /**
         * X-position
         * @return
         */
        public int getX() {
            return x;
        }
        
        /**
         * Y-position
         * @return
         */
        public int getY() {
            return y;
        }
        
        /**
         * Makes the star travel
         **/
        public void move() {
            //this.setLocation(this.x - deltax, this.y - deltay);
            //this.translate(-deltax, deltay);
            x += deltax;
            y += deltay;
            if (x < 0)
                x = 640;
            if (x > 640)
                x = 0;
            if (y < 0)
                y = 480;
            if (y > 480)
                y = 0;
        }
    }
    
    //private ArrayList<Star> stars;
    private Star[] stars;
    
    /**
     * Generates a star field
     *
     * @param Amount of stars
     **/
    public StarField(int amount) {
        //stars = new ArrayList<Star>(amount);
        stars = new Star[amount];
        for (int i=0; i < amount; ++i) {
            int x = Randomizer.getRandomNumber(0, 640);
            int y = Randomizer.getRandomNumber(0, 480);
            int dx = Randomizer.getRandomNumber(1, 3);
            float brightness = Randomizer.getRandomFloat();
            //stars.add(new Star(x, y, -dx, 0, brightness));
            stars[i] = new Star(x, y, -dx, 0, brightness);
        }
    }

    /**
     * Update the star field
     **/
    public void update() {
        for (Star star : stars) {
            star.move();
        }
    }

    /**
     * Paints the star field
     *
     * @param g
     **/
    public void draw(Graphics2D g) {
        for (Star star : stars) {
            g.setColor(star.getColor());
            g.drawLine(star.getX(), star.getY(), star.getX(), star.getY());
        }
    }
    
}
