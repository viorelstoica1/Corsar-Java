package Backbone;

public class EmptyNameException extends Exception{
    public EmptyNameException(){
        super("Numele introdus nu poate fi gol!");
    }
}
