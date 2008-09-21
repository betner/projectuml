package projectuml;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The main rendering window
 *
 * @author Jens Thuresson, Steve Eriksson
 **/
public class GameWindow extends JFrame {
    
    private BufferedImage backbuffer;
    private BufferStrategy strategy;
    private ArrayList<DrawListener> drawlisteners;
    
    /**
     * Creates main window
     *
     * @param title Window caption
     **/
    public GameWindow(String title) {
        this.setUndecorated(false);
        this.setIgnoreRepaint(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        drawlisteners = new ArrayList<DrawListener>();
        
        Canvas canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(640, 480);
        this.add(canvas);
        this.pack();
        
        initGraphics(canvas);
        
        this.setTitle(title);
        this.setResizable(false);
        this.setVisible(true);
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
        boolean run = true;
        while (run) {
            Graphics graphics = null;
            try {
                Graphics2D graph2d = backbuffer.createGraphics();
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
                graphics.dispose();
            }
            strategy.show();
        }
    }
}
