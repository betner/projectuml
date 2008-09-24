package projectuml;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * A floating star field
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class StarField extends Scenery {
    
    /**
     * A single star in space
     **/
    private class Star extends Point {
        
        private int deltax;
        private int deltay;
        private int startx;
        private int starty;
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
            super(x, y);
            this.deltax = deltax;
            this.deltay = deltay;
            startx = x;
            starty = y;
            color = new Color((int)(255*brightness), (int)(255*brightness), (int)(255*brightness));
        }
        
        /**
         * Color of this star
         **/
        public Color getColor() {
            return color;
        }
        
        /**
         * Makes the star travel
         **/
        public void move() {
            this.setLocation(this.x - deltax, this.y - deltay);
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
    
    private ArrayList<Star> stars;
    private Random random;
    
    /**
     * Generates a star field
     *
     * @param Amount of stars
     **/
    public StarField(int amount) {
        random = new Random();
        stars = new ArrayList<Star>(amount);
        for (int i=0; i < amount; ++i) {
            int x = random.nextInt(640);
            int y = random.nextInt(480);
            int dx = random.nextInt(3) + 1;
            float brightness = random.nextFloat();
            stars.add(new Star(x, y, dx, 0, brightness));
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
            //g.fillRect(star.x, star.y, 1, 1);
            g.drawLine(star.x, star.y, star.x, star.y);
        }
    }
    
}
