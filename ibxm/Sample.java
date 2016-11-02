package ibxm;

public class Sample {
    public static final int FP_SHIFT = 15;
    public static final int FP_ONE = 32768;
    public static final int FP_MASK = 32767;
    public static final int C2_PAL = 8287;
    public static final int C2_NTSC = 8363;
    public String name = "";
    public int volume = 0;
    public int panning = -1;
    public int relNote = 0;
    public int fineTune = 0;
    public int c2Rate = 8363;
    private int loopStart = 0;
    private int loopLength = 0;
    private short[] sampleData;
    private static final short[][] SINC_TABLES = calculateSincTables();

    private static short[][] calculateSincTables() {
        final short[][] sincTables = new short[8][];

        for (int tableIdx = 0; tableIdx < 8; ++tableIdx) {
            sincTables[tableIdx] = calculateSincTable(1.0D / (tableIdx + 1));
        }

        return sincTables;
    }

    private static short[] calculateSincTable(final double lowpass) {
        final short[] sincTable = new short[272];
        final double windDT = -0.39269908169872414D;
        final double sincDT = -3.141592653589793D;
        int tableIdx = 0;

        for (int tableY = 0; tableY <= 16; ++tableY) {
            final double fracT = tableY / 16.0D;
            double sincT = 3.141592653589793D * (7.0D + fracT);
            double windT = 3.141592653589793D + sincT * 2.0D / 16.0D;

            for (int tableX = 0; tableX < 16; ++tableX) {
                double sincY = lowpass;
                if (sincT != 0.0D) {
                    sincY = Math.sin(lowpass * sincT) / sincT;
                }

                double windY = 0.35875D;
                windY -= 0.48829D * Math.cos(windT);
                windY += 0.14128D * Math.cos(windT * 2.0D);
                windY -= 0.01168D * Math.cos(windT * 3.0D);
                sincTable[tableIdx++] = (short) (int) Math.round(sincY * windY * 32767.0D);
                sincT += sincDT;
                windT += windDT;
            }
        }

        return sincTable;
    }

    public void setSampleData(short[] sampleData, int loopStart, int loopLength, final boolean pingPong) {
        int sampleLength = sampleData.length;
        if (loopStart < 0 || loopStart > sampleLength) {
            loopStart = sampleLength;
        }

        if (loopLength < 0 || loopStart + loopLength > sampleLength) {
            loopLength = sampleLength - loopStart;
        }

        sampleLength = loopStart + loopLength;
        loopStart += 8;
        final int newSampleLength = 8 + sampleLength + (pingPong ? loopLength : 0) + 16;
        final short[] newSampleData = new short[newSampleLength];
        System.arraycopy(sampleData, 0, newSampleData, 8, sampleLength);
        sampleData = newSampleData;
        int idx;
        int end;
        if (pingPong) {
            idx = loopStart + loopLength;

            for (end = 0; end < loopLength; ++end) {
                sampleData[idx + end] = sampleData[idx - end - 1];
            }

            loopLength *= 2;
        }

        idx = loopStart + loopLength;

        for (end = idx + 16; idx < end; ++idx) {
            sampleData[idx] = sampleData[idx - loopLength];
        }

        this.sampleData = sampleData;
        this.loopStart = loopStart;
        this.loopLength = loopLength;
    }

