
public class Trackers {
	
	int x[];
	int y[];
	int z[];
	int xy[];
	int zy[];
	int skd[];
	int dam[];
	boolean notwall[];
	int c[][];
	int radx[];
	int radz[];
	int rady[];
	int nt;

	public Trackers() {
		x = new int[870];
		y = new int[870];
		z = new int[870];
		xy = new int[870];
		zy = new int[870];
		skd = new int[870];
		dam = new int[870];
		notwall = new boolean[870];
		c = new int[870][3];
		radx = new int[870];
		radz = new int[870];
		rady = new int[870];
		nt = 0;
	}
}
