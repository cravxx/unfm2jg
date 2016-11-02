package ds.nfm.xm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ds.nfm.ModuleSlayer;
import ibxm.IBXM;
import ibxm.Module;

public class IBXModSlayer extends ModuleSlayer {
    private final Module module;
    private final IBXM ibxm;
    private int samplePos;
    private final int duration;
    private int gain = 300;
    public boolean offMark = false;
    public int offIndex = -1;
    public int[] patternOffsets;

    public IBXModSlayer(final IBXMod pmod, final int smpl, final int gn, final int bpmflex) {
        module = pmod.refMod;
        if (module.defaultTempo == 125) {
            module.defaultTempo = bpmflex;
        }

        ibxm = new IBXM(module, smpl / 2);
        ibxm.connect(this);
        ibxm.setInterpolation(1);
        gain = gn;
        duration = ibxm.calculateSongDuration();
    }

    public void offMark(final int index) {
        offIndex = index;
        offMark = true;
    }

    @Override
    public byte[] turnbytesNorm(final boolean calvol) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final int[] mixBuf = new int[ibxm.getMixBufferLength()];
        final byte[] outBuf = new byte[mixBuf.length * 2];
        oln = 0;
        int olniu = 1;
        final boolean niu = false;
        final boolean cav = false;
        final int maxsize = Math.min(24000000, duration * 2);

        while (oln < maxsize) {
            final int count = getAudio(mixBuf);
            if (offMark) {
                if (patternOffsets[offIndex] == 0) {
                    patternOffsets[offIndex] = oln;
                }

                offMark = false;
            }

            int var15 = 0;
            int var14 = 0;
            int outIdx = 0;
            int mixIdx = 0;

            for (final int mixEnd = count * 2; mixIdx < mixEnd; ++mixIdx) {
                if (mixIdx % 2 == 0) {
                    int ampl = (int) (mixBuf[mixIdx] * (gain / 300.0F));
                    if (ampl > 32767) {
                        ampl = 32767;
                    }

                    if (ampl < -32768) {
                        ampl = -32768;
                    }

                    outBuf[outIdx++] = (byte) (ampl & 255);
                    outBuf[outIdx++] = (byte) (ampl >> 8);
                    oln += 2;
                    if (calvol) {
                        var15 += Math.abs(ampl);
                        ++var14;
                    }
                }
            }

            if (calvol && var14 != 0) {
                var15 /= var14;
                olav += var15;
                ++olniu;
            }

            out.write(outBuf, 0, outIdx);
            if (loopMark) {
                rollBackTrig = oln;
                loopMark = false;
            }
        }

        if (calvol) {
            olav /= olniu;
        }

        if (oln % 2 != 0) {
            ++oln;
        }

        return out.toByteArray();
    }

    private synchronized int getAudio(final int[] mixBuf) {
        final int count = ibxm.getAudio(mixBuf);
        samplePos += count;
        return count;
    }
}
