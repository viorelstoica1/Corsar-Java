package Obiecte;

public class GameObject {
    private float x, y;//coordonate
    private float unghi;//unghi in GRADE
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
        unghi = angel%360;
    }

    public GameObject Copiaza(GameObject x){
        this.x = x.x;
        this.y = x.y;
        this.unghi = x.unghi;
        return this;
    }

    public boolean DirectieColiziune(GameObject de_introdus) {
        float x1, x2;//+90 pentru ca bilele din asta sunt randate cu -90 ca sa arate corect
        x1 = (float) (Math.cos(Math.toRadians(GetUnghi()+90) * -1) * GetCoordX() - Math.sin(Math.toRadians(GetUnghi()+90) * -1) * GetCoordY());
        //x1 = (float) (Math.cos(GetUnghi() * -1) * GetCoordX() - Math.sin(GetUnghi() * -1) * GetCoordY());
        x2 = (float) (Math.cos(Math.toRadians(GetUnghi()+90) * -1) * de_introdus.GetCoordX() - Math.sin(Math.toRadians(GetUnghi()+90) * -1) * de_introdus.GetCoordY());
        //x2 = (float) (Math.cos(GetUnghi() * -1) * de_introdus.GetCoordX() - Math.sin(GetUnghi() * -1) * de_introdus.GetCoordY());
        //System.out.println("Obuzul rotit are pozitia "+x2+" iar bila rotita are pozitia "+x1+", la unghiul "+unghi);
        return !(x1 > x2);//stanga
    }
    public float DistantaPatrat(GameObject obiect){
        float xa = this.x - obiect.x;
        float ya = this.y - obiect.y;
        return xa * xa + ya * ya;
    }
}
