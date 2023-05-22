import java.awt.*;
import java.util.LinkedList;

public class Level2 extends Level{

    public Level2(int Width, int Height, int dificultate) {
        super(Width, Height, dificultate);
        fundal.SetTexRaw(ResourceManager.get().getFundal(2).GetTex());
        sirBile.indexRapid = 400;
        sirBile.indexIncet = 2650;
        sirBile.indexFinal = 3123;
    }
    @Override
    public void onStart() {
        firstPaint = true;
        tunar.SetCoordX(rezolutieX - (float)rezolutieX / 2);
        tunar.SetCoordY(rezolutieY - (float)rezolutieY / 20);
        tunar.SetLimite(rezolutieX / 20, rezolutieX - rezolutieX / 10, rezolutieY - rezolutieY / 19, rezolutieY - rezolutieY / 19);
        tunar.vitezaTragere = 20;
        tunar.SetUnghi(0);
        cursorPrincipal.SetUnghi(0);
        cursorSecundar.SetUnghi(0);
    }
    public stareAplicatie Actualizare() {//in actualizare trebuie implementat cursorul
        stareAplicatie status = super.Actualizare();
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
        if(sirBile.marime() == 0) {
            Scoruri.get().SalvareScor(scor, 2);
        }
        return  status;
    }


    @Override
    protected void AlocareTraseuBile() {
        traseuBile = new GameObject[3309];//3404
        for (int i = 0; i < 3309; i++) {
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (float unghi = (float) (Math.PI*3/4); unghi > Math.PI / 2; unghi = (float) (unghi - 0.0033)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 300 + 400));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 300 + 20));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 400; j < 650; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(280);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) (Math.PI*3/2); unghi < Math.PI*5 / 2; unghi = (float) (unghi + 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 650));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 320));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 650; j > 320; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(360);
            traseuBile[i].SetUnghi((float) (Math.PI/2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi > Math.PI/2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 320));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 410));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (int j = 320; j < 800; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(460);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) (Math.PI/ 2); unghi > 0; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 800));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 410));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (int j = 410; j > 240; j--) {
            traseuBile[i].SetCoordX(850);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi((float) (Math.PI));
            i++;
        }
        for (float unghi = (float) (Math.PI* 2); unghi > Math.PI*1.625; unghi = (float) (unghi - 0.0076)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 130 + 720));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 130 + 240));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (float unghi = 0; unghi < Math.PI / 2; unghi = (float) (unghi + 0.016)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 60 + 1380));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 60 + 160));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 1380; j > 1120; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(220);
            traseuBile[i].SetUnghi((float) (Math.PI/2));
            i++;
        }
        for (float unghi = (float) (Math.PI*3/2); unghi > Math.PI / 2; unghi = (float) (unghi - 0.0083)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 120 + 1120));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 120 + 340));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 1120; j < 1410; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(460);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) (Math.PI*3/2); unghi < Math.PI * 2; unghi = (float) (unghi + 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 1410));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 500));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 500; j < 730; j++) {
            traseuBile[i].SetCoordX(1450);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        System.out.println(i+" puncte are traseul");

        for (int j = 0; j < 3309; j++) {//3309
            traseuBile[j].SetUnghi((float) Math.toDegrees(traseuBile[j].GetUnghi()));
        }
        System.out.println("Traseul are " + i + " puncte");
    }

    @Override
    protected void MiddlePaint(Graphics g) {

    }
}