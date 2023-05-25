package Obiecte;

import Manageri.ResourceManager;
import Manageri.SetariManager;
import Proiectile.Proiectil;
import Proiectile.ProiectilBila;

import java.awt.*;
import java.awt.image.BufferedImage;

import Nivele.Level;

public class Capcana extends Textura {
    private int timerCapcana;
    private final int cooldownCapcana, dificultate;
    private final float viteza;
    public Proiectil proiectilIncarcat;
    public Capcana(BufferedImage imagine, float poz_x, float poz_y, float angel, int cooldownCapcana, float viteza, int dificultate) {
        super(imagine, poz_x, poz_y, angel);
        this.cooldownCapcana = (int) SetariManager.get().getCooldownCapcana();
        timerCapcana = cooldownCapcana;
        this.viteza = SetariManager.get().getVitezatragereCapcana();
        this.dificultate = dificultate;
        proiectilIncarcat = new ProiectilBila((Spritesheet) ResourceManager.get().getBilaRandom(dificultate, Level.numarNivel),GetCoordX()+5,GetCoordY(),GetUnghi(), viteza);
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
        proiectilIncarcat = new ProiectilBila((Spritesheet) ResourceManager.get().getBilaRandom(dificultate, Level.numarNivel),GetCoordX()+5,GetCoordY(),GetUnghi(), viteza);
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
