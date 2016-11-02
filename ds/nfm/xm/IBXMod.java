package ds.nfm.xm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import ds.nfm.Module;

public class IBXMod extends Module {
    ibxm.Module refMod;

    public IBXMod(final byte[] modf) {
        try {
            loadMod(new ByteArrayInputStream(modf));
            loaded = true;
        } catch (final Exception var3) {
            loaded = false;
        }

    }

    @Override
    public void loadMod(final InputStream inputstream) throws IOException {
        refMod = new ibxm.Module(inputstream);
        name = refMod.songName;
    }
}
