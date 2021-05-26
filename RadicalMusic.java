//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.sound.midi.*;

import fallk.logmaster.HLogger;
import javazoom.jl.decoder.JavaLayerException;
import jl.player.PausablePlayer;
import org.newdawn.easyogg.OggClip;
import sun.audio.AudioPlayer;

enum MusicType {
    MOD,
    MIDI,
    MP3,
    OGG
}

public class RadicalMusic {
    private BufferedInputStream is;
    private Sequencer sequencer;
    private boolean paused = false;
    private boolean loaded = false;
    private boolean playing = false;
    private String filename;
    private FileInputStream musicInputStream;
    private File musicFile;
    private OggClip ogg;
    private PausablePlayer player;
    private SuperStream stream;
    private byte modf[];
    MusicType musicType;

    public RadicalMusic(String filename) {
        if (filename.endsWith(".mp3")) {
            this.filename = filename;
            musicType = MusicType.MP3;
            musicFile = new File(this.filename);

            try {
                musicInputStream = new FileInputStream(musicFile);
                player = new PausablePlayer(musicInputStream);
                loaded = true;
            } catch (FileNotFoundException | JavaLayerException e) {
                HLogger.error("Error loading mp3 file: " + filename);
                HLogger.error(e);
            }
        } else if (filename.endsWith(".ogg")) {
            this.filename = filename;
            musicType = MusicType.OGG;

            try {
                ogg = new OggClip(filename);
                loaded = true;
            } catch (IOException e) {
                HLogger.error("Error loading ogg file: " + filename);
                HLogger.error(e);
            }
        } else {
            musicType = MusicType.MIDI;
            this.filename = filename;

            try {
                musicInputStream = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                HLogger.error("Error loading Midi file: " + filename);
                HLogger.error(e);
            }

            try {
                sequencer = MidiSystem.getSequencer();
                sequencer.open();
                loaded = true;
            } catch (Exception e) {
                HLogger.error("Error loading Midi file: " + filename);
                HLogger.error(e);
            }
        }

    }

    public RadicalMusic(String filename, int i, int j, int k, boolean flag1) {
        if (filename.endsWith(".radq")) {
            this.filename = filename;
            musicType = MusicType.MOD;

            try {
                ZipInputStream soundsInputStream = new ZipInputStream(new FileInputStream(filename));

                for (ZipEntry soundEntry = soundsInputStream.getNextEntry(); soundEntry != null; soundEntry = soundsInputStream.getNextEntry()) {
                    int size = (int) soundEntry.getSize();
                    modf = new byte[size];

                    int z;
                    for (int x = 0; size > 0; size -= z) {
                        z = soundsInputStream.read(modf, x, size);
                        x += z;
                    }
                }

                if (!flag1) {
                    i = (int) (i * 1.5D);
                } else {
                    i = (int) (i * 2.2000000000000002D);
                }

                Mod mod = new Mod(new ByteArrayInputStream(modf));
                ModSlayer modslayer = new ModSlayer(mod, j, i, k);
                try {
                    byte[] bytes = modslayer.turnbytesUlaw();
                    stream = new SuperStream(bytes);
                } catch (Exception e) {
                    HLogger.error("Error loading Mod bytes into SuperStream!");
                    HLogger.error(e);
                    loaded = false;
                }

                loaded = true;

                System.runFinalization();
                System.gc();

            } catch (IOException e) {
                HLogger.error("Error loading Mod: " + filename);
                HLogger.error(e);
                loaded = false;
            }
        }

    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isPlaying() {
        return playing;
    }

    public MusicType getMusicType() {
        return musicType;
    }

    public void load() {
        if (musicType.equals(MusicType.MIDI)) {
            this.loadMidi();
        }
    }

    public void play() {
        if (musicType.equals(MusicType.MP3)) {
            playMp3();
        } else if (musicType.equals(MusicType.OGG)) {
            ogg.loop();
        } else if (musicType.equals(MusicType.MIDI)) {
            playMidi();
        } else if (musicType.equals(MusicType.MOD)) {
            playMod();
        }
    }

    public void resume() {
        if (musicType.equals(MusicType.MP3)) {
            player.resume();
        } else if (musicType.equals(MusicType.OGG)) {
            ogg.resume();
        } else if (musicType.equals(MusicType.MIDI)) {
            resumeMidi();
        } else if (musicType.equals(MusicType.MOD)) {
            resumeMod();
        }
    }

    public void stop() {
        if (musicType.equals(MusicType.MP3)) {
            player.pause();
        } else if (musicType.equals(MusicType.OGG)) {
            ogg.pause();
        } else if (musicType.equals(MusicType.MIDI)) {
            stopMidi();
        } else if (musicType.equals(MusicType.MOD)) {
            stopMod();
        }
    }

    public void unload() {
        if (musicType.equals(MusicType.MP3)) {
            player.close();
            loaded = false;
        } else if (musicType.equals(MusicType.OGG)) {
            unloadOgg();
            loaded = false;
        } else if (musicType.equals(MusicType.MIDI)) {
            unloadMidi();
            loaded = false;
        } else if (musicType.equals(MusicType.MOD)) {
            unloadMod();
            loaded = false;
        }
    }

    private void loadMidi() {
        try {
            is = new BufferedInputStream(musicInputStream);
            loaded = true;
        } catch (Exception e) {
            HLogger.error("Error loading Midi file!");
            HLogger.error(e);
        }
    }

    private void resumeMod() {
        if (!playing && loaded) {
            try {
                AudioPlayer.player.start(stream);
            } catch (Exception e) {
                HLogger.error("Error resuming Mod!");
                HLogger.error(e);
            }
            playing = true;
        }
    }

    private void resumeMidi() {
        try {
            musicInputStream = new FileInputStream(filename);
            is = new BufferedInputStream(musicInputStream);
        } catch (IOException e) {
            HLogger.error("Error resuming Midi file!");
            HLogger.error(e);
        }
        playMidi();
    }

    private void playMidi() {
        try {
            sequencer.setSequence(is);
            sequencer.setLoopCount(9999);
            sequencer.start();
            playing = true;
        } catch (IllegalArgumentException e) {
            HLogger.error("Error playing Midi file, possibly malformed!");
            HLogger.error(e);
        } catch (IllegalStateException | IOException | InvalidMidiDataException e) {
            HLogger.error("Error playing Midi file!");
            HLogger.error(e);
        }
    }

    public void setPaused(boolean paused) {
        if (musicType.equals(MusicType.MP3)) {
            if (paused) {
                player.pause();
            } else {
                player.resume();
            }
        } else if (musicType.equals(MusicType.OGG)) {
            if (paused) {
                ogg.pause();
            } else {
                ogg.resume();
            }
        } else if (musicType.equals(MusicType.MIDI)) {
            if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
                this.paused = paused;
                if (paused) {
                    sequencer.stop();
                } else {
                    sequencer.start();
                }
            }
        }
    }

