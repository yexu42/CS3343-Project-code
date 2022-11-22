import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound{
public void sound(int i) {
    try {
        //this class only support wav
        AudioInputStream audioInputStream;
        if(i==1){
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("audio/sound.wav"));
        }else{
        audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("audio/sound"+i+".wav"));
        }
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();


        //clip.loop(Clip.LOOP_CONTINUOUSLY);
        // If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY); 
        // If you want to stop the sound, then use clip.stop();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    

}

}