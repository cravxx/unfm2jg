
public class PolyFX {		
	
	public float pulseVar = 1.0F;
	
	public int rapidItFlashes = 0;

	public int timesToRun = 2;
	public boolean halfBaked = false;
	
	public void reset() {
		pulseVar = 1.0F;
		rapidItFlashes = 0;
		timesToRun = 2;
		halfBaked = false;
	}
	
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
		
		reset();
		return false;
	}
	

	/**
	 * flash it
	 * @param conto instnace
	 * @return status
	 */
	public boolean flashIt(ContO conto) {	
		float rate = 0.50F;
		do{
			
			if(pulseVar > 0.0F && halfBaked == false){
				System.out.println("bright");
				pulseVar -= rate;
				for (int i1 = 0; i1 < conto.npl; i1++) {
					conto.p[i1].fullOn = true;
				}						
				return true;
			}else{
				halfBaked = true;
			}
			
			if(pulseVar < 1.0F && halfBaked == true){
				System.out.println("finishing up");
				pulseVar += rate;
				for (int i1 = 0; i1 < conto.npl; i1++) {
					conto.p[i1].fullOn = false;
				}
				return true;
			}else{
				halfBaked = false;
				timesToRun--;
			}			
			
		}while(timesToRun > 0);
		
		reset();
		
		return false;
	}
	
	/**
	 * rapid it
	 * @param conto instnace
	 * @return status
	 */
	public boolean rapidIt(ContO conto) {	
		do{						
			if(rapidItFlashes < 6){
				
				rapidItFlashes++;
				/**
				 * hold off 
				 */
				if(rapidItFlashes > 2){
					System.out.println(rapidItFlashes);
					for (int i1 = 0; i1 < conto.npl; i1++) {
						conto.p[i1].fullOn = !conto.p[i1].fullOn;
					}
				}
				return true;
			}else{
				/**
				 * just set it to zero
				 */
				timesToRun = 0;
				rapidItFlashes = 0;				
			}			
		}while(timesToRun > 0);
		
		reset();
		
		return false;
	}
	
	

}
