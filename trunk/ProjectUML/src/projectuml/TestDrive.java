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
        while(true){
            try{
            ship.update();
            repaint();
            
            Thread.sleep(30);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getWidth());
        ship.draw((Graphics2D)g);
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                ship.destroyShip();
                System.out.println("Space");
                break;
            case KeyEvent.VK_LEFT:
                ship.goLeft();
                System.out.println("VK_LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                ship.goRight();
                System.out.println("VK_RIGHT");
                break;
            case KeyEvent.VK_UP:
                ship.goUp();
                System.out.println("VK_UP");
                break;
            case KeyEvent.VK_DOWN:
                ship.goDown();
                System.out.println("VK_DOWN");
                break;
            case KeyEvent.VK_F:
                ship.fire();
                System.out.println("VK_F");
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        //gamestates.keyEvent(e);
         switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                ship.destroyShip();
                System.out.println("Space");
                break;
            case KeyEvent.VK_LEFT:
                ship.resetDx();
                System.out.println("VK_LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                ship.resetDx();
                System.out.println("VK_RIGHT");
                break;
            case KeyEvent.VK_UP:
                ship.resetDy();
                System.out.println("VK_UP");
                break;
            case KeyEvent.VK_DOWN:
                ship.resetDy();
                System.out.println("VK_DOWN");
                break;
             default:
                break;
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //gamestates.keyEvent(e);
    }
    
    public static void main(String[] args){
        TestDrive td = new TestDrive();
        td.run();
    }
}
