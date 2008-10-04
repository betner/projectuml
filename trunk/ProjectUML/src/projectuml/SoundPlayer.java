package projectuml;

import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/**
 * Implements a basic sound player that can load
 * audio files automatically from a given path.
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class SoundPlayer implements LineListener {
    
    private Boolean mute;
    private Timer timer;
    private Hashtable<String, Clip> clips;
    
    /**
     * Don't load any files
     */
    public SoundPlayer() {
        this(null);
    }
    
    /**
     * Loads all soundfiles found at the path
     * @param path Path to search
     */
    public SoundPlayer(String path) {
        timer = new Timer();
        clips = new Hashtable<String, Clip>();
        mute = false;
        
        if (path != null) {
            // Traverse the directory and search
            // for sound files
            File directory = new File(path);
            if (directory.isDirectory()) {
                for (File file : directory.listFiles(new AudioFilesOnly())) {
                    loadSound(file.getAbsolutePath());
                }
            }
        }
    }
    
    /**
     * Filter class that only accepts audio files
     */
    private class AudioFilesOnly implements FilenameFilter {
        
        public boolean accept(File dir, String name) {
            return name.endsWith(".wav");
        }
    }
    
    /**
     * Stops all sound immediately
     */
    public void mute() {
        mute = true;
        timer.cancel();
        for (Clip clip : clips.values()) {
            clip.stop();
        }
    }
    
    /**
     * Restore sound playing
     */
    public void unmute() {
        mute = false;
    }
    
    /**
     * Loads an audio file. To play it, access it by
     * its short filename ("pling.wav" becomes "pling")
     * @param filename
     * @return True if successful
     */
    public Boolean loadSound(String filename) {
        File file = new File(filename);
        int dot = file.getName().indexOf(".");
        String keyname = file.getName().substring(0, dot);
        return loadSound(filename, keyname);
    }
    
    /**
     * Loads an audio file and associates it with the
     * specified keyname. Use the keyname to play it
     * later.
     * @param filename
     * @param keyname
     * @return True if successful
     */
    public Boolean loadSound(String filename, String keyname) {
        // Always assume failure
        boolean success = false;
        
        System.out.println("Trying to load " + filename);
        try {
            // Don't store twice
            if (!clips.containsKey(keyname)) {
                // Load the audio clip
                File file = new File(filename);
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.addLineListener(this);
                clip.open(stream);
                clips.put(keyname, clip);
                Thread.sleep(0);
            }
            
            // Loading was a success!
            success = true;
        } catch (FileNotFoundException ex) {
            System.err.println("Sound " + filename + " not loaded!");
        } catch (UnsupportedAudioFileException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (LineUnavailableException ex) {
            System.err.println(ex.getMessage());
        } catch (InterruptedException ex) {
            // Dumb exception thrown by Thread.sleep
        } finally {
            return success;
        }
    }
    
    /**
     * Plays a loaded file, if not muted
     * @param name
     */
    public void play(String name) {
        if (!mute) {
            try {
                Clip clip = clips.get(name);
                if (clip.isRunning() /* clip.isActive() */) {
                    clip.stop();
                }
                // Rewind
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception ex) {
                System.out.println("Sound " + name + " not found!");
            }
        }
    }
    
    /**
     * Loop a loaded sound
     * @param name
     * @param count How many times it should loop
     */
    public void loopPlay(String name, int count) {
        if (!mute) {
            try {
                Clip clip = clips.get(name);
                if (clip.isActive()) {
                    clip.stop();
                }
                // Rewind
                clip.setFramePosition(0);
                clip.loop(count);
            } catch (Exception ex) {
                System.out.println("Sound " + name + " not found!");
            }
        }
    }
    
    /**
     * Loop a loaded sound forever
     * @param name
     */
    public void loopPlay(String name) {
        loopPlay(name, Clip.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Stops an active playing sound
     * @param name
     */
    public void stop(String name) {
        try {
            Clip clip = clips.get(name);
            clip.stop();
        } catch (Exception ex) {
            System.out.println("Sound " + name + " not found!");
        }
    }
    
    /**
     * Resumes a previously stopped sound clip
     * @param name
     */
    public void resume(String name) {
        try {
            Clip clip = clips.get(name);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Sound " + name + " not found!");
        }
    }
    
    /**
     * Returns true if there's a sound still playing
     * @return
     */
    public Boolean isPlaying() {
        if (mute) {
            return false;
        } else {
            for (Clip clip : clips.values()) {
                // Break at the first active sound so
                // we don't need to iterate over every clip
                if (clip.isActive()) {
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * Starts fading out a playing sound
     * @param keyname Clip to fade out
     */
    public void fadeOut(String keyname) {
        if (!mute) {
            Clip clip = clips.get(keyname);
            timer.scheduleAtFixedRate(new FadeOutTask(clip), 0, 100);
        }
    }
    
    /**
     * Starts fading out all sounds playing
     */
    public void fadeOutEverything() {
        if (!mute) {
            for (Clip clip : clips.values()) {
                timer.scheduleAtFixedRate(new FadeOutTask(clip), 0, 100);
            }
        }
    }
    
    /**
     * Outputs debug information
     */
    public void debugDump() {
        System.out.println("--- SoundPlayer ---");
        for (Map.Entry<String, Clip> key : clips.entrySet()) {
            System.out.println(key.getKey() + ": " + key.getValue().toString());
        }
        
        System.out.println("   --- Mixer ---");
        try {
            Mixer mixer = AudioSystem.getMixer(null);
            System.out.println(mixer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("-------------------");
    }
    
    /**
     * Outputs debug information about a certain clip
     * @param keyname
     */
    public void debugDump(String keyname) {
        System.out.println("--- SoundPlayer, clip: " + keyname + " ---");
        Clip clip = clips.get(keyname);
        Control[] controls = clip.getControls();
        for (Control control : controls) {
            System.out.println(control.getType() + ", " + control.toString());
        }
        System.out.println(clip.getFormat());
        System.out.println("-------------------");
    }
    
    /**
     * Receives updates from loaded clips
     * @param event
     */
    public void update(LineEvent event) {
        System.out.println("SoundPlayer: " + event.toString());
    }
    
    /**
     * A task that fades out a clip under a certain amount
     * of time
     */
    private class FadeOutTask extends TimerTask {
        
        private Clip clip;
        private FloatControl volume;
        private float x;
        private final float STEP_AMOUNT = 0.006f;
        private float start;
        
        public FadeOutTask(Clip clip) {
            this.clip = clip;
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            start = volume.getValue();
            x = 0.0f;
        }
        
        /**
         * Decreases the volume until not heared
         */
        public void run() {
            float vol = -80 * x;
            if (vol > volume.getMinimum()) {
                volume.setValue(vol);
            } else {
                // We've reached bottom, turn off the sound and
                // restore original volume
                clip.stop();
                volume.setValue(start);
                this.cancel();
            }
            x += STEP_AMOUNT;
        }
    }
}
