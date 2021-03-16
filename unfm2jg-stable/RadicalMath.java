/**
 * Contains many of the math functions for various purposes.
 *
 * @author Rafa, Kaffeinated, Omar Wally
 */
class RadicalMath {

    public static float sin_m_zy;
    public static float cos_m_zy;
    public static float sin_m_xz;
    public static float cos_m_xz;
    public static float sin_m_xy;
    public static float cos_m_xy;

    static private final float[] tcos = new float[360];
    static private final float[] tsin = new float[360];

    static {
        for (int i = 0; i < 360; i++) {
            tcos[i] = (float) Math.cos(i * 0.01745329251994329576922);
        }
        //3.14159265358979323846 / 180 = 0.01745329251994329576922
        for (int i = 0; i < 360; i++) {
            tsin[i] = (float) Math.sin(i * 0.01745329251994329576922);
        }
    }

    static public float cos(int i) {
        for (/**/; i >= 360; i -= 360) {

        }
        for (/**/; i < 0; i += 360) {

        }
        return tcos[i];
    }

    static public float sin(int i) {
        for (/**/; i >= 360; i -= 360) {

        }
        for (/**/; i < 0; i += 360) {

        }
        return tsin[i];
    }
}
