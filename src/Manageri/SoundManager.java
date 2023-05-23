package Manageri;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundManager {
    public static void playSound(String path, int decibeli, boolean loop){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            clip.open(ais);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(decibeli);
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.loop(0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
