package Manageri;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Backbone.Application;

public class MouseManager extends JPanel {
    public static boolean clickStanga = false, clickDreapta = false, middleMouse = false;
    public static int mousex = 0,mousey = 0;
    public MouseManager(){
        this.setBounds(0,0, Application.getScreenWidth(), Application.getScreenHeight());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //System.out.println("Click stanga apasat la coordonatele " + e.getX() + " " + e.getY());
                    clickStanga = true;
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    //System.out.println("Middle mouse apasat la coordonatele " + e.getX() + " " + e.getY());
                    middleMouse = true;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //System.out.println("Click dreapta apasat la coordonatele " + e.getX() + " " + e.getY());
                    clickDreapta = true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //System.out.println("Click stanga eliberat la coordonatele " + e.getX() + " " + e.getY());
                    clickStanga = false;
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    //System.out.println("Middle mouse eliberat la coordonatele " + e.getX() + " " + e.getY());
                    middleMouse = false;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //System.out.println("Click dreapta eliberat la coordonatele " + e.getX() + " " + e.getY());
                    clickDreapta = false;
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();
            }
            public void mouseDragged(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();
            }
        });
    }
}
