import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class Sir {
    private final LinkedList<Bila> listaBile;
    int marime, ramaseDeIntrodus;
    float viteza,acceleratie, viteza_max;
    GameObject[] traseu;
    ResourceManager manager;
    public Sir(GameObject[] s, int bile_de_introdus, float viteza_sir_intrare, float viteza_max_generala, ResourceManager manager) {
        this.manager = manager;
        listaBile = new LinkedList<>();
        ramaseDeIntrodus = bile_de_introdus;
        traseu = s; marime = 0;
        viteza = viteza_sir_intrare;
        acceleratie = 0;
        viteza_max = viteza_max_generala;
    }

    public void adaugaLaStangaListei(Bila de_introdus){
        listaBile.addFirst(de_introdus);
        marime++;
        //System.out.println("Am adaugat element la inceputul listei, marime: "+marime);
    }
    //presupunem ca pointerul membru nu este zero !!
    public void adaugaLaDreaptaBilei(Bila membru, Bila de_introdus){
        listaBile.add(listaBile.indexOf(membru)+1,de_introdus);
        //System.out.println("Dreapta");
        marime++;
    }
    //presupunem ca pointerul membru nu este zero !!
    public void adaugaLaStangaBilei(Bila membru, Bila de_introdus){
        listaBile.add(listaBile.indexOf(membru),de_introdus);
        //System.out.println("Stanga");
        marime++;
    }
    //verifica daca trebuie adaugat in stanga sau in dreapta si adauga
    public Bila adaugaPeBila(Bila membru, Bila de_introdus){
        if (membru.DirectieColiziune(de_introdus)/*de_introdus->GetCoordX() > membru->GetCoordX()*/) {//deocamdata las inserarea fara sa tin cont de unghi
            adaugaLaDreaptaBilei(membru, de_introdus);
        }
        else {
            adaugaLaStangaBilei(membru, de_introdus);
        }
        return de_introdus;
    }
    public void StergereLista(){
        int x = marime;
        for (Iterator<Bila> iterator = listaBile.iterator(); iterator.hasNext();) {
            Bila bila = iterator.next();
            iterator.remove();
        }
        marime = 0;
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
        /*static const int de_introdus_original = de_introdus;
        static int winlose = 1;
        static float viteza_max_curenta = viteza_max;
        Bila* index = Cap;
        static Bila* coliziune = 0;//bila dupa care s-a facut coliziune
        bool collided = 0;
        if (!index) {//daca nu mai sunt bile in sir
            coliziune = 0;
            viteza_max_curenta = viteza_max;
            if (winlose == 1) {
                scena = 3;
            }
            else if(winlose == -1){
                scena = 4;
            }
            return winlose;
        }
        //pentru inceputul nivelului
        if (index.GetIndex()>=index->GetMarimeSpriteX() && de_introdus) {
            this->adaugaLaStangaListei(CreeazaBilaRandom());
            //Cap->SetTex(GetRandomBila());
            Cap->CresteNumar(index->GetIndex()%index->GetMarimeSpriteX());
            de_introdus--;
        }
        while (index) {
            if (collided && index->GetInserare() == 0) {//nu stiu unde trebuie sincer
                index->CresteNumar(index->GetMarimeSpriteX() / 8);//10 de la numarul de cadre al animatiei de inserare
            }
            if (index->GetInserare() == 1) {
                AnimatieInserare(index);
                if (index->GetInserare() == 1) {
                    collided = 1;
                }
            }
            else {
                index->Copiaza(&traseu[index->GetIndex()]);//NU MISCA ASTA
            }

            if (CheckColiziuneBila(index, Tunar->GetProiectilIncarcat())) {
                Bila* noua = this->CreeazaBila(Tunar->GetProiectilIncarcat());

                coliziune = noua;
                if (this->adaugaPeBila(index, noua)) {//dreapta
                    noua->SetIndex(index->GetIndex()+index->GetMarimeSpriteX());
                }
			else {//stanga
                    noua->SetIndex(index->GetIndex());
                    index = noua;
                }

                Tunar->TerminatTras();
                //collided = 1;
                noua->SetInserare(1);
            }

            index->UpdateSprite();
            index->CresteNumar(viteza);
            index = index->GetBilaDreapta();
        }

        frame+= viteza;
        if (frame >= Cap->GetNrCadre() * 8) {
            frame = 0;
        }
        if (Coada->GetIndex() >= 750) {
            viteza_max_curenta = viteza_max;
        }
        if (Coada->GetIndex() >= 6000) {
            viteza_max_curenta = 1;
        }
        if (Coada->GetIndex() >= 6650) {
            viteza_max_curenta = 20;
        }
        if (Coada->GetIndex() >= 6700) {
            stergeBila(Coada);
            winlose = -1;
        }


        if (coliziune) {
            if (coliziune->GetInserare() == 0) {
                int nr_bile_identice = Check3Bile(coliziune);
                printf("Sunt %d bile de aceeasi culoare\n", nr_bile_identice);
                if (nr_bile_identice >= 3) {
                    StergeBileIdentice(coliziune);
                }
                coliziune = 0;
            }
        }



        CalculeazaAcceleratia(viteza,viteza_max_curenta);
        viteza = viteza + acceleratie;
        return 0;*/
        if(ramaseDeIntrodus >0 && marime !=0){
            if(listaBile.getFirst().index > listaBile.getFirst().GetMarimeSpriteX()){
                //listaBile.addFirst(new Bila(tex2, 32, 6, traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi()));
                listaBile.addFirst(new Bila((Spritesheet) manager.getBilaRandom(),traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi()));
                listaBile.get(0).index += listaBile.get(1).index % listaBile.get(1).GetMarimeSpriteX();
                marime++;
                ramaseDeIntrodus--;
            }
        }
        else if(ramaseDeIntrodus > 0){
            //listaBile.addFirst(new Bila(tex2, 32, 6, traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi()));
            listaBile.addFirst(new Bila((Spritesheet) manager.getBilaRandom(), traseu[0].GetCoordX(), traseu[0].GetCoordY(), traseu[0].GetUnghi()));
            marime++;
            ramaseDeIntrodus--;
        }
        for(int i=0;i<marime;i++){
            if(i==0){//daca e prima bila
                listaBile.get(i).index+= 2;
            }else{
                listaBile.get(i).index = listaBile.get(i-1).index + listaBile.get(i).GetMarimeSpriteX();
            }
            if(listaBile.get(i).index > 3089){
                listaBile.remove(i);
                marime--;
                i++;
            }else {
                listaBile.get(i).CresteCadru();
                listaBile.get(i).Copiaza(traseu[listaBile.get(i).index]);
            }
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
            return new Bila(obuz.getSprite(),obuz.GetCoordX(),obuz.GetCoordY(),obuz.GetUnghi());
        }
        return null;
    }
    public void StergeBileIdentice(Bila membru){
        /*int nr = 1;
        int index = listaBile.indexOf(membru);
        if(index <marime-1){
            while(listaBile.get(index+1).isSameColour(membru)){
                index++;
                nr++;
            }
        }
        index = listaBile.indexOf(membru);
        if(index>0){
            while(listaBile.get(index-1).isSameColour(membru)){
                index--;
                nr++;
            }
        }
        StergeBileInterval(index,nr);*/
        int index = listaBile.indexOf(membru);
        if(index>0){
            while(listaBile.get(index-1).isSameColour(membru)){
                index--;
            }
        }
        while((index < marime-1) && listaBile.get(index).isSameColour(listaBile.get(index+1))){
            listaBile.remove(index);
            marime--;
        }
        listaBile.remove(index);
        marime--;
    }
}
