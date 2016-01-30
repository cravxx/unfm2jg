import java.awt.image.BufferedImage;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 * Contains often used functions
 * @author Rafa, Kaffeinated, Omar Wally
 *
 */
public class Utility {
	static Medium medium;
	
	static Timer printTimer = new Timer();
	static Thread timedPrinter = null;
	
	static Date date = new Date();
	static long startTime;
	
	public Utility() {
		medium = new Medium();
	}	
    
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
            final String string_266_ = new StringBuilder().append("").append(source.charAt(k)).toString();
            if (string_266_.equals(",") || string_266_.equals(")")) {
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
        if (j < medium.cz) {
            j = medium.cz;
        }
        return (j - medium.focus_point) * (medium.cx - i) / j + i;
    }
    
    public static int cXs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - medium.focus_point) * (medium.cx - i) / j + i;
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
    public static int ys(final int i, int j, int m) {
    	int value = 0;
    	switch(m){
    	case 0:
    		value = medium.cz;
    	case 1:
    		value = 10;
    	}    	
        if (j < value) {
            j = value;
        }
        return (j - medium.focus_point) * (medium.cy - i) / j + i;
    }
    
    public static int cYs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - medium.focus_point) * (medium.cy - i) / j + i;
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
            final String string_185_ = new StringBuilder().append("").append(source.charAt(k)).toString();
            if (string_185_.equals(",") || string_185_.equals(")")) {
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
            final String string_190_ = new StringBuilder().append("").append(source.charAt(k)).toString();
            if (string_190_.equals(",") || string_190_.equals(")")) {
                var++;
                k++;
            }
            if (var == i) {
                part = new StringBuilder().append(part).append(source.charAt(k)).toString();
            }
        }
        return part;
    }
    
    public static BufferedImage webGet(String string) throws IOException{
    	System.out.println("Loaded " + string);    	
    	return ImageIO.read(new URL(string));
    }
        
    public static int spy(int i, int j) {
		return (int) Math.sqrt((i - medium.cx) * (i - medium.cx) + j * j);
	}
    
    public static float pys(int i, int j, int k, int l) {
		return (float) Math.sqrt((i - j) * (i - j) + (k - l) * (k - l));
	}
    
    public static int rpy(float f, float f1, float f2, float f3, float f4, float f5) {
		return (int) ((f - f1) * (f - f1) + (f2 - f3) * (f2 - f3) + (f4 - f5) * (f4 - f5));
	}
        
    public static int py(int i, int j, int k, int l) {
		return (i - j) * (i - j) + (k - l) * (k - l);
	}

    public static int distance(final int x1, final int y1, final int x2, final int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    public static void rot(float af[], float af1[], int i, int j, int k, int l) {
		if (k != 0) {
			for (int i1 = 0; i1 < l; i1++) {
				float f = af[i1];
				float f1 = af1[i1];
				af[i1] = i + ((f - i) * RadicalMath.cos(k) - (f1 - j) * RadicalMath.sin(k));
				af1[i1] = j + ((f - i) * RadicalMath.sin(k) + (f1 - j) * RadicalMath.cos(k));
			}

		}
	}
    
    public static void rot(int ai[], int ai1[], int i, int j, int k, int l) {
		if (k != 0) {
			for (int i1 = 0; i1 < l; i1++) {
				int j1 = ai[i1];
				int k1 = ai1[i1];
				ai[i1] = i + (int) ((j1 - i) * RadicalMath.cos(k) - (k1 - j) * RadicalMath.sin(k));
				ai1[i1] = j + (int) ((j1 - i) * RadicalMath.sin(k) + (k1 - j) * RadicalMath.cos(k));
			}

		}
	}    
    
    public static void timedPrint(final String output, final int seconds){    
    	//System.out.println("what");
    	if(timedPrinter == null){
    		timedPrinter = new Thread()
    	    {
    	        public void run() {

    	        	printTimer.schedule(new TimerTask(){
    	                @Override
    	                public void run() {
    	                	System.out.println(output);

    	                }
    	            }, 0, seconds * 1000);    	            
    	        }
    	    };
    	    timedPrinter.start();
    	}else{}
    }
    
    public static void startTimer(){
    	System.out.println("Timer started!");    	
    	startTime = System.nanoTime();	
    }
    
    public static void stopTimer(){
    	long endTime = System.nanoTime();
    	long finalTime = (endTime - startTime) / 1000000;
    	startTime = 0;
    	
    	System.out.println("Timer ended at " + finalTime + " ms");    	
    }
}
