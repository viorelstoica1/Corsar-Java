package Meniuri;

import Manageri.ResourceManager;

import java.awt.*;

public class CreditState implements StateLoadingScreen{
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.setFont(ResourceManager.get().font.deriveFont(65f));
        LoadingScreen.setTex(ResourceManager.get().getMeniu("Ajutor"));
        LoadingScreen.getTex().paintComponent(g);
    }
}
