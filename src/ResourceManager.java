import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ResourceManager {
    private static ResourceManager instanta = null;
    private final Vector<Textura> texturiBile, texturiCursorPrincipal, texturiCursorSecundar, texturiBileSparte;
    private final Textura fundal1, fundal2, fundal3, loadscreen, fundalMeniu, tunSus, tunJos;
    private final Textura selectiiMeniu, capcana1, capcana2, scor, ajutorMeniu, lupa0, lupa1, lupa2, lupa3;
    private final Vector<String> nume;
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
            texturiBile = new Vector<>();
            texturiCursorPrincipal = new Vector<>();
            texturiCursorSecundar = new Vector<>();
            texturiBileSparte = new Vector<>();
            nume = new Vector<>();
            //galben
            BufferedImage tex = ImageIO.read(new File("src/resources/bile/BILE_GALBENE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_galben.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_galben.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Galben_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("galben");
            //albastru
            tex = ImageIO.read(new File("src/resources/bile/BILE_ALBASTRE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_albastru.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_albastru.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Albastru_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("albastru");
            //alb
            tex = ImageIO.read(new File("src/resources/bile/BILE_ALBE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_alb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_alb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Alb_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("alb");
            //mov
            tex = ImageIO.read(new File("src/resources/bile/BILE_MOV.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_mov.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_mov.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Mov_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("mov");
            //rosu
            tex = ImageIO.read(new File("src/resources/bile/BILE_ROSII.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("rosu");
            //verde
            tex = ImageIO.read(new File("src/resources/bile/BILE_VERZI.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Verde_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("verde");
            //wildball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rgb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rgb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Alb_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("curcubeu");
            //fireball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_foc.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_foc.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiBileSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("fire");

            //fundaluri
            tex = ImageIO.read(new File(("src/resources/fundal1.png")));
            fundal1 = new Textura(tex,0,0,0).resize(1536,864);
            tex = ImageIO.read(new File("src/resources/LoadScreen.png"));
            loadscreen = new Textura(tex, 0 , 0, 0).resize(1536, 864);
            tex = ImageIO.read(new File(("src/resources/fundal2.png")));
            fundal2 = new Textura(tex,0,0,0);
            tex = ImageIO.read(new File(("src/resources/fundal3.png")));
            fundal3 = new Textura(tex,0,0,0).resize(1536,864);
            //Meniuri
            tex = ImageIO.read(new File(("src/resources/FundalMeniu.png")));
            fundalMeniu = new Textura(tex,0,0,0).resize(1536,864);
            tex = ImageIO.read(new File(("src/resources/SelectiiMeniu.png")));
            selectiiMeniu = new Textura(tex,0,0,0).resize(500,500);
            tex = ImageIO.read(new File(("src/resources/EcranAjutor.png")));
            ajutorMeniu = new Textura(tex,0,0,0)/*.resize(1536,864)*/;
            //tun
            tex = ImageIO.read(new File(("src/resources/Cannon_no_shade.png")));
            tunSus = new Textura(tex,0,0,0);
            tex = ImageIO.read(new File(("src/resources/Cannon_explosion-sheet.png")));
            tunJos = new Spritesheet(tex,5,5,0,0,0);
            //capcane
            tex = ImageIO.read(new File(("src/resources/capcana1.png")));
            capcana1 = new Textura(tex,0,0,0)/*.resize(500,500)*/;
            tex = ImageIO.read(new File(("src/resources/capcana2.png")));
            capcana2 = new Textura(tex,0,0,0)/*.resize(500,500)*/;
            //scor
            tex = ImageIO.read(new File(("src/resources/ScanduraScor.png")));
            scor = new Textura(tex,0,0,0).resize(200,100);
            //misc
            tex = ImageIO.read(new File(("src/resources/Lupa1.png")));
            lupa1 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/Lupa2.png")));
            lupa2 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/Lupa3.png")));
            lupa3 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
            tex = ImageIO.read(new File(("src/resources/Lupa0.png")));
            lupa0 = new Textura(tex,0,0,0)/*.resize(200,100)*/;
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public Textura getBilaRandom(int numarBile){
        return texturiBile.get((int) (Math.random()*numarBile));
    }

    public  Textura getTexturaCursorPrincipal(Textura bila){
        for(Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiCursorPrincipal.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public Textura getTexturaCursorPrincipal(){
        return texturiCursorPrincipal.get(0);
    }
    public Textura getTexturaCursorSecundar(){
        return texturiCursorSecundar.get(0);
    }

    public Textura getTexturaBila(String numeTextura){
        return texturiBile.get(nume.indexOf(numeTextura));
    }
    public Textura getTexturaCursorSecundar(Textura bila){
        for(Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiCursorSecundar.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public Textura getTexturaBilaSparta(Textura bila){
        for (Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiBileSparte.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public int getMarimeBila(){
        return ((Spritesheet)texturiBile.get(0)).GetMarimeSpriteX();
    }
    public int getMarimeBilaSparta(){
        return texturiBileSparte.get(0).marime_x;
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
}
