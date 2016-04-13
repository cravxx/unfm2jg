
public class PolyFX {	
	public int flashes = 0;
	public int timesToRun = 2;
	
	public void reset() {
		flashes = 0;
		timesToRun = 2;
	}
	
	public void reset(ContO conto) {
		flashes = 0;
		timesToRun = 2;
		for (int i1 = 0; i1 < conto.npl; i1++) {
			conto.p[i1].bodyFlash = false;
		}
	}
	
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
					//System.out.println(flashes);
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
					//System.out.println(flashes);
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
		
		reset(conto);
		
		return false;
	}
	
	/**
	 * wew 
	 * @param conto
	 * @return
	 */
	public boolean flashBody(ContO conto) {	
		for (int i1 = 0; i1 < conto.npl; i1++) {
			conto.p[i1].bodyFlash = true;				
		}		
		return false;
	}
	
}
