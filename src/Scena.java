import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scena extends JPanel {
    public String FrameTime = "Frame: 0";
    private int mousex, mousey, rezolutieX, rezolutieY;
    private final Textura fundal, cursorPrincipal, cursorSecundar;
    private BufferedImage tex, texfundal, tunJos, tunSus;
    private final List<Proiectil> listaProiectile;
    private int scena = 1, scor = 0;
    private final Tun tunar;
    private static final int targetScreenX = 1920, targetScreenY = 1080;
    private GameObject[] traseuBile;
    private final Sir sirBile;
    private final ResourceManager texturi;
    private final Font fontScor;
    public Scena() {
        this.setLayout(null);
        texturi = new ResourceManager();
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
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                //moveSquare(e.getX(),e.getY());
                mousex = e.getX();
                mousey = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                //moveSquare(e.getX(),e.getY());
                mousex = e.getX();
                mousey = e.getY();
            }
        });

        try {
            tex = ImageIO.read(new File("src/resources/Cannon.png"));
            tunSus = ImageIO.read(new File("src/resources/Cannon_no_shade.png"));
            texfundal = ImageIO.read(new File("src/resources/Background_2.png"));
            tunJos = ImageIO.read(new File("src/resources/Cannon_explosion-sheet.png"));
        } catch (IOException e) {
            System.out.println("Nu am putut incarca texturile !");
        }
        tunar = new Tun(tunJos, tunSus, mousex, 0, 270, 20);
        fundal = new Textura(texfundal, 0, 0, 0);
        cursorPrincipal = new Textura(texturi.getTexturaCursorPrincipal().GetTex(),0,0,270);
        cursorSecundar = new Textura(texturi.getTexturaCursorSecundar().GetTex(), 0,0,270);
        listaProiectile = new ArrayList<>();
        AlocareTraseuBile();
        sirBile = new Sir(traseuBile, 10, 1, 0.5f,0.2f,2700,700,3100,texturi);
    }

    public void onStart(int sizeX, int sizeY) {
        rezolutieY = sizeY;
        rezolutieX = sizeX;
        tex = resize(tex, rezolutieX * tex.getWidth() / targetScreenX, rezolutieY * tex.getHeight() / targetScreenY);
        texfundal = resize(texfundal, rezolutieX, rezolutieY);
        tunSus = resize(tunSus, rezolutieX * tunSus.getWidth() / targetScreenX, rezolutieY * tunSus.getHeight() / targetScreenY);
        tunJos = resize(tunJos,tunSus.getWidth()*5, tunSus.getHeight());
        tunar.SetCoordY((float) sizeY / 2 + (float) sizeY / 4);
        tunar.SetTexSus(tunSus);
        tunar.SetTexJos(tunJos);
        tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) texturi.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) texturi.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));

        tunar.SetLimite(rezolutieX - rezolutieX / 20, rezolutieX - rezolutieX / 20, rezolutieY - rezolutieY / 10, rezolutieY / 10);

        fundal.SetTexRaw(texfundal);
        fundal.SetCoordX((float) sizeX / 2);
        fundal.SetCoordY((float) sizeY / 2);
    }

    public int Actualizare() {
        //actualizari proiectile
        for (Iterator<Proiectil> iterator = listaProiectile.iterator(); iterator.hasNext(); ) {
            Proiectil proiectil = iterator.next();
            proiectil.UpdateProiectil();
            if (proiectil.isOutOfBounds(rezolutieX, rezolutieY)) {
                iterator.remove();
            } else {
                Bila aux = sirBile.TestColiziune(proiectil);
                if (aux != null) {
                    sirBile.adaugaPeBila(aux, new Bila(proiectil.getSprite(), proiectil.GetCoordX(), proiectil.GetCoordY(), proiectil.GetUnghi(), aux.acceleratie));
                    iterator.remove();
                }
            }
        }
        
        //actualizari cursor
        if(tunar.GetProiectilIncarcat() != null){
            cursorPrincipal.SetTexRaw(texturi.getTexturaCursorPrincipal(tunar.GetProiectilIncarcat()).GetTex());
        }
        if(tunar.GetProiectilRezerva() != null){
            cursorSecundar.SetTexRaw(texturi.getTexturaCursorSecundar(tunar.GetProiectilRezerva()).GetTex());
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
                tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
            }
            else{
                tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) texturi.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
            }
        }
        if(tunar.isGataDeTras() && bilaDinSir != null && !sirBile.isCuloareInSir(tunar.GetProiectilIncarcat().GetTex())){
            bilaDinSir = sirBile.getTexturaBilaRandom();
            tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
        }
        if(sirBile.marime() < 5){
            sirBile.WaveNou(10);// =)
        }
        scor += sirBile.Update();
        return scena;//cu asta poti returna ce scena sa se incarce
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);//rezolva trailingul
        //ordinea elementelor aici arata
        g.setFont(fontScor);
        g.setColor(Color.black);
        fundal.paintComponent(g);
        tunar.paintComponent(g);
        sirBile.paintComponent(g);
        for (Proiectil proiectil : listaProiectile) {
            proiectil.paintComponent(g);
        }
        cursorPrincipal.paintComponent(g);
        cursorSecundar.paintComponent(g);
        g.drawString(FrameTime, 10, 20);
        g.drawString("Nr proiectile: " + listaProiectile.size(), 10, 40);
        g.drawString("Nr bile: " + sirBile.marime(), 10, 65);
        g.drawString("Nr wave leaderi: " + sirBile.nrWaveLeaderi,10,90);
        g.drawString("Nr sir leaderi: "+ sirBile.nrSirLeaderi,10,115);
        g.drawString("Nr animati: "+ sirBile.nrAnimate,10,140);
        g.drawString("Nr instabile: "+sirBile.nrInstabile,10,165);
        g.drawString("Scor: "+scor,rezolutieX/2,30);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
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

    private void resizeTraseu(float sizeX, float sizeY) {
        for (GameObject gameObject : traseuBile) {
            gameObject.SetCoordX(gameObject.GetCoordX() * sizeX);
            gameObject.SetCoordY(gameObject.GetCoordY() * sizeY);
        }
    }

}