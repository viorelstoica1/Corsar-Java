package Backbone;

import Manageri.BazaDateManager;
import Manageri.KeyManager;
import Manageri.MouseManager;
import Meniuri.LoadingScreen;

public class Scoruri {
    private static Scoruri singletonScoruri;
    private final int[][] scoruri;
    private final String [][]nume;
    private final int nrNivele = 3, nrScoruriSalvate = 3;
    private int scorDeIntrodus = 0, nivel = 0;
    private String numeSalvat;
    public boolean newHighScore = false;
    private Scoruri(){
        numeSalvat = "";
        scoruri = new int[nrNivele][nrScoruriSalvate];
        nume = new String[nrNivele][nrScoruriSalvate];
        for(int i = 0; i< nrNivele; i++){
            for(int j = 0; j< nrScoruriSalvate; j++){
                scoruri[i][j] = 0;
                nume[i][j] = "Niciun scor";
            }
        }
    }
    public String numeDeAfisat(){
        return numeSalvat;
    }
    public stareAplicatie Update(){
        if(isHighscore(nivel,scorDeIntrodus)){
            newHighScore = true;
            //daca scorul salvat este highscore, trebuie introdus un nume
            if(KeyManager.litera != 0){
                if(numeSalvat.length() < 13){
                    numeSalvat += KeyManager.litera;
                }
                KeyManager.litera = 0;
            }
            if(KeyManager.backspace){
                if(!numeSalvat.isEmpty()){
                    numeSalvat = numeSalvat.substring(0,numeSalvat.length()-1);
                    KeyManager.backspace = false;
                }
            }
            if(KeyManager.enter){
                //salvare nume si scor
                try{
                    int pozitie = AdaugaScor(nivel, scorDeIntrodus, numeSalvat);
                    if(pozitie != -1){
                        BazaDateManager.ScriereScor(nivel,scorDeIntrodus,numeSalvat,pozitie+1);
                    }
                    scorDeIntrodus = 0;
                    LoadingScreen.moveOut = true;
                    newHighScore = false;
                    numeSalvat = "";
                    return stareAplicatie.meniu;
                }
                catch(EmptyNameException e){
                    System.out.println(e.getMessage());
                }
                finally{
                    KeyManager.enter = false;
                }
            }
            return stareAplicatie.endlevel;
        }
        else{
            if(MouseManager.middleMouse){
                LoadingScreen.moveOut = true;
                return stareAplicatie.meniu;
            }
            return stareAplicatie.endlevel;
        }
    }
    public static synchronized Scoruri get(){
        if(singletonScoruri == null){
            singletonScoruri = new Scoruri();
        }
        return singletonScoruri;
    }
    public int getValoareScor(int nivel, int pozitie){
        return scoruri[nivel][pozitie];
    }
    public String getnumeScor(int nivel, int pozitie){
        return nume[nivel][pozitie];
    }
    public void SalvareScor(int scor, int nivel, float multiplier){
        this.nivel = nivel;
        scorDeIntrodus = (int) (scor * multiplier);
    }
    public int AdaugaScor(int nivel, int scorNou, String nume) throws EmptyNameException {
        if(nume.isEmpty()){
            throw new EmptyNameException();
        }
        else{
            int pozitie = -1;
            for(int i=0; i< nrScoruriSalvate; i++){
                if(scoruri[nivel][i] <= scorNou){
                    pozitie = i;
                    //muta scorurile vechi mai jos
                    for(int j = nrScoruriSalvate-1; j > i; j--){
                        scoruri[nivel][j] = scoruri[nivel][j-1];
                        this.nume[nivel][j] = this.nume[nivel][j-1];
                    }
                    //baga scor nou in pozitia buna
                    scoruri[nivel][i] = scorNou;
                    this.nume[nivel][i] = nume;
                    break;
                }
            }
            return pozitie;
        }
    }
    public boolean isHighscore(int nivel, int scor){
        for(int i=0; i< nrScoruriSalvate; i++){
            if(scoruri[nivel][i] < scor){
                //muta scorurile vechi mai jos
                return true;
            }
        }
        return false;
    }
    public int getScorSalvat(){
        return scorDeIntrodus;
    }
    public void CitireScorBazaDate(int nivel, int pozitie, int scor, String nume){
        this.scoruri[nivel][pozitie] = scor;
        this.nume[nivel][pozitie] = nume;
    }
}
