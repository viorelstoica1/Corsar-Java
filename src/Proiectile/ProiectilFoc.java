package Proiectile;

import Manageri.ResourceManager;
import Manageri.SetariManager;
import Manageri.SoundManager;
import Nivele.Level;
import Obiecte.Bila;
import Obiecte.Sir;
import Obiecte.Spritesheet;

import java.util.LinkedList;

public class ProiectilFoc extends Proiectil{
    public float marimeZonaFoc;

    public ProiectilFoc(float poz_x, float poz_y, float angel, float viteza_max, int vitezaAnimatie) {
        super((Spritesheet) ResourceManager.get().getTexturaBila("fire", Level.numarNivel), poz_x, poz_y, angel, viteza_max, vitezaAnimatie);
        this.marimeZonaFoc = SetariManager.get().getRazaProiectilFoc();
    }

    @Override
    public void HitSir(Sir sir) {
        int index = 0;
        LinkedList<Bila> listaBile = sir.getListaBile();
        while(index < listaBile.size()){
            if(listaBile.get(index).DistantaPatrat(this) <= marimeZonaFoc*marimeZonaFoc){
                //Level.AdaugaEfect(new ProiectilEfect((Spritesheet) ResourceManager.get().getTexturaBilaSparta(listaBile.get(index), Level.numarNivel),listaBile.get(index).GetCoordX(),listaBile.get(index).GetCoordY(),listaBile.get(index).GetUnghi(),0));
                Level.AdaugaEfect(ProiectilFactory.getProiectil("efect", ResourceManager.get().nameOf(listaBile.get(index)), listaBile.get(index).GetCoordX(),listaBile.get(index).GetCoordY(),listaBile.get(index).GetUnghi(), 0, 0));
                SoundManager.playSound("src/resources/sunete/collapse_fireball.wav", -20, false);
                listaBile.remove(index);
            }
            else{
                index++;
            }
        }
        index = 0;
        while(index < listaBile.size() ){
            if(index == 0){
                listaBile.get(index).isWaveLeader = true;
            }
            else if(listaBile.get(index).index - listaBile.get(index-1).index > ResourceManager.get().getMarimeBila())
                listaBile.get(index).isSirLeader = true;
            index++;
        }
        shouldDissapear = true;
    }
}
