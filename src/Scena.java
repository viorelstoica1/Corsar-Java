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
    public Scena() {
        this.setLayout(null);
        //mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Click stanga la coordonatele " + e.getX() + " " + e.getY());
                    listaProiectile.add(tunar.GetProiectilIncarcat());
                    tunar.Trage();
                    tunar.SetProiectilCurent(new ProiectilBila(tex2, 32, 6, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere));
                    System.out.println("Nr proiectile: " + listaProiectile.size());
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    System.out.println("Middle mouse la coordonatele " + e.getX() + " " + e.getY());
                    scena = 0;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Click dreapta la coordonatele " + e.getX() + " " + e.getY());
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
            tex2 = ImageIO.read(new File("src/resources/galben2.png"));
            tunSus = ImageIO.read(new File("src/resources/Cannon_no_shade.png"));
            texfundal = ImageIO.read(new File("src/resources/Traseu1.png"));
        } catch (IOException e) {
            System.out.println("Nu am putut incarca textura !");
        }
        tunar = new Tun(tex, tunSus, mousex, 0, 270, 10);
        fundal = new Textura(texfundal, 0, 0, 0);
        listaProiectile = new ArrayList<>();
        AlocareTraseuBile();
        sirBile = new Sir(traseuBile,20,20,5,tex2);
    }

    public void onStart(int sizeX, int sizeY) {
        rezolutieY = sizeY;
        rezolutieX = sizeX;
        //resizeTraseu((float)rezolutieX/targetScreenX,(float)rezolutieY/targetScreenY);
        tex2 = resize(tex2, rezolutieX * tex2.getWidth() / targetScreenX, rezolutieY * tex2.getHeight() / targetScreenY);
        tex = resize(tex, rezolutieX * tex.getWidth() / targetScreenX, rezolutieY * tex.getHeight() / targetScreenY);
        texfundal = resize(texfundal, rezolutieX * texfundal.getWidth() / targetScreenX, rezolutieY * texfundal.getHeight() / targetScreenY);
        tunSus = resize(tunSus, rezolutieX * tunSus.getWidth() / targetScreenX, rezolutieY * tunSus.getHeight() / targetScreenY);

        tunar.SetCoordY((float) sizeY / 2 + (float) sizeY / 4);
        tunar.SetTexRaw(tex);
        tunar.SetTexSus(tunSus);
        tunar.SetProiectilCurent(new ProiectilBila(tex2, 32, 6, 250, 150, 0, tunar.vitezaTragere));
        tunar.SetProiectilRezerva(new ProiectilBila(tex2, 32, 6, 250, 150, 0, tunar.vitezaTragere));
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
        traseuBile = new GameObject[7000];//6682
        for(int i=0;i<7000;i++){
            traseuBile[i] = new GameObject();
        }
        int i = 0;
        for (int j = -50; j < 50; j++) {
            traseuBile[i].SetCoordX(700);
            traseuBile[i].SetCoordY(j);
            traseuBile[i].SetUnghi(1.57F);
            i++;
        }
        for (float unghi = 3.14F; unghi > 1.57; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 800));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 50));
            i++;
            traseuBile[i].SetUnghi((float) (unghi - 1.57));
        }
        for (int j = 800; j < 1600; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(150);
            i++;
        }
        for (float unghi = 4.71F; unghi < 6.28; unghi = (float) (unghi + 0.005)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 200 + 1600));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 200 + 350));
            i++;
            traseuBile[i].SetUnghi((float) (unghi - 4.71));
        }
        for (int j = 350; j < 750; j++) {
            traseuBile[i].SetCoordX(1800);
            traseuBile[i].SetCoordY(j);
            i++;
            traseuBile[i].SetUnghi(1.57F);
        }
        for (float unghi = 0; unghi < 1.57; unghi = (float) (unghi + 0.005)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 200 + 1600));//inmultesti cu raza cercului
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 200 + 750));//si aduni cu centrul cercului
            i++;
            traseuBile[i].SetUnghi((float) (unghi + 1.57));
        }
        for (int j = 1600; j > 800; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(950);
            i++;
            traseuBile[i].SetUnghi(3.14F);
        }
        for (float unghi = 1.57F; unghi < 3.14; unghi = (float) (unghi + 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 800));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 850));
            i++;
            traseuBile[i].SetUnghi((float) (unghi + 1.57));
        }
        for (int j = 850; j > 500; j--) {
            traseuBile[i].SetCoordX(700);
            traseuBile[i].SetCoordY(j);
            i++;
            traseuBile[i].SetUnghi(4.71F);
        }
        for (float unghi = 3.14F; unghi < 6.28; unghi = (float) (unghi + 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 800));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 500));
            i++;
            traseuBile[i].SetUnghi((float) (unghi + 1.57));
        }
        for (int j = 500; j < 750; j++) {
            traseuBile[i].SetCoordX(900);
            traseuBile[i].SetCoordY(j);
            i++;
            traseuBile[i].SetUnghi(1.57F);
        }
        for (float unghi = 3.14F; unghi > 1.57; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 1000));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 750));
            i++;
            traseuBile[i].SetUnghi((float) (unghi - 1.57));
        }
        for (int j = 1000; j < 1600; j++) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(850);
            i++;
        }
        for (float unghi = 1.57F; unghi > 0; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 1600));//inmultesti cu raza cercului
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 750));//si aduni cu centrul cercului
            i++;
            traseuBile[i].SetUnghi((float) (unghi - 1.57));
        }
        for (int j = 750; j > 400; j--) {
            traseuBile[i].SetCoordX(1700);
            traseuBile[i].SetCoordY(j);
            i++;
            traseuBile[i].SetUnghi(4.71F);
        }
        for (float unghi = 6.28F; unghi > 4.71; unghi = (float) (unghi - 0.01)) {
            traseuBile[i].SetCoordX((float) (Math.cos(unghi) * 100 + 1600));
            traseuBile[i].SetCoordY((float) (Math.sin(unghi) * 100 + 400));
            i++;
            traseuBile[i].SetUnghi((float) (unghi - 1.57));
        }
        for (int j = 1600; j > 250; j--) {
            traseuBile[i].SetCoordX(j);
            traseuBile[i].SetCoordY(300);
            i++;
            traseuBile[i].SetUnghi(3.14F);
        }
        System.out.println("Traseul are "+i+"puncte");
    }
    private void resizeTraseu(float sizeX, float sizeY){
        for(int i=0;i<traseuBile.length;i++){
            traseuBile[i].SetCoordX(traseuBile[i].GetCoordX()*sizeX);
            traseuBile[i].SetCoordY(traseuBile[i].GetCoordY()*sizeY);
        }
    }
}