import java.awt.*;
import java.awt.image.BufferedImage;
enum StariMeniu{
    start,
    SelectiiNivele,
    SelectiiDificultate,
}
public class MeniuSelectii extends Textura{
    public Buton buton1, buton2, buton3, buton4;
    public StariMeniu stareMeniu = StariMeniu.start;
    private int nivelSelectat;
    public MeniuSelectii(BufferedImage imagine, float poz_x, float poz_y, float angel) {
        super(imagine, poz_x, poz_y, angel);
        buton1 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) (GetCoordY()-GetMarimeTexY()*0.26),GetMarimeTexX(),100);
        buton1.textButon = "Start";
        buton2 = new Buton((int) GetCoordX()-GetMarimeTexX()/2, (int) (GetCoordY()-GetMarimeTexY()*0.09),GetMarimeTexX(),100);
        buton2.textButon = "Credite";
        buton3 = new Buton((int) GetCoordX()-GetMarimeTexX()/2,(int) (GetCoordY()+GetMarimeTexY()*0.08),GetMarimeTexX(),100);
        buton3.textButon = "Scoruri";
        buton4 = new Buton((int) GetCoordX()-GetMarimeTexX()/2,(int) (GetCoordY()+GetMarimeTexY()*0.25),GetMarimeTexX(),100);
        buton4.textButon = "Iesire";
    }
    public int ApasaButon1(){
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
        }
        System.out.println("Stare necunoscuta pe buton 1 !");
        return -1;
    }
    public int ApasaButon2(){
        switch(stareMeniu){
            case start -> {//intrare credite
                LoadingScreen.setTex(ResourceManager.get().getMeniu("Ajutor"));
                LoadingScreen.moveIn = true;
                return 2;
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
        }
        System.out.println("Stare necunoscuta pe buton 2 !");
        return -1;
    }
    public int ApasaButon3(){
        switch(stareMeniu){
            case start -> {
                //afisare scoruri
                return 0;
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
        }
        System.out.println("Stare necunoscuta pe buton 3 !");
        return -1;
    }
    public int ApasaButon4(){
        switch(stareMeniu){
            case start -> {
                LoadingScreen.moveIn = true;
                return -1;
            }
            case SelectiiNivele, SelectiiDificultate -> {
                IncarcaStare(StariMeniu.start);
                return 0;
            }
        }
        System.out.println("Stare necunoscuta pe buton 4 !");
        return -1;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buton1.paintComponent(g);
        buton2.paintComponent(g);
        buton3.paintComponent(g);
        buton4.paintComponent(g);
    }
    public void IncarcaStare(StariMeniu stare){
        switch (stare){
            case start ->{
                stareMeniu = StariMeniu.start;
                buton1.textButon = "Start";
                buton2.textButon = "Credite";
                buton3.textButon = "Scoruri";
                buton4.textButon = "Iesire";
            }
            case SelectiiNivele -> {
                stareMeniu = StariMeniu.SelectiiNivele;
                buton1.textButon = "Corabia";
                buton2.textButon = "Nu ma apasa";
                buton3.textButon = "Templul";
                buton4.textButon = "Inapoi";
            }
            case SelectiiDificultate -> {
                stareMeniu = StariMeniu.SelectiiDificultate;
                buton1.textButon = "Usor";
                buton2.textButon = "Mediu";
                buton3.textButon = "Greu";
                buton4.textButon = "Inapoi";
            }
        }
    }
}
