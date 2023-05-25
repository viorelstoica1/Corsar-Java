package Meniuri;

import Backbone.Application;
import Backbone.Scoruri;
import Backbone.stareAplicatie;
import Obiecte.Textura;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MeniuSelectii extends Textura {
    public Buton buton1, buton2, buton3, buton4;
    public StariMeniu stareMeniu = StariMeniu.start;
    private int nivelSelectat;
    public MeniuSelectii(BufferedImage imagine, float poz_x, float poz_y, float angel) {
        super(imagine, poz_x, poz_y, angel);
        buton1 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) (GetCoordY()-GetMarimeTexY()*0.26),GetMarimeTexX(),100);
        buton1.textButon = "Start";
        buton2 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) (GetCoordY()-GetMarimeTexY()*0.09),GetMarimeTexX(),100);
        buton2.textButon = " Ajutor si Credite";
        buton3 = new Buton((int) GetCoordX()-GetMarimeTexX()/2,(int) (GetCoordY()+GetMarimeTexY()*0.08),GetMarimeTexX(),100);
        buton3.textButon = "Scoruri";
        buton4 = new Buton((int) GetCoordX()-GetMarimeTexX()/2,(int) (GetCoordY()+GetMarimeTexY()*0.25),GetMarimeTexX(),100);
        buton4.textButon = "Iesire";
    }
    public stareAplicatie ApasaButon1(){
        switch(stareMeniu){
            case start -> {
                IncarcaStare(StariMeniu.SelectiiNivele);
                return stareAplicatie.meniu;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 1;
                return stareAplicatie.meniu;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                //LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                LoadingScreen.stare = stariLoading.LoadScreen;
                LoadingScreen.moveIn = true;
                return Application.StartLevel(nivelSelectat,4);
            }
            case SelectiiScoruri -> {
                nivelSelectat = 1;
                IncarcaStare(StariMeniu.Scoruri);
                return stareAplicatie.meniu;
            }
            case Scoruri -> {
                return stareAplicatie.meniu;
            }
        }
        System.out.println("Stare necunoscuta pe buton 1 !");
        return stareAplicatie.iesire;
    }
    public stareAplicatie ApasaButon2(){
        switch(stareMeniu){
            case start -> {//intrare credite
                //LoadingScreen.setTex(ResourceManager.get().getMeniu("Ajutor"));
                LoadingScreen.stare = stariLoading.CreditScreen;
                LoadingScreen.moveIn = true;
                return stareAplicatie.credite;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 2;
                return stareAplicatie.meniu;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                //LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                LoadingScreen.stare = stariLoading.LoadScreen;
                LoadingScreen.moveIn = true;
                return Application.StartLevel(nivelSelectat,5);
            }
            case SelectiiScoruri -> {
                nivelSelectat = 2;
                IncarcaStare(StariMeniu.Scoruri);
                return stareAplicatie.meniu;
            }
            case Scoruri -> {
                return stareAplicatie.meniu;
            }
        }
        System.out.println("Stare necunoscuta pe buton 2 !");
        return stareAplicatie.iesire;
    }
    public stareAplicatie ApasaButon3(){
        switch(stareMeniu){
            case start -> {
                //afisare scoruri
                IncarcaStare(StariMeniu.SelectiiScoruri);
                return stareAplicatie.meniu;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 3;
                return stareAplicatie.meniu;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                //LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                LoadingScreen.stare = stariLoading.LoadScreen;
                LoadingScreen.moveIn = true;
                return Application.StartLevel(nivelSelectat,6);
            }
            case SelectiiScoruri -> {
                nivelSelectat = 3;
                IncarcaStare(StariMeniu.Scoruri);
                return stareAplicatie.meniu;
            }
            case Scoruri -> {
                return stareAplicatie.meniu;
            }
        }
        System.out.println("Stare necunoscuta pe buton 3 !");
        return stareAplicatie.iesire;
    }
    public stareAplicatie ApasaButon4(){
        switch(stareMeniu){
            case start -> {
                //LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                LoadingScreen.stare = stariLoading.LoadScreen;
                LoadingScreen.moveIn = true;
                return stareAplicatie.iesire;
            }
            case SelectiiNivele, SelectiiDificultate, SelectiiScoruri, Scoruri -> {
                IncarcaStare(StariMeniu.start);
                return stareAplicatie.meniu;
            }
        }
        System.out.println("Stare necunoscuta pe buton 4 !");
        return stareAplicatie.iesire;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buton1.paintComponent(g);
        buton2.paintComponent(g);
        buton3.paintComponent(g);
        buton4.paintComponent(g);
    }
    public void IncarcaStare(StariMeniu stare){
        switch (stare){
            case start ->{
                stareMeniu = StariMeniu.start;
                buton1.textButon = "Start";
                buton2.textButon = " Ajutor si Credite";
                buton3.textButon = "Scoruri";
                buton4.textButon = "Iesire";
            }
            case SelectiiNivele -> {
                stareMeniu = StariMeniu.SelectiiNivele;
                buton1.textButon = "Corabia";
                buton2.textButon = "Insula";
                buton3.textButon = "Templul";
                buton4.textButon = "Inapoi";
            }
            case SelectiiDificultate -> {
                stareMeniu = StariMeniu.SelectiiDificultate;
                buton1.textButon = "Usor";
                buton2.textButon = "Mediu";
                buton3.textButon = "Greu";
                buton4.textButon = "Inapoi";
            }
            case SelectiiScoruri -> {
                stareMeniu = StariMeniu.SelectiiScoruri;
                buton1.textButon = "Corabia";
                buton2.textButon = "Insula";
                buton3.textButon = "Templul";
                buton4.textButon = "Inapoi";
            }
            case Scoruri -> {
                stareMeniu = StariMeniu.Scoruri;
                buton1.textButon = Scoruri.get().getnumeScor(nivelSelectat-1,0) + ": "+ Scoruri.get().getValoareScor(nivelSelectat-1,0);
                buton2.textButon = Scoruri.get().getnumeScor(nivelSelectat-1,1) + ": "+ Scoruri.get().getValoareScor(nivelSelectat-1,1);
                buton3.textButon = Scoruri.get().getnumeScor(nivelSelectat-1,2) + ": "+ Scoruri.get().getValoareScor(nivelSelectat-1,2);
                buton4.textButon = "Inapoi";
            }
        }
    }
}
