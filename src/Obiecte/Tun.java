package Obiecte;

import Manageri.SetariManager;
import Manageri.SoundManager;
import Proiectile.Proiectil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tun extends Textura {
    private Proiectil proiectilIncarcat,proiectilRezerva;
    private boolean gataDeTras;
    public int vitezaTragere;
    private int limitaStanga, limitaDreapta, limitaJos, limitaSus;
    private final Spritesheet tunJos;
    private int cadruAnimatie;
    private final int vitezaAnimatie;
    public Tun(BufferedImage imagine, BufferedImage imagineSus, float poz_x, float poz_y, float angel) {
        super(imagineSus,poz_x, poz_y, angel);
        tunJos = new Spritesheet(imagine,5,5,poz_x,poz_y,angel);
        vitezaTragere = (int) SetariManager.get().getVitezaTragereTun();
        proiectilIncarcat = null;
        proiectilRezerva = null;
        gataDeTras = true;
        cadruAnimatie = 0;
        vitezaAnimatie = (int) SetariManager.get().getVitezaAnimatieTun();
    }
    public void SetLimite( int stangaX, int dreaptaX, int josY, int susY){
        limitaDreapta = dreaptaX;
        limitaStanga = stangaX;
        limitaJos = josY;
        limitaSus = susY;
    }
    public void UpdateTun(int mousex, int mousey){
        if(!gataDeTras){
            cadruAnimatie++;
            if(cadruAnimatie >= vitezaAnimatie){
                cadruAnimatie = 0;
                gataDeTras = true;
            }
        }
        SetCoordX(mousex);
        SetCoordY(mousey);
        if(GetCoordX() < limitaStanga){
            SetCoordX(limitaStanga);
        }else if(GetCoordX() > limitaDreapta){
            SetCoordX((limitaDreapta));
        }
        if(GetCoordY() < limitaSus){
            SetCoordY(limitaSus);
        }else if(GetCoordY() > limitaJos){
            SetCoordY(limitaJos);
        }
        tunJos.SetCoordX((float) (GetCoordX() - Math.cos(Math.toRadians(GetUnghi()+90))*GetMarimeTexX()/2.25));
        tunJos.SetCoordY((float) (GetCoordY() - Math.sin(Math.toRadians(GetUnghi()+90))*GetMarimeTexY()/2.25));
        tunJos.SetCadru((int) Math.floor((float)cadruAnimatie/(float)(vitezaAnimatie/tunJos.GetNrCadre())));
        if(proiectilIncarcat != null){
            proiectilIncarcat.SetCoordX((float) (GetCoordX() + Math.cos(Math.toRadians(GetUnghi()-90)) * 50));
            proiectilIncarcat.SetCoordY((float) (GetCoordY() + Math.sin(Math.toRadians(GetUnghi()-90)) * 50));
            proiectilIncarcat.UpdateProiectil();
        }
        if(proiectilRezerva != null){
            proiectilRezerva.SetCoordX((float) (GetCoordX() - Math.cos(Math.toRadians(GetUnghi()-90)) * 15));
            proiectilRezerva.SetCoordY((float) (GetCoordY() - Math.sin(Math.toRadians(GetUnghi()-90)) * 15));
            proiectilRezerva.UpdateProiectil();
        }
    }
    public void Trage(){
        if(gataDeTras){
            gataDeTras = false;
            proiectilIncarcat.viteza_x = (float) (Math.cos(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
            proiectilIncarcat.viteza_y = (float) (Math.sin(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
            /*proiectilIncarcat.viteza_y = -6f;
            proiectilIncarcat.acceleratie_y = 0.5f;*/
            proiectilIncarcat.SetUnghi(GetUnghi());
            //System.out.println("Proiectil: "+proiectilIncarcat.viteza_x+" "+ proiectilIncarcat.viteza_y);
            proiectilIncarcat = null;
            SoundManager.playSound("src/resources/sunete/launch_sphere.wav", -10, false);
        }
    }
    public Proiectil GetProiectilIncarcat(){
        return proiectilIncarcat;
    }
    public Proiectil GetProiectilRezerva(){
        return proiectilRezerva;
    }
    public void SetProiectilCurent(Proiectil p){
        proiectilIncarcat = p;
    }
    public void SetProiectilRezerva(Proiectil p){
        proiectilRezerva = p;
    }
    public void CicleazaProiectil(Proiectil p) {
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = p;
        //System.out.println("cicleaza proiectil");
    }
    public boolean isGataDeTras() { return gataDeTras; }
    public void SchimbaOrdineProiectile(){
        Proiectil aux = proiectilIncarcat;
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = aux;
    }
    public void paintComponent(Graphics g){
        if(proiectilIncarcat != null){
            proiectilIncarcat.paintComponent(g);
        }
        if(proiectilRezerva != null){
            proiectilRezerva.paintComponent(g);
        }
        tunJos.paintComponent(g);
        super.paintComponent(g);

    }

    public Spritesheet GetTexJos(){
        return tunJos;
    }

    public void resizeTun(int newW, int newH){
        super.resize(newW,newH);
        tunJos.resize(super.marime_x*5,super.marime_y);
    }

    @Override
    public void SetUnghi(float angel) {
        super.SetUnghi(angel);
        tunJos.SetUnghi(angel);
    }
    public void ResetTunar(){
        tunJos.SetCadru(0);

    }
}
