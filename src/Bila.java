
public class Bila extends Spritesheet{
    public float index = 0;
    private final int marimeAnimatie = 16;
    public int frameAnimatie = marimeAnimatie, scoreMultiplier = 1;
    public float acceleratie, viteza = 0, vitezaMax = 0;
    public boolean isWaveLeader = false, isSirLeader = false, isAnimating = false, isStable;

    public Bila(Spritesheet imagine, float poz_x, float poz_y, float angel, float acceleratie) {
        super(imagine, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
        isStable = true;
    }

    public boolean isSameColour(Bila bila){
        return (this.imagineRaw == bila.imagineRaw) || (this.imagineRaw == ResourceManager.get().getTexturaBila("curcubeu").GetTex());
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
    }
    public void AnimatieInserare(GameObject traseuCurentBila){
        if (frameAnimatie == 0) {
            isAnimating = false;
            isStable = false;
        }
        else {
            float x = (traseuCurentBila.GetCoordX() - GetCoordX()) / frameAnimatie;
            float y = (traseuCurentBila.GetCoordY() - GetCoordY()) / frameAnimatie;
            float unghi;
            if((GetUnghi() - traseuCurentBila.GetUnghi())%360 < 180 && (GetUnghi() - traseuCurentBila.GetUnghi())%360 > -180){
                unghi = (traseuCurentBila.GetUnghi() - GetUnghi()) / frameAnimatie;
            }else{
                unghi = (traseuCurentBila.GetUnghi() + GetUnghi()) / frameAnimatie;
            }
            SetCoordX(GetCoordX() + x);
            SetCoordY(GetCoordY() + y);
            SetUnghi(GetUnghi() + unghi);
            frameAnimatie--;
        }
    }
    public float MarimeAnimatie(){
        if(frameAnimatie == 0){
            return (float)GetMarimeSpriteX();
        }
        return (float)GetMarimeSpriteX() * (marimeAnimatie-frameAnimatie)/marimeAnimatie;
    }
}
