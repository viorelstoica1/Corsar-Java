import java.awt.*;
import java.awt.image.BufferedImage;
enum StariMeniu{
    start,
    SelectiiNivele,
    SelectiiDificultate,
    Credite
}
public class MeniuSelectii extends Textura{
    public Buton buton1, buton2, buton3;
    private StariMeniu stareMeniu = StariMeniu.start;
    private int nivelSelectat;
    public MeniuSelectii(BufferedImage imagine, float poz_x, float poz_y, float angel) {
        super(imagine, poz_x, poz_y, angel);
        buton1 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) ((int) GetCoordY()-GetMarimeTexY()*0.26),GetMarimeTexX(),100);
        buton1.textButon = "Start";
        buton2 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) ((int) GetCoordY()+GetMarimeTexY()*0.03),GetMarimeTexX(),100);
        buton2.textButon = "Credite";
        buton3 = new Buton((int) GetCoordX()-GetMarimeTexX()/2,(int) GetCoordY()+GetMarimeTexY()*3/10,GetMarimeTexX(),100);
        buton3.textButon = "Iesire";

    }
    public int ApasaButonSus(){
        switch(stareMeniu){
            case start -> {
                IncarcaStare(StariMeniu.SelectiiNivele);
                return 0;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 1;
                return 0;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                Application.StartLevel(nivelSelectat,4);
                LoadingScreen.moveIn = true;
                return 1;
            }
            case Credite -> {
                return 0;
            }
        }
        System.out.println("Stare necunoscuta pe buton sus !");
        return -1;
    }
    public int ApasaButonMijloc(){
        switch(stareMeniu){
            case start -> {
                IncarcaStare(StariMeniu.Credite);
                return 0;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 2;
                return 0;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                Application.StartLevel(nivelSelectat,5);
                LoadingScreen.moveIn = true;
                return 1;
            }
            case Credite -> {
                return 0;
            }
        }
        System.out.println("Stare necunoscuta pe buton mijloc !");
        return -1;
    }
    public int ApasaButonJos(){
        switch(stareMeniu){
            case start -> {
                //Application.CloseGame();
                LoadingScreen.moveIn = true;
                return -1;
            }
            case SelectiiNivele -> {
                IncarcaStare(StariMeniu.SelectiiDificultate);
                nivelSelectat = 3;
                return 0;
            }
            case SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                Application.StartLevel(nivelSelectat,6);
                LoadingScreen.moveIn = true;
                return 1;
            }
            case Credite -> {
                IncarcaStare(StariMeniu.start);
                return 0;
            }
        }
        System.out.println("Stare necunoscuta pe buton jos !");
        return -1;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buton1.paintComponent(g);
        buton2.paintComponent(g);
        buton3.paintComponent(g);
    }
    private void IncarcaStare(StariMeniu stare){
        switch (stare){
            case start ->{
                stareMeniu = StariMeniu.start;
                buton1.textButon = "Start";
                buton2.textButon = "Credite";
                buton3.textButon = "Iesire";
            }
            case SelectiiNivele -> {
                stareMeniu = StariMeniu.SelectiiNivele;
                buton1.textButon = "Corabia";
                buton2.textButon = "Nu ma apasa";
                buton3.textButon = "Templul";
            }
            case SelectiiDificultate -> {
                stareMeniu = StariMeniu.SelectiiDificultate;
                buton1.textButon = "Usor";
                buton2.textButon = "Mediu";
                buton3.textButon = "Greu";
            }
            case Credite -> {
                stareMeniu = StariMeniu.Credite;
                buton1.textButon = "Alex Plescan";
                buton2.textButon = "Florin Alistar";
                buton3.textButon = "Inapoi";
            }
        }
    }
}
