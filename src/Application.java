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
        Initializare();
        /*boolean game_is_running = true;
        double timp_incepere = System.nanoTime();
        double timp_trecut;
        int scena = 1;
        while(game_is_running){
            if(scena == 0){
                game_is_running = false;
            }
                timp_incepere = System.nanoTime();
                scena = ContextAfisare.Actualizare();
                ContextAfisare.repaint();
                timp_trecut = (System.nanoTime()-timp_incepere)/1000000;//system time e nanosecunde, frametime e milisecunde
                if(timp_trecut < frameTime){
                    ContextAfisare.cuvantAfisat = "Frame time: "+timp_trecut;
                    Thread.sleep((long) (frameTime -timp_trecut));
                }
        }*/
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
        MouseStatus mouse = new MouseStatus();
        ScheletAplicatie.add(mouse);
        ScheletAplicatie.remove(ecranNegru);
        System.out.println(ScheletAplicatie.getWidth()+" x "+ScheletAplicatie.getHeight());
        //ScheletAplicatie.createBufferStrategy(2);
        //ScheletAplicatie.setResizable(false);
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
        int scena = 0, scenaViitoare = 0;
        while(game_is_running){
            timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
            if(timp_trecut >= frameTime){
                timp_incepere = System.nanoTime();
                LoadingScreen.Update();
                if(scena == -1 && LoadingScreen.isFinished()){
                    CloseGame();
                }
                if(LoadingScreen.isFinished()){
                    //este ori sus de tot, ori jos de tot
                    scena = scenaViitoare;
                    if(LoadingScreen.bottomY() <= 0){//daca este complet ridicat
                        switch(scena){
                            case 0 -> {//este in meniu
                                scenaViitoare = meniu.UpdateMeniu();
                                meniu.paintImmediately(0,0,screenWidth,screenHeight);
                            }
                            case 1 -> {//este in nivel
                                scenaViitoare = Nivel.Actualizare();
                                Nivel.paintImmediately(0,0,screenWidth,screenHeight);
                            }
                            case 2 -> {//este in ajutor, nu intra in cazul asta
                                LoadingScreen.setTex(ResourceManager.get().getLoadscreen());
                                scenaViitoare = 0;
                            }
                        }
                    }
                    else{//daca este complet coborat
                        if(scena != 2){//este in meniu/ nivel
                            LoadingScreen.moveOut = true;
                        }else{//este in ajutor/credite
                            if(MouseStatus.middleMouse){
                                LoadingScreen.moveOut = true;
                            }
                        }
                    }
                }
                else{
                    switch(scena){
                        case 0, 2 ->{
                            meniu.paintImmediately(0,LoadingScreen.bottomY(),screenWidth,screenHeight- LoadingScreen.bottomY());
                            panouloading.paintImmediately(0,0,screenWidth,LoadingScreen.bottomY());
                        }
                        case 1 -> {
                            Nivel.paintImmediately(0,LoadingScreen.bottomY(),screenWidth,screenHeight- LoadingScreen.bottomY());
                            Nivel.firstPaint = true;
                            panouloading.paintImmediately(0,0,screenWidth,LoadingScreen.bottomY());
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
    public static void StartLevel(int numarNivel, int dificultate){
        if(Nivel != null){
            ScheletAplicatie.remove(Nivel);
        }
        switch (numarNivel){
            case 1 -> Nivel = new Level1(screenWidth,screenHeight, dificultate);
            case 2 -> {
                //Nivel = new Level2(screenWidth,screenHeight);
            }
            case 3 -> Nivel = new Level3(screenWidth,screenHeight, dificultate);
            default -> System.out.println("Nu pot incarca nivelul "+numarNivel);
        }
        if(Nivel != null){
            Nivel.onStart();
            ScheletAplicatie.add(Nivel);
            ScheletAplicatie.setVisible(true);
        }
    }
    public static int getScreenWidth(){
        return screenWidth;
    }
    public static int getScreenHeight(){
        return screenHeight;
    }
}


