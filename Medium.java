import java.awt.*;
import java.util.Random;

/**
 * Medium manages most of the graphics you see during games, such as clouds, ground, and dust.
 * This class was edited and re-edited again by Chaotic for (mostly) full NFMM Graphics (excluding sparks and extra dust).
 *
 * @author Kaffeinated, Chaotic, Omar Wally
 */
public class Medium {
    private static int nmt = 0;
    private static int[] mrd = null;
    private static int[] nmv = null;
    private static int[][] mtx = null;
    private static int[][] mty = null;
    private static int[][] mtz = null;
    private static int[][][] mtc = null;
    public static int mgen;
    public static int focus_point;
    public static int ground;
    private static int skyline;
    public static final int[] fade = {
            3000, 4500, 6000, 7500, 9000, 10500, 12000, 13500, 15000, 16500, 18000, 19500, 21000, 22500, 24000, 25500
    };
    private static final int[] cldd = {
            210, 210, 210, 1, -1000
    };
    private static final int[] clds = {
            210, 210, 210
    };
    private static final int[] osky = {
            170, 220, 255
    };
    public static final int[] csky = {
            170, 220, 255
    };
    private static final int[] ogrnd = {
            205, 200, 200
    };
    public static final int[] cgrnd = {
            205, 200, 200
    };
    private static final int[] texture = {
            0, 0, 0, 50
    };
    private static final int[] cpol = {
            215, 210, 210
    };
    private static final int[] crgrnd = {
            205, 200, 200
    };
    public static final int[] cfade = {
            255, 220, 220
    };
    public static int snap[] = {
            0, 0, 0
    };
    public static int origfade;
    public static int fogd;
    public static boolean lightson;
    private static int noelec;
    public static int lightn;
    private static boolean lton;
    private static int lilo;
    public static int flex;
    public static boolean trk;
    public static boolean crs;
    public static int cx;
    public static int cy;
    public static int cz;
    public static int xz;
    public static int zy;
    public static int x;
    public static int y;
    public static int z;
    public static int w;
    public static int h;
    public static int nsp;
    public static int spx[];
    public static int spz[];
    public static int sprad[];
    private static boolean td;
    private static int bcxz;
    public static int vxz;
    public static int adv;
    public static boolean vert;
    private static int trns;
    private static int ogpx[][];
    private static int ogpz[][];
    private static int cgpx[];
    private static int cgpz[];
    private static int sgpx;
    private static int sgpz;
    private static int nrw;
    private static int ncl;
    public static int lastmaf;
    public static int checkpoint;
    public static boolean lastcheck;
    public static float elecr;
    public static boolean cpflik;
    public static boolean nochekflk;
    private static int cntrn;
    private static boolean diup[];
    private static int rand[];
    private static int trn;
    public static int hit;
    public static int ptr;
    public static int ptcnt;
    public static int nrnd;
    public static long trx;
    public static long trz;
    private static long atrx;
    private static long atrz;
    public static int fallen;
    private static float fo;
    private static float gofo;
    private static float pvr[][];
    private static int pmx[];
    private static float pcv[];
    private static int resdown;
    private static int noc;
    private static int clx[];
    private static int clz[];
    private static int cmx[];
    private static int clax[][][];
    private static int clay[][][];
    private static int claz[][][];
    private static int clc[][][][];
    private static int iw;
    private static int nst;
    private static int stx[];
    private static int stz[];
    private static int stc[][][];
    private static boolean bst[];
    private static int twn[];
    private static int ih;
    private static int fvect;

    public static float random() {
        if (cntrn == 0) {
            int i = 0;
            do {
                rand[i] = (int) (10D * Math.random());
                diup[i] = Math.random() <= Math.random();
            } while (++i < 3);
            cntrn = 20;
        } else {
            cntrn--;
        }
        int j = 0;
        do {
            if (diup[j]) {
                rand[j]++;
                if (rand[j] == 10) {
                    rand[j] = 0;
                }
            } else {
                rand[j]--;
                if (rand[j] == -1) {
                    rand[j] = 9;
                }
            }
        } while (++j < 3);
        trn++;
        if (trn == 3) {
            trn = 0;
        }
        return rand[trn] / 10F;
    }

    public static void newpolys(int i, int j, int k, int l, Trackers trackers, int i1) {
        Random random1 = new Random((i1 + cgrnd[0] + cgrnd[1] + cgrnd[2]) * 1671);
        nrw = j / 1200 + 9;
        ncl = l / 1200 + 9;
        sgpx = i - 4800;
        sgpz = k - 4800;
        ogpx = null;
        ogpz = null;
        pvr = null;
        cgpx = null;
        cgpz = null;
        pmx = null;
        pcv = null;
        ogpx = new int[nrw * ncl][8];
        ogpz = new int[nrw * ncl][8];
        pvr = new float[nrw * ncl][8];
        cgpx = new int[nrw * ncl];
        cgpz = new int[nrw * ncl];
        pmx = new int[nrw * ncl];
        pcv = new float[nrw * ncl];
        int j1 = 0;
        int k1 = 0;
        for (int l1 = 0; l1 < nrw * ncl; l1++) {
            cgpx[l1] = sgpx + j1 * 1200 + (int) (random1.nextDouble() * 1000D - 500D);
            cgpz[l1] = sgpz + k1 * 1200 + (int) (random1.nextDouble() * 1000D - 500D);
            for (int j2 = 0; j2 < trackers.nt; j2++) {
                if (trackers.zy[j2] != 0 || trackers.xy[j2] != 0) {
                    continue;
                }
                if (trackers.radx[j2] < trackers.radz[j2] && Math.abs(cgpz[l1] - trackers.z[j2]) < trackers.radz[j2]) {
                    for (; Math.abs(cgpx[l1] - trackers.x[j2]) < trackers.radx[j2]; cgpx[l1] += random1.nextDouble()
                            * trackers.radx[j2] * 2D - trackers.radx[j2]) {
                    }
                }
                if (trackers.radz[j2] >= trackers.radx[j2]
                        || Math.abs(cgpx[l1] - trackers.x[j2]) >= trackers.radx[j2]) {
                    continue;
                }
                for (; Math.abs(cgpz[l1] - trackers.z[j2]) < trackers.radz[j2]; cgpz[l1] += random1.nextDouble()
                        * trackers.radz[j2] * 2D - trackers.radz[j2]) {
                }
            }

            if (++j1 == nrw) {
                j1 = 0;
                k1++;
            }
        }

        for (int i2 = 0; i2 < nrw * ncl; i2++) {
            float f = (float) (0.29999999999999999D + 1.6000000000000001D * random1.nextDouble());
            ogpx[i2][0] = 0;
            ogpz[i2][0] = (int) ((100D + random1.nextDouble() * 760D) * f);
            ogpx[i2][1] = (int) ((100D + random1.nextDouble() * 760D) * 0.70709999999999995D * f);
            ogpz[i2][1] = ogpx[i2][1];
            ogpx[i2][2] = (int) ((100D + random1.nextDouble() * 760D) * f);
            ogpz[i2][2] = 0;
            ogpx[i2][3] = (int) ((100D + random1.nextDouble() * 760D) * 0.70709999999999995D * f);
            ogpz[i2][3] = -ogpx[i2][3];
            ogpx[i2][4] = 0;
            ogpz[i2][4] = -(int) ((100D + random1.nextDouble() * 760D) * f);
            ogpx[i2][5] = -(int) ((100D + random1.nextDouble() * 760D) * 0.70709999999999995D * f);
            ogpz[i2][5] = ogpx[i2][5];
            ogpx[i2][6] = -(int) ((100D + random1.nextDouble() * 760D) * f);
            ogpz[i2][6] = 0;
            ogpx[i2][7] = -(int) ((100D + random1.nextDouble() * 760D) * 0.70709999999999995D * f);
            ogpz[i2][7] = -ogpx[i2][7];
            for (int k2 = 0; k2 < 8; k2++) {
                int l2 = k2 - 1;
                if (l2 == -1) {
                    l2 = 7;
                }
                int i3 = k2 + 1;
                if (i3 == 8) {
                    i3 = 0;
                }
                ogpx[i2][k2] = ((ogpx[i2][l2] + ogpx[i2][i3]) / 2 + ogpx[i2][k2]) / 2;
                ogpz[i2][k2] = ((ogpz[i2][l2] + ogpz[i2][i3]) / 2 + ogpz[i2][k2]) / 2;
                pvr[i2][k2] = (float) (1.1000000000000001D + random1.nextDouble() * 0.80000000000000004D);
                int j3 = (int) Math.sqrt((int) (ogpx[i2][k2] * ogpx[i2][k2] * pvr[i2][k2] * pvr[i2][k2]
                        + ogpz[i2][k2] * ogpz[i2][k2] * pvr[i2][k2] * pvr[i2][k2]));
                if (j3 > pmx[i2]) {
                    pmx[i2] = j3;
                }
            }

            pcv[i2] = (float) (0.96999999999999997D + random1.nextDouble() * 0.029999999999999999D);
            if (pcv[i2] > 1.0F) {
                pcv[i2] = 1.0F;
            }
            if (random1.nextDouble() > random1.nextDouble()) {
                pcv[i2] = 1.0F;
            }
        }

    }

