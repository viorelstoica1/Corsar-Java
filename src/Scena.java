import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scena extends JPanel {
    public String FrameTime = "Frame: 0";
    public static boolean firstPaint = true;
    private int mousex, mousey;
    private static int rezolutieX, rezolutieY;
    private final Textura fundal, cursorPrincipal, cursorSecundar;
    private static List<Proiectil> listaProiectile;
    private int scena = 1, scor = 0;
    private final Tun tunar;
    private static final int targetScreenX = 1920, targetScreenY = 1080;
    private GameObject[] traseuBile;
    private final Sir sirBile;
    private final Font fontScor;
    private final LinkedList<GameObject> pozitiiProiectile, pozitiiBile;
    private final GameObject pozitieTun, pozitieCursor;
    private final ResourceManager resurse = ResourceManager.get();
    public Scena(int Width, int Height) {
        this.setLayout(null);
        pozitiiBile = new LinkedList<>();
        pozitiiProiectile = new LinkedList<>();
        pozitieTun = new GameObject();
        pozitieCursor = new GameObject();
        rezolutieX = Width;
        rezolutieY = Height;
        ResourceManager.get();
        fontScor = new Font("TimesRoman", Font.PLAIN, 25);
        //mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Click stanga la coordonatele " + e.getX() + " " + e.getY());
                    if(tunar.isGataDeTras()){
                        listaProiectile.add(tunar.GetProiectilIncarcat());
                        tunar.Trage();
                        SoundManager.playSound("src/resources/sunete/launch_sphere.wav", -10, false);
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    System.out.println("Middle mouse la coordonatele " + e.getX() + " " + e.getY());
                    scena = 0;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if(tunar.isGataDeTras()){
                        System.out.println("Click dreapta la coordonatele " + e.getX() + " " + e.getY());
                        tunar.SchimbaOrdineProiectile();
                        SoundManager.playSound("src/resources/sunete/bullet_swap.wav", -10, false);
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();
            }
        });

        tunar = new Tun(resurse.getTunJos().GetTex(), resurse.getTunSus().GetTex(), mousex, 0, 270, 20);
        fundal = new Textura(resurse.getFundal(1).GetTex(), 0, 0, 0);
        cursorPrincipal = new Textura(resurse.getTexturaCursorPrincipal().GetTex(),0,0,270);
        cursorSecundar = new Textura(resurse.getTexturaCursorSecundar().GetTex(), 0,0,270);
        listaProiectile = new ArrayList<>();
        AlocareTraseuBile();
        sirBile = new Sir(traseuBile, 10, 1, 0.5f,0.2f,2500,700,3075);
        tunar.resizeTun( rezolutieX * tunar.GetTex().getWidth() / targetScreenX,rezolutieY * tunar.GetTex().getHeight() / targetScreenY);
        fundal.SetCoordX((float) rezolutieX / 2);
        fundal.SetCoordY((float) rezolutieY / 2);
    }
    public void repaintBackground(int x, int y, int marimeX, int marimeY, Graphics g){
        if(x+marimeX > rezolutieX){
            marimeX = rezolutieX-x;
        }
        if(x<0){
            marimeX += x;
            x = 0;
        }
        if(y+marimeY > rezolutieY){
            marimeY = rezolutieY-y;
        }
        if(y<0){
            marimeY += y;
            y = 0;
        }
        if(marimeX > 0 && marimeY > 0){
            g.drawImage(fundal.GetTex().getSubimage(x,y,marimeX,marimeY),x,y,null);
        }
        //System.out.println(x+" "+y+" "+marimeX+" "+marimeY);
    }

    public void onStart() {
        tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        tunar.SetLimite(rezolutieX - rezolutieX / 20, rezolutieX - rezolutieX / 20, rezolutieY - rezolutieY / 10, rezolutieY / 10);
        SoundManager.playSound("src/resources/sunete/Music1.wav", -5, true);
    }

    private void salvarePozitii(){
        pozitieTun.Copiaza(tunar);
        pozitieCursor.Copiaza(cursorPrincipal);
        GameObject aux = new GameObject();
        for(Proiectil proiectil :listaProiectile){
            aux.Copiaza(proiectil);
            pozitiiProiectile.add(new GameObject().Copiaza(aux));
        }
        for(Bila bila : sirBile.getListaBile()){
            aux.Copiaza(bila);
            pozitiiBile.add(new GameObject().Copiaza(aux));
        }
    }

    public int Actualizare() {
        salvarePozitii();
        //actualizari proiectile
        int index = 0;
        while (index < listaProiectile.size()) {
            Proiectil proiectil = listaProiectile.get(index);
            proiectil.UpdateProiectil();
            Bila aux = sirBile.TestColiziune(proiectil);
            if (aux != null) {
                proiectil.HitSir(sirBile);
            }
            if (proiectil.shouldDissapear) {
                //iterator.remove();
                listaProiectile.remove(proiectil);
                index--;
            }
            index++;
        }
        //actualizari cursor
        if(tunar.GetProiectilIncarcat() != null){
            cursorPrincipal.SetTexRaw(resurse.getTexturaCursorPrincipal(tunar.GetProiectilIncarcat()).GetTex());
        }
        if(tunar.GetProiectilRezerva() != null){
            cursorSecundar.SetTexRaw(resurse.getTexturaCursorSecundar(tunar.GetProiectilRezerva()).GetTex());
        }
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

        tunar.UpdateTun(mousex, mousey);
        //actualizari tunar
        Spritesheet bilaDinSir = sirBile.getTexturaBilaRandom();
        if(tunar.isGataDeTras() && (tunar.GetProiectilIncarcat() == null)){
            if(bilaDinSir != null){
                tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
            }
            else{
                tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
            }
            SoundManager.playSound("src/resources/sunete/bullet_reload.wav", -10, false);
            if(Math.random()>0.95){
                tunar.SetProiectilRezerva(new ProiectilFoc(tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 100, 32));
            }
        }
        if(tunar.isGataDeTras() && bilaDinSir != null && !sirBile.isCuloareInSir(tunar.GetProiectilIncarcat().GetTex()) && (tunar.GetProiectilIncarcat().getClass() == ProiectilBila.class)){
            bilaDinSir = sirBile.getTexturaBilaRandom();
            tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        }
        if(sirBile.marime() < 5 && !sirBile.lost){
            sirBile.WaveNou(15);// =)
        }
        scor += sirBile.Update();
        return scena;//cu asta poti returna ce scena sa se incarce
    }

