public class Scoruri {
    private static Scoruri singletonScoruri;
    private final int[][] scoruri;
    private final String [][]nume;
    private final int nrNivele = 3, nrScoruriSalvate = 3;
    private Scoruri(){
        scoruri = new int[nrNivele][nrScoruriSalvate];
        nume = new String[nrNivele][nrScoruriSalvate];
        for(int i = 0; i< nrNivele; i++){
            for(int j = 0; j< nrScoruriSalvate; j++){
                scoruri[i][j] = i;
                nume[i][j] = "nimeni";
            }
        }
    }
    public static synchronized Scoruri get(){
        if(singletonScoruri == null){
            singletonScoruri = new Scoruri();
        }
        return singletonScoruri;
    }
    public int getValoareScor(int nivel, int pozitie){
        return scoruri[nivel][pozitie];
    }
    public String getnumeScor(int nivel, int pozitie){
        return nume[nivel][pozitie];
    }

    public void AdaugaScor(int nivel, int scorNou, String nume) {
        for(int i=0; i< nrScoruriSalvate; i++){
            if(scoruri[nivel][i] <= scorNou){
                //muta scorurile vechi mai jos
                for(int j = nrScoruriSalvate-1; j > i; j--){
                    scoruri[nivel][j] = scoruri[nivel][j-1];
                    this.nume[nivel][j] = this.nume[nivel][j-1];
                }
                //baga scor nou in pozitia buna
                scoruri[nivel][i] = scorNou;
                this.nume[nivel][i] = nume;
                break;
            }
        }
    }
}
