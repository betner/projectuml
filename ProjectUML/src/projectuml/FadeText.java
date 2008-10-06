package projectuml;

import java.awt.*;

/**
 * FadeText
 * Fades in/out a text
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class FadeText {

    private String text;
    private Color finalcolor;
    private int alpha;
    private boolean fadein;
    private Point position;
    private Font font;
    private int speed;

    /**
     * Constructs the fading text with a standard font.
     * 
     * @param text to display
     * @param finalcolor color of the fully visible text
     */
    public FadeText(String text, Color finalcolor) {
        this.text = text;
        this.finalcolor = finalcolor;
        alpha = 0;
        fadein = false;
        position = new Point(0, 0);
        font = new Font("Arial", Font.PLAIN, 10);
        speed = 1;
    }

    /**
     * Changes the in/out fading speed (default value is 1).
     * 
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Sets the position of our text.
     * 
     * @param x position
     * @param y position
     */
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    /**
     * Retrieves the position.
     * 
     * @return position Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Starts fading in.
     */
    public void fadeIn() {
        fadein = true;
    }

    public void fadeIn(int speed) {
        setSpeed(speed);
        fadein = true;
    }

    /**
     * Starts fading out.
     */
    public void fadeOut() {
        fadein = false;
    }

    public void fadeOut(int speed) {
        setSpeed(speed);
        fadein = false;
    }

    /**
     * Changes the font.
     * 
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Are we finished fading?
     * 
     * @return True if the text is finished fading, whether in or out
     */
    public Boolean finished() {
        if (fadein && alpha == 255) {
            return true;
        } else if (!fadein && alpha == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the text, e.g. the fade in/out-logic.
     */
    public void update() {
        if (fadein) {
            alpha += speed;
            if (alpha > 255) {
                alpha = 255;
            }
        } else {
            alpha -= speed;
            if (alpha < 0) {
                alpha = 0;
            }
        }
    }

    /**
     * Display the text on the screen.
     * 
     * @param g2D Graphics2D to draw on
     */
    public void draw(Graphics2D g2D) {
        // Only draw if it's visible
        if (alpha > 0) {
            g2D.setFont(font);
            g2D.setColor(new Color(finalcolor.getRed(), finalcolor.getGreen(), finalcolor.getBlue(), alpha));
            g2D.drawString(text, (int) position.getX(), (int) position.getY());
        }
    }
}
