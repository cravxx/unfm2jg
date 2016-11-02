package ds.nfm;

import java.io.IOException;
import java.io.InputStream;

public abstract class Module {
    protected String name;
    protected boolean loaded = false;

    public String getName() {
        return name;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public abstract void loadMod(InputStream var1) throws IOException;
}
