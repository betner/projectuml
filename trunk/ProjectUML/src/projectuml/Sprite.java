package projectuml;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Sprite
 * 
 * Sprite is a class that contains methods for creating objects
 * that are shown on game screen.
 * The sprite has methods for painting itself onscreen, update its
 * state and position.
 * Both the path imageFile and the image should be set by subclass.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class Sprite implements Serializable {

    private static final long serialVersionUID = 1l;
    private Point position;      // Objects upper left corner
    private Boolean visible;     // TRUE = object performs draw()
    private Boolean active;      // TRUE = object performs update()
    private int width;           // Should be set if inShape is used
    private int height;
    transient private BufferedImage image; // Graphic representing this object
    private String imageFile;    // Path to image
    private TouchBehaviour touch;

    /** 
     * Creates a new instance of Sprite and load
     * the image representing it on screen.
     * Subclasses to Sprite should set width and height 
     * to match the loaded image.
     */
    public Sprite() {
        position = new Point(0, 0); // Default placement for sprites
        hide();
        deactivate();
    }

    /**
     * Update sprite. E.g set new position.
     * 
     * @param level
     */
    public void update(Level level) {
    }

    /**
     * Load image asset and create a BufferedImage.
     * Make the image compatible to the graphics device
     * for hardware acceleration. This requires us to copy
     * the input image.
     * 
     * @param absolute file path -> path/name.xyz
     */
    public BufferedImage loadImage(String file) {
        // Get device's graphics configuration
        GraphicsEnvironment ge;
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc;
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

        try {
            BufferedImage bi, biCopy;
            // If image is loaded from a jar-archive we need to treat the
            // file path as a URL
            bi = ImageIO.read(new File(file));

            // Get the image transparency information
            int transparency = bi.getColorModel().getTransparency();

            // Here is were the optimization takes place by making the 
            // image compatible with the device's graphics 
            biCopy = gc.createCompatibleImage(bi.getWidth(), bi.getHeight(), transparency);

            // Create a graphics context to draw on
            Graphics2D g2d = biCopy.createGraphics();

            // Copy image 
            g2d.drawImage(bi, 0, 0, null);
            g2d.dispose();

            // Remember filename
            imageFile = file;

            // Return the copied and optimized image
            return biCopy;

        } catch (IOException e) {
            System.out.println("Image: " + file + " not found!\n" + e);
            return null;
        }

    }

    /**
     * Draw the sprite to the screen.
     * if the visibility flag is set to true.
     * Position variable belongs to the objects that
     * calls draw().
     * 
     * @param g2D Graphics2D
     */
    public void draw(Graphics2D g2D) {
        if (visible) {
            g2D.drawImage(image, getIntPositionX(), getIntPositionY(), null);
        }
    }

    /**
     * Functions to perform when object is touched.
     * Touch gets a reference to touching Sprite so
     * that we can alter its state if needed.
     * 
     * @param sprite
     */
    public void touch(Sprite sprite) {
        if (isActive() && isVisible()) {
            if (touch != null) {
                touch.action(sprite);
            }
        }
    }

    /**
     * Check if a point is within this objects boundries.
     * Default shape for a sprite is a rectangle.
     *
     * @param point
     * @return true or false
     */
    public boolean inShape(Point p) {
        if (p.getX() < position.getX() || p.getX() > position.getX() + width) {
            return false;
        } else if (p.getY() < position.getY() || p.getY() > position.getY() + height) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Make object visible. It will perform draw()
     */
    public void show() {
        visible = true;
    }

    /**
     * Make object invisible. It will not perform draw()
     */
    public void hide() {
        visible = false;
    }

    /**
     * Is the sprite visible on screen?
     **/
    public boolean isVisible() {
        return visible;
    }

    /**
     * Make the object perform update().
     */
    public void activate() {
        active = true;
    }

    /**
     * Make the object not perform update().
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Check if sprite is active
     * 
     * @return Boolean
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Set behaviour to be used when touch() is called.
     * 
     * @param touch
     */
    protected void setTouchBehaviour(TouchBehaviour touch) {
        this.touch = touch;
    }

    /**
     * Get a reference to the touch behaviour.
     * 
     * @return touch
     */
    protected TouchBehaviour getTouchBehaviour() {
        return touch;
    }

    /**
     * Get sprite's position.
     * 
     * @return position
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Get sprite's position in x-axis.
     * 
     * @return x integer
     */
    public int getIntPositionX() {
        return (int) position.getX();
    }

    /**
     * Get sprite's position in y-axis.
     * 
     * @return y integer
     */
    public int getIntPositionY() {
        return (int) position.getY();
    }

    /**
     * Get sprite's position in x-axis.
     * 
     * @return x double
     */
    public double getPositionX() {
        return position.getX();
    }

    /**
     * Get sprite's position in y-axis.
     * 
     * @return y double
     */
    public double getPositionY() {
        return position.getY();
    }

    /**
     * Set new position for sprite.
     * 
     * @param newPosition
     */
    public void setPosition(Point newPosition) {
        position.setLocation(newPosition);
    }

    /**
     * Set new position for sprite.
     * 
     * @param newPosition
     */
    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    /**
     * Set new position for sprite.
     * 
     * @param newPosition
     */
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    /**
     * Update current position with  delta values.
     * 
     * @param dx
     * @param dy
     */
    public void updatePosition(int dx, int dy) {
        position.translate(dx, dy);
    }

    /**
     * Get sprite's width.
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set sprite's width.
     * 
     * @param newWidth
     */
    public void setWidth(int newWidth) {
        width = newWidth;
    }

    /**
     * Get sprite's width.
     * 
     * @return width
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set sprite's height.
     * 
     * @param newHeight
     */
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    /**
     * Get sprite's image.
     * 
     * @return image BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Loads an image from file.
     * 
     * @param filename
     */
    public void loadImageFrom(String filename) {
        setImage(loadImage(filename));
    }

    /**
     * Alters the image directly.
     * 
     * @param image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }
    }

    /**
     * Get the path to the image.
     * 
     * @return imageFile 
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     * Provides custom serialization by restoring the image
     * @param in Stream to read from
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (imageFile != null) {
            loadImageFrom(imageFile);
        }
    }
}
