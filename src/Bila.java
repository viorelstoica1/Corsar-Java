import java.awt.image.BufferedImage;

public class Bila extends Spritesheet{
    public int index;
    private int viteza;
    private int frameCurent;
    //TODO flag-uri !!
    public Bila(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel) {
        super(imagine, nrcadre, coloane, poz_x, poz_y, angel);
        frameCurent = 0;
    }
    public boolean isSameColour(Bila bila){
        if (this.imagineRaw == bila.imagineRaw){
            return true;
        }
        return false;//TODO VERIFICA FUNCTIA
    }
}
