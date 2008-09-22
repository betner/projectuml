package projectuml;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The main rendering window
 *
 * @author Jens Thuresson, Steve Eriksson
 **/
public class GameWindow extends JFrame implements WindowFocusListener {

    private Boolean active = true;
    private Canvas canvas;
    private BufferedImage backbuffer;
    private BufferStrategy strategy;
    private ArrayList<DrawListener> drawlisteners;

    /**
     * Creates main window
     *
     * @param title Window caption
     **/
    public GameWindow(String title) {
        setUndecorated(false);
        setIgnoreRepaint(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowFocusListener(this);

        drawlisteners = new ArrayList<DrawListener>();

        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(640, 480);
        add(canvas);
        pack();

        initGraphics(canvas);

        setTitle(title);
        setResizable(false);
        center();
        setVisible(true);
    }

    /**
     * Centers the window on the screen
     */
    private void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screen.getWidth() / 2) - (this.getWidth() / 2);
        int y = (int) (screen.getHeight() / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
    }

    /**
     * Override this to ensure that both our window and
     * the canvas kept within registers the listener
     * @param l
     */
    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        super.addMouseMotionListener(l);
        canvas.addMouseMotionListener(l);
    }

    /**
     * Override this to ensure that both our window and
     * the canvas kept within registers the listener
     * @param l
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        canvas.addKeyListener(l);
    }

    /**
     * The window gained focus, activate rendering loop
     * @param e
     */
    public void windowGainedFocus(WindowEvent e) {
        active = true;
    }

    /**
     * The window lost its focus, deactive rendering loop
     * (as in don't waste any cycles)
     * @param e
     */
    public void windowLostFocus(WindowEvent e) {
        active = false;
    }

    /**
     * Initiates graphics configurations
     *
     * @param 
     **/
    private void initGraphics(Canvas canvas) {
        canvas.createBufferStrategy(1);
        strategy = canvas.getBufferStrategy();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();

        // Switch to full screen
//        if (device.isFullScreenSupported()) {
//            System.out.println("Going for fullscreen...");
//            device.setFullScreenWindow(this);
//            device.setDisplayMode(new DisplayMode(640, 480, 32, 60));
//        }
        
        // Create a backbuffer that's compatible with our
        // current display
        backbuffer = config.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Adds a draw listener to receive a call from the
     * rendering loop
     *
     * @param listener
     **/
    public void addDrawListener(DrawListener listener) {
        drawlisteners.add(listener);
    }

    /**
     * Removes a draw listener from receiving renderings calls
     *
     * @param listener
     **/
    public void removeDrawListener(DrawListener listener) {
        drawlisteners.remove(listener);
    }

    /**
     * Starts the main rendering loop
     **/
    public void run() {
        int width = backbuffer.getWidth();
        int height = backbuffer.getHeight();

        if (drawlisteners.isEmpty()) {
            System.err.println("---  Warning: no drawlistener registered!  ---");
        }

        // Main loop
        Graphics2D graph2d = backbuffer.createGraphics();
        boolean run = true;
        while (run) {
            // Only render if the window is active
            if (active) {
                Graphics graphics = null;
                try {
                    graph2d.setColor(Color.black);
                    graph2d.fillRect(0, 0, width, height);

                    // Notify all registered draw listeners
                    for (DrawListener listener : drawlisteners) {
                        listener.draw(graph2d);
                    }

                    // Flip buffers
                    graphics = strategy.getDrawGraphics();
                    graphics.drawImage(backbuffer, 0, 0, null);

                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (graphics != null) {
                        graphics.dispose();
                    }
                }
                strategy.show();
            } else {
                // Not active, let the OS work some! Hurrah!
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                }
            }
        }
    }
}
