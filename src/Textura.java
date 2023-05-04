import java.awt.*;
import java.awt.image.BufferedImage;
public class Textura extends GameObject {
    protected int marime_x,marime_y;
    protected BufferedImage imagineRaw;
    public Textura(BufferedImage imagine, float poz_x, float poz_y, float angel){
        super(poz_x, poz_y, angel);
        imagineRaw = imagine;
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
    }

    public void SetTexRaw(BufferedImage imagine){
        imagineRaw = imagine;
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
    }

    public BufferedImage GetTex(){
        return imagineRaw;
    }

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
            g2d.drawImage(imagineRaw,CenterX(),CenterY(), null);
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
