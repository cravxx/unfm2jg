package ds.nfm.mod;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import ds.nfm.Module;

public class Mod extends Module {
    int numtracks;
    int track_shift;
    int numpatterns;
    byte[][] patterns;
    ModInstrument[] insts;
    byte[] positions;
    int song_length_patterns;
    int song_repeat_patterns;
    boolean s3m;
    static final int voice_mk = FOURCC("M.K.");
    static final int voice_mk2 = FOURCC("M!K!");
    static final int voice_mk3 = FOURCC("M&K!");
    static final int voice_flt4 = FOURCC("FLT4");
    static final int voice_flt8 = FOURCC("FLT8");
    static final int voice_fltoct = FOURCC("OCTA");
    static final int voice_6chn = FOURCC("6CHN");
    static final int voice_8chn = FOURCC("8CHN");
    static final int voice_10ch = FOURCC("10CH");
    static final int voice_12ch = FOURCC("12CH");
    static final int voice_14ch = FOURCC("14CH");
    static final int voice_16ch = FOURCC("16CH");
    static final int voice_18ch = FOURCC("18CH");
    static final int voice_20ch = FOURCC("20CH");
    static final int voice_22ch = FOURCC("22CH");
    static final int voice_24ch = FOURCC("24CH");
    static final int voice_26ch = FOURCC("26CH");
    static final int voice_28ch = FOURCC("28CH");
    static final int voice_30ch = FOURCC("30CH");
    static final int voice_32ch = FOURCC("32CH");
    static final int voice_11ch = FOURCC("11CH");
    static final int voice_13ch = FOURCC("13CH");
    static final int voice_15ch = FOURCC("15CH");
    static final int voice_tdz1 = FOURCC("TDZ1");
    static final int voice_tdz2 = FOURCC("TDZ2");
    static final int voice_tdz3 = FOURCC("TDZ3");
    static final int voice_5chn = FOURCC("5CHN");
    static final int voice_7chn = FOURCC("7CHN");
    static final int voice_9chn = FOURCC("9CHN");
    static final int[] voice_31_list;

    public Mod(final byte[] modf) {
        try {
            loadMod(new ByteArrayInputStream(modf));
            loaded = true;
        } catch (final Exception var3) {
            loaded = false;
        }

    }

    static int FOURCC(final String s) {
        return s.charAt(3) & 255 | (s.charAt(2) & 255) << 8 | (s.charAt(1) & 255) << 16 | (s.charAt(0) & 255) << 24;
    }

    @Override
    public void loadMod(final InputStream inputstream) throws IOException {
        final DataInputStream datainputstream = new DataInputStream(inputstream);
        byte samples = 15;
        numtracks = 4;
        name = readText(datainputstream, 20);
        if (name.trim().isEmpty()) {
            name = "Untitled";
        }

        datainputstream.mark(1068);
        datainputstream.skip(1060L);
        final int signature = datainputstream.readInt();
        datainputstream.reset();

        int _ex;
        for (_ex = 0; _ex < voice_31_list.length; ++_ex) {
            if (signature == voice_31_list[_ex]) {
                samples = 31;
                break;
            }
        }

        if (samples == 31) {
            if (signature == voice_8chn) {
                numtracks = 8;
            } else if (signature == voice_6chn) {
                numtracks = 6;
            } else if (signature == voice_10ch) {
                numtracks = 10;
            } else if (signature == voice_28ch) {
                numtracks = 28;
            } else if (signature == voice_12ch) {
                numtracks = 12;
            } else if (signature == voice_14ch) {
                numtracks = 14;
            } else if (signature == voice_16ch) {
                numtracks = 16;
            } else if (signature == voice_18ch) {
                numtracks = 18;
            } else if (signature == voice_20ch) {
                numtracks = 20;
            } else if (signature == voice_22ch) {
                numtracks = 22;
            } else if (signature == voice_24ch) {
                numtracks = 24;
            } else if (signature == voice_26ch) {
                numtracks = 26;
            } else if (signature == voice_30ch) {
                numtracks = 30;
            } else if (signature == voice_32ch) {
                numtracks = 32;
            } else if (signature == voice_tdz1) {
                numtracks = 1;
            } else if (signature == voice_tdz2) {
                numtracks = 2;
            } else if (signature == voice_tdz3) {
                numtracks = 3;
            } else if (signature == voice_5chn) {
                numtracks = 5;
            } else if (signature == voice_7chn) {
                numtracks = 7;
            } else if (signature == voice_9chn) {
                numtracks = 9;
            } else if (signature == voice_11ch) {
                numtracks = 11;
            } else if (signature == voice_13ch) {
                numtracks = 13;
            } else if (signature == voice_15ch) {
                numtracks = 15;
            }
        } else {
            System.out.print("Format unknown. Checking bytes... ");
            datainputstream.mark(0);
            datainputstream.reset();
            datainputstream.skip(471L);
            final byte var7 = datainputstream.readByte();
            if (var7 >= 32 && var7 <= 126) {
                samples = 31;
            }

            System.out.println(var7);
            datainputstream.mark(1068);
            datainputstream.reset();
            datainputstream.skip(1064L);
        }

        insts = new ModInstrument[samples];

        for (_ex = 0; _ex < samples; ++_ex) {
            insts[_ex] = readInstrument(datainputstream);
        }

        readSequence(datainputstream);
        datainputstream.skipBytes(4);
        readPatterns(datainputstream);

        try {
            for (_ex = 0; _ex < samples; ++_ex) {
                readSampleData(datainputstream, insts[_ex]);
            }
        } catch (final EOFException var6) {
            System.out.println("Warning: EOF on MOD file");
        }

        datainputstream.close();
        inputstream.close();
    }

