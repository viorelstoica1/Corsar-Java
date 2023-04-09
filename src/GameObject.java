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
        x1 = (float) (Math.cos(Math.toRadians(GetUnghi()) * -1) * GetCoordX() - Math.sin(Math.toRadians(GetUnghi()) * -1) * GetCoordY());
        x2 = (float) (Math.cos(Math.toRadians(GetUnghi()) * -1) * de_introdus.GetCoordX() - Math.sin(Math.toRadians(GetUnghi()) * -1) * de_introdus.GetCoordY());
        System.out.println("Obuzul rotit are pozitia "+x2+" iar bila rotita are pozitia "+x1+", la unghiul "+unghi+", introduc in stanga");
        return !(x1 > x2);//stanga
    }
    public float DistantaPatrat(GameObject obiect){
        float xa = this.x - obiect.x;
        float ya = this.y - obiect.y;
        return xa * xa + ya * ya;
    }
}
