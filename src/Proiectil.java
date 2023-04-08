import java.awt.image.BufferedImage;

public abstract class Proiectil extends Spritesheet{
    public float viteza_x,viteza_y,acceleratie_x,acceleratie_y, viteza_max;

    public Proiectil(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel, float viteza_max) {
        super(imagine, nrcadre, coloane, poz_x, poz_y, angel);
        this.viteza_max = viteza_max;
    }
    public Proiectil(Spritesheet sprite,float poz_x,float poz_y,float angel, float viteza_max){
        super( sprite, poz_x, poz_y, angel);
        this.viteza_max = viteza_max;
    }
    void UpdateProiectil() {
        viteza_x += acceleratie_x;
        if(viteza_x > viteza_max){
            viteza_x = viteza_max;
        }else if(viteza_x < -viteza_max){
            viteza_x = -viteza_max;
        }
        viteza_y += acceleratie_y;
        if(viteza_y > viteza_max){
            viteza_y = viteza_max;
        }else if(viteza_y < -viteza_max){
            viteza_y = -viteza_max;
        }
        SetCoordX(GetCoordX()+viteza_x);
        SetCoordY(GetCoordY()+viteza_y);
    }
    public boolean isOutOfBounds(int ecranX,int ecranY){
        return GetCoordX() <= (float) GetMarimeTexX() / (-2) ||
                GetCoordX() >= (float) GetMarimeTexX() / 2 + ecranX ||
                GetCoordY() <= (float) GetMarimeTexY() / (-2) ||
                GetCoordY() >= (float) GetMarimeTexY() / 2 + ecranY;
    }

}
