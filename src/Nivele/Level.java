package Nivele;

import Backbone.stareAplicatie;
import Manageri.MouseManager;
import Manageri.ResourceManager;
import Manageri.SoundManager;
import Meniuri.LoadingScreen;
import Meniuri.stariLoading;
import Obiecte.*;
import Proiectile.Proiectil;
import Proiectile.ProiectilBani;
import Proiectile.ProiectilBila;
import Proiectile.ProiectilFoc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Level extends JPanel {
    public String FrameTime = "Frame: 0";
    public boolean firstPaint;
    protected static int rezolutieX, rezolutieY;
    protected final Textura fundal, cursorPrincipal, cursorSecundar, TexScor;
    protected static List<Proiectil> listaProiectile;
    protected stareAplicatie scena = stareAplicatie.nivel;
    protected int scor = 0, nrBileMinim = 5, nrbileWaveNou = 15;
    protected final Tun tunar;
    protected final int targetScreenX = 1920, targetScreenY = 1080;
    protected GameObject[] traseuBile;
    protected final Sir sirBile;
    protected Font fontScor;
    protected final LinkedList<GameObject> pozitiiProiectile, pozitiiBile;
    protected final GameObject pozitieTun, pozitieCursor;
    protected final ResourceManager resurse = ResourceManager.get();
    protected final int dificultate;
    private int cooldownSwap = 0;
    public static int numarNivel;
    public Level(int Width, int Height, int dificultate) {
        firstPaint = true;
        this.setLayout(null);
        this.dificultate = dificultate;
        pozitiiBile = new LinkedList<>();
        pozitiiProiectile = new LinkedList<>();
        pozitieTun = new GameObject();
        pozitieCursor = new GameObject();
        rezolutieX = Width;
        rezolutieY = Height;
        ResourceManager.get();
        AlocareTraseuBile();
        fontScor = new Font("BlackPearl", Font.PLAIN, 25);
        tunar = new Tun(resurse.getTunJos().GetTex(), resurse.getTunSus().GetTex(), MouseManager.mousex, 0, 270, 20);
        fundal = new Textura(resurse.getFundal(1).GetTex(), 0, 0, 0);
        cursorPrincipal = new Textura(resurse.getTexturaCursorPrincipal().GetTex(),0,0,270);
        cursorSecundar = new Textura(resurse.getTexturaCursorSecundar().GetTex(), 0,0,270);
        TexScor = new Textura(resurse.getMeniu("Scor").GetTex(), 100,50, 0);
        listaProiectile = new ArrayList<>();
        sirBile = new Sir(traseuBile, 10, 1, 0.5f,0.2f,2500,700,3075);
        tunar.resizeTun( rezolutieX * tunar.GetTex().getWidth() / targetScreenX,rezolutieY * tunar.GetTex().getHeight() / targetScreenY);
        fundal.SetCoordX((float) rezolutieX / 2);
        fundal.SetCoordY((float) rezolutieY / 2);

    }
    protected void repaintBackground(int x, int y, int marimeX, int marimeY, Graphics g){
        if(x+marimeX > rezolutieX){
            marimeX = rezolutieX-x;
        }
        if(x<0){
            marimeX += x;
            x = 0;
        }
        if(y+marimeY > rezolutieY){
            marimeY = rezolutieY-y;
        }
        if(y<0){
            marimeY += y;
            y = 0;
        }
        if(marimeX > 0 && marimeY > 0){
            g.drawImage(fundal.GetTex().getSubimage(x,y,marimeX,marimeY),x,y,null);
        }
    }

    public void onStart(){
        tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) resurse.getBilaRandom(dificultate, numarNivel), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) resurse.getBilaRandom(dificultate, numarNivel), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
    };

    protected void salvarePozitii(){
        pozitieTun.Copiaza(tunar);
        pozitieCursor.Copiaza(cursorPrincipal);
        GameObject aux = new GameObject();
        for(Proiectil proiectil :listaProiectile){
            aux.Copiaza(proiectil);
            pozitiiProiectile.add(new GameObject().Copiaza(aux));
        }
        for(Bila bila : sirBile.getListaBile()){
            aux.Copiaza(bila);
            pozitiiBile.add(new GameObject().Copiaza(aux));
        }
    }

    public stareAplicatie Actualizare() {
        scena = stareAplicatie.nivel;
        salvarePozitii();
        //actualizari clickuri
        if (MouseManager.clickStanga) {
            if(tunar.isGataDeTras()){
                listaProiectile.add(tunar.GetProiectilIncarcat());
                tunar.Trage();
            }
        }
        if (MouseManager.middleMouse) {
            scena = stareAplicatie.meniu;
            LoadingScreen.moveIn = true;
        }
        if (MouseManager.clickDreapta) {
            if(tunar.isGataDeTras() && cooldownSwap == 0){
                tunar.SchimbaOrdineProiectile();
                SoundManager.playSound("src/resources/sunete/bullet_swap.wav", -10, false);
                cooldownSwap = 10;
            }
        }
        if(cooldownSwap !=0){
            cooldownSwap--;
        }
        //actualizari proiectile
        int index = 0;
        while (index < listaProiectile.size()) {
            Proiectil proiectil = listaProiectile.get(index);
            proiectil.UpdateProiectil();
            Bila aux = sirBile.TestColiziune(proiectil);
            if (aux != null && (aux.canInsertRight || aux.canInsertLeft)) {
                proiectil.HitSir(sirBile);
            }
            if(proiectil.getClass() == ProiectilBani.class && tunar.Coliziune(proiectil)){
                SoundManager.playSound("src/resources/sunete/catch_coin.wav",-20,false);
                scor+=2;
                proiectil.shouldDissapear = true;
            }
            if (proiectil.shouldDissapear) {
                listaProiectile.remove(proiectil);
                index--;
            }
            index++;
        }
        //actualizari cursor
        if(tunar.GetProiectilIncarcat() != null){
            cursorPrincipal.SetTexRaw(resurse.getTexturaCursorPrincipal(tunar.GetProiectilIncarcat(), numarNivel).GetTex());
        }
        if(tunar.GetProiectilRezerva() != null){
            cursorSecundar.SetTexRaw(resurse.getTexturaCursorSecundar(tunar.GetProiectilRezerva(), numarNivel).GetTex());
        }

        tunar.UpdateTun(MouseManager.mousex, MouseManager.mousey);
        //actualizari tunar
        Spritesheet bilaDinSir = sirBile.getTexturaBilaRandom();
        if(tunar.isGataDeTras() && (tunar.GetProiectilIncarcat() == null)){
            if(bilaDinSir != null){
                tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
            }
            else{
                tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) resurse.getBilaRandom(dificultate, numarNivel), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
            }
            SoundManager.playSound("src/resources/sunete/bullet_reload.wav", -10, false);
            if(Math.random()>0.95){
                tunar.SetProiectilRezerva(new ProiectilFoc(tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 100, 32));
            }
            if(Math.random()>0.85){
                tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet)ResourceManager.get().getTexturaBila("curcubeu", numarNivel),tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
            }
        }
        if(tunar.isGataDeTras() && bilaDinSir != null && !sirBile.isCuloareInSir(tunar.GetProiectilIncarcat().GetTex()) && (tunar.GetProiectilIncarcat().getClass() == ProiectilBila.class)){
            bilaDinSir = sirBile.getTexturaBilaRandom();
            tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        }
        scor += sirBile.Update();
        if(sirBile.marime() < nrBileMinim && !sirBile.lost){
            sirBile.WaveNou(nrbileWaveNou, dificultate);// =)
            nrBileMinim++;
            nrbileWaveNou += 5;
        }
        if(sirBile.marime() == 0){
            //LoadingScreen.setTex(ResourceManager.get().getMeniu("Hartie"));
            LoadingScreen.stare = stariLoading.EndLevelScreen;
            LoadingScreen.moveIn = true;
            scena = stareAplicatie.endlevel;
        }
        return scena;//cu asta poti returna ce scena sa se incarce
    }

