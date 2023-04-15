import javax.swing.JFrame;
import java.awt.*;

public class Application implements Runnable {
    private static JFrame ScheletAplicatie;//marginile aplicatiei
    private static Scena ContextAfisare;//display-ul propriu zis
    private static int refreshRate;
    private static int screenWidth,screenHeight;
    private static double frameTime;
    private static Thread gameThread = new Thread(new Application());
    public static void main(String[] args) throws InterruptedException {
        Initializare();
        ContextAfisare.onStart(screenWidth,screenHeight);
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
        //System.setProperty("sun.java2d.opengl", "true");
        refreshRate = GetRefreshRate();
        frameTime = (double) 1000 / refreshRate;
        System.out.println("Frecventa monitorului: "+ refreshRate);
        ScheletAplicatie = new JFrame("Test Game Engine");
        ScheletAplicatie.setUndecorated(true);//scapa de marginile aplicatiei si da multe erori funny
        ScheletAplicatie.setExtendedState(Frame.MAXIMIZED_BOTH);//face aplicatia sa ocupe tot ecranul
        ContextAfisare = new Scena();
        ScheletAplicatie.setVisible(true);//face aplicatia sa apara
        ScheletAplicatie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScheletAplicatie.add(ContextAfisare);
        //ScheletAplicatie.createBufferStrategy(2);
        //ScheletAplicatie.setResizable(false);
        screenWidth = ScheletAplicatie.getWidth();
        screenHeight = ScheletAplicatie.getHeight();
        System.out.println(screenWidth+" x "+screenHeight);
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
        boolean game_is_running = true;
        double timp_incepere = System.nanoTime();
        double timp_trecut;
        int scena = 1;
        while(game_is_running){
            if(scena == 0){
                game_is_running = false;
            }
            timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
            if(timp_trecut >= frameTime){
                timp_incepere = System.nanoTime();
                scena = ContextAfisare.Actualizare();
                timp_trecut = (System.nanoTime()- timp_incepere)/1000000;
                ContextAfisare.cuvantAfisat = "Frame: "+timp_trecut;
                //ContextAfisare.repaint();
                ContextAfisare.paintImmediately(0,0,screenWidth,screenHeight);
            }
        }
    }
}


