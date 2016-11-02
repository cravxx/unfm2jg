package ds.nfm.mod;

import java.io.IOException;

import ds.nfm.ModuleSlayer;

public class ModSlayer extends ModuleSlayer {
    static final String VERSION = "1.1";
    static final String COPYRIGHT = "DragShot Software (c) 2014";
    static final int EFF_VOL_SLIDE = 1;
    static final int EFF_PORT_DOWN = 2;
    static final int EFF_PORT_UP = 4;
    static final int EFF_VIBRATO = 8;
    static final int EFF_ARPEGGIO = 16;
    static final int EFF_PORT_TO = 32;
    static final int EFF_TREMOLO = 64;
    static final int EFF_RETRIG = 128;
    static final int MIX_BUF_SIZE = 2048;
    static final int DEF_TEMPO_NTSC = 6;
    static final int DEF_TEMPO_PAL = 6;
    static final int DEF_BPM_NTSC = 125;
    static final int DEF_BPM_PAL = 145;
    static final int MIDCRATE = 8448;
    static final int MAX_SAMPLES = 100;
    static final int MAX_TRACKS = 32;
    static final int S3M_MAGIC1 = 4122;
    static final int S3M_MAGIC2 = Mod.FOURCC("SCRM");
    static final int S3M_INSTR2 = Mod.FOURCC("SCRS");
    static final int[] normal_vol_adj = new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
            29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 58, 59, 60, 61, 62, 63, 63
    };
    static final int[] loud_vol_adj = new int[] {
            0, 0, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42,
            44, 46, 47, 48, 49, 50, 51, 52, 53, 53, 54, 55, 55, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 61, 62,
            62, 62, 63, 63, 63, 63, 63, 63
    };
    static final int[] sintable = new int[] {
            0, 25, 50, 74, 98, 120, 142, 162, 180, 197, 212, 225, 236, 244, 250, 254, 255, 254, 250, 244, 236, 225, 212,
            197, 180, 162, 142, 120, 98, 74, 50, 25
    };
    static final int[] period_set = new int[] {
            1712, 1616, 1525, 1440, 1359, 1283, 1211, 1143, 1078, 1018, 961, 907, 856, 808, 763, 720, 679, 641, 605,
            571, 539, 509, 480, 453, 428, 404, 381, 360, 340, 321, 303, 286, 270, 254, 240, 227, 214, 202, 191, 180,
            170, 160, 151, 143, 135, 127, 120, 113, 107, 101, 95, 90, 85, 80, 76, 71, 67, 64, 60, 57, 53, 50, 48, 45,
            42, 40, 38, 36, 34, 32, 30, 28, 27, 25, 24, 22, 21, 20, 19, 18, 17, 16, 15, 14
    };
    static final int[] period_set_step = new int[] {
            1664, 1570, 1482, 1399, 1321, 1247, 1177, 1110, 1048, 989, 934, 881, 832, 785, 741, 699, 660, 623, 588, 555,
            524, 494, 466, 440, 416, 392, 370, 350, 330, 312, 294, 278, 262, 247, 233, 220, 208, 196, 185, 175, 165,
            155, 147, 139, 131, 123, 116, 110, 104, 98, 92, 87, 82, 78, 73, 69, 65, 62, 58, 55, 51, 49, 46, 43, 41, 39,
            37, 35, 33, 31, 29, 27, 26, 24, 23, 21, 20, 19, 18, 17, 16, 15, 14, 14
    };
    int def_tempo;
    int def_bpm;
    byte[] vol_table;
    int[] vol_adj;
    int vol_shift;
    Mod mod;
    int order_pos;
    int tempo;
    int tempo_wait;
    int bpm;
    int row;
    int break_row;
    int bpm_samples;
    int pattofs;
    byte[] patt;
    int numtracks;
    ModTrackInfo[] tracks;
    int mixspeed;
    boolean mod_done = false;
    public boolean bit16;
    public int samplingrate;
    public int oversample;
    public int gain;
    public int nloops = 1;
    public boolean loud = false;
    int loopA = 0;
    int loopB = 0;
    int loops = 0;
    boolean onLoop = false;
    int jumpTo = -1;
    int jumpLocation = 0;
    boolean reverseJump = false;
    int[] patternOffsets;

    public ModSlayer(final Mod pmod, final int smpl, final int gn, final int bpmflex) {
        samplingrate = smpl;
        gain = gn;
        oversample = 1;
        mod = pmod;
        def_tempo = 6;
        def_bpm = bpmflex;
    }

    final void beattrack(final ModTrackInfo modtrackinfo) {
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

        if ((modtrackinfo.effect & 32) != 0) {
            if (modtrackinfo.portto < modtrackinfo.period) {
                if ((modtrackinfo.period += modtrackinfo.port_inc) > modtrackinfo.portto) {
                    modtrackinfo.period = modtrackinfo.portto;
                }
            } else if (modtrackinfo.portto > modtrackinfo.period && (modtrackinfo.period -= modtrackinfo.port_inc) < modtrackinfo.portto) {
                modtrackinfo.period = modtrackinfo.portto;
            }

            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }

        if ((modtrackinfo.effect & 8) != 0) {
            modtrackinfo.vibpos += modtrackinfo.vib_rate << 2;
            int i = sintable[modtrackinfo.vibpos >> 2 & 31] * modtrackinfo.vib_depth >> 7;
            if ((modtrackinfo.vibpos & 128) != 0) {
                i = -i;
            }

            i += modtrackinfo.period;
            if (mod.s3m) {
                if (i < modtrackinfo.period_low_limit) {
                    i = modtrackinfo.period_low_limit;
                }

                if (i > modtrackinfo.period_high_limit) {
                    i = modtrackinfo.period_high_limit;
                }
            }

            i = i > 0 ? i : 1;
            modtrackinfo.pitch = modtrackinfo.finetune_rate / i;
        }

        if ((modtrackinfo.effect & 16) != 0) {
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.arp[modtrackinfo.arpindex];
            ++modtrackinfo.arpindex;
            if (modtrackinfo.arpindex >= 3) {
                modtrackinfo.arpindex = 0;
            }
        }

    }

    final int get_track(final ModTrackInfo modtrackinfo, final byte[] is, int i) {
        int i_2_ = is[i] & 240;
        int i_3_ = (is[i++] & 15) << 8;
        i_3_ |= is[i++] & 255;
        final int i_4_ = is[i] & 15;
        i_2_ |= (is[i++] & 240) >> 4;
        int i_5_ = is[i++];
        modtrackinfo.effect = 0;
        if (i_2_ != 0) {
            --i_2_;
            final ModInstrument i_7_ = mod.insts[i_2_];
            modtrackinfo.volume = i_7_.volume;
            modtrackinfo.length = i_7_.sample_length;
            modtrackinfo.repeat = i_7_.repeat_point;
            modtrackinfo.replen = i_7_.repeat_length;
            modtrackinfo.finetune_rate = i_7_.finetune_rate;
            modtrackinfo.samples = i_7_.samples;
            modtrackinfo.period_low_limit = i_7_.period_low_limit;
            modtrackinfo.period_high_limit = i_7_.period_high_limit;
        }

        modtrackinfo.notelimit = -1;
        modtrackinfo.noterestart -= tempo;
        if (modtrackinfo.noterestart < -1) {
            modtrackinfo.noterestart = -1;
        }

        if (i_3_ != 0) {
            modtrackinfo.portto = i_3_;
            if (i_4_ != 3 && i_4_ != 5) {
                modtrackinfo.start_period = modtrackinfo.period = i_3_;
                modtrackinfo.pitch = modtrackinfo.finetune_rate / i_3_;
                modtrackinfo.position = 0;
            }
        }

        if (i_4_ != 0 || i_5_ != 0) {
            int var9;
            switch (i_4_) {
                case 0:
                    for (var9 = 12; var9 < 48 && modtrackinfo.period < period_set[var9]; ++var9) {
                    }

                    modtrackinfo.arp[0] = period_set[var9];
                    modtrackinfo.arp[1] = period_set[var9 + (i_5_ & 15)];
                    modtrackinfo.arp[2] = period_set[var9 + ((i_5_ & 240) >> 4)];
                    modtrackinfo.arpindex = 0;
                    modtrackinfo.effect |= 16;
                    break;
                case 1:
                    modtrackinfo.effect |= 4;
                    if (i_5_ != 0) {
                        modtrackinfo.port_up = i_5_;
                    }
                    break;
                case 2:
                    modtrackinfo.effect |= 2;
                    if (i_5_ != 0) {
                        modtrackinfo.port_down = i_5_;
                    }
                    break;
                case 3:
                    if (i_5_ != 0) {
                        modtrackinfo.port_inc = i_5_ & 255;
                    }

                    modtrackinfo.effect |= 32;
                    break;
                case 4:
                    if ((i_5_ & 15) != 0) {
                        modtrackinfo.vib_depth = i_5_ & 15;
                    }

                    if ((i_5_ & 240) != 0) {
                        modtrackinfo.vib_rate = (i_5_ & 240) >> 4;
                    }

                    if (i_3_ != 0) {
                        modtrackinfo.vibpos = 0;
                    }

                    modtrackinfo.effect |= 8;
                    break;
                case 5:
                    modtrackinfo.effect |= 32;
                case 6:
                    if (i_4_ == 6) {
                        modtrackinfo.effect |= 8;
                    }
                case 10:
                    modtrackinfo.vol_slide = ((i_5_ & 240) >> 4) - (i_5_ & 15);
                    modtrackinfo.effect |= 1;
                case 7:
                case 8:
                default:
                    break;
                case 9:
                    if (i_5_ == 0) {
                        i_5_ = modtrackinfo.oldsampofs;
                    }

                    modtrackinfo.oldsampofs = i_5_;
                    modtrackinfo.position = (i_5_ & 255) << 8;
                    break;
                case 11:
                    if (jumpLocation == order_pos && reverseJump) {
                        reverseJump = false;
                    } else if (!reverseJump && i_5_ < 128 && i_5_ >= 0) {
                        jumpTo = i_5_;
                        jumpLocation = order_pos;
                        if (jumpTo < order_pos) {
                            reverseJump = true;
                            if (order_pos == mod.song_length_patterns) {
                                if (i_5_ < order_pos - 2) {
                                    rollBack = i_5_;
                                    rollBackPos = patternOffsets[i_5_];
                                    loopMark = true;
                                }

                                jumpTo = -1;
                                jumpLocation = 0;
                            }
                        }
                    }
                    break;
                case 12:
                    if (i_5_ <= 64 && i_5_ >= 0) {
                        modtrackinfo.volume = i_5_;
                    } else {
                        modtrackinfo.volume = 64;
                    }
                    break;
                case 13:
                    break_row = ((i_5_ & 240) >> 4) * 10 + (i_5_ & 15);
                    row = 64;
                    break;
                case 14:
                    var9 = (i_5_ & 240) >> 4;
                    i_5_ &= 15;
                    switch (var9) {
                        case 1:
                            modtrackinfo.period += i_5_;
                            if (modtrackinfo.period > modtrackinfo.period_high_limit) {
                                modtrackinfo.period = modtrackinfo.period_high_limit;
                            }

                            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                            return i;
                        case 2:
                            modtrackinfo.period -= i_5_;
                            if (modtrackinfo.period < modtrackinfo.period_low_limit) {
                                modtrackinfo.period = modtrackinfo.period_low_limit;
                            }

                            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                            return i;
                        case 3:
                        case 4:
                        case 5:
                        case 7:
                        case 8:
                        default:
                            return i;
                        case 6:
                            if (loops == 0 && !onLoop) {
                                if (i_5_ <= 0) {
                                    loopA = row;
                                } else {
                                    loopB = row;
                                    loops = i_5_;
                                    onLoop = true;
                                }

                                return i;
                            }

                            return i;
                        case 9:
                            modtrackinfo.noterestart = i_5_;
                            return i;
                        case 10:
                            modtrackinfo.volume += i_5_;
                            if (modtrackinfo.volume > 64) {
                                modtrackinfo.volume = 64;
                            }

                            if (modtrackinfo.volume < 0) {
                                modtrackinfo.volume = 0;
                            }

                            return i;
                        case 11:
                            modtrackinfo.volume -= i_5_;
                            if (modtrackinfo.volume > 64) {
                                modtrackinfo.volume = 64;
                            }

                            if (modtrackinfo.volume < 0) {
                                modtrackinfo.volume = 0;
                            }

                            return i;
                        case 12:
                            modtrackinfo.notelimit = i_5_;
                            return i;
                    }
                case 15:
                    if (i_5_ != 0) {
                        i_5_ &= 255;
                        if (i_5_ <= 32) {
                            tempo = i_5_;
                            tempo_wait = i_5_;
                        } else {
                            bpm = i_5_;
                            bpm_samples = samplingrate / (103 * i_5_ >> 8) * oversample;
                        }
                    }
            }
        }

        return i;
    }

    final void make_vol_table8() {
        vol_table = new byte[16640];

        for (int i = 0; i < 16640; ++i) {
            vol_table[i] = (byte) (vol_adj[i >> 8] * (byte) i >> 8 + vol_shift);
        }

    }

    final void mixtrack_16_mono(final ModTrackInfo modtrackinfo, final int[] buffer, int buffpos, int bufflen) {
        final byte[] samples = modtrackinfo.samples;
        int samplepos = modtrackinfo.position;
        final int volume = vol_adj[modtrackinfo.volume] * gain >> vol_shift + 8;
        int error = modtrackinfo.error;
        final int lopitch = modtrackinfo.pitch & 4095;
        final int hipitch = modtrackinfo.pitch >> 12;
        int endtr;
        int var10001;
        if (modtrackinfo.replen < 3) {
            endtr = modtrackinfo.length;
            if (samplepos < endtr) {
                final int buffend = buffpos + bufflen;
                if (modtrackinfo.pitch < 4096) {
                    while (samplepos < endtr && buffpos < buffend) {
                        if (modtrackinfo.notelimit != -1 && tempo - tempo_wait >= modtrackinfo.notelimit) {
                            modtrackinfo.volume = 0;
                        } else {
                            var10001 = buffpos++;
                            buffer[var10001] += (samples[samplepos] * (4096 - error) + samples[samplepos + 1] * error) * volume >> 12;
                        }

                        samplepos += hipitch + ((error += lopitch) >> 12);
                        error &= 4095;
                    }
                } else {
                    while (samplepos < endtr && buffpos < buffend) {
                        if (modtrackinfo.notelimit != -1 && tempo - tempo_wait >= modtrackinfo.notelimit) {
                            modtrackinfo.volume = 0;
                        } else {
                            var10001 = buffpos++;
                            buffer[var10001] += samples[samplepos] * volume;
                        }

                        samplepos += hipitch + ((error += lopitch) >> 12);
                        error &= 4095;
                    }
                }

                modtrackinfo.error = error;
                if (modtrackinfo.noterestart != -1 && tempo - tempo_wait >= modtrackinfo.noterestart) {
                    modtrackinfo.position = 0;
                    modtrackinfo.noterestart = -1;
                } else {
                    modtrackinfo.position = samplepos;
                }
            }
        } else {
            endtr = modtrackinfo.replen + modtrackinfo.repeat;
            if (modtrackinfo.pitch < 4096) {
                while (bufflen > 0) {
                    if (samplepos >= endtr) {
                        samplepos -= modtrackinfo.replen;
                    }

                    if (samplepos < 0) {
                        samplepos = 0;
                    }

                    while (samplepos >= samples.length) {
                        samplepos -= samples.length;
                    }

                    if (modtrackinfo.notelimit != -1 && tempo - tempo_wait >= modtrackinfo.notelimit) {
                        modtrackinfo.volume = 0;
                    } else {
                        var10001 = buffpos++;
                        buffer[var10001] += (samples[samplepos] * (4096 - error) + samples[samplepos + 1] * error) * volume >> 12;
                    }

                    samplepos += hipitch + ((error += lopitch) >> 12);
                    error &= 4095;
                    --bufflen;
                }
            } else {
                while (bufflen > 0) {
                    if (samplepos >= endtr) {
                        samplepos -= modtrackinfo.replen;
                    }

                    if (samplepos < 0) {
                        samplepos = 0;
                    }

                    while (samplepos >= samples.length) {
                        samplepos -= samples.length;
                    }

                    if (modtrackinfo.notelimit != -1 && tempo - tempo_wait >= modtrackinfo.notelimit) {
                        modtrackinfo.volume = 0;
                    } else {
                        var10001 = buffpos++;
                        buffer[var10001] += samples[samplepos] * volume;
                    }

                    samplepos += hipitch + ((error += lopitch) >> 12);
                    error &= 4095;
                    --bufflen;
                }
            }

            modtrackinfo.error = error;
            if (modtrackinfo.noterestart != -1 && tempo - tempo_wait >= modtrackinfo.noterestart) {
                modtrackinfo.position = 0;
                modtrackinfo.noterestart = -1;
            } else {
                modtrackinfo.position = samplepos;
            }
        }

    }

    @Override
    public byte[] turnbytesNorm(final boolean calvol) throws IOException {
        bit16 = true;
        startplaying(loud);
        final int[] buf = new int[mixspeed];
        final int[] emptybuf = new int[mixspeed];
        final byte[] realbytes = new byte[24000000];
        patternOffsets = new int[mod.song_length_patterns];
        oln = 0;
        olav = 1;
        int olniu = 1;

        while (!mod_done) {
            int real_samples;
            if (--tempo_wait > 0) {
                for (real_samples = 0; real_samples < numtracks; ++real_samples) {
                    beattrack(tracks[real_samples]);
                }
            } else {
                updatetracks();
            }

            if (!mod_done) {
                if (row == 1 && tempo_wait == tempo) {
                    patternOffsets[order_pos - 1] = oln;
                }

                System.arraycopy(emptybuf, 0, buf, 0, bpm_samples);

                for (real_samples = 0; real_samples < numtracks; ++real_samples) {
                    mixtrack_16_mono(tracks[real_samples], buf, 0, bpm_samples);
                }

                real_samples = bpm_samples;
                int niu;
                int cav;
                int i;
                if (oversample > 1) {
                    niu = 0;
                    real_samples = bpm_samples / oversample;
                    if (oversample == 2) {
                        for (cav = 0; cav < real_samples; ++cav) {
                            buf[cav] = buf[niu] + buf[niu + 1] >> 1;
                            niu += 2;
                        }
                    } else {
                        for (cav = 0; cav < real_samples; ++cav) {
                            i = buf[niu++];

                            for (int j = 1; j < oversample; ++j) {
                                i += buf[niu++];
                            }

                            buf[cav] = i / oversample;
                        }
                    }
                }

                if (oln + real_samples < 18000000) {
                    if (calvol) {
                        niu = 0;
                        cav = 0;

                        for (i = 0; i < real_samples; ++i) {
                            if (buf[i] > 0) {
                                cav += buf[i];
                                ++niu;
                            }
                        }

                        if (niu != 0) {
                            cav /= niu;
                            olav += cav;
                            ++olniu;
                        }
                    }

                    intToBytes16(buf, realbytes, real_samples, oln);
                    oln += real_samples;
                }
            }

            if (loopMark) {
                rollBackTrig = oln;
                loopMark = false;
            }
        }

        if (calvol) {
            olav /= olniu;
        }

        ++oln;
        return realbytes;
    }

    public static void intToBytes16(final int[] sample, final byte[] buffer, final int realsamples, final int oln) {
        int byteOffset = oln;

        for (int i = 0; i < realsamples; ++i) {
            buffer[byteOffset++] = (byte) (sample[i] >> 8);
            buffer[byteOffset] = (byte) (sample[i] & 255);
        }

    }

    final void startplaying(final boolean flag) {
        vol_adj = flag ? loud_vol_adj : normal_vol_adj;
        mixspeed = samplingrate * oversample;
        order_pos = 0;
        tempo_wait = tempo = def_tempo;
        bpm = def_bpm;
        row = 64;
        break_row = 0;
        bpm_samples = samplingrate / (24 * bpm / 60) * oversample;
        numtracks = mod.numtracks;
        tracks = new ModTrackInfo[numtracks];

        int k;
        for (k = 0; k < tracks.length; ++k) {
            tracks[k] = new ModTrackInfo();
        }

        ModInstrument modinstrument1;
        if (mod.s3m) {
            for (k = 0; k < mod.insts.length; ++k) {
                modinstrument1 = mod.insts[k];
                modinstrument1.finetune_rate = (int) (428L * modinstrument1.finetune_value << 8) / mixspeed;
                modinstrument1.period_low_limit = 14;
                modinstrument1.period_high_limit = 1712;
            }
        } else {
            for (k = 0; k < mod.insts.length; ++k) {
                modinstrument1 = mod.insts[k];
                modinstrument1.finetune_rate = (int) (22748294283264L / (mixspeed * (1536 - modinstrument1.finetune_value)));
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
            make_vol_table8();
        }

    }

    final void updatetracks() {
        tempo_wait = tempo;
        if (jumpTo != -1) {
            onLoop = false;
            loopA = row;
            row = break_row;
            break_row = 0;
            order_pos = jumpTo;
            patt = mod.patterns[mod.positions[order_pos]];
            pattofs = row * 4 * numtracks;
            ++order_pos;
            jumpTo = -1;
        }

        if (row >= 64) {
            onLoop = false;
            loopA = row;
            if (order_pos >= mod.song_length_patterns) {
                order_pos = 0;
                --nloops;
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
            ++order_pos;
        } else {
            if (loops > 0 && loops > 0 && row == loopB) {
                row = loopA - 1;
                pattofs = row * 4 * numtracks;
                --loops;
            }

            if (loops == 0 && row == loopB + 1) {
                onLoop = false;
                loopA = row;
            }
        }

        ++row;

        for (int i = 0; i < numtracks; ++i) {
            pattofs = get_track(tracks[i], patt, pattofs);
        }

    }
}
