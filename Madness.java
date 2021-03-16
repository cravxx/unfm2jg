import java.awt.*;

/**
 * Madness is where the stats, collisions, locations of the cars, and more, are handled.
 *
 * @author Kaffeinated, Omar Waly
 */
class Madness {

    public Stat stat;
    private final Record rpd;
    private final xtGraphics xt;
    private final boolean[] dominate;
    private final boolean[] caught;
    public int pzy;
    public int pxy;
    public float speed;
    private float forca;
    public final float[] scy;
    public final float[] scz;
    public final float[] scx;
    public boolean mtouch;
    public boolean wtouch;
    private int cntouch;
    public boolean capsized;
    private int txz;
    private int fxz;
    private int pmlt;
    private int nmlt;
    private int dcnt;
    public int skid;
    private boolean pushed;
    public boolean gtouch;
    private boolean pl;
    private boolean pr;
    private boolean pd;
    private boolean pu;
    public int loop;
    public float ucomp;
    public float dcomp;
    public float lcomp;
    public float rcomp;
    private int lxz;
    public int travxy;
    public int travzy;
    public int travxz;
    public int trcnt;
    public int capcnt;
    private int srfcnt;
    public boolean rtab;
    public boolean ftab;
    public boolean btab;
    public boolean surfer;
    public float powerup;
    private int xtpower;
    private float tilt;
    public int squash;
    private int nbsq;
    public int hitmag;
    public int cntdest;
    public boolean dest;
    public boolean newcar;
    public int pcleared;
    public int clear;
    public int nlaps;
    private int focus;
    public float power;
    public int missedcp;
    public int lastcolido;
    public int point;
    public boolean nofocus;
    private int rpdcatch;
    private boolean colidim;
    public int cn;
    public int im;
    public int mxz;
    public int cxz;
    /* variable for screen shake */
    public int shakedam;

    /**
     * universal rate that speed decreases when it exceeds swits[0][2], that is, top speed
     *
     * @author Kaffeinated
     */
    private static final float speeddec = 2.0F;

    private void regy(int i, float f, ContO conto) {
        f *= stat.dammult;
        if (f > 100F) {
            rpd.recy(i, f, mtouch, im);
            f -= 100F;
            byte byte0 = 0;
            byte byte1 = 0;
            int j = conto.zy;
            int k = conto.xy;
            for (; j < 360; j += 360)
                ;
            for (; j > 360; j -= 360)
                ;
            if (j < 210 && j > 150)
                byte0 = -1;
            if (j > 330 || j < 30)
                byte0 = 1;
            for (; k < 360; k += 360)
                ;
            for (; k > 360; k -= 360)
                ;
            if (k < 210 && k > 150)
                byte1 = -1;
            if (k > 330 || k < 30)
                byte1 = 1;
            if (byte0 * byte1 == 0)
                shakedam = (int) ((Math.abs(f) + shakedam) / 2.0F);
            if (im == 0 || colidim)
                xt.crash(f, byte1 * byte0);
            if (byte1 * byte0 == 0 || mtouch) {
                for (int l = 0; l < conto.npl; l++) {
                    float f1 = 0.0F;
                    for (int k1 = 0; k1 < conto.p[l].n; k1++)
                        if (conto.p[l].wz == 0
                                && Utility.py(conto.keyx[i], conto.p[l].ox[k1], conto.keyz[i], conto.p[l].oz[k1]) < stat.clrad) {
                            f1 = (f / 20F) * Medium.random();
                            conto.p[l].oz[k1] += f1 * RadicalMath.sin(j);
                            conto.p[l].ox[k1] -= f1 * RadicalMath.sin(k);
                            hitmag += Math.abs(f1);
                        }

                    if (f1 != 0.0F) {
                        if (Math.abs(f1) >= 1.0F) {
                            conto.p[l].chip = 1;
                            conto.p[l].ctmag = f1;
                        }
                        if (!conto.p[l].nocol && !conto.p[l].glass) {
                            if (conto.p[l].bfase > 20 && conto.p[l].hsb[1] > 0.25D)
                                conto.p[l].hsb[1] = 0.25F;
                            if (conto.p[l].bfase > 25 && conto.p[l].hsb[2] > 0.69999999999999996D)
                                conto.p[l].hsb[2] = 0.7F;
                            if (conto.p[l].bfase > 30 && conto.p[l].hsb[1] > 0.14999999999999999D)
                                conto.p[l].hsb[1] = 0.15F;
                            if (conto.p[l].bfase > 35 && conto.p[l].hsb[2] > 0.59999999999999998D)
                                conto.p[l].hsb[2] = 0.6F;
                            if (conto.p[l].bfase > 40)
                                conto.p[l].hsb[0] = 0.075F;
                            if (conto.p[l].bfase > 50 && conto.p[l].hsb[2] > 0.5D)
                                conto.p[l].hsb[2] = 0.5F;
                            if (conto.p[l].bfase > 60)
                                conto.p[l].hsb[0] = 0.05F;
                            conto.p[l].bfase += f1;
                            new Color(conto.p[l].c[0], conto.p[l].c[1], conto.p[l].c[2]);
                            Color color = Color.getHSBColor(conto.p[l].hsb[0], conto.p[l].hsb[1], conto.p[l].hsb[2]);
                            conto.p[l].c[0] = color.getRed();
                            conto.p[l].c[1] = color.getGreen();
                            conto.p[l].c[2] = color.getBlue();
                        }
                        if (conto.p[l].glass)
                            conto.p[l].gr += Math.abs(f1 * 1.5D);
                    }
                }

            }
            if (byte1 * byte0 == -1)
                if (nbsq > 0) {
                    int i1 = 0;
                    int j1 = 1;
                    for (int l1 = 0; l1 < conto.npl; l1++) {
                        float f2 = 0.0F;
                        for (int i2 = 0; i2 < conto.p[l1].n; i2++)
                            if (conto.p[l1].wz == 0) {
                                f2 = (f / 15F) * Medium.random();
                                if ((Math.abs(conto.p[l1].oy[i2] - stat.flipy - squash) < stat.msquash * 3
                                        || conto.p[l1].oy[i2] < stat.flipy + squash) && squash < stat.msquash) {
                                    conto.p[l1].oy[i2] += f2;
                                    i1 = (int) (i1 + f2);
                                    j1++;
                                    hitmag += Math.abs(f2);
                                }
                            }

                        if (conto.p[l1].glass)
                            conto.p[l1].gr += 5;
                        else if (f2 != 0.0F)
                            conto.p[l1].bfase += f2;
                        if (Math.abs(f2) >= 1.0F) {
                            conto.p[l1].chip = 1;
                            conto.p[l1].ctmag = f2;
                        }
                    }

                    squash += i1 / j1;
                    nbsq = 0;
                } else {
                    nbsq++;
                }
        }
    }

    public Madness(Record record, xtGraphics xtgraphics, int i) {
        cn = 0;
        im = 0;
        mxz = 0;
        cxz = 0;
        dominate = new boolean[51];
        caught = new boolean[51];
        pzy = 0;
        pxy = 0;
        speed = 0.0F;
        forca = 0.0F;
        scy = new float[4];
        scz = new float[4];
        scx = new float[4];
        mtouch = false;
        wtouch = false;
        cntouch = 0;
        capsized = false;
        txz = 0;
        fxz = 0;
        pmlt = 1;
        nmlt = 1;
        dcnt = 0;
        skid = 0;
        pushed = false;
        gtouch = false;
        pl = false;
        pr = false;
        pd = false;
        pu = false;
        loop = 0;
        ucomp = 0.0F;
        dcomp = 0.0F;
        lcomp = 0.0F;
        rcomp = 0.0F;
        lxz = 0;
        travxy = 0;
        travzy = 0;
        travxz = 0;
        trcnt = 0;
        capcnt = 0;
        srfcnt = 0;
        rtab = false;
        ftab = false;
        btab = false;
        surfer = false;
        powerup = 0.0F;
        xtpower = 0;
        tilt = 0.0F;
        squash = 0;
        nbsq = 0;
        hitmag = 0;
        cntdest = 0;
        dest = false;
        newcar = false;
        pcleared = 0;
        clear = 0;
        nlaps = 0;
        focus = -1;
        power = 75F;
        missedcp = 0;
        lastcolido = 0;
        point = 0;
        nofocus = false;
        rpdcatch = 0;
        colidim = false;
        rpd = record;
        xt = xtgraphics;
        im = i;
        shakedam = 0;
        stat = new Stat();
    }

