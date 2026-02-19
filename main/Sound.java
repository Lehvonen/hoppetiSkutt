package main;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/res/sound/Background.wav");
        soundURL[1] = getClass().getResource("/res/sound/Coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/LappaBiisi.wav");
        soundURL[3] = getClass().getResource("/res/sound/Collision.wav");
        soundURL[4] = getClass().getResource("/res/sound/Walking.wav");
        soundURL[5] = getClass().getResource("/res/sound/DoorTp.wav");

    }


    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f);
        }catch (Exception e){

        }


    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
