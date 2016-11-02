package ibxm;

import ds.nfm.xm.IBXModSlayer;

/*
	ProTracker, Scream Tracker 3, FastTracker 2 Replay (c)2016 mumart@gmail.com
*/
public class IBXM {
    public static final String VERSION = "a69 (c)2014 mumart@gmail.com";
    private final Module module;
    private final int[] rampBuf;
    private final Channel[] channels;
    private int sampleRate;
    private int interpolation;
    private int seqPos;
    private int breakSeqPos;
    private int row;
    private int nextRow;
    private int tick;
    private int speed;
    private int tempo;
    private int plCount;
    private int plChannel;
    private final GlobalVol globalVol;
    private final Note note;
    private IBXModSlayer slayer;
    private boolean mustNotify = false;

    public IBXM(final Module module, final int samplingRate) {
        this.module = module;
        setSampleRate(samplingRate);
        interpolation = 1;
        rampBuf = new int[128];
        channels = new Channel[module.numChannels];
        globalVol = new GlobalVol();
        note = new Note();
        setSequencePos(0);
    }

    public void connect(final IBXModSlayer slayer) {
        this.slayer = slayer;
        slayer.patternOffsets = new int[module.sequenceLength];
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(final int rate) {
        if (rate >= 8000 && rate <= 128000) {
            sampleRate = rate;
        } else
            throw new IllegalArgumentException("Unsupported sampling rate!");
    }

    public void setInterpolation(final int interpolation) {
        this.interpolation = interpolation;
    }

    public int getMixBufferLength() {
        return (calculateTickLen(32, 128000) + 65) * 4;
    }

    public int getRow() {
        return row;
    }

    public int getSequencePos() {
        return seqPos;
    }

    public void setSequencePos(int pos) {
        if (pos >= module.sequenceLength) {
            pos = 0;
        }

        breakSeqPos = pos;
        nextRow = 0;
        tick = 1;
        globalVol.volume = module.defaultGVol;
        speed = module.defaultSpeed > 0 ? module.defaultSpeed : 6;
        tempo = module.defaultTempo > 0 ? module.defaultTempo : 125;
        plCount = plChannel = -1;

        int idx;
        for (idx = 0; idx < module.numChannels; ++idx) {
            channels[idx] = new Channel(module, idx, globalVol);
        }

        for (idx = 0; idx < 128; ++idx) {
            rampBuf[idx] = 0;
        }

        tick();
    }

    public int calculateSongDuration() {
        int duration = 0;
        setSequencePos(0);

        for (boolean songEnd = false; !songEnd; songEnd = tick()) {
            duration += calculateTickLen(tempo, sampleRate);
        }

        setSequencePos(0);
        return duration;
    }

    public int seek(final int samplePos) {
        setSequencePos(0);
        int currentPos = 0;

        for (int tickLen = calculateTickLen(tempo, sampleRate); samplePos - currentPos >= tickLen; tickLen = calculateTickLen(tempo, sampleRate)) {
            for (int idx = 0; idx < module.numChannels; ++idx) {
                channels[idx].updateSampleIdx(tickLen * 2, sampleRate * 2);
            }

            currentPos += tickLen;
            tick();
        }

        return currentPos;
    }

    public void seekSequencePos(int sequencePos, int sequenceRow) {
        setSequencePos(0);
        if (sequencePos < 0 || sequencePos >= module.sequenceLength) {
            sequencePos = 0;
        }

        if (sequenceRow >= module.patterns[module.sequence[sequencePos]].numRows) {
            sequenceRow = 0;
        }

        do {
            if (seqPos >= sequencePos && row >= sequenceRow)
                return;

            final int tickLen = calculateTickLen(tempo, sampleRate);

            for (int idx = 0; idx < module.numChannels; ++idx) {
                channels[idx].updateSampleIdx(tickLen * 2, sampleRate * 2);
            }
        } while (!tick());

        setSequencePos(sequencePos);
    }

    public int getAudio(final int[] outputBuf) {
        final int tickLen = calculateTickLen(tempo, sampleRate);
        int chanIdx = 0;

        for (final int chan = (tickLen + 65) * 4; chanIdx < chan; ++chanIdx) {
            outputBuf[chanIdx] = 0;
        }

        for (chanIdx = 0; chanIdx < module.numChannels; ++chanIdx) {
            final Channel var5 = channels[chanIdx];
            var5.resample(outputBuf, 0, (tickLen + 65) * 2, sampleRate * 2, interpolation);
            var5.updateSampleIdx(tickLen * 2, sampleRate * 2);
        }

        downsample(outputBuf, tickLen + 64);
        volumeRamp(outputBuf, tickLen);
        tick();
        return tickLen;
    }

    private int calculateTickLen(final int tempo, final int samplingRate) {
        return samplingRate * 5 / (tempo * 2);
    }

    private void volumeRamp(final int[] mixBuf, final int tickLen) {
        final int rampRate = 524288 / sampleRate;
        int idx = 0;

        for (int a1 = 0; a1 < 256; a1 += rampRate) {
            final int a2 = 256 - a1;
            mixBuf[idx] = mixBuf[idx] * a1 + rampBuf[idx] * a2 >> 8;
            mixBuf[idx + 1] = mixBuf[idx + 1] * a1 + rampBuf[idx + 1] * a2 >> 8;
            idx += 2;
        }

        System.arraycopy(mixBuf, tickLen * 2, rampBuf, 0, 128);
    }

    private void downsample(final int[] buf, final int count) {
        final int outLen = count * 2;
        int inIdx = 0;

        for (int outIdx = 0; outIdx < outLen; outIdx += 2) {
            buf[outIdx] = (buf[inIdx] >> 2) + (buf[inIdx + 2] >> 1) + (buf[inIdx + 4] >> 2);
            buf[outIdx + 1] = (buf[inIdx + 1] >> 2) + (buf[inIdx + 3] >> 1) + (buf[inIdx + 5] >> 2);
            inIdx += 4;
        }

    }

    private boolean tick() {
        boolean songEnd = false;
        if (--tick <= 0) {
            tick = speed;
            songEnd = row();
        } else {
            for (int idx = 0; idx < module.numChannels; ++idx) {
                channels[idx].tick();
            }
        }

        return songEnd;
    }

    private boolean row() {
        boolean songEnd = false;
        if (breakSeqPos >= 0) {
            if (breakSeqPos >= module.sequenceLength) {
                breakSeqPos = nextRow = 0;
                songEnd = true;
            }

            while (module.sequence[breakSeqPos] >= module.numPatterns) {
                ++breakSeqPos;
                if (breakSeqPos >= module.sequenceLength) {
                    breakSeqPos = nextRow = 0;
                }
            }

            if (breakSeqPos <= seqPos) {
                songEnd = true;
                if (slayer != null && slayer.patternOffsets[breakSeqPos] != 0) {
                    slayer.loopMark = true;
                    slayer.rollBack = breakSeqPos;
                    slayer.rollBackPos = slayer.patternOffsets[breakSeqPos];
                }
            }

            seqPos = breakSeqPos;

            for (int pattern = 0; pattern < module.numChannels; ++pattern) {
                channels[pattern].plRow = 0;
            }

            breakSeqPos = -1;
        }

        final Pattern var6 = module.patterns[module.sequence[seqPos]];
        row = nextRow;
        if (row >= var6.numRows) {
            row = 0;
        }

        if (row == 0) {
            mustNotify = true;
        }

        if (mustNotify) {
            if (slayer != null && breakSeqPos < module.sequenceLength) {
                slayer.offMark(seqPos);
            }

            mustNotify = false;
        }

        nextRow = row + 1;
        if (nextRow >= var6.numRows) {
            breakSeqPos = seqPos + 1;
            nextRow = 0;
        }

        final int noteIdx = row * module.numChannels;

        for (int chanIdx = 0; chanIdx < module.numChannels; ++chanIdx) {
            final Channel channel = channels[chanIdx];
            var6.getNote(noteIdx + chanIdx, note);
            if (note.effect == 14) {
                note.effect = 112 | note.param >> 4;
                note.param &= 15;
            }

            if (note.effect == 147) {
                note.effect = 240 | note.param >> 4;
                note.param &= 15;
            }

            if (note.effect == 0 && note.param > 0) {
                note.effect = 138;
            }

            channel.row(note);
            switch (note.effect) {
                case 11:
                case 130:
                    if (plCount < 0) {
                        breakSeqPos = note.param;
                        nextRow = 0;
                        mustNotify = true;
                    }
                    break;
                case 13:
                case 131:
                    if (plCount < 0) {
                        breakSeqPos = seqPos + 1;
                        nextRow = (note.param >> 4) * 10 + (note.param & 15);
                        mustNotify = true;
                    }
                    break;
                case 15:
                    if (note.param > 0) {
                        if (note.param < 32) {
                            tick = speed = note.param;
                        } else {
                            tempo = note.param;
                        }
                    }
                    break;
                case 118:
                case 251:
                    if (note.param == 0) {
                        channel.plRow = row;
                    }

                    if (channel.plRow < row) {
                        if (plCount < 0) {
                            plCount = note.param;
                            plChannel = chanIdx;
                        }

                        if (plChannel == chanIdx) {
                            if (plCount == 0) {
                                channel.plRow = row + 1;
                            } else {
                                nextRow = channel.plRow;
                                breakSeqPos = -1;
                            }

                            --plCount;
                        }
                    }
                    break;
                case 126:
                case 254:
                    tick = speed + speed * note.param;
                    break;
                case 129:
                    if (note.param > 0) {
                        tick = speed = note.param;
                    }
                    break;
                case 148:
                    if (note.param > 32) {
                        tempo = note.param;
                    }
            }
        }

        return songEnd;
    }
}
