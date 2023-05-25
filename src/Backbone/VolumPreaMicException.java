package Backbone;

public class VolumPreaMicException extends Exception {
    public VolumPreaMicException(int volum){
        super("Volumul "+volum+" este prea mic");
    }
}
