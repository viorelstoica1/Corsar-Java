package Nivele;

import Backbone.Scoruri;
import Backbone.stareAplicatie;
import Manageri.ResourceManager;
import Obiecte.Bila;
import Obiecte.Capcana;
import Obiecte.GameObject;
import Proiectile.Proiectil;
import Proiectile.ProiectilBila;
import Proiectile.ProiectilFactory;

import java.awt.*;
import java.util.LinkedList;

public class Level3 extends Level{
    Capcana capcanaStanga, capcanaDreapta;
    public Level3(int Width, int Height, int dificultate) {
        super(Width, Height, dificultate);
        numarNivel = 3;
        fundal.SetTexRaw(ResourceManager.get().getFundal(3).GetTex());
        /*sirBile.indexRapid = 400;
        sirBile.indexIncet = 2650;
        sirBile.indexFinal = 3123;*/
        capcanaStanga = new Capcana(ResourceManager.get().getCapcana(1).GetTex(),373,36,0,200, 2, dificultate);
        capcanaDreapta = new Capcana(ResourceManager.get().getCapcana(2).GetTex(),753,34,0,200, 2, dificultate);
    }

    @Override
    public void onStart() {
        super.onStart();
        firstPaint = true;
        tunar.SetCoordX(rezolutieX - (float)rezolutieX / 2);
        tunar.SetCoordY(rezolutieY - (float)rezolutieY / 20);

        tunar.SetLimite(rezolutieX / 20, rezolutieX - rezolutieX / 10, rezolutieY - rezolutieY / 19, rezolutieY - rezolutieY / 19);
        tunar.vitezaTragere = 20;
        tunar.SetUnghi(0);
        cursorPrincipal.SetUnghi(0);
        cursorSecundar.SetUnghi(0);
        //tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) resurse.getBilaRandom(dificultate, numarNivel), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        //tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) resurse.getBilaRandom(dificultate, numarNivel), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        tunar.SetProiectilCurent(ProiectilFactory.getProiectil("bila", null, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), numarNivel,dificultate));
        tunar.SetProiectilRezerva(ProiectilFactory.getProiectil("bila", null, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), numarNivel, dificultate));
    }

    public stareAplicatie Actualizare() {//in actualizare trebuie implementat cursorul
        stareAplicatie status = super.Actualizare();
        //actualizari proiectile
        int index = 0;
        while (index < listaProiectile.size()){
            Proiectil proiectil = listaProiectile.get(index);
            if(proiectil.getClass() == ProiectilBila.class && proiectil.viteza_y >0 && proiectil.GetCoordY()>700){
                //Level.AdaugaEfect(new ProiectilEfect((Spritesheet) ResourceManager.get().getTexturaBilaSparta(proiectil, numarNivel),proiectil.GetCoordX(),proiectil.GetCoordY(),proiectil.GetUnghi(),0));
                Level.AdaugaEfect(ProiectilFactory.getProiectil("efect", ResourceManager.get().nameOf(proiectil), proiectil.GetCoordX(),proiectil.GetCoordY(),proiectil.GetUnghi(),3, 0));
                proiectil.shouldDissapear = true;
            }
            index++;
        }

        //actualizari capcane
        capcanaDreapta.Update();
        capcanaStanga.Update();
        if(capcanaDreapta.isReady()){
            //System.out.println("Capcana trage");
            listaProiectile.add(capcanaDreapta.proiectilIncarcat);
            capcanaDreapta.resetCapcana();
        }
        if(capcanaStanga.isReady()){
            //System.out.println("Capcana trage");
            listaProiectile.add(capcanaStanga.proiectilIncarcat);
            capcanaStanga.resetCapcana();
        }
        //actualizari cursor
        cursorPrincipal.SetCoordY(0);
        cursorPrincipal.SetCoordX(tunar.GetCoordX());
        cursorSecundar.SetCoordX(tunar.GetCoordX());
        LinkedList<Bila> lista = sirBile.getListaBile();
        for(Bila iterator : lista){
            if(iterator.GetCoordX() > cursorPrincipal.GetCoordX()-iterator.GetMarimeSpriteX() && iterator.GetCoordX() < cursorPrincipal.GetCoordX()+iterator.GetMarimeSpriteX()){
                float coordonataY = (float) (iterator.GetCoordY() + (Math.sqrt(Math.pow(iterator.GetMarimeSpriteX(),2)-Math.pow((tunar.GetCoordX()-iterator.GetCoordX()),2))));
                if(cursorPrincipal.GetCoordY() < coordonataY){
                    cursorPrincipal.SetCoordY(coordonataY );
                }
            }
        }
        if(cursorPrincipal.GetCoordY() == 0){
            cursorPrincipal.SetCoordY(-cursorPrincipal.GetMarimeTexY());
        }
        cursorSecundar.SetCoordY((cursorPrincipal.GetCoordY()));
        if(sirBile.marime() == 0) {
            switch(dificultate){
                case 4 -> Scoruri.get().SalvareScor(scor, 2, 1);
                case 5 -> Scoruri.get().SalvareScor(scor, 2, 1.5f);
                case 6 -> Scoruri.get().SalvareScor(scor, 2, 2);
            }
        }
        return status;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        capcanaStanga.paintComponent(g);
        capcanaDreapta.paintComponent(g);
    }
    @Override
    protected void AlocareTraseuBile() {
        traseuBile = new GameObject[3123];//3404
        for (int i = 0; i < 3123; i++) {
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (int j = -50; j < 80; j++) {
            traseuBile[i].SetCoordX(1320);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = 0; unghi < Math.PI / 2; unghi = (float) (unghi + 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 1240));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 80));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 1240; j > 200; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(160);
            traseuBile[i].SetUnghi((float) (Math.PI / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi > Math.PI; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 200));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 210));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (int j = 210; j < 470; j++) {
            traseuBile[i].SetCoordX(150);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = (float) (Math.PI); unghi > Math.PI / 2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 200));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 470));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 200; j < 490; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(520);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi < Math.PI * 1.75; unghi = (float) (unghi + 0.033)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 30 + 490));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 30 + 550));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 0; j < 203; j++) {//scandura
            int xinceput = 510, xsfarsit = 680;
            int yinceput = 528, ysfarsit = 640;
            traseuBile[i].SetCoordX(xinceput + (float)((xsfarsit - xinceput) * j) / 203);
            traseuBile[i].SetCoordY(yinceput + (float)((ysfarsit - yinceput) * j) / 203);
            traseuBile[i].SetUnghi((float) (Math.PI * 1.75));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3/4); unghi > Math.PI / 2; unghi = (float) (unghi - 0.033)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 30 + 702));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 30 + 618));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        //System.out.println(traseuBile[i-1].GetCoordX()+" "+traseuBile[i-1].GetCoordY());
        for (int j = 702; j < 900; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(648);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) Math.PI / 2; unghi > 0; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 900));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 548));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (float unghi = (float) Math.PI; unghi < (float) (Math.PI * 3 / 2); unghi = (float) (unghi + 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 1040));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 548));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 1040; j < 1150; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(508);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        //aici am ramas
        for (float unghi = (float) (Math.PI * 3 / 2); unghi < Math.PI * 2; unghi = (float) (unghi + 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 1150));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 548));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (float unghi = (float) (Math.PI); unghi > Math.PI / 2; unghi = (float) (unghi - 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 1270));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 548));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 1270; j < 1420; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(628);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        System.out.println(i+" puncte are traseul");
        //aici a ramas

        for (int j = 0; j < 3123; j++) {//3123
            traseuBile[j].SetUnghi((float) Math.toDegrees(traseuBile[j].GetUnghi()));
        }
        System.out.println("Traseul are " + i + " puncte");
    }
    protected void MiddlePaint(Graphics g){

    }
}
