package Meniuri;

import Backbone.Application;
import Backbone.stareAplicatie;
import Manageri.MouseManager;
import Manageri.ResourceManager;
import Manageri.SoundManager;
import Obiecte.Textura;

import javax.swing.*;
import java.awt.*;

public class Meniu extends JPanel{
    private final Textura FundalMeniu, lupa;
    protected Font font;
    private final MeniuSelectii selectii;
    private int selectedButton = 0, timerCooldown = 0;
    private final int cooldownButoane = 20;
    private boolean butoanePregatite = true;
    public Meniu(){
        font = ResourceManager.get().font.deriveFont(75f);
        FundalMeniu = new Textura(ResourceManager.get().getMeniu("Fundal").GetTex(),(float) Application.getScreenWidth()/2,(float)Application.getScreenHeight()/2,0);
        selectii = new MeniuSelectii(ResourceManager.get().getMeniu("Selectii").GetTex(),(float)Application.getScreenWidth()*2/3,(float)Application.getScreenHeight()/2.5f,0);
        lupa = new Textura(ResourceManager.get().getMeniu("Lupa0").imagineRaw, 200, 690, 0);
    }
    public stareAplicatie UpdateMeniu(){
        switch (selectedButton){
            case 1 -> {
                if(selectii.stareMeniu == StariMeniu.SelectiiNivele) {
                    lupa.SetTexRaw(ResourceManager.get().getMeniu("Lupa1").imagineRaw);
                }
            }
            case 2 -> {
                if(selectii.stareMeniu == StariMeniu.SelectiiNivele) {
                    lupa.SetTexRaw(ResourceManager.get().getMeniu("Lupa2").imagineRaw);
                }            }
            case 3 -> {
                if(selectii.stareMeniu == StariMeniu.SelectiiNivele) {
                    lupa.SetTexRaw(ResourceManager.get().getMeniu("Lupa3").imagineRaw);
                }            }
            default -> {
                if(selectii.stareMeniu == StariMeniu.start || selectii.stareMeniu == StariMeniu.SelectiiNivele){
                    lupa.SetTexRaw(ResourceManager.get().getMeniu("Lupa0").imagineRaw);
                }
            }
        }
        if(!butoanePregatite){
            timerCooldown++;
            if(timerCooldown >= cooldownButoane){
                timerCooldown = 0;
                butoanePregatite = true;
            }
        }
        if(selectii.buton1.isSelected(MouseManager.mousex, MouseManager.mousey)){
            //cursorul e pe butonul de sus
            selectedButton = 1;
            if(MouseManager.clickStanga && butoanePregatite){
                butoanePregatite = false;
                SoundManager.playSound("src/resources/sunete/button_click.wav",-5,false);
                return selectii.ApasaButon1();
            }
        }else if(selectii.buton2.isSelected(MouseManager.mousex, MouseManager.mousey)){
            //cursorul e pe butonul din mijloc
            selectedButton = 2;
            if(MouseManager.clickStanga && butoanePregatite){
                butoanePregatite = false;
                SoundManager.playSound("src/resources/sunete/button_click.wav",-5,false);
                return selectii.ApasaButon2();
            }
        }else if(selectii.buton3.isSelected(MouseManager.mousex, MouseManager.mousey)){
            //cursorul e pe butonul de jos
            selectedButton = 3;
            if(MouseManager.clickStanga && butoanePregatite){
                butoanePregatite = false;
                SoundManager.playSound("src/resources/sunete/button_click.wav",-5,false);
                return selectii.ApasaButon3();
            }
        }else if(selectii.buton4.isSelected(MouseManager.mousex, MouseManager.mousey)){
            //cursorul e pe butonul de jos
            selectedButton = 4;
            if(MouseManager.clickStanga && butoanePregatite){
                butoanePregatite = false;
                SoundManager.playSound("src/resources/sunete/button_click.wav",-5,false);
                return selectii.ApasaButon4();
            }
        }else{
            //cursorul e nu e niciun buton
            selectedButton = 0;
        }
        if(MouseManager.middleMouse){
            selectii.IncarcaStare(StariMeniu.start);
        }
        return stareAplicatie.meniu;
    }

    public void paintComponent(Graphics g) {
        FundalMeniu.paintComponent(g);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Corsar",100,300);
        selectii.paintComponent(g);
        lupa.paintComponent(g);
    }
}
