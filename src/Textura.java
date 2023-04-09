import java.awt.*;
import java.awt.image.BufferedImage;
public class Textura extends GameObject{
    protected int marime_x,marime_y;
    protected BufferedImage imagineRaw;
    public Textura(BufferedImage imagine, float poz_x, float poz_y, float angel){
        super(poz_x, poz_y, angel);
        imagineRaw = imagine;
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
        //System.out.println("Imagine incarcata");
    }
    public BufferedImage rotate(BufferedImage imagineAux) {
        int widthOfImage = imagineAux.getWidth();
        int heightOfImage = imagineAux.getHeight();
        int typeOfImage = imagineAux.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(GetUnghi()), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imagineAux, null, 0, 0);
        graphics2D.dispose();
        return newImageFromBuffer;
    }
    public void SetTexRaw(BufferedImage imagine){
        imagineRaw = imagine;
        marime_x = imagine.getWidth();
        marime_y = imagine.getHeight();
    }
    public boolean CheckColiziuneBila(Textura obuz){
        return DistantaPatrat(obuz) <= Math.pow((obuz.GetMarimeTexX() + (float) GetMarimeTexX()) / 2, 2);
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
        g.drawImage(this.rotate(imagineRaw),(int)CenterX(),(int)CenterY(),null);
    }
}
