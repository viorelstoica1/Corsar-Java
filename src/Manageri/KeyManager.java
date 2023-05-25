package Manageri;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Backbone.Application;
public class KeyManager extends JPanel {
    public static boolean enter = false, backspace = false;
    public static char litera;
    public KeyManager(){
        this.setBounds(0,0, Application.getScreenWidth(), Application.getScreenHeight());
        this.setFocusable(true);
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED){
                    if((e.getKeyChar() >= KeyEvent.VK_A && e.getKeyChar() <= KeyEvent.VK_Z) || (e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z')){
                        //litera
                        litera = e.getKeyChar();
                        //System.out.println("Tasta "+ litera);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                        //backspace
                        backspace = true;
                        //System.out.println("Backspace");
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        //enter
                        enter = true;
                        //System.out.println("Enter");
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED){
                    if((e.getKeyChar() >= KeyEvent.VK_A && e.getKeyChar() <= KeyEvent.VK_Z) || (e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z')){
                        //litera
                        litera = 0;
                        System.out.println("Tasta eliberata");
                    }
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                        //backspace
                        backspace = false;
                        System.out.println("Backspace eliberat");
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        //enter
                        enter = false;
                        System.out.println("Enter eliberat");
                    }
                }
            }
        });
    }
}
