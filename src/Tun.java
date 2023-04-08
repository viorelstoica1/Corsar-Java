import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Tun extends Textura{
    private Proiectil proiectilIncarcat,proiectilRezerva;
    private boolean gataDeTras;
    public int vitezaTragere;
    private int limitaStanga, limitaDreapta, limitaJos, limitaSus;
    private Textura tunSus;
    public Tun(BufferedImage imagine, BufferedImage imagineSus, float poz_x, float poz_y, float angel, int viteza) {
        super(imagine, poz_x, poz_y, angel);
        vitezaTragere = viteza;
        proiectilIncarcat = null;
        proiectilRezerva = null;
        tunSus = new Textura(imagineSus,0,0,GetUnghi());
        System.out.println(limitaJos + " " + limitaSus + " " + limitaStanga + " " + limitaDreapta);
    }
    public void SetTexSus(BufferedImage imagineSus){
        tunSus = new Textura(imagineSus,0,0,GetUnghi());
    }
    public void SetLimite( int stangaX, int dreaptaX, int josY, int susY){
        limitaDreapta = dreaptaX;
        limitaStanga = stangaX;
        limitaJos = josY;
        limitaSus = susY;
    }
    void UpdateTun(int mousex, int mousey){
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
        proiectilIncarcat.SetCoordX(this.GetCoordX());
        proiectilIncarcat.SetCoordY((this.GetCoordY()-35));
        proiectilRezerva.SetCoordX(this.GetCoordX());
        proiectilRezerva.SetCoordY(this.GetCoordY());
        tunSus.SetCoordX(GetCoordX());
        tunSus.SetCoordY(GetCoordY());

        proiectilIncarcat.SetCoordX((float) (GetCoordX() + Math.cos(Math.toRadians(GetUnghi()-90)) * 50));
        proiectilIncarcat.SetCoordY((float) (GetCoordY() + Math.sin(Math.toRadians(GetUnghi()-90)) * 50));
        proiectilRezerva.SetCoordX((float) (GetCoordX() - Math.cos(Math.toRadians(GetUnghi()-90)) * 15));
        proiectilRezerva.SetCoordY((float) (GetCoordY() - Math.sin(Math.toRadians(GetUnghi()-90)) * 15));
    }
    void Trage(){
        this.GetProiectilIncarcat().viteza_y = -vitezaTragere;
        proiectilIncarcat.viteza_x = (float) (Math.cos(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
        proiectilIncarcat.viteza_y = (float) (Math.sin(Math.toRadians(GetUnghi()-90)) * vitezaTragere);
        proiectilIncarcat.SetUnghi(GetUnghi());
        System.out.println("Proiectil: "+proiectilIncarcat.viteza_x+" "+ proiectilIncarcat.viteza_y);
    }
    Proiectil GetProiectilIncarcat(){
        return proiectilIncarcat;
    }
    Proiectil GetProiectilRezerva(){
        return proiectilRezerva;
    }
    void SetProiectilCurent(Proiectil p){
        proiectilIncarcat = p;
    }
    void SetProiectilRezerva(Proiectil p){
        proiectilRezerva = p;
    }
    void CicleazaProiectil(Proiectil p) {
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = p;
    }
    void SetGataTras(boolean x) { gataDeTras = x; };
    boolean isGataDeTras() { return gataDeTras; };
    void SchimbaOrdineProiectile(){
        Proiectil aux = proiectilIncarcat;
        proiectilIncarcat = proiectilRezerva;
        proiectilRezerva = aux;
    }
    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(proiectilIncarcat != null)
            proiectilIncarcat.paintComponent(g);
        if(proiectilRezerva != null)
            proiectilRezerva.paintComponent(g);
        tunSus.paintComponent(g);
    }
}
