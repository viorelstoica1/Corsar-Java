import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {
    private static JFrame ScheletAplicatie;//marginile aplicatiei
    private static Level1 Nivel;//display-ul propriu zis
    private static int refreshRate;
    private static int screenWidth,screenHeight;
    private static double frameTime;
    public static LoadingScreen panouloading;
    private static Thread gameThread = new Thread(new Application());
    public static void main(String[] args) throws InterruptedException {
        Initializare();
        Nivel.onStart();
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
        refreshRate = GetRefreshRate();
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
        ScheletAplicatie.add(panouloading);
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        ScheletAplicatie.remove(ecranNegru);
        LoadingScreen.ResetLoadingDown();
        LoadingScreen.moveOut = true;
        System.out.println(screenWidth+" x "+screenHeight);
        Nivel = new Level1(screenWidth, screenHeight);
        ScheletAplicatie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScheletAplicatie.add(Nivel);
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        System.out.println(ScheletAplicatie.getWidth()+" x "+ScheletAplicatie.getHeight());
        //ScheletAplicatie.createBufferStrategy(2);
        //ScheletAplicatie.setResizable(false);
    }
    private static int GetRefreshRate(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        int rate = 0;
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();
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
        System.out.println("start");
        boolean game_is_running = true;
        double timp_incepere = System.nanoTime();
        double timp_trecut;
        int scena = 1;
        while(game_is_running){
            timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
            if(timp_trecut >= frameTime){
                timp_incepere = System.nanoTime();
                LoadingScreen.Update();
                if(scena == 0 && LoadingScreen.isFinished()){
                    System.out.println("Iesire din joc!");
                    game_is_running = false;
                }
                if(LoadingScreen.isFinished()){
                    scena = Nivel.Actualizare();
                    Nivel.paintImmediately(0,0,screenWidth,screenHeight);
                }
                else{
                    Nivel.firstPaint = true;
                    Nivel.paintImmediately(0,LoadingScreen.bottomY(),screenWidth,screenHeight- LoadingScreen.bottomY());
                    panouloading.paintImmediately(0,0,screenWidth,LoadingScreen.bottomY());
                }
                //ContextAfisare.repaint(0,0,screenWidth,screenHeight);
                timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
                Nivel.FrameTime = "Frame: "+Math.floor(timp_trecut * 100) / 100;
                //ContextAfisare.repaint();
            }
        }
    }
    public static int getScreenWidth(){
        return screenWidth;
    }
    public static int getScreenHeight(){
        return screenHeight;
    }
}


