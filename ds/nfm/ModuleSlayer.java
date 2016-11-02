package ds.nfm;

import java.io.IOException;

public abstract class ModuleSlayer {
    public int oln = 0;
    public int olav = 0;
    public int rollBack = -1;
    public int rollBackPos = 0;
    public int rollBackTrig = 0;
    public boolean loopMark = false;

    public abstract byte[] turnbytesNorm(boolean var1) throws IOException;
}
