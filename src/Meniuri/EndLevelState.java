package Meniuri;

import Backbone.Scoruri;
import Manageri.ResourceManager;

import java.awt.*;

public class EndLevelState implements StateLoadingScreen{
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.setFont(ResourceManager.get().font.deriveFont(65f));
        LoadingScreen.setTex(ResourceManager.get().getMeniu("Hartie"));
        LoadingScreen.getTex().paintComponent(g);
        g.drawString("Scorul tau: "+ Scoruri.get().getScorSalvat(),LoadingScreen.getTex().GetMarimeTexX() / 4, LoadingScreen.getTex().GetMarimeTexY() / 4 + LoadingScreen.getTex().CenterY());
        if(Scoruri.get().newHighScore) {
            g.drawString("Introdu numele: ", LoadingScreen.getTex().GetMarimeTexX() / 4, LoadingScreen.getTex().GetMarimeTexY() / 3 + LoadingScreen.getTex().CenterY());
            g.drawString(Scoruri.get().numeDeAfisat(), LoadingScreen.getTex().GetMarimeTexX() / 4, LoadingScreen.getTex().GetMarimeTexY() / 2 + LoadingScreen.getTex().CenterY());
        }
        else{
            if(LoadingScreen.isFinished()){
                LoadingScreen.butonInapoi.paintComponent(g);
            }
        }
    }
}