    public int getNumPatterns() {
        return numpatterns;
    }

    public int getNumTracks() {
        return numtracks;
    }

    static ModInstrument readInstrument(final DataInputStream datainputstream) throws IOException {
        final ModInstrument modinstrument = new ModInstrument();
        modinstrument.name = readText(datainputstream, 22);
        modinstrument.sample_length = readu16(datainputstream) << 1;
        modinstrument.samples = new byte[modinstrument.sample_length + 8];
        int fine = readu8(datainputstream) & 15;
        fine = fine > 7 ? fine - 16 : fine;
        modinstrument.finetune_value = (byte) (fine << 4);
        modinstrument.volume = readu8(datainputstream);
        modinstrument.repeat_point = readu16(datainputstream) << 1;
        modinstrument.repeat_length = readu16(datainputstream) << 1;
        if (modinstrument.repeat_point > modinstrument.sample_length) {
            modinstrument.repeat_point = modinstrument.sample_length - 1;
        }

        if (modinstrument.repeat_point + modinstrument.repeat_length > modinstrument.sample_length) {
            modinstrument.repeat_length = modinstrument.sample_length - modinstrument.repeat_point;
        }

        return modinstrument;
    }

    void readPatterns(final DataInputStream datainputstream) throws IOException {
        final int i = numtracks * 4 * 64;
        patterns = new byte[numpatterns][];

        for (int j = 0; j < numpatterns; ++j) {
            patterns[j] = new byte[i];
            datainputstream.readFully(patterns[j], 0, i);
        }

    }

    static void readSampleData(final DataInputStream datainputstream, final ModInstrument modinstrument) throws IOException {
        datainputstream.readFully(modinstrument.samples, 0, modinstrument.sample_length);
        if (modinstrument.repeat_length > 3) {
            System.arraycopy(modinstrument.samples, modinstrument.repeat_point, modinstrument.samples, modinstrument.sample_length, 8);
        }

    }

    void readSequence(final DataInputStream datainputstream) throws IOException {
        positions = new byte[128];
        song_length_patterns = readu8(datainputstream);
        song_repeat_patterns = readu8(datainputstream);
        datainputstream.readFully(positions, 0, 128);
        if (song_repeat_patterns > song_length_patterns) {
            song_repeat_patterns = song_length_patterns;
        }

        numpatterns = 0;

        for (final byte position : positions) {
            if (position > numpatterns) {
                numpatterns = position;
            }
        }

        ++numpatterns;
    }

    static String readText(final DataInputStream datainputstream, final int i) throws IOException {
        final byte[] abyte0 = new byte[i];
        datainputstream.readFully(abyte0, 0, i);

        for (int j = i - 1; j >= 0; --j) {
            if (abyte0[j] != 0)
                return new String(abyte0, 0, 0, j + 1);
        }

        return "";
    }

    static int readu16(final DataInputStream datainputstream) throws IOException {
        return datainputstream.readShort() & '\uffff';
    }

    static int readu8(final DataInputStream datainputstream) throws IOException {
        return datainputstream.readByte() & 255;
    }

    @Override
    public String toString() {
        return name + " (" + numtracks + " tracks, " + numpatterns + " patterns, " + insts.length + " samples)";
    }

    static {
        voice_31_list = new int[] {
                voice_mk, voice_mk2, voice_mk3, voice_flt4, voice_flt8, voice_8chn, voice_6chn, voice_10ch, voice_12ch,
                voice_14ch, voice_16ch, voice_18ch, voice_20ch, voice_22ch, voice_24ch, voice_26ch, voice_28ch,
                voice_30ch, voice_32ch, voice_11ch, voice_13ch, voice_15ch, voice_tdz1, voice_tdz2, voice_tdz3,
                voice_5chn, voice_7chn, voice_9chn
        };
    }
}
