package ds.nfm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ds.nfm.mod.Mod;
import ds.nfm.mod.ModSlayer;
import ds.nfm.xm.IBXMod;
import ds.nfm.xm.IBXModSlayer;

public class ModuleLoader {
    public static Module loadMod(final String string) throws IOException {
        final ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(new File(string)));
        return loadMod(zipinputstream);
    }

    public static Module loadMod(final URL url) throws IOException {
        final ZipInputStream zipinputstream = new ZipInputStream(new DataInputStream(url.openStream()));
        return loadMod(zipinputstream);
    }

    public static Module loadMod(final byte[] is) throws IOException {
        final ZipInputStream zipinputstream = new ZipInputStream(new ByteArrayInputStream(is));
        return loadMod(zipinputstream);
    }

    public static Module loadMod(final ZipInputStream zipinputstream) throws IOException {
        final ZipEntry zipentry = zipinputstream.getNextEntry();
        final String string = zipentry.getName();
        int i = (int) zipentry.getSize();
        byte[] is;
        int i_2;
        if (i == -1) {
            final ByteArrayOutputStream module = new ByteArrayOutputStream();

            while ((i_2 = zipinputstream.read()) != -1) {
                module.write(i_2);
            }

            is = module.toByteArray();
        } else {
            is = new byte[i];

            for (int module1 = 0; i > 0; i -= i_2) {
                i_2 = zipinputstream.read(is, module1, i);
                module1 += i_2;
            }
        }

        zipinputstream.close();
        Object module2;
        if (!string.toLowerCase().endsWith(".xm") && !string.toLowerCase().endsWith(".s3m")) {
            module2 = new Mod(is);
        } else {
            module2 = new IBXMod(is);
        }

        return (Module) module2;
    }

    public static ModuleSlayer prepareSlayer(final Module module, final int i, final int i_3, final int i_4) {
        if (module instanceof Mod)
            return new ModSlayer((Mod) module, i, i_3, i_4);
        else if (module instanceof IBXMod)
            return new IBXModSlayer((IBXMod) module, i, i_3, i_4);
        else
            throw new ModuleException("Unsuported module format: " + module.getName());
    }
}
