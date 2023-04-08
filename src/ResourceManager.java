import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager {
    private HashMap<String,Textura> texturi;
    private HashMap<String,Integer> numarTexturi;
    public ResourceManager(){
        try {
            BufferedImage tex = ImageIO.read(new File("src/resources/BILE_GALBENE"));
            texturi.put("galben",new Spritesheet(tex,60,10,0,0,0));
            numarTexturi.put("galben",0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Textura get(String numeTextura){
        numarTexturi.put(numeTextura,(numarTexturi.get(numeTextura))+1);
        return texturi.get(numeTextura);
    }
}
