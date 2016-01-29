/**
 * Contains many of the math functions for various purposes.
 * @author Rafa, Kaffeinated, Omar Wally
 *
 */
public class RadicalMath {

    static float sin_m_zy;
    static float cos_m_zy;
    static float sin_m_xz;
    static float cos_m_xz;
    static float sin_m_xy;
    static float cos_m_xy;

    static private float[] tcos = new float[360];
    static private float[] tsin = new float[360];
    
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
