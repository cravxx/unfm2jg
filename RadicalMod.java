import fallk.logmaster.HLogger;
import sun.audio.AudioPlayer;

import java.applet.Applet;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RadicalMod {

    private SuperClip sClip;
    private byte modf[];
    private boolean playing;
    public int loaded;

    public void stop() {
        if (playing && loaded == 2) {
            sClip.stop();
            playing = false;
        }
    }

    public RadicalMod(String s, Applet applet) {
        playing = false;
        loaded = 0;
        loaded = 1;
        try {
            URL url = new URL(applet.getCodeBase(), s);
            ZipInputStream zipinputstream = new ZipInputStream(url.openStream());
            ZipEntry zipentry = zipinputstream.getNextEntry();
            int i = (int) zipentry.getSize();
            modf = new byte[i];
            int j = 0;
            int k;
            for (; i > 0; i -= k) {
                k = zipinputstream.read(modf, j, i);
                j += k;
            }

        } catch (Exception exception) {
            HLogger.error("Error loading Mod from zip file: " + exception);
            loaded = 0;
        }
    }

    public void resume() {
        if (!playing && loaded == 2) {
            sClip.resume();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }
    }

    void unloadAll() {
        if (playing && loaded == 2) {
            sClip.stop();
        }
        try {
            sClip.close();
            sClip = null;
        } catch (Exception _ex) {
        }
        try {
            modf = null;
        } catch (Exception _ex) {
        }
        System.gc();
    }

    public void play() {
        if (!playing && loaded == 2) {
            sClip.play();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }
    }

    void unloadMod() {
        if (loaded == 2) {
            if (playing) {
                sClip.stop();
                playing = false;
            }
            try {
                sClip.close();
                sClip = null;
            } catch (Exception _ex) {
            }
            System.gc();
            loaded = 1;
        }
    }

    public void loadMod(int i, int j, int k) {
        if (loaded == 1) {
            loaded = 2;
            int l = 22000;
            j = (int) ((j / 8000F) * 2.0F * l);
            Mod mod = new Mod(new ByteArrayInputStream(modf));
            ModSlayer modslayer = new ModSlayer(mod, j, i, k);
            try {
                byte abyte0[] = modslayer.turnbytesNorm();
                sClip = new SuperClip(abyte0, modslayer.oln, l);
            } catch (Exception exception) {
                HLogger.error("Error making a Mod: " + exception);
                loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }
}
