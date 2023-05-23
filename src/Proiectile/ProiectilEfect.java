package Proiectile;

import Obiecte.Sir;
import Obiecte.Spritesheet;

public class ProiectilEfect extends Proiectil{
    private int lastFrame = 0;
    public ProiectilEfect(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max, int vitezaAnimatie) {
        super(sprite, poz_x, poz_y, angel, viteza_max, vitezaAnimatie);
    }
    @Override public void UpdateProiectil(){
        super.UpdateProiectil();
        if(lastFrame > cadruAnimatie){
            shouldDissapear = true;
        }
        lastFrame = cadruAnimatie;
    }
    @Override
    public void HitSir(Sir sir) {

    }
}
