import java.awt.image.BufferedImage;

public class Bila extends Spritesheet{
    public float index = 0;
    public int frameAnimatie = 0;
    public float acceleratie = 0,viteza = 0, vitezaMax = 0;
    public boolean isWaveLeader = false, isSirLeader = false, isAnimating = false;
    public Bila(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel, float acceleratie) {
        super(imagine, nrcadre, coloane, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
    }
    public Bila(Spritesheet imagine, float poz_x, float poz_y, float angel, float acceleratie) {
        super(imagine, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
    }

    public boolean isSameColour(Bila bila){
        return this.imagineRaw == bila.imagineRaw;
    }

    public void calculeazaViteza(){
        if(viteza < vitezaMax){
            viteza += acceleratie;
        }else if(viteza > vitezaMax){
            viteza -= acceleratie;
        }
        if(vitezaMax%viteza < acceleratie){
            viteza = vitezaMax;
        }
    }

}
