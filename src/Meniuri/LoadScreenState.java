package Meniuri;

import Manageri.ResourceManager;

import java.awt.*;

public class LoadScreenState implements StateLoadingScreen{
    @Override
    public void paintComponent(Graphics g ) {
        LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
        LoadingScreen.getTex().paintComponent(g);
    }
}
