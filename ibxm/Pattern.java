package ibxm;

public class Pattern {
    public int numRows;
    public byte[] data;

    public Pattern(final int numChannels, final int numRows) {
        this.numRows = numRows;
        data = new byte[numChannels * numRows * 5];
    }

    public void getNote(final int index, final Note note) {
        final int offset = index * 5;
        note.key = data[offset] & 255;
        note.instrument = data[offset + 1] & 255;
        note.volume = data[offset + 2] & 255;
        note.effect = data[offset + 3] & 255;
        note.param = data[offset + 4] & 255;
    }

    public void toStringBuffer(final StringBuffer out) {
        final char[] hex = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        final int channels = data.length / (numRows * 5);
        int data_offset = 0;

        for (int row = 0; row < numRows; ++row) {
            for (int channel = 0; channel < channels; ++channel) {
                for (int n = 0; n < 5; ++n) {
                    final byte b = data[data_offset++];
                    if (b == 0) {
                        out.append("--");
                    } else {
                        out.append(hex[b >> 4 & 15]);
                        out.append(hex[b & 15]);
                    }
                }

                out.append(' ');
            }

            out.append('\n');
        }

    }
}
