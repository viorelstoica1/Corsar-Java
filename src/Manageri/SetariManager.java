package Manageri;

public class SetariManager {
    private static SetariManager singleton;
    private static float [][]SetariNivele;
    private static float []SetariGenerale;
    private SetariManager(){
        SetariNivele = new float[3][10];
        SetariGenerale = new float[8];
        BazaDateManager.CitireSetariNivele(SetariNivele);
        BazaDateManager.CitireSetariGenerale(SetariGenerale);
        /*for(int i = 0; i < 3; i++){
            for( int j = 0; j < 10; j++ ){
                System.out.print(SetariNivele[i][j]+" ");
            }
            System.out.println(" ");
        }*/
    }
    public static SetariManager get(){
        if(singleton == null){
            singleton = new SetariManager();
        }
        return singleton;
    }
    public float getVitezaGeneralaSir(int nivel){
        return SetariNivele[nivel-1][0];
    }
    public float getNumarBileSirNou(int nivel){
        return SetariNivele[nivel-1][1];
    }
    public float getIndexRapid(int nivel){
        return SetariNivele[nivel-1][2];
    }
    public float getIndexIncet(int nivel){
        return SetariNivele[nivel-1][3];
    }
    public float getIndexFinal(int nivel){
        return SetariNivele[nivel-1][4];
    }
    public float getVitezaMaxima(int nivel){
        return SetariNivele[nivel-1][5];
    }
    public float getVitezaMinima(int nivel){
        return SetariNivele[nivel-1][6];
    }
    public float getFactorCrestereSir(int nivel){
        return SetariNivele[nivel-1][7];
    }
    public float getNumarMinimBile(int nivel){return SetariNivele[nivel-1][8];}
    public float getAcceleratieBile(int nivel){
        return SetariNivele[nivel-1][9];
    }
    public float getVolum(){return SetariGenerale[0];}
    public float getVitezaTragereTun(){return SetariGenerale[1];}
    public float getVitezaAnimatieTun(){return SetariGenerale[2];}
    public float getVitezatragereCapcana(){return SetariGenerale[3];}
    public float getCooldownCapcana(){return SetariGenerale[4];}
    public float getRazaProiectilFoc(){return SetariGenerale[5];}
    public float getValoareBani(){return SetariGenerale[6];}
    public float getVitezaLoadingScreen(){return SetariGenerale[7];}
}
