import java.awt.*;
import java.awt.image.BufferedImage;

public class Capcana extends Textura{
    private int timerCapcana;
    private final int cooldownCapcana;
    private final float viteza;
    public Proiectil proiectilIncarcat = null;
    public Capcana(BufferedImage imagine, float poz_x, float poz_y, float angel, int cooldownCapcana, float viteza) {
        super(imagine, poz_x, poz_y, angel);
        this.cooldownCapcana = cooldownCapcana;
        timerCapcana = cooldownCapcana;
        this.viteza = viteza;
        proiectilIncarcat = new ProiectilBila((Spritesheet) ResourceManager.get().getBilaRandom(),GetCoordX()+5,GetCoordY(),GetUnghi(), viteza,120);
        proiectilIncarcat.viteza_x = 0;
        proiectilIncarcat.viteza_y = viteza;
        marime_x = proiectilIncarcat.GetMarimeSpriteX();
        marime_y = proiectilIncarcat.GetMarimeSpriteY();
    }
    public void Update(){
        if(timerCapcana !=0){
            timerCapcana--;
        }
    }
    public boolean isReady(){
        return timerCapcana == 0;
    }
    public void resetCapcana(){
        timerCapcana = cooldownCapcana;
        proiectilIncarcat = new ProiectilBila((Spritesheet) ResourceManager.get().getBilaRandom(),GetCoordX()+5,GetCoordY(),GetUnghi(), viteza,120);
        proiectilIncarcat.viteza_x = 0;
        proiectilIncarcat.viteza_y = viteza;
    }
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.rotate(Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        g2d.drawImage(imagineRaw, CenterX(), CenterY(), null);
        g2d.rotate(-Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        //g2d.drawRect(CenterX(),CenterY(),proiectilIncarcat.GetMarimeSpriteX(), (int) (proiectilIncarcat.GetMarimeSpriteY()*1.5));
    }
}
