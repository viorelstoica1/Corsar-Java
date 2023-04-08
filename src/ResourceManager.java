import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final HashMap<String,Textura> texturi;
    private final ArrayList<String> nume;
    public ResourceManager(){
        try {
            texturi = new HashMap<>();
            nume = new ArrayList<String>();
            BufferedImage tex = ImageIO.read(new File("src/resources/BILE_GALBENE.png"));
            texturi.put("galben",new Spritesheet(tex,60,10,0,0,0));
            nume.add("galben");
            tex = ImageIO.read(new File("src/resources/BILE_ALBASTRE.png"));
            texturi.put("albastru",new Spritesheet(tex,60,10,0,0,0));
            nume.add("albastru");
            tex = ImageIO.read(new File("src/resources/BILE_ALBE.png"));
            texturi.put("alb",new Spritesheet(tex,60,10,0,0,0));
            nume.add("alb");
            tex = ImageIO.read(new File("src/resources/BILE_MOV.png"));
            texturi.put("mov",new Spritesheet(tex,60,10,0,0,0));
            nume.add("mov");
            tex = ImageIO.read(new File("src/resources/BILE_ROSII.png"));
            texturi.put("rosu",new Spritesheet(tex,60,10,0,0,0));
            nume.add("rosu");
            tex = ImageIO.read(new File("src/resources/BILE_VERZI.png"));
            texturi.put("verde",new Spritesheet(tex,60,10,0,0,0));
            nume.add("verde");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Textura getTextura(String numeTextura){
        return texturi.get(numeTextura);
    }
    public Textura getBilaRandom(){
        return texturi.get(nume.get((int)(Math.random() * nume.size())));
    }
}
