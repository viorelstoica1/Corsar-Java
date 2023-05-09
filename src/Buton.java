import java.awt.*;

public class Buton extends Rectangle {
    Buton(int x, int y, int width, int height){
        super(x,y,width,height);
    }
    public boolean isSelected(int mousex, int mousey){
        return mousex > x && mousex < x + width && mousey > y && mousey < y + height;
    }
}
