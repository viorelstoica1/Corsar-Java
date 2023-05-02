import java.awt.image.BufferedImage;

public class ProiectilFoc extends Proiectil{
    float marimeZonaFoc;

    public ProiectilFoc(float poz_x, float poz_y, float angel, float viteza_max, float marimeZonaFoc) {
        super((Spritesheet) ResourceManager.getTexturaBila("foc"), poz_x, poz_y, angel, viteza_max);
    }

    @Override
    public void HitSir(Sir sir) {
        sir.getListaBile().removeIf(bila -> bila.DistantaPatrat(this) <= marimeZonaFoc);
    }
}
