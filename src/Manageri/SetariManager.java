package Manageri;


public class SetariManager {
    private static SetariManager singleton;
    private static float [][]SetariNivele;
    private SetariManager(){
        SetariNivele = new float[3][10];
        BazaDateManager.CitireSetariNivele(SetariNivele);
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
    public float getNumarMinimBile(int nivel){
        return SetariNivele[nivel-1][8];
    }
    public float getAcceleratieBile(int nivel){
        return SetariNivele[nivel-1][9];
    }
}
