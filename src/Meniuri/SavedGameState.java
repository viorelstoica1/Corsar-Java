package Meniuri;
import Manageri.ResourceManager;
import java.awt.*;

public class SavedGameState implements StateLoadingScreen {

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.setFont(ResourceManager.get().font.deriveFont(65f));
        LoadingScreen.setTex(ResourceManager.get().getMeniu("Hartie"));
        LoadingScreen.getTex().paintComponent(g);
        g.drawString("Joc in desfasurare", LoadingScreen.getTex().GetMarimeTexX() / 8, LoadingScreen.getTex().GetMarimeTexY() / 4 + LoadingScreen.getTex().CenterY());
        g.drawString("Continuati?", LoadingScreen.getTex().GetMarimeTexX() / 8, LoadingScreen.getTex().GetMarimeTexY() * 3 / 8 + LoadingScreen.getTex().CenterY());
        if(LoadingScreen.isFinished()){
            LoadingScreen.butonDa.paintComponent(g);
            LoadingScreen.butonNu.paintComponent(g);
        }
    }
}
