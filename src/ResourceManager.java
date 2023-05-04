import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ResourceManager {
    private static Vector<Textura> texturiBile = null;
    private static Vector<Textura> texturiCursorPrincipal = null;
    private static Vector<Textura> texturiCursorSecundar = null;
    private static Vector<Textura> texturiSparte = null;
    private static Textura fundal1, fundal2, fundal3, tunSus, tunJos;
    private static Vector<String> nume = null;
    public static void initResourceManager(){
        try {
            texturiBile = new Vector<>();
            texturiCursorPrincipal = new Vector<>();
            texturiCursorSecundar = new Vector<>();
            texturiSparte = new Vector<>();
            nume = new Vector<>();
            //galben
            BufferedImage tex = ImageIO.read(new File("src/resources/bile/BILE_GALBENE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_galben.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_galben.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Galben_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("galben");
            //albastru
            tex = ImageIO.read(new File("src/resources/bile/BILE_ALBASTRE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_albastru.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_albastru.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Albastru_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("albastru");
            //alb
            tex = ImageIO.read(new File("src/resources/bile/BILE_ALBE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_alb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_alb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Alb_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("alb");
            //mov
            tex = ImageIO.read(new File("src/resources/bile/BILE_MOV.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_mov.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_mov.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Mov_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("mov");
            //rosu
            tex = ImageIO.read(new File("src/resources/bile/BILE_ROSII.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("rosu");
            //verde
            tex = ImageIO.read(new File("src/resources/bile/BILE_VERZI.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Verde_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("verde");
            //wildball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Verde_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("curcubeu");
            //fireball
            tex = ImageIO.read(new File("src/resources/bile/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/bile/Boom_Rosu_Sheet.png")));
            texturiSparte.add( new Spritesheet(tex,4,4,0,0,0).resize(360,72));
            nume.add("fire");

            //fundaluri
            tex = ImageIO.read(new File(("src/resources/fundal1.png")));
            fundal1 = new Textura(tex,0,0,0).resize(1536,864);
            /*tex = ImageIO.read(new File(("src/resources/fundal2.png")));
            fundal2 = new Textura(tex,0,0,0);
            tex = ImageIO.read(new File(("src/resources/fundal3.png")));
            fundal3 = new Textura(tex,0,0,0);*/
            //TODO fundal 2 si 3
            //tun
            tex = ImageIO.read(new File(("src/resources/Cannon_no_shade.png")));
            tunSus = new Textura(tex,0,0,0);
            tex = ImageIO.read(new File(("src/resources/Cannon_explosion-sheet.png")));
            tunJos = new Spritesheet(tex,5,5,0,0,0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Textura getFundal(int nrNivel){
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
    public static Textura getTunSus(){
        return tunSus;
    }
    public static Textura getTunJos(){
        return tunJos;
    }
    public static Textura getBilaRandom(){
        return texturiBile.get((int) (Math.random()*4));
    }

    public static Textura getTexturaCursorPrincipal(Textura bila){
        for(Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiCursorPrincipal.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public static Textura getTexturaCursorPrincipal(){
        return texturiCursorPrincipal.get(0);
    }
    public static Textura getTexturaCursorSecundar(){
        return texturiCursorSecundar.get(0);
    }

    public static Textura getTexturaBila(String nume){
        //System.out.println("textura "+ResourceManager.nume.indexOf(nume));
        return texturiBile.get(ResourceManager.nume.indexOf(nume));
    }
    public static Textura getTexturaCursorSecundar(Textura bila){
        for(Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiCursorSecundar.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public static Textura getTexturaBilaSparta(Textura bila){
        for (Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiSparte.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
    public static int getMarimeBila(){
        return ((Spritesheet)texturiBile.get(0)).GetMarimeSpriteX();
    }
    public static int getMarimeBilaSparta(){
        return texturiSparte.get(0).marime_x;
    }
}
