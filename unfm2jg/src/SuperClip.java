import java.io.ByteArrayInputStream;
import javax.sound.sampled.*;

public class SuperClip
    implements Runnable
{

    int skiprate;
    Thread cliper;
    int stoped;
    SourceDataLine source;
    ByteArrayInputStream stream;

    public SuperClip(byte abyte0[], int i, int j)
    {
        skiprate = 0;
        stoped = 1;
        source = null;
        stoped = 2;
        skiprate = j;
        stream = new ByteArrayInputStream(abyte0, 0, i);
    }

    public void run()
    {
        boolean flag = false;
        try
        {
            javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(javax.sound.sampled.SourceDataLine.class, new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, -1F, 16, 2, 4, -1F, true));
            source = (SourceDataLine)AudioSystem.getLine(info);
            source.open(new AudioFormat(skiprate, 16, 1, false, false));
            source.start();
        }
        catch(Exception exception)
        {
            stoped = 1;
        }
        while(stoped == 0) 
        {
            try
            {
                if(source.available() < skiprate || !flag)
                {
                    byte abyte0[] = new byte[skiprate];
                    int i = stream.read(abyte0, 0, abyte0.length);
                    if(i == -1)
                    {
                        stream.reset();
                        stream.read(abyte0, 0, abyte0.length);
                    }
                    source.write(abyte0, 0, abyte0.length);
                    flag = true;
                }
            }
            catch(Exception exception1)
            {
                System.out.println("play error: " + exception1);
                stoped = 1;
            }
            try
            {
                Thread.sleep(200L);
            }
            catch(InterruptedException interruptedexception) { }
        }
        source.stop();
        source.close();
        source = null;
        stoped = 2;
    }

    public void play()
    {
        if(stoped == 2)
        {
            stoped = 0;
            try
            {
                stream.reset();
            }
            catch(Exception exception) { }
            cliper = new Thread(this);
            cliper.start();
        }
    }

    public void resume()
    {
        if(stoped == 2)
        {
            stoped = 0;
            cliper = new Thread(this);
            cliper.start();
        }
    }

    public void stop()
    {
        if(stoped == 0)
        {
            stoped = 1;
            if(source != null)
            {
                source.stop();
            }
        }
    }

    public void close()
    {
        try
        {
            stream.close();
            stream = null;
        }
        catch(Exception exception) { }
    }
}
