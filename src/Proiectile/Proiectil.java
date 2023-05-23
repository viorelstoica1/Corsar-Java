package Proiectile;

import Backbone.Application;
import Obiecte.Sir;
import Obiecte.Spritesheet;

public abstract class Proiectil extends Spritesheet {
    public boolean shouldDissapear = false;
    public float viteza_x,viteza_y,acceleratie_x,acceleratie_y, viteza_max;
    protected int cadruAnimatie = 0;
    protected int vitezaAnimatie = 20;

    public Proiectil(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max, int vitezaAnimatie){
        super( sprite, poz_x, poz_y, angel);
        this.viteza_max = viteza_max;
        this.vitezaAnimatie = vitezaAnimatie;
    }
    public void UpdateProiectil() {
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

        UpdateCadru();
        if (isOutOfBounds(Application.getScreenWidth(), Application.getScreenHeight())) {
            shouldDissapear = true;
        }
    }
    protected void UpdateCadru(){
        if(cadruAnimatie >= vitezaAnimatie){
            cadruAnimatie = 0;
        }
        else{
            cadruAnimatie++;
        }
        CresteCadru((float)numar_cadre/vitezaAnimatie);
    }
    public boolean isOutOfBounds(int ecranX,int ecranY){
        return GetCoordX() <= (float) GetMarimeTexX() / (-2) ||
                GetCoordX() >= (float) GetMarimeTexX() / 2 + ecranX ||
                GetCoordY() <= (float) GetMarimeTexY() / (-2) ||
                GetCoordY() >= (float) GetMarimeTexY() / 2 + ecranY;
    }
    public Spritesheet getSprite(){
        return this;
    }
    public abstract void HitSir(Sir sir);
}