//linia asta de cod a fost scrisa de un domn anonim

    public void paintComponent(Graphics g) {
        //background
        if(firstPaint){
            firstPaint = false;
            fundal.paintComponent(g);
        }
        //fundal.paintComponent(g);

        g.setFont(fontScor);
        g.setColor(Color.black);
        if(!pozitiiProiectile.isEmpty()){
            for (GameObject proiectil : pozitiiProiectile) {
                repaintBackground((int) (proiectil.GetCoordX()-(resurse.getMarimeBilaSparta()/2)-1),(int) (proiectil.GetCoordY()-(resurse.getMarimeBilaSparta()/2)-1),((resurse.getMarimeBilaSparta())+2), ((resurse.getMarimeBilaSparta())+2), g);
            }
        }
        if(!pozitiiBile.isEmpty()){
            for (GameObject bila : pozitiiBile) {
                repaintBackground((int) (bila.GetCoordX()-((Spritesheet)resurse.getBilaRandom(dificultate, numarNivel)).GetMarimeSpriteX())-1,(int) (bila.GetCoordY()-((Spritesheet)resurse.getBilaRandom(dificultate, numarNivel)).GetMarimeSpriteY())-1,((Spritesheet)resurse.getBilaRandom(dificultate, numarNivel)).GetMarimeSpriteX()*2+2, ((Spritesheet)resurse.getBilaRandom(dificultate, numarNivel)).GetMarimeSpriteY()*2+2, g);
            }
        }
        pozitiiBile.clear();
        pozitiiProiectile.clear();
        repaintBackground((int) (pozitieCursor.GetCoordX()-cursorPrincipal.GetMarimeTexX()/2)-1, (int) (pozitieCursor.GetCoordY()-cursorPrincipal.GetMarimeTexY()/2)-1, cursorPrincipal.GetMarimeTexX()+2, cursorPrincipal.GetMarimeTexY()+2, g);
        //tunul
        repaintBackground((int) (pozitieTun.GetCoordX()-tunar.GetMarimeTexX()/2)-1, (int) (pozitieTun.GetCoordY()-tunar.GetMarimeTexY()/2)-1, tunar.GetMarimeTexX()+2, tunar.GetMarimeTexY()+2, g);
        Spritesheet tunjos = tunar.GetTexJos();
        repaintBackground((int)(pozitieTun.GetCoordX() -tunar.GetMarimeTexX()/2 - 1 - Math.cos(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexX()/2.25),(int)(pozitieTun.GetCoordY() -tunar.GetMarimeTexY()/2 - 1- Math.sin(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexY()/2.25), tunjos.GetMarimeSpriteX() + 2,tunjos.GetMarimeSpriteY() + 2,g);
        //g.drawRect((int)(pozitieTun.GetCoordX() -tunar.marime_x/2 - Math.cos(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexX()/2.25), (int)(pozitieTun.GetCoordY() -tunar.marime_y/2 - Math.sin(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexY()/2.25), tunjos.GetMarimeSpriteX(), tunjos.GetMarimeTexY());
        //textul de debug
        //repaintBackground(0,0, 200, 200, g);
        //textul de scor
        repaintBackground(rezolutieX/2,10, 120, 30, g);
        //restul
        sirBile.paintComponent(g);
        MiddlePaint(g);
        tunar.paintComponent(g);
        LinkedList<Proiectil> listaRandare  = new LinkedList<>(listaProiectile);
        for (Proiectil proiectil : listaRandare) {
            proiectil.paintComponent(g);
        }
        cursorPrincipal.paintComponent(g);
        /*if(tunar.GetProiectilIncarcat() != null && tunar.GetProiectilIncarcat().getClass() == ProiectilFoc.class){
            g.setColor(Color.red);
            g.drawOval((int) cursorPrincipal.GetCoordX(), (int) cursorPrincipal.GetCoordY(), (int) ((ProiectilFoc)tunar.GetProiectilIncarcat()).marimeZonaFoc, (int) ((ProiectilFoc)tunar.GetProiectilIncarcat()).marimeZonaFoc);
            g.setColor(Color.BLACK);
        }*/
        cursorSecundar.paintComponent(g);
        TexScor.paintComponent(g);
        //g.drawString(FrameTime, 10, 20);
        //g.drawString("Proiectile: " + listaProiectile.size(), 10, 40);
        //g.drawString("Bile: " + sirBile.marime(), 10, 65);
        //g.drawString("Wave leaderi: " + sirBile.nrWaveLeaderi,10,90);
        //g.drawString("Sir leaderi: "+ sirBile.nrSirLeaderi,10,115);
        //g.drawString("Animati: "+ sirBile.nrAnimate,10,140);
        //g.drawString("Instabile: "+sirBile.nrInstabile,10,165);
        g.drawString("Scor: "+scor, TexScor.CenterX()+30, TexScor.CenterY()+80);
    }

    public static void AdaugaEfect(Proiectil efect){
        listaProiectile.add(listaProiectile.size(),efect);
    }

    protected abstract void AlocareTraseuBile();
    protected abstract void MiddlePaint(Graphics g);

}