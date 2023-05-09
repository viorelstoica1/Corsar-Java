import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Level extends JPanel {
    public String FrameTime = "Frame: 0";
    protected boolean firstPaint = true;
    protected static int rezolutieX, rezolutieY;
    protected final Textura fundal, cursorPrincipal, cursorSecundar;
    protected static List<Proiectil> listaProiectile;
    protected int scena = 1, scor = 0;
    protected final Tun tunar;
    protected final int targetScreenX = 1920, targetScreenY = 1080;
    protected GameObject[] traseuBile;
    protected final Sir sirBile;
    protected final Font fontScor;
    protected final LinkedList<GameObject> pozitiiProiectile, pozitiiBile;
    protected final GameObject pozitieTun, pozitieCursor;
    protected final ResourceManager resurse = ResourceManager.get();
    private int cooldownSwap = 0;

    public Level(int Width, int Height) {
        this.setLayout(null);
        pozitiiBile = new LinkedList<>();
        pozitiiProiectile = new LinkedList<>();
        pozitieTun = new GameObject();
        pozitieCursor = new GameObject();
        rezolutieX = Width;
        rezolutieY = Height;
        ResourceManager.get();
        AlocareTraseuBile();
        fontScor = new Font("TimesRoman", Font.PLAIN, 25);
        tunar = new Tun(resurse.getTunJos().GetTex(), resurse.getTunSus().GetTex(), MouseStatus.mousex, 0, 270, 20);
        fundal = new Textura(resurse.getFundal(1).GetTex(), 0, 0, 0);
        cursorPrincipal = new Textura(resurse.getTexturaCursorPrincipal().GetTex(),0,0,270);
        cursorSecundar = new Textura(resurse.getTexturaCursorSecundar().GetTex(), 0,0,270);
        listaProiectile = new ArrayList<>();
        sirBile = new Sir(traseuBile, 10, 1, 0.5f,0.2f,2500,700,3075);
        tunar.resizeTun( rezolutieX * tunar.GetTex().getWidth() / targetScreenX,rezolutieY * tunar.GetTex().getHeight() / targetScreenY);
        fundal.SetCoordX((float) rezolutieX / 2);
        fundal.SetCoordY((float) rezolutieY / 2);
    }
    protected void repaintBackground(int x, int y, int marimeX, int marimeY, Graphics g){
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
    }

    public abstract void onStart();

    protected void salvarePozitii(){
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
        scena = 1;
        salvarePozitii();
        //actualizari clickuri
        if (MouseStatus.clickStanga) {
            if(tunar.isGataDeTras()){
                listaProiectile.add(tunar.GetProiectilIncarcat());
                tunar.Trage();
            }
        }
        if (MouseStatus.middleMouse) {
            scena = 0;
            LoadingScreen.moveIn = true;
        }
        if (MouseStatus.clickDreapta) {
            if(tunar.isGataDeTras() && cooldownSwap == 0){
                tunar.SchimbaOrdineProiectile();
                SoundManager.playSound("src/resources/sunete/bullet_swap.wav", -10, false);
                cooldownSwap = 10;
            }
        }
        if(cooldownSwap !=0){
            cooldownSwap--;
        }
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

        tunar.UpdateTun(MouseStatus.mousex, MouseStatus.mousey);
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
            if(Math.random()>0.85){
                tunar.SetProiectilRezerva(new ProiectilBila((Spritesheet)ResourceManager.get().getTexturaBila("curcubeu"),tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 32));
            }
        }
        if(tunar.isGataDeTras() && bilaDinSir != null && !sirBile.isCuloareInSir(tunar.GetProiectilIncarcat().GetTex()) && (tunar.GetProiectilIncarcat().getClass() == ProiectilBila.class)){
            bilaDinSir = sirBile.getTexturaBilaRandom();
            tunar.CicleazaProiectil(new ProiectilBila(bilaDinSir, tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
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
        repaintBackground((int)(pozitieTun.GetCoordX() -tunar.marime_x/2 - 1 - Math.cos(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexX()/2.25),(int)(pozitieTun.GetCoordY() -tunar.marime_y/2 - 1- Math.sin(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexY()/2.25), tunjos.GetMarimeSpriteX() + 2,tunjos.GetMarimeSpriteY() + 2,g);
        //g.drawRect((int)(pozitieTun.GetCoordX() -tunar.marime_x/2 - Math.cos(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexX()/2.25), (int)(pozitieTun.GetCoordY() -tunar.marime_y/2 - Math.sin(Math.toRadians(tunjos.GetUnghi()+90))*tunar.GetMarimeTexY()/2.25), tunjos.GetMarimeSpriteX(), tunjos.GetMarimeTexY());
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

    protected abstract void AlocareTraseuBile();
    public void ResetNivel(){
        listaProiectile.clear();
        sirBile.getListaBile().clear();
        scor = 0;
        tunar.ResetTunar();
        tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        tunar.CicleazaProiectil(new ProiectilBila((Spritesheet) resurse.getBilaRandom(), tunar.GetCoordX(), tunar.GetCoordY(), tunar.GetUnghi(), tunar.vitezaTragere, 120));
        cursorPrincipal.SetCoordX(0);
        cursorPrincipal.SetCoordY(0);
        cursorSecundar.SetCoordX(0);
        cursorSecundar.SetCoordY(0);
        sirBile.lost = false;
        //TODO vezi de ce dracu nu merge viteza dupa ce pierd
        sirBile.viteza_min = sirBile.viteza_min_save;
        sirBile.viteza_max = sirBile.viteza_max_save;
    }
}