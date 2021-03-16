import fallk.logmaster.HLogger;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final HashMap<String, SoundClip> clips = new HashMap<>();

    public void add(String name, SoundClip clip) {
        clips.put(name, clip);
    }

    public void play(String name) {
        SoundClip soundClip = clips.get(name);
        if (soundClip != null) {
            if (soundClip instanceof SoundClipThreaded) {
                executor.execute(((SoundClipThreaded) soundClip));
            } else {
                soundClip.play();
            }
        } else {
            HLogger.warn("clip not found: " + name);
        }

    }

    public void stop(String name) {
        SoundClip soundClip = clips.get(name);
        if (soundClip != null) {
            if (soundClip instanceof SoundClipUnthreaded) {
                soundClip.stop();
            } else {
                HLogger.warn("threaded clips cannot be stopped! " + name);
            }
        } else {
            HLogger.warn("clip not found: " + name);
        }
    }

    public void loop(String name) {
        SoundClip soundClip = clips.get(name);
        if (soundClip != null) {
            if (soundClip instanceof SoundClipUnthreaded) {
                soundClip.loop();
            } else {
                HLogger.warn("threaded clips cannot be looped! " + name);
            }
        } else {
            HLogger.warn("clip not found: " + name);
        }
    }

}