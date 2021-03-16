//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.PausablePlayer;
import org.newdawn.easyogg.OggClip;

public class RadicalMidi {
    BufferedInputStream is;
    Sequencer sequencer;
    boolean paused;
    boolean loaded = false;
    boolean playing = false;
    boolean isMp3 = false;
    boolean isOgg = false;
    String s;
    FileInputStream fi;
    File fl;
    OggClip ogg;
    PausablePlayer player;
    String filePath;

    public RadicalMidi(String fn) {
        if (fn.endsWith(".mp3")) {
            this.s = fn;
            this.isMp3 = true;
            this.isOgg = false;
            this.fl = new File(fn);

            try {
                this.fi = new FileInputStream(this.fl);
                this.player = new PausablePlayer(this.fi);
            } catch (FileNotFoundException | JavaLayerException var6) {
                System.out.println("Error loading Mp3!");
                var6.printStackTrace();
            }
        } else if (fn.endsWith(".ogg")) {
            this.s = fn;
            this.isMp3 = false;
            this.isOgg = true;

            try {
                this.ogg = new OggClip(fn);
            } catch (IOException var5) {
                System.out.println("Error loading Ogg!");
                var5.printStackTrace();
            }
        } else {
            this.isMp3 = false;
            this.isOgg = false;
            this.s = fn;

            try {
                this.fi = new FileInputStream(new File(fn));
            } catch (FileNotFoundException var4) {
                System.out.println("Midi file \"" + fn + "\" not found!");
                var4.printStackTrace();
            }

            try {
                this.sequencer = MidiSystem.getSequencer();
                this.sequencer.open();
            } catch (Exception var3) {
                System.out.println("Error loading Midi file \"" + fn + "\":");
                var3.printStackTrace();
            }
        }

    }

    public void load() {
        if (!this.isOgg && !this.isMp3) {
            this.loadMidi();
        }

    }

    public void play() {
        if (this.isMp3) {
            this.playMp3();
        } else if (this.isOgg) {
            this.ogg.loop();
        } else {
            this.playMidi();
        }

    }

    /** @deprecated */
    @Deprecated
    public void resume() {
        if (this.isMp3) {
            this.player.resume();
        } else if (this.isOgg) {
            this.ogg.resume();
        } else {
            this.resumeMidi();
        }

    }

    /** @deprecated */
    @Deprecated
    public void stop() {
        if (this.isMp3) {
            this.player.pause();
        } else if (this.isOgg) {
            this.ogg.pause();
        } else {
            this.stopMidi();
        }

    }

    public void unload() {
        if (this.isMp3) {
            this.player.close();
        } else if (this.isOgg) {
            this.unloadOgg();
        } else {
            this.unloadMidi();
        }

    }

    public void loadMidi() {
        try {
            this.is = new BufferedInputStream(this.fi);
            this.loaded = true;
        } catch (Exception var2) {
            System.out.println("Error buffering Midi file:");
            var2.printStackTrace();
        }

    }

    /** @deprecated */
    @Deprecated
    public void resumeMidi(int gain, int loops) {
        try {
            this.fi = new FileInputStream(new File(this.s));
            this.is = new BufferedInputStream(this.fi);
        } catch (IOException var4) {
            System.out.println("Midi file not found!");
            var4.printStackTrace();
        } catch (Exception var5) {
            System.out.println("Error buffering Midi file:");
            var5.printStackTrace();
        }

        this.playMidi(gain, loops);
    }

    /** @deprecated */
    @Deprecated
    public void resumeMidi(int gain) {
        try {
            this.fi = new FileInputStream(new File(this.s));
            this.is = new BufferedInputStream(this.fi);
        } catch (IOException var3) {
            System.out.println("Midi file not found!");
            var3.printStackTrace();
        } catch (Exception var4) {
            System.out.println("Error buffering Midi file:");
            var4.printStackTrace();
        }

        this.playMidi(gain);
    }

    /** @deprecated */
    @Deprecated
    public void resumeMidi() {
        try {
            this.fi = new FileInputStream(new File(this.s));
            this.is = new BufferedInputStream(this.fi);
        } catch (IOException var2) {
            System.out.println("Midi file not found!");
            var2.printStackTrace();
        } catch (Exception var3) {
            System.out.println("Error buffering Midi file:");
            var3.printStackTrace();
        }

        this.playMidi();
    }

