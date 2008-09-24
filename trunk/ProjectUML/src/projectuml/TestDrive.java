package projectuml;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;

/**
 *
 * @author steeri
 */
public class TestDrive extends JFrame implements KeyListener{
    
  
    PlayerShip ship = new PlayerShip();
    
    public TestDrive(){
        addKeyListener(this);
        setSize(400, 400);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        ship.show();
        ship.activate();
        ship.setPosition(new Point(100,100));
    }
    
    public void run(){
        repaint();
    }
    
    public void paint(Graphics g) {
       
        ship.update();
        ship.draw((Graphics2D)g);
        g.setColor(Color.GREEN);
        g.drawRect(1, 1, 20, 20);
        System.out.println("Paint Component");
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                ship.destroyShip();
                System.out.println("Space");
                break;
            case KeyEvent.VK_LEFT:
                ship.setPosition(new Point(10,100));
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        //gamestates.keyEvent(e);
    }
    
    public void keyTyped(KeyEvent e) {
        //gamestates.keyEvent(e);
    }
    
    public static void main(String[] args){
        TestDrive td = new TestDrive();
        td.run();
    }
}
