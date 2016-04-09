
public class PolyFX {		
	
	public float pulseVar = 1.0F;
	public boolean halfBaked = false;
	
	/**
	 * decreases a float to edit color
	 * 
	 * @param conto
	 *            conto instance
	 * @param pulseVar
	 *            original pulse
	 * @return returns false when done
	 */
	public boolean setPulse(ContO conto) {		
		while (pulseVar > 0.0F && halfBaked == false) {
			System.out.println("greater than 0.0");
			pulseVar -= 0.01F;
			for (int i1 = 0; i1 < conto.npl; i1++) {
				conto.p[i1].pulse = pulseVar;
			}			
			return true;
		}
		halfBaked = true;

		System.out.println("finishing up");

		pulseVar = 1.0F;
		for (int i1 = 0; i1 < conto.npl; i1++) {
			conto.p[i1].pulse = pulseVar;
		}
		
		return false;
	}

}
