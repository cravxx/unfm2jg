import java.awt.Color;
import java.awt.Graphics2D;

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
			conto.p[i1].blastBody = false;
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
	 * rapid body
	 * @param conto instnace
	 * @return status
	 */
	public boolean rapidBlast(Graphics2D rd, ContO conto) {	
		do{						
			if(flashes < 3){
				
				flashes++;
				/**
				 * hold off 
				 */
				if(flashes > 2){
					//System.out.println(flashes);
					createChips(rd, conto, 30.0F, 1);
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
	
	public static void createChips(Graphics2D rd, ContO conto, float sepCtMag, int sepChip) {	
		int cxz = conto.x;
		int cxy = conto.y;
		int czy = conto.z;
		int[] cox = new int[3];
		int[] coz = new int[3];
		int[] coy = new int[3];
		int dx = 0;
		int dy = 0;
		int dz = 0;
		int vx = 0;
		int vy = 0;
		int vz = 0;
		
		if (sepChip != 0) {
			if (sepChip == 1) {
				
				int i3 = (int) (Medium.random());
				
				cox[0] = conto.x;
				
				coz[0] = conto.y;
				
				coy[0] = conto.z;
				if (sepCtMag > 30F) {
					sepCtMag = 30F;
				}
				if (sepCtMag < -3F) {
					sepCtMag = -3F;
				}
				cox[1] = (int) (cox[0] + sepCtMag * (10F - Medium.random() * 20F));
				cox[2] = (int) (cox[0] + sepCtMag * (10F - Medium.random() * 20F));
				coy[1] = (int) (coy[0] + sepCtMag * (10F - Medium.random() * 20F));
				coy[2] = (int) (coy[0] + sepCtMag * (10F - Medium.random() * 20F));
				coz[1] = (int) (coz[0] + sepCtMag * (10F - Medium.random() * 20F));
				coz[2] = (int) (coz[0] + sepCtMag * (10F - Medium.random() * 20F));
				
				vx = (int) (sepCtMag * (30F - Medium.random() * 60F));
				vz = (int) (sepCtMag * (30F - Medium.random() * 60F));
				vy = (int) (sepCtMag * (30F - Medium.random() * 60F));
				sepChip = 2;
			}
			int ai4[] = new int[3];
			int ai6[] = new int[3];
			int ai8[] = new int[3];
			int k4 = 0;
			do {
				ai4[k4] = cox[k4] + conto.x;
				ai8[k4] = coy[k4] + conto.y;
				ai6[k4] = coz[k4] + conto.z;
			} while (++k4 < 3);
			Utility.rot(ai4, ai8, conto.x, conto.y, cxy, 3);
			Utility.rot(ai8, ai6, conto.y, conto.z, czy, 3);
			Utility.rot(ai4, ai6, conto.x, conto.z, cxz, 3);
			k4 = 0;
			do {
				ai4[k4] += dx;
				ai8[k4] += dy;
				ai6[k4] += dz;
			} while (++k4 < 3);
			dx += vx;
			dz += vz;
			dy += vy;
			vy += 7;
			/*if (ai8[0] > m.ground) {
				chip = 19;
			}*/
			Utility.rot(ai4, ai6, Medium.cx, Medium.cz, Medium.xz, 3);
			Utility.rot(ai8, ai6, Medium.cy, Medium.cz, Medium.zy, 3);
			int ai10[] = new int[3];
			int ai11[] = new int[3];
			int l5 = 0;
			do {
				ai10[l5] = Utility.xs(ai4[l5], ai6[l5]);
				ai11[l5] = Utility.ys(ai8[l5], ai6[l5], 0);				
			} while (++l5 < 3);
			l5 = (int) (Medium.random() * 3F);
			if (l5 == 0) {
				rd.setColor((new Color(conto.p[0].c[0], conto.p[0].c[1], conto.p[0].c[2])).darker());
			}
			if (l5 == 1) {
				rd.setColor(new Color(conto.p[0].c[0], conto.p[0].c[1], conto.p[0].c[2]));
			}
			if (l5 == 2) {
				rd.setColor((new Color(conto.p[0].c[0], conto.p[0].c[1], conto.p[0].c[2])).brighter());
			}
			rd.fillPolygon(ai10, ai11, 3);
			sepChip++;
			/**
			 * this is the limit for how many chips, or, polygons, are created at once
			 */
			if (sepChip == 400) {
				sepChip = 0;
			}
		}
	}
	
}
