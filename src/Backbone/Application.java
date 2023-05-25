package Backbone;

import Manageri.*;
import Meniuri.LoadingScreen;
import Meniuri.Meniu;
import Meniuri.stariLoading;
import Nivele.Level;
import Nivele.Level1;
import Nivele.Level2;
import Nivele.Level3;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {
    private static JFrame ScheletAplicatie;//marginile aplicatiei
    private static Level Nivel = null;//display-ul propriu zis
    private static Meniu meniu;
    private static int screenWidth,screenHeight;
    private static double frameTime;
    public static LoadingScreen panouloading;
    public static boolean game_is_running = true;
    private static final Thread gameThread = new Thread(new Application());
    public static void main(String[] args) throws InterruptedException {
        BazaDateManager.CitireScoruri();
        SetariManager.get();
        Initializare();
        gameThread.start();
        gameThread.join();
        Inchidere();
    }
    private static synchronized void Inchidere(){
        ScheletAplicatie.setVisible(false);
        ScheletAplicatie.removeAll();
        System.exit(0);
    }
    private static synchronized void Initializare() {
        BlackScreen ecranNegru = new BlackScreen();
        int refreshRate = GetRefreshRate();
        frameTime = (double) 1000 / refreshRate;
        System.out.println("Frecventa monitorului: "+ refreshRate);
        ScheletAplicatie = new JFrame("Test Game Engine");
        ScheletAplicatie.setUndecorated(true);//scapa de marginile aplicatiei si da multe erori funny
        ScheletAplicatie.setExtendedState(Frame.MAXIMIZED_BOTH);//face aplicatia sa ocupe tot ecranul
        ScheletAplicatie.add(ecranNegru);
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        screenWidth = ScheletAplicatie.getWidth();
        screenHeight = ScheletAplicatie.getHeight();
        //loading screen
        panouloading = new LoadingScreen();
        LoadingScreen.butonInapoi.textButon = "Inapoi";
        LoadingScreen.butonDa.textButon = "Da";
        LoadingScreen.butonNu.textButon = "Nu";
        //ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        LoadingScreen.ResetLoadingDown();
        LoadingScreen.moveOut = true;
        System.out.println(screenWidth+" x "+screenHeight);
        ScheletAplicatie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        meniu = new Meniu();
        ScheletAplicatie.add(meniu);
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara

        ScheletAplicatie.add(panouloading);
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        MouseManager mouse = new MouseManager();
        ScheletAplicatie.add(mouse);

        KeyManager key = new KeyManager();
        ScheletAplicatie.add(key);
        System.out.println(key.requestFocusInWindow());//NU MAI STERGE ASTA
        ScheletAplicatie.remove(ecranNegru);
        //System.out.println(ScheletAplicatie.getWidth()+" x "+ScheletAplicatie.getHeight());
    }
    private static int GetRefreshRate(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        int rate = 0;
        for (GraphicsDevice g : gs) {
            DisplayMode dm = g.getDisplayMode();
            rate = dm.getRefreshRate();
            if (rate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                System.out.println("Unknown rate");
                rate = 60;
            }
        }
        return rate;
    }

    @Override
    public void run() {
        System.out.println("start aplicatie");
        SoundManager.playSound("src/resources/sunete/Music1.wav", -5, true);
        double timp_incepere = System.nanoTime();
        double timp_trecut;
        stareAplicatie scena = stareAplicatie.meniu, scenaViitoare = stareAplicatie.meniu;
        while(game_is_running){
            timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
            if(timp_trecut >= frameTime){
                timp_incepere = System.nanoTime();
                LoadingScreen.Update();
                if(scena == stareAplicatie.iesire && LoadingScreen.isFinished()){
                    CloseGame();
                }
                if(LoadingScreen.isFinished()){
                    //este ori sus de tot, ori jos de tot
                    scena = scenaViitoare;
                    if(LoadingScreen.bottomY() <= 0){//daca este complet ridicat
                        switch(scena){
                            case meniu -> {//este in meniu
                                scenaViitoare = meniu.UpdateMeniu();
                                meniu.paintImmediately(0,0,screenWidth,screenHeight);
                            }
                            case nivel -> {//este in nivel
                                scenaViitoare = Nivel.Actualizare();
                                Nivel.paintImmediately(0,0,screenWidth,screenHeight);
                            }
                            case credite, endlevel -> {//este in ajutor, nu intra in cazul asta
                                //LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                                LoadingScreen.stare = stariLoading.LoadScreen;
                                scenaViitoare = stareAplicatie.meniu;
                            }
                        }
                    }
                    else{//daca este complet coborat
                        switch(scena){
                            case meniu, nivel -> LoadingScreen.moveOut = true;
                            case credite ->{
                                if(MouseManager.middleMouse){
                                    LoadingScreen.stare = stariLoading.LoadScreen;
                                    LoadingScreen.moveOut = true;
                                }
                            }
                            case endlevel ->{
                                Nivel = null;
                                scenaViitoare = Scoruri.get().Update();
                                panouloading.paintImmediately(0,0,screenWidth, LoadingScreen.bottomY());
                                //logica de citit nume si salvat scor
                            }
                            case selectieNivelIncarcat -> {
                                panouloading.paintImmediately(0,0,screenWidth, LoadingScreen.bottomY());
                                if(LoadingScreen.butonDa.isSelected(MouseManager.mousex, MouseManager.mousey) && MouseManager.clickStanga){
                                    MouseManager.clickStanga = false;
                                    scenaViitoare = stareAplicatie.nivel;
                                }
                                else if(LoadingScreen.butonNu.isSelected(MouseManager.mousex, MouseManager.mousey) && MouseManager.clickStanga){
                                    MouseManager.clickStanga = false;
                                    scenaViitoare = stareAplicatie.nivel;
                                    if(Nivel.getClass() == Level1.class){
                                        Nivel = new Level1(screenWidth,screenHeight, Nivel.getDificultate());
                                        ScheletAplicatie.add(Nivel);
                                        ScheletAplicatie.setVisible(true);
                                        Nivel.onStart();
                                        Nivel.setVisible(true);
                                    }else if(Nivel.getClass() == Level2.class){
                                        Nivel = new Level2(screenWidth,screenHeight, Nivel.getDificultate());
                                        ScheletAplicatie.add(Nivel);
                                        ScheletAplicatie.setVisible(true);
                                        Nivel.onStart();
                                        Nivel.setVisible(true);
                                    }else if(Nivel.getClass() == Level3.class){
                                        Nivel = new Level3(screenWidth,screenHeight, Nivel.getDificultate());
                                        ScheletAplicatie.add(Nivel);
                                        ScheletAplicatie.setVisible(true);
                                        Nivel.onStart();
                                        Nivel.setVisible(true);
                                    }
                                }
                                //else scenaViitoare = stareAplicatie.selectieNivelIncarcat;
                            }
                        }
                    }
                }
                else{
                    switch(scena){
                        case meniu, credite, endlevel, iesire, selectieNivelIncarcat ->{
                            meniu.paintImmediately(0, LoadingScreen.bottomY(),screenWidth,screenHeight- LoadingScreen.bottomY());
                            panouloading.paintImmediately(0,0,screenWidth, LoadingScreen.bottomY());
                        }
                        case nivel -> {
                            Nivel.paintImmediately(0, LoadingScreen.bottomY(),screenWidth,screenHeight- LoadingScreen.bottomY());
                            Nivel.firstPaint = true;
                            panouloading.paintImmediately(0,0,screenWidth, LoadingScreen.bottomY());
                        }
                    }
                }
                timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
                if(Nivel != null){
                    Nivel.FrameTime = "Frame: "+Math.floor(timp_trecut * 100) / 100;
                }
            }
        }
    }
    public static void CloseGame(){
        System.out.println("Iesire din joc!");
        game_is_running = false;
    }
    public static stareAplicatie StartLevel(int numarNivel, int dificultate){
        /*if(Nivel != null){
            ScheletAplicatie.remove(Nivel);
        }*/
        if(Nivel != null && ((Nivel.getClass() == Level1.class && numarNivel == 1) || (Nivel.getClass() == Level2.class && numarNivel == 2) || (Nivel.getClass() == Level3.class && numarNivel == 3))){
            System.out.println("Nivel salvat");
            LoadingScreen.stare = stariLoading.SavedGameScreen;
            LoadingScreen.moveIn = true;
            return stareAplicatie.selectieNivelIncarcat;
        }
        else{
            switch (numarNivel){
                case 1 -> Nivel = new Level1(screenWidth,screenHeight, dificultate);
                case 2 -> Nivel = new Level2(screenWidth,screenHeight, dificultate);
                case 3 -> Nivel = new Level3(screenWidth,screenHeight, dificultate);
                default -> System.out.println("Nu pot incarca nivelul "+numarNivel);
            }
            if(Nivel != null){
                Nivel.onStart();
                ScheletAplicatie.add(Nivel);
                ScheletAplicatie.setVisible(true);
            }
        }
        return stareAplicatie.nivel;
    }
    public static int getScreenWidth(){
        return screenWidth;
    }
    public static int getScreenHeight(){
        return screenHeight;
    }
}


