import java.util.LinkedList;

public class ProiectilFoc extends Proiectil{
    float marimeZonaFoc;

    public ProiectilFoc(float poz_x, float poz_y, float angel, float viteza_max, float marimeZonaFoc, int vitezaAnimatie) {
        super((Spritesheet) ResourceManager.get().getTexturaBila("fire"), poz_x, poz_y, angel, viteza_max, vitezaAnimatie);
        this.marimeZonaFoc = marimeZonaFoc;
    }

    @Override
    public void HitSir(Sir sir) {
        int index = 0;
        LinkedList<Bila> listaBile = sir.getListaBile();
        //float viteza = listaBile.get(0).viteza;

        while(index < listaBile.size()){
            if(listaBile.get(index).DistantaPatrat(this) <= marimeZonaFoc*marimeZonaFoc){
                //TODO gaseste o solutie sa mearga asta
                Scena.AdaugaEfect(new ProiectilEfect((Spritesheet) ResourceManager.get().getTexturaBilaSparta(listaBile.get(index)),listaBile.get(index).GetCoordX(),listaBile.get(index).GetCoordY(),listaBile.get(index).GetUnghi(),0,16));
                SoundManager.playSound("src/resources/sunete/collapse_fireball.wav");
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
