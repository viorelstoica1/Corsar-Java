
public class ProiectilBila extends Proiectil{

    public ProiectilBila(Spritesheet sprite,float poz_x,float poz_y,float angel, float viteza_max){
        super( sprite, poz_x, poz_y, angel, viteza_max);
    }

    @Override
    public void HitSir(Sir sir) {
        Bila aux = sir.TestColiziune(this);
        sir.adaugaPeBila(aux, new Bila(getSprite(), GetCoordX(), GetCoordY(), GetUnghi(), aux.acceleratie));
        SoundManager.playSound("src/resources/sunete/collide_spheres_shot.wav");
        shouldDissapear = true;
    }
}