    private static void groundpolys(Graphics2D graphics2d) {
        int i = (x - sgpx) / 1200 - 12;
        if (i < 0) {
            i = 0;
        }
        int j = i + 25;
        if (j > nrw) {
            j = nrw;
        }
        if (j < i) {
            j = i;
        }
        int k = (z - sgpz) / 1200 - 12;
        if (k < 0) {
            k = 0;
        }
        int l = k + 25;
        if (l > ncl) {
            l = ncl;
        }
        if (l < k) {
            l = k;
        }
        int ai[][] = new int[j - i][l - k];
        for (int i1 = i; i1 < j; i1++) {
            for (int k1 = k; k1 < l; k1++) {
                ai[i1 - i][k1 - k] = 0;
                int i2 = i1 + k1 * nrw;
                if (resdown >= 2 && i2 % 2 != 0) {
                    continue;
                }
                int k2 = cx + (int) ((cgpx[i2] - x - cx) * RadicalMath.cos(xz) - (cgpz[i2] - z - cz) * RadicalMath.sin(xz));
                int l2 = cz + (int) ((cgpx[i2] - x - cx) * RadicalMath.sin(xz) + (cgpz[i2] - z - cz) * RadicalMath.cos(xz));
                int i3 = cz + (int) ((250 - y - cy) * RadicalMath.sin(zy) + (l2 - cz) * RadicalMath.cos(zy));
                if (Utility.xs(k2 + pmx[i2], i3) <= 0 || Utility.xs(k2 - pmx[i2], i3) >= w || i3 <= -pmx[i2] || i3 >= fade[2]) {
                    continue;
                }
                ai[i1 - i][k1 - k] = i3;
                int ai4[] = new int[8];
                int ai6[] = new int[8];
                int ai8[] = new int[8];
                for (int l3 = 0; l3 < 8; l3++) {
                    ai4[l3] = (int) ((ogpx[i2][l3] * pvr[i2][l3] + cgpx[i2]) - x);
                    ai6[l3] = (int) ((ogpz[i2][l3] * pvr[i2][l3] + cgpz[i2]) - z);
                    ai8[l3] = ground;
                }

                Utility.rot(ai4, ai6, cx, cz, xz, 8);
                Utility.rot(ai8, ai6, cy, cz, zy, 8);
                int ai9[] = new int[8];
                int ai10[] = new int[8];
                int k4 = 0;
                int i5 = 0;
                int j5 = 0;
                int i6 = 0;
                boolean flag1 = true;
                for (int l6 = 0; l6 < 8; l6++) {
                    ai9[l6] = Utility.xs(ai4[l6], ai6[l6]);
                    ai10[l6] = Utility.ys(ai8[l6], ai6[l6], 1);
                    if (ai10[l6] < 0 || ai6[l6] < 10) {
                        k4++;
                    }
                    if (ai10[l6] > h || ai6[l6] < 10) {
                        i5++;
                    }
                    if (ai9[l6] < 0 || ai6[l6] < 10) {
                        j5++;
                    }
                    if (ai9[l6] > w || ai6[l6] < 10) {
                        i6++;
                    }
                }

                if (j5 == 8 || k4 == 8 || i5 == 8 || i6 == 8) {
                    flag1 = false;
                }
                if (!flag1) {
                    continue;
                }
                int i7 = (int) ((cpol[0] * pcv[i2] + cgrnd[0]) / 2.0F);
                int j7 = (int) ((cpol[1] * pcv[i2] + cgrnd[1]) / 2.0F);
                int k7 = (int) ((cpol[2] * pcv[i2] + cgrnd[2]) / 2.0F);
                if (i3 - pmx[i2] > fade[0]) {
                    i7 = (i7 * 7 + cfade[0]) / 8;
                    j7 = (j7 * 7 + cfade[1]) / 8;
                    k7 = (k7 * 7 + cfade[2]) / 8;
                }
                if (i3 - pmx[i2] > fade[1]) {
                    i7 = (i7 * 7 + cfade[0]) / 8;
                    j7 = (j7 * 7 + cfade[1]) / 8;
                    k7 = (k7 * 7 + cfade[2]) / 8;
                }
                graphics2d.setColor(new Color(i7, j7, k7));
                graphics2d.fillPolygon(ai9, ai10, 8);
            }

        }

        for (int j1 = i; j1 < j; j1++) {
            for (int l1 = k; l1 < l; l1++) {
                if (ai[j1 - i][l1 - k] == 0) {
                    continue;
                }
                int j2 = j1 + l1 * nrw;
                int ai1[] = new int[8];
                int ai2[] = new int[8];
                int ai3[] = new int[8];
                for (int j3 = 0; j3 < 8; j3++) {
                    ai1[j3] = (ogpx[j2][j3] + cgpx[j2]) - x;
                    ai2[j3] = (ogpz[j2][j3] + cgpz[j2]) - z;
                    ai3[j3] = ground;
                }

                Utility.rot(ai1, ai2, cx, cz, xz, 8);
                Utility.rot(ai3, ai2, cy, cz, zy, 8);
                int ai5[] = new int[8];
                int ai7[] = new int[8];
                int k3 = 0;
                int i4 = 0;
                int j4 = 0;
                int l4 = 0;
                boolean flag = true;
                for (int k5 = 0; k5 < 8; k5++) {
                    ai5[k5] = Utility.xs(ai1[k5], ai2[k5]);
                    ai7[k5] = Utility.ys(ai3[k5], ai2[k5], 1);
                    if (ai7[k5] < 0 || ai2[k5] < 10) {
                        k3++;
                    }
                    if (ai7[k5] > h || ai2[k5] < 10) {
                        i4++;
                    }
                    if (ai5[k5] < 0 || ai2[k5] < 10) {
                        j4++;
                    }
                    if (ai5[k5] > w || ai2[k5] < 10) {
                        l4++;
                    }
                }

                if (j4 == 8 || k3 == 8 || i4 == 8 || l4 == 8) {
                    flag = false;
                }
                if (!flag) {
                    continue;
                }
                int l5 = (int) (cpol[0] * pcv[j2]);
                int j6 = (int) (cpol[1] * pcv[j2]);
                int k6 = (int) (cpol[2] * pcv[j2]);
                if (ai[j1 - i][l1 - k] - pmx[j2] > fade[0]) {
                    l5 = (l5 * 7 + cfade[0]) / 8;
                    j6 = (j6 * 7 + cfade[1]) / 8;
                    k6 = (k6 * 7 + cfade[2]) / 8;
                }
                if (ai[j1 - i][l1 - k] - pmx[j2] > fade[1]) {
                    l5 = (l5 * 7 + cfade[0]) / 8;
                    j6 = (j6 * 7 + cfade[1]) / 8;
                    k6 = (k6 * 7 + cfade[2]) / 8;
                }
                graphics2d.setColor(new Color(l5, j6, k6));
                graphics2d.fillPolygon(ai5, ai7, 8);
            }
        }
    }

    public Medium() {
        focus_point = 500;
        ground = 250;
        skyline = -300;
        fogd = 7;
        mgen = (int) (Math.random() * 100000D);
        snap = new int[3];
        origfade = 3000;
        fogd = 3;
        lightson = false;
        lightn = -1;
        lilo = 217;
        flex = 0;
        trk = false;
        crs = false;
        noelec = 0;
        cx = GameFacts.screenWidth / 2;
        cy = GameFacts.screenHeight / 2;
        cz = 50;
        xz = 0;
        zy = 0;
        x = 0;
        y = 0;
        z = 0;
        w = GameFacts.screenWidth;
        h = GameFacts.screenHeight;
        nsp = 0;
        spx = new int[7];
        spz = new int[7];
        sprad = new int[7];
        td = false;
        bcxz = 0;
        vxz = 180;
        adv = 500;
        vert = false;
        trns = 1;
        ogpx = new int[9500][8];
        ogpz = new int[9500][8];
        cgpx = new int[9500];
        cgpz = new int[9500];
        sgpx = 0;
        sgpz = 0;
        nrw = 0;
        ncl = 0;
        lastmaf = 0;
        checkpoint = -1;
        lastcheck = false;
        elecr = 0.0F;
        cpflik = false;
        nochekflk = false;
        cntrn = 0;
        diup = new boolean[3];
        rand = new int[3];
        trn = 0;
        hit = 45000;
        ptr = 0;
        ptcnt = -10;
        nrnd = 0;
        trx = 0L;
        trz = 0L;
        atrx = 0L;
        atrz = 0L;
        fallen = 0;
        fo = 1.0F;
        gofo = (float) (0.33000001311302185D + Math.random() * 1.3400000000000001D);
    }

