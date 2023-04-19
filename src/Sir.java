import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Sir {
    private final LinkedList<Bila> listaBile;
    public int nrWaveLeaderi = 0, nrSirLeaderi = 0, nrAnimate = 0, nrInstabile = 0;
    private final int indexRapid, indexIncet, indexFinal;
    private int ramaseDeIntrodus;
    float viteza,acceleratie, viteza_max, viteza_min;
    GameObject[] traseu;
    ResourceManager manager;
    public Sir(GameObject[] s, int bile_de_introdus, float viteza_sir_intrare, float viteza_max_generala,float viteza_min, float acceleratie_bile, int indexIncet, int indexRapid ,int indexFinal,ResourceManager manager) {
        this.manager = manager;
        listaBile = new LinkedList<>();
        ramaseDeIntrodus = bile_de_introdus;
        traseu = s;
        viteza_max = viteza_sir_intrare;
        acceleratie = acceleratie_bile;
        viteza = viteza_max_generala;
        this.viteza_min = viteza_min;
        this.indexIncet = indexIncet;
        this.indexRapid = indexRapid;
        this.indexFinal = indexFinal;
    }

    public void adaugaLaInceputulListei(Bila de_introdus){
        if(!listaBile.isEmpty()){
            listaBile.get(0).isWaveLeader = false;
            de_introdus.viteza = listaBile.get(0).viteza;
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
        int nr = 1, stanga = listaBile.indexOf(membru)-1,dreapta = listaBile.indexOf(membru)+1;
        while(stanga >= 0){
            if(!listaBile.get(stanga).isSameColour(membru)){
                break;
            }
            nr++;
            stanga--;
        }
        while(dreapta < listaBile.size()){
            if(!listaBile.get(dreapta).isSameColour(membru)){
                break;
            }
            nr++;
            dreapta++;
        }
        System.out.println("Bile identice gasite: "+(nr));
        return nr;
    }

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
        //getBilaFinalSir(listaBile.indexOf(de_introdus)).index+=de_introdus.GetMarimeSpriteX();
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

    public void Update(){
        nrWaveLeaderi = 0;nrSirLeaderi = 0;nrAnimate = 0;nrInstabile = 0;///incepe numararea de bile speciale la fiecare cadru
        if(ramaseDeIntrodus >0 && !listaBile.isEmpty()){
            if(listaBile.getFirst().index > listaBile.getFirst().GetMarimeSpriteX()){
                adaugaLaInceputulListei(new Bila((Spritesheet) manager.getBilaRandom(),traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi(), acceleratie));
                listaBile.get(0).index += listaBile.get(1).index % listaBile.get(1).GetMarimeSpriteX();
                ramaseDeIntrodus--;
            }
        }
        else if(ramaseDeIntrodus > 0){
            adaugaLaInceputulListei(new Bila((Spritesheet) manager.getBilaRandom(), traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi(), acceleratie));
            ramaseDeIntrodus--;
        }

        int i = 0;
        while(i <listaBile.size()){//parcurge lista de bile (update_wave)
            NumaraStatusBile(i);//contorizeaza numarul de bile speciale din lista
            if(listaBile.get(i).isWaveLeader){//daca este wave leader
                //seteaza viteza maxima fata normal
                listaBile.get(i).vitezaMax = viteza;
            }
            else if(listaBile.get(i).isSirLeader){//daca este sir leader
                if(listaBile.get(i).CheckColiziuneBila(listaBile.get(i-1))){
                    //daca s-a ciocnit cu bila din urma lui
                    listaBile.get(i).isSirLeader = false;
                    if(!listaBile.get(i).isAnimating){
                        listaBile.get(i).isStable = false;

                    }
                    //seteaza viteza maxima a bilei din urma
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
                    StergeBileIdentice(listaBile.get(i));
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
        while(index >= 0){
            if(!listaBile.get(index).isSameColour(membru)){
                index++;
                break;
            }
            index--;
        }
        if(index <= 0){
            index = 0;
            waveLeader = true;
        }
        while(index < listaBile.size()){
            if(!listaBile.get(index).isSameColour(membru)){//daca nu sunt aceeasi culoare
                if(waveLeader){//nu sunt sigur ca e buna
                    listaBile.get(index).isWaveLeader = true;
                    System.out.println("Nou wave leader, pozitia "+index);
                }
                else{
                    listaBile.get(index).isSirLeader = true;
                    getBilaInceputSir(index-1).viteza = (viteza+listaBile.get(index-1).viteza)/2;
                    //listaBile.get(index-1).viteza = (viteza+listaBile.get(index).viteza)/2;//(listaBile.get(index).viteza + listaBile.get(index-1).viteza)/2;
                    System.out.println("Nou sir leader, pozitia "+index);
                }
                break;
            }
            listaBile.remove(index);
            if(index !=0){
                listaBile.get(index-1).viteza = (viteza+listaBile.get(index-1).viteza)/2;//(listaBile.get(index).viteza + listaBile.get(index-1).viteza)/2;
            }
        }
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
        return listaBile.get(index);
    }
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
