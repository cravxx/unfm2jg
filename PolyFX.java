
public class PolyFX {		
	
	/*public float pulseVar = 1.0F;*/
	
	public int flashes = 0;

	public int timesToRun = 2;
	/*public boolean halfBaked = false;*/
	
	public void reset() {
		/*pulseVar = 1.0F;*/
		flashes = 0;
		timesToRun = 2;
		/*halfBaked = false;*/
	}
	
	/**
	 * decreases a float to edit color
	 * 
	 * @param conto
	 *            conto instance
	 * @param pulseVar
	 *            original pulse
	 * @return returns false when done
	 *//*
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
	

	*//**
	 * flash it
	 * @param conto instnace
	 * @return status
	 *//*
	public boolean flashIt(ContO conto) {	
		float rate = 0.50F;
		do{
			
			if(pulseVar > 0.0F && halfBaked == false){
				System.out.println("bright");
				pulseVar -= rate;
				for (int i1 = 0; i1 < conto.npl; i1++) {
					conto.p[i1].wireframeFlash = true;
				}						
				return true;
			}else{
				halfBaked = true;
			}
			
			if(pulseVar < 1.0F && halfBaked == true){
				System.out.println("finishing up");
				pulseVar += rate;
				for (int i1 = 0; i1 < conto.npl; i1++) {
					conto.p[i1].wireframeFlash = false;
				}
				return true;
			}else{
				halfBaked = false;
				timesToRun--;
			}			
			
		}while(timesToRun > 0);
		
		reset();
		
		return false;
	}*/
	
	/**
	 * rapid wireframe
	 * @param conto instnace
	 * @return status
	 */
	public boolean rapidWireframe(ContO conto) {	
		do{						
			if(flashes < 6){
				
				flashes++;
				/**
				 * hold off 
				 */
				if(flashes > 2){
					System.out.println(flashes);
					for (int i1 = 0; i1 < conto.npl; i1++) {
						/**
						 * don't want wheels to flash
						 */
						if(!conto.p[i1].wheel)
							conto.p[i1].wireframeFlash = !conto.p[i1].wireframeFlash;
					}
				}
				return true;
			}else{
				/**
				 * just set it to zero
				 */
				timesToRun = 0;
				flashes = 0;				
			}			
		}while(timesToRun > 0);
		
		reset();
		
		return false;
	}
	
	/**
	 * rapid body
	 * @param conto instnace
	 * @return status
	 */
	public boolean rapidBody(ContO conto) {	
		do{						
			if(flashes < 6){
				
				flashes++;
				/**
				 * hold off 
				 */
				if(flashes > 2){
					System.out.println(flashes);
					for (int i1 = 0; i1 < conto.npl; i1++) {
						/**
						 * don't want wheels to flash
						 */
						if(!conto.p[i1].wheel)
							conto.p[i1].bodyFlash = !conto.p[i1].bodyFlash;
					}
				}
				return true;
			}else{
				/**
				 * just set it to zero
				 */
				timesToRun = 0;
				flashes = 0;				
			}			
		}while(timesToRun > 0);
		
		reset();
		
		return false;
	}
	
	

}
