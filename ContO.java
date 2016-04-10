import java.awt.*;
import java.io.*;

/**
 * models
 * @author Omar Waly, Kaffeinated
 */
public class ContO {
	public Medium m;
	public Trackers t;
	public Plane p[];	
	public int npl = 0;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int xz = 0;
	public int xy = 0;
	public int zy = 0;
	public int wxz = 0;
	public int wzy = 0;
	public int dist = 0;
	public int maxR = 0;
	public int disp = 0;
	public int disline = 7;
	public boolean shadow = false;
	public boolean noline = false;
	public float grounded = 1.0F;
	public int grat = 0;
	public int keyx[] = new int[4];
	public int keyz[] = new int[4];
	public int sx[] = new int[4];
	public int sy[] = new int[4];
	public int sz[] = new int[4];
	public int stg[] = new int[4];
	public int dov[] = new int[4];
	public float smag[] = new float[4];
	public int scx[] = new int[4];
	public int scz[] = new int[4];
	public boolean fulls[] = new boolean[4];
	public boolean elec = false;
	public boolean roted = false;
	public int edl[] = new int[4];
	public int edr[] = new int[4];
	public int elc[] = new int[4];
	public boolean fix = false;
	public int fcnt = 0;
	public int checkpoint = 0;
	public int tnt = 0;	
	public int txy[];
	public int tzy[];
	public int tc[][];
	public int tradx[];
	public int tradz[];
	public int trady[];
	public int tx[];
	public int ty[];
	public int tz[];
	public int skd[];
	public int dam[];
	public boolean notwall[];	

