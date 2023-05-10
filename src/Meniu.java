import javax.swing.*;
import java.awt.*;

public class Meniu extends JPanel{
    private final Textura FundalMeniu;
    private final Textura SelectiiMeniu;
    private int selectedButton = 0;
    Buton buton1, buton2, buton3;
    public Meniu(){
        FundalMeniu = new Textura(ResourceManager.get().getMeniu("Fundal").GetTex(),(float)Application.getScreenWidth()/2,(float)Application.getScreenHeight()/2,0);
        SelectiiMeniu = new Textura(ResourceManager.get().getMeniu("Selectii").GetTex(),(float)Application.getScreenWidth()*2/3,(float)Application.getScreenHeight()/2,0);
        buton1 = new Buton((int) SelectiiMeniu.GetCoordX()-SelectiiMeniu.GetMarimeTexX()/2,(int) SelectiiMeniu.GetCoordY()-SelectiiMeniu.GetMarimeTexY()*3/10,SelectiiMeniu.GetMarimeTexX(),100);
        buton2 = new Buton((int) SelectiiMeniu.GetCoordX()-SelectiiMeniu.GetMarimeTexX()/2,(int) SelectiiMeniu.GetCoordY()+SelectiiMeniu.GetMarimeTexY()/20,SelectiiMeniu.GetMarimeTexX(),100);
        buton3 = new Buton((int) SelectiiMeniu.GetCoordX()-SelectiiMeniu.GetMarimeTexX()/2,(int) SelectiiMeniu.GetCoordY()+SelectiiMeniu.GetMarimeTexY()*3/10,SelectiiMeniu.GetMarimeTexX(),100);
    }
    public int UpdateMeniu(){
        //System.out.println("mousex "+MouseStatus.mousex+" mousey "+MouseStatus.mousey);
        if(buton1.isSelected(MouseStatus.mousex,MouseStatus.mousey)){
            //cursorul e pe butonul de sus
            selectedButton = 1;
            //System.out.println("cursorul e pe butonul de sus");
            if(MouseStatus.clickStanga){
                System.out.println("Buton Play");
                LoadingScreen.moveIn = true;
                return 1;
            }
        }else if(buton2.isSelected(MouseStatus.mousex,MouseStatus.mousey)){
            //cursorul e pe butonul din mijloc
            selectedButton = 2;
            if(MouseStatus.clickStanga){
                System.out.println("Buton Credite");
            }
            //System.out.println("cursorul e pe butonul din mijloc");
        }else if(buton3.isSelected(MouseStatus.mousex,MouseStatus.mousey)){
            //cursorul e pe butonul de jos
            selectedButton = 3;
            if(MouseStatus.clickStanga){
                System.out.println("Buton iesire");
                LoadingScreen.moveIn = true;
                return -1;
            }
            //System.out.println("cursorul e pe butonul de jos");
        }else{
            //cursorul e nu e niciun buton
            selectedButton = 0;
        }
        return 0;
    }

    public void paintComponent(Graphics g) {
        FundalMeniu.paintComponent(g);
        SelectiiMeniu.paintComponent(g);
        /*g.drawRect(buton1.x,buton1.y,buton1.width,buton1.height);
        g.drawRect(buton2.x,buton2.y,buton2.width,buton2.height);
        g.drawRect(buton3.x,buton3.y,buton3.width,buton3.height);*/
    }
}
