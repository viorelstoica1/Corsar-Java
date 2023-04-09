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
import java.util.List;

public class Scena extends JPanel {
    public String cuvantAfisat = "ceva";
    private int mousex, mousey, rezolutieX, rezolutieY;
    private final Textura fundal;
    private BufferedImage tex, tex2, texfundal, tunSus = null;
    private final List<Proiectil> listaProiectile;
    private int scena = 1;
    private final Tun tunar;
    private static final int targetScreenX = 1920, targetScreenY = 1080;
    private GameObject[] traseuBile;
    private final Sir sirBile;
    private final ResourceManager texturi;
    public Scena() {
        this.setLayout(null);
        texturi = new ResourceManager();
        //mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Click stanga la coordonatele " + e.getX() + " " + e.getY());
                    listaProiectile.add(tunar.GetProiectilIncarcat());
                    tunar.Trage();
                    //tunar.SetProiectilCurent(new ProiectilBila(tex2, 60, 10, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
                    tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) texturi.getBilaRandom(),tunar.GetCoordX(),tunar.GetCoordY(),tunar.GetUnghi(), tunar.vitezaTragere));
                    //tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) texturi.getBilaRandom(),tunar.GetCoordX(),tunar.GetCoordY(),tunar.GetUnghi(), tunar.vitezaTragere));
                    System.out.println("Nr proiectile: " + listaProiectile.size());
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    System.out.println("Middle mouse la coordonatele " + e.getX() + " " + e.getY());
                    scena = 0;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Click dreapta la coordonatele " + e.getX() + " " + e.getY());
                    tunar.SchimbaOrdineProiectile();
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
            //tex2 = ImageIO.read(new File("src/resources/galben2.png"));
            tex2 = ImageIO.read(new File("src/resources/BILE_GALBENE.png"));
            tunSus = ImageIO.read(new File("src/resources/Cannon_no_shade.png"));
            texfundal = ImageIO.read(new File("src/resources/Traseu1.png"));
        } catch (IOException e) {
            System.out.println("Nu am putut incarca textura !");
        }
        tunar = new Tun(tex, tunSus, mousex, 0, 270, 15);
        fundal = new Textura(texfundal, 0, 0, 0);
        listaProiectile = new ArrayList<>();
        AlocareTraseuBile();
        sirBile = new Sir(traseuBile,50,20,5,texturi);
    }

    public void onStart(int sizeX, int sizeY) {
        rezolutieY = sizeY;
        rezolutieX = sizeX;
        //resizeTraseu((float)rezolutieX/targetScreenX,(float)rezolutieY/targetScreenY);
        //tex2 = resize(tex2, rezolutieX * tex2.getWidth() / targetScreenX, rezolutieY * tex2.getHeight() / targetScreenY);
        tex = resize(tex, rezolutieX * tex.getWidth() / targetScreenX, rezolutieY * tex.getHeight() / targetScreenY);
        texfundal = resize(texfundal, rezolutieX * texfundal.getWidth() / targetScreenX, rezolutieY * texfundal.getHeight() / targetScreenY);
        tunSus = resize(tunSus, rezolutieX * tunSus.getWidth() / targetScreenX, rezolutieY * tunSus.getHeight() / targetScreenY);

        tunar.SetCoordY((float) sizeY / 2 + (float) sizeY / 4);
        tunar.SetTexRaw(tex);
        tunar.SetTexSus(tunSus);
        //tunar.SetProiectilCurent(new ProiectilBila(tex2, 60, 10, 250, 150, 0, tunar.vitezaTragere));
        tunar.SetProiectilCurent(new ProiectilBila((Spritesheet) texturi.getBilaRandom(),tunar.GetCoordX(),tunar.GetCoordY(),tunar.GetUnghi(), tunar.vitezaTragere));
        //tunar.SetProiectilRezerva(new ProiectilBila(tex2, 60, 10, 250, 150, 0, tunar.vitezaTragere));
        tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet) texturi.getBilaRandom(),tunar.GetCoordX(),tunar.GetCoordY(),tunar.GetUnghi(), tunar.vitezaTragere));

        tunar.SetLimite(rezolutieX - rezolutieX / 20, rezolutieX - rezolutieX / 20, rezolutieY - rezolutieY / 10, rezolutieY / 4);

        fundal.SetTexRaw(texfundal);
        fundal.SetCoordX((float) sizeX / 2);
        fundal.SetCoordY((float) sizeY / 2);

        //sirBile.tex2 = tex2;
    }

    public int Actualizare() {
        tunar.UpdateTun(mousex, mousey);
        for (Iterator<Proiectil> iterator = listaProiectile.iterator(); iterator.hasNext(); ) {
            Proiectil proiectil = iterator.next();
            proiectil.UpdateProiectil();
            if (proiectil.isOutOfBounds(rezolutieX, rezolutieY)) {
                iterator.remove();
            }else{
                Bila aux = sirBile.TestColiziune(proiectil);
                if(aux != null){
                     aux = sirBile.adaugaPeBila(aux, new Bila(proiectil.getSprite(),proiectil.GetCoordX(),proiectil.GetCoordY(),proiectil.GetUnghi()));
                    iterator.remove();
                    if(sirBile.NrBileIdentice(aux) >=3){
                        sirBile.StergeBileIdentice(aux);
                    }
                }
            }
        }
        sirBile.Update();
        return scena;//cu asta poti returna ce scena sa se incarce
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);//rezolva trailingul
        //ordinea elementelor aici arata
        fundal.paintComponent(g);
        tunar.paintComponent(g);
        for (Proiectil proiectil : listaProiectile) {
            proiectil.paintComponent(g);
        }
        sirBile.paintComponent(g);

        g.drawString(cuvantAfisat, 10, 20);
        g.drawString("Nr proiectile: " + listaProiectile.size(), 10, 30);
        g.drawString("Nr bile: "+sirBile.marime, 10,40);
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
        for(int i=0;i<3300;i++){
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (int j = -50; j < 160; j++) {
            traseuBile[i].SetCoordX(390);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = 0; unghi < Math.PI/2; unghi = (float) (unghi + 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 310));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 160));
            traseuBile[i].SetUnghi((float) (unghi));
            i++;
        }
        for (int j = 310; j > 120; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(240);
            traseuBile[i].SetUnghi((float) (Math.PI/2));
            i++;
        }
        for (float unghi = (float) (Math.PI*3/2); unghi > Math.PI; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 120));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 290));
            traseuBile[i].SetUnghi((float) (unghi-Math.PI));
            i++;
        }
        for (int j = 290; j < 400; j++) {
            traseuBile[i].SetCoordX(70);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(0);
            i++;
        }
        for (float unghi = (float) (Math.PI); unghi > Math.PI/2; unghi = (float) (unghi - 0.0125)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 80 + 150));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 80 + 400));
            traseuBile[i].SetUnghi((float) (unghi+Math.PI));
            i++;
        }
        for (int j = 150; j < 310; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(480);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) (Math.PI*3/2); unghi < Math.PI*2; unghi = (float) (unghi + 0.02)) {
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
        for (float unghi = (float) Math.PI; unghi > Math.PI/2; unghi = (float) (unghi - 0.025)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 40 + 400));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 40 + 760));
            traseuBile[i].SetUnghi((float) (unghi+Math.PI));
            i++;
        }
        for (int j = 400; j < 470; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(800);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) Math.PI/2; unghi > Math.PI/6; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 470));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 750));
            traseuBile[i].SetUnghi((float) (unghi+Math.PI));
            i++;
        }
        for (int j = 0; j < 461; j++) {
            int xinceput = 513, xsfarsit = 770;
            int yinceput = 775, ysfarsit = 390;
            traseuBile[i].SetCoordX(xinceput+(xsfarsit-xinceput)*j/461);
            traseuBile[i].SetCoordY(yinceput+(ysfarsit-yinceput)*j/461);
            traseuBile[i].SetUnghi((float) (Math.PI*7/6));
            i++;
        }
        for (float unghi = (float) Math.PI/6; unghi > 0; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 725));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 365));
            traseuBile[i].SetUnghi((float) (unghi+Math.PI));
            i++;
        }
        for (int j = 365; j > 220; j--) {
            traseuBile[i].SetCoordX(775);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi((float) (Math.PI));
            i++;
        }
        for (float unghi = (float) Math.PI; unghi < Math.PI*2; unghi = (float) (unghi + 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 825));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 220));
            traseuBile[i].SetUnghi((float) (unghi));
            i++;
        }
        for (int j = 220; j < 320; j++) {
            traseuBile[i].SetCoordX(875);
            traseuBile[i].SetCoordY(j);
            i++;
        }
        for (float unghi = (float) Math.PI; unghi > Math.PI/2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 925));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 320));
            traseuBile[i].SetUnghi((float) (unghi+ Math.PI));
            i++;
        }
        for (int j = 925; j < 1090; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(370);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) Math.PI*3/2; unghi < Math.PI*2; unghi = (float) (unghi + 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 1090));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 420));
            traseuBile[i].SetUnghi((float) (unghi));
            i++;
        }
        for (int j = 420; j < 670; j++) {
            traseuBile[i].SetCoordX(1140);
            traseuBile[i].SetCoordY(j);
            //traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for (float unghi = (float) Math.PI; unghi > Math.PI/2; unghi = (float) (unghi - 0.02)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 50 + 1190));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 50 + 670));
            traseuBile[i].SetUnghi((float) (unghi+Math.PI));
            i++;
        }
        for (int j = 1190; j < 1240; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(720);
            traseuBile[i].SetUnghi((float) (Math.PI*3/2));
            i++;
        }
        for(int j=0;j<3300;j++){
            traseuBile[j].SetUnghi((float) Math.toDegrees(traseuBile[j].GetUnghi()));
        }
        System.out.println("Traseul are "+i+" puncte");
    }
    private void resizeTraseu(float sizeX, float sizeY){
        for(int i=0;i<traseuBile.length;i++){
            traseuBile[i].SetCoordX(traseuBile[i].GetCoordX()*sizeX);
            traseuBile[i].SetCoordY(traseuBile[i].GetCoordY()*sizeY);
        }
    }
}