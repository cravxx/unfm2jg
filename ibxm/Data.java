package ibxm;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Data {
    private int bufLen;
    private byte[] buffer;
    private InputStream stream;

    public Data(final InputStream inputStream) throws IOException {
        bufLen = 65536;
        buffer = new byte[bufLen];
        stream = inputStream;
        readFully(stream, buffer, 0, bufLen);
    }

    public Data(final byte[] data) {
        bufLen = data.length;
        buffer = data;
    }

    public byte sByte(final int offset) throws IOException {
        load(offset, 1);
        return buffer[offset];
    }

    public int uByte(final int offset) throws IOException {
        load(offset, 1);
        return buffer[offset] & 255;
    }

    public int ubeShort(final int offset) throws IOException {
        load(offset, 2);
        return (buffer[offset] & 255) << 8 | buffer[offset + 1] & 255;
    }

    public int uleShort(final int offset) throws IOException {
        load(offset, 2);
        return buffer[offset] & 255 | (buffer[offset + 1] & 255) << 8;
    }

    public int uleInt(final int offset) throws IOException {
        load(offset, 4);
        int value = buffer[offset] & 255;
        value |= (buffer[offset + 1] & 255) << 8;
        value |= (buffer[offset + 2] & 255) << 16;
        value |= (buffer[offset + 3] & 127) << 24;
        return value;
    }

    public String strLatin1(final int offset, final int length) throws IOException {
        load(offset, length);
        final char[] str = new char[length];

        for (int idx = 0; idx < length; ++idx) {
            final int chr = buffer[offset + idx] & 255;
            str[idx] = chr < 32 ? 32 : (char) chr;
        }

        return new String(str);
    }

    public String strCp850(final int offset, final int length) throws IOException {
        load(offset, length);

        try {
            final char[] e = new String(buffer, offset, length, "Cp850").toCharArray();

            for (int idx = 0; idx < e.length; ++idx) {
                e[idx] = e[idx] < 32 ? 32 : e[idx];
            }

            return new String(e);
        } catch (final UnsupportedEncodingException var5) {
            return strLatin1(offset, length);
        }
    }

    public short[] samS8(final int offset, final int length) throws IOException {
        load(offset, length);
        final short[] sampleData = new short[length];

        for (int idx = 0; idx < length; ++idx) {
            sampleData[idx] = (short) (buffer[offset + idx] << 8);
        }

        return sampleData;
    }

    public short[] samS8D(final int offset, final int length) throws IOException {
        load(offset, length);
        final short[] sampleData = new short[length];
        int sam = 0;

        for (int idx = 0; idx < length; ++idx) {
            sam += buffer[offset + idx];
            sampleData[idx] = (short) (sam << 8);
        }

        return sampleData;
    }

    public short[] samU8(final int offset, final int length) throws IOException {
        load(offset, length);
        final short[] sampleData = new short[length];

        for (int idx = 0; idx < length; ++idx) {
            sampleData[idx] = (short) ((buffer[offset + idx] & 255) - 128 << 8);
        }

        return sampleData;
    }

    public short[] samS16(final int offset, final int samples) throws IOException {
        load(offset, samples * 2);
        final short[] sampleData = new short[samples];

        for (int idx = 0; idx < samples; ++idx) {
            sampleData[idx] = (short) (buffer[offset + idx * 2] & 255 | buffer[offset + idx * 2 + 1] << 8);
        }

        return sampleData;
    }

    public short[] samS16D(final int offset, final int samples) throws IOException {
        load(offset, samples * 2);
        final short[] sampleData = new short[samples];
        int sam = 0;

        for (int idx = 0; idx < samples; ++idx) {
            sam += buffer[offset + idx * 2] & 255 | buffer[offset + idx * 2 + 1] << 8;
            sampleData[idx] = (short) sam;
        }

        return sampleData;
    }

    public short[] samU16(final int offset, final int samples) throws IOException {
        load(offset, samples * 2);
        final short[] sampleData = new short[samples];

        for (int idx = 0; idx < samples; ++idx) {
            final int sam = buffer[offset + idx * 2] & 255 | (buffer[offset + idx * 2 + 1] & 255) << 8;
            sampleData[idx] = (short) (sam - '耀');
        }

        return sampleData;
    }

    private void load(final int offset, final int length) throws IOException {
        while (offset + length > bufLen) {
            final int newBufLen = bufLen << 1;
            final byte[] newBuf = new byte[newBufLen];
            System.arraycopy(buffer, 0, newBuf, 0, bufLen);
            if (stream != null) {
                readFully(stream, newBuf, bufLen, newBufLen - bufLen);
            }

            bufLen = newBufLen;
            buffer = newBuf;
        }

    }

    private static void readFully(final InputStream inputStream, final byte[] buffer, int offset, final int length) throws IOException {
        int read = 1;

        for (final int end = offset + length; read > 0; offset += read) {
            read = inputStream.read(buffer, offset, end - offset);
        }

    }
}
