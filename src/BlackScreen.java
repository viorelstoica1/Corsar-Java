import javax.swing.*;
import java.awt.*;

public class BlackScreen extends JPanel {
    BlackScreen(){
        this.setLayout(null);
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,1920,1080);
    }
}