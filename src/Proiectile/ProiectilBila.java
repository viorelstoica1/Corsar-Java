package Proiectile;

import Nivele.Level;
import Obiecte.Bila;
import Obiecte.Sir;
import Obiecte.Spritesheet;

import javax.swing.text.AbstractDocument;

public class ProiectilBila extends Proiectil{
    public ProiectilBila(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max){
        super( sprite, poz_x, poz_y, angel, viteza_max, 60);
    }

    @Override
    public void HitSir(Sir sir) {
            Bila aux = sir.TestColiziune(this);
            shouldDissapear = sir.adaugaPeBila(aux, new Bila(getSprite(), GetCoordX(), GetCoordY(), GetUnghi(), aux.acceleratie, Level.numarNivel));
    }
}
