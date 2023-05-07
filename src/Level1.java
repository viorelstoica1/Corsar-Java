import java.util.LinkedList;

public class Level1 extends Level {
    public Level1(int Width, int Height) {
        super(Width, Height);
    }

    public void onStart() {
        firstPaint = true;
        tunar.SetCoordX(rezolutieX - (float)rezolutieX / 20);
        tunar.SetCoordY(rezolutieY - (float)rezolutieY / 10);
        tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        tunar.SetLimite(rezolutieX - rezolutieX / 20, rezolutieX - rezolutieX / 20, rezolutieY - rezolutieY / 10, rezolutieY / 10);
        tunar.vitezaTragere = 20;
        tunar.SetUnghi(270);
        SoundManager.playSound("src/resources/sunete/Music1.wav", -5, true);
    }

    public int Actualizare() {//in actualizare trebuie implementat cursorul
        int status = super.Actualizare();
        cursorPrincipal.SetCoordX(0);
        cursorPrincipal.SetCoordY(tunar.GetCoordY());
        cursorSecundar.SetCoordY(tunar.GetCoordY());
        LinkedList<Bila> lista = sirBile.getListaBile();
        for(Bila iterator : lista){
            if(iterator.GetCoordY() > cursorPrincipal.GetCoordY()-iterator.GetMarimeSpriteX() && iterator.GetCoordY() < cursorPrincipal.GetCoordY()+iterator.GetMarimeSpriteX()/* && iterator.GetCoordX() > cursor.GetCoordX()*/){
                float coordonataX = (float) (iterator.GetCoordX() + (Math.sqrt(Math.pow(iterator.GetMarimeSpriteX(),2)-Math.pow((tunar.GetCoordY()-iterator.GetCoordY()),2))));
                if(cursorPrincipal.GetCoordX() < coordonataX){
                    cursorPrincipal.SetCoordX(coordonataX );
                }
            }
        }
        if(cursorPrincipal.GetCoordX() == 0){
            cursorPrincipal.SetCoordX(-cursorPrincipal.GetMarimeTexX());
        }
        cursorSecundar.SetCoordX((cursorPrincipal.GetCoordX()));

        if(sirBile.marime() < 5 && !sirBile.lost){
            sirBile.WaveNou(15);// =)
        }
        if(sirBile.marime() == 0){
            LoadingScreen.moveIn = true;
            return 0;
        }
        return status;//cu asta poti returna ce scena sa se incarce
    }
//linia asta de cod a fost scrisa de un domn anonim

    public void AlocareTraseuBile() {
        traseuBile = new GameObject[3300];//3089
        for (int i = 0; i < 3300; i++) {
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (int j = -50; j < 160; j++) {
            traseuBile[i].SetCoordX(390);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = 0; unghi < Math.PI / 2; unghi = (float) (unghi + 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 310));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 160));
            traseuBile[i].SetUnghi((unghi));
            i++;
        }
        for (int j = 310; j > 120; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(240);
            traseuBile[i].SetUnghi((float) (Math.PI / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi > Math.PI; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 120));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 290));
            traseuBile[i].SetUnghi((float) (unghi - Math.PI));
            i++;
        }
        for (int j = 290; j < 400; j++) {
            traseuBile[i].SetCoordX(70);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = (float) (Math.PI); unghi > Math.PI / 2; unghi = (float) (unghi - 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 150));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 400));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 150; j < 310; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(480);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) (Math.PI * 3 / 2); unghi < Math.PI * 2; unghi = (float) (unghi + 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 310));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 530));
            traseuBile[i].SetUnghi(unghi);
            i++;
        }
        for (int j = 530; j < 760; j++) {
            traseuBile[i].SetCoordX(360);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = (float) Math.PI; unghi > Math.PI / 2; unghi = (float) (unghi - 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 400));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 760));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 400; j < 470; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(800);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) Math.PI / 2; unghi > Math.PI / 6; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 470));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 750));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 0; j < 461; j++) {
            int xinceput = 513, xsfarsit = 770;
            int yinceput = 775, ysfarsit = 390;
            traseuBile[i].SetCoordX(xinceput + (float)((xsfarsit - xinceput) * j) / 461);
            traseuBile[i].SetCoordY(yinceput + (float)((ysfarsit - yinceput) * j) / 461);
            traseuBile[i].SetUnghi((float) (Math.PI * 7 / 6));
            i++;
        }
        for (float unghi = (float) Math.PI / 6; unghi > 0; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 725));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 365));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 365; j > 220; j--) {
            traseuBile[i].SetCoordX(775);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi((float) (Math.PI));
            i++;
        }
        for (float unghi = (float) Math.PI; unghi < Math.PI * 2; unghi = (float) (unghi + 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 825));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 220));
            traseuBile[i].SetUnghi( (unghi));
            i++;
        }
        for (int j = 220; j < 320; j++) {
            traseuBile[i].SetCoordX(875);
            traseuBile[i].SetCoordY(j);
            i++;
        }
        for (float unghi = (float) Math.PI; unghi > Math.PI / 2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 925));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 320));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 925; j < 1090; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(370);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (float unghi = (float) Math.PI * 3 / 2; unghi < Math.PI * 2; unghi = (float) (unghi + 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 1090));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 420));
            traseuBile[i].SetUnghi( (unghi));
            i++;
        }
        for (int j = 420; j < 670; j++) {
            traseuBile[i].SetCoordX(1140);
            traseuBile[i].SetCoordY(j);
            i++;
        }
        for (float unghi = (float) Math.PI; unghi > Math.PI / 2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 1190));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 670));
            traseuBile[i].SetUnghi((float) (unghi + Math.PI));
            i++;
        }
        for (int j = 1190; j < 1240; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(720);
            traseuBile[i].SetUnghi((float) (Math.PI * 3 / 2));
            i++;
        }
        for (int j = 0; j < 3300; j++) {
            traseuBile[j].SetUnghi((float) Math.toDegrees(traseuBile[j].GetUnghi()));
        }
        System.out.println("Traseul are " + i + " puncte");
    }

}