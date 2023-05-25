package Backbone;

public class VolumPreaMareException extends Exception {
    public VolumPreaMareException(int volum){
        super("Volumul "+volum+" este prea mare");
    }
}
