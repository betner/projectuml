package projectuml;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    
    private BufferedImage backbuffer;
    private BufferStrategy strategy;
    
    public GameWindow(String title) {
        this.setIgnoreRepaint(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Canvas canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(640, 480);
        this.add(canvas);
        this.pack();
        
        initGraphics(canvas);
        
        this.setTitle(title);
        this.setResizable(false);
        this.setVisible(true);
        run();
    }
    
    private void initGraphics(Canvas canvas) {
        canvas.createBufferStrategy(1);
        strategy = canvas.getBufferStrategy();
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        
        backbuffer = config.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
    }
    
    public void run() {
        boolean run = true;
        int width = backbuffer.getWidth();
        int height = backbuffer.getHeight();
        
        while (run) {
            Graphics graphics = null;
            try {
                Graphics2D graph2d = backbuffer.createGraphics();
                graph2d.setColor(Color.black);
                graph2d.fillRect(0, 0, width, height);
                
                // Draw
                graph2d.setColor(Color.green);
                graph2d.drawOval(width/2, height/2, 50, 40);
                
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
