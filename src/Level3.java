import java.awt.*;
import java.util.LinkedList;

public class Level3 extends Level{
    Capcana capcanaStanga, capcanaDreapta;
    public Level3(int Width, int Height) {
        super(Width, Height);
        fundal.SetTexRaw(ResourceManager.get().getFundal(3).GetTex());
        sirBile.indexRapid = 400;
        sirBile.indexIncet = 2850;
        sirBile.indexFinal = 3300;
        capcanaStanga = new Capcana(ResourceManager.get().getCapcana(1).GetTex(),374,43,0,200, 2);
        capcanaDreapta = new Capcana(ResourceManager.get().getCapcana(2).GetTex(),753,39,0,200, 2);
    }

    @Override
    public void onStart() {
        firstPaint = true;
        tunar.SetCoordX(rezolutieX - (float)rezolutieX / 2);
        tunar.SetCoordY(rezolutieY - (float)rezolutieY / 20);

        tunar.SetLimite(rezolutieX / 20, rezolutieX - rezolutieX / 8, rezolutieY - rezolutieY / 19, rezolutieY - rezolutieY / 19);
        tunar.vitezaTragere = 20;
        tunar.SetUnghi(0);
        cursorPrincipal.SetUnghi(0);
        cursorSecundar.SetUnghi(0);
        SoundManager.playSound("src/resources/sunete/Music1.wav", -5, true);
    }

    public int Actualizare() {//in actualizare trebuie implementat cursorul
        int status = super.Actualizare();
        //actualizari capcane
        capcanaDreapta.Update();
        capcanaStanga.Update();
        if(capcanaDreapta.isReady()){
            System.out.println("Capcana trage");
            listaProiectile.add(capcanaDreapta.proiectilIncarcat);
            capcanaDreapta.resetCapcana();
        }
        if(capcanaStanga.isReady()){
            System.out.println("Capcana trage");
            listaProiectile.add(capcanaStanga.proiectilIncarcat);
            capcanaStanga.resetCapcana();
        }
        //actualizari cursor
        cursorPrincipal.SetCoordY(0);
        cursorPrincipal.SetCoordX(tunar.GetCoordX());
        cursorSecundar.SetCoordX(tunar.GetCoordX());
        LinkedList<Bila> lista = sirBile.getListaBile();
        for(Bila iterator : lista){
            if(iterator.GetCoordX() > cursorPrincipal.GetCoordX()-iterator.GetMarimeSpriteX() && iterator.GetCoordX() < cursorPrincipal.GetCoordX()+iterator.GetMarimeSpriteX()){
                float coordonataY = (float) (iterator.GetCoordY() + (Math.sqrt(Math.pow(iterator.GetMarimeSpriteX(),2)-Math.pow((tunar.GetCoordX()-iterator.GetCoordX()),2))));
                if(cursorPrincipal.GetCoordY() < coordonataY){
                    cursorPrincipal.SetCoordY(coordonataY );
                }
            }
        }
        if(cursorPrincipal.GetCoordY() == 0){
            cursorPrincipal.SetCoordY(-cursorPrincipal.GetMarimeTexY());
        }
        cursorSecundar.SetCoordY((cursorPrincipal.GetCoordY()));

        if(sirBile.marime() < 5 && !sirBile.lost){
            sirBile.WaveNou(15);// =)
        }
        if(sirBile.marime() == 0){
            LoadingScreen.moveIn = true;
            status = 0;
        }
        return status;//cu asta poti returna ce scena sa se incarce
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        capcanaStanga.paintComponent(g);
        capcanaDreapta.paintComponent(g);
    }
    @Override
    protected void AlocareTraseuBile() {
        traseuBile = new GameObject[3404];//3404
        for (int i = 0; i < 3404; i++) {
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (int j = -50; j < 80; j++) {
            traseuBile[i].SetCoordX(1320);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = 0; unghi < Math.PI / 2; unghi = (float) (unghi + 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 1240));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 80));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 1240; j > 200; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(160);
            traseuBile[i].SetUnghi((float) (Math.PI / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi > Math.PI; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 200));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 210));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (int j = 210; j < 470; j++) {
            traseuBile[i].SetCoordX(150);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = (float) (Math.PI); unghi > Math.PI / 2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 200));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 470));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 200; j < 490; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(520);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi < Math.PI * 1.75; unghi = (float) (unghi + 0.033)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 30 + 490));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 30 + 550));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 0; j < 203; j++) {//scandura
            int xinceput = 510, xsfarsit = 680;
            int yinceput = 528, ysfarsit = 640;
            traseuBile[i].SetCoordX(xinceput + (float)((xsfarsit - xinceput) * j) / 203);
            traseuBile[i].SetCoordY(yinceput + (float)((ysfarsit - yinceput) * j) / 203);
            traseuBile[i].SetUnghi((float) (Math.PI * 1.75));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3/4); unghi > Math.PI / 2; unghi = (float) (unghi - 0.033)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 30 + 702));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 30 + 618));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        //System.out.println(traseuBile[i-1].GetCoordX()+" "+traseuBile[i-1].GetCoordY());
        for (int j = 702; j < 900; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(648);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) Math.PI / 2; unghi > 0; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 900));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 548));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (float unghi = (float) Math.PI; unghi < (float) (Math.PI * 3 / 2); unghi = (float) (unghi + 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 1080));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 548));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 1080; j < 1200; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(468);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi < Math.PI * 2; unghi = (float) (unghi + 0.005)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 200 + 1200));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 200 + 668));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 668; j < 900; j++) {
            traseuBile[i].SetCoordX(1400);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        System.out.println(i+" puncte are traseul");
        //aici a ramas

        for (int j = 0; j < 3404; j++) {//3404
            traseuBile[j].SetUnghi((float) Math.toDegrees(traseuBile[j].GetUnghi()));
        }
        System.out.println("Traseul are " + i + " puncte");
    }
}