    private void stopMidi() {
        try {
            this.sequencer.stop();
            this.playing = false;
        } catch (Exception e) {
            HLogger.error("Error stopping Midi file!");
            HLogger.error(e);
        }
    }

    private void stopMod() {
        if (playing && loaded) {
            try {
                AudioPlayer.player.stop(stream);
            } catch (Exception e) {
                HLogger.error("Error stopping Mod!");
                HLogger.error(e);
            }
            playing = false;
        }
    }

    private void unloadMod() {
        if (loaded) {
            if (playing) {
                try {
                    AudioPlayer.player.stop(stream);
                } catch (Exception e) {
                    HLogger.error("Error unloading Mod!");
                    HLogger.error(e);
                }
                playing = false;
            }
            try {
                stream.close();
                stream = null;
            } catch (Exception e) {
                HLogger.error("Error unloading Mod!");
                HLogger.error(e);
            }
            System.gc();
            loaded = false;
        }
    }

    private void playMod() {
        if (!playing && loaded) {
            if (stream != null) {
                stream.reset();
            }
            try {
                AudioPlayer.player.start(stream);
            } catch (Exception e) {
                HLogger.error("Error playing Mod!");
                HLogger.error(e);
            }
            playing = true;
        }
    }

    private void unloadAllMod() {
        if (playing && loaded) {
            try {
                AudioPlayer.player.stop(stream);
            } catch (Exception e) {
                HLogger.error("Error unloading Mod!");
                HLogger.error(e);
            }
        }
        try {
            stream.close();
            stream = null;
        } catch (Exception e) {
            HLogger.error("Error unloading Mod!");
            HLogger.error(e);
        }
        try {
            modf = null;
        } catch (Exception e) {
            HLogger.error("Error unloading Mod!");
            HLogger.error(e);
        }
        System.gc();
    }

    private void unloadMidi() {
        try {
            if (is != null) {
                is.close();
            }
            loaded = false;
        } catch (Exception e) {
            HLogger.error("Error unloading Midi file!");
            HLogger.error(e);
        }
    }

    private void playMp3() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            HLogger.error("Error playing mp3 file!");
            HLogger.error(e);
        }
    }

    private void unloadOgg() {
        ogg.stop();
        ogg.close();
    }
}
