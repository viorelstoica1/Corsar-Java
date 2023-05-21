public class ProiectilBani extends Proiectil{

    public ProiectilBani(Spritesheet sprite, float poz_x, float poz_y, float angel, float viteza_max, int vitezaAnimatie) {
        super(sprite, poz_x, poz_y, angel, viteza_max, vitezaAnimatie);
        if(Math.random() >= 0.5){
            viteza_x = (float) (Math.random()*10);
        }
        else{
            viteza_x = (float) (Math.random()*-10);
        }
        viteza_y = (float) (Math.random()*-10);
        acceleratie_y = 0.2f;
    }
    @Override
    public void UpdateProiectil(){
        super.UpdateProiectil();
        if((GetCoordX() <= (float)GetMarimeSpriteX()/2) || (GetCoordX() >= Application.getScreenWidth() - (float)GetMarimeSpriteX()/2)){
            viteza_x = -viteza_x;
        }
        if(GetCoordY()<0){
            shouldDissapear = false;
        }
    }
    @Override
    public void HitSir(Sir sir) {

    }
}
