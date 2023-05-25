package Meniuri;

import Backbone.Application;
import Backbone.Scoruri;
import Manageri.MouseManager;
import Manageri.ResourceManager;
import Manageri.SetariManager;
import Manageri.SoundManager;
import Obiecte.Textura;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JPanel {
    private static final Textura FundalLoading = new Textura(ResourceManager.get().getLoadscreen().imagineRaw,(float) Application.getScreenWidth()/2,(float)Application.getScreenHeight()/2,0);
    private static StateLoadingScreen starePaint = new LoadScreenState();
    public static boolean moveIn = false, moveOut = false;
    public static float vitezaAnimatie = 0, acceleratieAnimatie = SetariManager.get().getVitezaLoadingScreen();
    public static stariLoading stare = stariLoading.LoadScreen;
    public static Buton butonInapoi = new Buton(FundalLoading.GetMarimeTexX() / 3, FundalLoading.GetMarimeTexY() / 3 + FundalLoading.CenterY(),200,50);
    public static Buton butonDa = new Buton(FundalLoading.GetMarimeTexX()/3,FundalLoading.GetMarimeTexY() *2/ 5 + FundalLoading.CenterY(),200,50);
    public static Buton butonNu = new Buton(FundalLoading.GetMarimeTexX()*2/3,FundalLoading.GetMarimeTexY() *2/ 5 + FundalLoading.CenterY(),200,50);
    public static void Update(){
        switch (stare){
            case CreditScreen -> starePaint = new CreditState();
            case LoadScreen -> starePaint = new LoadScreenState();
            case EndLevelScreen -> starePaint = new EndLevelState();
            case SavedGameScreen -> starePaint = new SavedGameState();
        }
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
        float y = FundalLoading.GetCoordY();
        vitezaAnimatie += acceleratieAnimatie;
        if(moveIn){
            if(y < (float)Application.getScreenHeight()/2){
                FundalLoading.SetCoordY(y+vitezaAnimatie);
            }else{
                FundalLoading.SetCoordY((float)Application.getScreenHeight()/2);
                moveIn = false;
                vitezaAnimatie = 0;
            }
        }else if(moveOut){
            if(y > -(float)Application.getScreenHeight()/2){
                FundalLoading.SetCoordY(y-vitezaAnimatie);
            }else{
                FundalLoading.SetCoordY(-(float)Application.getScreenHeight()/2);
                moveOut = false;
                vitezaAnimatie = 0;
            }
        }else{
            vitezaAnimatie = 0;
        }
        if(bottomY() == Application.getScreenHeight() && butonInapoi.isSelected(MouseManager.mousex, MouseManager.mousey) && MouseManager.clickStanga && !Scoruri.get().newHighScore && isFinished()){
            SoundManager.playSound("src/resources/sunete/button_click.wav",-5,false);
            moveOut = true;
        }
    }
    public static void ResetLoadingUp(){
        FundalLoading.SetCoordY(-(float)Application.getScreenHeight()/2);
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
    }
    public static void ResetLoadingDown(){
        FundalLoading.SetCoordY((float)Application.getScreenHeight()/2);
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.setFont(ResourceManager.get().font.deriveFont(65f));
        starePaint.paintComponent(g);
        /*switch (stare){
            case LoadScreen -> {
                setTex(ResourceManager.get().getLoadscreen());
                FundalLoading.paintComponent(g);
            }
            case CreditScreen -> {
                setTex(ResourceManager.get().getMeniu("Ajutor"));
                FundalLoading.paintComponent(g);
            }
            case EndLevelScreen -> {
                setTex(ResourceManager.get().getMeniu("Hartie"));
                FundalLoading.paintComponent(g);
                g.drawString("Scorul tau: "+ Scoruri.get().getScorSalvat(),FundalLoading.GetMarimeTexX() / 4, FundalLoading.GetMarimeTexY() / 4 + FundalLoading.CenterY());
                if(Scoruri.get().newHighScore) {
                    g.drawString("Introdu numele: ", FundalLoading.GetMarimeTexX() / 4, FundalLoading.GetMarimeTexY() / 3 + FundalLoading.CenterY());
                    g.drawString(Scoruri.get().numeDeAfisat(), FundalLoading.GetMarimeTexX() / 4, FundalLoading.GetMarimeTexY() / 2 + FundalLoading.CenterY());
                }
                else{
                    if(isFinished()){
                        butonInapoi.paintComponent(g);
                    }
                }
            }
            case SavedGameScreen -> {
                setTex(ResourceManager.get().getMeniu("Hartie"));
                FundalLoading.paintComponent(g);
                g.drawString("Joc in desfasurare", FundalLoading.GetMarimeTexX() / 8, FundalLoading.GetMarimeTexY() / 4 + FundalLoading.CenterY());
                g.drawString("Continuati?", FundalLoading.GetMarimeTexX() / 8, FundalLoading.GetMarimeTexY() * 3 / 8 + FundalLoading.CenterY());
                if(isFinished()){
                    butonDa.paintComponent(g);
                    butonNu.paintComponent(g);
                }
            }
        }*/

        /*g.drawString("Trage",550,  FundalLoading.CenterY()+130);
        g.drawString("Inapoi",550, FundalLoading.CenterY()+180);
        g.drawString("Schimba bila",550, FundalLoading.CenterY()+230);
        g.drawString("Tine loc de orice bila",550, FundalLoading.CenterY()+325);
        g.drawString("Distruge bile in jurul ei",550, FundalLoading.CenterY()+400);
        //testeri
        g.drawString("Testeri:",1150,FundalLoading.CenterY()+130);
        g.drawString("Tudor Bogos",1150,FundalLoading.CenterY()+180);
        g.drawString("Cezar Cimpoca",1150,FundalLoading.CenterY()+230);
        g.drawString("David Andrei",1150,FundalLoading.CenterY()+280);
        g.drawString("Fratii Irimia",1150,FundalLoading.CenterY()+330);
        g.drawString("Alex Timisorean",1150,FundalLoading.CenterY()+380);
        g.drawString("Mihnea Bastea",1150,FundalLoading.CenterY()+430);
        g.drawString("Andrei Sereduc",1150,FundalLoading.CenterY()+480);
        //credite
        g.drawString("Multumiri Speciale:",Application.getScreenWidth()/4,FundalLoading.CenterY()+560);
        g.drawString("Florin Alistar - Grafica nivelului 3, a meniurilor",250,FundalLoading.CenterY()+600);
        g.drawString("- Promptitudine si multa rabdare",550,FundalLoading.CenterY()+640);
        g.drawString("Alex Plescan - Grafica nivelului 1, 2, tun, bile",250,FundalLoading.CenterY()+680);
        g.drawString("- Eficienta si stil artistic",550,FundalLoading.CenterY()+720);
*/
    }
    public static boolean isFinished(){
        return !moveIn && !moveOut;
    }
    public static int bottomY(){
        return (FundalLoading.GetMarimeTexY() + FundalLoading.CenterY());
    }
    public static void setTex(Textura tex){
        FundalLoading.SetTexRaw(tex.imagineRaw);
    }
    public static Textura getTex(){
        return FundalLoading;
    }
}