//linia asta de cod a fost scrisa de un domn anonim

    public void paintComponent(Graphics g) {
        //background
        if(firstPaint){
            firstPaint = false;
            fundal.paintComponent(g);
        }
        //fundal.paintComponent(g);

        g.setFont(fontScor);
        g.setColor(Color.black);
        if(!pozitiiProiectile.isEmpty()){
            for (GameObject proiectil : pozitiiProiectile) {
                repaintBackground((int) (proiectil.GetCoordX()-(resurse.getMarimeBilaSparta()/2)-1),(int) (proiectil.GetCoordY()-(resurse.getMarimeBilaSparta()/2)-1),((resurse.getMarimeBilaSparta())+2), ((resurse.getMarimeBilaSparta())+2), g);
            }
        }
        if(!pozitiiBile.isEmpty()){
            for (GameObject bila : pozitiiBile) {
                repaintBackground((int) (bila.GetCoordX()-((Spritesheet)resurse.getBilaRandom()).GetMarimeSpriteX())-1,(int) (bila.GetCoordY()-((Spritesheet)resurse.getBilaRandom()).GetMarimeSpriteY())-1,((Spritesheet)resurse.getBilaRandom()).GetMarimeSpriteX()*2+2, ((Spritesheet)resurse.getBilaRandom()).GetMarimeSpriteY()*2+2, g);
            }
        }
        pozitiiBile.clear();
        pozitiiProiectile.clear();
        repaintBackground((int) (pozitieCursor.GetCoordX()-cursorPrincipal.marime_x/2)-1, (int) (pozitieCursor.GetCoordY()-cursorPrincipal.marime_y/2)-1, cursorPrincipal.marime_x+2, cursorPrincipal.marime_y+2, g);
        //tunul
        repaintBackground((int) (pozitieTun.GetCoordX()-tunar.marime_x/2)-1, (int) (pozitieTun.GetCoordY()-tunar.marime_y/2)-1, tunar.marime_x+2, tunar.marime_y+2, g);
        Spritesheet tunjos = tunar.GetTexJos();
        repaintBackground(tunjos.CenterX(),(int)(pozitieTun.GetCoordY()-tunar.marime_y/2)-1,tunjos.GetMarimeSpriteX(),tunjos.GetMarimeSpriteY(),g);
        //textul de debug
        repaintBackground(0,0, 180, 180, g);
        //textul de scor
        repaintBackground(rezolutieX/2,10, 120, 30, g);
        //restul
        tunar.paintComponent(g);
        sirBile.paintComponent(g);
        LinkedList<Proiectil> listaRandare  = new LinkedList<>(listaProiectile);
        for (Proiectil proiectil : listaRandare) {
            proiectil.paintComponent(g);
        }
        /*for (Proiectil proiectil : listaProiectile) {
            proiectil.paintComponent(g);
        }*/
        cursorPrincipal.paintComponent(g);
        cursorSecundar.paintComponent(g);
        g.drawString(FrameTime, 10, 20);
        g.drawString("Proiectile:" + listaProiectile.size(), 10, 40);
        g.drawString("Bile:" + sirBile.marime(), 10, 65);
        g.drawString("Wave leaderi:" + sirBile.nrWaveLeaderi,10,90);
        g.drawString("Sir leaderi:"+ sirBile.nrSirLeaderi,10,115);
        g.drawString("Animati:"+ sirBile.nrAnimate,10,140);
        g.drawString("Instabile:"+sirBile.nrInstabile,10,165);
        g.drawString("Scor:"+scor,rezolutieX/2,30);
    }
    public static void AdaugaEfect(Proiectil efect){
        listaProiectile.add(listaProiectile.size(),efect);
    }

    private void AlocareTraseuBile() {
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