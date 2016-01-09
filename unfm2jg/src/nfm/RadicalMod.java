package nfm;
import java.applet.Applet;
import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import sun.audio.AudioPlayer;

public class RadicalMod
{

    byte modf[];
    SuperStream stream;
    SuperClip sClip;
    boolean suny;
    boolean playing;
    int loaded;

    public void stop()
    {
        if(playing && loaded == 2)
        {
            if(suny)
            {
                sClip.stop();
            } else
            {
                try
                {
                    AudioPlayer.player.stop(stream);
                }
                catch(Exception _ex) { }
            }
            playing = false;
        }
    }

    public RadicalMod(String s, Applet applet)
    {
        suny = false;
        playing = false;
        loaded = 0;
        loaded = 1;
        try
        {
            URL url = new URL(applet.getCodeBase(), s);
            ZipInputStream zipinputstream = new ZipInputStream(url.openStream());
            ZipEntry zipentry = zipinputstream.getNextEntry();
            int i = (int)zipentry.getSize();
            modf = new byte[i];
            int j = 0;
            int k;
            for(; i > 0; i -= k)
            {
                k = zipinputstream.read(modf, j, i);
                j += k;
            }

        }
        catch(Exception exception)
        {
            System.out.println("Error loading Mod from zip file: " + exception);
            loaded = 0;
        }
    }

    public void resume()
    {
        if(!playing && loaded == 2)
        {
            if(suny)
            {
                sClip.resume();
                if(sClip.stoped == 0)
                {
                    playing = true;
                }
            } else
            {
                try
                {
                    AudioPlayer.player.start(stream);
                }
                catch(Exception _ex) { }
                playing = true;
            }
        }
    }

    protected void unloadAll()
    {
        if(playing && loaded == 2)
        {
            if(suny)
            {
                sClip.stop();
            } else
            {
                try
                {
                    AudioPlayer.player.stop(stream);
                }
                catch(Exception _ex) { }
            }
        }
        try
        {
            if(suny)
            {
                sClip.close();
                sClip = null;
            } else
            {
                stream.close();
                stream = null;
            }
        }
        catch(Exception _ex) { }
        try
        {
            modf = null;
        }
        catch(Exception _ex) { }
        System.gc();
    }

    public void play()
    {
        if(!playing && loaded == 2)
        {
            if(suny)
            {
                sClip.play();
                if(sClip.stoped == 0)
                {
                    playing = true;
                }
            } else
            {
                if(stream != null)
                {
                    stream.reset();
                }
                try
                {
                    AudioPlayer.player.start(stream);
                }
                catch(Exception _ex) { }
                playing = true;
            }
        }
    }

    protected void unloadMod()
    {
        if(loaded == 2)
        {
            if(playing)
            {
                if(suny)
                {
                    sClip.stop();
                } else
                {
                    try
                    {
                        AudioPlayer.player.stop(stream);
                    }
                    catch(Exception _ex) { }
                }
                playing = false;
            }
            try
            {
                if(suny)
                {
                    sClip.close();
                    sClip = null;
                } else
                {
                    stream.close();
                    stream = null;
                }
            }
            catch(Exception _ex) { }
            System.gc();
            loaded = 1;
        }
    }

    public void loadMod(int i, int j, int k, boolean flag, boolean flag1)
    {
        if(loaded == 1)
        {
            loaded = 2;
            suny = flag;
            int l = 22000;
            if(flag1)
            {
                suny = false;
            }
            if(suny)
            {
                j = (int)(((float)j / 8000F) * 2.0F * (float)l);
            }
            if(!suny)
            {
                if(!flag1)
                {
                    i = (int)((double)i * 1.5D);
                } else
                {
                    i = (int)((double)i * 2.2000000000000002D);
                }
            }
            Mod mod = new Mod(new ByteArrayInputStream(modf));
            ModSlayer modslayer = new ModSlayer(mod, j, i, k);
            try
            {
                if(suny)
                {
                    byte abyte0[] = modslayer.turnbytesNorm();
                    sClip = new SuperClip(abyte0, modslayer.oln, l);
                } else
                {
                    byte abyte1[] = modslayer.turnbytesUlaw();
                    stream = new SuperStream(abyte1);
                }
                modslayer = null;
            }
            catch(Exception exception)
            {
                System.out.println("Error making a Mod: " + exception);
                loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }
}
