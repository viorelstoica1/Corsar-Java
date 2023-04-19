import java.awt.image.BufferedImage;

public class Bila extends Spritesheet{
    public float index = 0;
    public int frameAnimatie = 8;
    public float acceleratie, viteza = 0, vitezaMax = 0;
    public boolean isWaveLeader = false, isSirLeader = false, isAnimating = false, isStable;
    public Bila(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel, float acceleratie) {
        super(imagine, nrcadre, coloane, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
        isStable = true;
    }
    public Bila(Spritesheet imagine, float poz_x, float poz_y, float angel, float acceleratie) {
        super(imagine, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
        isStable = true;
    }

    public boolean isSameColour(Bila bila){
        return this.imagineRaw == bila.imagineRaw;
    }

    public void calculeazaViteza(){
        if(vitezaMax > 0){
            if(viteza < vitezaMax){
                viteza += acceleratie;
            }
            else if(viteza > vitezaMax){
                viteza -= acceleratie;
            }
        }else if(vitezaMax < 0){
            if(viteza < vitezaMax){
                viteza += acceleratie;
            }
            else if(viteza > vitezaMax){
                viteza -= acceleratie;
            }
        }
        else{
            if(Math.abs(viteza)<acceleratie){
                viteza = 0;
            }
            else{
                if(viteza>0){
                    viteza-=acceleratie;
                }else{
                    viteza+=acceleratie;
                }
            }
        }
        /*if(vitezaMax%viteza < acceleratie){
            viteza = vitezaMax;
        }*/
    }
    public void AnimatieInserare(GameObject traseuCurentBila){
        if (frameAnimatie == 0) {
            isAnimating = false;
            isStable = false;
        }
        else {
            float x = (traseuCurentBila.GetCoordX() - GetCoordX()) / frameAnimatie;
            float y = (traseuCurentBila.GetCoordY() - GetCoordY()) / frameAnimatie;
            SetCoordX(GetCoordX() + x);
            SetCoordY(GetCoordY() + y);
            frameAnimatie--;
        }
    }
    public float MarimeAnimatie(){
        if(frameAnimatie == 0){
            return (float)GetMarimeSpriteX();
        }
        return (float)GetMarimeSpriteX() * (8-frameAnimatie)/8;
    }
}