    public void resampleNearest(int sampleIdx, int sampleFrac, final int step, final int leftGain, final int rightGain, final int[] mixBuffer, final int offset, final int length) {
        final int loopLen = loopLength;
        final int loopEnd = loopStart + loopLen;
        sampleIdx += 8;
        if (sampleIdx >= loopEnd) {
            sampleIdx = normaliseSampleIdx(sampleIdx);
        }

        final short[] data = sampleData;
        int outIdx = offset << 1;

        for (final int outEnd = offset + length << 1; outIdx < outEnd; sampleFrac &= 32767) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) {
                    break;
                }

                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }

            final short y = data[sampleIdx];
            int var10001 = outIdx++;
            mixBuffer[var10001] += y * leftGain >> 15;
            var10001 = outIdx++;
            mixBuffer[var10001] += y * rightGain >> 15;
            sampleFrac += step;
            sampleIdx += sampleFrac >> 15;
        }

    }

    public void resampleLinear(int sampleIdx, int sampleFrac, final int step, final int leftGain, final int rightGain, final int[] mixBuffer, final int offset, final int length) {
        final int loopLen = loopLength;
        final int loopEnd = loopStart + loopLen;
        sampleIdx += 8;
        if (sampleIdx >= loopEnd) {
            sampleIdx = normaliseSampleIdx(sampleIdx);
        }

        final short[] data = sampleData;
        int outIdx = offset << 1;

        for (final int outEnd = offset + length << 1; outIdx < outEnd; sampleFrac &= 32767) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) {
                    break;
                }

                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }

            final short c = data[sampleIdx];
            final int m = data[sampleIdx + 1] - c;
            final int y = (m * sampleFrac >> 15) + c;
            int var10001 = outIdx++;
            mixBuffer[var10001] += y * leftGain >> 15;
            var10001 = outIdx++;
            mixBuffer[var10001] += y * rightGain >> 15;
            sampleFrac += step;
            sampleIdx += sampleFrac >> 15;
        }

    }

    public void resampleSinc(int sampleIdx, int sampleFrac, final int step, final int leftGain, final int rightGain, final int[] mixBuffer, final int offset, final int length) {
        int tableIdx = 0;
        if (step > '耀') {
            tableIdx = (step >> 15) - 1;
            if (tableIdx >= 8) {
                tableIdx = 7;
            }
        }

        final short[] sincTable = SINC_TABLES[tableIdx];
        final int loopLen = loopLength;
        final int loopEnd = loopStart + loopLen;
        if (sampleIdx >= loopEnd) {
            sampleIdx = normaliseSampleIdx(sampleIdx);
        }

        final short[] data = sampleData;
        int outIdx = offset << 1;

        for (final int outEnd = offset + length << 1; outIdx < outEnd; sampleFrac &= 32767) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) {
                    break;
                }

                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }

            final int tableIdx1 = sampleFrac >> 11 << 4;
            final int tableIdx2 = tableIdx1 + 16;
            int a1 = 0;
            int a2 = 0;

            int y;
            for (y = 0; y < 16; ++y) {
                a1 += sincTable[tableIdx1 + y] * data[sampleIdx + y];
                a2 += sincTable[tableIdx2 + y] * data[sampleIdx + y];
            }

            a1 >>= 15;
            a2 >>= 15;
            y = a1 + ((a2 - a1) * (sampleFrac & 2047) >> 11);
            int var10001 = outIdx++;
            mixBuffer[var10001] += y * leftGain >> 15;
            var10001 = outIdx++;
            mixBuffer[var10001] += y * rightGain >> 15;
            sampleFrac += step;
            sampleIdx += sampleFrac >> 15;
        }

    }

    public int normaliseSampleIdx(int sampleIdx) {
        final int loopOffset = sampleIdx - loopStart;
        if (loopOffset > 0) {
            sampleIdx = loopStart;
            if (loopLength > 1) {
                sampleIdx += loopOffset % loopLength;
            }
        }

        return sampleIdx;
    }

    public boolean looped() {
        return loopLength > 1;
    }

    public void toStringBuffer(final StringBuffer out) {
        out.append("Name: ").append(name).append('\n');
        out.append("Volume: ").append(volume).append('\n');
        out.append("Panning: ").append(panning).append('\n');
        out.append("Relative Note: ").append(relNote).append('\n');
        out.append("Fine Tune: ").append(fineTune).append('\n');
        out.append("Loop Start: ").append(loopStart).append('\n');
        out.append("Loop Length: ").append(loopLength).append('\n');
    }
}
