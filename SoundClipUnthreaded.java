import fallk.logmaster.HLogger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.ByteArrayInputStream;

public class SoundClipUnthreaded implements SoundClip {

    Clip clip = null;
    AudioInputStream sound;
    public boolean loaded;
    int lfrpo = -1;
    int cntcheck = 0;

    public SoundClipUnthreaded(byte[] byteInput) {
        try {
            ByteArrayInputStream var2 = new ByteArrayInputStream(byteInput);
            sound = AudioSystem.getAudioInputStream(var2);
            sound.mark(byteInput.length);
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, this.sound.getFormat()));
            loaded = true;
        } catch (Exception var4) {
            HLogger.error("Loading Clip error: " + var4);
            loaded = false;
        }
    }

    @Override
    public void play() {
        if (loaded) {
            try {
                if (!clip.isOpen()) {
                    try {
                        clip.open(sound);
                    } catch (Exception e) {
                        HLogger.error(e);
                    }
                    clip.loop(0);
                } else {
                    clip.loop(1);
                }

                lfrpo = -1;
                cntcheck = 5;
            } catch (Exception var3) {
                HLogger.error(var3);
            }
        }
    }

    @Override
    public void loop() {
        if (this.loaded) {
            try {
                if (!clip.isOpen()) {
                    try {
                        clip.open(sound);
                    } catch (Exception e) {
                        HLogger.error(e);
                    }
                }

                clip.loop(70);
                lfrpo = -2;
                cntcheck = 0;
            } catch (Exception var3) {
                HLogger.error(var3);
            }
        }
    }

    @Override
    public void stop() {
        if (loaded) {
            try {
                clip.stop();
                lfrpo = -1;
            } catch (Exception e) {
                HLogger.error(e);
            }
        }
    }

}