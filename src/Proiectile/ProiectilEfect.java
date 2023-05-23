package Proiectile;

import Obiecte.Sir;
import Obiecte.Spritesheet;

public class ProiectilEfect extends Proiectil{
    public ProiectilEfect(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max) {
        super(sprite, poz_x, poz_y, angel, viteza_max, 20);
    }
    @Override protected void UpdateCadru(){
        if(cadruAnimatie >= vitezaAnimatie-1){
            cadruAnimatie = vitezaAnimatie-1;
            shouldDissapear = true;
        }
        else{
            cadruAnimatie++;
        }
        System.out.println((float)numar_cadre/vitezaAnimatie);
        CresteCadru((float)numar_cadre/vitezaAnimatie);
    }
    @Override
    public void HitSir(Sir sir) {

    }
}
