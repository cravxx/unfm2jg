import java.awt.Graphics2D;

/**
 * controls lights on the car
 * @author Kaffeinated
 *
 */
public class Lights {		
	/*
	 * bool that triggers brake light 
	 */
	boolean brake;
	
	/*
	 * bool that triggers reverse light
	 */
	boolean rev;

	public Lights() {
		brake = false;		
		rev = false;
	}
		
	/**
	 * we got a Conto from gamesparker, let's act on it
	 * @param aconto1 the car
	 * @param bBool braking?
	 * @param rBool reversing?
	 * @param rd graphics
	 */
	public void changeLights(ContO aconto1, Graphics2D rd){	
		if (brake) {
			/*
			 * we're braking
			 */
			System.out.println("braking");
			/*
			 * check to see if we've changed the light color already
			 */
			if (!aconto1.brakingStatus()) {
				/*
				 * if not, change it
				 */
				aconto1.brakingLight(rd);
			}
		} else if (rev) {
			/*
			 * we're reversing
			 */
			System.out.println("reversing");
			/*
			 * check to see if we've changed the light color already
			 */
			if (!aconto1.reversingStatus()) {				
				/*
				 * if not, change it
				 */
				aconto1.reversingLight(rd);
			}
		} else {
			/*
			 * bools are false, revert
			 */
			aconto1.revertLights(rd);
		}
	}		
}

	
	
	
	
	
	
	
	
	
	