    public static void newclouds(int i, int j, int k, int l) {
        clx = null;
        clz = null;
        cmx = null;
        clax = null;
        clay = null;
        claz = null;
        clc = null;
        i = i / 20 - 10000;
        j = j / 20 + 10000;
        k = k / 20 - 10000;
        l = l / 20 + 10000;
        noc = ((j - i) * (l - k)) / 0xfe502b;
        clx = new int[noc];
        clz = new int[noc];
        cmx = new int[noc];
        clax = new int[noc][3][12];
        clay = new int[noc][3][12];
        claz = new int[noc][3][12];
        clc = new int[noc][2][6][3];
        for (int i1 = 0; i1 < noc; i1++) {
            clx[i1] = (int) (i + (j - i) * Math.random());
            clz[i1] = (int) (k + (l - k) * Math.random());
            float f = (float) (0.25D + Math.random() * 1.25D);
            float f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][0] = (int) (f1 * 0.3826D);
            claz[i1][0][0] = (int) (f1 * 0.92379999999999995D);
            clay[i1][0][0] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][1] = (int) (f1 * 0.70709999999999995D);
            claz[i1][0][1] = (int) (f1 * 0.70709999999999995D);
            clay[i1][0][1] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][2] = (int) (f1 * 0.92379999999999995D);
            claz[i1][0][2] = (int) (f1 * 0.3826D);
            clay[i1][0][2] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][3] = (int) (f1 * 0.92379999999999995D);
            claz[i1][0][3] = -(int) (f1 * 0.3826D);
            clay[i1][0][3] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][4] = (int) (f1 * 0.70709999999999995D);
            claz[i1][0][4] = -(int) (f1 * 0.70709999999999995D);
            clay[i1][0][4] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][5] = (int) (f1 * 0.3826D);
            claz[i1][0][5] = -(int) (f1 * 0.92379999999999995D);
            clay[i1][0][5] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][6] = -(int) (f1 * 0.3826D);
            claz[i1][0][6] = -(int) (f1 * 0.92379999999999995D);
            clay[i1][0][6] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][7] = -(int) (f1 * 0.70709999999999995D);
            claz[i1][0][7] = -(int) (f1 * 0.70709999999999995D);
            clay[i1][0][7] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][8] = -(int) (f1 * 0.92379999999999995D);
            claz[i1][0][8] = -(int) (f1 * 0.3826D);
            clay[i1][0][8] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][9] = -(int) (f1 * 0.92379999999999995D);
            claz[i1][0][9] = (int) (f1 * 0.3826D);
            clay[i1][0][9] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][10] = -(int) (f1 * 0.70709999999999995D);
            claz[i1][0][10] = (int) (f1 * 0.70709999999999995D);
            clay[i1][0][10] = (int) ((25D - Math.random() * 50D) * f);
            f1 = (float) ((200D + Math.random() * 700D) * f);
            clax[i1][0][11] = -(int) (f1 * 0.3826D);
            claz[i1][0][11] = (int) (f1 * 0.92379999999999995D);
            clay[i1][0][11] = (int) ((25D - Math.random() * 50D) * f);
            for (int j1 = 0; j1 < 12; j1++) {
                int j2 = j1 - 1;
                if (j2 == -1) {
                    j2 = 11;
                }
                int l2 = j1 + 1;
                if (l2 == 12) {
                    l2 = 0;
                }
                clax[i1][0][j1] = ((clax[i1][0][j2] + clax[i1][0][l2]) / 2 + clax[i1][0][j1]) / 2;
                clay[i1][0][j1] = ((clay[i1][0][j2] + clay[i1][0][l2]) / 2 + clay[i1][0][j1]) / 2;
                claz[i1][0][j1] = ((claz[i1][0][j2] + claz[i1][0][l2]) / 2 + claz[i1][0][j1]) / 2;
            }

            for (int k1 = 0; k1 < 12; k1++) {
                float f2 = (float) (1.2D + 0.59999999999999998D * Math.random());
                clax[i1][1][k1] = (int) (clax[i1][0][k1] * f2);
                claz[i1][1][k1] = (int) (claz[i1][0][k1] * f2);
                clay[i1][1][k1] = (int) (clay[i1][0][k1] - 100D * Math.random());
                f2 = (float) (1.1000000000000001D + 0.29999999999999999D * Math.random());
                clax[i1][2][k1] = (int) (clax[i1][1][k1] * f2);
                claz[i1][2][k1] = (int) (claz[i1][1][k1] * f2);
                clay[i1][2][k1] = (int) (clay[i1][1][k1] - 240D * Math.random());
            }

            cmx[i1] = 0;
            for (int l1 = 0; l1 < 12; l1++) {
                int k2 = l1 - 1;
                if (k2 == -1) {
                    k2 = 11;
                }
                int i3 = l1 + 1;
                if (i3 == 12) {
                    i3 = 0;
                }
                clay[i1][1][l1] = ((clay[i1][1][k2] + clay[i1][1][i3]) / 2 + clay[i1][1][l1]) / 2;
                clay[i1][2][l1] = ((clay[i1][2][k2] + clay[i1][2][i3]) / 2 + clay[i1][2][l1]) / 2;
                int j3 = (int) Math.sqrt(clax[i1][2][l1] * clax[i1][2][l1] + claz[i1][2][l1] * claz[i1][2][l1]);
                if (j3 > cmx[i1]) {
                    cmx[i1] = j3;
                }
            }

            for (int i2 = 0; i2 < 6; i2++) {
                double d1 = Math.random();
                double d2 = Math.random();
                for (int k3 = 0; k3 < 3; k3++) {
                    float f3 = clds[k3] * 1.05F - clds[k3];
                    clc[i1][0][i2][k3] = (int) (clds[k3] + f3 * d1);
                    if (clc[i1][0][i2][k3] > 255) {
                        clc[i1][0][i2][k3] = 255;
                    }
                    if (clc[i1][0][i2][k3] < 0) {
                        clc[i1][0][i2][k3] = 0;
                    }
                    clc[i1][1][i2][k3] = (int) (clds[k3] * 1.05F + f3 * d2);
                    if (clc[i1][1][i2][k3] > 255) {
                        clc[i1][1][i2][k3] = 255;
                    }
                    if (clc[i1][1][i2][k3] < 0) {
                        clc[i1][1][i2][k3] = 0;
                    }
                }

            }

        }

    }

    private static void drawclouds(Graphics2D graphics2d) {
        for (int i = 0; i < noc; i++) {
            int j = cx + (int) ((clx[i] - x / 20 - cx) * RadicalMath.cos(xz) - (clz[i] - z / 20 - cz) * RadicalMath.sin(xz));
            int k = cz + (int) ((clx[i] - x / 20 - cx) * RadicalMath.sin(xz) + (clz[i] - z / 20 - cz) * RadicalMath.cos(xz));
            int l = cz + (int) ((cldd[4] - y / 20 - cy) * RadicalMath.sin(zy) + (k - cz) * RadicalMath.cos(zy));
            int i1 = Utility.xs(j + cmx[i], l);
            int j1 = Utility.xs(j - cmx[i], l);
            if (i1 <= 0 || j1 >= w || l <= -cmx[i] || i1 - j1 <= 20) {
                continue;
            }
            int ai[][] = new int[3][12];
            int ai1[][] = new int[3][12];
            int ai2[][] = new int[3][12];
            int ai3[] = new int[12];
            int ai4[] = new int[12];
            int k1;
            int l1;
            int i2;
            int j2;
            boolean flag;
            int k2;
            int l2;
            int i3;
            for (int j3 = 0; j3 < 3; j3++) {
                for (int k4 = 0; k4 < 12; k4++) {
                    ai[j3][k4] = (clax[i][j3][k4] + clx[i]) - x / 20;
                    ai2[j3][k4] = (claz[i][j3][k4] + clz[i]) - z / 20;
                    ai1[j3][k4] = (clay[i][j3][k4] + cldd[4]) - y / 20;
                }

                Utility.rot(ai[j3], ai2[j3], cx, cz, xz, 12);
                Utility.rot(ai1[j3], ai2[j3], cy, cz, zy, 12);
            }

            for (int k3 = 0; k3 < 12; k3 += 2) {
                k1 = 0;
                l1 = 0;
                i2 = 0;
                j2 = 0;
                flag = true;
                k2 = 0;
                l2 = 0;
                i3 = 0;
                for (int l4 = 0; l4 < 6; l4++) {
                    int i6 = 0;
                    byte byte0 = 1;
                    if (l4 == 0) {
                        i6 = k3;
                    }
                    if (l4 == 1) {
                        i6 = k3 + 1;
                        if (i6 >= 12) {
                            i6 -= 12;
                        }
                    }
                    if (l4 == 2) {
                        i6 = k3 + 2;
                        if (i6 >= 12) {
                            i6 -= 12;
                        }
                    }
                    if (l4 == 3) {
                        i6 = k3 + 2;
                        if (i6 >= 12) {
                            i6 -= 12;
                        }
                        byte0 = 2;
                    }
                    if (l4 == 4) {
                        i6 = k3 + 1;
                        if (i6 >= 12) {
                            i6 -= 12;
                        }
                        byte0 = 2;
                    }
                    if (l4 == 5) {
                        i6 = k3;
                        byte0 = 2;
                    }
                    ai3[l4] = Utility.xs(ai[byte0][i6], ai2[byte0][i6]);
                    ai4[l4] = Utility.ys(ai1[byte0][i6], ai2[byte0][i6], 1);
                    l2 += ai[byte0][i6];
                    k2 += ai1[byte0][i6];
                    i3 += ai2[byte0][i6];
                    if (ai4[l4] < 0 || ai2[0][l4] < 10) {
                        k1++;
                    }
                    if (ai4[l4] > h || ai2[0][l4] < 10) {
                        l1++;
                    }
                    if (ai3[l4] < 0 || ai2[0][l4] < 10) {
                        i2++;
                    }
                    if (ai3[l4] > w || ai2[0][l4] < 10) {
                        j2++;
                    }
                }

                if (i2 == 6 || k1 == 6 || l1 == 6 || j2 == 6) {
                    flag = false;
                }
                if (!flag) {
                    continue;
                }
                l2 /= 6;
                k2 /= 6;
                i3 /= 6;
                int i5 = (int) Math.sqrt((cy - k2) * (cy - k2) + (cx - l2) * (cx - l2) + i3 * i3);
                if (i5 >= fade[7]) {
                    continue;
                }
                int j6 = clc[i][1][k3 / 2][0];
                int j7 = clc[i][1][k3 / 2][1];
                int j8 = clc[i][1][k3 / 2][2];
                for (int i9 = 0; i9 < 16; i9++) {
                    if (i5 > fade[i9]) {
                        j6 = (j6 * fogd + cfade[0]) / (fogd + 1);
                        j7 = (j7 * fogd + cfade[1]) / (fogd + 1);
                        j8 = (j8 * fogd + cfade[2]) / (fogd + 1);
                    }
                }

                graphics2d.setColor(new Color(j6, j7, j8));
                graphics2d.fillPolygon(ai3, ai4, 6);
            }

            for (int l3 = 0; l3 < 12; l3 += 2) {
                k1 = 0;
                l1 = 0;
                i2 = 0;
                j2 = 0;
                flag = true;
                k2 = 0;
                l2 = 0;
                i3 = 0;
                for (int j5 = 0; j5 < 6; j5++) {
                    int k6 = 0;
                    int k7 = 0;
                    if (j5 == 0) {
                        k6 = l3;
                    }
                    if (j5 == 1) {
                        k6 = l3 + 1;
                        if (k6 >= 12) {
                            k6 -= 12;
                        }
                    }
                    if (j5 == 2) {
                        k6 = l3 + 2;
                        if (k6 >= 12) {
                            k6 -= 12;
                        }
                    }
                    if (j5 == 3) {
                        k6 = l3 + 2;
                        if (k6 >= 12) {
                            k6 -= 12;
                        }
                        k7 = 1;
                    }
                    if (j5 == 4) {
                        k6 = l3 + 1;
                        if (k6 >= 12) {
                            k6 -= 12;
                        }
                        k7 = 1;
                    }
                    if (j5 == 5) {
                        k6 = l3;
                        k7 = 1;
                    }
                    ai3[j5] = Utility.xs(ai[k7][k6], ai2[k7][k6]);
                    ai4[j5] = Utility.ys(ai1[k7][k6], ai2[k7][k6], 1);
                    l2 += ai[k7][k6];
                    k2 += ai1[k7][k6];
                    i3 += ai2[k7][k6];
                    if (ai4[j5] < 0 || ai2[0][j5] < 10) {
                        k1++;
                    }
                    if (ai4[j5] > h || ai2[0][j5] < 10) {
                        l1++;
                    }
                    if (ai3[j5] < 0 || ai2[0][j5] < 10) {
                        i2++;
                    }
                    if (ai3[j5] > w || ai2[0][j5] < 10) {
                        j2++;
                    }
                }

                if (i2 == 6 || k1 == 6 || l1 == 6 || j2 == 6) {
                    flag = false;
                }
                if (!flag) {
                    continue;
                }
                l2 /= 6;
                k2 /= 6;
                i3 /= 6;
                int k5 = (int) Math.sqrt((cy - k2) * (cy - k2) + (cx - l2) * (cx - l2) + i3 * i3);
                if (k5 >= fade[7]) {
                    continue;
                }
                int l6 = clc[i][0][l3 / 2][0];
                int l7 = clc[i][0][l3 / 2][1];
                int k8 = clc[i][0][l3 / 2][2];
                for (int j9 = 0; j9 < 16; j9++) {
                    if (k5 > fade[j9]) {
                        l6 = (l6 * fogd + cfade[0]) / (fogd + 1);
                        l7 = (l7 * fogd + cfade[1]) / (fogd + 1);
                        k8 = (k8 * fogd + cfade[2]) / (fogd + 1);
                    }
                }

                graphics2d.setColor(new Color(l6, l7, k8));
                graphics2d.fillPolygon(ai3, ai4, 6);
            }

            k1 = 0;
            l1 = 0;
            i2 = 0;
            j2 = 0;
            flag = true;
            k2 = 0;
            l2 = 0;
            i3 = 0;
            for (int i4 = 0; i4 < 12; i4++) {
                ai3[i4] = Utility.xs(ai[0][i4], ai2[0][i4]);
                ai4[i4] = Utility.ys(ai1[0][i4], ai2[0][i4], 1);
                l2 += ai[0][i4];
                k2 += ai1[0][i4];
                i3 += ai2[0][i4];
                if (ai4[i4] < 0 || ai2[0][i4] < 10) {
                    k1++;
                }
                if (ai4[i4] > h || ai2[0][i4] < 10) {
                    l1++;
                }
                if (ai3[i4] < 0 || ai2[0][i4] < 10) {
                    i2++;
                }
                if (ai3[i4] > w || ai2[0][i4] < 10) {
                    j2++;
                }
            }

            if (i2 == 12 || k1 == 12 || l1 == 12 || j2 == 12) {
                flag = false;
            }
            if (!flag) {
                continue;
            }
            l2 /= 12;
            k2 /= 12;
            i3 /= 12;
            int j4 = (int) Math.sqrt((cy - k2) * (cy - k2) + (cx - l2) * (cx - l2) + i3 * i3);
            if (j4 >= fade[7]) {
                continue;
            }
            int l5 = clds[0];
            int i7 = clds[1];
            int i8 = clds[2];
            for (int l8 = 0; l8 < 16; l8++) {
                if (j4 > fade[l8]) {
                    l5 = (l5 * fogd + cfade[0]) / (fogd + 1);
                    i7 = (i7 * fogd + cfade[1]) / (fogd + 1);
                    i8 = (i8 * fogd + cfade[2]) / (fogd + 1);
                }
            }

            graphics2d.setColor(new Color(l5, i7, i8));
            graphics2d.fillPolygon(ai3, ai4, 12);
        }

    }

    public static void newmountains(int i, int i_167_, int i_168_, int i_169_) {
        Random random = new Random(mgen);
        nmt = (int) (20.0 + 10.0 * random.nextDouble());
        int i_170_ = (i + i_167_) / 60;
        int i_171_ = (i_168_ + i_169_) / 60;
        int i_172_ = Math.max(i_167_ - i, i_169_ - i_168_) / 60;
        mrd = null;
        nmv = null;
        mtx = null;
        mty = null;
        mtz = null;
        mtc = null;
        mrd = new int[nmt];
        nmv = new int[nmt];
        mtx = new int[nmt][];
        mty = new int[nmt][];
        mtz = new int[nmt][];
        mtc = new int[nmt][][];
        int[] is = new int[nmt];
        int[] is_173_ = new int[nmt];
        for (int i_174_ = 0; i_174_ < nmt; i_174_++) {
            int i_175_;
            float f;
            float f_176_;
            is[i_174_] = (int) (10000.0 + random.nextDouble() * 10000.0);
            int i_177_ = (int) (random.nextDouble() * 360.0);
            if (random.nextDouble() > random.nextDouble()) {
                f = (float) (0.2 + random.nextDouble() * 0.35);
                f_176_ = (float) (0.2 + random.nextDouble() * 0.35);
                nmv[i_174_] = (int) (f * (24.0 + 16.0 * random.nextDouble()));
                i_175_ = (int) (85.0 + 10.0 * random.nextDouble());
            } else {
                f = (float) (0.3 + random.nextDouble() * 1.1);
                f_176_ = (float) (0.2 + random.nextDouble() * 0.35);
                nmv[i_174_] = (int) (f * (12.0 + 8.0 * random.nextDouble()));
                i_175_ = (int) (104.0 - 10.0 * random.nextDouble());
            }
            mtx[i_174_] = new int[nmv[i_174_] * 2];
            mty[i_174_] = new int[nmv[i_174_] * 2];
            mtz[i_174_] = new int[nmv[i_174_] * 2];
            mtc[i_174_] = new int[nmv[i_174_]][3];
            for (int i_178_ = 0; i_178_ < nmv[i_174_]; i_178_++) {
                mtx[i_174_][i_178_] = (int) ((i_178_ * 500 + (random.nextDouble() * 800.0 - 400.0)
                        - 250 * (nmv[i_174_] - 1)) * f);
                mtx[i_174_][i_178_ + nmv[i_174_]] = (int) ((i_178_ * 500
                        + (random.nextDouble() * 800.0 - 400.0) - 250 * (nmv[i_174_] - 1)) * f);
                mtx[i_174_][nmv[i_174_]] = (int) (mtx[i_174_][0]
                        - (100.0 + random.nextDouble() * 600.0) * f);
                mtx[i_174_][nmv[i_174_] * 2 - 1] = (int) (mtx[i_174_][nmv[i_174_] - 1]
                        + (100.0 + random.nextDouble() * 600.0) * f);
                if (i_178_ == 0 || i_178_ == nmv[i_174_] - 1)
                    mty[i_174_][i_178_] = (int) ((-400.0 - 1200.0 * random.nextDouble()) * f_176_
                            + ground);
                if (i_178_ == 1 || i_178_ == nmv[i_174_] - 2)
                    mty[i_174_][i_178_] = (int) ((-1000.0 - 1450.0 * random.nextDouble()) * f_176_
                            + ground);
                if (i_178_ > 1 && i_178_ < nmv[i_174_] - 2)
                    mty[i_174_][i_178_] = (int) ((-1600.0 - 1700.0 * random.nextDouble()) * f_176_
                            + ground);
                mty[i_174_][i_178_ + nmv[i_174_]] = ground - 70;
                mtz[i_174_][i_178_] = i_171_ + i_172_ + is[i_174_];
                mtz[i_174_][i_178_ + nmv[i_174_]] = i_171_ + i_172_ + is[i_174_];
                float f_179_ = (float) (0.5 + random.nextDouble() * 0.5);
                mtc[i_174_][i_178_][0] = (int) (170.0F * f_179_ + 170.0F * f_179_ * (snap[0] / 100.0F));
                if (mtc[i_174_][i_178_][0] > 255)
                    mtc[i_174_][i_178_][0] = 255;
                if (mtc[i_174_][i_178_][0] < 0)
                    mtc[i_174_][i_178_][0] = 0;
                mtc[i_174_][i_178_][1] = (int) (i_175_ * f_179_ + 85.0F * f_179_ * (snap[1] / 100.0F));
                if (mtc[i_174_][i_178_][1] > 255)
                    mtc[i_174_][i_178_][1] = 255;
                if (mtc[i_174_][i_178_][1] < 1)
                    mtc[i_174_][i_178_][1] = 0;
                mtc[i_174_][i_178_][2] = 0;
            }
            for (int i_180_ = 1; i_180_ < nmv[i_174_] - 1; i_180_++) {
                int i_181_ = i_180_ - 1;
                int i_182_ = i_180_ + 1;
                mty[i_174_][i_180_] = ((mty[i_174_][i_181_] + mty[i_174_][i_182_]) / 2 + mty[i_174_][i_180_]) / 2;
            }
            Utility.rot(mtx[i_174_], mtz[i_174_], i_170_, i_171_, i_177_, nmv[i_174_] * 2);
            is_173_[i_174_] = 0;
        }
        for (int i_183_ = 0; i_183_ < nmt; i_183_++) {
            for (int i_184_ = i_183_ + 1; i_184_ < nmt; i_184_++) {
                if (is[i_183_] < is[i_184_])
                    is_173_[i_183_]++;
                else
                    is_173_[i_184_]++;
            }
            mrd[is_173_[i_183_]] = i_183_;
        }
    }

    private static void drawmountains(Graphics2D rd) {
        for (int i = 0; i < nmt; i++) {
            int i_185_ = mrd[i];
            int i_186_ = cx + (int) ((mtx[i_185_][0] - x / 30 - cx) * RadicalMath.cos(xz)
                    - (mtz[i_185_][0] - z / 30 - cz) * RadicalMath.sin(xz));
            int i_187_ = cz + (int) ((mtx[i_185_][0] - x / 30 - cx) * RadicalMath.sin(xz)
                    + (mtz[i_185_][0] - z / 30 - cz) * RadicalMath.cos(xz));
            int i_188_ = cz
                    + (int) ((mty[i_185_][0] - y / 30 - cy) * RadicalMath.sin(zy) + (i_187_ - cz) * RadicalMath.cos(zy));
            int i_189_ = cx + (int) ((mtx[i_185_][nmv[i_185_] - 1] - x / 30 - cx) * RadicalMath.cos(xz)
                    - (mtz[i_185_][nmv[i_185_] - 1] - z / 30 - cz) * RadicalMath.sin(xz));
            int i_190_ = cz + (int) ((mtx[i_185_][nmv[i_185_] - 1] - x / 30 - cx) * RadicalMath.sin(xz)
                    + (mtz[i_185_][nmv[i_185_] - 1] - z / 30 - cz) * RadicalMath.cos(xz));
            int i_191_ = cz + (int) ((mty[i_185_][nmv[i_185_] - 1] - y / 30 - cy) * RadicalMath.sin(zy)
                    + (i_190_ - cz) * RadicalMath.cos(zy));
            if (Utility.xs(i_189_, i_191_) > 0 && Utility.xs(i_186_, i_188_) < w) {
                int[] is = new int[nmv[i_185_] * 2];
                int[] is_192_ = new int[nmv[i_185_] * 2];
                int[] is_193_ = new int[nmv[i_185_] * 2];
                for (int i_194_ = 0; i_194_ < nmv[i_185_] * 2; i_194_++) {
                    is[i_194_] = mtx[i_185_][i_194_] - x / 30;
                    is_192_[i_194_] = mty[i_185_][i_194_] - y / 30;
                    is_193_[i_194_] = mtz[i_185_][i_194_] - z / 30;
                }
                int i_195_ = (int) Math.sqrt(is[nmv[i_185_] / 4] * is[nmv[i_185_] / 4]
                        + is_193_[nmv[i_185_] / 4] * is_193_[nmv[i_185_] / 4]);
                Utility.rot(is, is_193_, cx, cz, xz, nmv[i_185_] * 2);
                Utility.rot(is_192_, is_193_, cy, cz, zy, nmv[i_185_] * 2);
                int[] is_196_ = new int[4];
                int[] is_197_ = new int[4];
                boolean bool_201_;
                for (int i_202_ = 0; i_202_ < nmv[i_185_] - 1; i_202_++) {
                    int i_203_ = 0;
                    int i_204_ = 0;
                    int i_205_ = 0;
                    int i_206_ = 0;
                    bool_201_ = true;
                    for (int i_207_ = 0; i_207_ < 4; i_207_++) {
                        int i_208_ = i_207_ + i_202_;
                        if (i_207_ == 2)
                            i_208_ = i_202_ + nmv[i_185_] + 1;
                        if (i_207_ == 3)
                            i_208_ = i_202_ + nmv[i_185_];
                        is_196_[i_207_] = Utility.xs(is[i_208_], is_193_[i_208_]);
                        is_197_[i_207_] = Utility.ys(is_192_[i_208_], is_193_[i_208_], 1);
                        if (is_197_[i_207_] < 0 || is_193_[i_208_] < 10)
                            i_203_++;
                        if (is_197_[i_207_] > h || is_193_[i_208_] < 10)
                            i_204_++;
                        if (is_196_[i_207_] < 0 || is_193_[i_208_] < 10)
                            i_205_++;
                        if (is_196_[i_207_] > w || is_193_[i_208_] < 10)
                            i_206_++;
                    }
                    if (i_205_ == 4 || i_203_ == 4 || i_204_ == 4 || i_206_ == 4)
                        bool_201_ = false;
                    if (bool_201_) {
                        float f = i_195_ / 2500.0F + (8000.0F - fade[0]) / 1000.0F - 2.0F
                                - (Math.abs(y) - 250.0F) / 5000.0F;
                        if (f > 0.0F && f < 10.0F) {
                            if (f < 3.5)
                                f = 3.5F;
                            int i_209_ = (int) ((mtc[i_185_][i_202_][0] + cgrnd[0] + csky[0] * f
                                    + cfade[0] * f) / (2.0F + f * 2.0F));
                            int i_210_ = (int) ((mtc[i_185_][i_202_][1] + cgrnd[1] + csky[1] * f
                                    + cfade[1] * f) / (2.0F + f * 2.0F));
                            int i_211_ = (int) ((mtc[i_185_][i_202_][2] + cgrnd[2] + csky[2] * f
                                    + cfade[2] * f) / (2.0F + f * 2.0F));
                            rd.setColor(new Color(i_209_, i_210_, i_211_)); // Mountain Color, should make this editable in the stage code
                            rd.fillPolygon(is_196_, is_197_, 4);
                        }
                    }
                }
            }
        }
    }

    public static void newstars() {
        stx = null;
        stz = null;
        stc = null;
        bst = null;
        twn = null;
        nst = 0;
        if (lightson) {
            Random random1 = new Random((long) (Math.random() * 100000D));
            nst = 40;
            stx = new int[nst];
            stz = new int[nst];
            stc = new int[nst][2][3];
            bst = new boolean[nst];
            twn = new int[nst];
            for (int i = 0; i < nst; i++) {
                stx[i] = (int) (2000D * random1.nextDouble() - 1000D);
                stz[i] = (int) (2000D * random1.nextDouble() - 1000D);
                int j = (int) (3D * random1.nextDouble());
                if (j >= 3) {
                    j = 0;
                }
                if (j <= -1) {
                    j = 2;
                }
                int k = j + 1;
                if (random1.nextDouble() > random1.nextDouble()) {
                    k = j - 1;
                }
                if (k == 3) {
                    k = 0;
                }
                if (k == -1) {
                    k = 2;
                }
                for (int l = 0; l < 3; l++) {
                    stc[i][0][l] = 200;
                    if (j == l) {
                        stc[i][0][l] += (int) (55D * random1.nextDouble());
                    }
                    if (k == l) {
                        stc[i][0][l] += 55;
                    }
                    stc[i][0][l] = (stc[i][0][l] * 2 + csky[l]) / 3;
                    stc[i][1][l] = (stc[i][0][l] + csky[l]) / 2;
                }

                twn[i] = (int) (4D * random1.nextDouble());
                bst[i] = random1.nextDouble() > 0.80000000000000004D;
            }

        }
    }

    private static void drawstars(Graphics2D graphics2d) {
        for (int i = 0; i < nst; i++) {
            int j = cx + (int) (stx[i] * RadicalMath.cos(xz) - stz[i] * RadicalMath.sin(xz));
            int k = cz + (int) (stx[i] * RadicalMath.sin(xz) + stz[i] * RadicalMath.cos(xz));
            int l = cy + (int) (-200F * RadicalMath.cos(zy) - k * RadicalMath.sin(zy));
            int i1 = cz + (int) (-200F * RadicalMath.sin(zy) + k * RadicalMath.cos(zy));
            j = Utility.xs(j, i1);
            l = Utility.ys(l, i1, 1);
            if (j - 1 <= iw || j + 3 >= w || l - 1 <= ih || l + 3 >= h) {
                continue;
            }
            if (twn[i] == 0) {
                int j1 = (int) (3D * Math.random());
                if (j1 >= 3) {
                    j1 = 0;
                }
                if (j1 <= -1) {
                    j1 = 2;
                }
                int l1 = j1 + 1;
                if (Math.random() > Math.random()) {
                    l1 = j1 - 1;
                }
                if (l1 == 3) {
                    l1 = 0;
                }
                if (l1 == -1) {
                    l1 = 2;
                }
                for (int i2 = 0; i2 < 3; i2++) {
                    stc[i][0][i2] = 200;
                    if (j1 == i2) {
                        stc[i][0][i2] += (int) (55D * Math.random());
                    }
                    if (l1 == i2) {
                        stc[i][0][i2] += 55;
                    }
                    stc[i][0][i2] = (stc[i][0][i2] * 2 + csky[i2]) / 3;
                    stc[i][1][i2] = (stc[i][0][i2] + csky[i2]) / 2;
                }

                twn[i] = 3;
            } else {
                twn[i]--;
            }
            int k1 = 0;
            if (bst[i]) {
                k1 = 1;
            }
            graphics2d.setColor(new Color(stc[i][1][0], stc[i][1][1], stc[i][1][2]));
            graphics2d.fillRect(j - 1, l, 3 + k1, 1 + k1);
            graphics2d.fillRect(j, l - 1, 1 + k1, 3 + k1);
            graphics2d.setColor(new Color(stc[i][0][0], stc[i][0][1], stc[i][0][2]));
            graphics2d.fillRect(j, l, 1 + k1, 1 + k1);
        }

    }

    public static void d(Graphics2D graphics2d) {
        nsp = 0;
        if (zy > 90) {
            zy = 90;
        }
        if (zy < -90) {
            zy = -90;
        }
        if (xz > 360) {
            xz -= 360;
        }
        if (xz < 0) {
            xz += 360;
        }
        if (y > 0) {
            y = 0;
        }

        float t_cos = RadicalMath.cos(zy);
        float t_sin = RadicalMath.sin(zy);

        ground = 250 - y;
        int ai[] = new int[4];
        int ai1[] = new int[4];
        int i = cgrnd[0];
        int j = cgrnd[1];
        int k = cgrnd[2];
        int l = crgrnd[0];
        int i1 = crgrnd[1];
        int j1 = crgrnd[2];
        int k1 = h;
        for (int l1 = 0; l1 < 16; l1++) {
            int j2 = fade[l1];
            int l2 = ground;
            if (zy != 0) {
                l2 = cy + (int) ((ground - cy) * t_cos - (fade[l1] - cz) * t_sin);
                j2 = cz + (int) ((ground - cy) * t_sin + (fade[l1] - cz) * t_cos);
            }
            ai[0] = iw;
            ai1[0] = Utility.ys(l2, j2, 1);
            if (ai1[0] < ih) {
                ai1[0] = ih;
            }
            if (ai1[0] > h) {
                ai1[0] = h;
            }
            ai[1] = iw;
            ai1[1] = k1;
            ai[2] = w;
            ai1[2] = k1;
            ai[3] = w;
            ai1[3] = ai1[0];
            k1 = ai1[0];
            if (l1 > 0) {
                l = (l * 7 + cfade[0]) / 8;
                i1 = (i1 * 7 + cfade[1]) / 8;
                j1 = (j1 * 7 + cfade[2]) / 8;
                if (l1 < 3) {
                    i = (i * 7 + cfade[0]) / 8;
                    j = (j * 7 + cfade[1]) / 8;
                    k = (k * 7 + cfade[2]) / 8;
                } else {
                    i = l;
                    j = i1;
                    k = j1;
                }
            }
            if (ai1[0] < h && ai1[1] > ih) {
                graphics2d.setColor(new Color(i, j, k));
                graphics2d.fillPolygon(ai, ai1, 4);
            }
        }

        if (lightn != -1 && lton) {
            if (lightn < 16) {
                if (lilo > lightn + 217) {
                    lilo -= 3;
                } else {
                    lightn = (int) (16F + 16F * random());
                }
            } else if (lilo < lightn + 217) {
                lilo += 7;
            } else {
                lightn = (int) (16F * random());
            }
            csky[0] = (int) (lilo + lilo * (snap[0] / 100F));
            if (csky[0] > 255) {
                csky[0] = 255;
            }
            if (csky[0] < 0) {
                csky[0] = 0;
            }
            csky[1] = (int) (lilo + lilo * (snap[1] / 100F));
            if (csky[1] > 255) {
                csky[1] = 255;
            }
            if (csky[1] < 0) {
                csky[1] = 0;
            }
            csky[2] = (int) (lilo + lilo * (snap[2] / 100F));
            if (csky[2] > 255) {
                csky[2] = 255;
            }
            if (csky[2] < 0) {
                csky[2] = 0;
            }
        }
        i = csky[0];
        j = csky[1];
        k = csky[2];
        int i2 = i;
        int k2 = j;
        int i3 = k;
        int j3 = cy + (int) ((skyline - 700 - cy) * t_cos - (7000 - cz) * t_sin);
        int k3 = cz + (int) ((skyline - 700 - cy) * t_sin + (7000 - cz) * t_cos);
        j3 = Utility.ys(j3, k3, 1);
        int l3 = ih;
        for (int i4 = 0; i4 < 16; i4++) {
            int k4 = fade[i4];
            int i5 = skyline;
            if (zy != 0) {
                i5 = cy + (int) ((skyline - cy) * t_cos - (fade[i4] - cz) * t_sin);
                k4 = cz + (int) ((skyline - cy) * t_sin + (fade[i4] - cz) * t_cos);
            }
            ai[0] = iw;
            ai1[0] = Utility.ys(i5, k4, 1);
            if (ai1[0] > h) {
                ai1[0] = h;
            }
            if (ai1[0] < ih) {
                ai1[0] = ih;
            }
            ai[1] = iw;
            ai1[1] = l3;
            ai[2] = w;
            ai1[2] = l3;
            ai[3] = w;
            ai1[3] = ai1[0];
            l3 = ai1[0];
            if (i4 > 0) {
                i = (i * 7 + cfade[0]) / 8;
                j = (j * 7 + cfade[1]) / 8;
                k = (k * 7 + cfade[2]) / 8;
            }
            if (ai1[1] < j3) {
                i2 = i;
                k2 = j;
                i3 = k;
            }
            if (ai1[0] > ih && ai1[1] < h) {
                graphics2d.setColor(new Color(i, j, k));
                graphics2d.fillPolygon(ai, ai1, 4);
            }
        }

        ai[0] = iw;
        ai1[0] = l3;
        ai[1] = iw;
        ai1[1] = k1;
        ai[2] = w;
        ai1[2] = k1;
        ai[3] = w;
        ai1[3] = l3;
        if (ai1[0] < h && ai1[1] > ih) {
            float f = (Math.abs(y) - 250F) / (fade[0] * 2);
            if (f < 0.0F) {
                f = 0.0F;
            }
            if (f > 1.0F) {
                f = 1.0F;
            }
            i = (int) ((i * (1.0F - f) + l * (1.0F + f)) / 2.0F);
            j = (int) ((j * (1.0F - f) + i1 * (1.0F + f)) / 2.0F);
            k = (int) ((k * (1.0F - f) + j1 * (1.0F + f)) / 2.0F);
            graphics2d.setColor(new Color(i, j, k));
            graphics2d.fillPolygon(ai, ai1, 4);
        }
        if (resdown != 2) {
            for (int j4 = 1; j4 < 20; j4++) {
                int l4 = 7000;
                int j5 = skyline - 700 - j4 * 70;
                if (zy != 0 && j4 != 19) {
                    j5 = cy + (int) ((skyline - 700 - j4 * 70 - cy) * t_cos - (7000 - cz) * t_sin);
                    l4 = cz + (int) ((skyline - 700 - j4 * 70 - cy) * t_sin + (7000 - cz) * t_cos);
                }
                ai[0] = iw;
                if (j4 != 19) {
                    ai1[0] = Utility.ys(j5, l4, 1);
                    if (ai1[0] > h) {
                        ai1[0] = h;
                    }
                    if (ai1[0] < ih) {
                        ai1[0] = ih;
                    }
                } else {
                    ai1[0] = ih;
                }
                ai[1] = iw;
                ai1[1] = j3;
                ai[2] = w;
                ai1[2] = j3;
                ai[3] = w;
                ai1[3] = ai1[0];
                j3 = ai1[0];
                i2 = (int) (i2 * 0.99099999999999999D);
                k2 = (int) (k2 * 0.99099999999999999D);
                i3 = (int) (i3 * 0.998D);
                if (ai1[1] > ih && ai1[0] < h) {
                    graphics2d.setColor(new Color(i2, k2, i3));
                    graphics2d.fillPolygon(ai, ai1, 4);
                }
            }

            if (lightson) {
                drawstars(graphics2d);
            }
            drawmountains(graphics2d);
            drawclouds(graphics2d);
        }
        groundpolys(graphics2d);
        if (noelec != 0) {
            noelec--;
        }
        if (cpflik) {
            cpflik = false;
        } else {
            cpflik = true;
            elecr = random() * 15F - 6F;
        }
    }

    public static void addSp(int i, int j, int k) {
        if (nsp != 7) {
            spx[nsp] = i;
            spz[nsp] = j;
            sprad[nsp] = k;
            nsp++;
        }
    }

    public static void setSnap(int i, int j, int k) {
        snap[0] = i;
        snap[1] = j;
        snap[2] = k;
    }

    public static void adjustFade(float f) {
        if (f < 15F) {
            fade[0] = (int) (origfade - 1000F * (15F - f));
            if (fade[0] < 3000) {
                fade[0] = 3000;
            }
            fadeFrom(fade[0]);
        } else if (fade[0] != origfade) {
            fade[0] += 500;
            if (fade[0] > origfade) {
                fade[0] = origfade;
            }
            fadeFrom(fade[0]);
        }
    }

    public static void setFade(int i, int j, int k) {
        cfade[0] = (int) (i + i * (snap[0] / 100F));
        if (cfade[0] > 255) {
            cfade[0] = 255;
        }
        if (cfade[0] < 0) {
            cfade[0] = 0;
        }
        cfade[1] = (int) (j + j * (snap[1] / 100F));
        if (cfade[1] > 255) {
            cfade[1] = 255;
        }
        if (cfade[1] < 0) {
            cfade[1] = 0;
        }
        cfade[2] = (int) (k + k * (snap[2] / 100F));
        if (cfade[2] > 255) {
            cfade[2] = 255;
        }
        if (cfade[2] < 0) {
            cfade[2] = 0;
        }
    }

    public static void setSky(int i, int j, int k) {
        osky[0] = i;
        osky[1] = j;
        osky[2] = k;
        for (int l = 0; l < 3; l++) {
            clds[l] = (osky[l] * cldd[3] + cldd[l]) / (cldd[3] + 1);
            clds[l] = (int) (clds[l] + clds[l] * (snap[l] / 100F));
            if (clds[l] > 255) {
                clds[l] = 255;
            }
            if (clds[l] < 0) {
                clds[l] = 0;
            }
        }

        csky[0] = (int) (i + i * (snap[0] / 100F));
        if (csky[0] > 255) {
            csky[0] = 255;
        }
        if (csky[0] < 0) {
            csky[0] = 0;
        }
        csky[1] = (int) (j + j * (snap[1] / 100F));
        if (csky[1] > 255) {
            csky[1] = 255;
        }
        if (csky[1] < 0) {
            csky[1] = 0;
        }
        csky[2] = (int) (k + k * (snap[2] / 100F));
        if (csky[2] > 255) {
            csky[2] = 255;
        }
        if (csky[2] < 0) {
            csky[2] = 0;
        }
        float af[] = new float[3];
        Color.RGBtoHSB(csky[0], csky[1], csky[2], af);
    }

    public static void setClouds(int i, int j, int k, int l, int i1) {
        if (l < 0) {
            l = 0;
        }
        if (l > 10) {
            l = 10;
        }
        if (i1 < -1500) {
            i1 = -1500;
        }
        if (i1 > -500) {
            i1 = -500;
        }
        cldd[0] = i;
        cldd[1] = j;
        cldd[2] = k;
        cldd[3] = l;
        cldd[4] = i1;
        for (int j1 = 0; j1 < 3; j1++) {
            clds[j1] = (osky[j1] * cldd[3] + cldd[j1]) / (cldd[3] + 1);
            clds[j1] = (int) (clds[j1] + clds[j1] * (snap[j1] / 100F));
            if (clds[j1] > 255) {
                clds[j1] = 255;
            }
            if (clds[j1] < 0) {
                clds[j1] = 0;
            }
        }
    }

    public static void setGround(int i, int j, int k) {
        ogrnd[0] = i;
        ogrnd[1] = j;
        ogrnd[2] = k;
        for (int l = 0; l < 3; l++) {
            cpol[l] = (ogrnd[l] * texture[3] + texture[l]) / (1 + texture[3]);
            cpol[l] = (int) (cpol[l] + cpol[l] * (snap[l] / 100F));
            if (cpol[l] > 255) {
                cpol[l] = 255;
            }
            if (cpol[l] < 0) {
                cpol[l] = 0;
            }
        }

        cgrnd[0] = (int) (i + i * (snap[0] / 100F));
        if (cgrnd[0] > 255) {
            cgrnd[0] = 255;
        }
        if (cgrnd[0] < 0) {
            cgrnd[0] = 0;
        }
        cgrnd[1] = (int) (j + j * (snap[1] / 100F));
        if (cgrnd[1] > 255) {
            cgrnd[1] = 255;
        }
        if (cgrnd[1] < 0) {
            cgrnd[1] = 0;
        }
        cgrnd[2] = (int) (k + k * (snap[2] / 100F));
        if (cgrnd[2] > 255) {
            cgrnd[2] = 255;
        }
        if (cgrnd[2] < 0) {
            cgrnd[2] = 0;
        }
        for (int i1 = 0; i1 < 3; i1++) {
            crgrnd[i1] = (int) ((cpol[i1] * 0.98999999999999999D + cgrnd[i1]) / 2D);
        }

    }

    public static void setTexture(int i, int j, int k, int l) {
        if (l < 20) {
            l = 20;
        }
        if (l > 60) {
            l = 60;
        }
        texture[0] = i;
        texture[1] = j;
        texture[2] = k;
        texture[3] = l;
        i = (ogrnd[0] * l + i) / (1 + l);
        j = (ogrnd[1] * l + j) / (1 + l);
        k = (ogrnd[2] * l + k) / (1 + l);
        cpol[0] = (int) (i + i * (snap[0] / 100F));
        if (cpol[0] > 255) {
            cpol[0] = 255;
        }
        if (cpol[0] < 0) {
            cpol[0] = 0;
        }
        cpol[1] = (int) (j + j * (snap[1] / 100F));
        if (cpol[1] > 255) {
            cpol[1] = 255;
        }
        if (cpol[1] < 0) {
            cpol[1] = 0;
        }
        cpol[2] = (int) (k + k * (snap[2] / 100F));
        if (cpol[2] > 255) {
            cpol[2] = 255;
        }
        if (cpol[2] < 0) {
            cpol[2] = 0;
        }
        for (int i1 = 0; i1 < 3; i1++) {
            crgrnd[i1] = (int) ((cpol[i1] * 0.98999999999999999D + cgrnd[i1]) / 2D);
        }

    }

    public static void setPolys(int i, int j, int k) {
        cpol[0] = (int) (i + i * (snap[0] / 100F));
        if (cpol[0] > 255) {
            cpol[0] = 255;
        }
        if (cpol[0] < 0) {
            cpol[0] = 0;
        }
        cpol[1] = (int) (j + j * (snap[1] / 100F));
        if (cpol[1] > 255) {
            cpol[1] = 255;
        }
        if (cpol[1] < 0) {
            cpol[1] = 0;
        }
        cpol[2] = (int) (k + k * (snap[2] / 100F));
        if (cpol[2] > 255) {
            cpol[2] = 255;
        }
        if (cpol[2] < 0) {
            cpol[2] = 0;
        }
        for (int l = 0; l < 3; l++) {
            crgrnd[l] = (int) ((cpol[l] * 0.98999999999999999D + cgrnd[l]) / 2D);
        }

    }

    public static void fadeFrom(int i) {
        if (i > 8000) {
            i = 8000;
        }
        for (int j = 1; j < 17; j++) {
            fade[j - 1] = (i / 2) * (j + 1);
        }
    }

    public static void watch(ContO conto, int i) {
        if (flex != 0) {
            flex = 0;
        }
        if (td) {
            y = (int) (conto.y - 300 - 1100F * random());
            x = conto.x + (int) (((conto.x + 400) - conto.x) * RadicalMath.cos(i)
                    - ((conto.z + 5000) - conto.z) * RadicalMath.sin(i));
            z = conto.z + (int) (((conto.x + 400) - conto.x) * RadicalMath.sin(i)
                    + ((conto.z + 5000) - conto.z) * RadicalMath.cos(i));
            td = false;
        }
        char c = '\0';
        if (conto.x - x - cx > 0) {
            c = '\264';
        }
        int j = -(int) (90 + c
                + Math.atan((double) (conto.z - z) / (double) (conto.x - x - cx)) / 0.017453292519943295D);
        c = '\0';
        if (conto.y - y - cy < 0) {
            c = '\uFF4C';
        }
        int k = (int) Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx));
        int l = (int) (90 + c - Math.atan((double) k / (double) (conto.y - y - cy)) / 0.017453292519943295D);
        xz += (j - xz) / trns;
        if (trns != 1) {
            trns--;
        }
        zy += (l - zy) / 5;
        if ((int) Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx)
                + (conto.y - y - cy) * (conto.y - y - cy)) > 6000) {
            td = true;
        }
    }

    public static void around(ContO conto, boolean flag) {
        if (flex != 0) {
            flex = 0;
        }
        if (!flag) {
            if (!vert) {
                adv += 2;
            } else {
                adv -= 2;
            }
            if (adv > 900) {
                vert = true;
            }
            if (adv < -500) {
                vert = false;
            }
        } else {
            adv -= 14;
        }
        int i = 500 + adv;
        if (flag && i < 1300) {
            i = 1300;
        }
        if (i < 1000) {
            i = 1000;
        }
        y = conto.y - adv;
        if (y > 10) {
            vert = false;
        }
        x = conto.x + (int) ((conto.x - i - conto.x) * RadicalMath.cos(vxz));
        z = conto.z + (int) ((conto.x - i - conto.x) * RadicalMath.sin(vxz));
        if (!flag) {
            vxz += 2;
        } else {
            vxz += 4;
        }
        char c = '\0';
        int j = y;
        if (j > 0) {
            j = 0;
        }
        if (conto.y - j - cy < 0) {
            c = '\uFF4C';
        }
        int k = (int) Math.sqrt(((conto.z - z) + cz) * ((conto.z - z) + cz) + (conto.x - x - cx) * (conto.x - x - cx));
        int l = (int) (90 + c - Math.atan((double) k / (double) (conto.y - j - cy)) / 0.017453292519943295D);
        xz = -vxz + 90;
        if (flag) {
            l -= 15;
        }
        zy += (l - zy) / 10;
        if (trns != 5) {
            trns = 5;
        }
    }

    public static void aroundTrack(CheckPoints checkpoints) {
        if (flex != 0) {
            flex = 0;
        }
        y = -hit;
        x = cx + (int) trx + (int) (17000F * RadicalMath.cos(vxz));
        z = (int) trz + (int) (17000F * RadicalMath.sin(vxz));
        if (hit > 5000) {
            if (hit == 45000) {
                fo = 1.0F;
                zy = 67;
                atrx = (checkpoints.x[0] - trx) / 116L;
                atrz = (checkpoints.z[0] - trz) / 116L;
                focus_point = 400;
            }
            hit -= fallen;
            fallen += 7;
            trx += atrx;
            trz += atrz;
            if (hit < 17600) {
                zy -= 2;
            }
            if (fallen > 500) {
                fallen = 500;
            }
            if (hit <= 5000) {
                hit = 5000;
                fallen = 0;
            }
            vxz += 3;
        } else {
            focus_point = (int) (400F * fo);
            if (Math.abs(fo - gofo) > 0.0050000000000000001D) {
                if (fo < gofo) {
                    fo += 0.005F;
                } else {
                    fo -= 0.005F;
                }
            } else {
                gofo = (float) (0.34999999403953552D + Math.random() * 1.3D);
            }
            vxz++;
            trx -= (trx - checkpoints.x[ptr]) / 10L;
            trz -= (trz - checkpoints.z[ptr]) / 10L;
            if (ptcnt == 7) {
                ptr++;
                if (ptr == checkpoints.n) {
                    ptr = 0;
                    nrnd++;
                }
                ptcnt = 0;
            } else {
                ptcnt++;
            }
        }
        if (vxz > 360) {
            vxz -= 360;
        }
        xz = -vxz - 90;
        Math.sqrt(((trz - z) + cz) * ((trz - z) + cz)
                + (trx - x - cx) * (trx - x - cx));
        cpflik = !cpflik;
    }

    public static void follow(ContO conto, int i, int j) {
        zy = 10;
        int k = 2 + Math.abs(bcxz) / 4;
        if (k > 20) {
            k = 20;
        }
        if (j != 0) {
            if (j == 1) {
                if (bcxz < 180) {
                    bcxz += k;
                }
                if (bcxz > 180) {
                    bcxz = 180;
                }
            }
            if (j == -1) {
                if (bcxz > -180) {
                    bcxz -= k;
                }
                if (bcxz < -180) {
                    bcxz = -180;
                }
            }
        } else if (Math.abs(bcxz) > k) {
            if (bcxz > 0) {
                bcxz -= k;
            } else {
                bcxz += k;
            }
        } else if (bcxz != 0) {
            bcxz = 0;
        }
        i += bcxz;
        xz = -i;
        x = (conto.x - cx) + (int) ((-(conto.z - 800 - conto.z)) * RadicalMath.sin(i));
        z = (conto.z - cz) + (int) ((conto.z - 800 - conto.z) * RadicalMath.cos(i));
        y = conto.y - 250 - cy;
        if (trns != 1) {
            trns = 1;
        }
    }

    public static void getaround(ContO conto) {
        if (!vert) {
            adv += 2;
        } else {
            adv -= 2;
        }
        if (adv > 1700) {
            vert = true;
        }
        if (adv < -500) {
            vert = false;
        }
        if (conto.y - adv > 10) {
            vert = false;
        }
        int i = 500 + adv;
        if (i < 1000) {
            i = 1000;
        }
        int j = conto.y - adv;
        int k = conto.x + (int) ((conto.x - i - conto.x) * RadicalMath.cos(vxz));
        int l = conto.z + (int) ((conto.x - i - conto.x) * RadicalMath.sin(vxz));
        int i1 = 0;
        if (Math.abs(j - y) > fvect) {
            if (y < j) {
                y += fvect;
            } else {
                y -= fvect;
            }
        } else {
            y = j;
            i1++;
        }
        if (Math.abs(k - x) > fvect) {
            if (x < k) {
                x += fvect;
            } else {
                x -= fvect;
            }
        } else {
            x = k;
            i1++;
        }
        if (Math.abs(l - z) > fvect) {
            if (z < l) {
                z += fvect;
            } else {
                z -= fvect;
            }
        } else {
            z = l;
            i1++;
        }
        if (i1 == 3) {
            fvect = 200;
        } else {
            fvect += 2;
        }
        for (vxz += 2; vxz > 360; vxz -= 360) {
        }
        int j1 = -vxz + 90;
        char c = '\0';
        if (conto.x - x - cx > 0) {
            c = '\264';
        }
        int k1 = -(int) (90 + c
                + Math.atan((double) (conto.z - z) / (double) (conto.x - x - cx)) / 0.017453292519943295D);
        int l1 = y;
        c = '\0';
        if (l1 > 0) {
            l1 = 0;
        }
        if (conto.y - l1 - cy < 0) {
            c = '\uFF4C';
        }
        int i2 = (int) Math.sqrt(((conto.z - z) + cz) * ((conto.z - z) + cz) + (conto.x - x - cx) * (conto.x - x - cx));
        int j2 = 25;
        if (i2 != 0) {
            j2 = (int) (90 + c
                    - Math.atan((double) i2 / (double) (conto.y - l1 - cy)) / 0.017453292519943295D);
        }
        for (; j1 < 0; j1 += 360) {
        }
        for (; j1 > 360; j1 -= 360) {
        }
        for (; k1 < 0; k1 += 360) {
        }
        for (; k1 > 360; k1 -= 360) {
        }
        if ((Math.abs(j1 - k1) < 30 || Math.abs(j1 - k1) > 330) && i1 == 3) {
            if (Math.abs(j1 - xz) > 7 && Math.abs(j1 - xz) < 353) {
                if (Math.abs(j1 - xz) > 180) {
                    if (xz > j1) {
                        xz += 7;
                    } else {
                        xz -= 7;
                    }
                } else if (xz < j1) {
                    xz += 7;
                } else {
                    xz -= 7;
                }
            } else {
                xz = j1;
            }
        } else if (Math.abs(k1 - xz) > 6 && Math.abs(k1 - xz) < 354) {
            if (Math.abs(k1 - xz) > 180) {
                if (xz > k1) {
                    xz += 3;
                } else {
                    xz -= 3;
                }
            } else if (xz < k1) {
                xz += 3;
            } else {
                xz -= 3;
            }
        } else {
            xz = k1;
        }
        zy += (j2 - zy) / 10;
    }

    public static void transaround(ContO conto, ContO conto1, int i) {
        if (flex != 0) {
            flex = 0;
        }
        int j = (conto.x * (20 - i) + conto1.x * i) / 20;
        int k = (conto.y * (20 - i) + conto1.y * i) / 20;
        int l = (conto.z * (20 - i) + conto1.z * i) / 20;
        if (!vert) {
            adv += 2;
        } else {
            adv -= 2;
        }
        if (adv > 900) {
            vert = true;
        }
        if (adv < -500) {
            vert = false;
        }
        int i1 = 500 + adv;
        if (i1 < 1000) {
            i1 = 1000;
        }
        y = k - adv;
        if (y > 10) {
            vert = false;
        }
        x = j + (int) ((j - i1 - j) * RadicalMath.cos(vxz));
        z = l + (int) ((j - i1 - j) * RadicalMath.sin(vxz));
        vxz += 2;
        char c = '\0';
        int j1 = y;
        if (j1 > 0) {
            j1 = 0;
        }
        if (k - j1 - cy < 0) {
            c = '\uFF4C';
        }
        int k1 = (int) Math.sqrt(((l - z) + cz) * ((l - z) + cz) + (j - x - cx) * (j - x - cx));
        int l1 = (int) (90 + c - Math.atan((double) k1 / (double) (k - j1 - cy)) / 0.017453292519943295D);
        xz = -vxz + 90;
        zy += (l1 - zy) / 10;
        if (trns != 5) {
            trns = 5;
        }
    }
}
