package Manageri;

import Backbone.Application;
import Obiecte.Spritesheet;
import Obiecte.Textura;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ResourceManager {
    private static ResourceManager instanta = null;
    private Vector<Textura> texturiBile1, texturiBile2, texturiBile3, texturiCursorPrincipal, texturiCursorSecundar, texturiBileSparte;
    private Textura fundal1, fundal2, fundal3, loadscreen, fundalMeniu, tunSus, tunJos;
    private Textura selectiiMeniu, capcana1, capcana2, scor, ajutorMeniu, lupa0, lupa1, lupa2, lupa3, hartieMeniu, bani, butoi, bustean, sarpe, frunza1, frunza2, gauraNava, raci;
    private Vector<String> nume;
    public Font font;
    private ResourceManager(){
        try {
            try {
                GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
                System.out.println(ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Blackpearl-vPxA.ttf"))));
            } catch (IOException|FontFormatException e) {
                e.printStackTrace();
            }
            font = new Font("BlackPearl", Font.PLAIN, 25);
            texturiBile1 = new Vector<>();
            texturiBile2 = new Vector<>();
            texturiBile3 = new Vector<>();
            texturiCursorPrincipal = new Vector<>();
            texturiCursorSecundar = new Vector<>();
            texturiBileSparte = new Vector<>();
            nume = new Vector<>();
            //galben
            BufferedImage tex = ImageIO.read(new File("src/resources/bile/bile_galbene1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_galbene2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_galbene3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_galben.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_galben.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Galben_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("galben");
            //albastru
            tex = ImageIO.read(new File("src/resources/bile/bile_albastre1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_albastre2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_albastre3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_albastru.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_albastru.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Albastru_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("albastru");
            //alb
            tex = ImageIO.read(new File("src/resources/bile/bile_albe1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_albe2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_albe3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_alb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_alb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Alb_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("alb");
            //mov
            tex = ImageIO.read(new File("src/resources/bile/bile_mov1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_mov2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_mov3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_mov.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_mov.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Mov_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("mov");
            //rosu
            tex = ImageIO.read(new File("src/resources/bile/bile_rosii1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_rosii2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_rosii3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("rosu");
            //verde
            tex = ImageIO.read(new File("src/resources/bile/bile_verzi1.png"));
            texturiBile1.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_verzi2.png"));
            texturiBile2.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File("src/resources/bile/bile_verzi3.png"));
            texturiBile3.add(new Spritesheet(tex,32,6,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Verde_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("verde");
            //wildball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile1.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile2.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile3.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rgb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rgb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Alb_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("curcubeu");
            //fireball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile1.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile2.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile3.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_foc.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_foc.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("fire");

            //fundaluri
            tex = ImageIO.read(new File(("src/resources/fundaluri/fundal1.png")));
            fundal1 = new Textura(tex,0,0,0).resize(1536,864);
            tex = ImageIO.read(new File("src/resources/meniuri/LoadScreen.png"));
            loadscreen = new Textura(tex, 0 , 0, 0).resize(1536, 864);
            tex = ImageIO.read(new File(("src/resources/fundaluri/fundal2.png")));
            fundal2 = new Textura(tex,0,0,0).resize(1536, 864);
            tex = ImageIO.read(new File(("src/resources/fundaluri/fundal3.png")));
            fundal3 = new Textura(tex,0,0,0).resize(1536,864);
            //Meniuri
            tex = ImageIO.read(new File(("src/resources/meniuri/FundalMeniu.png")));
            fundalMeniu = new Textura(tex,0,0,0).resize(1536,864);
            tex = ImageIO.read(new File(("src/resources/meniuri/SelectiiMeniu.png")));
            selectiiMeniu = new Textura(tex,0,0,0)/*.resize(500,500)*/;
            tex = ImageIO.read(new File(("src/resources/meniuri/EcranAjutor.png")));
            ajutorMeniu = new Textura(tex,0,0,0).resize(1536,864);
            tex = ImageIO.read(new File(("src/resources/meniuri/EcranHartie.png")));
            hartieMeniu = new Textura(tex,0,0,0).resize(1536,864);
            //tun
            tex = ImageIO.read(new File(("src/resources/Cannon_no_shade.png")));
            tunSus = new Textura(tex,0,0,0);
            tex = ImageIO.read(new File(("src/resources/Cannon_explosion-sheet.png")));
            tunJos = new Spritesheet(tex,5,5,0,0,0);
            //capcane
            tex = ImageIO.read(new File(("src/resources/fundaluri/capcana1.png")));
            capcana1 = new Textura(tex,0,0,0)/*.resize(500,500)*/;
            tex = ImageIO.read(new File(("src/resources/fundaluri/capcana2.png")));
            capcana2 = new Textura(tex,0,0,0)/*.resize(500,500)*/;
            //scor
            tex = ImageIO.read(new File(("src/resources/meniuri/ScanduraScor.png")));
            scor = new Textura(tex,0,0,0).resize(200,100);
            //misc
            tex = ImageIO.read(new File(("src/resources/meniuri/Lupa1.png")));
            lupa1 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/meniuri/Lupa2.png")));
            lupa2 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/meniuri/Lupa3.png")));
            lupa3 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/meniuri/Lupa0.png")));
            lupa0 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/Bani.png")));
            bani = new Spritesheet(tex,9,9,0,0,0).resize(324,36);
            tex = ImageIO.read(new File(("src/resources/Butoi.png")));
            butoi = new Textura(tex,0,0,0).resize(73,89);
            tex = ImageIO.read(new File(("src/resources/fundaluri/Bustean.png")));
            bustean = new Spritesheet(tex,9,9,0,0,0).resize(170,100);
            tex = ImageIO.read(new File(("src/resources/fundaluri/CapSarpe.png")));
            sarpe = new Textura(tex,0,0,0).resize(120,120);
            tex = ImageIO.read(new File(("src/resources/fundaluri/FrunzeStanga.png")));
            frunza1 = new Textura(tex,0,0,0).resize(354,180);
            tex = ImageIO.read(new File(("src/resources/fundaluri/FrunzeDreapta.png")));
            frunza2 = new Textura(tex,0,0,0)/*.resize(120,120)*/;
            tex = ImageIO.read(new File(("src/resources/fundaluri/gauraNava.png")));
            gauraNava = new Textura(tex,0,0,0)/*.resize(120,120)*/;
            tex = ImageIO.read(new File(("src/resources/fundaluri/CrabRave.png")));
            raci = new Spritesheet(tex,4,4,0,0,0).resize(256,64);
        } catch (IOException e) {
            System.out.println("Nu pot incarca toate texturile!");
            Application.CloseGame();
            //throw new RuntimeException(e);
        }
    }
    public static synchronized ResourceManager get(){
        if(instanta == null){
            instanta = new ResourceManager();
        }
        return instanta;
    }
    public Textura getFundal(int nrNivel){
        switch (nrNivel) {
            case 1 -> {
                return fundal1;
            }
            case 2 -> {
                return fundal2;
            }
            case 3 -> {
                return fundal3;
            }
            default -> {
                return null;
            }
        }
    }
    public Textura getTunSus(){
        return tunSus;
    }
    public Textura getTunJos(){
        return tunJos;
    }
    public Textura getBilaRandom(int numarBile, int nivel){
        switch(nivel){
            case 1 -> {
                return texturiBile1.get((int) (Math.random()*numarBile));}
            case 2 -> {
                return texturiBile2.get((int) (Math.random()*numarBile));}
            case 3 -> {
                return texturiBile3.get((int) (Math.random()*numarBile));}
        }
        System.out.println("Nu pot returna bila random pentru nivelul "+nivel);
        return null;
    }
    public String nameOf(Textura bila){
        for(Textura textura: texturiBile1){
            if(textura.GetTex() == bila.GetTex()){
                return nume.get(texturiBile1.indexOf(textura));
            }
        }
        for(Textura textura: texturiBile2){
            if(textura.GetTex() == bila.GetTex()){
                return nume.get(texturiBile2.indexOf(textura));
            }
        }
        for(Textura textura: texturiBile3){
            if(textura.GetTex() == bila.GetTex()){
                return nume.get(texturiBile3.indexOf(textura));
            }
        }
        System.out.println("Nu am gasit numele!");
        return null;
    }
    public Textura getTexturaCursorPrincipal(Textura bila, int nivel){
        switch (nivel){
            case 1 -> {
                for(Textura textura: texturiBile1){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorPrincipal.get(texturiBile1.indexOf(textura));
                    }
                }
            }
            case 2 -> {
                for(Textura textura: texturiBile2){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorPrincipal.get(texturiBile2.indexOf(textura));
                    }
                }
            }
            case 3 -> {
                for(Textura textura: texturiBile3){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorPrincipal.get(texturiBile3.indexOf(textura));
                    }
                }
            }
        }
        System.out.println("Nu pot returna cursorul principal pentru nivelul "+nivel);
        return null;
    }
    public Textura getTexturaCursorPrincipal(){
        return texturiCursorPrincipal.get(0);
    }
    public Textura getTexturaCursorSecundar(){
        return texturiCursorSecundar.get(0);
    }

    public Textura getTexturaBila(String numeTextura, int nivel){
        switch (nivel){
            case 1 -> {
                return texturiBile1.get(nume.indexOf(numeTextura));
            }
            case 2 -> {
                return texturiBile2.get(nume.indexOf(numeTextura));
            }
            case 3 -> {
                return texturiBile3.get(nume.indexOf(numeTextura));
            }
        }
        System.out.println("Nu pot returna bila specifica pentru nivelul "+nivel);
        return null;
    }
    public Textura getTexturaCursorSecundar(Textura bila, int nivel){
        switch (nivel){
            case 1 -> {
                for(Textura textura: texturiBile1){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorSecundar.get(texturiBile1.indexOf(textura));
                    }
                }
            }
            case 2 -> {
                for(Textura textura: texturiBile2){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorSecundar.get(texturiBile2.indexOf(textura));
                    }
                }
            }
            case 3 -> {
                for(Textura textura: texturiBile3){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiCursorSecundar.get(texturiBile3.indexOf(textura));
                    }
                }
            }
        }
        System.out.println("Nu pot returna cursorul secundar pentru nivelul "+nivel);
        return null;
    }
    public Textura getTexturaBilaSparta(String culoare){
        return texturiBileSparte.get(nume.indexOf(culoare));
    }
    public Textura getTexturaBilaSparta(Textura bila, int nivel){
        switch (nivel){
            case 1 -> {
                for(Textura textura: texturiBile1){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiBileSparte.get(texturiBile1.indexOf(textura));
                    }
                }
            }
            case 2 -> {
                for(Textura textura: texturiBile2){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiBileSparte.get(texturiBile2.indexOf(textura));
                    }
                }
            }
            case 3 -> {
                for(Textura textura: texturiBile3){
                    if(textura.GetTex() == bila.GetTex()){
                        return texturiBileSparte.get(texturiBile3.indexOf(textura));
                    }
                }
            }
        }
        System.out.println("Nu pot returna bila sparta pentru nivelul "+nivel);
        return null;
    }
    public int getMarimeBila(){
        return ((Spritesheet) texturiBile1.get(0)).GetMarimeSpriteX();
    }
    public int getMarimeBilaSparta(){
        return ((Spritesheet)texturiBileSparte.get(0)).GetMarimeSpriteX();
    }
    public Textura getLoadscreen(){
        return loadscreen;
    }
    public Textura getMeniu(String s){
        switch(s){
            case "Fundal" ->{
                return fundalMeniu;
            }
            case "Selectii" ->{
                return selectiiMeniu;
            }
            case "Scor" -> {
                return scor;
            }
            case "Ajutor" -> {
                return ajutorMeniu;
            }
            case "Lupa1" ->  {
                return lupa1;
            }
            case "Lupa2" -> {
                return lupa2;
            }
            case "Lupa3" -> {
                return lupa3;
            }
            case "Lupa0" -> {
                return lupa0;
            }
            case "Hartie" -> {
                return hartieMeniu;
            }
        }
        System.out.println("Nume textura meniu nerecunoscut!");
        return null;
    }
    public Textura getCapcana(int nrCapcana){
        switch (nrCapcana){
            case 1 ->{
                return capcana1;
            }
            case 2 -> {
                return capcana2;
            }
        }
        System.out.println("Numar textura capcana nerecunoscut!");
        return null;
    }
    public Textura getMisc(String nume){
        switch(nume){
            case "bani" ->{
                return bani;
            }
            case "butoi" ->{
                return butoi;
            }
            case "bustean" -> {
                return bustean;
            }
            case "sarpe" -> {
                return sarpe;
            }
            case "frunzaStanga" ->{
                return frunza1;
            }
            case "frunzaDreapta" -> {
                return frunza2;
            }
            case "gauraNava" -> {
                return gauraNava;
            }
            case "rac" -> {
                return raci;
            }
        }
        System.out.println("Nume misc necunoscut!");
        return null;
    }
}
