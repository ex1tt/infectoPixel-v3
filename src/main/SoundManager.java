package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {

    Panel gp;

    public SoundManager(Panel gp) {

        this.gp = gp;
    }

    // This method creates a playable clip...
    public Clip setSoundFile(String filePath) {

        try {
            File file = new File(filePath);

            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            return clip;
        }
        catch(Exception e) {
            System.out.println("Exception...");
        }

        return null;
    }

    public void playSound(Clip sound) {

        if(sound != null) {

            sound.setFramePosition(0);

            // These float values for decibels are currently ambiguous
            setVolume(sound, 0.3f);
            sound.start();
        }
        else {
            System.out.println("Sound clip is null...");
        }
    }

    public void playBackground(Clip sound) {

        if(sound != null) {
            
            sound.setFramePosition(0);
            
            // These float values for decibels are currently ambiguous
            setVolume(sound, 0.7f);
            sound.loop(Clip.LOOP_CONTINUOUSLY);  // Continuous looping
        }
        else {
            System.out.println("Background clip is null...");
        }

    }

    public void toggleSound(Clip sound) {

        if(sound.isRunning()) {
            sound.stop();
        }
        else {
            playBackground(sound);
        }
    }

    // Method to set volume
    private void setVolume(Clip clip, float volume) {

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    
        // Convert volume to decibels (valid range usually from -80.0 to 6.0 dB)
        float dB = 20f * (float) Math.log10(volume);
        gainControl.setValue(dB);
    }
}
