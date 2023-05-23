package Obiecte;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spritesheet extends Textura{
    protected final int numar_cadre;
    float cadru_curent;
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

    }
    public Spritesheet(BufferedImage imagine, int nrcadre, int coloane,int liniiComplet, float poz_x, float poz_y, float angel, int inceputTex) {
        super(imagine, poz_x, poz_y, angel);
        numar_cadre = nrcadre;
        cadru_curent = 0;
        pozitii_cadre = new Rectangle[numar_cadre];
        int linii = (int)Math.ceil((float)(nrcadre+inceputTex)/coloane);
        //System.out.println("linii = "+linii+" coloane = "+coloane);
        for(int i=(int)Math.ceil((float)(inceputTex/linii));i<linii;i++){
            if(i==(int)Math.ceil((float)(inceputTex/linii))){
                for (int j = inceputTex%coloane;j < coloane && i*coloane+j <nrcadre+inceputTex;j++){
                    pozitii_cadre[i*coloane+j-inceputTex] = new Rectangle();
                    pozitii_cadre[i*coloane+j-inceputTex].x = (int)Math.floor((float)this.GetMarimeTexX()*(j) / coloane);
                    pozitii_cadre[i*coloane+j-inceputTex].y = (int)Math.floor((float)this.GetMarimeTexY()*(i) / liniiComplet);
                    pozitii_cadre[i*coloane+j-inceputTex].width = this.GetMarimeTexX()/coloane;
                    pozitii_cadre[i*coloane+j-inceputTex].height = this.GetMarimeTexY()/liniiComplet;
                    //System.out.print("("+pozitii_cadre[i*coloane+j-inceputTex].x+","+pozitii_cadre[i*coloane+j-inceputTex].y+")");
                    //System.out.print("W:"+pozitii_cadre[i*coloane+j-inceputTex].width+","+pozitii_cadre[i*coloane+j-inceputTex].height);
                }
            }else{
                for (int j = 0;j < coloane && i*coloane+j <nrcadre+inceputTex;j++){
                    pozitii_cadre[i*coloane+j-inceputTex] = new Rectangle();
                    pozitii_cadre[i*coloane+j-inceputTex].x = (int)Math.floor((float)this.GetMarimeTexX()*(j) / coloane);
                    pozitii_cadre[i*coloane+j-inceputTex].y = (int)Math.floor((float)this.GetMarimeTexY()*(i) / liniiComplet);
                    pozitii_cadre[i*coloane+j-inceputTex].width = this.GetMarimeTexX()/coloane;
                    pozitii_cadre[i*coloane+j-inceputTex].height = this.GetMarimeTexY()/liniiComplet;
                    System.out.print("("+pozitii_cadre[i*coloane+j-inceputTex].x+","+pozitii_cadre[i*coloane+j-inceputTex].y+")");
                    //System.out.print("W:"+pozitii_cadre[i*coloane+j-inceputTex].width+","+pozitii_cadre[i*coloane+j-inceputTex].height);
                }
            }

        }

    }
    public Spritesheet(Spritesheet sprite,float poz_x,float poz_y,float angel){
        super(sprite.imagineRaw,poz_x,poz_y,angel);
        this.numar_cadre = sprite.numar_cadre;
        this.cadru_curent = 0;
        this.pozitii_cadre = sprite.pozitii_cadre;
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
    public void SetCadru(float x)
    {
        if(x >= numar_cadre){
            System.out.println("WARNING depasire marime spritesheet de marime "+numar_cadre+" cu indexul "+x);
        }
        cadru_curent = x%numar_cadre;
    }
    public void CresteCadru(float x) {
        cadru_curent+=x;
        if (cadru_curent > numar_cadre - 1)
        {
            cadru_curent = 0;
        }
        if (cadru_curent < 0) {
            cadru_curent = numar_cadre-1;
        }
    }

    public boolean CheckColiziuneBila(Spritesheet bila){
        return DistantaPatrat(bila) <= Math.pow((bila.GetMarimeSpriteX() + (float) GetMarimeSpriteX()) / 2, 2);
    }

    @Override
    public int CenterX(){
        return (int)(this.GetCoordX() - this.GetMarimeSpriteX()/2);
    }
    @Override
    public int CenterY(){
        return (int)(this.GetCoordY() - this.GetMarimeSpriteY()/2);
    }

    @Override public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.rotate(Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        g2d.drawImage(imagineRaw.getSubimage(pozitii_cadre[(int) cadru_curent].x,pozitii_cadre[(int) cadru_curent].y,pozitii_cadre[(int) cadru_curent].width,pozitii_cadre[(int) cadru_curent].height),CenterX(),CenterY(),null);
        g2d.rotate(-Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
    }
    @Override public Spritesheet resize(int newW, int newH) {//nu merge exceptional
        Image tmp = imagineRaw.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        imagineRaw = dimg;
        g2d.dispose();
        for(int i=0;i<numar_cadre;i++){
                pozitii_cadre[i].x = (int)Math.floor(newW * pozitii_cadre[i].x / (float)this.GetMarimeTexX());
                pozitii_cadre[i].y = (int)Math.floor(newH * pozitii_cadre[i].y / (float)this.GetMarimeTexY());
                pozitii_cadre[i].width = pozitii_cadre[i].width*newW/this.GetMarimeTexX();
                pozitii_cadre[i].height = pozitii_cadre[i].height*newH/this.GetMarimeTexY();
        }
        marime_x = newW;
        marime_y = newH;
        return this;
    }
}
