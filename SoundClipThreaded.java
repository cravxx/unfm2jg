import fallk.logmaster.HLogger;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SoundClipThreaded implements Runnable, SoundClip {

    private byte[] clipBytes;
    boolean isPlaying = false;
    boolean loaded;

    public SoundClipThreaded(byte[] byteInput) {
        try {
            this.clipBytes = byteInput;
            loaded = true;
        } catch (Exception e) {
            HLogger.error(e);
            loaded = false;
        }
    }

    @Override
    public void play() {
        if (loaded && !isPlaying) {

            isPlaying = true;

            try {

                final byte[] data = new byte[4096];
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(clipBytes));
                SourceDataLine dataLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioInputStream.getFormat()));

                if (!dataLine.isOpen()) {

                    dataLine.open();
                    dataLine.start();

                    int nBytesRead = 0;

                    while ((nBytesRead != -1)) {
                        nBytesRead = audioInputStream.read(data, 0, data.length);
                        if (nBytesRead != -1) {
                            dataLine.write(data, 0, nBytesRead);
                        }
                    }

                    dataLine.drain();

                }

                dataLine.stop();
                dataLine.flush();
                dataLine.close();
                audioInputStream.close();

            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                HLogger.error(e);
            }
        }
        isPlaying = false;
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        play();
    }

}