package projectuml;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

/**
 * The main rendering window
 *
 * @author Jens Thuresson, Steve Eriksson
 **/
public class GameWindow extends JFrame implements WindowFocusListener {
    
    private Boolean active = true;
    private Canvas canvas;
    //private BufferedImage backbuffer;
    private VolatileImage backbuffer;
    private GraphicsConfiguration config;
    private BufferStrategy strategy;
    private ArrayList<DrawListener> drawlisteners;
    private java.util.Timer timer;
    private final long RENDER_INTERVAL = 16;
    
    /**
     * Timer task that renders the scene
     **/
    private class RenderTask extends TimerTask {
        public void run() {
            if (active) {
                long frametime = System.currentTimeMillis();
                Graphics graphics = null;
                Graphics2D graph2d = null;
                try {
                    if (backbuffer.validate(config) == VolatileImage.IMAGE_INCOMPATIBLE) {
                        // Backbuffer lost, recreate
                        System.out.println("Backbuffer lost");
                        backbuffer = config.createCompatibleVolatileImage(backbuffer.getWidth(), backbuffer.getHeight());
                    }
                    graph2d = backbuffer.createGraphics();
                    graph2d.setColor(Color.black);
                    graph2d.fillRect(0, 0, backbuffer.getWidth(), backbuffer.getHeight());
                    
                    // Notify all registered draw listeners
                    for (DrawListener listener : drawlisteners) {
                        listener.draw(graph2d);
                    }
                    
                    // Display new contents
                    graphics = strategy.getDrawGraphics();
                    graphics.drawImage(backbuffer, 0, 0, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (graphics != null) {
                        graphics.dispose();
                    }
                    if (graph2d != null) {
                        graph2d.dispose();
                    }
                }
                
                if (!strategy.contentsLost()) {
                    strategy.show();
                }
                
                Toolkit.getDefaultToolkit().sync();
                
                //frametime = System.currentTimeMillis() - frametime;
                //System.out.println("Frametime: " + frametime);
            }
        }
    }
    
    /**
     * Creates main window
     *
     * @param title Window caption
     **/
    public GameWindow(String title) {
        super(title);
        setUndecorated(false);
        setIgnoreRepaint(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowFocusListener(this);
        
        // Create space for our draw listeners
        drawlisteners = new ArrayList<DrawListener>();
        
        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(640, 480);
        add(canvas);
        pack();
        
        initGraphics(canvas);
        
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
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
        canvas.addMouseListener(l);
    }
    
    /**
     * Override this to ensure that both our window and
     * the canvas kept within registers the listener
     * @param l
     */
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        super.addMouseMotionListener(l);
        canvas.addMouseMotionListener(l);
    }
    
    /**
     * Override this to ensure that both our window and
     * the canvas kept within registers the listener
     * @param l
     */
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
        //active = false;
    }

    /**
     * Ensure the best graphics configuration
     */
    private class BestConfig extends GraphicsConfigTemplate {

        public boolean isGraphicsConfigSupported(GraphicsConfiguration gc) {
            return gc.getImageCapabilities().isAccelerated() &&
                    gc.getImageCapabilities().isTrueVolatile();
        }

        @Override
        public GraphicsConfiguration getBestConfiguration(GraphicsConfiguration[] gc) {
            for (GraphicsConfiguration g : gc) {
                if (isGraphicsConfigSupported(g)) {
                    return g;
                }
            }
            return null;
        }
        
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
        //config = device.getDefaultConfiguration();
        config = device.getBestConfiguration(new BestConfig());
        
        dumpVideoInfo(config.getDevice().getDisplayMode());
                
        // Switch to full screen
//        if (device.isFullScreenSupported()) {
//            System.out.println("Going for fullscreen...");
//            device.setFullScreenWindow(this);
//            device.setDisplayMode(new DisplayMode(640, 480, 32, 60));
//        }
        
        // Create a backbuffer that's compatible with our
        // current display
        //backbuffer = config.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
        ImageCapabilities desired = new ImageCapabilities(true);
        try {
            backbuffer = config.createCompatibleVolatileImage(canvas.getWidth(), canvas.getHeight(), desired);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Works...?
        //backbuffer.setAccelerationPriority(1.0f);
        
        // Print info about our backbuffer
        ImageCapabilities caps = backbuffer.getCapabilities();
        if (caps.isAccelerated()) {
            System.out.println("Backbuffer is accelerated.");
        }
        if (caps.isTrueVolatile()) {
            System.out.println("Backbuffer is a true volatile.");
        }
    }
    
    private void dumpVideoInfo(DisplayMode mode) {
        System.out.println("---  Video info  ---");
        System.out.print("Mode: ");
        System.out.print(mode.getWidth() + "x" + mode.getHeight() + " ");
        System.out.print(mode.getBitDepth() + " bpp @ ");
        System.out.println(mode.getRefreshRate() + " Hz");
        System.out.println("--------------------");
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
        if (drawlisteners.isEmpty()) {
            System.err.println("---  Warning: no drawlistener registered!  ---");
        }
        
        // Start main loop
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new RenderTask(), RENDER_INTERVAL, RENDER_INTERVAL);
    }
}