    private void regz(int i, float f, ContO conto) {
        f *= stat.dammult;
        if (Math.abs(f) > 100F) {
            rpd.recz(i, f, im);
            if (f > 100F)
                f -= 100F;
            if (f < -100F)
                f += 100F;
            shakedam = (int) ((Math.abs(f) + shakedam) / 2.0F);
            if (im == 0 || colidim)
                xt.crash(f, 0);
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++)
                    if (conto.p[j].wz == 0
                            && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                        conto.p[j].oz[k] += f1 * RadicalMath.cos(conto.xz) * RadicalMath.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * RadicalMath.sin(conto.xz) * RadicalMath.cos(conto.xy);
                        hitmag += Math.abs(f1);
                    }

                if (f1 != 0.0F) {
                    if (Math.abs(f1) >= 1.0F) {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if (!conto.p[j].nocol && !conto.p[j].glass) {
                        if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.25D)
                            conto.p[j].hsb[1] = 0.25F;
                        if (conto.p[j].bfase > 25 && conto.p[j].hsb[2] > 0.69999999999999996D)
                            conto.p[j].hsb[2] = 0.7F;
                        if (conto.p[j].bfase > 30 && conto.p[j].hsb[1] > 0.14999999999999999D)
                            conto.p[j].hsb[1] = 0.15F;
                        if (conto.p[j].bfase > 35 && conto.p[j].hsb[2] > 0.59999999999999998D)
                            conto.p[j].hsb[2] = 0.6F;
                        if (conto.p[j].bfase > 40)
                            conto.p[j].hsb[0] = 0.075F;
                        if (conto.p[j].bfase > 50 && conto.p[j].hsb[2] > 0.5D)
                            conto.p[j].hsb[2] = 0.5F;
                        if (conto.p[j].bfase > 60)
                            conto.p[j].hsb[0] = 0.05F;
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if (conto.p[j].glass)
                        conto.p[j].gr += Math.abs(f1 * 1.5D);
                }
            }

        }
    }

    public void colide(ContO conto, Madness madness, ContO conto1) {
        float af[] = new float[4];
        float af1[] = new float[4];
        float af2[] = new float[4];
        float af3[] = new float[4];
        float af4[] = new float[4];
        float af5[] = new float[4];
        int i = 0;
        do {
            af[i] = conto.x + conto.keyx[i];
            if (capsized)
                af1[i] = conto.y + stat.flipy + squash;
            else
                af1[i] = conto.y + conto.grat;
            af2[i] = conto.z + conto.keyz[i];
            af3[i] = conto1.x + conto1.keyx[i];
            if (capsized)
                af4[i] = conto1.y + StatList.flipy[madness.cn] + madness.squash;
            else
                af4[i] = conto1.y + conto1.grat;
            af5[i] = conto1.z + conto1.keyz[i];
        } while (++i < 4);
        Utility.rot(af, af1, conto.x, conto.y, conto.xy, 4);
        Utility.rot(af1, af2, conto.y, conto.z, conto.zy, 4);
        Utility.rot(af, af2, conto.x, conto.z, conto.xz, 4);
        Utility.rot(af3, af4, conto1.x, conto1.y, conto1.xy, 4);
        Utility.rot(af4, af5, conto1.y, conto1.z, conto1.zy, 4);
        Utility.rot(af3, af5, conto1.x, conto1.z, conto1.xz, 4);
        if (Utility.rpy(conto.x, conto1.x, conto.y, conto1.y, conto.z,
                conto1.z) < (conto.maxR * conto.maxR + conto1.maxR * conto1.maxR) * 1.5D) {
            if (!caught[madness.im] && (speed != 0.0F || madness.speed != 0.0F)) {
                if (Math.abs(power * speed * stat.moment) != Math
                        .abs(madness.power * madness.speed * StatList.moment[madness.cn])) {
                    dominate[madness.im] = Math.abs(power * speed * stat.moment) > Math
                            .abs(madness.power * madness.speed * StatList.moment[madness.cn]);
                } else dominate[madness.im] = stat.moment > StatList.moment[madness.cn];
                caught[madness.im] = true;
            }
        } else if (caught[madness.im])
            caught[madness.im] = false;
        if (dominate[madness.im]) {
            int j = (int) (((((((((scz[0] - madness.scz[0]) + scz[1]) - madness.scz[1]) + scz[2]) - madness.scz[2])
                    + scz[3]) - madness.scz[3])
                    * (((((((scz[0] - madness.scz[0]) + scz[1]) - madness.scz[1]) + scz[2]) - madness.scz[2]) + scz[3])
                    - madness.scz[3])
                    + (((((((scx[0] - madness.scx[0]) + scx[1]) - madness.scx[1]) + scx[2]) - madness.scx[2]) + scx[3])
                    - madness.scx[3])
                    * (((((((scx[0] - madness.scx[0]) + scx[1]) - madness.scx[1]) + scx[2]) - madness.scx[2])
                    + scx[3]) - madness.scx[3]))
                    / 16F);
            int k = 0;
            do {
                int l = 0;
                do
                    if (Utility.rpy(af[k], af3[l], af1[k], af4[l], af2[k], af5[l]) < (j + 7000)
                            * (stat.comprad + stat.comprad)) {
                        if (Math.abs(scx[k] * stat.moment) > Math.abs(madness.scx[l] * StatList.moment[madness.cn])) {
                            float f = madness.scx[l] * stat.revpush;
                            if (f > 300F)
                                f = 300F;
                            if (f < -300F)
                                f = -300F;
                            float f2 = scx[k] * stat.push;
                            if (f2 > 300F)
                                f2 = 300F;
                            if (f2 < -300F)
                                f2 = -300F;
                            float f4 = 1.0F;
                            if (madness.cn == 13)
                                f4 = stat.moment;
                            madness.scx[l] += f2;
                            if (im == 0)
                                madness.colidim = true;
                            madness.regx(l, f2 * stat.moment * f4, conto1);
                            if (madness.colidim)
                                madness.colidim = false;
                            scx[k] -= f;
                            regx(k, -f * StatList.moment[cn], conto);
                            scy[k] -= stat.revlift;
                            if (im == 0)
                                madness.colidim = true;
                            madness.regy(l, stat.revlift * 7, conto1);
                            if (madness.colidim)
                                madness.colidim = false;
                        }
                        if (Math.abs(scz[k] * stat.moment) > Math.abs(madness.scz[l] * StatList.moment[madness.cn])) {
                            float f1 = madness.scz[l] * stat.revpush;
                            if (f1 > 300F)
                                f1 = 300F;
                            if (f1 < -300F)
                                f1 = -300F;
                            float f3 = scz[k] * stat.push;
                            if (f3 > 300F)
                                f3 = 300F;
                            if (f3 < -300F)
                                f3 = -300F;
                            float f5 = 1.0F;
                            if (madness.cn == 13)
                                f5 = stat.moment;
                            madness.scz[l] += f3;
                            if (im == 0)
                                madness.colidim = true;
                            madness.regz(l, f3 * stat.moment * f5, conto1);
                            if (madness.colidim)
                                madness.colidim = false;
                            scz[k] -= f1;
                            regz(k, -f1 * StatList.moment[cn], conto);
                            scy[k] -= stat.revlift;
                            if (im == 0)
                                madness.colidim = true;
                            madness.regy(l, stat.revlift * 7, conto1);
                            if (madness.colidim)
                                madness.colidim = false;
                        }
                        if (im == 0)
                            madness.lastcolido = 70;
                        if (madness.im == 0)
                            lastcolido = 70;
                        madness.scy[l] -= stat.lift;
                    }
                while (++l < 4);
            } while (++k < 4);

        }
    }

    private void distruct(ContO conto) {
        for (int i = 0; i < conto.npl; i++)
            if (conto.p[i].wz == 0 || conto.p[i].gr == -17 || conto.p[i].gr == -16)
                conto.p[i].embos = 1;

    }

    public void reseto(int i, ContO conto, CheckPoints checkpoints) {
        cn = i;
        int j = 0;
        do {
            dominate[j] = false;
            caught[j] = false;
        } while (++j < 51);
        if (cn == 11 && im == 0)
            if (checkpoints.stage == 10)
                stat.moment = 2.5F;
            else
                stat.moment = 2.0F;
        if (cn == 9 && im != 0)
            if (checkpoints.stage == 4)
                stat.maxmag = 8000;
            else
                stat.maxmag = 9700;
        mxz = 0;
        cxz = 0;
        pzy = 0;
        pxy = 0;
        speed = 0.0F;
        j = 0;
        do {
            scy[j] = 0.0F;
            scx[j] = 0.0F;
            scz[j] = 0.0F;
        } while (++j < 4);
        forca = (((float) Math.sqrt(conto.keyz[0] * conto.keyz[0] + conto.keyx[0] * conto.keyx[0])
                + (float) Math.sqrt(conto.keyz[1] * conto.keyz[1] + conto.keyx[1] * conto.keyx[1])
                + (float) Math.sqrt(conto.keyz[2] * conto.keyz[2] + conto.keyx[2] * conto.keyx[2])
                + (float) Math.sqrt(conto.keyz[3] * conto.keyz[3] + conto.keyx[3] * conto.keyx[3])) / 10000F)
                * (float) (stat.bounce - 0.29999999999999999D);
        mtouch = false;
        wtouch = false;
        txz = 0;
        fxz = 0;
        pmlt = 1;
        nmlt = 1;
        dcnt = 0;
        skid = 0;
        pushed = false;
        gtouch = false;
        pl = false;
        pr = false;
        pd = false;
        pu = false;
        loop = 0;
        ucomp = 0.0F;
        dcomp = 0.0F;
        lcomp = 0.0F;
        rcomp = 0.0F;
        lxz = 0;
        travxy = 0;
        travzy = 0;
        travxz = 0;
        rtab = false;
        ftab = false;
        btab = false;
        powerup = 0.0F;
        xtpower = 0;
        trcnt = 0;
        capcnt = 0;
        tilt = 0.0F;
        pcleared = checkpoints.pcs;
        clear = 0;
        nlaps = 0;
        focus = -1;
        missedcp = 0;
        nofocus = false;
        power = 98F;
        lastcolido = 0;
        checkpoints.dested[im] = 0;
        squash = 0;
        nbsq = 0;
        hitmag = 0;
        cntdest = 0;
        dest = false;
        newcar = false;
        if (im == 0) {
            Medium.checkpoint = -1;
            Medium.lastcheck = false;
        }
        rpdcatch = 0;
    }

    private void regx(int i, float f, ContO conto) {
        f *= stat.dammult;
        if (Math.abs(f) > 100F) {
            rpd.recx(i, f, im);
            if (f > 100F)
                f -= 100F;
            if (f < -100F)
                f += 100F;
            shakedam = (int) ((Math.abs(f) + shakedam) / 2.0F);
            if (im == 0 || colidim)
                xt.crash(f, 0);
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++)
                    if (conto.p[j].wz == 0
                            && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                        conto.p[j].oz[k] -= f1 * RadicalMath.sin(conto.xz) * RadicalMath.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * RadicalMath.cos(conto.xz) * RadicalMath.cos(conto.xy);
                        hitmag += Math.abs(f1);
                    }

                if (f1 != 0.0F) {
                    if (Math.abs(f1) >= 1.0F) {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if (!conto.p[j].nocol && !conto.p[j].glass) {
                        if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.25D)
                            conto.p[j].hsb[1] = 0.25F;
                        if (conto.p[j].bfase > 25 && conto.p[j].hsb[2] > 0.69999999999999996D)
                            conto.p[j].hsb[2] = 0.7F;
                        if (conto.p[j].bfase > 30 && conto.p[j].hsb[1] > 0.14999999999999999D)
                            conto.p[j].hsb[1] = 0.15F;
                        if (conto.p[j].bfase > 35 && conto.p[j].hsb[2] > 0.59999999999999998D)
                            conto.p[j].hsb[2] = 0.6F;
                        if (conto.p[j].bfase > 40)
                            conto.p[j].hsb[0] = 0.075F;
                        if (conto.p[j].bfase > 50 && conto.p[j].hsb[2] > 0.5D)
                            conto.p[j].hsb[2] = 0.5F;
                        if (conto.p[j].bfase > 60)
                            conto.p[j].hsb[0] = 0.05F;
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if (conto.p[j].glass)
                        conto.p[j].gr += Math.abs(f1 * 1.5D);
                }
            }

        }
    }

    public void drive(Control control, ContO conto, Trackers trackers, CheckPoints checkpoints) {
        int i = 1;
        int j = 1;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        capsized = false;
        int k;
        for (k = Math.abs(pzy); k > 270; k -= 360)
            ;
        k = Math.abs(k);
        if (k > 90)
            flag = true;
        boolean flag3 = false;
        int l;
        for (l = Math.abs(pxy); l > 270; l -= 360)
            ;
        l = Math.abs(l);
        if (l > 90) {
            flag3 = true;
            j = -1;
        }
        int i1 = conto.grat;
        if (flag) {
            if (flag3) {
                flag3 = false;
                flag1 = true;
            } else {
                flag3 = true;
                capsized = true;
            }
            i = -1;
        } else if (flag3)
            capsized = true;
        if (capsized)
            i1 = stat.flipy + squash;
        control.zyinv = flag;
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        if (mtouch)
            loop = 0;
        if (wtouch) {
            if (loop == 2 || loop == -1) {
                loop = -1;
                if (control.left)
                    pl = true;
                if (control.right)
                    pr = true;
                if (control.up)
                    pu = true;
                if (control.down)
                    pd = true;
            }
            ucomp = 0.0F;
            dcomp = 0.0F;
            lcomp = 0.0F;
            rcomp = 0.0F;
        }
        if (control.handb) {
            if (!pushed)
                if (!wtouch) {
                    if (loop == 0)
                        loop = 1;
                } else if (gtouch)
                    pushed = true;
        } else {
            pushed = false;
        }
        if (loop == 1) {
            float f3 = (scy[0] + scy[1] + scy[2] + scy[3]) / 4F;
            int j1 = 0;
            do
                scy[j1] = f3;
            while (++j1 < 4);
            loop = 2;
        }
        if (!dest)
            if (loop == 2) {
                if (control.up) {
                    if (ucomp == 0.0F) {
                        ucomp = 10F + (scy[0] + 50F) / 20F;
                        if (ucomp < 5F)
                            ucomp = 5F;
                        if (ucomp > 10F)
                            ucomp = 10F;
                        ucomp *= stat.airs;
                    }
                    if (ucomp < 20F)
                        ucomp += 0.5D * stat.airs;
                    f = (-stat.airc) * RadicalMath.sin(conto.xz) * j;
                    f1 = stat.airc * RadicalMath.cos(conto.xz) * j;
                } else if (ucomp != 0.0F && ucomp > -2F)
                    ucomp -= 0.5D * stat.airs;
                if (control.down) {
                    if (dcomp == 0.0F) {
                        dcomp = 10F + (scy[0] + 50F) / 20F;
                        if (dcomp < 5F)
                            dcomp = 5F;
                        if (dcomp > 10F)
                            dcomp = 10F;
                        dcomp *= stat.airs;
                    }
                    if (dcomp < 20F)
                        dcomp += 0.5D * stat.airs;
                    f2 = -stat.airc;
                } else if (dcomp != 0.0F && ucomp > -2F)
                    dcomp -= 0.5D * stat.airs;
                if (control.left) {
                    if (lcomp == 0.0F)
                        lcomp = 5F;
                    if (lcomp < 20F)
                        lcomp += 2.0F * stat.airs;
                    f = (-stat.airc) * RadicalMath.cos(conto.xz) * i;
                    f1 = (-stat.airc) * RadicalMath.sin(conto.xz) * i;
                } else if (lcomp > 0.0F)
                    lcomp -= 2.0F * stat.airs;
                if (control.right) {
                    if (rcomp == 0.0F)
                        rcomp = 5F;
                    if (rcomp < 20F)
                        rcomp += 2.0F * stat.airs;
                    f = stat.airc * RadicalMath.cos(conto.xz) * i;
                    f1 = stat.airc * RadicalMath.sin(conto.xz) * i;
                } else if (rcomp > 0.0F)
                    rcomp -= 2.0F * stat.airs;
                pzy += (dcomp - ucomp) * RadicalMath.cos(pxy);
                if (flag)
                    conto.xz += (dcomp - ucomp) * RadicalMath.sin(pxy);
                else
                    conto.xz -= (dcomp - ucomp) * RadicalMath.sin(pxy);
                pxy += rcomp - lcomp;
            } else {
                float f4 = power;
                if (f4 < 40F)
                    f4 = 40F;
                if (im == 0 && power != 98F)
                    if (checkpoints.stage != 6 && checkpoints.stage != 8)
                        f4 = (float) (f4 * 0.76000000000000001D);
                    else if (checkpoints.stage != 6)
                        f4 = (float) (f4 * 0.90000000000000002D);
                if (control.down)
                    if (speed > 0.0F) {
                        speed -= stat.handb / 2;
                    } else {
                        int k1 = 0;
                        int j2 = 0;
                        do
                            if (speed <= -(stat.swits[j2] / 2 + f4 * (stat.swits[j2]) / 196F))
                                k1++;
                        while (++j2 < 2);
                        if (k1 != 2)
                            speed -= stat.acelf[k1] / 2.0F + f4 * (stat.acelf[k1]) / 196F;
                        else
                            speed = -(stat.swits[1] / 2 + f4 * (stat.swits[1]) / 196F);
                    }
                if (control.up)
                    if (speed < 0.0F) {
                        speed += stat.handb;
                    } else {
                        int l1 = 0;
                        int k2 = 0;
                        do
                            if (speed >= stat.swits[k2] / 2 + (f4 * stat.swits[k2]) / 196F)
                                l1++;
                        while (++k2 < 3);
                        if (l1 != 3)
                            speed += stat.acelf[l1] / 2.0F + (f4 * stat.acelf[l1]) / 196F;
                        else
                            speed = stat.swits[2] / 2 + (f4 * stat.swits[2]) / 196F;
                    }
                if (control.handb && Math.abs(speed) > stat.handb)
                    if (speed < 0.0F)
                        speed += stat.handb;
                    else
                        speed -= stat.handb;
                if (loop == -1 && conto.y < 100) {
                    if (control.left) {
                        if (!pl) {
                            if (lcomp == 0.0F)
                                lcomp = 5F * stat.airs;
                            if (lcomp < 20F)
                                lcomp += 2.0F * stat.airs;
                        }
                    } else {
                        if (lcomp > 0.0F)
                            lcomp -= 2.0F * stat.airs;
                        pl = false;
                    }
                    if (control.right) {
                        if (!pr) {
                            if (rcomp == 0.0F)
                                rcomp = 5F * stat.airs;
                            if (rcomp < 20F)
                                rcomp += 2.0F * stat.airs;
                        }
                    } else {
                        if (rcomp > 0.0F)
                            rcomp -= 2.0F * stat.airs;
                        pr = false;
                    }
                    if (control.up) {
                        if (!pu) {
                            if (ucomp == 0.0F)
                                ucomp = 5F * stat.airs;
                            if (ucomp < 20F)
                                ucomp += 2.0F * stat.airs;
                        }
                    } else {
                        if (ucomp > 0.0F)
                            ucomp -= 2.0F * stat.airs;
                        pu = false;
                    }
                    if (control.down) {
                        if (!pd) {
                            if (dcomp == 0.0F)
                                dcomp = 5F * stat.airs;
                            if (dcomp < 20F)
                                dcomp += 2.0F * stat.airs;
                        }
                    } else {
                        if (dcomp > 0.0F)
                            dcomp -= 2.0F * stat.airs;
                        pd = false;
                    }
                    pzy += (dcomp - ucomp) * RadicalMath.cos(pxy);
                    if (flag)
                        conto.xz += (dcomp - ucomp) * RadicalMath.sin(pxy);
                    else
                        conto.xz -= (dcomp - ucomp) * RadicalMath.sin(pxy);
                    pxy += rcomp - lcomp;
                }
            }
        float f5 = (20F * speed) / (154F * stat.simag);
        if (f5 > 20F)
            f5 = 20F;
        conto.wzy -= f5;
        if (conto.wzy < -45)
            conto.wzy += 45;
        if (conto.wzy > 45)
            conto.wzy -= 45;
        if (control.right) {
            conto.wxz -= stat.turn;
            if (conto.wxz < -36)
                conto.wxz = -36;
        }
        if (control.left) {
            conto.wxz += stat.turn;
            if (conto.wxz > 36)
                conto.wxz = 36;
        }
        if (conto.wxz != 0 && !control.left && !control.right)
            if (Math.abs(speed) < 10F) {
                if (Math.abs(conto.wxz) == 1)
                    conto.wxz = 0;
                if (conto.wxz > 0)
                    conto.wxz--;
                if (conto.wxz < 0)
                    conto.wxz++;
            } else {
                if (Math.abs(conto.wxz) < stat.turn * 2)
                    conto.wxz = 0;
                if (conto.wxz > 0)
                    conto.wxz -= stat.turn * 2;
                if (conto.wxz < 0)
                    conto.wxz += stat.turn * 2;
            }
        int i2 = (int) (3600F / (speed * speed));
        if (i2 < 5)
            i2 = 5;
        if (speed < 0.0F)
            i2 = -i2;
        if (wtouch) {
            if (!capsized) {
                if (!control.handb)
                    fxz = conto.wxz / (i2 * 3);
                else
                    fxz = conto.wxz / i2;
                conto.xz += conto.wxz / i2;
            }
            wtouch = false;
            gtouch = false;
        } else {
            conto.xz += fxz;
        }
        if (speed > 30F || speed < -100F) {
            do {
                if (Math.abs(mxz - cxz) <= 180)
                    break;
                if (cxz > mxz)
                    cxz -= 360;
                else if (cxz < mxz)
                    cxz += 360;
            } while (true);
            if (Math.abs(mxz - cxz) < 30) {
                cxz += (mxz - cxz) / 4F;
            } else {
                if (cxz > mxz)
                    cxz -= 10;
                if (cxz < mxz)
                    cxz += 10;
            }
        }
        float af[] = new float[4];
        float af1[] = new float[4];
        float af2[] = new float[4];
        int l2 = 0;
        do {
            af[l2] = conto.keyx[l2] + conto.x;
            af2[l2] = i1 + conto.y;
            af1[l2] = conto.z + conto.keyz[l2];
            scy[l2] += 7F;
        } while (++l2 < 4);
        Utility.rot(af, af2, conto.x, conto.y, pxy, 4);
        Utility.rot(af2, af1, conto.y, conto.z, pzy, 4);
        Utility.rot(af, af1, conto.x, conto.z, conto.xz, 4);
        boolean flag4 = false;
        int i3 = (int) ((scx[0] + scx[1] + scx[2] + scx[3]) / 4F);
        int j3 = (int) ((scz[0] + scz[1] + scz[2] + scz[3]) / 4F);
        int k3 = 0;
        do {
            if (scx[k3] - i3 > 200F)
                scx[k3] = 200 + i3;
            if (scx[k3] - i3 < -200F)
                scx[k3] = i3 - 200;
            if (scz[k3] - j3 > 200F)
                scz[k3] = 200 + j3;
            if (scz[k3] - j3 < -200F)
                scz[k3] = j3 - 200;
        } while (++k3 < 4);
        k3 = 0;
        do {
            af2[k3] += scy[k3];
            af[k3] += (scx[0] + scx[1] + scx[2] + scx[3]) / 4F;
            af1[k3] += (scz[0] + scz[1] + scz[2] + scz[3]) / 4F;
        } while (++k3 < 4);
        k3 = 1;
        for (int l3 = 0; l3 < trackers.nt; l3++)
            if (Math.abs(trackers.zy[l3]) != 90 && Math.abs(trackers.xy[l3]) != 90
                    && Math.abs(conto.x - trackers.x[l3]) < trackers.radx[l3]
                    && Math.abs(conto.z - trackers.z[l3]) < trackers.radz[l3])
                k3 = trackers.skd[l3];

        if (mtouch) {
            float f6 = stat.grip;
            f6 -= (Math.abs(txz - conto.xz) * speed) / 250F;
            if (control.handb)
                f6 -= Math.abs(txz - conto.xz) * 4;
            if (f6 < stat.grip) {
                if (skid != 2)
                    skid = 1;
                speed -= speed / 100F;
            } else if (skid == 1)
                skid = 2;
            if (k3 == 1)
                f6 = (float) (f6 * 0.75D);
            if (k3 == 2)
                f6 = (float) (f6 * 0.55000000000000004D);
            int j4 = -(int) (speed * RadicalMath.sin(conto.xz) * RadicalMath.cos(pzy));
            int k4 = (int) (speed * RadicalMath.cos(conto.xz) * RadicalMath.cos(pzy));
            int i5 = -(int) (speed * RadicalMath.sin(pzy));

            if (!control.up) {
                if (speed <= stat.swits[2]) {
                    if (speed > 0.0F)
                        speed -= 0.5F;
                } else {
                    if (speed > 0.0F)
                        speed -= speeddec;
                }
            }

            if (capsized || dest || checkpoints.haltall) {
                j4 = 0;
                k4 = 0;
                i5 = 0;
                f6 = stat.grip / 5F;
                if (speed > 0.0F)
                    speed -= 2.0F;
                else
                    speed += 2.0F;
            }
            if (f6 < 1.0F)
                f6 = 1.0F;
            float f9 = 0.0F;
            float f10 = 0.0F;
            int l6 = 0;
            do {
                if (Math.abs(scx[l6] - j4) > f6) {
                    if (scx[l6] < j4)
                        scx[l6] += f6;
                    else
                        scx[l6] -= f6;
                } else {
                    scx[l6] = j4;
                }
                if (Math.abs(scz[l6] - k4) > f6) {
                    if (scz[l6] < k4)
                        scz[l6] += f6;
                    else
                        scz[l6] -= f6;
                } else {
                    scz[l6] = k4;
                }
                if (Math.abs(scy[l6] - i5) > f6) {
                    if (scy[l6] < i5)
                        scy[l6] += f6;
                    else
                        scy[l6] -= f6;
                } else {
                    scy[l6] = i5;
                }
                if (f6 < stat.grip) {
                    if (txz != conto.xz)
                        dcnt++;
                    else if (dcnt != 0)
                        dcnt = 0;
                    if (dcnt > (40F * f6) / stat.grip || capsized) {
                        float f11 = 1.0F;
                        if (k3 != 0)
                            f11 = 1.2F;
                        if (Medium.random() > 0.75D) {
                            conto.dust(l6, af[l6], af2[l6], af1[l6], scx[l6], scz[l6], f11 * stat.simag, true,
                                    (int) tilt);
                            if (im == 0 && !capsized)
                                xt.skid(k3, (float) Math.sqrt(scx[l6] * scx[l6] + scz[l6] * scz[l6]));
                        }
                    } else {
                        if (k3 == 1 && Medium.random() > 0.84999999999999998D)
                            conto.dust(l6, af[l6], af2[l6], af1[l6], scx[l6], scz[l6], 1.1F * stat.simag, false,
                                    (int) tilt);
                        if ((k3 == 2 || k3 == 3) && Medium.random() > 0.69999999999999996D)
                            conto.dust(l6, af[l6], af2[l6], af1[l6], scx[l6], scz[l6], 1.15F * stat.simag, false,
                                    (int) tilt);
                    }
                } else if (dcnt != 0) {
                    dcnt -= 2;
                    if (dcnt < 0)
                        dcnt = 0;
                }
                if (k3 == 3) {
                    int k7 = (int) (Medium.random() * 4F);
                    scy[k7] = (float) (-100F * Medium.random() * (speed / stat.swits[2])
                            * (stat.bounce - 0.29999999999999999D));
                }
                if (k3 == 4) {
                    int l7 = (int) (Medium.random() * 4F);
                    scy[l7] = (float) (-150F * Medium.random() * (speed / stat.swits[2])
                            * (stat.bounce - 0.29999999999999999D));
                }
                f9 += scx[l6];
                f10 += scz[l6];
            } while (++l6 < 4);
            txz = conto.xz;
            if (f9 > 0.0F)
                i = -1;
            else
                i = 1;
            double d1 = f10 / Math.sqrt(f9 * f9 + f10 * f10);
            mxz = (int) ((Math.acos(d1) / 0.017453292519943295D) * i);
            if (skid == 2) {
                if (!capsized) {
                    f9 /= 4F;
                    f10 /= 4F;
                    if (flag1)
                        speed = -((float) Math.sqrt(f9 * f9 + f10 * f10) * RadicalMath.cos(mxz - conto.xz));
                    else
                        speed = (float) Math.sqrt(f9 * f9 + f10 * f10) * RadicalMath.cos(mxz - conto.xz);
                }
                skid = 0;
            }
            if (capsized && f9 == 0.0F && f10 == 0.0F)
                k3 = 0;
            mtouch = false;
            flag4 = true;
        } else if (skid != 2)
            skid = 2;
        int i4 = 0;
        boolean aflag[] = new boolean[4];
        int l4 = 0;
        do {
            if (af2[l4] > 245F) {
                i4++;
                wtouch = true;
                gtouch = true;
                if (!flag4 && scy[l4] != 7F) {
                    float f7 = scy[l4] / 333.33F;
                    if (f7 > 0.29999999999999999D)
                        f7 = 0.3F;
                    if (k3 == 0)
                        f7 = (float) (f7 + 1.1000000000000001D);
                    else
                        f7 = (float) (f7 + 1.2D);
                    conto.dust(l4, af[l4], af2[l4], af1[l4], scx[l4], scz[l4], f7 * stat.simag, true, 0);
                }
                af2[l4] = 250F;
                float f8 = 0.0F;
                do
                    if (l4 != f8 && af2[(int) f8] <= 245F)
                        af2[(int) f8] -= af2[l4] - 250F;
                while (++f8 < 4F);
                f8 = Math.abs(RadicalMath.sin(pxy)) + Math.abs(RadicalMath.sin(pzy));
                f8 /= 3F;
                if (f8 > 0.40000000000000002D)
                    f8 = 0.4F;
                f8 += stat.bounce;
                if (f8 < 1.1000000000000001D)
                    f8 = 1.1F;
                regy(l4, Math.abs(scy[l4] * f8), conto);
                if (scy[l4] > 0.0F)
                    scy[l4] -= Math.abs(scy[l4] * f8);
            }
            aflag[l4] = false;
        } while (++l4 < 4);
        l4 = 0;
        for (int j5 = 0; j5 < trackers.nt; j5++) {
            int l5 = 0;
            int j6 = 0;
            int i7 = 0;
            do
                if (!aflag[i7] && af[i7] > trackers.x[j5] - trackers.radx[j5]
                        && af[i7] < trackers.x[j5] + trackers.radx[j5]
                        && af1[i7] > trackers.z[j5] - trackers.radz[j5]
                        && af1[i7] < trackers.z[j5] + trackers.radz[j5]
                        && af2[i7] > trackers.y[j5] - trackers.rady[j5]
                        && af2[i7] < trackers.y[j5] + trackers.rady[j5]) {
                    if (trackers.xy[j5] == 0 && trackers.zy[j5] == 0 && trackers.y[j5] != 250
                            && af2[i7] > trackers.y[j5] - 5) {
                        j6++;
                        wtouch = true;
                        gtouch = true;
                        if (!flag4 && scy[i7] != 7F) {
                            float f12 = scy[i7] / 333.33F;
                            if (f12 > 0.29999999999999999D)
                                f12 = 0.3F;
                            if (k3 == 0)
                                f12 = (float) (f12 + 1.1000000000000001D);
                            else
                                f12 = (float) (f12 + 1.2D);
                            conto.dust(i7, af[i7], af2[i7], af1[i7], scx[i7], scz[i7], f12 * stat.simag, true, 0);
                        }
                        af2[i7] = trackers.y[j5];
                        float f13 = 0.0F;
                        do
                            if (i7 != f13 && af2[(int) f13] <= trackers.y[j5] - 5)
                                af2[(int) f13] -= af2[i7] - trackers.y[j5];
                        while (++f13 < 4F);
                        f13 = Math.abs(RadicalMath.sin(pxy)) + Math.abs(RadicalMath.sin(pzy));
                        f13 /= 3F;
                        if (f13 > 0.40000000000000002D)
                            f13 = 0.4F;
                        f13 += stat.bounce;
                        if (f13 < 1.1000000000000001D)
                            f13 = 1.1F;
                        regy(i7, Math.abs(scy[i7] * f13), conto);
                        if (scy[i7] > 0.0F)
                            scy[i7] -= Math.abs(scy[i7] * f13);
                        aflag[i7] = true;
                    }
                    if (trackers.zy[j5] == -90 && af1[i7] < trackers.z[j5] + trackers.radz[j5]
                            && scz[i7] < 0.0F) {
                        af1[i7] = trackers.z[j5] + trackers.radz[j5];
                        float f14 = 0.0F;
                        do
                            if (i7 != f14 && af1[(int) f14] >= trackers.z[j5] + trackers.radz[j5])
                                af1[(int) f14] -= af1[i7] - (trackers.z[j5] + trackers.radz[j5]);
                        while (++f14 < 4F);
                        f14 = Math.abs(RadicalMath.cos(pxy)) + Math.abs(RadicalMath.cos(pzy));
                        f14 /= 4F;
                        if (f14 > 0.29999999999999999D)
                            f14 = 0.3F;
                        if (flag4)
                            f14 = 0.0F;
                        f14 = (float) (f14 + (stat.bounce - 0.20000000000000001D));
                        if (f14 < 1.1000000000000001D)
                            f14 = 1.1F;
                        regz(i7, Math.abs(scz[i7] * f14 * trackers.dam[j5]), conto);
                        scz[i7] += Math.abs(scz[i7] * f14);
                        skid = 2;
                        flag2 = true;
                        aflag[i7] = true;
                        control.wall = j5;
                    }
                    if (trackers.zy[j5] == 90 && af1[i7] > trackers.z[j5] - trackers.radz[j5]
                            && scz[i7] > 0.0F) {
                        af1[i7] = trackers.z[j5] - trackers.radz[j5];
                        float f15 = 0.0F;
                        do
                            if (i7 != f15 && af1[(int) f15] <= trackers.z[j5] - trackers.radz[j5])
                                af1[(int) f15] -= af1[i7] - (trackers.z[j5] - trackers.radz[j5]);
                        while (++f15 < 4F);
                        f15 = Math.abs(RadicalMath.cos(pxy)) + Math.abs(RadicalMath.cos(pzy));
                        f15 /= 4F;
                        if (f15 > 0.29999999999999999D)
                            f15 = 0.3F;
                        if (flag4)
                            f15 = 0.0F;
                        f15 = (float) (f15 + (stat.bounce - 0.20000000000000001D));
                        if (f15 < 1.1000000000000001D)
                            f15 = 1.1F;
                        regz(i7, -Math.abs(scz[i7] * f15 * trackers.dam[j5]), conto);
                        scz[i7] -= Math.abs(scz[i7] * f15);
                        skid = 2;
                        flag2 = true;
                        aflag[i7] = true;
                        control.wall = j5;
                    }
                    if (trackers.xy[j5] == -90 && af[i7] < trackers.x[j5] + trackers.radx[j5]
                            && scx[i7] < 0.0F) {
                        af[i7] = trackers.x[j5] + trackers.radx[j5];
                        float f16 = 0.0F;
                        do
                            if (i7 != f16 && af[(int) f16] >= trackers.x[j5] + trackers.radx[j5])
                                af[(int) f16] -= af[i7] - (trackers.x[j5] + trackers.radx[j5]);
                        while (++f16 < 4F);
                        f16 = Math.abs(RadicalMath.cos(pxy)) + Math.abs(RadicalMath.cos(pzy));
                        f16 /= 4F;
                        if (f16 > 0.29999999999999999D)
                            f16 = 0.3F;
                        if (flag4)
                            f16 = 0.0F;
                        f16 = (float) (f16 + (stat.bounce - 0.20000000000000001D));
                        if (f16 < 1.1000000000000001D)
                            f16 = 1.1F;
                        regx(i7, Math.abs(scx[i7] * f16 * trackers.dam[j5]), conto);
                        scx[i7] += Math.abs(scx[i7] * f16);
                        skid = 2;
                        flag2 = true;
                        aflag[i7] = true;
                        control.wall = j5;
                    }
                    if (trackers.xy[j5] == 90 && af[i7] > trackers.x[j5] - trackers.radx[j5]
                            && scx[i7] > 0.0F) {
                        af[i7] = trackers.x[j5] - trackers.radx[j5];
                        float f17 = 0.0F;
                        do
                            if (i7 != f17 && af[(int) f17] <= trackers.x[j5] - trackers.radx[j5])
                                af[(int) f17] -= af[i7] - (trackers.x[j5] - trackers.radx[j5]);
                        while (++f17 < 4F);
                        f17 = Math.abs(RadicalMath.cos(pxy)) + Math.abs(RadicalMath.cos(pzy));
                        f17 /= 4F;
                        if (f17 > 0.29999999999999999D)
                            f17 = 0.3F;
                        if (flag4)
                            f17 = 0.0F;
                        f17 = (float) (f17 + (stat.bounce - 0.20000000000000001D));
                        if (f17 < 1.1000000000000001D)
                            f17 = 1.1F;
                        regx(i7, -Math.abs(scx[i7] * f17 * trackers.dam[j5]), conto);
                        scx[i7] -= Math.abs(scx[i7] * f17);
                        skid = 2;
                        flag2 = true;
                        aflag[i7] = true;
                        control.wall = j5;
                    }
                    if (trackers.zy[j5] != 0 && trackers.zy[j5] != 90 && trackers.zy[j5] != -90) {
                        int l7 = 90 + trackers.zy[j5];
                        float f19 = 1.0F + (50 - Math.abs(trackers.zy[j5])) / 30F;
                        if (f19 < 1.0F)
                            f19 = 1.0F;
                        float f21 = trackers.y[j5] + ((af2[i7] - trackers.y[j5]) * RadicalMath.cos(l7)
                                - (af1[i7] - trackers.z[j5]) * RadicalMath.sin(l7));
                        float f23 = trackers.z[j5] + ((af2[i7] - trackers.y[j5]) * RadicalMath.sin(l7)
                                + (af1[i7] - trackers.z[j5]) * RadicalMath.cos(l7));
                        if (f23 > trackers.z[j5] && f23 < trackers.z[j5] + 200) {
                            scy[i7] -= (f23 - trackers.z[j5]) / f19;
                            f23 = trackers.z[j5];
                        }
                        if (f23 > trackers.z[j5] - 30) {
                            if (trackers.skd[j5] == 2)
                                l5++;
                            else
                                l4++;
                            wtouch = true;
                            gtouch = false;
                            if (!flag4 && k3 != 0) {
                                float f25 = 1.4F;
                                conto.dust(i7, af[i7], af2[i7], af1[i7], scx[i7], scz[i7], f25 * stat.simag, true, 0);
                            }
                        }
                        af2[i7] = trackers.y[j5] + ((f21 - trackers.y[j5]) * RadicalMath.cos(-l7)
                                - (f23 - trackers.z[j5]) * RadicalMath.sin(-l7));
                        af1[i7] = trackers.z[j5] + ((f21 - trackers.y[j5]) * RadicalMath.sin(-l7)
                                + (f23 - trackers.z[j5]) * RadicalMath.cos(-l7));
                        aflag[i7] = true;
                    }
                    if (trackers.xy[j5] != 0 && trackers.xy[j5] != 90 && trackers.xy[j5] != -90) {
                        int i8 = 90 + trackers.xy[j5];
                        float f20 = 1.0F + (50 - Math.abs(trackers.xy[j5])) / 30F;
                        if (f20 < 1.0F)
                            f20 = 1.0F;
                        float f22 = trackers.y[j5] + ((af2[i7] - trackers.y[j5]) * RadicalMath.cos(i8)
                                - (af[i7] - trackers.x[j5]) * RadicalMath.sin(i8));
                        float f24 = trackers.x[j5] + ((af2[i7] - trackers.y[j5]) * RadicalMath.sin(i8)
                                + (af[i7] - trackers.x[j5]) * RadicalMath.cos(i8));
                        if (f24 > trackers.x[j5] && f24 < trackers.x[j5] + 200) {
                            scy[i7] -= (f24 - trackers.x[j5]) / f20;
                            f24 = trackers.x[j5];
                        }
                        if (f24 > trackers.x[j5] - 30) {
                            if (trackers.skd[j5] == 2)
                                l5++;
                            else
                                l4++;
                            wtouch = true;
                            gtouch = false;
                            if (!flag4 && k3 != 0) {
                                float f26 = 1.4F;
                                conto.dust(i7, af[i7], af2[i7], af1[i7], scx[i7], scz[i7], f26 * stat.simag, true, 0);
                            }
                        }
                        af2[i7] = trackers.y[j5] + ((f22 - trackers.y[j5]) * RadicalMath.cos(-i8)
                                - (f24 - trackers.x[j5]) * RadicalMath.sin(-i8));
                        af[i7] = trackers.x[j5] + ((f22 - trackers.y[j5]) * RadicalMath.sin(-i8)
                                + (f24 - trackers.x[j5]) * RadicalMath.cos(-i8));
                        aflag[i7] = true;
                    }
                }
            while (++i7 < 4);
            if (l5 == 4)
                mtouch = true;
            if (j6 == 4)
                i4 = 4;
        }

        if (l4 == 4)
            mtouch = true;
        int k5 = 0;
        int i6 = 0;
        int k6 = 0;
        int j7 = 0;
        if (scy[2] != scy[0]) {
            if (scy[2] < scy[0])
                i = -1;
            else
                i = 1;
            double d2 = Math
                    .sqrt((af1[0] - af1[2]) * (af1[0] - af1[2]) + (af2[0] - af2[2]) * (af2[0] - af2[2])
                            + (af[0] - af[2]) * (af[0] - af[2]))
                    / (Math.abs(conto.keyz[0]) + Math.abs(conto.keyz[2]));
            if (d2 >= 0.99980000000000002D)
                k5 = i;
            else
                k5 = (int) ((Math.acos(d2) / 0.017453292519943295D) * i);
        }
        if (scy[3] != scy[1]) {
            if (scy[3] < scy[1])
                i = -1;
            else
                i = 1;
            double d3 = Math
                    .sqrt((af1[1] - af1[3]) * (af1[1] - af1[3]) + (af2[1] - af2[3]) * (af2[1] - af2[3])
                            + (af[1] - af[3]) * (af[1] - af[3]))
                    / (Math.abs(conto.keyz[1]) + Math.abs(conto.keyz[3]));
            if (d3 >= 0.99980000000000002D)
                i6 = i;
            else
                i6 = (int) ((Math.acos(d3) / 0.017453292519943295D) * i);
        }
        if (scy[1] != scy[0]) {
            if (scy[1] < scy[0])
                i = -1;
            else
                i = 1;
            double d4 = Math
                    .sqrt((af1[0] - af1[1]) * (af1[0] - af1[1]) + (af2[0] - af2[1]) * (af2[0] - af2[1])
                            + (af[0] - af[1]) * (af[0] - af[1]))
                    / (Math.abs(conto.keyx[0]) + Math.abs(conto.keyx[1]));
            if (d4 >= 0.99980000000000002D)
                k6 = i;
            else
                k6 = (int) ((Math.acos(d4) / 0.017453292519943295D) * i);
        }
        if (scy[3] != scy[2]) {
            if (scy[3] < scy[2])
                i = -1;
            else
                i = 1;
            double d5 = Math
                    .sqrt((af1[2] - af1[3]) * (af1[2] - af1[3]) + (af2[2] - af2[3]) * (af2[2] - af2[3])
                            + (af[2] - af[3]) * (af[2] - af[3]))
                    / (Math.abs(conto.keyx[2]) + Math.abs(conto.keyx[3]));
            if (d5 >= 0.99980000000000002D)
                j7 = i;
            else
                j7 = (int) ((Math.acos(d5) / 0.017453292519943295D) * i);
        }
        if (flag2) {
            int j8;
            for (j8 = Math.abs(conto.xz + 45); j8 > 180; j8 -= 360)
                ;
            if (Math.abs(j8) > 90)
                pmlt = 1;
            else
                pmlt = -1;
            for (j8 = Math.abs(conto.xz - 45); j8 > 180; j8 -= 360)
                ;
            if (Math.abs(j8) > 90)
                nmlt = 1;
            else
                nmlt = -1;
        }
        conto.xz += forca
                * (((((scz[0] * nmlt - scz[1] * pmlt) + scz[2] * pmlt) - scz[3] * nmlt)
                + scx[0] * pmlt + scx[1] * nmlt) - scx[2] * nmlt
                - scx[3] * pmlt);
        if (Math.abs(i6) > Math.abs(k5))
            k5 = i6;
        if (Math.abs(j7) > Math.abs(k6))
            k6 = j7;
        if (!flag)
            pzy += k5;
        else
            pzy -= k5;
        if (!flag3)
            pxy += k6;
        else
            pxy -= k6;
        if (i4 == 4) {
            int k8 = 0;
            while (pzy < 360) {
                pzy += 360;
                conto.zy += 360;
            }
            while (pzy > 360) {
                pzy -= 360;
                conto.zy -= 360;
            }
            if (pzy < 190 && pzy > 170) {
                pzy = 180;
                conto.zy = 180;
                k8++;
            }
            if (pzy > 350 || pzy < 10) {
                pzy = 0;
                conto.zy = 0;
                k8++;
            }
            while (pxy < 360) {
                pxy += 360;
                conto.xy += 360;
            }
            while (pxy > 360) {
                pxy -= 360;
                conto.xy -= 360;
            }
            if (pxy < 190 && pxy > 170) {
                pxy = 180;
                conto.xy = 180;
                k8++;
            }
            if (pxy > 350 || pxy < 10) {
                pxy = 0;
                conto.xy = 0;
                k8++;
            }
            if (k8 == 2)
                mtouch = true;
        }
        if (!mtouch && wtouch) {
            if (cntouch == 10)
                mtouch = true;
            else
                cntouch++;
        } else {
            cntouch = 0;
        }
        conto.y = (int) (((af2[0] + af2[1] + af2[2] + af2[3]) / 4F - i1 * RadicalMath.cos(pzy) * RadicalMath.cos(pxy)) + f2);
        if (flag)
            i = -1;
        else
            i = 1;
        conto.x = (int) (((((((((((af[0] - conto.keyx[0] * RadicalMath.cos(conto.xz))
                + i * conto.keyz[0] * RadicalMath.sin(conto.xz) + af[1]) - conto.keyx[1] * RadicalMath.cos(conto.xz))
                + i * conto.keyz[1] * RadicalMath.sin(conto.xz) + af[2]) - conto.keyx[2] * RadicalMath.cos(conto.xz))
                + i * conto.keyz[2] * RadicalMath.sin(conto.xz) + af[3]) - conto.keyx[3] * RadicalMath.cos(conto.xz))
                + i * conto.keyz[3] * RadicalMath.sin(conto.xz)) / 4F + i1 * RadicalMath.sin(pxy) * RadicalMath.cos(conto.xz))
                - i1 * RadicalMath.sin(pzy) * RadicalMath.sin(conto.xz)) + f);
        conto.z = (int) ((((((((((af1[0] - i * conto.keyz[0] * RadicalMath.cos(conto.xz)
                - conto.keyx[0] * RadicalMath.sin(conto.xz)) + af1[1]) - i * conto.keyz[1] * RadicalMath.cos(conto.xz)
                - conto.keyx[1] * RadicalMath.sin(conto.xz)) + af1[2]) - i * conto.keyz[2] * RadicalMath.cos(conto.xz)
                - conto.keyx[2] * RadicalMath.sin(conto.xz)) + af1[3]) - i * conto.keyz[3] * RadicalMath.cos(conto.xz)
                - conto.keyx[3] * RadicalMath.sin(conto.xz)) / 4F + i1 * RadicalMath.sin(pxy) * RadicalMath.sin(conto.xz))
                - i1 * RadicalMath.sin(pzy) * RadicalMath.cos(conto.xz)) + f1);
        if (Math.abs(speed) > 10F || !mtouch) {
            if (Math.abs(pxy - conto.xy) >= 4) {
                if (pxy > conto.xy)
                    conto.xy += 2 + (pxy - conto.xy) / 2;
                else
                    conto.xy -= 2 + (conto.xy - pxy) / 2;
            } else {
                conto.xy = pxy;
            }
            if (Math.abs(pzy - conto.zy) >= 4) {
                if (pzy > conto.zy)
                    conto.zy += 2 + (pzy - conto.zy) / 2;
                else
                    conto.zy -= 2 + (conto.zy - pzy) / 2;
            } else {
                conto.zy = pzy;
            }
        }
        if (wtouch && !capsized) {
            float f18 = (float) ((speed / stat.swits[2]) * 14F
                    * (stat.bounce - 0.40000000000000002D));
            if (control.left && tilt < f18 && tilt >= 0.0F)
                tilt += 0.40000000000000002D;
            else if (control.right && tilt > -f18 && tilt <= 0.0F)
                tilt -= 0.40000000000000002D;
            else if (Math.abs(tilt) > 3D * (stat.bounce - 0.40000000000000002D)) {
                if (tilt > 0.0F)
                    tilt -= 3D * (stat.bounce - 0.29999999999999999D);
                else
                    tilt += 3D * (stat.bounce - 0.29999999999999999D);
            } else {
                tilt = 0.0F;
            }
            conto.xy += tilt;
            if (gtouch)
                conto.y -= tilt / 1.5D;
        } else if (tilt != 0.0F)
            tilt = 0.0F;
        if (wtouch && k3 == 2) {
            conto.zy += (int) (((Medium.random() * 25F * speed) / stat.swits[2]
                    - (15F * speed) / stat.swits[2]) * (stat.bounce - 0.99999999999999989D));
            conto.xy += (int) (((Medium.random() * 25F * speed) / stat.swits[2]
                    - (15F * speed) / stat.swits[2]) * (stat.bounce - 0.99999999999999989D));
        }
        if (wtouch && k3 == 1) {
            conto.zy += (int) (((Medium.random() * 20F * speed) / stat.swits[2]
                    - (10F * speed) / stat.swits[2]) * (stat.bounce - 0.99999999999999989D));
            conto.xy += (int) (((Medium.random() * 20F * speed) / stat.swits[2]
                    - (10F * speed) / stat.swits[2]) * (stat.bounce - 0.99999999999999989D));
        }
        if (hitmag > stat.maxmag && !dest) {
            distruct(conto);
            if (cntdest == 7)
                dest = true;
            else
                cntdest++;
            if (cntdest == 1)
                rpd.dest[im] = 300;
        }
        if (conto.dist == 0) {
            for (int i9 = 0; i9 < conto.npl; i9++) {
                if (conto.p[i9].chip != 0)
                    conto.p[i9].chip = 0;
                if (conto.p[i9].embos != 0)
                    conto.p[i9].embos = 13;
            }

        }
        int j9 = 0;
        int k9 = 0;
        int l9 = 0;
        if (nofocus)
            j = 1;
        else
            j = 7;
        for (int j10 = 0; j10 < checkpoints.n; j10++) {
            if (checkpoints.typ[j10] > 0) {
                l9++;
                if (checkpoints.typ[j10] == 1) {
                    if (clear == l9 + nlaps * checkpoints.nsp)
                        j = 1;
                    if (Math.abs(conto.z - checkpoints.z[j10]) < 60F
                            + Math.abs(scz[0] + scz[1] + scz[2] + scz[3]) / 4F
                            && Math.abs(conto.x - checkpoints.x[j10]) < 700
                            && Math.abs(conto.y - checkpoints.y[j10]) < 800
                            && clear == (l9 + nlaps * checkpoints.nsp) - 1) {
                        clear = l9 + nlaps * checkpoints.nsp;
                        pcleared = j10;
                        focus = -1;
                    }
                }
                if (checkpoints.typ[j10] == 2) {
                    if (clear == l9 + nlaps * checkpoints.nsp)
                        j = 1;
                    if (Math.abs(conto.x - checkpoints.x[j10]) < 60F
                            + Math.abs(scx[0] + scx[1] + scx[2] + scx[3]) / 4F
                            && Math.abs(conto.z - checkpoints.z[j10]) < 700
                            && Math.abs(conto.y - checkpoints.y[j10]) < 800
                            && clear == (l9 + nlaps * checkpoints.nsp) - 1) {
                        clear = l9 + nlaps * checkpoints.nsp;
                        pcleared = j10;
                        focus = -1;
                    }
                }
            }
            if (Utility.py(conto.x / 100, checkpoints.x[j10] / 100, conto.z / 100, checkpoints.z[j10] / 100) * j < k9
                    || k9 == 0) {
                j9 = j10;
                k9 = Utility.py(conto.x / 100, checkpoints.x[j10] / 100, conto.z / 100, checkpoints.z[j10] / 100) * j;
            }
        }

        if (clear == l9 + nlaps * checkpoints.nsp)
            nlaps++;
        if (im == 0) {
            for (Medium.checkpoint = clear; Medium.checkpoint >= checkpoints.nsp; Medium.checkpoint -= checkpoints.nsp)
                ;
            if (clear == checkpoints.nlaps * checkpoints.nsp - 1)
                Medium.lastcheck = true;
            if (checkpoints.haltall)
                Medium.lastcheck = false;
        }
        if (focus == -1) {
            if (im == 0)
                j9 += 2;
            else
                j9++;
            if (!nofocus) {
                int i10;
                for (i10 = pcleared + 1; checkpoints.typ[i10] <= 0; )
                    if (++i10 == checkpoints.n)
                        i10 = 0;

                if (j9 > i10 && (clear != nlaps * checkpoints.nsp || j9 < pcleared)) {
                    j9 = i10;
                    focus = j9;
                }
            }
            if (j9 >= checkpoints.n)
                j9 -= checkpoints.n;
            if (checkpoints.typ[j9] == -3)
                j9 = 0;
            if (im == 0) {
                if (missedcp != -1)
                    missedcp = -1;
            } else if (missedcp != 0)
                missedcp = 0;
        } else {
            j9 = focus;
            if (im == 0) {
                if (missedcp == 0 && mtouch && Math.sqrt(
                        Utility.py(conto.x / 10, checkpoints.x[focus] / 10, conto.z / 10, checkpoints.z[focus] / 10)) > 800D)
                    missedcp = 1;
                if (missedcp == -2 && Math.sqrt(
                        Utility.py(conto.x / 10, checkpoints.x[focus] / 10, conto.z / 10, checkpoints.z[focus] / 10)) < 400D)
                    missedcp = 0;
                if (missedcp != 0 && mtouch && Math.sqrt(
                        Utility.py(conto.x / 10, checkpoints.x[focus] / 10, conto.z / 10, checkpoints.z[focus] / 10)) < 250D)
                    missedcp = 68;
            } else {
                missedcp = 1;
            }
            if (nofocus) {
                focus = -1;
                missedcp = 0;
            }
        }
        if (nofocus)
            nofocus = false;
        point = j9;
        for (int k10 = 0; k10 < checkpoints.fn; k10++)
            if (!checkpoints.roted[k10]) {
                if (Math.abs(conto.z - checkpoints.fz[k10]) < 200 && Utility.py(conto.x / 100, checkpoints.fx[k10] / 100,
                        conto.y / 100, checkpoints.fy[k10] / 100) < 30) {
                    if (conto.dist == 0) {
                        conto.fcnt = 8;
                    } else {
                        if (im == 0 && !conto.fix && !xt.mutes) {
                            xt.sm.play("carfixed");
                        }
                        conto.fix = true;
                    }
                    rpd.fix[im] = 300;
                }
            } else if (Math.abs(conto.x - checkpoints.fx[k10]) < 200
                    && Utility.py(conto.z / 100, checkpoints.fz[k10] / 100, conto.y / 100, checkpoints.fy[k10] / 100) < 30) {
                if (conto.dist == 0) {
                    conto.fcnt = 8;
                } else {
                    if (im == 0 && !conto.fix && !xt.mutes) {
                        xt.sm.play("carfixed");
                    }
                    conto.fix = true;
                }
                rpd.fix[im] = 300;
            }

        if (conto.fcnt == 7 || conto.fcnt == 8) {
            squash = 0;
            nbsq = 0;
            hitmag = 0;
            cntdest = 0;
            dest = false;
            newcar = true;
        }
        if (!mtouch) {
            if (trcnt != 1) {
                trcnt = 1;
                lxz = conto.xz;
            }
            if (loop == 2 || loop == -1) {
                travxy += rcomp - lcomp;
                if (Math.abs(travxy) > 135)
                    rtab = true;
                travzy += ucomp - dcomp;
                if (travzy > 135)
                    ftab = true;
                if (travzy < -135)
                    btab = true;
            }
            if (lxz != conto.xz) {
                travxz += lxz - conto.xz;
                lxz = conto.xz;
            }
            if (srfcnt < 10) {
                if (control.wall != -1)
                    surfer = true;
                srfcnt++;
            }
        } else if (!dest) {
            if (!capsized) {
                if (capcnt != 0)
                    capcnt = 0;
                if (gtouch && trcnt != 0) {
                    if (trcnt == 9) {
                        powerup = 0.0F;
                        if (Math.abs(travxy) > 90)
                            powerup += Math.abs(travxy) / 24F;
                        else if (rtab)
                            powerup += 30F;
                        if (Math.abs(travzy) > 90) {
                            powerup += Math.abs(travzy) / 18F;
                        } else {
                            if (ftab)
                                powerup += 40F;
                            if (btab)
                                powerup += 40F;
                        }
                        if (Math.abs(travxz) > 90)
                            powerup += Math.abs(travxz) / 18F;
                        if (surfer)
                            powerup += 30F;
                        power += powerup;
                        if (im == 0 && (int) powerup > rpd.powered && rpd.wasted == 0
                                && (powerup > 60F || checkpoints.stage <= 2)) {
                            rpdcatch = 30;
                            if (rpd.hcaught)
                                rpd.powered = (int) powerup;
                        }
                        if (power > 98F) {
                            power = 98F;
                            if (powerup > 150F)
                                xtpower = 200;
                            else
                                xtpower = 100;
                        }
                    }
                    if (trcnt == 10) {
                        travxy = 0;
                        travzy = 0;
                        travxz = 0;
                        ftab = false;
                        rtab = false;
                        btab = false;
                        trcnt = 0;
                        srfcnt = 0;
                        surfer = false;
                    } else {
                        trcnt++;
                    }
                }
            } else {
                if (trcnt != 0) {
                    travxy = 0;
                    travzy = 0;
                    travxz = 0;
                    ftab = false;
                    rtab = false;
                    btab = false;
                    trcnt = 0;
                    srfcnt = 0;
                    surfer = false;
                }
                if (capcnt == 0) {
                    int l10 = 0;
                    int i11 = 0;
                    do
                        if (Math.abs(scz[i11]) < 70F && Math.abs(scx[i11]) < 70F)
                            l10++;
                    while (++i11 < 4);
                    if (l10 == 4)
                        capcnt = 1;
                } else {
                    capcnt++;
                    if (capcnt == 30) {
                        speed = 0.0F;
                        conto.y += stat.flipy;
                        pxy += 180;
                        conto.xy += 180;
                        capcnt = 0;
                    }
                }
            }
            if (trcnt == 0 && speed != 0.0F)
                if (xtpower == 0) {
                    if (power > 0.0F)
                        power -= (power * power * power) / stat.powerloss;
                    else
                        power = 0.0F;
                } else {
                    xtpower--;
                }
        }
        if (im == 0) {
            if (control.wall != -1)
                control.wall = -1;
        } else if (lastcolido != 0 && !dest)
            lastcolido--;
        if (dest) {
            if (checkpoints.dested[im] == 0)
                if (lastcolido == 0)
                    checkpoints.dested[im] = 1;
                else
                    checkpoints.dested[im] = 2;
        } else if (checkpoints.dested[im] != 0)
            checkpoints.dested[im] = 0;
        if (im == 0 && rpd.wasted == 0 && rpdcatch != 0) {
            rpdcatch--;
            if (rpdcatch == 0) {
                rpd.cotchinow(0);
                if (rpd.hcaught)
                    rpd.whenwasted = (int) (185F + Medium.random() * 20F);
            }
        }
    }
}
