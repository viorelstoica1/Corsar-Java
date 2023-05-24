package Obiecte;

import Manageri.ResourceManager;
import Nivele.Level;

public class Bila extends Spritesheet {
    private static final int targetCadre = 60;
    public float index = 0;
    private final int marimeAnimatie = 16;
    public float frameAnimatie = marimeAnimatie;
    public int scoreMultiplier = 1;
    public float acceleratie, viteza = 0, vitezaMax = 0;
    public boolean isWaveLeader = false, isSirLeader = false, isAnimating = false, isStable, canInsertLeft = true, canInsertRight = true;
    private boolean reversedRotation = false;
    public Bila(Spritesheet imagine, float poz_x, float poz_y, float angel, float acceleratie, int nivel) {
        super(imagine, poz_x, poz_y, angel);
        this.acceleratie = acceleratie;
        if(nivel != 2)
            this.reversedRotation = true;
        isStable = true;
    }


    public boolean isSameColour(Bila bila){
        return (this.imagineRaw == bila.imagineRaw) || (this.imagineRaw == ResourceManager.get().getTexturaBila("curcubeu", Level.numarNivel).GetTex()) || (bila.imagineRaw == ResourceManager.get().getTexturaBila("curcubeu", Level.numarNivel).GetTex());
    }
    @Override
    public void CresteCadru(float x){
        if(reversedRotation){
            cadru_curent -= x * (float)numar_cadre/(float)targetCadre;
        }else cadru_curent += x * (float)numar_cadre/(float)targetCadre;
        if (Math.floor(cadru_curent) > numar_cadre - 1)
        {
            cadru_curent = 0;
        }
        if (cadru_curent < 0) {
            cadru_curent = numar_cadre-1;
        }
    }
    public void calculeazaViteza(){
        if(Math.abs(vitezaMax - viteza)<acceleratie){
            viteza = vitezaMax;
        }else if(vitezaMax > 0){
            if(viteza < vitezaMax){
                viteza += acceleratie;
            }
            else if(viteza > vitezaMax) {
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
            if(viteza>0){
                viteza-=acceleratie;
            }else{
                viteza+=acceleratie;
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
