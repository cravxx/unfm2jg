import java.io.ByteArrayInputStream;

public class SuperStream extends ByteArrayInputStream
{

    public SuperStream(byte abyte0[])
    {
        super(abyte0);
    }

    public int read()
    {
        int i = super.read();
        if(i == -1)
        {
            reset();
            i = super.read();
        }
        return i;
    }

    public int read(byte abyte0[], int i, int j)
    {
        int k;
        for(k = 0; k < j;)
        {
            int l = super.read(abyte0, i + k, j - k);
            if(l >= 0)
            {
                k += l;
            } else
            {
                reset();
            }
        }

        return k;
    }
}