    public void playMidi(int gain, int loops) {
        try {
            this.sequencer.setSequence(this.is);
            this.sequencer.setLoopCount(loops);
            if (this.sequencer instanceof Synthesizer) {
                Synthesizer synthesizer = (Synthesizer)this.sequencer;
                MidiChannel[] channels = synthesizer.getChannels();

                for(int i = 0; i < channels.length; ++i) {
                    channels[i].controlChange(7, (int)((double)((float)gain) * 2D));
                }
            }

            this.sequencer.start();
            this.playing = true;
        } catch (IllegalArgumentException var6) {
            System.out.println("There is a mistake in your Midi code,");
            System.out.println("please re-check!");
            var6.printStackTrace();
        } catch (IllegalStateException var7) {
            System.out.println("Error playing Midi file " + this.s + ", check if the file exists!");
            var7.printStackTrace();
        } catch (Exception var8) {
            System.out.println("Error playing Midi file:");
            var8.printStackTrace();
        }

    }

    public void playMidi(int gain) {
        try {
            this.sequencer.setSequence(this.is);
            this.sequencer.setLoopCount(9999);
            if (this.sequencer instanceof Synthesizer) {
                Synthesizer synthesizer = (Synthesizer)this.sequencer;
                MidiChannel[] channels = synthesizer.getChannels();

                for(int i = 0; i < channels.length; ++i) {
                    channels[i].controlChange(7, (int)((double)((float)gain) * 1.27D));
                }
            }

            this.sequencer.start();
            this.playing = true;
        } catch (IllegalArgumentException var5) {
            System.out.println("There is a mistake in your Midi code,");
            System.out.println("please re-check!");
            var5.printStackTrace();
        } catch (IllegalStateException var6) {
            System.out.println("Error playing Midi file " + this.s + ", check if the file exists!");
            var6.printStackTrace();
        } catch (Exception var7) {
            System.out.println("Error playing Midi file:");
            var7.printStackTrace();
        }

    }

    public void playMidi() {
        try {
            this.sequencer.setSequence(this.is);
            this.sequencer.setLoopCount(9999);
            this.sequencer.start();
            this.playing = true;
        } catch (IllegalArgumentException var2) {
            System.out.println("There is a mistake in your Midi code,");
            System.out.println("please re-check!");
            var2.printStackTrace();
        } catch (IllegalStateException var3) {
            System.out.println("Error playing Midi file " + this.s + ", check if the file exists!");
            var3.printStackTrace();
        } catch (Exception var4) {
            System.out.println("Error playing Midi file:");
            var4.printStackTrace();
        }

    }

    public void setPaused(boolean paused) {
        if (!this.isOgg && !this.isMp3) {
            if (this.paused != paused && this.sequencer != null && this.sequencer.isOpen()) {
                this.paused = paused;
                if (paused) {
                    this.sequencer.stop();
                } else {
                    this.sequencer.start();
                }
            }
        } else if (paused) {
            if (this.isMp3) {
                this.player.pause();
            } else if (this.isOgg) {
                this.ogg.pause();
            }
        } else if (this.isMp3) {
            this.player.resume();
        } else if (this.isOgg) {
            this.ogg.resume();
        }

    }

    public boolean isPaused() {
        return this.paused;
    }

    public void stopMidi() {
        System.out.println("Stopping Midi file...");

        try {
            this.sequencer.stop();
            this.playing = false;
        } catch (Exception var2) {
            System.out.println("Error stopping Midi file:");
            var2.printStackTrace();
        }

    }

    public void unloadMidi() {
        try {
            if (this.is != null) {
                this.is.close();
            }

            this.loaded = false;
        } catch (Exception var2) {
            System.out.println("Error unloading Midi file:");
            var2.printStackTrace();
        }

    }

    public void playMp3() {
        try {
            this.player.play();
        } catch (JavaLayerException var2) {
            var2.printStackTrace();
        }

    }

    public void unloadOgg() {
        this.ogg.stop();
        this.ogg.close();
    }
}
