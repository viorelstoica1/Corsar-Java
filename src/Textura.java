import java.awt.*;
import java.awt.image.BufferedImage;
public class Textura extends GameObject{
    protected int marime_x,marime_y;//, typeOfImage;
    protected BufferedImage imagineRaw;//, imagineRotita;
    //protected Graphics2D contextGrafic;
    //float unghiRandat;
    public Textura(BufferedImage imagine, float poz_x, float poz_y, float angel){
        super(poz_x, poz_y, angel);
        imagineRaw = imagine;
        //typeOfImage = imagineRaw.getType();
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
        //imagineRotita = new BufferedImage(marime_x, marime_y, typeOfImage);
        //contextGrafic = imagineRotita.createGraphics();
        //contextGrafic.setBackground(new Color(255, 255, 255, 0));
        //contextGrafic.rotate(Math.toRadians(angel),marime_x/2,marime_y/2);
        //unghiRandat = 0;
        //System.out.println("Imagine incarcata");
    }
    /*public BufferedImage rotate() {
        contextGrafic.clearRect(0,0,marime_x,marime_y);
        contextGrafic.rotate(Math.toRadians(GetUnghi() - unghiRandat), marime_x/2, marime_y/2);
        unghiRandat = GetUnghi();
        contextGrafic.drawImage(imagineRaw, null, 0, 0);
        return imagineRotita;
    }*/
    public void SetTexRaw(BufferedImage imagine){
        imagineRaw = imagine;
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
    }

    public BufferedImage GetTex(){
        return imagineRaw;
    }
    /*public JLabel getLabel()
    {
        return tex;
    }*/
    public int GetMarimeTexX() {
        return marime_x;
    }
    public int GetMarimeTexY() {
        return marime_y;
    }
    //"centrul" unei texturi, folosit doar la randat!!
    int CenterX() {
        return (int)(this.GetCoordX() - this.GetMarimeTexX()/2);
    }
    //"centrul" unei texturi, folosit doar la randat!!
    int CenterY() {
        return (int)(this.GetCoordY() - this.GetMarimeTexY()  / 2);
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        if(GetUnghi() !=0){
            g2d.rotate(Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
            g2d.drawImage(imagineRaw,CenterX(),CenterY(),null);
            g2d.rotate(-Math.toRadians(GetUnghi()),GetCoordX(),GetCoordY());
        }else{
            g2d.drawImage(imagineRaw,CenterX(),CenterY(),null);
        }
        //g.drawImage(imagineRaw,CenterX(),CenterY(),marime_x,marime_y,null);
    }
    public Textura resize(int newW, int newH) {
        marime_x = newW;
        marime_y = newH;
        Image tmp = imagineRaw.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        imagineRaw = dimg;
        g2d.dispose();
        return this;
    }
}
