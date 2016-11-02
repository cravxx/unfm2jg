
import java.net.URL;

import ds.nfm.Module;
import ds.nfm.ModuleLoader;
import ds.nfm.ModuleSlayer;

public class RadicalMod implements RadicalMusic {
    public static String name = "";
    public static String filename = "";
    public static boolean nonempty = false;
    public SuperClip sClip;
    public boolean playing = false;
    public int loaded = 0;
    public int rvol = 0;
    public String imod = "";
    public String pmod = "";

    public RadicalMod() {
        loaded = 0;
        nonempty = false;
        System.gc();
    }

    public RadicalMod(String string, int i, int i_0, final int i_1, final boolean bool, final boolean bool_2) {
        final short i_3 = 22000;
        i_0 = (int) (i_0 / 8000.0F * 2.0F * i_3);
        i = (int) (i * 0.8F);
        filename = string.replace("mystages/mymusic/", "");
        nonempty = true;

        try {
            Module mod = ModuleLoader.loadMod(string);

            if (mod.isLoaded()) {
                name = mod.getName();
                if (name == null || name.trim().equals("")) {
                    name = "Untitled";
                }

                final ModuleSlayer moduleslayer1 = ModuleLoader.prepareSlayer(mod, i_0, i, i_1);
                final byte[] is = moduleslayer1.turnbytesNorm(bool);
                if (bool) {
                    rvol = moduleslayer1.olav;
                }

                sClip = new SuperClip(is, moduleslayer1.oln, i_3);
                sClip.rollBackPos = moduleslayer1.rollBackPos;
                sClip.rollBackTrig = moduleslayer1.oln - moduleslayer1.rollBackTrig;
                if (bool_2) {
                    filename = "Length: " + getTimer(sClip.stream.available() / '걄');
                }

                loaded = 2;
            }
        } catch (final Exception var11) {
            var11.printStackTrace();
            System.out.println("Error downloading and making Mod: " + var11.toString());
            loaded = 0;
            nonempty = false;
        }

        System.runFinalization();
        System.gc();
    }

    public RadicalMod(final String string) {
        loaded = 1;
        imod = string;
        filename = string;
        nonempty = true;
    }

    String getTimer(int secs) {
        final int mins = secs / 60;
        secs %= 60;
        return secs >= 10 ? mins + ":" + secs : mins + ":0" + secs;
    }

    public void loadimod(final boolean bool) {
        if (loaded == 1) {
            final char i = 'ꯠ';
            short i_6 = 160;
            if (bool) {
                i_6 = 300;
            }

            final byte i_7 = 125;

            try {
                final Module exception = ModuleLoader.loadMod(imod);
                if (exception.isLoaded()) {
                    name = exception.getName();
                    if (name.trim().equals("")) {
                        name = "Untitled";
                    }

                    final ModuleSlayer moduleslayer = ModuleLoader.prepareSlayer(exception, i, i_6, i_7);
                    final byte[] is = moduleslayer.turnbytesNorm(bool);
                    if (bool) {
                        rvol = moduleslayer.olav;
                    }

                    sClip = new SuperClip(is, moduleslayer.oln, 22000);
                    sClip.rollBackPos = moduleslayer.rollBackPos;
                    sClip.rollBackTrig = moduleslayer.oln - moduleslayer.rollBackTrig;
                    loaded = 2;
                }
            } catch (final Exception var8) {
                System.out.println("Error making a imod: " + var8.toString());
                loaded = 0;
                nonempty = false;
            }

            System.runFinalization();
            System.gc();
        }

    }

    public void loadpmod(final boolean bool) {
        if (loaded == 1) {
            final char i = 'ꯠ';
            short i_10 = 160;
            if (bool) {
                i_10 = 300;
            }

            final byte i_11 = 125;

            try {
                final Module exception = ModuleLoader.loadMod(pmod);
                if (exception.isLoaded()) {
                    name = exception.getName();
                    if (name.trim().equals("")) {
                        name = "Untitled";
                    }

                    final ModuleSlayer moduleslayer = ModuleLoader.prepareSlayer(exception, i, i_10, i_11);
                    final byte[] is = moduleslayer.turnbytesNorm(bool);
                    if (bool) {
                        rvol = moduleslayer.olav;
                    }

                    sClip = new SuperClip(is, moduleslayer.oln, 22000);
                    sClip.rollBackPos = moduleslayer.rollBackPos;
                    sClip.rollBackTrig = moduleslayer.oln - moduleslayer.rollBackTrig;
                    loaded = 2;
                }
            } catch (final Exception var8) {
                System.out.println("Error making a imod: " + var8.toString());
                loaded = 0;
                nonempty = false;
            }

            System.runFinalization();
            System.gc();
        }

    }

    public RadicalMod(final String string, final boolean bool) {
        loaded = 1;
        pmod = string;
        loadpmod(true);
        filename = string;
        nonempty = true;
    }

    @Override
    public void play() {
        if (!playing && loaded == 2) {
            sClip.play();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }

    }

    @Override
    public void resume() {
        if (!playing && loaded == 2) {
            sClip.resume();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }

    }

    @Override
    public void stop() {
        if (playing && loaded == 2) {
            sClip.stop();
            playing = false;
        }

    }

    public void unloadimod() {
        if (loaded == 2) {
            if (playing) {
                sClip.stop();
                playing = false;
            }

            try {
                sClip.close();
                sClip = null;
            } catch (final Exception var2) {
                ;
            }

            System.gc();
            loaded = 1;
        }

    }

    @Override
    public void unload() {
        if (playing && loaded == 2) {
            sClip.stop();
            playing = false;
        }

        try {
            sClip.close();
            sClip = null;
        } catch (final Exception var3) {
            ;
        }

        try {
            imod = null;
        } catch (final Exception var2) {
            ;
        }

        System.gc();
        loaded = 0;
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return RadicalMusic.Type.TYPE_MOD;
    }

    @Override
    public void setPaused(final boolean pause) {
        if (pause) {
            stop();
        } else {
            resume();
        }
    }

    @Override
    public boolean isPaused() {
        return !playing && loaded == 2;
    }
}
