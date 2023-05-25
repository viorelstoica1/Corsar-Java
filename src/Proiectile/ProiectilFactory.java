package Proiectile;

import Manageri.ResourceManager;
import Manageri.SetariManager;
import Obiecte.Spritesheet;

public class ProiectilFactory {
    public static Proiectil getProiectil(String tip, String culoare, float pozitieX, float pozitieY, float angel, int nivel, int dificultate){
        switch(tip){
            case "bani" -> {
                return new ProiectilBani((Spritesheet) ResourceManager.get().getMisc("bani"), pozitieX, pozitieY, 0, 10);
            }
            case "foc" ->{
                return new ProiectilFoc(pozitieX,pozitieY,angel, SetariManager.get().getVitezaTragereTun(), 32);
            }
            case "efect" -> {
                return new ProiectilEfect((Spritesheet) ResourceManager.get().getTexturaBilaSparta(culoare),pozitieX,pozitieY,angel,0);
            }
            case "bila" -> {
                if(culoare == null){
                    return new ProiectilBila((Spritesheet) ResourceManager.get().getBilaRandom(dificultate, nivel),pozitieX,pozitieY,angel,SetariManager.get().getVitezaTragereTun());
                }
                else{
                    return new ProiectilBila((Spritesheet) ResourceManager.get().getTexturaBila(culoare,nivel),pozitieX,pozitieY,angel,SetariManager.get().getVitezaTragereTun());
                }
            }
        }
        System.out.println("Nu am putut crea proiectilul!");
        return null;
    }
}
