import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Sir {
    private final LinkedList<Bila> listaBile;
    public int nrWaveLeaderi = 0, nrSirLeaderi = 0, nrAnimate = 0, nrInstabile = 0, scor = 0;
    private final int indexRapid, indexIncet, indexFinal;
    float viteza,acceleratie, viteza_max, viteza_min;
    GameObject[] traseu;
    ResourceManager manager;
    public Sir(GameObject[] s, float viteza_sir_intrare, float viteza_max_generala,float viteza_min, float acceleratie_bile, int indexIncet, int indexRapid ,int indexFinal,ResourceManager manager) {
        this.manager = manager;
        listaBile = new LinkedList<>();
        traseu = s;
        viteza_max = viteza_sir_intrare;
        acceleratie = acceleratie_bile;
        viteza = viteza_max_generala;
        this.viteza_min = viteza_min;
        this.indexIncet = indexIncet;
        this.indexRapid = indexRapid;
        this.indexFinal = indexFinal;
    }
    public void WaveNou(int numarBile){
        if(numarBile > 0){
            listaBile.addFirst(new Bila((Spritesheet) manager.getBilaRandom(),traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi(), acceleratie));
            listaBile.get(0).isWaveLeader = true;
            numarBile--;
        }
        while(numarBile != 0){
            adaugaLaWave(new Bila((Spritesheet) manager.getBilaRandom(),traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi(), acceleratie));
            numarBile--;
        }
    }
    public void adaugaLaWave(Bila de_introdus){
        if(!listaBile.isEmpty()){
            listaBile.get(0).isWaveLeader = false;
            de_introdus.viteza = listaBile.get(0).viteza;
            de_introdus.index = listaBile.get(0).index-de_introdus.GetMarimeSpriteX();
        }
        de_introdus.isWaveLeader = true;
        de_introdus.vitezaMax = viteza_max;
        listaBile.addFirst(de_introdus);
        //System.out.println("Am adaugat element la inceputul listei, marime: "+marime);
    }

    public LinkedList<Bila> getListaBile(){
        return listaBile;
    }
    public int NrBileIdentice(Bila membru){
        int nrBileIdentice = 0;
        int index = listaBile.indexOf(membru);
        while(index > 0 && listaBile.get(index-1).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            index--;
        }
        index++;
        nrBileIdentice++;
        while(index < listaBile.size() && listaBile.get(index).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            nrBileIdentice++;
            index++;
        }
        return nrBileIdentice;
    }//merge pe wave-uri

    //presupunem ca pointerul membru nu este zero !!
    public void adaugaLaDreaptaBilei(Bila membru, Bila de_introdus){
        listaBile.add(listaBile.indexOf(membru)+1,de_introdus);
        de_introdus.index = membru.index+membru.GetMarimeSpriteX();
    }
    //presupunem ca pointerul membru nu este zero !!
    public void adaugaLaStangaBilei(Bila membru, Bila de_introdus){
        if(membru.isWaveLeader){
            de_introdus.isWaveLeader = true;
            membru.isWaveLeader = false;
        }
        if(membru.isSirLeader){
            de_introdus.isSirLeader = true;
            membru.isSirLeader = false;
        }
        listaBile.add(listaBile.indexOf(membru),de_introdus);
        de_introdus.index = membru.index-membru.GetMarimeSpriteX();
    }
    //verifica daca trebuie adaugat in stanga sau in dreapta si adauga
    public Bila adaugaPeBila(Bila membru, Bila de_introdus){
        if (membru.DirectieColiziune(de_introdus)) {
            adaugaLaDreaptaBilei(membru, de_introdus);
        }
        else {
            adaugaLaStangaBilei(membru, de_introdus);
        }
        de_introdus.isAnimating = true;
        de_introdus.viteza = membru.viteza;
        getBilaFinalSir(listaBile.indexOf(de_introdus)).index+=de_introdus.GetMarimeSpriteX();
        return de_introdus;
    }

    public void paintComponent(Graphics g){
        /*LinkedList<Bila> listaRandare  = new LinkedList<Bila>(listaBile);
        for (Bila bila : listaRandare) {
            bila.paintComponent(g);
        }*/
        for(Bila bila:listaBile){
            bila.paintComponent(g);
        }
    }

    public int Update(){
        scor = 0;
        nrWaveLeaderi = 0;nrSirLeaderi = 0;nrAnimate = 0;nrInstabile = 0;///incepe numararea de bile speciale la fiecare cadru
        int i = 0;
        while(i <listaBile.size()){//parcurge lista de bile (update_wave)
            NumaraStatusBile(i);//contorizeaza numarul de bile speciale din lista
            if(listaBile.get(i).isWaveLeader){//daca este wave leader
                //seteaza viteza maxima fata normal
                if(i > 0 && listaBile.get(i).CheckColiziuneBila(listaBile.get(i-1))){
                    //daca s-a ciocnit cu bila din urma lui
                    listaBile.get(i).isWaveLeader = false;
                    if(!listaBile.get(i).isAnimating){
                        listaBile.get(i).isStable = false;
                    }
                    //seteaza viteza maxima a bilei din urma
                    getBilaInceputSir(i-1).viteza = (listaBile.get(i).viteza+listaBile.get(i-1).viteza)/2;
                }
                listaBile.get(i).vitezaMax = viteza;
            }
            else if(listaBile.get(i).isSirLeader){//daca este sir leader
                if(listaBile.get(i).CheckColiziuneBila(listaBile.get(i-1))){
                    //daca s-a ciocnit cu bila din urma lui
                    listaBile.get(i).isSirLeader = false;
                    if(!listaBile.get(i).isAnimating){
                        listaBile.get(i).isStable = false;
                    }
                    //seteaza viteza bilei din urma
                    getBilaInceputSir(i-1).viteza = (listaBile.get(i).viteza+listaBile.get(i-1).viteza)/2;
                }
                else{
                    //daca nu s-a ciocnit
                    if(listaBile.get(i-1).isSameColour(listaBile.get(i))){
                        //daca sunt aceeasi culoare
                        //seteaza viteza maxima invers rapid
                        listaBile.get(i).vitezaMax = -viteza_max;
                    }
                    else{
                        listaBile.get(i).scoreMultiplier = 1;
                        //daca nu sunt aceeasi culoare
                        //seteaza viteza maxima oprire
                        listaBile.get(i).vitezaMax = 0;
                    }
                }
            }
            else{//este bila normala
                //seteaza viteza maxima la bila din urma
                listaBile.get(i).vitezaMax = listaBile.get(i-1).vitezaMax;
            }
            if(!listaBile.get(i).isStable) {
                //daca bila curenta nu e stabila
                if(NrBileIdentice(listaBile.get(i))>=3){
                    //daca sunt destule bile de aceeasi culoare
                    scor+= NrBileIdentice(listaBile.get(i)) * GetMultiplier(listaBile.get(i));
                    StergeBileIdentice(listaBile.get(i));
                    if(i<listaBile.size()) {
                        while (!listaBile.get(i).isSirLeader && !listaBile.get(i).isWaveLeader) {
                            i--;
                        }
                        listaBile.get(i).scoreMultiplier++;
                    }
                }
                else{
                    //stabilizeaza bila
                    listaBile.get(i).isStable = true;
                }
            }
            i++;
        }
        i = 0;
        while(i<listaBile.size()){
            if(listaBile.get(i).isWaveLeader || listaBile.get(i).isSirLeader){
                //daca bila e capatul drept al unui sir
                if(getBilaFinalSir(i).index < indexRapid){
                    //daca bila e la inceput, merge rapid
                    if(listaBile.get(i).vitezaMax > 0){
                        listaBile.get(i).vitezaMax = viteza_max;
                    }
                }else if(getBilaFinalSir(i).index > indexIncet){
                    //daca bila e la sfarsit, merge incet
                    if(listaBile.get(i).vitezaMax > 0){
                        listaBile.get(i).vitezaMax = viteza_min;
                    }
                }
                listaBile.get(i).calculeazaViteza();
                listaBile.get(i).index+= listaBile.get(i).viteza;
                //System.out.println(listaBile.get(i).viteza);
            }
            else{
                //daca bila nu e capatul unui sir, ia viteza bilei din stanga
                listaBile.get(i).viteza = listaBile.get(i-1).viteza;
                if(listaBile.get(i-1).isAnimating && !listaBile.get(i-1).isWaveLeader && !listaBile.get(i-1).isSirLeader){
                    listaBile.get(i).index = listaBile.get(i-1).index+listaBile.get(i-1).MarimeAnimatie();
                }else listaBile.get(i).index = listaBile.get(i-1).index+listaBile.get(i).GetMarimeSpriteX();
                //listaBile.get(i).index = listaBile.get(i-1).index+listaBile.get(i).GetMarimeSpriteX();
            }

            if(listaBile.get(i).index >=0){//modificarea pozitiei efective a bilei
                if(!listaBile.get(i).isAnimating){
                    listaBile.get(i).Copiaza(traseu[(int) listaBile.get(i).index]);
                }else{
                    listaBile.get(i).AnimatieInserare(traseu[(int) listaBile.get(i).index]);
                }
            }
            else{
                listaBile.get(i).Copiaza(traseu[0]);
            }
            listaBile.get(i).CresteCadru(listaBile.get(i).viteza);
            if(getBilaFinalSir(i).index > indexFinal){//a pierdut jocul
                listaBile.remove(i);
            }
            i++;
        }
        return scor;
    }
    public void NumaraStatusBile(int i){
        //numarare statusuri bile
        if(listaBile.get(i).isWaveLeader){
            nrWaveLeaderi++;
        }
        if(listaBile.get(i).isSirLeader){
            nrSirLeaderi++;
        }
        if(listaBile.get(i).isAnimating){
            nrAnimate++;
        }
        if(!listaBile.get(i).isStable){
            nrInstabile++;
        }
    }

//parcurge lista si returneaza obiectul cu care s-a facut coliziunea
    public Bila TestColiziune(Proiectil obuz){
        for ( Bila index : listaBile) {
            if (index.DistantaPatrat(obuz) <= Math.pow((obuz.GetMarimeSpriteX() + (float) index.GetMarimeSpriteX()) / 2, 2)) {
                //System.out.println("Coliziune!" + index.DistantaPatrat(obuz));
                return index;
            }
        }
        return null;
    }
    public void StergeBileIdentice(Bila membru){
        int index = listaBile.indexOf(membru);
        float viteza = membru.viteza;
        boolean waveLeader = false;
        while(index > 0 && listaBile.get(index-1).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            index--;
        }
        if(listaBile.get(index).isWaveLeader){
            waveLeader = true;
        }
        listaBile.remove(index);
        while(index < listaBile.size() && listaBile.get(index).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            listaBile.remove(index);
        }
        if(index < listaBile.size()){
            if(waveLeader){
                listaBile.get(index).isWaveLeader = true;
            }
            else{
                listaBile.get(index).isSirLeader = true;
            }
            listaBile.get(index).viteza = viteza;
        }
    }//merge pe wave-uri

    public int GetMultiplier(Bila membru){
        int multiplier = 1;
        int index = listaBile.indexOf(membru);
        while(index > 0 && listaBile.get(index-1).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            index--;
        }
        if(listaBile.get(index).scoreMultiplier > multiplier){
            multiplier = listaBile.get(index).scoreMultiplier;
        }
        index++;
        while(index < listaBile.size() && listaBile.get(index).isSameColour(membru) && !listaBile.get(index).isSirLeader && !listaBile.get(index).isWaveLeader){
            if(listaBile.get(index).scoreMultiplier > multiplier){
                multiplier = listaBile.get(index).scoreMultiplier;
            }
            index++;
        }
        System.out.println("Multiplier: "+multiplier);
        return multiplier;
    }
    public int marime(){
        return listaBile.size();
    }

    public Spritesheet getTexturaBilaRandom(){
        if(listaBile.size() >0){
            return listaBile.get((int)(Math.random() * listaBile.size()));
        }
        return null;
    }

    public Bila getBilaFinalSir(int index){
        index++;
        while(index < listaBile.size()){
            if(listaBile.get(index).isSirLeader || listaBile.get(index).isWaveLeader){
                //index--;
                break;
            }
            index++;
        }
        //System.out.println(listaBile.get(index-1).index+" ");
        if(index == listaBile.size()){
            return listaBile.get(index-1);
        }
        return listaBile.get(index-1);
    }//merge pe wave-uri
    public boolean isCuloareInSir(BufferedImage culoare){
        for (Bila bila : listaBile) {
            if (bila.GetTex() == culoare) {
                return true;
            }
        }
        return false;
    }
    public Bila getBilaInceputSir(int index){
        index--;
        while(index >= 0){
            if(listaBile.get(index).isSirLeader || listaBile.get(index).isWaveLeader){
                index--;
                break;
            }
            index--;
        }
        //System.out.println(listaBile.get(index+1).index+" ");
        return listaBile.get(index+1);
    }
}
