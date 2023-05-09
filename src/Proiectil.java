
public abstract class Proiectil extends Spritesheet{
    public boolean shouldDissapear = false;
    public float viteza_x,viteza_y,acceleratie_x,acceleratie_y, viteza_max;
    protected int cadruAnimatie = 0;
    protected final int vitezaAnimatie;

    public Proiectil(Spritesheet sprite,float poz_x,float poz_y,float angel, float viteza_max, int vitezaAnimatie){
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

        if(cadruAnimatie >= vitezaAnimatie-1){
            cadruAnimatie = 0;
        }
        else{
            cadruAnimatie++;
        }

        SetCadru((int) Math.floor((float)cadruAnimatie/(float)(vitezaAnimatie/GetNrCadre())));
        if (isOutOfBounds(Application.getScreenWidth(), Application.getScreenHeight())) {
            shouldDissapear = true;
        }
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
