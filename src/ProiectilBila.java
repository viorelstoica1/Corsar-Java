import java.awt.image.BufferedImage;

public class ProiectilBila extends Proiectil{
    public ProiectilBila(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel, float viteza_max) {
        super(imagine, nrcadre, coloane, poz_x, poz_y, angel, viteza_max);
    }
    public ProiectilBila(Spritesheet sprite,float poz_x,float poz_y,float angel, float viteza_max){
        super( sprite, poz_x, poz_y, angel, viteza_max);
    }


}
