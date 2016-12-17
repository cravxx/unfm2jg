import java.io.IOException;

class ModSlayer {

    private final Mod mod;
    private ModTrackInfo tracks[];
    public static final String VERSION = "1.0";
    public static final String COPYRIGHT = "";
    public static final int EFF_VOL_SLIDE = 1;
    public static final int EFF_PORT_DOWN = 2;
    public static final int EFF_PORT_UP = 4;
    public static final int EFF_VIBRATO = 8;
    public static final int EFF_ARPEGGIO = 16;
    public static final int EFF_PORT_TO = 32;
    public static final int EFF_TREMOLO = 64;
    public static final int EFF_RETRIG = 128;
    public static final int MIX_BUF_SIZE = 2048;
    public static final int DEF_TEMPO_NTSC = 6;
    public static final int DEF_TEMPO_PAL = 6;
    public static final int DEF_BPM_NTSC = 125;
    public static final int DEF_BPM_PAL = 145;
    public static final int MIDCRATE = 8448;
    public static final int MAX_SAMPLES = 100;
    public static final int MAX_TRACKS = 32;
    public static final int S3M_MAGIC1 = 4122;
    public static final int S3M_MAGIC2 = Mod.fourCC("SCRM");
    public static final int S3M_INSTR2 = Mod.fourCC("SCRS");
    private static final int normal_vol_adj[] = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
            29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 58, 59, 60, 61, 62, 63, 63
    };
    private static final int loud_vol_adj[] = {
            0, 0, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42,
            44, 46, 47, 48, 49, 50, 51, 52, 53, 53, 54, 55, 55, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 61, 62,
            62, 62, 63, 63, 63, 63, 63, 63
    };
    private static final int sintable[] = {
            0, 25, 50, 74, 98, 120, 142, 162, 180, 197, 212, 225, 236, 244, 250, 254, 255, 254, 250, 244, 236, 225, 212,
            197, 180, 162, 142, 120, 98, 74, 50, 25
    };
    private static final int period_set[] = {
            1712, 1616, 1525, 1440, 1359, 1283, 1211, 1143, 1078, 1018, 961, 907, 856, 808, 763, 720, 679, 641, 605,
            571, 539, 509, 480, 453, 428, 404, 381, 360, 340, 321, 303, 286, 270, 254, 240, 227, 214, 202, 191, 180,
            170, 160, 151, 143, 135, 127, 120, 113, 107, 101, 95, 90, 85, 80, 76, 71, 67, 64, 60, 57, 53, 50, 48, 45,
            42, 40, 38, 36, 34, 32, 30, 28, 27, 25, 24, 22, 21, 20, 19, 18, 17, 16, 15, 14
    };
    public static final int period_set_step[] = {
            1664, 1570, 1482, 1399, 1321, 1247, 1177, 1110, 1048, 989, 934, 881, 832, 785, 741, 699, 660, 623, 588, 555,
            524, 494, 466, 440, 416, 392, 370, 350, 330, 312, 294, 278, 262, 247, 233, 220, 208, 196, 185, 175, 165,
            155, 147, 139, 131, 123, 116, 110, 104, 98, 92, 87, 82, 78, 73, 69, 65, 62, 58, 55, 51, 49, 46, 43, 41, 39,
            37, 35, 33, 31, 29, 27, 26, 24, 23, 21, 20, 19, 18, 17, 16, 15, 14, 14
    };
    private final int def_tempo;
    private final int def_bpm;
    private byte[] vol_table;
    private int vol_adj[];
    private int vol_shift;
    private int order_pos;
    private int tempo;
    private int tempo_wait;
    private int bpm;
    private int row;
    private int break_row;
    private int bpm_samples;
    private int pattofs;
    private byte patt[];
    private int numtracks;
    private int mixspeed;
    private boolean mod_done;
    private boolean bit16;
    private final int samplingrate;
    private final int oversample;
    private final int gain;
    private int nloops;
    private final boolean loud;
    public static final byte sunfmt[] = {
            46, 115, 110, 100, 0, 0, 0, 24, 127, 127, 127, 127, 0, 0, 0, 1, 0, 0, 31, 76, 0, 0, 0, 1, 0, 0, 0, 0
    };
    public int oln;

    private void beattrack(ModTrackInfo modtrackinfo) {
        if (modtrackinfo.period_low_limit == 0) {
            modtrackinfo.period_low_limit = 1;
        }
        if ((modtrackinfo.effect & 1) != 0) {
            modtrackinfo.volume += modtrackinfo.vol_slide;
            if (modtrackinfo.volume < 0) {
                modtrackinfo.volume = 0;
            }
            if (modtrackinfo.volume > 64) {
                modtrackinfo.volume = 64;
            }
        }
        if ((modtrackinfo.effect & 2) != 0) {
            if ((modtrackinfo.period += modtrackinfo.port_down) > modtrackinfo.period_high_limit) {
                modtrackinfo.period = modtrackinfo.period_high_limit;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 4) != 0) {
            if ((modtrackinfo.period -= modtrackinfo.port_up) < modtrackinfo.period_low_limit) {
                if (mod.s3m) {
                    modtrackinfo.period = modtrackinfo.period_high_limit;
                } else {
                    modtrackinfo.period = modtrackinfo.period_low_limit;
                }
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 0x20) != 0) {
            if (modtrackinfo.portto < modtrackinfo.period) {
                if ((modtrackinfo.period += modtrackinfo.port_inc) > modtrackinfo.portto) {
                    modtrackinfo.period = modtrackinfo.portto;
                }
            } else if (modtrackinfo.portto > modtrackinfo.period
                    && (modtrackinfo.period -= modtrackinfo.port_inc) < modtrackinfo.portto) {
                modtrackinfo.period = modtrackinfo.portto;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 8) != 0) {
            modtrackinfo.vibpos += modtrackinfo.vib_rate << 2;
            int i = sintable[modtrackinfo.vibpos >> 2 & 0x1f] * modtrackinfo.vib_depth >> 7;
            if ((modtrackinfo.vibpos & 0x80) != 0) {
                i = -i;
            }
            i += modtrackinfo.period;
            if (i < modtrackinfo.period_low_limit) {
                i = modtrackinfo.period_low_limit;
            }
            if (i > modtrackinfo.period_high_limit) {
                i = modtrackinfo.period_high_limit;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / i;
        }
        if ((modtrackinfo.effect & 0x10) != 0) {
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.arp[modtrackinfo.arpindex];
            modtrackinfo.arpindex++;
            if (modtrackinfo.arpindex >= 3) {
                modtrackinfo.arpindex = 0;
            }
        }
    }

    private void mixtrack16Mono(ModTrackInfo modtrackinfo, int ai[], int i, int j) {
        byte abyte0[] = modtrackinfo.samples;
        int k = modtrackinfo.position;
        int j1 = vol_adj[modtrackinfo.volume] * gain >> vol_shift + 8;
        int i2 = modtrackinfo.error;
        int k1 = modtrackinfo.pitch & 0xfff;
        int l1 = modtrackinfo.pitch >> 12;
        if (modtrackinfo.replen < 3) {
            int l = modtrackinfo.length;
            if (k >= l) {
                return;
            }
            int j2 = i + j;
            if (modtrackinfo.pitch < 4096) {
                while (k < l && i < j2) {
                    ai[i++] += (abyte0[k] * (4096 - i2) + abyte0[k + 1] * i2) * j1 >> 12;
                    k += l1 + ((i2 += k1) >> 12);
                    i2 &= 0xfff;
                }
            } else {
                while (k < l && i < j2) {
                    ai[i++] += abyte0[k] * j1;
                    k += l1 + ((i2 += k1) >> 12);
                    i2 &= 0xfff;
                }
            }
            modtrackinfo.error = i2;
            modtrackinfo.position = k;
        } else {
            int i1 = modtrackinfo.replen + modtrackinfo.repeat;
            if (modtrackinfo.pitch < 4096) {
                for (; j > 0; j--) {
                    if (k >= i1) {
                        k -= modtrackinfo.replen;
                    }
                    ai[i++] += (abyte0[k] * (4096 - i2) + abyte0[k + 1] * i2) * j1 >> 12;
                    k += l1 + ((i2 += k1) >> 12);
                    i2 &= 0xfff;
                }

            } else {
                for (; j > 0; j--) {
                    if (k >= i1) {
                        k -= modtrackinfo.replen;
                    }
                    ai[i++] += abyte0[k] * j1;
                    k += l1 + ((i2 += k1) >> 12);
                    i2 &= 0xfff;
                }

            }
            modtrackinfo.error = i2;
            modtrackinfo.position = k;
        }
    }

    ModSlayer(Mod mod1, int i, int j, int k) {
        mod_done = false;
        nloops = 1;
        loud = false;
        oln = 0;
        samplingrate = i;
        gain = j;
        oversample = 1;
        mod = mod1;
        def_tempo = 6;
        def_bpm = k;
    }

    private void makeVolTable8() {
        vol_table = new byte[16640];
        int i = 0;
        do {
            vol_table[i] = (byte) (vol_adj[i >> 8] * (byte) i >> 8 + vol_shift);
        } while (++i < 16640);
    }

    public byte[] turnbytesNorm() {
        bit16 = true;
        startplaying(loud);
        int ai[] = new int[mixspeed];
        int ai1[] = new int[mixspeed];
        byte abyte0[] = new byte[0x112a880];
        oln = 0;
        while (!mod_done) {
            if (--tempo_wait > 0) {
                for (int l = 0; l < numtracks; l++) {
                    beattrack(tracks[l]);
                }

            } else {
                updatetracks();
            }
            System.arraycopy(ai1, 0, ai, 0, bpm_samples);
            for (int i = 0; i < numtracks; i++) {
                mixtrack16Mono(tracks[i], ai, 0, bpm_samples);
            }

            int i1 = bpm_samples;
            if (oversample > 1) {
                int j1 = 0;
                i1 = bpm_samples / oversample;
                if (oversample == 2) {
                    for (int j = 0; j < i1; j++) {
                        ai[j] = ai[j1] + ai[j1 + 1] >> 1;
                        j1 += 2;
                    }

                } else {
                    for (int k = 0; k < i1; k++) {
                        int k1 = ai[j1++];
                        for (int l1 = 1; l1 < oversample; l1++) {
                            k1 += ai[j1++];
                        }

                        ai[k] = k1 / oversample;
                    }

                }
            }
            if (oln + i1 < 0x112a880) {
                intToBytes16(ai, abyte0, i1, oln);
                oln += i1;
            }
        }
        oln++;
        return abyte0;
    }

    private void updatetracks() {
        tempo_wait = tempo;
        if (row >= 64) {
            if (order_pos >= mod.song_length_patterns) {
                order_pos = 0;
                nloops--;
                if (nloops == 0) {
                    mod_done = true;
                }
            }
            row = break_row;
            break_row = 0;
            if (mod.positions[order_pos] == 255) {
                order_pos = 0;
                row = 0;
            }
            patt = mod.patterns[mod.positions[order_pos]];
            pattofs = row * 4 * numtracks;
            order_pos++;
        }
        row++;
        for (int i = 0; i < numtracks; i++) {
            pattofs = getTrack(tracks[i], patt, pattofs);
        }

    }

    private int getTrack(ModTrackInfo modtrackinfo, byte abyte0[], int i) {
        int j = abyte0[i] & 0xf0;
        int k = (abyte0[i++] & 0xf) << 8;
        k |= abyte0[i++] & 0xff;
        int l = abyte0[i] & 0xf;
        j |= (abyte0[i++] & 0xf0) >> 4;
        int i1 = abyte0[i++];
        modtrackinfo.effect = 0;
        if (j != 0) {
            j--;
            ModInstrument modinstrument = mod.insts[j];
            modtrackinfo.volume = modinstrument.volume;
            modtrackinfo.length = modinstrument.sample_length;
            modtrackinfo.repeat = modinstrument.repeat_point;
            modtrackinfo.replen = modinstrument.repeat_length;
            modtrackinfo.finetune_rate = modinstrument.finetune_rate;
            modtrackinfo.samples = modinstrument.samples;
            modtrackinfo.period_low_limit = modinstrument.period_low_limit;
            modtrackinfo.period_high_limit = modinstrument.period_high_limit;
        }
        if (k != 0) {
            modtrackinfo.portto = k;
            if (l != 3 && l != 5) {
                modtrackinfo.start_period = modtrackinfo.period = k;
                modtrackinfo.pitch = modtrackinfo.finetune_rate / k;
                modtrackinfo.position = 0;
            }
        }
        if (l != 0 || i1 != 0) {
            label0:
            switch (l) {
                case 7: // '\007'
                case 8: // '\b'
                case 11: // '\013'
                default:
                    break;

                case 0: // '\0'
                    int j1;
                    for (j1 = 12; modtrackinfo.period < period_set[j1] && ++j1 < 48; ) {
                    }
                    modtrackinfo.arp[0] = period_set[j1];
                    modtrackinfo.arp[1] = period_set[j1 + (i1 & 0xf)];
                    modtrackinfo.arp[2] = period_set[j1 + ((i1 & 0xf0) >> 4)];
                    modtrackinfo.arpindex = 0;
                    modtrackinfo.effect |= 0x10;
                    break;

                case 1: // '\001'
                    modtrackinfo.effect |= 4;
                    if (i1 != 0) {
                        modtrackinfo.port_up = i1;
                    }
                    break;

                case 2: // '\002'
                    modtrackinfo.effect |= 2;
                    if (i1 != 0) {
                        modtrackinfo.port_down = i1;
                    }
                    break;

                case 3: // '\003'
                    if (i1 != 0) {
                        modtrackinfo.port_inc = i1 & 0xff;
                    }
                    modtrackinfo.effect |= 0x20;
                    break;

                case 4: // '\004'
                    if ((i1 & 0xf) != 0) {
                        modtrackinfo.vib_depth = i1 & 0xf;
                    }
                    if ((i1 & 0xf0) != 0) {
                        modtrackinfo.vib_rate = (i1 & 0xf0) >> 4;
                    }
                    if (k != 0) {
                        modtrackinfo.vibpos = 0;
                    }
                    modtrackinfo.effect |= 8;
                    break;

                case 9: // '\t'
                    if (i1 == 0) {
                        i1 = modtrackinfo.oldsampofs;
                    }
                    modtrackinfo.oldsampofs = i1;
                    modtrackinfo.position = (i1 & 0xff) << 8;
                    break;

                case 5: // '\005'
                    modtrackinfo.effect |= 0x20;
                    // fall through

                case 6: // '\006'
                    if (l == 6) {
                        modtrackinfo.effect |= 8;
                    }
                    // fall through

                case 10: // '\n'
                    modtrackinfo.vol_slide = ((i1 & 0xf0) >> 4) - (i1 & 0xf);
                    modtrackinfo.effect |= 1;
                    break;

                case 12: // '\f'
                    if (i1 > 64 || i1 < 0) {
                        modtrackinfo.volume = 64;
                    } else {
                        modtrackinfo.volume = i1;
                    }
                    break;

                case 13: // '\r'
                    break_row = ((i1 & 0xf0) >> 4) * 10 + (i1 & 0xf);
                    row = 64;
                    break;

                case 14: // '\016'
                    int k1 = i1 & 0xf0;
                    i1 &= 0xf;
                    switch (k1) {
                        default:
                            break label0;

                        case 1: // '\001'
                            modtrackinfo.period += i1;
                            if (modtrackinfo.period > modtrackinfo.period_high_limit) {
                                modtrackinfo.period = modtrackinfo.period_high_limit;
                            }
                            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                            break label0;

                        case 2: // '\002'
                            modtrackinfo.period -= i1;
                            break;
                    }
                    if (modtrackinfo.period < modtrackinfo.period_low_limit) {
                        modtrackinfo.period = modtrackinfo.period_low_limit;
                    }
                    modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                    break;

                case 15: // '\017'
                    if (i1 == 0) {
                        break;
                    }
                    i1 &= 0xff;
                    if (i1 <= 32) {
                        tempo = i1;
                        tempo_wait = i1;
                    } else {
                        bpm = i1;
                        bpm_samples = (samplingrate / (103 * i1 >> 8)) * oversample;
                    }
                    break;
            }
        }
        return i;
    }

    private void startplaying(boolean flag) {
        vol_adj = flag ? loud_vol_adj : normal_vol_adj;
        mixspeed = samplingrate * oversample;
        order_pos = 0;
        tempo_wait = tempo = def_tempo;
        bpm = def_bpm;
        row = 64;
        break_row = 0;
        bpm_samples = (samplingrate / ((24 * bpm) / 60)) * oversample;
        numtracks = mod.numtracks;
        tracks = new ModTrackInfo[numtracks];
        for (int i = 0; i < tracks.length; i++) {
            tracks[i] = new ModTrackInfo();
        }

        if (mod.s3m) {
            for (int j = 0; j < mod.insts.length; j++) {
                ModInstrument modinstrument = mod.insts[j];
                modinstrument.finetune_rate = (int) (428L * modinstrument.finetune_value << 8) / mixspeed;
                modinstrument.period_low_limit = 14;
                modinstrument.period_high_limit = 1712;
            }

        } else {
            for (int k = 0; k < mod.insts.length; k++) {
                ModInstrument modinstrument1 = mod.insts[k];
                modinstrument1.finetune_rate = (int) (0x14b080000000L
                        / (mixspeed * (1536 - modinstrument1.finetune_value)));
                modinstrument1.period_low_limit = 113;
                modinstrument1.period_high_limit = 856;
            }

        }
        if (numtracks > 8) {
            vol_shift = 2;
        } else if (numtracks > 4) {
            vol_shift = 1;
        } else {
            vol_shift = 0;
        }
        if (!bit16) {
            makeVolTable8();
        }
    }

    private static void intToBytes16(int ai[], byte abyte0[], int i, int j) {
        int k = j;
        for (int l = 0; l < i; l++) {
            if (ai[l] < -32767) {
                ai[l] = -32767;
            }
            if (ai[l] > 32767) {
                ai[l] = 32767;
            }
            abyte0[k++] = (byte) (ai[l] >> 8);
            abyte0[k] = (byte) (ai[l] & 0xff);
        }

    }

    public byte[] turnbytesUlaw() {
        bit16 = true;
        startplaying(loud);
        int ai[] = new int[mixspeed];
        int ai1[] = new int[mixspeed];
        int ai2[] = new int[0x30d400];
        oln = 0;
        while (!mod_done) {
            if (--tempo_wait > 0) {
                for (int k1 = 0; k1 < numtracks; k1++) {
                    beattrack(tracks[k1]);
                }

            } else {
                updatetracks();
            }
            System.arraycopy(ai1, 0, ai, 0, bpm_samples);
            for (int i = 0; i < numtracks; i++) {
                mixtrack16Mono(tracks[i], ai, 0, bpm_samples);
            }

            int l1 = bpm_samples;
            if (oversample > 1) {
                int i2 = 0;
                l1 = bpm_samples / oversample;
                if (oversample == 2) {
                    for (int j = 0; j < l1; j++) {
                        ai[j] = ai[i2] + ai[i2 + 1] >> 1;
                        i2 += 2;
                    }

                } else {
                    for (int k = 0; k < l1; k++) {
                        int k2 = ai[i2++];
                        for (int l2 = 1; l2 < oversample; l2++) {
                            k2 += ai[i2++];
                        }

                        ai[k] = k2 / oversample;
                    }

                }
            }
            for (int l = 0; l < l1; l++) {
                if (oln < 0x30d400) {
                    ai2[oln] = ai[l];
                    oln++;
                }
            }

        }
        for (int i1 = 2; i1 < oln; i1++) {
            ai2[i1] = (ai2[i1] + ai2[i1 - 2]) / 2;
        }

        for (int j1 = 57; j1 < oln; j1++) {
            ai2[j1] = (ai2[j1] + ai2[j1] + ai2[j1 - 50]) / 3;
        }

        byte abyte0[] = new byte[oln];
        for (int j2 = 0; j2 < oln; j2++) {
            abyte0[j2] = UlawUtils.linear2ulawclip(ai2[j2]);
        }

        return abyte0;
    }

}
