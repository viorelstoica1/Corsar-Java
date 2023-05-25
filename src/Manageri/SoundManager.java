package Manageri;

import Backbone.VolumPreaMareException;
import Backbone.VolumPreaMicException;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    public static void playSound(String path, int decibeli, boolean loop){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            clip.open(ais);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            try{
                volume.setValue(decibeli + SetariManager.get().getVolum());
            }
            catch(IllegalArgumentException e){
                if(decibeli + SetariManager.get().getVolum() < 0){
                    throw new VolumPreaMicException((int) (decibeli + SetariManager.get().getVolum()));
                }
                else throw new VolumPreaMareException((int) (decibeli + SetariManager.get().getVolum()));
            }
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.loop(0);
            }
        }
        catch(VolumPreaMareException | VolumPreaMicException e){
            System.out.println(e.getMessage());
            System.out.println("Play la volum normal");
            playSound(path, (int) (decibeli - SetariManager.get().getVolum()), loop);
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("Fisierul audio "+path+" nu exista sau nu e valid!");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
