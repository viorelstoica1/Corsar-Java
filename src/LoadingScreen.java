import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JPanel {
    private static Textura FundalLoading = new Textura(ResourceManager.get().getLoadscreen().imagineRaw,(float)Application.getScreenWidth()/2,(float)Application.getScreenHeight()/2,0);
    public static boolean moveIn = false, moveOut = false;
    public static float vitezaAnimatie = 0, acceleratieAnimatie = 0.1f;
    public static void Update(){
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
        float y = FundalLoading.GetCoordY();
        vitezaAnimatie += acceleratieAnimatie;
        if(moveIn){
            if(y < (float)Application.getScreenHeight()/2){
                FundalLoading.SetCoordY(y+vitezaAnimatie);
            }else{
                FundalLoading.SetCoordY((float)Application.getScreenHeight()/2);
                moveIn = false;
            }
        }else if(moveOut){
            if(y > -(float)Application.getScreenHeight()/2){
                FundalLoading.SetCoordY(y-vitezaAnimatie);
            }else{
                FundalLoading.SetCoordY(-(float)Application.getScreenHeight()/2);
                moveOut = false;
            }
        }else{
            vitezaAnimatie = 0;
        }
    }
    public static void ResetLoadingUp(){
        FundalLoading.SetCoordY(-(float)Application.getScreenHeight()/2);
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
    }
    public static void ResetLoadingDown(){
        FundalLoading.SetCoordY((float)Application.getScreenHeight()/2);
        FundalLoading.SetCoordX((float)Application.getScreenWidth()/2);
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        //g.fillRect(0,0,1920,1080);
        FundalLoading.paintComponent(g);
    }
    public static boolean isFinished(){
        return !moveIn && !moveOut;
    }
    public static int bottomY(){
        return (int) (FundalLoading.GetMarimeTexY() + FundalLoading.CenterY());
    }
}
