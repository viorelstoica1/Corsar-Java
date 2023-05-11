import java.awt.*;

public class Buton extends Rectangle {
    public String textButon = null;
    Buton(int x, int y, int width, int height){
        super(x,y,width,height);
    }
    public boolean isSelected(int mousex, int mousey){
        return mousex > x && mousex < x + width && mousey > y && mousey < y + height;
    }
    public void paintComponent(Graphics g){
        g.setFont(ResourceManager.get().font.deriveFont(40f));
        //g.drawRect(x,y,width,height);
        if(textButon != null){
            g.drawString(textButon,x+width/2-ResourceManager.get().font.getSize()*textButon.length()*2/3,y+height/2+ResourceManager.get().font.getSize()/2);
        }
    }
}
