
import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * GameSparker brings everything together. 
 * @author Kaffeinated, Omar Waly
 *
 */
public class GameSparker extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -34048182014310663L;
	
	/**
	 * save the last Id FX were performed to
	 * when the id changes, run a reset on this Id
	 */
	private int saveAnId = 0;
	/**
	 * change this if a car gets fixed, change back after fx return false
	 */
	private boolean justFixed[] = {
			false, false, false, false, false, false, false
	};
	
	public static final String carModels[] = {
			"2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", "leadoxide", "koolkat", "drifter",
			"policecops", "mustang", "king", "audir8", "masheen", "radicalone", "drmonster"
	};

	public static final String trackModels[] = {
			"road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", "nroad", "nturn",
			"roblend", "noblend", "rnblend", "roadend", "offroadend", "hpground", "ramp30", "cramp35", "dramp15",
			"dhilo15", "slide10", "takeoff", "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail",
			"thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", "uprise", "riseroad", "sroad",
			"soffroad"
	};

	public static final String extraModels[] = {};
	
	/**
	 * false to disable splash
	 */
	public static final boolean splashScreenState = true;
	
	public static final String stageDir = "stages/";
	
	/**
	 * Set directory for temporary creation of cookies (directory is deleted after writing is complete) 
	 */
	public static final String cookieDirTemp = "data/cookies/";
	/**
	 * Set location for the cookie.radq
	 */
	public static final String cookieDirZip = "data/cookies.radq";
	
	private String stageError = "";

	public Graphics2D rd;
	public Graphics sg;
	public Image offImage;
	public Thread gamer;
	public Control u[];
	public int mouses;
	public int xm;
	public int ym;
	public boolean lostfcs;
	public boolean exwist;
	public int nob;
	public int notb;
	public int view;

	/* variables for screen shake */
	
	private int shaka = 0;
	private int apx = 0;
	private int apy = 0;

	/**
	 * <a href="http://www.expandinghead.net/keycode.html">http://www.expandinghead.net/keycode.html</a>
	 */
	@Override
	public boolean keyDown(Event event, int i)
	{
		if (!exwist) {
			if (i == 1004)
				u[0].up = true;
			if (i == 1005)
				u[0].down = true;
			if (i == 1007)
				u[0].right = true;
			if (i == 1006)
				u[0].left = true;
			if (i == 32)
				u[0].handb = true;
			if (i == 120 || i == 88)
				u[0].lookback = -1;
			if (i == 122 || i == 90)
				u[0].lookback = 1;
			if (i == 10 || i == 80 || i == 112 || i == 27)
				u[0].enter = true;
			if (i == 77 || i == 109)
				Control.mutem = !Control.mutem;
			if (i == 78 || i == 110)
				Control.mutes = !Control.mutes;
			if (i == 97 || i == 65)
				if (u[0].arrace)
					u[0].arrace = false;
				else
					u[0].arrace = true;
			if (i == 118 || i == 86) {
				view++;
				if (view == 3)
					view = 0;
			}
		}
		return false;
	}

	@Override
	public void stop() {
		if (exwist && gamer != null) {
			System.gc();
			gamer.stop();
			gamer = null;
		}
		exwist = true;
	}

	@Override
	public boolean lostFocus(Event event, Object obj) {
		if (!exwist && !lostfcs) {
			lostfcs = true;
			mouses = 0;
			u[0].falseo();
			setCursor(new Cursor(0));
		}
		return false;
	}

	@Override
	public boolean gotFocus(Event event, Object obj) {
		if (!exwist && lostfcs)
			lostfcs = false;
		return false;
	}

	public void savecookie(String filename, String num) {
		try {
			/**
			 * since I want full control over the filenames, we'll create a normal file in the temporary file directory
			 */
			try {
				File cookieTempLocation = new File(cookieDirTemp);

				if (!cookieTempLocation.exists()) {
					cookieTempLocation.mkdirs();
				}

				File[] cookieFile = {
						new File(cookieDirTemp + filename + ".dat")
				};

				if (!cookieFile[0].exists()) {
					cookieFile[0].createNewFile();
				}
				FileWriter fw = new FileWriter(cookieFile[0].getPath());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(num + '\n');
				bw.close();

				File cookieZip = new File(cookieDirZip);
				if (!cookieZip.exists()) {
					cookieZip.createNewFile();
				}

				addFile(cookieZip, cookieFile, "");

				cookieFile[0].delete();
				cookieTempLocation.delete();

				System.out.println("Successfully saved game (" + filename + ")");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fromStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		return out.toString();
	}

	/**
	 * attempts to read a cookie
	 * 
	 * @param string name to match
	 * @return value
	 */
	public int readcookie(String string) {
		try {
			ZipFile zipFile = new ZipFile(cookieDirZip);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			String fromEntry = " ";

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.getName().contains(string)) {
					InputStream stream = zipFile.getInputStream(entry);
					fromEntry = fromStream(stream);
				}
			}
			zipFile.close();

			System.out.println("Successfully read cookie " + string + " with value " + Integer.parseInt(fromEntry));
			return Integer.parseInt(fromEntry);
		} catch (IOException ioexception) {
			// System.out.println(ioexception.toString());
			System.out.println(string + ".dat probably doesn't exist");
			return -1;
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	private void cropit(final Graphics2D graphics2d, final int i, final int i_98_) {
		if (i != 0 || i_98_ != 0) {
			graphics2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
			graphics2d.setColor(new Color(0, 0, 0));
		}
		if (i != 0) {
			if (i < 0) {
				graphics2d.fillRect(apx + i, apy - (int) (25.0F), Math.abs(i), (int) (400.0F));
			} else {
				graphics2d.fillRect(apx + (int) (670.0F), apy - (int) (25.0F), i, (int) (400.0F));
			}
		}
		if (i_98_ != 0) {
			if (i_98_ < 0) {
				graphics2d.fillRect(apx - (int) (25.0F), apy + i_98_, (int) (720.0F), Math.abs(i_98_));
			} else {
				graphics2d.fillRect(apx - (int) (25.0F), apy + (int) (450.0F), (int) (720.0F), i_98_);
			}
		}

	}

	@Override
	public void paint(Graphics g) {
		final Graphics2D graphics2d = (Graphics2D) g;

		int i = 0;
		int i_97_ = 0;
		if (shaka > 10) {
			i = (int) ((shaka * 2 * Math.random() - shaka));
			i_97_ = (int) ((shaka * 2 * Math.random() - shaka));
			shaka -= 5;
		}
		apx = (int) (getWidth() / 2 - 335.0F);
		apy = (int) (getHeight() / 2 - 200.0F);

		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.drawImage(offImage, apx + i, apy + i_97_, this);

		cropit(graphics2d, i, i_97_);
	}

	public GameSparker() {
		u = new Control[7];
		mouses = 0;
		xm = 0;
		ym = 0;
		lostfcs = false;
		exwist = true;
		nob = 0;
		notb = 0;
		view = 0;
	}

	/**
	 * @param input name of model you want id of
	 * @return Position on model in array. If you spelled it wrong or if it doesn't eist, it returns -1, so you have that to look forward to.
	 * @author Kaffeinated
	 */
	private int getModel(String input) {

		String[][] allModels = new String[][] {
				carModels, trackModels, extraModels
		}; /// need to have all the model arrays here

		int modelId = 0;

		for (int i = 0; i < allModels.length; i++) {
			for (int j = 0; j < allModels[i].length; j++) {
				if (input == allModels[i][j]) {
					int addWhat = 0;
					if (i == 1) {
						addWhat = carModels.length;
					}
					if (i == 2) {
						addWhat = carModels.length + trackModels.length;
					}
					modelId = j + addWhat;
					System.out.println("Found model " + modelId + " matching string \"" + input + "\"");
					return modelId;
				}
			}
		}
		System.out.println("No results for getModel | check you're speling and grammer");
		return -1;
	}

	/**
	 * Loads all models
	 * @param conto conto instance
	 * @param medium medium instance
	 * @param trackers trackers instance
	 * @param xtgraphics xtgraphics instance
	 * @author Kaffeinated, Omar Waly
	 * 
	 */
	private void loadbase(final ContO conto[], Medium medium, Trackers trackers, xtGraphics xtgraphics) {
		xtgraphics.dnload += 6;
		try {
			ZipInputStream zipinputstream;
			final File file = new File(new StringBuilder().append("").append("data/models.radq").toString());
			zipinputstream = new ZipInputStream(new FileInputStream(file));
			ZipEntry zipentry = zipinputstream.getNextEntry();
			for (; zipentry != null; zipentry = zipinputstream.getNextEntry()) {
				int modelId = -1;
				
				final int carCount = carModels.length;
				final int trackCount = trackModels.length;
				// final int extraCount = extraModels.length;

				for (int car = 0; car < carModels.length; car++)
					if (zipentry.getName().startsWith(carModels[car]))
						modelId = car;

				for (int track = 0; track < trackModels.length; track++)
					if (zipentry.getName().startsWith(trackModels[track]))
						modelId = track + carCount;

				for (int extra = 0; extra < extraModels.length; extra++)
					if (zipentry.getName().startsWith(extraModels[extra]))
						modelId = extra + trackCount + carCount;

				int entireSize = (int) zipentry.getSize();
				final byte[] modelData = new byte[entireSize];

				int unknown1 = 0;
				int unknown2;
				for (; entireSize > 0; entireSize -= unknown2) {
					unknown2 = zipinputstream.read(modelData, unknown1, entireSize);
					unknown1 += unknown2;
				}
				conto[modelId] = new ContO(modelData, medium, trackers);
				xtgraphics.dnload++;
			}
			/*
			 * be sure to add your added arrays here			
			 */
			System.out.println("Contos loaded: " + (carModels.length + trackModels.length + extraModels.length));
			zipinputstream.close();
		} catch (IOException e) {
			System.out.println("Error Reading Models: " + e);
			e.printStackTrace();
		}
		System.gc();
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public int sunytyp() {
		String s = System.getProperty("java.version");
		String s1 = "" + getAppletContext();
		if (!s1.startsWith("com.ms."))
			return !s.startsWith("1.3") && !s.startsWith("1.4") ? 2 : 1;
		else
			return 0;
	}

	/**
	 * <a href="http://www.expandinghead.net/keycode.html">http://www.expandinghead.net/keycode.html</a>
	 */
	@Override
	public boolean keyUp(Event event, int i)
	{
		if (!exwist) {
			if (i == 1004)
				u[0].up = false;
			if (i == 1005)
				u[0].down = false;
			if (i == 1007)
				u[0].right = false;
			if (i == 1006)
				u[0].left = false;
			if (i == 32)
				u[0].handb = false;
			if (i == 120 || i == 88 || i == 122 || i == 90)
				u[0].lookback = 0;
		}
		return false;
	}

	@Override
	public void start() {
		if (gamer == null)
			gamer = new Thread(this);
		if (gamer.getState() == Thread.State.NEW)
			gamer.start();
	}

	@Override
	public boolean mouseDown(Event event, int i, int j) {
		if (!exwist && mouses == 0) {
			xm = i;
			ym = j;
			mouses = 1;
		}
		return false;
	}

	/**
	 * Loads stage elements
	 *
	 * @param aconto 		conto instance
	 * @param aconto1 		conto instance specifically for cars
	 * @param medium 		medium instance
	 * @param trackers 		trackers instance
	 * @param checkpoints 	checkpoints instance
	 * @param xtgraphics 	xtgraphics instance
	 * @param amadness 		madness instance
	 * @param record 		record instance
	 * @author Kaffeinated, Omar Waly
	 * 
	 */
	public void loadstage(ContO aconto[], ContO aconto1[], PolyFX polyfx1[], Medium medium, Trackers trackers, CheckPoints checkpoints,
			xtGraphics xtgraphics, Madness amadness[], Record record) {
		trackers.nt = 0;
		nob = 7;
		notb = 0;
		checkpoints.n = 0;
		checkpoints.nsp = 0;
		checkpoints.fn = 0;
		checkpoints.haltall = false;
		checkpoints.wasted = 0;
		checkpoints.catchfin = 0;
		medium.lightson = false;
		medium.ground = 250;
		view = 0;

		final int wall_id = getModel("thewall");
		int r_wall = 0;
		int l_wall = 100;
		int t_wall = 0;
		int b_wall = 100;
		
		
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader(new File(stageDir + checkpoints.stage + ".txt")))) {
			for (String line; (line = bufferedreader.readLine()) != null;) {
				line = line.trim();
				
				if (line.startsWith("mountains"))
					medium.mgen = Utility.getint("mountains", line, 0);
				if (line.startsWith("snap"))
					medium.setsnap(Utility.getint("snap", line, 0), Utility.getint("snap", line, 1), Utility.getint("snap", line, 2));
				if (line.startsWith("sky")) {
					medium.setsky(Utility.getint("sky", line, 0), Utility.getint("sky", line, 1), Utility.getint("sky", line, 2));
					xtgraphics.snap(checkpoints.stage);
				}
				if (line.startsWith("ground"))
					medium.setgrnd(Utility.getint("ground", line, 0), Utility.getint("ground", line, 1), Utility.getint("ground", line, 2));
				if (line.startsWith("polys"))
					medium.setpolys(Utility.getint("polys", line, 0), Utility.getint("polys", line, 1), Utility.getint("polys", line, 2));
				if (line.startsWith("fog"))
					medium.setfade(Utility.getint("fog", line, 0), Utility.getint("fog", line, 1), Utility.getint("fog", line, 2));
				if (line.startsWith("density"))
					medium.fogd = Utility.getint("density", line, 0);
				if (line.startsWith("texture")) {
					medium.setexture(Utility.getint("texture", line, 0), Utility.getint("texture", line, 1), Utility.getint("texture", line, 2),
							Utility.getint("texture", line, 3));
				}
				if (line.startsWith("clouds")) {
					medium.setclouds(Utility.getint("clouds", line, 0), Utility.getint("clouds", line, 1), Utility.getint("clouds", line, 2),
							Utility.getint("clouds", line, 3), Utility.getint("clouds", line, 4));
				}
				if (line.startsWith("fadefrom")) {
					medium.fadfrom(Utility.getint("fadefrom", line, 0));
					medium.origfade = medium.fade[0];
				}
				if (line.startsWith("lightson"))
					medium.lightson = true;
				if (line.startsWith("set")) {
					int k1 = Utility.getint("set", line, 0);
					k1 += 6;
					aconto[nob] = new ContO(aconto1[k1], Utility.getint("set", line, 1), medium.ground - aconto1[k1].grat,
							Utility.getint("set", line, 2), Utility.getint("set", line, 3));
					if (line.indexOf(")p") != -1) {
						checkpoints.x[checkpoints.n] = Utility.getint("chk", line, 1);
						checkpoints.z[checkpoints.n] = Utility.getint("chk", line, 2);
						checkpoints.y[checkpoints.n] = 0;
						checkpoints.typ[checkpoints.n] = 0;
						if (line.indexOf(")pt") != -1)
							checkpoints.typ[checkpoints.n] = -1;
						if (line.indexOf(")pr") != -1)
							checkpoints.typ[checkpoints.n] = -2;
						if (line.indexOf(")po") != -1)
							checkpoints.typ[checkpoints.n] = -3;
						if (line.indexOf(")ph") != -1)
							checkpoints.typ[checkpoints.n] = -4;
						checkpoints.n++;
						notb = nob + 1;
					}
					nob++;
				}
				if (line.startsWith("fltset")) {
					int i2 = Utility.getint("fltset", line, 0);
					i2 += 6;
					aconto[nob] = new ContO(aconto1[i2], Utility.getint("fltset", line, 1), Utility.getint("fltset", line, 3),
							Utility.getint("set", line, 2), Utility.getint("set", line, 4));
					if (line.indexOf(")p") != -1) {
						checkpoints.x[checkpoints.n] = Utility.getint("fltset", line, 1);
						checkpoints.z[checkpoints.n] = Utility.getint("fltset", line, 2);
						checkpoints.y[checkpoints.n] = Utility.getint("fltset", line, 3);
						checkpoints.typ[checkpoints.n] = 0;
						if (line.indexOf(")pt") != -1) {
							checkpoints.typ[checkpoints.n] = -1;
						}
						if (line.indexOf(")pr") != -1) {
							checkpoints.typ[checkpoints.n] = -2;
						}
						if (line.indexOf(")po") != -1) {
							checkpoints.typ[checkpoints.n] = -3;
						}
						if (line.indexOf(")ph") != -1) {
							checkpoints.typ[checkpoints.n] = -4;
						}
						checkpoints.n++;
						notb = nob + 1;
					}
					nob++;
				}
				if (line.startsWith("chk")) {
					int l1 = Utility.getint("chk", line, 0);
					l1 += 6;
					aconto[nob] = new ContO(aconto1[l1], Utility.getint("chk", line, 1), medium.ground - aconto1[l1].grat,
							Utility.getint("chk", line, 2), Utility.getint("chk", line, 3));
					checkpoints.x[checkpoints.n] = Utility.getint("chk", line, 1);
					checkpoints.z[checkpoints.n] = Utility.getint("chk", line, 2);
					checkpoints.y[checkpoints.n] = medium.ground - aconto1[l1].grat;
					if (Utility.getint("chk", line, 3) == 0)
						checkpoints.typ[checkpoints.n] = 1;
					else
						checkpoints.typ[checkpoints.n] = 2;
					checkpoints.pcs = checkpoints.n;
					checkpoints.n++;
					aconto[nob].checkpoint = checkpoints.nsp + 1;
					checkpoints.nsp++;
					nob++;
					notb = nob;
				}
				if (line.startsWith("fltchk")) {
					int l1 = Utility.getint("fltchk", line, 0);
					l1 += 6;
					aconto[nob] = new ContO(aconto1[l1], Utility.getint("fltchk", line, 1), Utility.getint("fltchk", line, 3),
							Utility.getint("fltchk", line, 2), Utility.getint("fltchk", line, 4));
					checkpoints.x[checkpoints.n] = Utility.getint("fltchk", line, 1);
					checkpoints.z[checkpoints.n] = Utility.getint("fltchk", line, 2);
					checkpoints.y[checkpoints.n] = Utility.getint("fltchk", line, 3);
					if (Utility.getint("fltchk", line, 4) == 0)
						checkpoints.typ[checkpoints.n] = 1;
					else
						checkpoints.typ[checkpoints.n] = 2;
					checkpoints.pcs = checkpoints.n;
					checkpoints.n++;
					aconto[nob].checkpoint = checkpoints.nsp + 1;
					checkpoints.nsp++;
					nob++;
					notb = nob;
				}
				if (line.startsWith("fix")) {
					int i2 = Utility.getint("fix", line, 0);
					i2 += 6;
					aconto[nob] = new ContO(aconto1[i2], Utility.getint("fix", line, 1), Utility.getint("fix", line, 3),
							Utility.getint("fix", line, 2), Utility.getint("fix", line, 4));
					checkpoints.fx[checkpoints.fn] = Utility.getint("fix", line, 1);
					checkpoints.fz[checkpoints.fn] = Utility.getint("fix", line, 2);
					checkpoints.fy[checkpoints.fn] = Utility.getint("fix", line, 3);
					aconto[nob].elec = true;
					if (Utility.getint("fix", line, 4) != 0) {
						checkpoints.roted[checkpoints.fn] = true;
						aconto[nob].roted = true;
					} else {
						checkpoints.roted[checkpoints.fn] = false;
					}
					if (line.indexOf(")s") != -1)
						checkpoints.special[checkpoints.fn] = true;
					else
						checkpoints.special[checkpoints.fn] = false;
					checkpoints.fn++;
					nob++;
					notb = nob;
				}
				if (line.startsWith("nlaps"))
					checkpoints.nlaps = Utility.getint("nlaps", line, 0);
				if (line.startsWith("name"))
					checkpoints.name = Utility.getstring("name", line, 0).replace('|', ',');
				if (line.startsWith("maxr")) {
					int j2 = Utility.getint("maxr", line, 0);
					int j3 = Utility.getint("maxr", line, 1);
					r_wall = j3;
					int j4 = Utility.getint("maxr", line, 2);
					for (int j5 = 0; j5 < j2; j5++) {
						aconto[nob] = new ContO(aconto1[wall_id], j3, medium.ground - aconto1[wall_id].grat,
								j5 * 4800 + j4, 0);
						nob++;
					}

					trackers.y[trackers.nt] = -5000;
					trackers.rady[trackers.nt] = 7100;
					trackers.x[trackers.nt] = j3 + 500;
					trackers.radx[trackers.nt] = 600;
					trackers.z[trackers.nt] = ((j2 * 4800) / 2 + j4) - 2400;
					trackers.radz[trackers.nt] = (j2 * 4800) / 2;
					trackers.xy[trackers.nt] = 90;
					trackers.zy[trackers.nt] = 0;
					trackers.dam[trackers.nt] = 1;
					trackers.nt++;
				}
				if (line.startsWith("maxl")) {
					int k2 = Utility.getint("maxl", line, 0);
					int k3 = Utility.getint("maxl", line, 1);
					l_wall = k3;
					int k4 = Utility.getint("maxl", line, 2);
					for (int k5 = 0; k5 < k2; k5++) {
						aconto[nob] = new ContO(aconto1[wall_id], k3, medium.ground - aconto1[wall_id].grat,
								k5 * 4800 + k4, 0);
						nob++;
					}

					trackers.y[trackers.nt] = -5000;
					trackers.rady[trackers.nt] = 7100;
					trackers.x[trackers.nt] = k3 - 500;
					trackers.radx[trackers.nt] = 600;
					trackers.z[trackers.nt] = ((k2 * 4800) / 2 + k4) - 2400;
					trackers.radz[trackers.nt] = (k2 * 4800) / 2;
					trackers.xy[trackers.nt] = -90;
					trackers.zy[trackers.nt] = 0;
					trackers.dam[trackers.nt] = 1;
					trackers.nt++;
				}

				if (line.startsWith("maxt")) {
					int l2 = Utility.getint("maxt", line, 0);
					int l3 = Utility.getint("maxt", line, 1);
					t_wall = l3;
					int l4 = Utility.getint("maxt", line, 2);
					for (int l5 = 0; l5 < l2; l5++) {
						aconto[nob] = new ContO(aconto1[wall_id], l5 * 4800 + l4, medium.ground - aconto1[wall_id].grat,
								l3, 90);
						nob++;
					}

					trackers.y[trackers.nt] = -5000;
					trackers.rady[trackers.nt] = 7100;
					trackers.z[trackers.nt] = l3 + 500;
					trackers.radz[trackers.nt] = 600;
					trackers.x[trackers.nt] = ((l2 * 4800) / 2 + l4) - 2400;
					trackers.radx[trackers.nt] = (l2 * 4800) / 2;
					trackers.zy[trackers.nt] = 90;
					trackers.xy[trackers.nt] = 0;
					trackers.dam[trackers.nt] = 1;
					trackers.nt++;
				}
				if (line.startsWith("maxb")) {
					int i3 = Utility.getint("maxb", line, 0);
					int i4 = Utility.getint("maxb", line, 1);
					b_wall = i4;
					int i5 = Utility.getint("maxb", line, 2);
					for (int i6 = 0; i6 < i3; i6++) {
						aconto[nob] = new ContO(aconto1[wall_id], i6 * 4800 + i5, medium.ground - aconto1[wall_id].grat,
								i4, 90);
						nob++;
					}

					trackers.y[trackers.nt] = -5000;
					trackers.rady[trackers.nt] = 7100;
					trackers.z[trackers.nt] = i4 - 500;
					trackers.radz[trackers.nt] = 600;
					trackers.x[trackers.nt] = ((i3 * 4800) / 2 + i5) - 2400;
					trackers.radx[trackers.nt] = (i3 * 4800) / 2;
					trackers.zy[trackers.nt] = -90;
					trackers.xy[trackers.nt] = 0;
					trackers.dam[trackers.nt] = 1;
					trackers.nt++;
				}
			}
			System.out.println(nob + " nob " + notb + " notb " + trackers.nt + " nt");
			for(int nobs = 0; nobs < trackers.nt; nobs++){
				polyfx1[nobs] = new PolyFX();
			}
			System.out.println("just made " + nob + " polysfxs");
			medium.newpolys(l_wall, r_wall - l_wall, b_wall, t_wall - b_wall, trackers, notb);
			medium.newmountains(l_wall, r_wall, b_wall, t_wall);
			medium.newclouds(l_wall, r_wall, b_wall, t_wall);
			medium.newstars();						
		} catch (IOException e) {
			String exceptStr = e.toString();
			final int maxChar = 30;
			
			int maxLength = (exceptStr.length() < maxChar)?exceptStr.length():maxChar;
			stageError = e.toString().substring(0, maxLength) + "...";
			
			xtgraphics.fase = 3;
			System.out.println("Error loading stage " + checkpoints.stage);
			e.printStackTrace();
		}
		if (checkpoints.stage == 16)
			medium.lightn = 0;
		else
			medium.lightn = -1;
		if (checkpoints.stage == 1)
			medium.nochekflk = false;
		else
			medium.nochekflk = true;
		if (xtgraphics.fase == 2) {
			medium.trx = 0L;
			medium.trz = 0L;
			if (trackers.nt >= 4) {
				int i1 = 4;
				do {
					medium.trx += trackers.x[trackers.nt - i1];
					medium.trz += trackers.z[trackers.nt - i1];
				} while (--i1 > 0);
			}
			medium.trx = medium.trx / 4L;
			medium.trz = medium.trz / 4L;
			medium.ptr = 0;
			medium.ptcnt = -10;
			medium.hit = 45000;
			medium.fallen = 0;
			medium.nrnd = 0;
			medium.trk = true;
			xtgraphics.fase = 1;
			mouses = 0;
		}
		int j1 = 0;
		do
			u[j1].reset(checkpoints, xtgraphics.sc[j1]);
		while (++j1 < 7);
		xtgraphics.resetstat(checkpoints.stage);
		j1 = 0;
		do {
			aconto[j1] = new ContO(aconto1[xtgraphics.sc[j1]], xtgraphics.xstart[j1],
					250 - aconto1[xtgraphics.sc[j1]].grat, xtgraphics.zstart[j1], 0);
			amadness[j1].reseto(xtgraphics.sc[j1], aconto[j1], checkpoints);
		} while (++j1 < 7);
		record.reset(aconto);
		System.gc();
	}

	/**
	 * motion
	 * @param amadness madness 
	 * @param shakeAmt shake sensitivity 
	 * 					1 normal
	 * 					20 maximum
	 * @param maxAmt maximum displacement of the screen while shaking
	 */
	public void initMoto(Madness amadness[], int shakeAmt, int maxAmt) {
		if (amadness[0].shakedam > 0) {
			shaka = amadness[0].shakedam / (20 / shakeAmt);
			amadness[0].shakedam = 0;
			if (shaka > maxAmt) {
				shaka = maxAmt;
			}
			shaka--;
		}		
	}

	@Override
	public void run() {		
		rd.setColor(new Color(0, 0, 0));
		rd.fillRect(0, 0, 670, 400);
		repaint();
		/*
		 * start an example timer
		 */
		Utility.startTimer();
		Trackers trackers = new Trackers();
		Medium medium = new Medium();
		int i = 5;
		int j = 530;
		int k = sunytyp();
		if (k != 0)
			i = 15;
		if (k != 2)
			j = 500;
		CheckPoints checkpoints = new CheckPoints();
		xtGraphics xtgraphics = new xtGraphics(medium, rd, this);
		xtgraphics.loaddata(k);
		Record record = new Record(medium);
		ContO aconto[] = new ContO[carModels.length + trackModels.length + extraModels.length]; // be sure all your arrays get in here
		loadbase(aconto, medium, trackers, xtgraphics);
		
		ContO aconto1[] = new ContO[3000];
		/**
		 * one for all the track pieces in a stage
		 */
		PolyFX polyfx1[] = new PolyFX[3000];		
		
		/**
		 * ... and one for each car
		 */
		PolyFX polyfx[] = new PolyFX[7];
		Madness amadness[] = new Madness[7];
		int l = 0;
		do {
			amadness[l] = new Madness(medium, record, xtgraphics, l);			
			u[l] = new Control(medium);
			/**
			 * set the car polyfx up
			 */
			polyfx[l] = new PolyFX();
		} while (++l < 7);
		l = 0;
		float f = 35F;
		int i1 = 80;	
		/*
		 * stop an example timer
		 */
		Utility.stopTimer();
		/**
		 * this bit in here reads cookies and set values
		 */		
		l = readcookie("unlocked");
		if (l >= 1 && l <= 17) {		
			/*
			 * Note: that is an L
			 */
			xtgraphics.unlocked = l;
			if (xtgraphics.unlocked != 17)
				checkpoints.stage = xtgraphics.unlocked;
			else
				checkpoints.stage = (int) (Math.random() * 17D) + 1;
			xtgraphics.opselect = 0;
		}
		l = readcookie("usercar");
		if (l >= 0 && l <= 15)
			xtgraphics.sc[0] = l;
		l = readcookie("gameprfact");
		if (l != -1) {
			f = l;
			i1 = 1;
		}				
		
		boolean flag = false;
		xtgraphics.stoploading();
		System.gc();
		Date date = new Date();
		long l3 = date.getTime();
		float f1 = 30F;
		boolean flag1 = false;
		int j1 = 0;
		int k1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		boolean flag2 = false;
		exwist = false;
		do {			
			Date date1 = new Date();
			long l4 = date1.getTime();			
			if (xtgraphics.fase == 111) {
				if (mouses == 1)
					i2 = 800;
				if (i2 < 800) {
					xtgraphics.clicknow();
					i2++;
				} else {
					i2 = 0;
					xtgraphics.fase = 7;
					mouses = 0;
					lostfcs = false;
				}
			}
			if (xtgraphics.fase == 9)
				if (i2 < 71 && splashScreenState) {
					xtgraphics.rad(i2, 1);
					catchlink(0);
					if (mouses == 2)
						mouses = 0;
					if (mouses == 1)
						mouses = 2;
					i2++;
				} else {
					i2 = 0;
					xtgraphics.fase = 10;
					mouses = 0;
					u[0].falseo();
				}
			if (xtgraphics.fase == -9)
				if (i2 < 2) {
					rd.setColor(new Color(0, 0, 0));
					rd.fillRect(0, 0, 670, 400);
					i2++;
				} else {
					xtgraphics.inishcarselect();
					i2 = 0;
					xtgraphics.fase = 7;
					mouses = 0;
				}
			if (xtgraphics.fase == 8) {
				xtgraphics.credits(u[0]);
				if (xtgraphics.flipo == 102) {
					rd.drawImage(xtgraphics.credsnap(offImage), 0, 0, null);
				}
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (xtgraphics.flipo <= 100)
					catchlink(0);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 10) {
				xtgraphics.maini(u[0]);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 11) {
				xtgraphics.inst(u[0]);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == -5) {
				xtgraphics.finish(checkpoints, aconto, u[0]);
				if (flag) {
					if (checkpoints.stage == xtgraphics.unlocked && xtgraphics.winner && xtgraphics.unlocked != 17)
						savecookie("unlocked", "" + xtgraphics.unlocked);
					savecookie("gameprfact", "" + (int) f);
					savecookie("usercar", "" + xtgraphics.sc[0]);
					flag = true;
				}
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (checkpoints.stage == 17 && xtgraphics.winner)
					catchlink(1);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 7) {
				xtgraphics.carselect(u[0], aconto, amadness[0]);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 6) {
				xtgraphics.musicomp(checkpoints.stage, u[0]);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 5) {
				xtgraphics.loadmusic(checkpoints.stage, i1);
				if (!flag) {
					savecookie("usercar", "" + xtgraphics.sc[0]);
					flag = true;
				}
			}
			if (xtgraphics.fase == 4) {
				xtgraphics.cantgo(u[0]);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 3) {
				xtgraphics.loadingfailed(checkpoints, u[0], stageError);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == 2) {
				xtgraphics.loadingstage(checkpoints.stage);
				loadstage(aconto1, aconto, polyfx1, medium, trackers, checkpoints, xtgraphics, amadness, record);
				u[0].falseo();
			}
			if (xtgraphics.fase == 1) {
				rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				xtgraphics.trackbg(false);
				medium.aroundtrack(checkpoints);
				int i3 = 0;
				int ai[] = new int[200];
				for (int k5 = 7; k5 < notb; k5++)
					if (aconto1[k5].dist != 0) {
						ai[i3] = k5;
						i3++;
					} else {
						aconto1[k5].d(rd);
					}

				int ai5[] = new int[i3];
				for (int j7 = 0; j7 < i3; j7++)
					ai5[j7] = 0;

				for (int k7 = 0; k7 < i3; k7++) {
					for (int i11 = k7 + 1; i11 < i3; i11++)
						if (aconto1[ai[k7]].dist != aconto1[ai[i11]].dist) {
							if (aconto1[ai[k7]].dist < aconto1[ai[i11]].dist)
								ai5[k7]++;
							else
								ai5[i11]++;
						} else if (i11 > k7)
							ai5[k7]++;
						else
							ai5[i11]++;

				}

				for (int l7 = 0; l7 < i3; l7++) {
					for (int j11 = 0; j11 < i3; j11++)
						if (ai5[j11] == l7)
							aconto1[ai[j11]].d(rd);

				}
				
				rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
				xtgraphics.stageselect(checkpoints, u[0]);
			}
			if (xtgraphics.fase == 176) {
				medium.d(rd);
				int j3 = 0;
				int ai1[] = new int[200];
				for (int i6 = 0; i6 < nob; i6++)
					if (aconto1[i6].dist != 0) {
						ai1[j3] = i6;
						j3++;
					} else {
						aconto1[i6].d(rd);
					}

				int ai6[] = new int[j3];
				for (int i8 = 0; i8 < j3; i8++)
					ai6[i8] = 0;

				for (int j8 = 0; j8 < j3; j8++) {
					for (int k11 = j8 + 1; k11 < j3; k11++)
						if (aconto1[ai1[j8]].dist != aconto1[ai1[k11]].dist) {
							if (aconto1[ai1[j8]].dist < aconto1[ai1[k11]].dist)
								ai6[j8]++;
							else
								ai6[k11]++;
						} else if (k11 > j8)
							ai6[j8]++;
						else
							ai6[k11]++;

				}

				for (int k8 = 0; k8 < j3; k8++) {
					for (int l11 = 0; l11 < j3; l11++)
						if (ai6[l11] == k8)
							aconto1[ai1[l11]].d(rd);

				}

				medium.follow(aconto1[0], 0, 0);
				xtgraphics.hipnoload(checkpoints.stage, false);
				if (i1 != 0) {
					i1--;
				} else {
					u[0].enter = false;
					u[0].handb = false;
					if (xtgraphics.loadedt[checkpoints.stage - 1])
						xtgraphics.stracks[checkpoints.stage - 1].play();
					setCursor(new Cursor(0));
					xtgraphics.fase = 6;
				}
			}
			if (xtgraphics.fase == 0) {
				int k3 = 0;
				do {
					if (amadness[k3].newcar) {
						int j5 = aconto1[k3].xz;
						int j6 = aconto1[k3].xy;
						int l8 = aconto1[k3].zy;
						aconto1[k3] = new ContO(aconto[amadness[k3].cn], aconto1[k3].x, aconto1[k3].y, aconto1[k3].z,
								0);
						aconto1[k3].xz = j5;
						aconto1[k3].xy = j6;
						aconto1[k3].zy = l8;
						amadness[k3].newcar = false;
						justFixed[k3] = true;
					}
				} while (++k3 < 7);
				medium.d(rd);
				k3 = 0;
				int ai2[] = new int[200];
				for (int k6 = 0; k6 < nob; k6++)
					if (aconto1[k6].dist != 0) {
						ai2[k3] = k6;
						k3++;
					} else {
						aconto1[k6].d(rd);
					}

				int ai7[] = new int[k3];
				int ai10[] = new int[k3];
				for (int i12 = 0; i12 < k3; i12++)
					ai7[i12] = 0;

				for (int j12 = 0; j12 < k3; j12++) {
					for (int i14 = j12 + 1; i14 < k3; i14++)
						if (aconto1[ai2[j12]].dist != aconto1[ai2[i14]].dist) {
							if (aconto1[ai2[j12]].dist < aconto1[ai2[i14]].dist)
								ai7[j12]++;
							else
								ai7[i14]++;
						} else if (i14 > j12)
							ai7[j12]++;
						else
							ai7[i14]++;

					ai10[ai7[j12]] = j12;
				}

				for (int k12 = 0; k12 < k3; k12++)
					aconto1[ai2[ai10[k12]]].d(rd);

				if (xtgraphics.starcnt == 0) {
					int l12 = 0;
					do {
						int j14 = 0;
						do {
							if (j14 != l12) {
								amadness[l12].colide(aconto1[l12], amadness[j14], aconto1[j14]);
							}
						} while (++j14 < 7);
					} while (++l12 < 7);
					l12 = 0;
					do
						amadness[l12].drive(u[l12], aconto1[l12], trackers, checkpoints);
					while (++l12 < 7);
					l12 = 0;
					do
						record.rec(aconto1[l12], l12, amadness[l12].squash, amadness[l12].lastcolido,
								amadness[l12].cntdest);
					while (++l12 < 7);
					checkpoints.checkstat(amadness, aconto1, record);
					l12 = 1;
					do
						u[l12].preform(amadness[l12], aconto1[l12], checkpoints, trackers);
					while (++l12 < 7);	
					
					l12 = 0;
					do{						
						if(justFixed[l12]){
							if(!polyfx[l12].rapidWireframe(aconto1[l12]))
								justFixed[l12] = false;
						}
					}while (++l12 < 7);					
					
					try {		
						/**
						 * get the nearest piece
						 */
						int nearId = Utility.nearestPiece(aconto1[0], aconto1);
						
						/**
						 * if nearId has changed and saveId is not zero (hasnt saved anything yet)
						 * reset the saved conto
						 */
						if(saveAnId != nearId && saveAnId != 0){
							polyfx1[saveAnId].reset(aconto1[saveAnId]);
							System.out.println("just reset " + saveAnId);
						}
						
						if(nearId != -1){
							/*
							 * it returned a good Id, save it
							 */
							saveAnId = nearId;
							
							/**
							 * perform effects
							 */
							if(!polyfx1[nearId].rapidBody(aconto1[nearId])){
								
							}
						}else{
							System.out.println("not on a piece");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}					
					
				} else {
					if (xtgraphics.starcnt == 130) {
						medium.adv = 1900;
						medium.zy = 40;
						medium.vxz = 70;
						rd.setColor(new Color(255, 255, 255));
						rd.fillRect(0, 0, 670, 400);
					}
					if (xtgraphics.starcnt != 0)
						xtgraphics.starcnt--;
				}
				if (xtgraphics.starcnt < 38) {
					if (view == 0) {
						medium.follow(aconto1[0], amadness[0].cxz, u[0].lookback);
						xtgraphics.stat(amadness, checkpoints, u[0], aconto1, polyfx, true);
						initMoto(amadness, 1, 25);
					}
					if (view == 1) {
						medium.around(aconto1[0], false);
						xtgraphics.stat(amadness, checkpoints, u[0], aconto1, polyfx, false);
					}
					if (view == 2) {
						medium.watch(aconto1[0], amadness[0].mxz);
						xtgraphics.stat(amadness, checkpoints, u[0], aconto1, polyfx, false);
					}
					if (mouses == 1) {
						u[0].enter = true;
						mouses = 0;
					}
					if (xtgraphics.starcnt == 36) {
						repaint();
						xtgraphics.blendude(offImage);
					}
				} else {
					medium.around(aconto1[3], true);
					if (u[0].enter || u[0].handb) {
						xtgraphics.starcnt = 38;
						u[0].enter = false;
						u[0].handb = false;
					}
					if (xtgraphics.starcnt == 38) {
						mouses = 0;
						medium.vert = false;
						medium.adv = 900;
						medium.vxz = 180;
						checkpoints.checkstat(amadness, aconto1, record);
						medium.follow(aconto1[0], amadness[0].cxz, 0);
						xtgraphics.stat(amadness, checkpoints, u[0], aconto1, polyfx, true);
						rd.setColor(new Color(255, 255, 255));
						rd.fillRect(0, 0, 670, 400);
					}
				}
			}
			if (xtgraphics.fase == -1) {
				if (k1 == 0) {
					int i4 = 0;
					do {
						record.ocar[i4] = new ContO(aconto1[i4], 0, 0, 0, 0);
						aconto1[i4] = new ContO(record.car[0][i4], 0, 0, 0, 0);
					} while (++i4 < 7);
				}
				medium.d(rd);
				int j4 = 0;
				int ai3[] = new int[100];
				for (int l6 = 0; l6 < nob; l6++)
					if (aconto1[l6].dist != 0) {
						ai3[j4] = l6;
						j4++;
					} else {
						aconto1[l6].d(rd);
					}

				int ai8[] = new int[j4];
				for (int i9 = 0; i9 < j4; i9++)
					ai8[i9] = 0;

				for (int j9 = 0; j9 < j4; j9++) {
					for (int i13 = j9 + 1; i13 < j4; i13++)
						if (aconto1[ai3[j9]].dist != aconto1[ai3[i13]].dist) {
							if (aconto1[ai3[j9]].dist < aconto1[ai3[i13]].dist)
								ai8[j9]++;
							else
								ai8[i13]++;
						} else if (i13 > j9)
							ai8[j9]++;
						else
							ai8[i13]++;

				}

				for (int k9 = 0; k9 < j4; k9++) {
					for (int j13 = 0; j13 < j4; j13++)
						if (ai8[j13] == k9)
							aconto1[ai3[j13]].d(rd);

				}

				if (u[0].enter || u[0].handb || mouses == 1) {
					k1 = 299;
					u[0].enter = false;
					u[0].handb = false;
					mouses = 0;
				}
				int l9 = 0;
				do {
					if (record.fix[l9] == k1)
						if (aconto1[l9].dist == 0)
							aconto1[l9].fcnt = 8;
						else
							aconto1[l9].fix = true;
					if (aconto1[l9].fcnt == 7 || aconto1[l9].fcnt == 8) {
						aconto1[l9] = new ContO(aconto[amadness[l9].cn], 0, 0, 0, 0);
						record.cntdest[l9] = 0;
					}
					if (k1 == 299)
						aconto1[l9] = new ContO(record.ocar[l9], 0, 0, 0, 0);
					record.play(aconto1[l9], amadness[l9], l9, k1);
				} while (++l9 < 7);
				if (++k1 == 300) {
					k1 = 0;
					xtgraphics.fase = -6;
				} else {
					xtgraphics.replyn();
				}
				medium.around(aconto1[0], false);
			}
			if (xtgraphics.fase == -2) {
				if (record.hcaught && record.wasted == 0 && record.whenwasted != 229 && checkpoints.stage <= 2
						&& xtgraphics.looped != 0)
					record.hcaught = false;
				if (record.hcaught) {
					if (medium.random() > 0.45000000000000001D)
						medium.vert = false;
					else
						medium.vert = true;
					medium.adv = (int) (900F * medium.random());
					medium.vxz = (int) (360F * medium.random());
					k1 = 0;
					xtgraphics.fase = -3;
					i2 = 0;
					j2 = 0;
				} else {
					k1 = -2;
					xtgraphics.fase = -4;
				}
			}
			if (xtgraphics.fase == -3) {
				if (k1 == 0) {
					if (record.wasted == 0) {
						if (record.whenwasted == 229) {
							k2 = 67;
							medium.vxz += 90;
						} else {
							k2 = (int) (medium.random() * 4F);
							if (k2 == 1 || k2 == 3)
								k2 = 69;
							if (k2 == 2 || k2 == 4)
								k2 = 30;
						}
					} else if (record.closefinish != 0 && j2 != 0)
						medium.vxz += 90;
					int k4 = 0;
					do
						aconto1[k4] = new ContO(record.starcar[k4], 0, 0, 0, 0);
					while (++k4 < 7);
				}
				medium.d(rd);
				int i5 = 0;
				int ai4[] = new int[100];
				for (int i7 = 0; i7 < nob; i7++)
					if (aconto1[i7].dist != 0) {
						ai4[i5] = i7;
						i5++;
					} else {
						aconto1[i7].d(rd);
					}

				int ai9[] = new int[i5];
				for (int i10 = 0; i10 < i5; i10++)
					ai9[i10] = 0;

				for (int j10 = 0; j10 < i5; j10++) {
					for (int k13 = j10 + 1; k13 < i5; k13++)
						if (aconto1[ai4[j10]].dist != aconto1[ai4[k13]].dist) {
							if (aconto1[ai4[j10]].dist < aconto1[ai4[k13]].dist)
								ai9[j10]++;
							else
								ai9[k13]++;
						} else if (k13 > j10)
							ai9[j10]++;
						else
							ai9[k13]++;

				}

				for (int k10 = 0; k10 < i5; k10++) {
					for (int l13 = 0; l13 < i5; l13++)
						if (ai9[l13] == k10)
							aconto1[ai4[l13]].d(rd);

				}

				int l10 = 0;
				do {
					if (record.hfix[l10] == k1)
						if (aconto1[l10].dist == 0)
							aconto1[l10].fcnt = 8;
						else
							aconto1[l10].fix = true;
					if (aconto1[l10].fcnt == 7 || aconto1[l10].fcnt == 8) {
						aconto1[l10] = new ContO(aconto[amadness[l10].cn], 0, 0, 0, 0);
						record.cntdest[l10] = 0;
					}
					record.playh(aconto1[l10], amadness[l10], l10, k1);
				} while (++l10 < 7);
				if (j2 == 2 && k1 == 299)
					u[0].enter = true;
				if (u[0].enter || u[0].handb) {
					xtgraphics.fase = -4;
					u[0].enter = false;
					u[0].handb = false;
					k1 = -7;
				} else {
					xtgraphics.levelhigh(record.wasted, record.whenwasted, record.closefinish, k1, checkpoints.stage);
					if (k1 == 0 || k1 == 1 || k1 == 2) {
						rd.setColor(new Color(0, 0, 0));
						rd.fillRect(0, 0, 670, 400);
					}
					if (record.wasted != 0) {
						if (record.closefinish == 0) {
							if (i2 == 9 || i2 == 11) {
								rd.setColor(new Color(255, 255, 255));
								rd.fillRect(0, 0, 670, 400);
							}
							if (i2 == 0)
								medium.around(aconto1[0], false);
							if (i2 > 0 && i2 < 20)
								medium.transaround(aconto1[0], aconto1[record.wasted], i2);
							if (i2 == 20)
								medium.around(aconto1[record.wasted], false);
							if (k1 > record.whenwasted && i2 != 20)
								i2++;
							if ((i2 == 0 || i2 == 20) && ++k1 == 300) {
								k1 = 0;
								i2 = 0;
								j2++;
							}
						} else if (record.closefinish == 1) {
							if (i2 == 0)
								medium.around(aconto1[0], false);
							if (i2 > 0 && i2 < 20)
								medium.transaround(aconto1[0], aconto1[record.wasted], i2);
							if (i2 == 20)
								medium.around(aconto1[record.wasted], false);
							if (i2 > 20 && i2 < 40)
								medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 20);
							if (i2 == 40)
								medium.around(aconto1[0], false);
							if (i2 > 40 && i2 < 60)
								medium.transaround(aconto1[0], aconto1[record.wasted], i2 - 40);
							if (i2 == 60)
								medium.around(aconto1[record.wasted], false);
							if (k1 > 160 && i2 < 20)
								i2++;
							if (k1 > 230 && i2 < 40)
								i2++;
							if (k1 > 280 && i2 < 60)
								i2++;
							if ((i2 == 0 || i2 == 20 || i2 == 40 || i2 == 60) && ++k1 == 300) {
								k1 = 0;
								i2 = 0;
								j2++;
							}
						} else {
							if (i2 == 0)
								medium.around(aconto1[0], false);
							if (i2 > 0 && i2 < 20)
								medium.transaround(aconto1[0], aconto1[record.wasted], i2);
							if (i2 == 20)
								medium.around(aconto1[record.wasted], false);
							if (i2 > 20 && i2 < 40)
								medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 20);
							if (i2 == 40)
								medium.around(aconto1[0], false);
							if (i2 > 40 && i2 < 60)
								medium.transaround(aconto1[0], aconto1[record.wasted], i2 - 40);
							if (i2 == 60)
								medium.around(aconto1[record.wasted], false);
							if (i2 > 60 && i2 < 80)
								medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 60);
							if (i2 == 80)
								medium.around(aconto1[0], false);
							if (k1 > 90 && i2 < 20)
								i2++;
							if (k1 > 160 && i2 < 40)
								i2++;
							if (k1 > 230 && i2 < 60)
								i2++;
							if (k1 > 280 && i2 < 80)
								i2++;
							if ((i2 == 0 || i2 == 20 || i2 == 40 || i2 == 60 || i2 == 80) && ++k1 == 300) {
								k1 = 0;
								i2 = 0;
								j2++;
							}
						}
					} else {
						if (k2 == 67 && (i2 == 3 || i2 == 31 || i2 == 66)) {
							rd.setColor(new Color(255, 255, 255));
							rd.fillRect(0, 0, 670, 400);
						}
						if (k2 == 69 && (i2 == 3 || i2 == 5 || i2 == 31 || i2 == 33 || i2 == 66 || i2 == 68)) {
							rd.setColor(new Color(255, 255, 255));
							rd.fillRect(0, 0, 670, 400);
						}
						if (k2 == 30 && i2 >= 1 && i2 < 30)
							if (i2 % (int) (2.0F + medium.random() * 3F) == 0 && !flag2) {
								rd.setColor(new Color(255, 255, 255));
								rd.fillRect(0, 0, 670, 400);
								flag2 = true;
							} else {
								flag2 = false;
							}
						if (k1 > record.whenwasted && i2 != k2)
							i2++;
						medium.around(aconto1[0], false);
						if ((i2 == 0 || i2 == k2) && ++k1 == 300) {
							k1 = 0;
							i2 = 0;
							j2++;
						}
					}
				}
			}
			if (xtgraphics.fase == -4) {
				if (k1 <= 0) {
					rd.drawImage(xtgraphics.mdness, 224, 30, null);
					rd.drawImage(xtgraphics.dude[0], 70, 10, null);
				}
				if (k1 >= 0)
					xtgraphics.fleximage(offImage, k1, checkpoints.stage);
				k1++;
				if (checkpoints.stage == 17 && k1 == 10)
					xtgraphics.fase = -5;
				if (k1 == 12)
					xtgraphics.fase = -5;
			}
			if (xtgraphics.fase == -6) {
				repaint();
				xtgraphics.pauseimage(offImage);
				xtgraphics.fase = -7;
				mouses = 0;
			}
			if (xtgraphics.fase == -7) {
				xtgraphics.pausedgame(checkpoints.stage, u[0], record);
				if (k1 != 0)
					k1 = 0;
				xtgraphics.ctachm(xm, ym, mouses, u[0]);
				if (mouses == 2)
					mouses = 0;
				if (mouses == 1)
					mouses = 2;
			}
			if (xtgraphics.fase == -8) {
				xtgraphics.cantreply();
				if (++k1 == 150 || u[0].enter || u[0].handb || mouses == 1) {
					xtgraphics.fase = -7;
					mouses = 0;
					u[0].enter = false;
					u[0].handb = false;
				}
			}
			if (lostfcs && xtgraphics.fase != 176 && xtgraphics.fase != 111) {
				if (xtgraphics.fase == 0)
					u[0].enter = true;
				else
					xtgraphics.nofocus();
				if (mouses == 1 || mouses == 2)
					lostfcs = false;
			}
			repaint();
			xtgraphics.playsounds(amadness[0], u[0], checkpoints.stage);
			date1 = new Date();
			long l5 = date1.getTime();
			if (xtgraphics.fase == 0 || xtgraphics.fase == -1 || xtgraphics.fase == -3) {
				if (!flag1) {
					f1 = f;
					flag1 = true;
					j1 = 0;
				}
				if (j1 == 10) {
					if (l5 - l3 < j) {
						f1 = (float) (f1 + 0.5D);
					} else {
						f1 = (float) (f1 - 0.5D);
						if (f1 < 5F)
							f1 = 5F;
					}
					if (xtgraphics.starcnt == 0)
						medium.adjstfade(f1);
					l3 = l5;
					j1 = 0;
				} else {
					j1++;
				}
			} else {
				if (flag1) {
					f = f1;
					flag1 = false;
					j1 = 0;
				}
				if (i1 == 0 || xtgraphics.fase != 176) {
					if (j1 == 10) {
						if (l5 - l3 < 400L) {
							f1 = (float) (f1 + 3.5D);
						} else {
							f1 = (float) (f1 - 3.5D);
							if (f1 < 5F)
								f1 = 5F;
						}
						l3 = l5;
						j1 = 0;
					} else {
						j1++;
					}
				} else {
					if (i1 == 79) {
						f1 = f;
						l3 = l5;
						j1 = 0;
					}
					if (j1 == 10) {
						if (l5 - l3 < j) {
							f1 += 5F;
						} else {
							f1 -= 5F;
							if (f1 < 5F)
								f1 = 5F;
						}
						l3 = l5;
						j1 = 0;
					} else {
						j1++;
					}
					if (i1 == 1)
						f = f1;
				}
			}
			if (exwist) {
				rd.dispose();
				xtgraphics.stopallnow();
				System.gc();
				gamer.stop();
				gamer = null;
			}
			long l2 = Math.round(f1) - (l5 - l4);
			if (l2 < i)
				l2 = i;
			try {
				Thread.sleep(l2);
			} catch (InterruptedException _ex) {
			}
		} while (true);
	}

	@Override
	public void init() {		
		/*
         * load some fonts
         */
        new FontHandler();
        
		offImage = createImage(670, 400);
		if (offImage != null) {
			sg = offImage.getGraphics();
			rd = ((Graphics2D) sg);
			rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			rd.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			rd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		}
	}

	private void addFile(File source, File[] files, String path) {
		try {
			File tmpZip = File.createTempFile(source.getName(), null);
			tmpZip.delete();
			if (!source.renameTo(tmpZip)) {
				throw new RuntimeException("Could not make temp file (" + source.getName() + ")");
			}
			byte[] buffer = new byte[4096];
			ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(source));
			for (int i = 0; i < files.length; i++) {
				InputStream in = new FileInputStream(files[i]);
				out.putNextEntry(new ZipEntry(path + files[i].getName()));
				for (int read = in.read(buffer); read > -1; read = in.read(buffer)) {
					out.write(buffer, 0, read);
				}
				out.closeEntry();
				in.close();
			}
			for (ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()) {
				if (!zipEntryMatch(ze.getName(), files, path)) {
					out.putNextEntry(ze);
					for (int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
						out.write(buffer, 0, read);
					}
					out.closeEntry();
				}
			}
			out.close();
			tmpZip.delete();
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean zipEntryMatch(String zeName, File[] files, String path) {
		for (int i = 0; i < files.length; i++) {
			if ((path + files[i].getName()).equals(zeName)) {
				return true;
			}
		}
		return false;
	}			

	public void catchlink(int i) {
		if (!lostfcs) {
			if (i == 0)
				if (xm > 0 && xm < 670 && ym > 110 && ym < 169 || xm > 210 && xm < 460 && ym > 240 && ym < 259) {
					setCursor(new Cursor(12));
					if (mouses == 2)
						try {
							URL url = new URL("javascript:radicalplay();");
							getAppletContext().showDocument(url);
						} catch (Exception _ex) {
						}
				} else {
					setCursor(new Cursor(0));
				}
			if (i == 1)
				if (xm > 0 && xm < 670 && ym > 205 && ym < 267) {
					setCursor(new Cursor(12));
					if (mouses == 2)
						try {
							URL url1 = new URL("javascript:radicalplay();");
							getAppletContext().showDocument(url1);
						} catch (Exception _ex) {
						}
				} else {
					setCursor(new Cursor(0));
				}
		}
	}

	@Override
	public boolean mouseMove(Event event, int i, int j) {
		if (!exwist && !lostfcs) {
			xm = i;
			ym = j;
		}
		return false;
	}	
}
