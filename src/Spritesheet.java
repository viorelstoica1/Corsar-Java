import java.awt.*;
import java.awt.image.BufferedImage;

public class Spritesheet extends Textura{
    private final int numar_cadre;
    int cadru_curent;
    private final Rectangle[] pozitii_cadre;
    public Spritesheet(BufferedImage imagine, int nrcadre, int coloane, float poz_x, float poz_y, float angel) {
        super(imagine, poz_x, poz_y, angel);
        numar_cadre = nrcadre;
        cadru_curent = 0;
        pozitii_cadre = new Rectangle[numar_cadre];
        int linii = (int)Math.ceil((float)nrcadre/coloane);
        //System.out.println("linii = "+linii+" coloane = "+coloane);
        for(int i=0;i<linii;i++){
            for (int j = 0;j < coloane && i*coloane+j <nrcadre;j++){
                pozitii_cadre[i*coloane+j] = new Rectangle();
                pozitii_cadre[i*coloane+j].x = (int)Math.floor((float)this.GetMarimeTexX()*j / coloane);
                pozitii_cadre[i*coloane+j].y = (int)Math.floor((float)this.GetMarimeTexY()*i / linii);
                pozitii_cadre[i*coloane+j].width = this.GetMarimeTexX()/coloane;
                pozitii_cadre[i*coloane+j].height = this.GetMarimeTexY()/linii;
                //System.out.print("("+pozitii_cadre[i*coloane+j].x+","+pozitii_cadre[i*coloane+j].y+")");
            }
        }
        //imagineRotita = new BufferedImage(GetMarimeSpriteX(),GetMarimeSpriteY(),typeOfImage);
        //contextGrafic = imagineRotita.createGraphics();
        //contextGrafic.setBackground(new Color(255, 255, 255, 0));
        //System.out.println("Am construit un spritesheet cu "+numar_cadre+" cadre");
    }
    public Spritesheet(Spritesheet sprite,float poz_x,float poz_y,float angel){
        super(sprite.imagineRaw,poz_x,poz_y,angel);
        System.out.println("Copiere sprite");
        this.numar_cadre = sprite.numar_cadre;
        this.cadru_curent = 0;
        this.pozitii_cadre = sprite.pozitii_cadre;
        //imagineRotita = sprite.imagineRotita;
        //contextGrafic = imagineRotita.createGraphics();
        //this.contextGrafic = sprite.contextGrafic;
        //contextGrafic.setBackground(new Color(255, 255, 255, 0));
        //System.out.println("Am COPIAT un spritesheet cu "+numar_cadre+" cadre");
    }
    public int GetMarimeSpriteX()
    {
        return this.pozitii_cadre[0].width;
    }
    public int GetMarimeSpriteY()
    {
        return this.pozitii_cadre[0].height;
    }
    public int GetNrCadre()
    {
        return numar_cadre;
    }
    public void SetCadru(int x)
    {
        cadru_curent = x;
    }
    public void CresteCadru() {
        if (cadru_curent == numar_cadre - 1)
        {
            cadru_curent = 0;
        }
        else cadru_curent++;
    }
    public void ScadeCadru() {
        if (cadru_curent == 0) {
            cadru_curent = numar_cadre-1;
        }
        else cadru_curent--;
    }
    @Override int CenterX(){
        return (int)(this.GetCoordX() - this.GetMarimeSpriteX()/2);
    }
    @Override int CenterY(){
        return (int)(this.GetCoordY() - this.GetMarimeSpriteY()/2);
    }

    /*@Override public BufferedImage rotate() {*/
        /*int typeOfImage = imagineRaw.getType();
        BufferedImage newImageFromBuffer = new BufferedImage(GetMarimeSpriteX(), GetMarimeSpriteY(), typeOfImage);
        Graphics2D graphics2D = newImageFromBuffer.createGraphics();
        graphics2D.rotate(Math.toRadians(GetUnghi()), (float)this.pozitii_cadre[0].width / 2, (float)this.pozitii_cadre[0].height / 2);
        graphics2D.drawImage(imagineRaw.getSubimage(pozitii_cadre[cadru_curent].x,pozitii_cadre[cadru_curent].y,pozitii_cadre[cadru_curent].width,pozitii_cadre[cadru_curent].height), null, 0, 0);
        graphics2D.dispose();
        return newImageFromBuffer;*/
        /*contextGrafic.clearRect(0,0,pozitii_cadre[cadru_curent].width,pozitii_cadre[cadru_curent].height);
        contextGrafic.rotate(Math.toRadians(GetUnghi() - unghiRandat), pozitii_cadre[cadru_curent].width/2, pozitii_cadre[cadru_curent].height/2);
        unghiRandat = GetUnghi();
        contextGrafic.drawImage(imagineRaw.getSubimage(pozitii_cadre[cadru_curent].x,pozitii_cadre[cadru_curent].y,pozitii_cadre[cadru_curent].width,pozitii_cadre[cadru_curent].height), null, 0, 0);
        return imagineRotita;

    }*/
    @Override public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.rotate(Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        g2d.drawImage(imagineRaw.getSubimage(pozitii_cadre[cadru_curent].x,pozitii_cadre[cadru_curent].y,pozitii_cadre[cadru_curent].width,pozitii_cadre[cadru_curent].height),CenterX(),CenterY(),null);
        g2d.rotate(-Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        //g.drawImage(imagineRaw.getSubimage(pozitii_cadre[cadru_curent].x,pozitii_cadre[cadru_curent].y,pozitii_cadre[cadru_curent].width,pozitii_cadre[cadru_curent].height),CenterX(),CenterY(),null);
    }
}
