import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ResourceManager {
    private final Vector<Textura> texturiBile, texturiCursorPrincipal, texturiCursorSecundar;
    private final ArrayList<String> nume;
    public ResourceManager(){
        try {
            texturiBile = new Vector<>();
            texturiCursorPrincipal = new Vector<>();
            texturiCursorSecundar = new Vector<>();
            nume = new ArrayList<String>();
            //galben
            BufferedImage tex = ImageIO.read(new File("src/resources/BILE_GALBENE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_galben.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_galben.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("galben");
            //albastru
            tex = ImageIO.read(new File("src/resources/BILE_ALBASTRE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_albastru.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_albastru.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("albastru");
            //alb
            tex = ImageIO.read(new File("src/resources/BILE_ALBE.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_alb.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_alb.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("alb");
            //mov
            tex = ImageIO.read(new File("src/resources/BILE_MOV.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_mov.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_mov.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("mov");
            //rosu
            tex = ImageIO.read(new File("src/resources/BILE_ROSII.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("rosu");
            //verde
            tex = ImageIO.read(new File("src/resources/BILE_VERZI.png"));
            texturiBile.add(new Spritesheet(tex,60,10,0,0,0));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("verde");
            //wildball
            tex = ImageIO.read(new File("src/resources/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0, 0).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_verde.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_verde.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("curcubeu");
            //fireball
            tex = ImageIO.read(new File("src/resources/bile_curcubeu.png"));
            texturiBile.add(new Spritesheet(tex,16,6,6,0,0,0,16).resize(216,216));
            tex = ImageIO.read(new File(("src/resources/cursor/cursor_rosu.png")));
            texturiCursorPrincipal.add( new Textura(tex,0,0,0).resize(36,36));
            tex = ImageIO.read(new File(("src/resources/cursor/craniu_rosu.png")));
            texturiCursorSecundar.add( new Textura(tex,0,0,0).resize(36,36));
            nume.add("fire");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Textura getBilaRandom(){
        return texturiBile.get((int) (Math.random()*8/*nume.size()*/));
    }

    public Textura getTexturaCursorPrincipal(Textura bila){
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

    public Textura getTexturaCursorSecundar(Textura bila){
        for(Textura textura: texturiBile){
            if(textura.GetTex() == bila.GetTex()){
                return texturiCursorSecundar.get(texturiBile.indexOf(textura));
            }
        }
        return null;
    }
}
