package com.radicalplay.nfm2;

import fallk.logmaster.HLogger;
import io.github.uwx.AppletPolyfill;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.URI;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * GameSparker brings everything together.
 *
 * @author Kaffeinated, Omar Waly
 */
public class GameSparker extends AppletPolyfill implements Runnable {
    /**
     * get os name / type
     */
    public static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_UNIX = OPERATING_SYSTEM.indexOf("nix") == 0 || OPERATING_SYSTEM.indexOf("nux") == 0;
    public static final boolean IS_WINDOWS = OPERATING_SYSTEM.indexOf("win") == 0;
    public static final boolean IS_MAC = OPERATING_SYSTEM.indexOf("mac") == 0;
    /**
     * get os bit
     */
    public static final String IS_64_BIT = System.getProperty("sun.arch.data.model").equals("64") ? "64" : "32";
    /**
     * uh help
     */
    public static final String WORKING_DIRECTORY = ".";
    public static final boolean DEBUG = true;

    private static final String[] carModels = {
            "2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", "leadoxide", "koolkat", "drifter",
            "policecops", "mustang", "king", "audir8", "masheen", "radicalone", "drmonster"
    };

    private static final String[] trackModels = {
            "road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", "nroad", "nturn",
            "roblend", "noblend", "rnblend", "roadend", "offroadend", "hpground", "ramp30", "cramp35", "dramp15",
            "dhilo15", "slide10", "takeoff", "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail",
            "thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", "uprise", "riseroad", "sroad",
            "soffroad", "tside", "launchpad", "thenet", "speedramp", "offhill", "slider", "uphill", "roll1", "roll2",
            "roll3", "roll4", "roll5", "roll6", "opile1", "opile2", "aircheckpoint", "tree1", "tree2", "tree3", "tree4",
            "tree5", "tree6", "tree7", "tree8", "cac1", "cac2", "cac3", "8sroad", "8soffroad", "singlewallroad", "thewall2"
    };
    private static final String[] extraModels = {};

    /**
     * false to disable splash
     */
    private static final boolean splashScreenState = true;

    private static final String stageDir = "data/stages/";

    public int stageID = 1;
    public static String stageSubDir = "nfm2/";
    public static String stageName = "";

    public String loadStage = stageDir + stageSubDir + stageID + ".txt";
    public static String loadStageCus;

    /**
     * Set directory for temporary creation of cookies (directory is deleted after
     * writing is complete)
     */
    private static final String cookieDirTemp = "data/cookies/";
    /**
     * Set location for the cookie.radq
     */
    private static final String cookieDirZip = "data/cookies.radq";

    private String stageError = "";

    private Graphics2D rd;
    private Graphics sg;
    private Image offImage;
    private Thread gamer;
    private final Control[] u;
    private int mouses;
    private int xm;
    private int ym;
    private boolean lostfcs;
    private boolean exwist;
    private int nob;
    private int notb;
    private int view;

    /* variables for screen shake */

    private int shaka = 0;
    private int apx = 0;
    private int apy = 0;

    public static String gameState = "None";
    public static int gameStateID;
    public static int ContosCount;

    public String wallmodel;

    public int noboffset = 10;      //this makes it so IDs are offset correctly, can be modified by stage via idoffset(x)
    public int nobfix = carModels.length - noboffset;

    public boolean reverseYRot = false;
    private xtGraphics xtgraphics;

    /**
     * <a href="http://www.expandinghead.net/keycode.html">http://www.expandinghead.net/keycode.html</a>
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean keyDown(Event event, int key) {
        if (!exwist) {
            if (key == Event.UP)
                u[0].up = true;
            if (key == Event.DOWN)
                u[0].down = true;
            if (key == Event.RIGHT)
                u[0].right = true;
            if (key == Event.LEFT)
                u[0].left = true;
            if (key == ' ')
                u[0].handb = true;
            if (key == 'x' || key == 'X')
                u[0].lookback = -1;
            if (key == 'z' || key == 'Z')
                u[0].lookback = 1;
            if (key == '\n' || key == 'P' || key == 'p' || key == Event.ESCAPE)
                u[0].enter = true;
            if (key == 'm' || key == 'M')
                Control.mutem = !Control.mutem;
            if (key == 'n' || key == 'N')
                Control.mutes = !Control.mutes;
            if (key == 'a' || key == 'A')
                u[0].arrace = !u[0].arrace;
            if (key == 'v' || key == 'V') {
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
            gamer = null;
        }
        exwist = true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean lostFocus(Event event, Object obj) {
        if (!exwist && !lostfcs) {
            lostfcs = true;
            mouses = 0;
            u[0].falseo();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean gotFocus(Event event, Object obj) {
        if (!exwist && lostfcs)
            lostfcs = false;
        return false;
    }

    private void savecookie(String filename, String num) {
        try {
            /**
             * since I want full control over the filenames, we'll create a normal file in
             * the temporary file directory
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

                HLogger.info("Successfully saved game (" + filename + ")");
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fromStream(InputStream in) throws IOException {
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
    private int readcookie(String string) {
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

            HLogger.info("Successfully read cookie " + string + " with value " + Integer.parseInt(fromEntry));
            return Integer.parseInt(fromEntry);
        } catch (IOException ioexception) {
            // HLogger.error(ioexception.toString());
            HLogger.error(string + ".dat probably doesn't exist");
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
                graphics2d.fillRect(apx + i, apy - (int) (25.0F), Math.abs(i), (int) (720.0F));
            } else {
                graphics2d.fillRect(apx + (int) (1280.0F), apy - (int) (25.0F), i, (int) (720.0F));
            }
        }
        if (i_98_ != 0) {
            if (i_98_ < 0) {
                graphics2d.fillRect(apx - (int) (25.0F), apy + i_98_, (int) (1280.0F + 50.F), Math.abs(i_98_));
            } else {
                graphics2d.fillRect(apx - (int) (25.0F), apy + (int) (720.0F + 50.0F), (int) (1280.0F + 50.0F), i_98_);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;

        graphics2d.setColor(new Color(0, 0, 0));
        graphics2d.fillRect(0, 0, getWidth(), getHeight());

        int xshake = 0;
        int yshake = 0;
        if (shaka > 10) {
            xshake = (int) ((double) (shaka * 2) * Math.random() - (double) shaka);
            yshake = (int) ((double) (shaka * 2) * Math.random() - (double) shaka);
            shaka -= 5;
        }

        if (!useUnlimitedResize) {
            apx = (int) ((getWidth() / 2f) - (GameFacts.screenWidth / 2f));
            apy = (int) ((getHeight() / 2f) - (GameFacts.screenHeight / 2f));
        } else {
            apx = 0;
            apy = 0;
        }
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(offImage, apx + xshake, apy + yshake, null);
        cropit(graphics2d, xshake, yshake);
    }

    public GameSparker() {
        u = new Control[51];
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
     * @return Position on model in array. If you spelled it wrong or if it doesn't
     *         eist, it returns -1, so you have that to look forward to.
     * @author Kaffeinated
     */
    private int getModel(String input) {

        String[][] allModels = new String[][]{
                carModels, trackModels, extraModels
        }; /// need to have all the model arrays here

        int modelId = 0;

        for (int i = 0; i < allModels.length; i++) {
            for (int j = 0; j < allModels[i].length; j++) {
                if (Objects.equals(input, allModels[i][j])) {
                    int addWhat = 0;
                    if (i == 1) {
                        addWhat = carModels.length;
                    }
                    if (i == 2) {
                        addWhat = carModels.length + trackModels.length;
                    }
                    modelId = j + addWhat;
                    //HLogger.info("Found model " + modelId + " matching string \"" + input + "\"");
                    return modelId;
                }
            }
        }
        HLogger.warn("No results for getModel | check you're speling and grammer");
        return -1;
    }

