package ibxm;

public class Instrument {
    public String name = "";
    public int numSamples = 1;
    public int vibratoType = 0;
    public int vibratoSweep = 0;
    public int vibratoDepth = 0;
    public int vibratoRate = 0;
    public int volumeFadeOut = 0;
    public Envelope volumeEnvelope = new Envelope();
    public Envelope panningEnvelope = new Envelope();
    public int[] keyToSample = new int[97];
    public Sample[] samples = new Sample[] {
            new Sample()
    };

    public void toStringBuffer(final StringBuffer out) {
        out.append("Name: ").append(name).append('\n');
        if (numSamples > 0) {
            out.append("Num Samples: ").append(numSamples).append('\n');
            out.append("Vibrato Type: ").append(vibratoType).append('\n');
            out.append("Vibrato Sweep: ").append(vibratoSweep).append('\n');
            out.append("Vibrato Depth: ").append(vibratoDepth).append('\n');
            out.append("Vibrato Rate: ").append(vibratoRate).append('\n');
            out.append("Volume Fade Out: ").append(volumeFadeOut).append('\n');
            out.append("Volume Envelope:\n");
            volumeEnvelope.toStringBuffer(out);
            out.append("Panning Envelope:\n");
            panningEnvelope.toStringBuffer(out);

            int keyIdx;
            for (keyIdx = 0; keyIdx < numSamples; ++keyIdx) {
                out.append("Sample ").append(keyIdx).append(":\n");
                samples[keyIdx].toStringBuffer(out);
            }

            out.append("Key To Sample: ");

            for (keyIdx = 1; keyIdx < 97; ++keyIdx) {
                out.append(keyToSample[keyIdx]).append(", ");
            }

            out.append('\n');
        }

    }
}
