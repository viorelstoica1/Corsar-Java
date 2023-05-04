
public class ProiectilEfect extends Proiectil{
    private int cadruAnimatie = 0;
    private final int vitezaAnimatie;

    public ProiectilEfect(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max, int vitezaAnimatie) {
        super(sprite, poz_x, poz_y, angel, viteza_max);
        this.vitezaAnimatie = vitezaAnimatie;
    }

    @Override
    void UpdateProiectil() {
        super.UpdateProiectil();
        cadruAnimatie++;
        if(cadruAnimatie >= vitezaAnimatie){
            cadruAnimatie = 0;
            shouldDissapear = true;
        }
        SetCadru((int) Math.floor((float)cadruAnimatie/(float)(vitezaAnimatie/GetNrCadre())));
    }

    @Override
    public void HitSir(Sir sir) {

    }
}