    /**
     * Loads all models
     *
     * @param conto      conto instance
     * @param trackers   trackers instance
     * @param xtgraphics xtgraphics instance
     * @author Kaffeinated, Omar Waly
     */
    private void loadbase(final ContO[] conto, Trackers trackers, xtGraphics xtgraphics) {
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
                conto[modelId] = new ContO(modelData, trackers);
                xtgraphics.dnload++;
            }
            /*
             * be sure to add your added arrays here            
             */
            HLogger.info("Contos loaded: " + (carModels.length + trackModels.length + extraModels.length));
            ContosCount = carModels.length + trackModels.length + extraModels.length;
            zipinputstream.close();
        } catch (IOException e) {
            HLogger.error("Error Reading Models: " + e);
            e.printStackTrace();
        }
        System.gc();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * <a href="http://www.expandinghead.net/keycode.html">http://www.expandinghead.net/keycode.html</a>
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean keyUp(Event event, int i) {
        if (!exwist) {
            if (i == Event.UP)
                u[0].up = false;
            if (i == Event.DOWN)
                u[0].down = false;
            if (i == Event.RIGHT)
                u[0].right = false;
            if (i == Event.LEFT)
                u[0].left = false;
            if (i == ' ')
                u[0].handb = false;
            if (i == 'x' || i == 'X' || i == 'z' || i == 'Z')
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean mouseDown(Event event, int x, int y) {
        if (!exwist && mouses == 0) {
            xm = x - this.apx;
            ym = y - this.apy;
            mouses = 1;
        }
        return false;
    }

    /**
     * Loads stage elements
     *
     * @param aconto      conto instance
     * @param aconto1     conto instance specifically for cars
     * @param trackers    trackers instance
     * @param checkpoints checkpoints instance
     * @param xtgraphics  xtgraphics instance
     * @param amadness    madness instance
     * @param record      record instance
     * @author Kaffeinated, Omar Waly
     */
    private void loadstage(ContO[] aconto, ContO[] aconto1, Trackers trackers, CheckPoints checkpoints,
                           xtGraphics xtgraphics, Madness[] amadness, Record record, boolean custom) {
        trackers.nt = 0;
        nob = GameFacts.numberOfPlayers;
        notb = 0;
        checkpoints.n = 0;
        checkpoints.nsp = 0;
        checkpoints.fn = 0;
        checkpoints.haltall = false;
        checkpoints.wasted = 0;
        checkpoints.catchfin = 0;
        Medium.lightson = false;
        Medium.detailtype = 2;
        Medium.ground = 250;

        reverseYRot = false;

        noboffset = 10;
        nobfix = carModels.length - noboffset;

        wallmodel = "thewall";

        //reset noarrow and nostatus
        xtgraphics.arrowDisabled = false;
        xtgraphics.opstatusDisabled = false;

        view = 0;

        int r_wall = 0;
        int l_wall = 100;
        int t_wall = 0;
        int b_wall = 100;

        CheckPoints.customTrack = false;

        loadStage = stageDir + stageSubDir + checkpoints.stage + ".txt";
        if (xtgraphics.nfmmode == 1) {
            stageSubDir = "nfm1/";
        } else if (xtgraphics.nfmmode == 2) {
            stageSubDir = "nfm2/";
        }

        if (custom) {
            loadStage = stageDir + loadStageCus + ".txt";
            HLogger.info(loadStage);
        }

        String string = "";

        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(new File(loadStage)))) {
            for (String line; (line = bufferedreader.readLine()) != null;) {
                line = line.trim();

                if (line.startsWith("mountains"))
                    Medium.mgen = Utility.getint("mountains", line, 0);
                if (line.startsWith("snap"))
                    Medium.setSnap(Utility.getint("snap", line, 0), Utility.getint("snap", line, 1),
                            Utility.getint("snap", line, 2));
                if (line.startsWith("sky")) {
                    Medium.setSky(Utility.getint("sky", line, 0), Utility.getint("sky", line, 1),
                            Utility.getint("sky", line, 2));
                    xtgraphics.snap(checkpoints.stage);
                }
                if (line.startsWith("ground"))
                    Medium.setGround(Utility.getint("ground", line, 0), Utility.getint("ground", line, 1),
                            Utility.getint("ground", line, 2));
                if (line.startsWith("polys"))
                    Medium.setPolys(Utility.getint("polys", line, 0), Utility.getint("polys", line, 1),
                            Utility.getint("polys", line, 2));
                if (line.startsWith("fog"))
                    Medium.setFade(Utility.getint("fog", line, 0), Utility.getint("fog", line, 1),
                            Utility.getint("fog", line, 2));
                if (line.startsWith("density"))
                    Medium.fogd = Utility.getint("density", line, 0);
                if (line.startsWith("texture")) {
                    Medium.setTexture(Utility.getint("texture", line, 0), Utility.getint("texture", line, 1),
                            Utility.getint("texture", line, 2),
                            Utility.getint("texture", line, 3));
                }
                if (line.startsWith("clouds")) {
                    Medium.setClouds(Utility.getint("clouds", line, 0), Utility.getint("clouds", line, 1),
                            Utility.getint("clouds", line, 2),
                            Utility.getint("clouds", line, 3), Utility.getint("clouds", line, 4));
                }
                if (line.startsWith("noclouds"))
                    Medium.noclouds = true;
                if (line.startsWith("fadefrom")) {
                    Medium.fadeFrom(Utility.getint("fadefrom", line, 0));
                    Medium.origfade = Medium.fade[0];
                }
                if (line.startsWith("lightson"))
                    Medium.lightson = true;

                if (line.startsWith("noarrow"))
                    xtgraphics.arrowDisabled = true;
                if (line.startsWith("nostatus"))
                    xtgraphics.opstatusDisabled = true;

                if (line.startsWith("idoffset")) {
                    noboffset = Utility.getint("idoffset", line, 0);
                    nobfix = carModels.length - noboffset;
                }

                if (line.startsWith("yrot")) {
                    reverseYRot = true;
                }

                if (line.startsWith("set")) {
                    int k1 = Utility.getint("set", line, 0);
                    k1 += nobfix;
                
                    // compute default Y (ground-height)
                    int yVal = Medium.ground - aconto1[k1].grat;
                    int rot = Utility.getint("set", line, 3);
                
                    // if there *is* a 5th comma-separated value, use that instead
                    // (splitting only the part inside the parentheses)
                    String inside = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')'));
                    String[] parts = inside.split("\\s*,\\s*");
                    if (parts.length > 4) {
                        yVal = Utility.getint("set", line, 4);
                        if (reverseYRot) {
                            yVal = Utility.getint("set", line, 3);
                            rot = Utility.getint("set", line, 4);
                        }
                    }
                
                    // now create the object, exactly as before but with our yVal
                    aconto[nob] = new ContO(
                        aconto1[k1],
                        Utility.getint("set", line, 1),
                        yVal,
                        Utility.getint("set", line, 2),
                        rot
                    );
                
                    if (line.contains(")p")) {
                        checkpoints.x[checkpoints.n] = Utility.getint("set", line, 1);
                        checkpoints.z[checkpoints.n] = Utility.getint("set", line, 2);
                
                        // same trick for the checkpoint Y (default=0)
                        if (parts.length > 4) {
                            checkpoints.y[checkpoints.n] = Utility.getint("set", line, 4);
                            if (reverseYRot) {
                                checkpoints.y[checkpoints.n] = Utility.getint("set", line, 3);
                            }
                        } else {
                            checkpoints.y[checkpoints.n] = 0;
                        }
                
                        checkpoints.typ[checkpoints.n] = 0;
                        if (line.contains(")pt")) checkpoints.typ[checkpoints.n] = -1;
                        if (line.contains(")pr")) checkpoints.typ[checkpoints.n] = -2;
                        if (line.contains(")pl")) checkpoints.typ[checkpoints.n] = -2;
                        if (line.contains(")po")) checkpoints.typ[checkpoints.n] = -3;
                        if (line.contains(")ph")) checkpoints.typ[checkpoints.n] = -4;
                
                        checkpoints.n++;
                        notb = nob + 1;
                    }
                    nob++;
                }
                if (line.startsWith("wall")) {
                    String modelname = Utility.getstring("wall", line, 0);
                    wallmodel = modelname;
                }
                if (line.startsWith("ds:set")) {
                    String modelname = Utility.getstring("ds:set", line, 0);
                    int id = getModel(modelname);
                    int yVal = Medium.ground - aconto1[id].grat;
                
                    // if there *is* a 5th comma-separated value, use that instead
                    // (splitting only the part inside the parentheses)
                    String inside = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')'));
                    String[] parts = inside.split("\\s*,\\s*");
                    if (parts.length > 4) {
                        yVal = Utility.getint("ds:set", line, 4);
                    }
                
                    // now create the object, exactly as before but with our yVal
                    aconto[nob] = new ContO(
                        aconto1[id],
                        Utility.getint("ds:set", line, 1),
                        yVal,
                        Utility.getint("ds:set", line, 2),
                        Utility.getint("ds:set", line, 3)
                    );
                
                    if (line.contains(")p")) {
                        checkpoints.x[checkpoints.n] = Utility.getint("ds:set", line, 1);
                        checkpoints.z[checkpoints.n] = Utility.getint("ds:set", line, 2);
                
                        // same trick for the checkpoint Y (default=0)
                        if (parts.length > 4) {
                            checkpoints.y[checkpoints.n] = Utility.getint("ds:set", line, 4);
                        } else {
                            checkpoints.y[checkpoints.n] = 0;
                        }
                
                        checkpoints.typ[checkpoints.n] = 0;
                        if (line.contains(")pt")) checkpoints.typ[checkpoints.n] = -1;
                        if (line.contains(")pr")) checkpoints.typ[checkpoints.n] = -2;
                        if (line.contains(")pl")) checkpoints.typ[checkpoints.n] = -2;
                        if (line.contains(")po")) checkpoints.typ[checkpoints.n] = -3;
                        if (line.contains(")ph")) checkpoints.typ[checkpoints.n] = -4;
                
                        checkpoints.n++;
                        notb = nob + 1;
                    }
                    nob++;
                }
                if (line.startsWith("chk")) {
                    int l1 = Utility.getint("chk", line, 0);
                    l1 += nobfix;
                
                    // compute default Y (ground-height)
                    int yVal = Medium.ground - aconto1[l1].grat;
                    int rot = Utility.getint("chk", line, 3);
                
                    // grab the args between '(' and ')', split on commas
                    String inside = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')'));
                    String[] parts = inside.split("\\s*,\\s*");
                    // if there's a 5th element, use it as Y instead
                    if (parts.length > 4) {
                        yVal = Utility.getint("chk", line, 4);
                        if (reverseYRot) {
                            yVal = Utility.getint("chk", line, 3);
                            rot = Utility.getint("chk", line, 4);
                        }
                    }
                
                    // create your object exactly as before, but with our yVal
                    aconto[nob] = new ContO(
                        aconto1[l1],
                        Utility.getint("chk", line, 1),
                        yVal,
                        Utility.getint("chk", line, 2),
                        rot
                    );
                
                    // now the checkpoint data:
                    checkpoints.x[checkpoints.n] = Utility.getint("chk", line, 1);
                    checkpoints.z[checkpoints.n] = Utility.getint("chk", line, 2);
                    // use the same yVal
                    checkpoints.y[checkpoints.n] = yVal;
                
                    // type based on rotation-arg logic unchanged
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
                if (line.startsWith("fix")) {
                    int i2 = Utility.getint("fix", line, 0);
                    i2 += nobfix;
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
                    checkpoints.special[checkpoints.fn] = line.contains(")s");
                    checkpoints.fn++;
                    nob++;
                    notb = nob;
                }
                if (line.startsWith("nlaps"))
                    checkpoints.nlaps = Utility.getint("nlaps", line, 0);
                if (line.startsWith("name"))
                    checkpoints.name = Utility.getstring("name", line, 0).replace('|', ',');
                if (line.startsWith("soundtrack")) {
                    CheckPoints.customTrack = true;
                    CheckPoints.trackname = Utility.getstring("soundtrack", line, 0);
                    CheckPoints.trackformat = Utility.getstring("soundtrack", line, 1);
                    // xtGraphics.sndsize[18] = Utility.getint("soundtrack", string, 2);
                }

                int wall_id = getModel(wallmodel);

                if (line.startsWith("maxr")) {
                    int j2 = Utility.getint("maxr", line, 0);
                    int j3 = Utility.getint("maxr", line, 1);
                    r_wall = j3;
                    int j4 = Utility.getint("maxr", line, 2);
                    for (int j5 = 0; j5 < j2; j5++) {
                        aconto[nob] = new ContO(aconto1[wall_id], j3, Medium.ground - aconto1[wall_id].grat,
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
                        aconto[nob] = new ContO(aconto1[wall_id], k3, Medium.ground - aconto1[wall_id].grat,
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
                        aconto[nob] = new ContO(aconto1[wall_id], l5 * 4800 + l4, Medium.ground - aconto1[wall_id].grat,
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
                        aconto[nob] = new ContO(aconto1[wall_id], i6 * 4800 + i5, Medium.ground - aconto1[wall_id].grat,
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
                if (line.startsWith("detail")) {
                    Medium.detailtype = Utility.getint("detail", line, 0);
                }
            }
            Medium.newpolys(l_wall, r_wall - l_wall, b_wall, t_wall - b_wall, trackers, notb);
            Medium.newmountains(l_wall, r_wall, b_wall, t_wall);
            Medium.newclouds(l_wall, r_wall, b_wall, t_wall);
            Medium.newstars();
        } catch (IOException e) {
            String exceptStr = e.toString();
            final int maxChar = 30;

            int maxLength = (exceptStr.length() < maxChar) ? exceptStr.length() : maxChar;
            stageError = e.toString().substring(0, maxLength) + "...";

            xtgraphics.fase = Phase.ERRORLOADINGSTAGE;
            HLogger.error("Error loading stage " + checkpoints.stage);
            e.printStackTrace();
        }
        if (checkpoints.stage == 16)
            Medium.lightn = 0;
        else
            Medium.lightn = -1;
        Medium.nochekflk = checkpoints.stage != 1;
        if (xtgraphics.fase == Phase.STAGESELECTTRIGGER) {
            Medium.trx = 0L;
            Medium.trz = 0L;
            if (trackers.nt >= 4) {
                int i1 = 4;
                do {
                    Medium.trx += trackers.x[trackers.nt - i1];
                    Medium.trz += trackers.z[trackers.nt - i1];
                } while (--i1 > 0);
            }
            Medium.trx = Medium.trx / 4L;
            Medium.trz = Medium.trz / 4L;
            Medium.ptr = 0;
            Medium.ptcnt = -10;
            Medium.hit = 45000;
            Medium.fallen = 0;
            Medium.nrnd = 0;
            Medium.trk = true;
            //Medium.detailtype = 0;
            xtgraphics.fase = Phase.STAGESELECT;
            mouses = 0;
        }
        int j1 = 0;
        do
            u[j1].reset(checkpoints, xtgraphics.sc[j1]);
        while (++j1 < GameFacts.numberOfPlayers);
        xtgraphics.resetstat(checkpoints.stage);
        j1 = 0;
        do {
            if (j1 % 3 == 0) {
                aconto[j1] = new ContO(aconto1[xtgraphics.sc[j1]], 0, 250 - aconto1[xtgraphics.sc[j1]].grat,
                        -760 + ((j1 / 3) * 760), 0);
            }
            if (j1 % 3 == 1) {
                aconto[j1] = new ContO(aconto1[xtgraphics.sc[j1]], -350, 250 - aconto1[xtgraphics.sc[j1]].grat,
                        -380 + ((int) (j1 / 3) * 760), 0);
            }
            if (j1 % 3 == 2) {
                aconto[j1] = new ContO(aconto1[xtgraphics.sc[j1]], 350, 250 - aconto1[xtgraphics.sc[j1]].grat,
                        -380 + ((int) (j1 / 3) * 760), 0);
            }
            amadness[j1].reseto(xtgraphics.sc[j1], aconto[j1], checkpoints);
        } while (++j1 < GameFacts.numberOfPlayers);
        record.reset(aconto);
        System.gc();
    }

    /**
     * motion
     *
     * @param amadness madness
     * @param shakeAmt shake sensitivity
     *                 1 normal
     *                 20 maximum
     * @param maxAmt   maximum displacement of the screen while shaking
     */
    private void initMoto(Madness[] amadness, int shakeAmt, int maxAmt) {
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
        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
        repaint();
        /*
         * start an example timer
         */
        Utility.startTimer();
        Trackers trackers = new Trackers();
        CheckPoints checkpoints = new CheckPoints();
        xtgraphics = new xtGraphics(rd, this);
        xtgraphics.loaddata();
        Record record = new Record();
        ContO[] aconto = new ContO[carModels.length + trackModels.length + extraModels.length]; // be sure all your arrays get in here
        loadbase(aconto, trackers, xtgraphics);
        ContO[] aconto1 = new ContO[GameFacts.maxTrackPieces];
        Madness[] amadness = new Madness[51];
        int l = 0;
        do {
            amadness[l] = new Madness(record, xtgraphics, l);
            u[l] = new Control();
        } while (++l < 51); // dont touch this
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
        if (l >= 1 && l <= GameFacts.numberOfStages) {
            /*
             * Note: that is an L
             */
            xtgraphics.unlocked = l;
            if (xtgraphics.unlocked != GameFacts.numberOfStages)
                checkpoints.stage = xtgraphics.unlocked;
            else
                checkpoints.stage = (int) (RadicalRand.random() * GameFacts.numberOfStages) + 1;
            xtgraphics.opselect = 0;
        }
        l = readcookie("usercar");
        if (l >= 0 && l <= GameFacts.numberOfCars - 1)
            xtgraphics.sc[0] = l;
        l = readcookie("gameprfact");
        if (l != -1) {
            f = l;
            i1 = 1;
        }

        xtgraphics.stoploading();
        System.gc();
        Date date = new Date();
        int i = 15;
        int j = 530;
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
            if (xtgraphics.fase == Phase.LOADING) {
                if (mouses == 1)
                    i2 = 800;
                if (i2 < 800) {
                    xtgraphics.clicknow();
                    i2++;
                } else {
                    i2 = 0;
                    xtgraphics.fase = Phase.AWAITLOADDISMISSAL;
                    xtgraphics.sm.play("powerup");
                    mouses = 0;
                    lostfcs = false;
                }
            }
            if (xtgraphics.fase == Phase.AWAITLOADDISMISSAL)
                if (i2 < 100 && splashScreenState) {
                    xtgraphics.rad(i2);
                    catchlink(0, xtgraphics);
                    if (mouses == 2)
                        mouses = 0;
                    if (mouses == 1)
                        mouses = 2;
                    i2++;
                } else {
                    i2 = 0;
                    xtgraphics.fase = Phase.MAINMENU;
                    mouses = 0;
                    u[0].falseo();
                }
            if (xtgraphics.fase == Phase.CARSELECTTRIGGER)
                if (i2 < 2) {
                    rd.setColor(new Color(0, 0, 0));
                    rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                    i2++;
                } else {
                    xtgraphics.inishcarselect();
                    i2 = 0;
                    xtgraphics.fase = Phase.CARSELECT;
                    mouses = 0;
                }
            if (xtgraphics.fase == Phase.CREDITS) {
                xtgraphics.credits(u[0]);
                if (xtgraphics.flipo == 102) {
                    rd.drawImage(xtgraphics.credsnap(offImage), 0, 0, null);
                }
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (xtgraphics.flipo <= 100)
                    catchlink(0, xtgraphics);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.MAINMENU) {
                xtgraphics.maini(u[0], checkpoints, amadness, aconto, aconto1);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.INSTRUCTIONS) {
                xtgraphics.inst(u[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.CUSTOMSETTINGS) { // settings menu
                xtgraphics.menusettings(u[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.POSTGAMEHANDOVER) {
                xtgraphics.fase = Phase.POSTGAME;
            }
            if (xtgraphics.fase == Phase.SAVEGAME) { // save the fucking game properly

                if (checkpoints.stage == xtgraphics.unlocked && xtgraphics.winner
                        && xtgraphics.unlocked != GameFacts.numberOfStages + 1)
                    savecookie("unlocked", "" + xtgraphics.unlocked);
                savecookie("gameprfact", "" + (int) f);
                savecookie("usercar", "" + xtgraphics.sc[0]);

                xtgraphics.fase = Phase.MAINMENU;
            }
            if (xtgraphics.fase == Phase.POSTGAME) {
                xtgraphics.finish(checkpoints, aconto, u[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (checkpoints.stage == GameFacts.numberOfStages && xtgraphics.winner)
                    catchlink(1, xtgraphics);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.CARSELECT) {
                xtgraphics.carselect(u[0], aconto, amadness[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.PREGAME) {
                xtgraphics.musicomp(checkpoints.stage, u[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.SELECTEDCARSAVE) {
                savecookie("usercar", "" + xtgraphics.sc[0]);

                for (int x = 0; x < GameFacts.numberOfPlayers; x++) {
                    amadness[x].stat = new Stat(xtgraphics.sc[x]);
                }
                xtgraphics.fase = Phase.PREGAMEMUSIC;
            }
            if (xtgraphics.fase == Phase.PREGAMEMUSIC) {
                xtgraphics.loadmusic(checkpoints.stage, i1);
            }
            if (xtgraphics.fase == Phase.LOCKEDSTAGE) {
                xtgraphics.cantgo(u[0]);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.ERRORLOADINGSTAGE) {
                xtgraphics.loadingfailed(checkpoints, u[0], stageError);
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.NPLAYERSCHECK) {
                xtgraphics.carspergame(checkpoints);
            }
            if (xtgraphics.fase == Phase.STAGESELECTTRIGGER) {
                xtgraphics.loadingstage(checkpoints.stage);
                loadstage(aconto1, aconto, trackers, checkpoints, xtgraphics, amadness, record, false);
                u[0].falseo();
            }
            if (xtgraphics.fase == Phase.LOADSTAGE) { // for custom stage loading
                repaint();
                loadstage(aconto1, aconto, trackers, checkpoints, xtgraphics, amadness, record, true);
                xtgraphics.fase = Phase.STAGESELECT;
            }
            if (xtgraphics.fase == Phase.LOADSTAGE2) { // for custom stage loading
                repaint();
                loadstage(aconto1, aconto, trackers, checkpoints, xtgraphics, amadness, record, true);
                xtgraphics.loadmusic(checkpoints.stage, i1);
            }
            if (xtgraphics.fase == Phase.STAGESELECT) {
                rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                xtgraphics.trackbg(true);
                Medium.d(rd);
                Medium.aroundTrack(checkpoints);
                int i3 = 0;
                int[] ai = new int[200];
                for (int k5 = GameFacts.numberOfPlayers; k5 < notb; k5++)
                    if (aconto1[k5].dist != 0) {
                        ai[i3] = k5;
                        i3++;
                    } else {
                        aconto1[k5].d(rd);
                    }

                int[] ai5 = new int[i3];
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
            if (xtgraphics.fase == Phase.DRAWENVIRONMENT) {
                Medium.d(rd);
                int j3 = 0;
                int[] ai1 = new int[200];
                for (int i6 = 0; i6 < nob; i6++)
                    if (aconto1[i6].dist != 0) {
                        ai1[j3] = i6;
                        j3++;
                    } else {
                        aconto1[i6].d(rd);
                    }

                int[] ai6 = new int[j3];
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

                Medium.follow(aconto1[0], 0, 0);
                xtgraphics.hipnoload(checkpoints.stage, false);
                if (i1 != 0) {
                    i1--;
                } else {
                    u[0].enter = false;
                    u[0].handb = false;

                    setCursor(new Cursor(0));
                    xtgraphics.fase = Phase.PREGAME;
                }
            }
            if (xtgraphics.fase == Phase.INGAME) {
                int k3 = 0;
                do {
                    if (amadness[k3].newcar) {
                        int j5 = aconto1[k3].xz;
                        int j6 = aconto1[k3].xy;
                        int l8 = aconto1[k3].zy;
                        aconto1[k3] = new ContO(aconto1[k3].original, aconto1[k3].x, aconto1[k3].y, aconto1[k3].z,
                                0);
                        aconto1[k3].xz = j5;
                        aconto1[k3].xy = j6;
                        aconto1[k3].zy = l8;
                        amadness[k3].newcar = false;
                    }
                } while (++k3 < GameFacts.numberOfPlayers);
                Medium.d(rd);
                k3 = 0;
                int[] ai2 = new int[200];
                for (int k6 = 0; k6 < nob; k6++)
                    if (aconto1[k6].dist != 0) {
                        ai2[k3] = k6;
                        k3++;
                    } else {
                        aconto1[k6].d(rd);
                    }

                int[] ai7 = new int[k3];
                int[] ai10 = new int[k3];
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
                        } while (++j14 < GameFacts.numberOfPlayers);
                    } while (++l12 < GameFacts.numberOfPlayers);
                    l12 = 0;
                    do
                        amadness[l12].drive(u[l12], aconto1[l12], trackers, checkpoints);
                    while (++l12 < GameFacts.numberOfPlayers);
                    l12 = 0;
                    do
                        record.rec(aconto1[l12], l12, amadness[l12].squash, amadness[l12].lastcolido,
                                amadness[l12].cntdest);
                    while (++l12 < GameFacts.numberOfPlayers);
                    checkpoints.checkstat(amadness, aconto1, record, GameFacts.numberOfPlayers);
                    l12 = 1;
                    do
                        u[l12].preform(amadness[l12], aconto1[l12], checkpoints, trackers, GameFacts.numberOfPlayers);
                    while (++l12 < GameFacts.numberOfPlayers);
                } else {
                    if (xtgraphics.starcnt == 130) {
                        Medium.adv = 1900;
                        Medium.zy = 40;
                        Medium.vxz = 70;
                        rd.setColor(new Color(255, 255, 255));
                        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                    }
                    if (xtgraphics.starcnt != 0)
                        xtgraphics.starcnt--;
                }
                if (xtgraphics.starcnt < 38) {
                    if (view == 0) {
                        Medium.follow(aconto1[xtgraphics.spectate], amadness[xtgraphics.spectate].cxz,
                                u[xtgraphics.spectate].lookback);
                        xtgraphics.stat(amadness, checkpoints, u[xtgraphics.spectate], aconto1, true);
                        initMoto(amadness, 2, 5);
                    }
                    if (view == 1) {
                        Medium.around(aconto1[xtgraphics.spectate], false);
                        xtgraphics.stat(amadness, checkpoints, u[xtgraphics.spectate], aconto1, false);
                    }
                    if (view == 2) {
                        Medium.watch(aconto1[xtgraphics.spectate], amadness[0].mxz);
                        xtgraphics.stat(amadness, checkpoints, u[xtgraphics.spectate], aconto1, false);
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
                    if (GameFacts.numberOfPlayers < 5)
                        Medium.around(aconto1[0], true);
                    else
                        Medium.around(aconto1[3], true);
                    if (u[0].enter || u[0].handb) {
                        xtgraphics.starcnt = 38;
                        u[0].enter = false;
                        u[0].handb = false;
                    }
                    if (xtgraphics.starcnt == 38) {
                        mouses = 0;
                        Medium.vert = false;
                        Medium.adv = GameFacts.screenWidth;
                        Medium.vxz = 180;
                        checkpoints.checkstat(amadness, aconto1, record, GameFacts.numberOfPlayers);
                        Medium.follow(aconto1[0], amadness[0].cxz, 0);
                        xtgraphics.stat(amadness, checkpoints, u[0], aconto1, true);
                        rd.setColor(new Color(255, 255, 255));
                        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                    }
                }
            }
            if (xtgraphics.fase == Phase.INSTANTREPLAY) {
                if (k1 == 0) {
                    int i4 = 0;
                    do {
                        record.ocar[i4] = new ContO(aconto1[i4], 0, 0, 0, 0);
                        aconto1[i4] = new ContO(record.car[0][i4], 0, 0, 0, 0);
                    } while (++i4 < GameFacts.numberOfPlayers);
                }
                Medium.d(rd);
                int j4 = 0;
                int[] ai3 = new int[100];
                for (int l6 = 0; l6 < nob; l6++)
                    if (aconto1[l6].dist != 0) {
                        ai3[j4] = l6;
                        j4++;
                    } else {
                        aconto1[l6].d(rd);
                    }

                int[] ai8 = new int[j4];
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
                        aconto1[l9] = new ContO(aconto1[l9].original, 0, 0, 0, 0);
                        record.cntdest[l9] = 0;
                    }
                    if (k1 == 299)
                        aconto1[l9] = new ContO(aconto1[l9].original, 0, 0, 0, 0);
                    record.play(aconto1[l9], amadness[l9], l9, k1);
                } while (++l9 < GameFacts.numberOfPlayers);
                if (++k1 == 300) {
                    k1 = 0;
                    xtgraphics.fase = Phase.PAUSETRIGGER;
                } else {
                    xtgraphics.replyn();
                }
                Medium.around(aconto1[0], false);
            }
            if (xtgraphics.fase == Phase.CAUGHTHIGHLIGHT) {
                if (record.hcaught && record.wasted == 0 && record.whenwasted != 229 && checkpoints.stage <= 2
                        && xtgraphics.looped != 0)
                    record.hcaught = false;
                if (record.hcaught) {
                    Medium.vert = Medium.random() <= 0.45000000000000001D;
                    Medium.adv = (int) (900F * Medium.random());
                    Medium.vxz = (int) (360F * Medium.random());
                    k1 = 0;
                    xtgraphics.fase = Phase.GAMEHIGHLIGHT;
                    i2 = 0;
                    j2 = 0;
                } else {
                    k1 = -2;
                    xtgraphics.fase = Phase.POSTGAMEFADEOUT;
                }
            }
            if (xtgraphics.fase == Phase.GAMEHIGHLIGHT) {
                if (k1 == 0) {
                    if (record.wasted == 0) {
                        if (record.whenwasted == 229) {
                            k2 = 67;
                            Medium.vxz += 90;
                        } else {
                            k2 = (int) (Medium.random() * 4F);
                            if (k2 == 1 || k2 == 3)
                                k2 = 69;
                            if (k2 == 2 || k2 == 4)
                                k2 = 30;
                        }
                    } else if (record.closefinish != 0 && j2 != 0)
                        Medium.vxz += 90;
                    int k4 = 0;
                    do
                        aconto1[k4] = new ContO(record.starcar[k4], 0, 0, 0, 0);
                    while (++k4 < GameFacts.numberOfPlayers);
                }
                Medium.d(rd);
                int i5 = 0;
                int[] ai4 = new int[100];
                for (int i7 = 0; i7 < nob; i7++)
                    if (aconto1[i7].dist != 0) {
                        ai4[i5] = i7;
                        i5++;
                    } else {
                        aconto1[i7].d(rd);
                    }

                int[] ai9 = new int[i5];
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
                        aconto1[l10] = new ContO(aconto1[l10].original, 0, 0, 0, 0);
                        record.cntdest[l10] = 0;
                    }
                    record.playh(aconto1[l10], amadness[l10], l10, k1);
                } while (++l10 < GameFacts.numberOfPlayers);
                if (j2 == 2 && k1 == 299)
                    u[0].enter = true;
                if (u[0].enter || u[0].handb) {
                    xtgraphics.fase = Phase.POSTGAMEFADEOUT;
                    u[0].enter = false;
                    u[0].handb = false;
                    k1 = -7;
                } else {
                    xtgraphics.levelhigh(record.wasted, record.whenwasted, record.closefinish, k1, checkpoints.stage);
                    if (k1 == 0 || k1 == 1 || k1 == 2) {
                        rd.setColor(new Color(0, 0, 0));
                        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                    }
                    if (record.wasted != 0) {
                        if (record.closefinish == 0) {
                            if (i2 == 9 || i2 == 11) {
                                rd.setColor(new Color(255, 255, 255));
                                rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                            }
                            if (i2 == 0)
                                Medium.around(aconto1[0], false);
                            if (i2 > 0 && i2 < 20)
                                Medium.transaround(aconto1[0], aconto1[record.wasted], i2);
                            if (i2 == 20)
                                Medium.around(aconto1[record.wasted], false);
                            if (k1 > record.whenwasted && i2 != 20)
                                i2++;
                            if ((i2 == 0 || i2 == 20) && ++k1 == 300) {
                                k1 = 0;
                                i2 = 0;
                                j2++;
                            }
                        } else if (record.closefinish == 1) {
                            if (i2 == 0)
                                Medium.around(aconto1[0], false);
                            if (i2 > 0 && i2 < 20)
                                Medium.transaround(aconto1[0], aconto1[record.wasted], i2);
                            if (i2 == 20)
                                Medium.around(aconto1[record.wasted], false);
                            if (i2 > 20 && i2 < 40)
                                Medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 20);
                            if (i2 == 40)
                                Medium.around(aconto1[0], false);
                            if (i2 > 40 && i2 < 60)
                                Medium.transaround(aconto1[0], aconto1[record.wasted], i2 - 40);
                            if (i2 == 60)
                                Medium.around(aconto1[record.wasted], false);
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
                                Medium.around(aconto1[0], false);
                            if (i2 > 0 && i2 < 20)
                                Medium.transaround(aconto1[0], aconto1[record.wasted], i2);
                            if (i2 == 20)
                                Medium.around(aconto1[record.wasted], false);
                            if (i2 > 20 && i2 < 40)
                                Medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 20);
                            if (i2 == 40)
                                Medium.around(aconto1[0], false);
                            if (i2 > 40 && i2 < 60)
                                Medium.transaround(aconto1[0], aconto1[record.wasted], i2 - 40);
                            if (i2 == 60)
                                Medium.around(aconto1[record.wasted], false);
                            if (i2 > 60 && i2 < 80)
                                Medium.transaround(aconto1[record.wasted], aconto1[0], i2 - 60);
                            if (i2 == 80)
                                Medium.around(aconto1[0], false);
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
                            rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                        }
                        if (k2 == 69 && (i2 == 3 || i2 == 5 || i2 == 31 || i2 == 33 || i2 == 66 || i2 == 68)) {
                            rd.setColor(new Color(255, 255, 255));
                            rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                        }
                        if (k2 == 30 && i2 >= 1 && i2 < 30)
                            if (i2 % (int) (2.0F + Medium.random() * 3F) == 0 && !flag2) {
                                rd.setColor(new Color(255, 255, 255));
                                rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
                                flag2 = true;
                            } else {
                                flag2 = false;
                            }
                        if (k1 > record.whenwasted && i2 != k2)
                            i2++;
                        Medium.around(aconto1[0], false);
                        if ((i2 == 0 || i2 == k2) && ++k1 == 300) {
                            k1 = 0;
                            i2 = 0;
                            j2++;
                        }
                    }
                }
            }
            if (xtgraphics.fase == Phase.POSTGAMEFADEOUT) {
                if (k1 <= 0) {
                    rd.drawImage(xtgraphics.mdness, 224, 30, null);
                    rd.drawImage(xtgraphics.dude[0], 70, 10, null);
                }
                if (k1 >= 0)
                    xtgraphics.fleximage(offImage, k1, checkpoints.stage);
                k1++;
                if (checkpoints.stage == GameFacts.numberOfStages && k1 == 10)
                    xtgraphics.fase = Phase.POSTGAME;
                if (k1 == 12)
                    xtgraphics.fase = Phase.POSTGAME;
            }
            if (xtgraphics.fase == Phase.PAUSETRIGGER) {
                repaint();
                xtgraphics.pauseimage(offImage);
                xtgraphics.fase = Phase.PAUSEMENU;
                mouses = 0;
            }
            if (xtgraphics.fase == Phase.PAUSEMENU) {
                xtgraphics.pausedgame(checkpoints.stage, u[0], record);
                if (k1 != 0)
                    k1 = 0;
                xtgraphics.ctachm(xm, ym, mouses, u[0]);
                if (mouses == 2)
                    mouses = 0;
                if (mouses == 1)
                    mouses = 2;
            }
            if (xtgraphics.fase == Phase.NOTENOUGHREPLAYDATA) {
                xtgraphics.cantreply();
                if (++k1 == 150 || u[0].enter || u[0].handb || mouses == 1) {
                    xtgraphics.fase = Phase.PAUSEMENU;
                    mouses = 0;
                    u[0].enter = false;
                    u[0].handb = false;
                }
            }
            if (lostfcs && xtgraphics.fase != Phase.DRAWENVIRONMENT && xtgraphics.fase != Phase.LOADING) {
                if (xtgraphics.fase == Phase.INGAME)
                    u[0].enter = false;
                else
                    xtgraphics.nofocus();
                if (mouses == 1 || mouses == 2)
                    lostfcs = false;
            }
            repaint();
            xtgraphics.playsounds(amadness[0], u[0], checkpoints.stage);
            date1 = new Date();
            long l5 = date1.getTime();
            if (xtgraphics.fase == Phase.INGAME || xtgraphics.fase == Phase.INSTANTREPLAY
                    || xtgraphics.fase == Phase.GAMEHIGHLIGHT) {
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
                        Medium.adjustFade(f1);
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
                if (i1 == 0 || xtgraphics.fase != Phase.DRAWENVIRONMENT) {
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
                gamer = null;
            }

            // if (xtgraphics.devtriggered) {

            // this could be useful for discord rich presence
            switch (xtgraphics.fase) {
                case INGAME:
                    gameState = "In game\nStage " + checkpoints.stage + ": " + checkpoints.name +
                    "\nPlayers: " + GameFacts.numberOfPlayers;
                    gameStateID = 0;
                    break;
                case STAGESELECT:
                    gameState = "Selecting a Stage";
                    gameStateID = 1;
                    break;
                case ERRORLOADINGSTAGE:
                    gameState = "Selecting a Stage";
                    gameStateID = 3;
                    break;
                case CARSELECT:
                    gameState = "Selecting a Car";
                    gameStateID = 7;
                    break;
                case MAINMENU:
                    gameState = "In main menu";
                    gameStateID = 10;
                    break;
                case INSTRUCTIONS:
                    gameState = "Reading game instructions";
                    gameStateID = 11;
                    break;
                default:
                    gameState = "Unknown State";
                    gameStateID = -1738;
                    break;
            }

            long l2 = Math.round(f1) - (l5 - l4);
            if (l2 < i)
                l2 = i;
            if (xtgraphics.fase != Phase.LOADING && xtgraphics.debugmode) {
                xtgraphics.gameMetrics.addFrameTimeSample((int) (l5 - l4));
                xtgraphics.gameMetrics.render(rd);
            }
            try {
                Thread.sleep(l2);
            } catch (InterruptedException _ex) {
            }
        } while (true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameFacts.screenWidth, GameFacts.screenHeight);
    }

    @Override
    public void init() {
        /*
         * load some fonts
         */
        new FontHandler();

        createOffImage();
    }

    private void createOffImage() {
        Dimension dimension = getOffImageDimensions();
        offImage = createImage(dimension.width, dimension.height);
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
            for (File file : files) {
                InputStream in = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(path + file.getName()));
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
        for (File file : files) {
            if ((path + file.getName()).equals(zeName)) {
                return true;
            }
        }
        return false;
    }

    private void openurl(final String string) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(string));
            } catch (final Exception exception) {

            }
        } else {
            try {
                Runtime.getRuntime().exec(urlopen() + " " + string);
            } catch (final Exception exception) {

            }
        }
    }

    private static String urlopen() {
        String string = "explorer";
        final String string27 = System.getProperty("os.name").toLowerCase();
        if (string27.contains("linux") || string27.contains("unix") || string27.equals("aix")) {
            string = "xdg-open";
        }
        if (string27.contains("mac")) {
            string = "open";
        }
        return string;
    }

    private void catchlink(int i, xtGraphics xtg) {
        if (!lostfcs) {
            if (i == 0)
                if (xm > 0 && xm < GameFacts.screenWidth && ym > 110 && ym < 169
                        || xm > Utility.centeredImageX(xtg.rpro)
                                && xm < Utility.centeredImageX(xtg.rpro) + xtg.rpro.getWidth(null) && ym > 240
                                && ym < 259) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    if (mouses == 2)
                        openurl("http://www.radicalplay.com/");
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            if (i == 1)
                if (xm > 0 && xm < GameFacts.screenWidth && ym > 205 && ym < 267) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    if (mouses == 2)
                        openurl("http://www.radicalplay.com/");
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean mouseMove(Event event, int x, int y) {
        if (!exwist && !lostfcs) {
            xm = x - apx;
            ym = y - apy;
        }
        return false;
    }

    boolean useUnlimitedResize = true;
    private Dimension getOffImageDimensions() {
        if (useUnlimitedResize) {
            return new Dimension(getWidth(), getHeight());
        } else {
            return new Dimension(GameFacts.screenWidth, GameFacts.screenHeight);
        }
    }

    @Override
    public void ratioChanged(double newRatio) {
        if (useUnlimitedResize && offImage != null && (getWidth() != offImage.getWidth(null) || getHeight() != offImage.getHeight(null))) {
            createOffImage();

            if (xtgraphics != null) {
                xtgraphics.setGraphics(rd);
            }

            rd.setTransform(new AffineTransform());
            rd.scale(newRatio, newRatio);
            HLogger.info("Scaling to " + newRatio);
        }
    }
}
