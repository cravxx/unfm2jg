import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Contains often used functions, and some new ones
 * @author Rafa
 * @author Kaffeinated
 * @author Omar Waly
 *
 */
public class Utility {
	/**
	 * GameSparker.nob
	 */
	public static int numberOfModels = 0;
	
	private final static Medium medium = new Medium();
	
	private static long startTime;
	    
    /**
     * Gets a value from a string in format:
     * string(value1,value2,value3...)
     *
     *
     * @param string The variable name (e.g: foo(bar) = foo)
     * @param source The string (single line) to get the value from
     * @param i The position of the value (starting from 0)
     * @return An integer containing the value
     */
    public static int getvalue(final String string, final String source, final int i) {
        int var = 0;
        String part = "";
        for (int k = string.length() + 1; k < source.length(); k++) {
            final String strChar = new StringBuilder().append("").append(source.charAt(k)).toString();
            if ((",").equals(strChar) || (")").equals(strChar)) {
                var++;
                k++;
            }
            if (var == i) {
                part = new StringBuilder().append(part).append(source.charAt(k)).toString();
            }
        }
        return Float.valueOf(part).intValue();
    }

    /**
     * Turns a 3D XY coordinate into a 2D X perspective coordinate.
     *
     * @param i
     *            The 3D X point
     * @param j
     *            The 3D Y point
     * @return The 2D X coordinate.
     */
    public static int xs(final int i, int j) {
        if (j < Medium.cz) {
            j = Medium.cz;
        }
        return (j - medium.focus_point) * (Medium.cx - i) / j + i;
    }
    
