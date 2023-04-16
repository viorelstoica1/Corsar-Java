import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Tun extends Textura{
    private Proiectil proiectilIncarcat,proiectilRezerva;
    private boolean gataDeTras;
    public int vitezaTragere;
    private int limitaStanga, limitaDreapta, limitaJos, limitaSus;
    private Spritesheet tunJos;
    private int cadruAnimatie, vitezaAnimatie;
    public Tun(BufferedImage imagine, BufferedImage imagineSus, float poz_x, float poz_y, float angel, int viteza) {
        super(imagineSus,poz_x, poz_y, angel);
        tunJos = new Spritesheet(imagine,5,5,poz_x,poz_y,angel);
        vitezaTragere = viteza;
        proiectilIncarcat = null;
        proiectilRezerva = null;
        gataDeTras = true;
        cadruAnimatie = 0;
        vitezaAnimatie = 30;
    }
    public void SetTexSus(BufferedImage imagineSus){
        this.SetTexRaw(imagineSus);
    }
    public void SetLimite( int stangaX, int dreaptaX, int josY, int susY){
        limitaDreapta = dreaptaX;
        limitaStanga = stangaX;
        limitaJos = josY;
        limitaSus = susY;
    }
    public void UpdateTun(int mousex, int mousey){
        if(!gataDeTras){
            cadruAnimatie++;
            if(cadruAnimatie >= vitezaAnimatie){
                cadruAnimatie = 0;
                gataDeTras = true;
            }
        }
        SetCoordX(mousex);
        SetCoordY(mousey);
        if(GetCoordX() < limitaStanga){
            SetCoordX(limitaStanga);
        }else if(GetCoordX() > limitaDreapta){
            SetCoordX((limitaDreapta));
        }
        if(GetCoordY() < limitaSus){
            SetCoordY(limitaSus);
        }else if(GetCoordY() > limitaJos){
            SetCoordY(limitaJos);
        }
        tunJos.SetCoordX((float) (GetCoordX()-GetMarimeTexX()/2.25));
        tunJos.SetCoordY(GetCoordY());
        tunJos.SetCadru((int) Math.floor((float)cadruAnimatie/(float)(vitezaAnimatie/tunJos.GetNrCadre())));
        if(proiectilIncarcat != null){
            proiectilIncarcat.SetCoordX((float) (GetCoordX() + Math.cos(Math.toRadians(GetUnghi()-90)) * 50));
            proiectilIncarcat.SetCoordY((float) (GetCoordY() + Math.sin(Math.toRadians(GetUnghi()-90)) * 50));
        }
        if(proiectilRezerva != null){
            proiectilRezerva.SetCoordX((float) (GetCoordX() - Math.cos(Math.toRadians(GetUnghi()-90)) * 15));
            proiectilRezerva.SetCoordY((float) (GetCoordY() - Math.sin(Math.toRadians(GetUnghi()-90)) * 15));
        }
    }
    void Trage(){
        if(gataDeTras){
            gataDeTras = false;
            this.GetProiectilIncarcat().viteza_y = -vitezaTragere;
            proiectilIncarcat.viteza_x = (float) (Math.cos(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
            proiectilIncarcat.viteza_y = (float) (Math.sin(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
            proiectilIncarcat.SetUnghi(GetUnghi());
            System.out.println("Proiectil: "+proiectilIncarcat.viteza_x+" "+ proiectilIncarcat.viteza_y);
            proiectilIncarcat = null;
        }
    }
    public Proiectil GetProiectilIncarcat(){
        return proiectilIncarcat;
    }
    public Proiectil GetProiectilRezerva(){
        return proiectilRezerva;
    }
    public void SetProiectilCurent(Proiectil p){
        proiectilIncarcat = p;
    }
    public void SetProiectilRezerva(Proiectil p){
        proiectilRezerva = p;
    }
    public void CicleazaProiectil(Proiectil p) {
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = p;
        System.out.println("cicleaza proiectil");
    }
    public void SetGataTras(boolean x) { gataDeTras = x; };
    public boolean isGataDeTras() { return gataDeTras; };
    public void SchimbaOrdineProiectile(){
        Proiectil aux = proiectilIncarcat;
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = aux;
    }
    public void paintComponent(Graphics g){
        if(proiectilIncarcat != null)
            proiectilIncarcat.paintComponent(g);
        if(proiectilRezerva != null)
            proiectilRezerva.paintComponent(g);
        tunJos.paintComponent(g);
        super.paintComponent(g);

    }

    public void SetTexJos(BufferedImage tunJos) {
        this.tunJos = new Spritesheet(tunJos,5,5,this.tunJos.GetCoordX(),this.tunJos.GetCoordY(),this.GetUnghi());
    }
}
