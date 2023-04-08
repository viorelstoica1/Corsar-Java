public class GameObject {
    private float x, y;//coordonate
    private float unghi;//unghi in RADIANI
    public GameObject() {
        x = y = unghi = 0;
    }
    public GameObject(float poz_x, float poz_y, float angel ){
        x = poz_x;
        y = poz_y;
        unghi = angel;
    }
    public float GetCoordX(){
        return x;
    }
    public float GetCoordY(){
        return y;
    }
    public void SetCoordX(float _x){
        x = _x;
    }
    public void SetCoordY(float _y){
        y = _y;
    }
    public float GetUnghi(){
        return unghi;
    }
    public void SetUnghi(float angel){
        unghi = angel;
    }
//    public void Update() {
//
//    }
    public void Copiaza(GameObject x){
        this.x = x.x;
        this.y = x.y;
        this.unghi = x.unghi;
    }

    public boolean DirectieColiziune(GameObject de_introdus) {
        float x1, x2;
        x1 = (float) (Math.cos(GetUnghi() * -1) * GetCoordX() - Math.sin(GetUnghi() * -1) * GetCoordY());
        x2 = (float) (Math.cos(GetUnghi() * -1) * de_introdus.GetCoordX() - Math.sin(GetUnghi() * -1) * de_introdus.GetCoordY());
        //printf("Obuzul rotit are pozitia %f iar bila rotita are pozitia %f, la unghiul %f, introduc in stanga", x2, x1, membru->GetUnghi() * 180 / 3.14);
        //printf("Obuzul rotit are pozitia %f iar bila rotita are pozitia %f, la unghiul %f, introduc in dreapta", x2, x1, membru->GetUnghi() * 180 / 3.14);
        return !(x1 > x2);//stanga
    }
    public float DistantaPatrat(GameObject obiect){
        float xa = x - obiect.x;
        float ya = y - obiect.y;
        return xa * xa + ya * ya;
    }
}