    public static int cXs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - medium.focus_point) * (Medium.cx - i) / j + i;
    }

    /**
     * Turns a 3D ZY coordinate into a 2D Y perspective coordinate.
     *
     * @param i
     *            The 3D Z point
     * @param j
     *            The 3D Y point
     * @param m
     * 			  There are two different ys() methods within NFM2, this integer is used to unify them while providing a way to distinguish between them. 
     * 			  0 is for using Medium.cz (normal)
     * 			  1 is for using 10 (only found in Medium)
     * @return The 2D Y coordinate.
     */
    public static int ys(final int i, int j, final int m) {
    	int value = 0;
    	switch(m){
    	case 0:
    		value = Medium.cz;
    		break;
    	case 1:
    		value = 10;
    		break;
    	default:
    		break;
    	}    	
        if (j < value) {
            j = value;
        }
        return (j - medium.focus_point) * (Medium.cy - i) / j + i;
    }
    
    public static int cYs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - medium.focus_point) * (Medium.cy - i) / j + i;
    }

    /**
     * Gets an integer from a string in format:
     * string(int1,int2,int3...)
     *
     *
     * @param string The beginning of the string (e.g: foo(0,0,1) = foo)
     * @param source The string (single line) to get the value from
     * @param i The position of the value (starting from 0)
     * @return An integer containing the value
     */
    public static int getint(final String string, final String source, final int i) {
        int var = 0;
        String part = "";
        for (int k = string.length() + 1; k < source.length(); k++) {
            final String strChar = new StringBuilder().append("").append(source.charAt(k)).toString();
            if ((",").equals(strChar) || (")").equals(strChar)) {
                var++;
                k++;
            }
            if (var == i) {
                part = new StringBuilder().append(part).append(source.charAt(k)).toString();
            }
        }
        return Integer.valueOf(part).intValue();
    }

    /**
     * Gets a substring, nicely
     *
     *
     * @param string The beginning of the string (e.g: foo(0,0,1) = foo)
     * @param source The string (single line) to get the value from
     * @param i The position of the string (starting from 0)
     * @return A string
     */
    public static String getstring(final String string, final String source, final int i) {
        int var = 0;
        String part = "";
        for (int k = string.length() + 1; k < source.length(); k++) {
            final String strChar = new StringBuilder().append("").append(source.charAt(k)).toString();
            if ((",").equals(strChar) || (")").equals(strChar)) {
                var++;
                k++;
            }
            if (var == i) {
                part = new StringBuilder().append(part).append(source.charAt(k)).toString();
            }
        }
        return part;
    }
    
    public static BufferedImage webGet(final String string) throws IOException{
    	System.out.println("Loaded " + string);    	
    	return ImageIO.read(new URL(string));
    }
        
    public static int spy(final int i, final int j) {
		return (int) Math.sqrt((i - Medium.cx) * (i - Medium.cx) + j * j);
	}
    
    public static float pys(final int i, final int j, final int k, final int l) {
		return (float) Math.sqrt((i - j) * (i - j) + (k - l) * (k - l));
	}
    
    public static int rpy(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
		return (int) ((f - f1) * (f - f1) + (f2 - f3) * (f2 - f3) + (f4 - f5) * (f4 - f5));
	}
        
    public static int py(final int i, final int j, final int k, final int l) {
		return (i - j) * (i - j) + (k - l) * (k - l);
	}

    public static int distance(final int x1, final int y1, final int x2, final int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    public static void rot(float af[], float af1[], final int i, final int j, final int k, final int l) {
		if (k != 0) {
			for (int i1 = 0; i1 < l; i1++) {
				float f = af[i1];
				float f1 = af1[i1];
				af[i1] = i + ((f - i) * RadicalMath.cos(k) - (f1 - j) * RadicalMath.sin(k));
				af1[i1] = j + ((f - i) * RadicalMath.sin(k) + (f1 - j) * RadicalMath.cos(k));
			}

		}
	}
    
    public static void rot(int ai[], int ai1[], final int i, final int j, final int k, final int l) {
		if (k != 0) {
			for (int i1 = 0; i1 < l; i1++) {
				int j1 = ai[i1];
				int k1 = ai1[i1];
				ai[i1] = i + (int) ((j1 - i) * RadicalMath.cos(k) - (k1 - j) * RadicalMath.sin(k));
				ai1[i1] = j + (int) ((j1 - i) * RadicalMath.sin(k) + (k1 - j) * RadicalMath.cos(k));
			}

		}
	}       
    
    public static int[] rotSingle(int poly1, int poly2, final int center1, final int center2, final int angle, final float sinAng, final float cosAng) {
        if (angle != 0) {
            final int j1 = poly1;
            final int k1 = poly2;
            poly1 = center1 + (int) ((j1 - center1) * cosAng - (k1 - center2) * sinAng);
            poly2 = center2 + (int) ((j1 - center1) * sinAng + (k1 - center2) * cosAng);
        }
        return new int[] {
                poly1, poly2
        };
    }
    
    public static Color evenBrighter(Color color) {
    	return color.brighter().brighter();
    }
    
    public static int distanceX(final int x1, final int x2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2));
    }
    
    public static int distanceZ(final int z1, final int z2) {
        return (int) Math.sqrt(Math.pow(z2 - z1, 2));
    }
    //public static int timesExe = 0;
    //public static int tempTimesExe = 0;
    /**
     * get the piece your car is on
     * @param conto Your car
     * @param conto1 Conto array to compare against
     * @return closest Conto <br> -1 if not on any conto
     */
	public static int nearestPiece(ContO conto, ContO conto1[]) {
		try {
			int numberOfTracksAdded = 0;
			/**
			 * skip 7 to pass cars 
			 */
			for (int k = 7; k < numberOfModels; k++) {
				//tempTimesExe++;
				numberOfTracksAdded += conto1[k].numberOfTracks;
				for (int j = 0; j < numberOfTracksAdded; j++) {
					//tempTimesExe++;					
					/*System.out.println(k + "  " + j);
					System.out.println(conto1.length + "  " + conto1[k].t.nt);*/
					if (Math.abs(conto1[k].t.zy[j]) != 90 && Math.abs(conto1[k].t.xy[j]) != 90
							&& Utility.distanceX(conto.x, conto1[k].x) < conto1[k].t.radx[j]
							&& Utility.distanceZ(conto.z, conto1[k].z) < conto1[k].t.radz[j]) {	
						numberOfTracksAdded = 0;
						//System.out.println("TRACKS   " + numberOfModels);
						//System.out.println("TIMES EXECUTED    " + Math.abs(timesExe - tempTimesExe));
						//timesExe = tempTimesExe;
						/*System.out.println("DISTANCES   " + Utility.distanceZ(conto1[k].z, conto.z) + "   " + Utility.distanceX(conto1[k].x, conto.x));
						System.out.println("RADS        " + conto1[k].t.radz[j] + "   " + conto1[k].t.radx[j]);*/
						return k;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return -1;
	}
	
    public static void startTimer(){
    	startTime = System.nanoTime();
    	System.out.println("Timer started!");    	
    }
    
    public static void stopTimer(){
    	long endTime = System.nanoTime();
    	long finalTime = (endTime - startTime) / 1000000;
    	startTime = 0;    	
    	System.out.println("Timer ended at " + finalTime + " ms");    	
    }
}
