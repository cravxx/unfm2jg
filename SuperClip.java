import fallk.logmaster.HLogger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayInputStream;

class SuperClip implements Runnable {

    private int skiprate;
    private Thread cliper;
    public int stoped;
    private SourceDataLine source;
    private ByteArrayInputStream stream;

    public SuperClip(byte abyte0[], int i, int j) {
        skiprate = 0;
        stoped = 1;
        source = null;
        stoped = 2;
        skiprate = j;
        stream = new ByteArrayInputStream(abyte0, 0, i);
    }

    @Override
    public void run() {
        boolean flag = false;
        try {
            final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, skiprate, 16, 1, 2, skiprate, false);
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            source = (SourceDataLine) AudioSystem.getLine(info);
            source.open(audioFormat);
            source.start();
        } catch (Exception exception) {
            exception.printStackTrace();
            stoped = 1;
        }
        while (stoped == 0) {
            try {
                if (source.available() < skiprate || !flag) {
                    byte abyte0[] = new byte[skiprate];
                    int i = stream.read(abyte0, 0, abyte0.length);
                    if (i == -1) {
                        stream.reset();
                        stream.read(abyte0, 0, abyte0.length);
                    }
                    source.write(abyte0, 0, abyte0.length);
                    flag = true;
                }
            } catch (Exception exception1) {
                HLogger.error("play error: " + exception1);
                stoped = 1;
            }
            try {
                Thread.sleep(200L);
            } catch (InterruptedException interruptedexception) {
            }
        }
        source.stop();
        source.close();
        source = null;
        stoped = 2;
    }

    public void play() {
        if (stoped == 2) {
            stoped = 0;
            try {
                stream.reset();
            } catch (Exception exception) {
            }
            cliper = new Thread(this);
            cliper.start();
        }
    }

    public void resume() {
        if (stoped == 2) {
            stoped = 0;
            cliper = new Thread(this);
            cliper.start();
        }
    }

    public void stop() {
        if (stoped == 0) {
            stoped = 1;
            if (source != null) {
                source.stop();
            }
        }
    }

    public void close() {
        try {
            stream.close();
            stream = null;
        } catch (Exception exception) {
        }
    }
}
