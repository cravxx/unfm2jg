import fallk.logmaster.HLogger;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

class Mod {

    private String name;
    public int numtracks;
    private int numpatterns;
    public byte patterns[][];
    public ModInstrument insts[];
    public byte positions[];
    public int song_length_patterns;
    private int song_repeat_patterns;
    public boolean s3m;
    private static final int voice_mk;
    private static final int voice_mk2;
    private static final int voice_mk3;
    private static final int voice_flt4;
    private static final int voice_flt8;
    private static final int voice_28ch;
    private static final int voice_8chn;
    private static final int voice_6chn;
    private static final int voice_31_list[];

    public int getNumPatterns() {
        return numpatterns;
    }

    @Override
    public String toString() {
        return name + " (" + numtracks + " tracks, " + numpatterns + " patterns, " + insts.length + " samples)";
    }

    public Mod(InputStream inputstream) {
        try {
            loadMod(inputstream);
        } catch (Exception exception) {
            HLogger.error("Error loading up a Mod: " + exception);
        }
    }

    private static int readu16(DataInputStream datainputstream) throws IOException {
        return datainputstream.readShort() & 0xffff;
    }

    private static String readText(DataInputStream datainputstream, int i) throws IOException {
        byte abyte0[] = new byte[i];
        datainputstream.readFully(abyte0, 0, i);
        for (int j = i - 1; j >= 0; j--) {
            if (abyte0[j] != 0) {
                return new String(abyte0, 0, 0, j + 1);
            }
        }

        return "";
    }

    private void readSequence(DataInputStream datainputstream) throws IOException {
        positions = new byte[128];
        song_length_patterns = readu8(datainputstream);
        song_repeat_patterns = readu8(datainputstream);
        datainputstream.readFully(positions, 0, 128);
        if (song_repeat_patterns > song_length_patterns) {
            song_repeat_patterns = song_length_patterns;
        }
        numpatterns = 0;
        for (byte position : positions) {
            if (position > numpatterns) {
                numpatterns = position;
            }
        }

        numpatterns++;
    }

    /**
     * datainputstream.skip doesnt check if it worked correctly
     *
     * @param str stream
     * @param n   skiptime
     * @throws IOException yeah
     */
    private void skipExactly(DataInputStream str, long n) throws IOException {
        while (n != 0) {
            long skipped = str.skip(n);
            if (skipped == 0)
                throw new EOFException();
            n -= skipped;
        }
    }

    private void loadMod(InputStream inputstream) throws IOException {
        DataInputStream datainputstream = new DataInputStream(inputstream);
        byte byte0 = 15;
        numtracks = 4;
        name = readText(datainputstream, 20);
        datainputstream.mark(1068);
        /*
		 * datainputstream.skip(1060L);
		 */
        skipExactly(datainputstream, 1060L);
        int i = datainputstream.readInt();
        datainputstream.reset();
        for (int aVoice_31_list : voice_31_list) {
            if (i != aVoice_31_list) {
                continue;
            }
            byte0 = 31;
            break;
        }

        if (byte0 == 31) {
            if (i == voice_8chn) {
                numtracks = 8;
            } else if (i == voice_6chn) {
                numtracks = 6;
            } else if (i == voice_28ch) {
                numtracks = 28;
            }
        }
        insts = new ModInstrument[byte0];
        for (int k = 0; k < byte0; k++) {
            insts[k] = readInstrument(datainputstream);
        }

        readSequence(datainputstream);
        datainputstream.skipBytes(4);
        readPatterns(datainputstream);
        try {
            for (int l = 0; l < byte0; l++) {
                readSampleData(datainputstream, insts[l]);
            }

        } catch (EOFException _ex) {
            HLogger.warn("Warning: EOF on MOD file");
        }
        datainputstream.close();
        inputstream.close();
    }

    private static void readSampleData(DataInputStream datainputstream, ModInstrument modinstrument) throws IOException {
        datainputstream.readFully(modinstrument.samples, 0, modinstrument.sample_length);
        if (modinstrument.repeat_length > 3) {
            System.arraycopy(modinstrument.samples, modinstrument.repeat_point, modinstrument.samples,
                    modinstrument.sample_length, 8);
        }
    }

    private static ModInstrument readInstrument(DataInputStream datainputstream) throws IOException {
        ModInstrument modinstrument = new ModInstrument();
        modinstrument.name = readText(datainputstream, 22);
        modinstrument.sample_length = readu16(datainputstream) << 1;
        modinstrument.samples = new byte[modinstrument.sample_length + 8];
        modinstrument.finetune_value = (byte) (readu8(datainputstream) << 4);
        modinstrument.volume = readu8(datainputstream);
        modinstrument.repeat_point = readu16(datainputstream) << 1;
        modinstrument.repeat_length = readu16(datainputstream) << 1;
        if (modinstrument.repeat_point > modinstrument.sample_length) {
            modinstrument.repeat_point = modinstrument.sample_length;
        }
        if (modinstrument.repeat_point + modinstrument.repeat_length > modinstrument.sample_length) {
            modinstrument.repeat_length = modinstrument.sample_length - modinstrument.repeat_point;
        }
        return modinstrument;
    }

    public static int fourCC(String s) {
        return s.charAt(3) & 0xff | (s.charAt(2) & 0xff) << 8 | (s.charAt(1) & 0xff) << 16 | (s.charAt(0) & 0xff) << 24;
    }

    public int getNumTracks() {
        return numtracks;
    }

    public String getName() {
        return name;
    }

    private void readPatterns(DataInputStream datainputstream) throws IOException {
        int i = numtracks * 4 * 64;
        patterns = new byte[numpatterns][];
        for (int j = 0; j < numpatterns; j++) {
            patterns[j] = new byte[i];
            datainputstream.readFully(patterns[j], 0, i);
        }

    }

    private static int readu8(DataInputStream datainputstream) throws IOException {
        return datainputstream.readByte() & 0xff;
    }

    static {
        voice_mk = fourCC("M.K.");
        voice_mk2 = fourCC("M!K!");
        voice_mk3 = fourCC("M&K!");
        voice_flt4 = fourCC("FLT4");
        voice_flt8 = fourCC("FLT8");
        voice_28ch = fourCC("28CH");
        voice_8chn = fourCC("8CHN");
        voice_6chn = fourCC("6CHN");
        voice_31_list = (new int[]{
                voice_mk, voice_mk2, voice_mk3, voice_flt4, voice_flt8, voice_8chn, voice_6chn, voice_28ch
        });
    }
}
