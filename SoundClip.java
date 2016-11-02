

/* soundClip - Decompiled by JODE extended
 * DragShot Software
 * JODE (c) 1998-2001 Jochen Hoenicke
 */
import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class SoundClip {
    Clip clip = null;
    int cntcheck = 0;
    int lfrpo = -1;
    boolean loaded = false;
    int rollBackPos;
    int rollBackTrig;
    AudioInputStream sound;

    public SoundClip(final byte[] is) {
        try {
            final ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(is);
            sound = AudioSystem.getAudioInputStream(bytearrayinputstream);
            sound.mark(is.length);
            clip = AudioSystem.getClip();
            loaded = true;
        } catch (final Exception exception) {
            System.err.println("Loading Clip error: " + exception);
            exception.printStackTrace();
            loaded = false;
        }
    }

    public void checkopen() {
        if (loaded && clip.isOpen() && lfrpo != -2)
            if (cntcheck == 0) {
                final int i = clip.getFramePosition();
                if (lfrpo == i && !clip.isRunning()) {
                    try {
                        clip.close();
                        sound.reset();
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                    lfrpo = -1;
                } else {
                    lfrpo = i;
                }
            } else {
                cntcheck--;
            }
    }

    public void loop() {
        if (loaded) {
            if (!clip.isOpen()) {
                try {
                    clip.open(sound);
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }
            clip.loop(70);
            lfrpo = -2;
            cntcheck = 0;
        }
    }

    public void play() {
        if (loaded) {
            if (!clip.isOpen()) {
                try {
                    clip.open(sound);
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
                clip.loop(0);
            } else {
                clip.loop(1);
            }
            lfrpo = -1;
            cntcheck = 5;
        }
    }

    public void stop() {
        if (loaded) {
            clip.stop();
            lfrpo = -1;
        }
    }
}
