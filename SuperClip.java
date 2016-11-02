

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   music.SuperClip.java

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class SuperClip implements Runnable {

    SuperClip(final byte[] abyte0, final int i, final int j) {
        skiprate = 0;
        stoped = 1;
        source = null;
        rollBackPos = 0;
        rollBackTrig = 0;
        stoped = 2;
        skiprate = j;
        stream = new ByteArrayInputStream(abyte0, 0, i);
    }

    @Override
    public void run() {
        try {
            final AudioFormat audioformat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, skiprate, 16, 1, 2, skiprate, false);
            final javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(SourceDataLine.class, audioformat);
            source = (SourceDataLine) AudioSystem.getLine(info);
            source.open(audioformat);
            source.start();
        } catch (final Exception exception) {
            stoped = 1;
        }
        boolean flag = false;
        while (stoped == 0) {
            try {
                final int i = skiprate;
                int j = stream.available();
                if (j % 2 != 0) {
                    j++;
                }
                byte[] abyte0 = new byte[j <= i ? j : i];
                final int l = stream.read(abyte0, 0, abyte0.length);
                if (l == -1 || rollBackPos != 0 && j < rollBackTrig) {
                    flag = true;
                }
                if (flag) {
                    if (l != -1) {
                        source.write(abyte0, 0, abyte0.length);
                    }
                    stream.reset();
                    if (rollBackPos != 0) {
                        stream.skip(rollBackPos);
                    }
                    int k = stream.available();
                    if (k % 2 != 0) {
                        k++;
                    }
                    abyte0 = new byte[k <= i ? k : i];
                    stream.read(abyte0, 0, abyte0.length);
                    flag = false;
                }
                source.write(abyte0, 0, abyte0.length);
            } catch (final Exception exception1) {
                System.out.println("Play error: " + exception1);
                stoped = 1;
            }
            try {
                Thread.sleep(200L);
            } catch (final InterruptedException ignored) {
            }
        }
        source.stop();
        source.close();
        source = null;
        stoped = 2;
    }

    void play() {
        if (stoped == 2) {
            stoped = 0;
            try {
                stream.reset();
            } catch (final Exception ignored) {
            }
            cliper = new Thread(this);
            cliper.start();
        }
    }

    void resume() {
        if (stoped == 2) {
            stoped = 0;
            cliper = new Thread(this);
            cliper.start();
        }
    }

    void stop() {
        if (stoped == 0) {
            stoped = 1;
            if (source != null) {
                source.stop();
            }
        }
    }

    void close() {
        try {
            stream.close();
            stream = null;
        } catch (final Exception ignored) {
        }
    }

    private int skiprate;
    private Thread cliper;
    public int stoped;
    private SourceDataLine source;
    public ByteArrayInputStream stream;
    public int rollBackPos;
    public int rollBackTrig;
}
