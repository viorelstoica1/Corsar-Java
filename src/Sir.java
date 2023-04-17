import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Sir {
    private final LinkedList<Bila> listaBile;
    public int nrWaveLeaderi = 0, nrSirLeaderi = 0, nrAnimate = 0;
    private int indexRapid,indexIncet,indexFinal,ramaseDeIntrodus;
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
        }
        de_introdus.isWaveLeader = true;
        listaBile.addFirst(de_introdus);
        //System.out.println("Am adaugat element la inceputul listei, marime: "+marime);
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
        return de_introdus;
    }
    public void StergereLista(){
        int x = listaBile.size();
        for (Iterator<Bila> iterator = listaBile.iterator(); iterator.hasNext();) {
            Bila bila = iterator.next();
            iterator.remove();
        }
        System.out.println("Am sters lista cu "+x+" elemente");
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
        nrWaveLeaderi = 0;nrSirLeaderi = 0;nrAnimate = 0;//incepe numararea de bile speciale la fiecare cadru
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
                    if(listaBile.get(i-1).isSameColour(listaBile.get(i)) && NrBileIdentice(listaBile.get(i))>=3){
                        //daca sunt aceeasi culoare
                        StergeBileIdentice(listaBile.get(i));
                        //in functia de stergere am grija de flaguri
                    }
                    else{
                        //daca nu sunt aceeasi culoare, se unesc sirurile
                        listaBile.get(i).isSirLeader = false;
                        //seteaza viteza maxima a bilei din urma
                        listaBile.get(i).vitezaMax = listaBile.get(i-1).vitezaMax;
                    }
                    System.out.println("S-au ciocnit sirurile");
                }
                else{
                    //daca nu s-a ciocnit
                    if(listaBile.get(i-1).isSameColour(listaBile.get(i))){
                        //daca sunt aceeasi culoare
                        //seteaza viteza maxima invers rapid
                        listaBile.get(i).vitezaMax = -viteza;
                    }
                    else{
                        //daca nu sunt aceeasi culoare
                        //seteaza viteza maxima oprire
                        listaBile.get(i).vitezaMax = 0;
                    }
                    System.out.println("NU S-au ciocnit sirurile");
                }
            }
            else{//este bila normala
                //seteaza viteza maxima la bila din urma
                listaBile.get(i).vitezaMax = listaBile.get(i-1).vitezaMax;
            }
            /*if(i==0){//daca e prima bila
                listaBile.get(i).index+= 1;
            }else{
                listaBile.get(i).index = listaBile.get(i-1).index + listaBile.get(i).GetMarimeSpriteX();
            }
            if(listaBile.get(i).index > 3089){
                listaBile.remove(i);
                i++;
            }else {
                listaBile.get(i).ScadeCadru();
                listaBile.get(i).Copiaza(traseu[listaBile.get(i).index]);
            }*/
            i++;
        }
        i = listaBile.size()-1;

        while(i >= 0){//parcurgere sir de bile (CALCUL VITEZA)
            if(i+1 == listaBile.size() || listaBile.get(i+1).isWaveLeader || listaBile.get(i+1).isSirLeader){
                //daca bila e capatul drept al unui sir
                if(listaBile.get(i).index < indexRapid){
                    //daca bila e la inceput, merge rapid
                    if(listaBile.get(i).vitezaMax > 0){
                        listaBile.get(i).vitezaMax = viteza_max;
                    }
                }else if(listaBile.get(i).index > indexIncet){
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
                //daca bila nu e capatul unui
                // sir, ia viteza bilei din dreapta
                listaBile.get(i).viteza = listaBile.get(i+1).viteza;
                listaBile.get(i).index = listaBile.get(i+1).index-listaBile.get(i).GetMarimeSpriteX();
                //listaBile.get(i).index += listaBile.get(i).viteza;
            }
            listaBile.get(i).Copiaza(traseu[(int) listaBile.get(i).index]);
            if(listaBile.get(i).index > indexFinal){//a pierdut jocul
                listaBile.remove(i);
            }
            listaBile.get(i).CresteCadru(listaBile.get(i).viteza);
            i--;
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
    }

    public void CalculeazaAcceleratia(float viteza_, float viteza_max_){
        if (viteza_ == viteza_max_) {
            acceleratie = 0;
        }
        else if (viteza_ < viteza_max_) {
            acceleratie = 0.2F;
        }
        else acceleratie = (float) -0.2;
    }
    //verifica cate bile identice sunt legate de bila data parametru

    //animeaza inserarea bilei in sir
    /*public void AnimatieInserare(Bila membru){
        static int cadre = 8;
        if (cadre == 0) {
            membru.SetInserare(0);
            cadre = 8;
        }
        else {
            float x = (traseu[membru->GetIndex()].GetCoordX() - membru->GetCoordX()) / (float)cadre;
            float y = (traseu[membru->GetIndex()].GetCoordY() - membru->GetCoordY()) / (float)cadre;
            membru->SetCoordX(membru->GetCoordX() + x);
            membru->SetCoordY(membru->GetCoordY() + y);
            cadre--;
        }
    }*/

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
    //sterge o bila din lista si reface legaturile
    public void stergeBila(Bila membru){
        if (membru != null) {
            listaBile.remove(membru.index);
        }
    }
    public Bila CreeazaBila(Proiectil obuz){
        if(obuz != null){
            return new Bila(obuz.getSprite(),obuz.GetCoordX(),obuz.GetCoordY(),obuz.GetUnghi(), acceleratie);
        }
        return null;
    }
    public void StergeBileIdentice(Bila membru){
        int index = listaBile.indexOf(membru);
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
                    listaBile.get(index).isWaveLeader = waveLeader;
                    System.out.println("Nou wave leader, pozitia "+index);
                }
                else{
                    listaBile.get(index).isSirLeader = true;
                    System.out.println("Nou sir leader, pozitia "+index);
                }
                break;
            }
            listaBile.remove(index);
        }
    }
    public int marime(){
        return listaBile.size();
    }
}