	public void pdust(int i, Graphics2D rd, int j) {
		if (j * dov[i] > 0) {
			int k;
			if (fulls[i]) {
				k = stg[i] * stg[i];
			} else {
				k = stg[i] * stg[i] * stg[i] + 1;
			}
			int l = (m.cgrnd[0] * k + m.cfade[0] * 2 + m.csky[0]) / (3 + k);
			int i1 = (m.cgrnd[1] * k + m.cfade[0] * 2 + m.csky[1]) / (3 + k);
			int j1 = (m.cgrnd[2] * k + m.cfade[0] * 2 + m.csky[2]) / (3 + k);
			for (int k1 = 0; k1 < t.nt; k1++) {
				if (Math.abs(t.zy[k1]) != 90 && Math.abs(t.xy[k1]) != 90 && Math.abs(sx[i] - t.x[k1]) < t.radx[k1]
						&& Math.abs(sz[i] - t.z[k1]) < t.radz[k1]) {
					if (t.skd[k1] == 0) {
						k = stg[i] * stg[i] * stg[i] + 2;
					}
					l = (int) ((t.c[k1][0] * 0.87D * k + m.cfade[0] * 2
							+ m.csky[0]) / (3 + k));
					i1 = (int) ((t.c[k1][1] * 0.87D * k + m.cfade[0] * 2
							+ m.csky[1]) / (3 + k));
					j1 = (int) ((t.c[k1][2] * 0.87D * k + m.cfade[0] * 2
							+ m.csky[2]) / (3 + k));
				}
			}

			if (sy[i] > 250) {
				sy[i] = 250;
			}
			int l1 = m.cx
					+ (int) ((sx[i] - m.x - m.cx) * RadicalMath.cos(m.xz) - (sz[i] - m.z - m.cz) * RadicalMath.sin(m.xz));
			int i2 = m.cz
					+ (int) ((sx[i] - m.x - m.cx) * RadicalMath.sin(m.xz) + (sz[i] - m.z - m.cz) * RadicalMath.cos(m.xz));
			int j2 = m.cy + (int) ((sy[i] - m.y - m.cy) * RadicalMath.cos(m.zy) - (i2 - m.cz) * RadicalMath.sin(m.zy));
			i2 = m.cz + (int) ((sy[i] - m.y - m.cy) * RadicalMath.sin(m.zy) + (i2 - m.cz) * RadicalMath.cos(m.zy));
			int k2 = (int) Math.sqrt((m.cy - j2) * (m.cy - j2) + (m.cx - l1) * (m.cx - l1) + i2 * i2);
			int l2 = 0;
			do {
				if (k2 > m.fade[l2]) {
					l = (l * m.fogd + m.cfade[0]) / (m.fogd + 1);
					i1 = (i1 * m.fogd + m.cfade[1]) / (m.fogd + 1);
					j1 = (j1 * m.fogd + m.cfade[2]) / (m.fogd + 1);
				}
			} while (++l2 < 8);
			if (Math.abs(scx[i]) + Math.abs(scz[i]) > 150) {
				sy[i] -= 3F + 27F * smag[i];
			} else {
				sy[i] -= 23F + 7F * smag[i];
			}
			sx[i] += scx[i] / ((stg[i] + 1) * smag[i]);
			sz[i] += scz[i] / ((stg[i] + 1) * smag[i]);
			int ai[] = new int[8];
			int ai1[] = new int[8];
			int i3 = stg[i] - 3;
			ai[0] = Utility.cXs((int) (l1 - (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai1[0] = Utility.cYs(
					(int) (j2 - (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			if (ai1[0] < 45 && m.flex != 0) {
				m.flex = 0;
			}
			ai[1] = Utility.cXs((int) (l1 - (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai1[1] = Utility.cYs(
					(int) (j2 + (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai[2] = Utility.cXs(
					(int) (l1 - (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai1[2] = Utility.cYs((int) (j2 + (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai[3] = Utility.cXs(
					(int) (l1 + (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai1[3] = Utility.cYs((int) (j2 + (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai[4] = Utility.cXs((int) (l1 + (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai1[4] = Utility.cYs(
					(int) (j2 + (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai[5] = Utility.cXs((int) (l1 + (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai1[5] = Utility.cYs(
					(int) (j2 - (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai[6] = Utility.cXs(
					(int) (l1 + (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai1[6] = Utility.cYs((int) (j2 - (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			ai[7] = Utility.cXs(
					(int) (l1 - (7.5D + m.random() * 7.5D + i3 * 2.5D) * smag[i]),
					i2);
			ai1[7] = Utility.cYs((int) (j2 - (18F + m.random() * 18F + i3 * 6) * smag[i]), i2);
			boolean flag = true;
			int j3 = 0;
			int k3 = 0;
			int l3 = 0;
			int i4 = 0;
			int j4 = 0;
			do {
				if (ai1[j4] < 0 || i2 < 10) {
					j3++;
				}
				if (ai1[j4] > m.h || i2 < 10) {
					k3++;
				}
				if (ai[j4] < 0 || i2 < 10) {
					l3++;
				}
				if (ai[j4] > m.w || i2 < 10) {
					i4++;
				}
				if (ai1[j4] < 45 && m.flex != 0) {
					m.flex = 0;
				}
			} while (++j4 < 8);
			if (l3 == 4 || j3 == 4 || k3 == 4 || i4 == 4) {
				flag = false;
			}
			if (flag) {
				rd.setColor(new Color(l, i1, j1));
				rd.fillPolygon(ai, ai1, 8);
			}
			if (dov[i] == 1) {
				dov[i] = -1;
			}
			if (stg[i] == 4) {
				stg[i] = 0;
			} else {
				stg[i]++;
				if (stg[i] == 2 && fulls[i]) {
					dov[i] = 0;
				}
			}
		} else if (dov[i] == 0) {
			dov[i] = 1;
		}
	}

	public ContO(byte abyte0[], Medium medium, Trackers trackers) {				
		m = medium;
		t = trackers;
		p = new Plane[270];
		boolean flag = false;
		boolean flag1 = false;
		int i = 0;
		float f = 1.0F;
		float f1 = 1.0F;
		int ai[] = new int[100];
		int ai1[] = new int[100];
		int ai2[] = new int[100];
		int ai3[] = new int[3];
		boolean flag2 = false;
		boolean flag3 = false;
		Wheels wheels = new Wheels();
		int j = 0;
		int k = 1;
		int l = 0;
		int i1 = 0;
		byte byte0 = 0;

		float nfmm_scale[] = {
				1.0F, 1.0F, 1.0F
		};
		
		try (BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader((new ByteArrayInputStream(abyte0))))) {
			for (String line; (line = bufferedreader.readLine()) != null;) {
				line = line.trim();

				if (line.startsWith("<p>")) {
					flag = true;
					i = 0;
					k = 0;
					l = 0;
					byte0 = 0;
				}
				if (flag) {
					if (line.startsWith("gr")) {
						k = Utility.getint("gr", line, 0);
					}
					if (line.startsWith("fs")) {
						l = Utility.getint("fs", line, 0);
					}
					if (line.startsWith("c")) {
						flag2 = false;
						ai3[0] = Utility.getint("c", line, 0);
						ai3[1] = Utility.getint("c", line, 1);
						ai3[2] = Utility.getint("c", line, 2);
					}
					if (line.startsWith("glass")) {
						flag2 = true;
					}
					if (line.startsWith("lightF")) {
						byte0 = 1;
					}
					if (line.startsWith("lightB")) {
						byte0 = 2;
					}
					if (line.startsWith("light()")) {
						byte0 = 1;
					}
					if (line.startsWith("p")) {
						ai[i] = (int) ((Utility.getint("p", line, 0) * f * f1) * nfmm_scale[0]);
						ai1[i] = (int) ((Utility.getint("p", line, 1) * f) * nfmm_scale[1]);
						ai2[i] = (int) ((Utility.getint("p", line, 2) * f) * nfmm_scale[2]);
						int j1 = (int) Math.sqrt(ai[i] * ai[i] + ai1[i] * ai1[i] + ai2[i] * ai2[i]);
						if (j1 > maxR) {
							maxR = j1;
						}
						i++;
					}
				}
				if (line.startsWith("</p>")) {
					p[npl] = new Plane(m, t, ai, ai2, ai1, i, ai3, flag2, k, l, 0, 0, 0, disline, 0, flag3, byte0);
					npl++;
					flag = false;
				}
				if (line.startsWith("rims")) {
					wheels.setrims(Utility.getint("rims", line, 0), Utility.getint("rims", line, 1),
							Utility.getint("rims", line, 2), Utility.getint("rims", line, 3),
							Utility.getint("rims", line, 4));
				}
				if (line.startsWith("w")) {
					keyx[j] = (int) (Utility.getint("w", line, 0) * f * nfmm_scale[0]);
					keyz[j] = (int) (Utility.getint("w", line, 2) * f * nfmm_scale[2]);
					j++;
					wheels.make(m, t, p, npl, (int) (Utility.getint("w", line, 0) * f * f1 * nfmm_scale[0]),
							(int) (Utility.getint("w", line, 1) * f * nfmm_scale[1]),
							(int) (Utility.getint("w", line, 2) * f * nfmm_scale[2]), Utility.getint("w", line, 3),
							(int) (Utility.getint("w", line, 4) * f * f1), (int) (Utility.getint("w", line, 5) * f),
							i1);
					/**
					 * these next 15 planes are wheels
					 */
					for(int itsAWheel = 0; itsAWheel < 15; itsAWheel++)
						p[npl+itsAWheel].wheel = true;
					npl += 15;
				}
				if (line.startsWith("tracks")) {
					int k1 = Utility.getint("tracks", line, 0);
					txy = new int[k1];
					tzy = new int[k1];
					tc = new int[k1][3];
					tradx = new int[k1];
					tradz = new int[k1];
					trady = new int[k1];
					tx = new int[k1];
					ty = new int[k1];
					tz = new int[k1];
					skd = new int[k1];
					dam = new int[k1];
					notwall = new boolean[k1];
				}
				if (line.startsWith("<track>")) {
					flag1 = true;
					notwall[tnt] = false;
					dam[tnt] = 1;
					skd[tnt] = 0;
					ty[tnt] = 0;
					tx[tnt] = 0;
					tz[tnt] = 0;
					txy[tnt] = 0;
					tzy[tnt] = 0;
					trady[tnt] = 0;
					tradx[tnt] = 0;
					tradz[tnt] = 0;
					tc[tnt][0] = 0;
					tc[tnt][1] = 0;
					tc[tnt][2] = 0;
				}
				if (flag1) {
					if (line.startsWith("c")) {
						tc[tnt][0] = Utility.getint("c", line, 0);
						tc[tnt][1] = Utility.getint("c", line, 1);
						tc[tnt][2] = Utility.getint("c", line, 2);
					}
					if (line.startsWith("xy")) {
						txy[tnt] = Utility.getint("xy", line, 0);
					}
					if (line.startsWith("zy")) {
						tzy[tnt] = Utility.getint("zy", line, 0);
					}
					if (line.startsWith("radx")) {
						tradx[tnt] = (int) (Utility.getint("radx", line, 0) * f);
					}
					if (line.startsWith("rady")) {
						trady[tnt] = (int) (Utility.getint("rady", line, 0) * f);
					}
					if (line.startsWith("radz")) {
						tradz[tnt] = (int) (Utility.getint("radz", line, 0) * f);
					}
					if (line.startsWith("ty")) {
						ty[tnt] = (int) (Utility.getint("ty", line, 0) * f);
					}
					if (line.startsWith("tx")) {
						tx[tnt] = (int) (Utility.getint("tx", line, 0) * f);
					}
					if (line.startsWith("tz")) {
						tz[tnt] = (int) (Utility.getint("tz", line, 0) * f);
					}
					if (line.startsWith("skid")) {
						skd[tnt] = Utility.getint("skid", line, 0);
					}
					if (line.startsWith("dam")) {
						dam[tnt] = 3;
					}
					if (line.startsWith("notwall")) {
						notwall[tnt] = true;
					}
				}
				if (line.startsWith("</track>")) {
					flag1 = false;
					tnt++;
				}
				if (line.startsWith("disp")) {
					disp = Utility.getint("disp", line, 0);
				}
				if (line.startsWith("disline")) {
					disline = Utility.getint("disline", line, 0);
				}
				if (line.startsWith("shadow")) {
					shadow = true;
				}
				if (line.startsWith("stonecold")) {
					noline = true;
				}
				if (line.startsWith("road")) {
					flag3 = true;
				}
				if (line.startsWith("notroad")) {
					flag3 = false;
				}
				if (line.startsWith("grounded")) {
					grounded = Utility.getint("grounded", line, 0) / 100F;
				}
				if (line.startsWith("div")) {
					f = Utility.getint("div", line, 0) / 10F;
				}
				if (line.startsWith("idiv")) {
					f = Utility.getint("idiv", line, 0) / 100F;
				}
				if (line.startsWith("iwid")) {
					f1 = Utility.getint("iwid", line, 0) / 100F;
				}
				if (line.startsWith("gwgr")) {
					i1 = Utility.getint("gwgr", line, 0);
				}
				if (line.startsWith("ScaleX")) {
					nfmm_scale[0] = Utility.getint("ScaleX", line, 0) / 100F;
				}
				if (line.startsWith("ScaleY")) {
					nfmm_scale[1] = Utility.getint("ScaleY", line, 0) / 100F;
				}
				if (line.startsWith("ScaleZ")) {
					nfmm_scale[2] = Utility.getint("ScaleZ", line, 0) / 100F;
				}
			}
		} catch (IOException e) {
			System.out.println("Error loading " + abyte0 + ".rad");
			e.printStackTrace();
		}
		grat = wheels.ground;
	}

	public ContO(ContO conto, int i, int j, int k, int l) {					
		m = conto.m;
		t = conto.t;
		npl = conto.npl;
		maxR = conto.maxR;
		disp = conto.disp;
		disline = conto.disline;
		noline = conto.noline;
		shadow = conto.shadow;
		grounded = conto.grounded;
		grat = conto.grat;
		p = new Plane[conto.npl];
		for (int i1 = 0; i1 < npl; i1++) {
			if (conto.p[i1].master != 0) {
				conto.p[i1].n = 16;
			}
			p[i1] = new Plane(m, t, conto.p[i1].ox, conto.p[i1].oz, conto.p[i1].oy, conto.p[i1].n, conto.p[i1].oc,
					conto.p[i1].glass, conto.p[i1].gr, conto.p[i1].fs, conto.p[i1].wx, conto.p[i1].wy, conto.p[i1].wz,
					conto.disline, conto.p[i1].bfase, conto.p[i1].road, conto.p[i1].light);
		}

		x = i;
		y = j;
		z = k;
		for (int j1 = 0; j1 < npl; j1++) {
			/**
			 * get it
			 * TODO
			 */
			p[j1].wheel = conto.p[j1].wheel;
			
			p[j1].master = conto.p[j1].master;
			Utility.rot(p[j1].ox, p[j1].oz, 0, 0, l, p[j1].n);
			p[j1].loadprojf();
		}

		if (conto.tnt != 0) {
			for (int k1 = 0; k1 < conto.tnt; k1++) {
				t.xy[t.nt] = (int) (conto.txy[k1] * RadicalMath.cos(l) - conto.tzy[k1] * RadicalMath.sin(l));
				t.zy[t.nt] = (int) (conto.tzy[k1] * RadicalMath.cos(l) + conto.txy[k1] * RadicalMath.sin(l));
				int i2 = 0;
				do {
					t.c[t.nt][i2] = (int) (conto.tc[k1][i2]
							+ conto.tc[k1][i2] * (m.snap[i2] / 100F));
					if (t.c[t.nt][i2] > 255) {
						t.c[t.nt][i2] = 255;
					}
					if (t.c[t.nt][i2] < 0) {
						t.c[t.nt][i2] = 0;
					}
				} while (++i2 < 3);
				t.x[t.nt] = (int) ((x + conto.tx[k1] * RadicalMath.cos(l)) - conto.tz[k1] * RadicalMath.sin(l));
				t.z[t.nt] = (int) (z + conto.tz[k1] * RadicalMath.cos(l) + conto.tx[k1] * RadicalMath.sin(l));
				t.y[t.nt] = y + conto.ty[k1];
				t.skd[t.nt] = conto.skd[k1];
				t.dam[t.nt] = conto.dam[k1];
				t.notwall[t.nt] = conto.notwall[k1];
				i2 = Math.abs(l);
				if (i2 == 180) {
					i2 = 0;
				}
				t.radx[t.nt] = (int) Math
						.abs(conto.tradx[k1] * RadicalMath.cos(i2) + conto.tradz[k1] * RadicalMath.sin(i2));
				t.radz[t.nt] = (int) Math
						.abs(conto.tradx[k1] * RadicalMath.sin(i2) + conto.tradz[k1] * RadicalMath.cos(i2));
				t.rady[t.nt] = conto.trady[k1];
				t.nt++;
			}

		}
		int l1 = 0;
		do {
			stg[l1] = 0;
			keyx[l1] = conto.keyx[l1];
			keyz[l1] = conto.keyz[l1];
		} while (++l1 < 4);
	}

	public void d(Graphics2D rd) {
		if (dist != 0) {
			dist = 0;
		}
		int i = m.cx + (int) ((x - m.x - m.cx) * RadicalMath.cos(m.xz) - (z - m.z - m.cz) * RadicalMath.sin(m.xz));
		int j = m.cz + (int) ((x - m.x - m.cx) * RadicalMath.sin(m.xz) + (z - m.z - m.cz) * RadicalMath.cos(m.xz));
		int k = m.cz + (int) ((y - m.y - m.cy) * RadicalMath.sin(m.zy) + (j - m.cz) * RadicalMath.cos(m.zy));
		int l = Utility.cXs(i + maxR, k) - Utility.cXs(i - maxR, k);
		if (Utility.cXs(i + maxR * 2, k) > 0 && Utility.cXs(i - maxR * 2, k) < m.w && k > -maxR && (k < m.fade[disline] + maxR || m.trk)
				&& (l > disp || m.trk)) {
			if (shadow) {
				if (!m.crs) {
					if (k < 2000) {
						boolean flag = false;
						for (int l1 = t.nt - 1; l1 >= 0; l1--) {
							if (Math.abs(t.zy[l1]) == 90 || Math.abs(t.xy[l1]) == 90
									|| Math.abs(x - t.x[l1]) >= t.radx[l1] + maxR
									|| Math.abs(z - t.z[l1]) >= t.radz[l1] + maxR) {
								continue;
							}
							flag = true;
							break;
						}

						rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						if (flag) {
							for (int i2 = 0; i2 < npl; i2++) {
								p[i2].s(rd, x - m.x, y - m.y, z - m.z, xz, xy, zy, 0);
							}

						} else {
							int j2 = m.cy + (int) ((m.ground - m.cy) * RadicalMath.cos(m.zy)
									- (j - m.cz) * RadicalMath.sin(m.zy));
							int k2 = m.cz + (int) ((m.ground - m.cy) * RadicalMath.sin(m.zy)
									+ (j - m.cz) * RadicalMath.cos(m.zy));
							if (Utility.cYs(j2 + maxR, k2) > 0 && Utility.cYs(j2 - maxR, k2) < m.h) {
								for (int l2 = 0; l2 < npl; l2++) {
									p[l2].s(rd, x - m.x, y - m.y, z - m.z, xz, xy, zy, 1);
								}

							}
						}
						m.addsp(x - m.x, z - m.z, (int) (maxR * 0.80000000000000004D));
						rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
					} else {
						lowshadow(rd, k);
					}
				} else {
					for (int i1 = 0; i1 < npl; i1++) {
						p[i1].s(rd, x - m.x, y - m.y, z - m.z, xz, xy, zy, 2);
					}
				}
			}
			int j1 = m.cy + (int) ((y - m.y - m.cy) * RadicalMath.cos(m.zy) - (j - m.cz) * RadicalMath.sin(m.zy));
			if (Utility.cYs(j1 + maxR, k) > 0 && Utility.cYs(j1 - maxR, k) < m.h) {
				if (elec) {
					electrify(rd);
				}
				if (fix) {
					fixit(rd);
				}
				if (checkpoint != 0 && checkpoint - 1 == m.checkpoint) {
					l = -1;
				}
				int ai[] = new int[npl];
				int ai1[] = new int[npl];
				int i3 = 0;
				do {
					if (stg[i3] != 0) {
						pdust(i3, rd, -1);
					}
				} while (++i3 < 4);
				for (int j3 = 0; j3 < npl; j3++) {
					ai[j3] = 0;
				}

				for (int k3 = 0; k3 < npl; k3++) {
					for (int i4 = k3 + 1; i4 < npl; i4++) {
						if (p[k3].av != p[i4].av) {
							if (p[k3].av < p[i4].av) {
								ai[k3]++;
							} else {
								ai[i4]++;
							}
						} else if (k3 > i4) {
							ai[k3]++;
						} else {
							ai[i4]++;
						}
					}

					ai1[ai[k3]] = k3;
				}

				for (int l3 = 0; l3 < npl; l3++) {
					p[ai1[l3]].d(rd, x - m.x, y - m.y, z - m.z, xz, xy, zy, wxz, wzy, noline, l);
					if (p[ai1[l3]].master != 0 && stg[p[ai1[l3]].master - 1] != 0) {
						pdust(p[ai1[l3]].master - 1, rd, 1);
					}
				}

				dist = (int) (Math.sqrt((int) Math.sqrt(((m.x + m.cx) - x) * ((m.x + m.cx) - x) + (m.z - z) * (m.z - z)
						+ ((m.y + m.cy) - y) * ((m.y + m.cy) - y))) * grounded);
			}
		}
		if (dist == 0) {
			int k1 = 0;
			do {
				if (stg[k1] != 0) {
					if (stg[k1] == 4) {
						stg[k1] = 0;
					} else {
						stg[k1]++;
					}
				}
			} while (++k1 < 4);
		}
	}

	public void dust(int i, float f, float f1, float f2, float f3, float f4, float f5, boolean flag, int j) {
		boolean flag1 = false;
		if (j > 5 && (i == 0 || i == 2)) {
			flag1 = true;
		}
		if (j < -5 && (i == 1 || i == 3)) {
			flag1 = true;
		}
		if (stg[i] == 0 && Math.abs(f3) + Math.abs(f4) > 100F && !flag1) {
			sx[i] = (int) f;
			sy[i] = (int) f1;
			sz[i] = (int) f2;
			stg[i] = 1;
			dov[i] = -1;
			smag[i] = f5;
			scx[i] = (int) f3;
			scz[i] = (int) f4;
			fulls[i] = flag;
		}
	}

	public void lowshadow(Graphics2D rd, int i) {
		int ai[] = new int[4];
		int ai1[] = new int[4];
		int ai2[] = new int[4];
		byte byte0 = 1;
		int j;
		for (j = Math.abs(zy); j > 270; j -= 360) {
		}
		j = Math.abs(j);
		if (j > 90) {
			byte0 = -1;
		}
		ai[0] = (int) ((keyx[0] * 1.2D + x) - m.x);
		ai2[0] = (int) (((keyz[0] + 30) * byte0 * 1.2D + z) - m.z);
		ai[1] = (int) ((keyx[1] * 1.2D + x) - m.x);
		ai2[1] = (int) (((keyz[1] + 30) * byte0 * 1.2D + z) - m.z);
		ai[2] = (int) ((keyx[3] * 1.2D + x) - m.x);
		ai2[2] = (int) (((keyz[3] - 30) * byte0 * 1.2D + z) - m.z);
		ai[3] = (int) ((keyx[2] * 1.2D + x) - m.x);
		ai2[3] = (int) (((keyz[2] - 30) * byte0 * 1.2D + z) - m.z);
		Utility.rot(ai, ai2, x - m.x, z - m.z, xz, 4);
		int k = (int) (m.cgrnd[0] / 1.5D);
		int l = (int) (m.cgrnd[1] / 1.5D);
		int i1 = (int) (m.cgrnd[2] / 1.5D);
		int j1 = 0;
		do {
			ai1[j1] = m.ground;
		} while (++j1 < 4);
		for (int k1 = t.nt - 1; k1 >= 0; k1--) {
			int l1 = 0;
			int j2 = 0;
			do {
				if (Math.abs(t.zy[k1]) != 90 && Math.abs(t.xy[k1]) != 90
						&& Math.abs(ai[j2] - (t.x[k1] - m.x)) < t.radx[k1]
						&& Math.abs(ai2[j2] - (t.z[k1] - m.z)) < t.radz[k1]) {
					l1++;
				}
			} while (++j2 < 4);
			if (l1 <= 2) {
				continue;
			}
			j2 = 0;
			do {
				ai1[j2] = t.y[k1] - m.y;
				if (t.zy[k1] != 0) {
					ai1[j2] += ((ai2[j2] - (t.z[k1] - m.z - t.radz[k1])) * RadicalMath.sin(t.zy[k1]))
							/ RadicalMath.sin(90 - t.zy[k1]) - (t.radz[k1] * RadicalMath.sin(t.zy[k1])) / RadicalMath.sin(90 - t.zy[k1]);
				}
				if (t.xy[k1] != 0) {
					ai1[j2] += ((ai[j2] - (t.x[k1] - m.x - t.radx[k1])) * RadicalMath.sin(t.xy[k1]))
							/ RadicalMath.sin(90 - t.xy[k1]) - (t.radx[k1] * RadicalMath.sin(t.xy[k1])) / RadicalMath.sin(90 - t.xy[k1]);
				}
			} while (++j2 < 4);
			k = (int) (t.c[k1][0] / 1.5D);
			l = (int) (t.c[k1][1] / 1.5D);
			i1 = (int) (t.c[k1][2] / 1.5D);
			break;
		}

		Utility.rot(ai, ai2, m.cx, m.cz, m.xz, 4);
		Utility.rot(ai1, ai2, m.cy, m.cz, m.zy, 4);
		boolean flag = true;
		int i2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		do {
			ai[j3] = Utility.cXs(ai[j3], ai2[j3]);
			ai1[j3] = Utility.cYs(ai1[j3], ai2[j3]);
			if (ai1[j3] < 0 || ai2[j3] < 10) {
				i2++;
			}
			if (ai1[j3] > m.h || ai2[j3] < 10) {
				k2++;
			}
			if (ai[j3] < 0 || ai2[j3] < 10) {
				l2++;
			}
			if (ai[j3] > m.w || ai2[j3] < 10) {
				i3++;
			}
		} while (++j3 < 4);
		if (l2 == 4 || i2 == 4 || k2 == 4 || i3 == 4) {
			flag = false;
		}
		if (flag) {
			int k3 = 0;
			do {
				if (i > m.fade[k3]) {
					k = (k * m.fogd + m.cfade[0]) / (m.fogd + 1);
					l = (l * m.fogd + m.cfade[1]) / (m.fogd + 1);
					i1 = (i1 * m.fogd + m.cfade[2]) / (m.fogd + 1);
				}
			} while (++k3 < 8);
			rd.setColor(new Color(k, l, i1));
			rd.fillPolygon(ai, ai1, 4);
		}
	}

	public void fixit(Graphics2D rd) {
		if (fcnt == 1) {
			for (int i = 0; i < npl; i++) {
				// COLOR OF CAR GOING THROUGH FIX HOOP
				p[i].hsb[0] = 0.57F;
				p[i].hsb[2] = 0.8F;
				p[i].hsb[1] = 0.8F;
				Color color = Color.getHSBColor(p[i].hsb[0], p[i].hsb[1], p[i].hsb[2]);
				int l = (int) (color.getRed() + color.getRed() * (m.snap[0] / 100F));
				if (l > 255) {
					l = 255;
				}
				if (l < 0) {
					l = 0;
				}
				int i1 = (int) (color.getGreen() + color.getGreen() * (m.snap[1] / 100F));
				if (i1 > 255) {
					i1 = 255;
				}
				if (i1 < 0) {
					i1 = 0;
				}
				int k1 = (int) (color.getBlue() + color.getBlue() * (m.snap[2] / 100F));
				if (k1 > 255) {
					k1 = 255;
				}
				if (k1 < 0) {
					k1 = 0;
				}
				Color.RGBtoHSB(l, i1, k1, p[i].hsb);
				p[i].flx = 1;
			}

		}
		if (fcnt == 2) {
			for (int j = 0; j < npl; j++) {
				p[j].flx = 1;
			}

		}
		if (fcnt == 4) {
			for (int k = 0; k < npl; k++) {
				p[k].flx = 3;
			}

		}
		if (fcnt == 1 || fcnt > 2) {
			int ai[] = new int[8];
			int ai1[] = new int[8];
			int ai2[] = new int[4];
			int j1 = 0;
			do {
				ai[j1] = (keyx[j1] + x) - m.x;
				ai1[j1] = (grat + y) - m.y;
				ai2[j1] = (keyz[j1] + z) - m.z;
			} while (++j1 < 4);
			Utility.rot(ai, ai1, x - m.x, y - m.y, xy, 4);
			Utility.rot(ai1, ai2, y - m.y, z - m.y, zy, 4);
			Utility.rot(ai, ai2, x - m.x, z - m.z, xz, 4);
			Utility.rot(ai, ai2, m.cx, m.cz, m.xz, 4);
			Utility.rot(ai1, ai2, m.cy, m.cz, m.zy, 4);
			j1 = 0;
			int l1 = 0;
			int i2 = 0;
			int j2 = 0;
			do {
				int k2 = 0;
				do {
					if (Math.abs(ai[j2] - ai[k2]) > j1) {
						j1 = Math.abs(ai[j2] - ai[k2]);
					}
					if (Math.abs(ai1[j2] - ai1[k2]) > l1) {
						l1 = Math.abs(ai1[j2] - ai1[k2]);
					}
					if (Utility.py(ai[j2], ai[k2], ai1[j2], ai1[k2]) > i2) {
						i2 = Utility.py(ai[j2], ai[k2], ai1[j2], ai1[k2]);
					}
				} while (++k2 < 4);
			} while (++j2 < 4);
			i2 = (int) (Math.sqrt(i2) / 1.5D);
			if (j1 < i2) {
				j1 = i2;
			}
			if (l1 < i2) {
				l1 = i2;
			}
			j2 = m.cx + (int) ((x - m.x - m.cx) * RadicalMath.cos(m.xz) - (z - m.z - m.cz) * RadicalMath.sin(m.xz));
			int l2 = m.cz + (int) ((x - m.x - m.cx) * RadicalMath.sin(m.xz) + (z - m.z - m.cz) * RadicalMath.cos(m.xz));
			int i3 = m.cy + (int) ((y - m.y - m.cy) * RadicalMath.cos(m.zy) - (l2 - m.cz) * RadicalMath.sin(m.zy));
			l2 = m.cz + (int) ((y - m.y - m.cy) * RadicalMath.sin(m.zy) + (l2 - m.cz) * RadicalMath.cos(m.zy));
			ai[0] = Utility.cXs((int) (j2 - j1 / 0.80000000000000004D
					- m.random() * (j1 / 2.3999999999999999D)), l2);
			ai1[0] = Utility.cYs((int) (i3 - l1 / 1.9199999999999999D
					- m.random() * (l1 / 5.6699999999999999D)), l2);
			ai[1] = Utility.cXs((int) (j2 - j1 / 0.80000000000000004D
					- m.random() * (j1 / 2.3999999999999999D)), l2);
			ai1[1] = Utility.cYs((int) (i3 + l1 / 1.9199999999999999D
					+ m.random() * (l1 / 5.6699999999999999D)), l2);
			ai[2] = Utility.cXs((int) (j2 - j1 / 1.9199999999999999D
					- m.random() * (j1 / 5.6699999999999999D)), l2);
			ai1[2] = Utility.cYs((int) (i3 + l1 / 0.80000000000000004D
					+ m.random() * (l1 / 2.3999999999999999D)), l2);
			ai[3] = Utility.cXs((int) (j2 + j1 / 1.9199999999999999D
					+ m.random() * (j1 / 5.6699999999999999D)), l2);
			ai1[3] = Utility.cYs((int) (i3 + l1 / 0.80000000000000004D
					+ m.random() * (l1 / 2.3999999999999999D)), l2);
			ai[4] = Utility.cXs((int) (j2 + j1 / 0.80000000000000004D
					+ m.random() * (j1 / 2.3999999999999999D)), l2);
			ai1[4] = Utility.cYs((int) (i3 + l1 / 1.9199999999999999D
					+ m.random() * (l1 / 5.6699999999999999D)), l2);
			ai[5] = Utility.cXs((int) (j2 + j1 / 0.80000000000000004D
					+ m.random() * (j1 / 2.3999999999999999D)), l2);
			ai1[5] = Utility.cYs((int) (i3 - l1 / 1.9199999999999999D
					- m.random() * (l1 / 5.6699999999999999D)), l2);
			ai[6] = Utility.cXs((int) (j2 + j1 / 1.9199999999999999D
					+ m.random() * (j1 / 5.6699999999999999D)), l2);
			ai1[6] = Utility.cYs((int) (i3 - l1 / 0.80000000000000004D
					- m.random() * (l1 / 2.3999999999999999D)), l2);
			ai[7] = Utility.cXs((int) (j2 - j1 / 1.9199999999999999D
					- m.random() * (j1 / 5.6699999999999999D)), l2);
			ai1[7] = Utility.cYs((int) (i3 - l1 / 0.80000000000000004D
					- m.random() * (l1 / 2.3999999999999999D)), l2);
			if (fcnt == 3) {
				Utility.rot(ai, ai1, Utility.cXs(j2, l2), Utility.cYs(i3, l2), 22, 8);
			}
			if (fcnt == 4) {
				Utility.rot(ai, ai1, Utility.cXs(j2, l2), Utility.cYs(i3, l2), 22, 8);
			}
			if (fcnt == 5) {
				Utility.rot(ai, ai1, Utility.cXs(j2, l2), Utility.cYs(i3, l2), 0, 8);
			}
			if (fcnt == 6) {
				Utility.rot(ai, ai1, Utility.cXs(j2, l2), Utility.cYs(i3, l2), -22, 8);
			}
			if (fcnt == 7) {
				Utility.rot(ai, ai1, Utility.cXs(j2, l2), Utility.cYs(i3, l2), -22, 8);
			}
			int j3 = (int) (191F + 191F * (m.snap[0] / 350F));
			if (j3 > 255) {
				j3 = 255;
			}
			if (j3 < 0) {
				j3 = 0;
			}
			int k3 = (int) (232F + 232F * (m.snap[1] / 350F));
			if (k3 > 255) {
				k3 = 255;
			}
			if (k3 < 0) {
				k3 = 0;
			}
			int l3 = (int) (255F + 255F * (m.snap[2] / 350F));
			if (l3 > 255) {
				l3 = 255;
			}
			if (l3 < 0) {
				l3 = 0;
			}
			rd.setColor(new Color(j3, k3, l3));
			rd.fillPolygon(ai, ai1, 8);
			ai[0] = Utility.cXs((int) (j2 - j1 - m.random() * (j1 / 4)), l2);
			ai1[0] = Utility.cYs((int) (i3 - l1 / 2.3999999999999999D
					- m.random() * (l1 / 9.5999999999999996D)), l2);
			ai[1] = Utility.cXs((int) (j2 - j1 - m.random() * (j1 / 4)), l2);
			ai1[1] = Utility.cYs((int) (i3 + l1 / 2.3999999999999999D
					+ m.random() * (l1 / 9.5999999999999996D)), l2);
			ai[2] = Utility.cXs((int) (j2 - j1 / 2.3999999999999999D
					- m.random() * (j1 / 9.5999999999999996D)), l2);
			ai1[2] = Utility.cYs((int) (i3 + l1 + m.random() * (l1 / 4)), l2);
			ai[3] = Utility.cXs((int) (j2 + j1 / 2.3999999999999999D
					+ m.random() * (j1 / 9.5999999999999996D)), l2);
			ai1[3] = Utility.cYs((int) (i3 + l1 + m.random() * (l1 / 4)), l2);
			ai[4] = Utility.cXs((int) (j2 + j1 + m.random() * (j1 / 4)), l2);
			ai1[4] = Utility.cYs((int) (i3 + l1 / 2.3999999999999999D
					+ m.random() * (l1 / 9.5999999999999996D)), l2);
			ai[5] = Utility.cXs((int) (j2 + j1 + m.random() * (j1 / 4)), l2);
			ai1[5] = Utility.cYs((int) (i3 - l1 / 2.3999999999999999D
					- m.random() * (l1 / 9.5999999999999996D)), l2);
			ai[6] = Utility.cXs((int) (j2 + j1 / 2.3999999999999999D
					+ m.random() * (j1 / 9.5999999999999996D)), l2);
			ai1[6] = Utility.cYs((int) (i3 - l1 - m.random() * (l1 / 4)), l2);
			ai[7] = Utility.cXs((int) (j2 - j1 / 2.3999999999999999D
					- m.random() * (j1 / 9.5999999999999996D)), l2);
			ai1[7] = Utility.cYs((int) (i3 - l1 - m.random() * (l1 / 4)), l2);
			j3 = (int) (213F + 213F * (m.snap[0] / 350F));
			if (j3 > 255) {
				j3 = 255;
			}
			if (j3 < 0) {
				j3 = 0;
			}
			k3 = (int) (239F + 239F * (m.snap[1] / 350F));
			if (k3 > 255) {
				k3 = 255;
			}
			if (k3 < 0) {
				k3 = 0;
			}
			l3 = (int) (255F + 255F * (m.snap[2] / 350F));
			if (l3 > 255) {
				l3 = 255;
			}
			if (l3 < 0) {
				l3 = 0;
			}
			rd.setColor(new Color(j3, k3, l3));
			rd.fillPolygon(ai, ai1, 8);
		}
		if (fcnt > 7) {
			fcnt = 0;
			fix = false;
		} else {
			fcnt++;
		}
	}

	public void electrify(Graphics2D rd) {
		int i = 0;
		do {
			if (elc[i] == 0) {
				edl[i] = (int) (380F - m.random() * 760F);
				edr[i] = (int) (380F - m.random() * 760F);
				elc[i] = 1;
			}
			int j = (int) (edl[i] + (190F - m.random() * 380F));
			int k = (int) (edr[i] + (190F - m.random() * 380F));
			int l = (int) (m.random() * 126F);
			int i1 = (int) (m.random() * 126F);
			int ai[] = new int[8];
			int ai1[] = new int[8];
			int ai2[] = new int[8];
			int j1 = 0;
			do {
				ai2[j1] = z - m.z;
			} while (++j1 < 8);
			ai[0] = x - m.x - 504;
			ai1[0] = y - m.y - edl[i] - 5 - (int) (m.random() * 5F);
			ai[1] = (x - m.x - 252) + i1;
			ai1[1] = y - m.y - j - 5 - (int) (m.random() * 5F);
			ai[2] = ((x - m.x) + 252) - l;
			ai1[2] = y - m.y - k - 5 - (int) (m.random() * 5F);
			ai[3] = (x - m.x) + 504;
			ai1[3] = y - m.y - edr[i] - 5 - (int) (m.random() * 5F);
			ai[4] = (x - m.x) + 504;
			ai1[4] = (y - m.y - edr[i]) + 5 + (int) (m.random() * 5F);
			ai[5] = ((x - m.x) + 252) - l;
			ai1[5] = (y - m.y - k) + 5 + (int) (m.random() * 5F);
			ai[6] = (x - m.x - 252) + i1;
			ai1[6] = (y - m.y - j) + 5 + (int) (m.random() * 5F);
			ai[7] = x - m.x - 504;
			ai1[7] = (y - m.y - edl[i]) + 5 + (int) (m.random() * 5F);
			if (roted) {
				Utility.rot(ai, ai2, x - m.x, z - m.z, 90, 8);
			}
			Utility.rot(ai, ai2, m.cx, m.cz, m.xz, 8);
			Utility.rot(ai1, ai2, m.cy, m.cz, m.zy, 8);
			boolean flag = true;
			int k1 = 0;
			int l1 = 0;
			int i2 = 0;
			int j2 = 0;
			int ai3[] = new int[8];
			int ai4[] = new int[8];
			int k2 = 0;
			do {
				ai3[k2] = Utility.cXs(ai[k2], ai2[k2]);
				ai4[k2] = Utility.cYs(ai1[k2], ai2[k2]);
				if (ai4[k2] < 0 || ai2[k2] < 10) {
					k1++;
				}
				if (ai4[k2] > m.h || ai2[k2] < 10) {
					l1++;
				}
				if (ai3[k2] < 0 || ai2[k2] < 10) {
					i2++;
				}
				if (ai3[k2] > m.w || ai2[k2] < 10) {
					j2++;
				}
			} while (++k2 < 8);
			if (i2 == 8 || k1 == 8 || l1 == 8 || j2 == 8) {
				flag = false;
			}
			if (flag) {
				int l2 = (int) (160F + 160F * (m.snap[0] / 500F));
				if (l2 > 255) {
					l2 = 255;
				}
				if (l2 < 0) {
					l2 = 0;
				}
				int j3 = (int) (238F + 238F * (m.snap[1] / 500F));
				if (j3 > 255) {
					j3 = 255;
				}
				if (j3 < 0) {
					j3 = 0;
				}
				int l3 = (int) (255F + 255F * (m.snap[2] / 500F));
				if (l3 > 255) {
					l3 = 255;
				}
				if (l3 < 0) {
					l3 = 0;
				}
				l2 = (l2 * 2 + 214 * (elc[i] - 1)) / (elc[i] + 1);
				j3 = (j3 * 2 + 236 * (elc[i] - 1)) / (elc[i] + 1);
				if (m.trk) {
					l2 = 255;
					j3 = 128;
					l3 = 0;
				}
				rd.setColor(new Color(l2, j3, l3));
				rd.fillPolygon(ai3, ai4, 8);
				if (ai2[0] < 4000) {
					int i3 = (int) (150F + 150F * (m.snap[0] / 500F));
					if (i3 > 255) {
						i3 = 255;
					}
					if (i3 < 0) {
						i3 = 0;
					}
					int k3 = (int) (227F + 227F * (m.snap[1] / 500F));
					if (k3 > 255) {
						k3 = 255;
					}
					if (k3 < 0) {
						k3 = 0;
					}
					int i4 = (int) (255F + 255F * (m.snap[2] / 500F));
					if (i4 > 255) {
						i4 = 255;
					}
					if (i4 < 0) {
						i4 = 0;
					}
					rd.setColor(new Color(i3, k3, i4));
					rd.drawPolygon(ai3, ai4, 8);
				}
			}
			if (elc[i] > m.random() * 60F) {
				elc[i] = 0;
			} else {
				elc[i]++;
			}
		} while (++i < 4);
		if (!roted) {
			xy += 11;
		} else {
			zy += 11;
		}
	}	
	
}
