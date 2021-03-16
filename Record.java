import java.awt.*;

class Record {

    public final ContO[] ocar;
    public final ContO[] starcar;
    public final ContO[][] car;
    public int caught;
    public boolean hcaught;
    private boolean prepit;
    private int cntf;
    private final int[][] squash;
    public final int[] fix;
    public final int[] dest;
    private final int[][] x;
    private final int[][] y;
    private final int[][] z;
    private final int[][] xy;
    private final int[][] zy;
    private final int[][] xz;
    private final int[][] wxz;
    private final int[][] wzy;
    private final int[][] ns;
    private final int[][][] sspark;
    private final int[][][] sx;
    private final int[][][] sy;
    private final int[][][] sz;
    private final float[][][] smag;
    private final int[][][] scx;
    private final int[][][] scz;
    private final boolean[][][] fulls;
    private final int[][] nry;
    private final int[][][] ry;
    private final int[][][] magy;
    private final boolean[][] mtouch;
    private final int[][] nrx;
    private final int[][][] rx;
    private final int[][][] magx;
    private final int[][] nrz;
    private final int[][][] rz;
    private final int[][][] magz;
    private final int[] checkpoint;
    private final boolean[] lastcheck;
    public int wasted;
    public int whenwasted;
    public int powered;
    public int closefinish;
    private final int[] hsquash;
    public final int[] hfix = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };
    private final int[] hdest = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };
    private final int[][] hx;
    private final int[][] hy;
    private final int[][] hz;
    private final int[][] hxy;
    private final int[][] hzy;
    private final int[][] hxz;
    private final int[][] hwxz;
    private final int[][] hwzy;
    private final int[][] hns;
    private final int[][][] hsspark;
    private final int[][][] hsx;
    private final int[][][] hsy;
    private final int[][][] hsz;
    private final float[][][] hsmag;
    private final int[][][] hscx;
    private final int[][][] hscz;
    private final boolean[][][] hfulls;
    private final int[][] hnry;
    private final int[][][] hry;
    private final int[][][] hmagy;
    private final int[][] hnrx;
    private final int[][][] hrx;
    private final int[][][] hmagx;
    private final int[][] hnrz;
    private final int[][][] hrz;
    private final int[][][] hmagz;
    private final boolean[][] hmtouch;
    private final int[] hcheckpoint;
    private final boolean[] hlastcheck;
    public final int[] cntdest;
    private int lastfr;

    private void regy(int i, float f, boolean flag, ContO conto, Madness madness) {
        if (f > 100F) {
            f -= 100F;
            byte byte0 = 0;
            byte byte1 = 0;
            int j = conto.zy;
            int k = conto.xy;
            for (; j < 360; j += 360) {
            }
            for (; j > 360; j -= 360) {
            }
            if (j < 210 && j > 150) {
                byte0 = -1;
            }
            if (j > 330 || j < 30) {
                byte0 = 1;
            }
            for (; k < 360; k += 360) {
            }
            for (; k > 360; k -= 360) {
            }
            if (k < 210 && k > 150) {
                byte1 = -1;
            }
            if (k > 330 || k < 30) {
                byte1 = 1;
            }
            if (byte1 * byte0 == 0 || flag) {
                for (int l = 0; l < conto.npl; l++) {
                    float f1 = 0.0F;
                    for (int k1 = 0; k1 < conto.p[l].n; k1++) {
                        if (conto.p[l].wz == 0 && Utility.py(conto.keyx[i], conto.p[l].ox[k1], conto.keyz[i],
                                conto.p[l].oz[k1]) < madness.stat.clrad) {
                            f1 = (f / 20F) * Medium.random();
                            conto.p[l].oz[k1] += f1 * RadicalMath.sin(j);
                            conto.p[l].ox[k1] -= f1 * RadicalMath.sin(k);
                        }
                    }

                    if (f1 != 0.0F) {
                        if (Math.abs(f1) >= 1.0F) {
                            conto.p[l].chip = 1;
                            conto.p[l].ctmag = f1;
                        }
                        if (!conto.p[l].nocol && !conto.p[l].glass) {
                            if (conto.p[l].bfase > 20 && conto.p[l].hsb[1] > 0.20000000000000001D) {
                                conto.p[l].hsb[1] = 0.2F;
                            }
                            if (conto.p[l].bfase > 30) {
                                if (conto.p[l].hsb[2] < 0.5D) {
                                    conto.p[l].hsb[2] = 0.5F;
                                }
                                if (conto.p[l].hsb[1] > 0.10000000000000001D) {
                                    conto.p[l].hsb[1] = 0.1F;
                                }
                            }
                            if (conto.p[l].bfase > 40) {
                                conto.p[l].hsb[1] = 0.05F;
                            }
                            if (conto.p[l].bfase > 50) {
                                if (conto.p[l].hsb[2] > 0.80000000000000004D) {
                                    conto.p[l].hsb[2] = 0.8F;
                                }
                                conto.p[l].hsb[0] = 0.075F;
                                conto.p[l].hsb[1] = 0.05F;
                            }
                            if (conto.p[l].bfase > 60) {
                                conto.p[l].hsb[0] = 0.05F;
                            }
                            conto.p[l].bfase += f1;
                            new Color(conto.p[l].c[0], conto.p[l].c[1], conto.p[l].c[2]);
                            Color color = Color.getHSBColor(conto.p[l].hsb[0], conto.p[l].hsb[1], conto.p[l].hsb[2]);
                            conto.p[l].c[0] = color.getRed();
                            conto.p[l].c[1] = color.getGreen();
                            conto.p[l].c[2] = color.getBlue();
                        }
                        if (conto.p[l].glass) {
                            conto.p[l].gr += Math.abs(f1 * 1.5D);
                        }
                    }
                }

            }
            if (byte1 * byte0 == -1) {
                int i1 = 0;
                int j1 = 1;
                for (int l1 = 0; l1 < conto.npl; l1++) {
                    float f2 = 0.0F;
                    for (int i2 = 0; i2 < conto.p[l1].n; i2++) {
                        if (conto.p[l1].wz == 0) {
                            f2 = (f / 15F) * Medium.random();
                            if ((Math
                                    .abs(conto.p[l1].oy[i2] - madness.stat.flipy
                                            - squash[0][madness.im]) < madness.stat.msquash * 3
                                    || conto.p[l1].oy[i2] < madness.stat.flipy + squash[0][madness.im])
                                    && squash[0][madness.im] < madness.stat.msquash) {
                                conto.p[l1].oy[i2] += f2;
                                i1 = (int) (i1 + f2);
                                j1++;
                            }
                        }
                    }

                    if (conto.p[l1].glass) {
                        conto.p[l1].gr += 5;
                    } else if (f2 != 0.0F) {
                        conto.p[l1].bfase += f2;
                    }
                    if (Math.abs(f2) >= 1.0F) {
                        conto.p[l1].chip = 1;
                        conto.p[l1].ctmag = f2;
                    }
                }

                squash[0][madness.im] += i1 / j1;
            }
        }
    }

    public void reset(ContO aconto[]) {
        caught = 0;
        hcaught = false;
        wasted = 0;
        whenwasted = 0;
        closefinish = 0;
        powered = 0;
        int i = 0;
        do {
            if (prepit) {
                starcar[i] = new ContO(aconto[i], 0, 0, 0, 0);
            }
            fix[i] = -1;
            dest[i] = -1;
            cntdest[i] = 0;
        } while (++i < 51);
        i = 0;
        do {
            int j = 0;
            do {
                car[i][j] = new ContO(aconto[j], 0, 0, 0, 0);
                squash[i][j] = 0;
            } while (++j < 51);
        } while (++i < 6);
        i = 0;
        do {
            int k = 0;
            do {
                int l = 0;
                do {
                    sspark[i][k][l] = -1;
                    ns[i][k] = 0;
                } while (++l < 30);
                l = 0;
                do {
                    ry[i][k][l] = -1;
                    nry[i][k] = 0;
                    rx[i][k][l] = -1;
                    nrx[i][k] = 0;
                    rz[i][k][l] = -1;
                    nrz[i][k] = 0;
                } while (++l < 7);
            } while (++k < 4);
        } while (++i < 51);
        prepit = false;
    }

    public Record() {
        caught = 0;
        hcaught = false;
        prepit = true;
        ocar = new ContO[51];
        cntf = 50;
        car = new ContO[6][51];
        squash = new int[6][51];
        fix = new int[51];
        dest = new int[51];
        x = new int[300][51];
        y = new int[300][51];
        z = new int[300][51];
        xy = new int[300][51];
        zy = new int[300][51];
        xz = new int[300][51];
        wxz = new int[300][51];
        wzy = new int[300][51];
        ns = new int[51][4];
        sspark = new int[51][4][30];
        sx = new int[51][4][30];
        sy = new int[51][4][30];
        sz = new int[51][4][30];
        smag = new float[51][4][30];
        scx = new int[51][4][30];
        scz = new int[51][4][30];
        fulls = new boolean[51][4][30];
        nry = new int[51][4];
        ry = new int[51][4][7]; //no
        magy = new int[51][4][7]; //no
        mtouch = new boolean[51][7]; //no
        nrx = new int[51][4];
        rx = new int[51][4][7]; //no
        magx = new int[51][4][7]; //no
        nrz = new int[51][4];
        rz = new int[51][4][7]; //no
        magz = new int[51][4][7]; //no
        checkpoint = new int[300];
        lastcheck = new boolean[300];
        wasted = 0;
        whenwasted = 0;
        powered = 0;
        closefinish = 0;
        starcar = new ContO[51];
        hsquash = new int[51];
        hx = new int[300][51];
        hy = new int[300][51];
        hz = new int[300][51];
        hxy = new int[300][51];
        hzy = new int[300][51];
        hxz = new int[300][51];
        hwxz = new int[300][51];
        hwzy = new int[300][51];
        hns = new int[51][4];
        hsspark = new int[51][4][30];
        hsx = new int[51][4][30];
        hsy = new int[51][4][30];
        hsz = new int[51][4][30];
        hsmag = new float[51][4][30];
        hscx = new int[51][4][30];
        hscz = new int[51][4][30];
        hfulls = new boolean[51][4][30];
        hnry = new int[51][4];
        hry = new int[51][4][7]; //no
        hmagy = new int[51][4][7]; //no
        hnrx = new int[51][4];
        hrx = new int[51][4][7]; //no
        hmagx = new int[51][4][7]; //no
        hnrz = new int[51][4];
        hrz = new int[51][4][7]; //no
        hmagz = new int[51][4][7]; //no
        hmtouch = new boolean[51][7]; //no
        hcheckpoint = new int[300];
        hlastcheck = new boolean[300];
        cntdest = new int[51];
        lastfr = 0;
        caught = 0;
        cotchinow(0);
    }

    public void playh(ContO conto, Madness madness, int i, int j) {
        conto.x = hx[j][i];
        conto.y = hy[j][i];
        conto.z = hz[j][i];
        conto.zy = hzy[j][i];
        conto.xy = hxy[j][i];
        conto.xz = hxz[j][i];
        conto.wxz = hwxz[j][i];
        conto.wzy = hwzy[j][i];
        if (i == 0) {
            Medium.checkpoint = hcheckpoint[j];
            Medium.lastcheck = hlastcheck[j];
        }
        if (j == 0) {
            cntdest[i] = 0;
        }
        if (hdest[i] == j) {
            cntdest[i] = 7;
        }
        if (j == 0 && hdest[i] < -1) {
            for (int k = 0; k < conto.npl; k++) {
                if (conto.p[k].wz == 0 || conto.p[k].gr == -17 || conto.p[k].gr == -16) {
                    conto.p[k].embos = 13;
                }
            }

        }
        if (cntdest[i] != 0) {
            for (int l = 0; l < conto.npl; l++) {
                if (conto.p[l].wz == 0 || conto.p[l].gr == -17 || conto.p[l].gr == -16) {
                    conto.p[l].embos = 1;
                }
            }

            cntdest[i]--;
        }
        int i1 = 0;
        do {
            int j1 = 0;
            do {
                if (hsspark[i][i1][j1] == j) {
                    conto.stg[i1] = 1;
                    conto.dov[i1] = -1;
                    conto.sx[i1] = hsx[i][i1][j1];
                    conto.sy[i1] = hsy[i][i1][j1];
                    conto.sz[i1] = hsz[i][i1][j1];
                    conto.smag[i1] = hsmag[i][i1][j1];
                    conto.scx[i1] = hscx[i][i1][j1];
                    conto.scz[i1] = hscz[i][i1][j1];
                    conto.fulls[i1] = hfulls[i][i1][j1];
                }
            } while (++j1 < 30);
            j1 = 0;
            do {
                if (hry[i][i1][j1] == j && lastfr != j) {
                    regy(i1, hmagy[i][i1][j1], hmtouch[i][j1], conto, madness);
                }
                if (hrx[i][i1][j1] == j) {
                    if (lastfr != j) {
                        regx(i1, hmagx[i][i1][j1], conto, madness);
                    } else {
                        chipx(i1, hmagx[i][i1][j1], conto, madness);
                    }
                }
                if (hrz[i][i1][j1] == j) {
                    if (lastfr != j) {
                        regz(i1, hmagz[i][i1][j1], conto, madness);
                    } else {
                        chipz(i1, hmagz[i][i1][j1], conto, madness);
                    }
                }
            } while (++j1 < 7);
        } while (++i1 < 4);
        lastfr = j;
    }

    private void chipz(int i, float f, ContO conto, Madness madness) {
        if (Math.abs(f) > 100F) {
            if (f > 100F) {
                f -= 100F;
            }
            if (f < -100F) {
                f += 100F;
            }
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++) {
                    if (conto.p[j].wz == 0 && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i],
                            conto.p[j].oz[k]) < madness.stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                    }
                }

                if (f1 != 0.0F && Math.abs(f1) >= 1.0F) {
                    conto.p[j].chip = 1;
                    conto.p[j].ctmag = f1;
                }
            }

        }
    }

    private void regz(int i, float f, ContO conto, Madness madness) {
        if (Math.abs(f) > 100F) {
            if (f > 100F) {
                f -= 100F;
            }
            if (f < -100F) {
                f += 100F;
            }
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++) {
                    if (conto.p[j].wz == 0 && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i],
                            conto.p[j].oz[k]) < madness.stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                        conto.p[j].oz[k] += f1 * RadicalMath.cos(conto.xz) * RadicalMath.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * RadicalMath.sin(conto.xz) * RadicalMath.cos(conto.xy);
                    }
                }

                if (f1 != 0.0F) {
                    if (Math.abs(f1) >= 1.0F) {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if (!conto.p[j].nocol && !conto.p[j].glass) {
                        if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.20000000000000001D) {
                            conto.p[j].hsb[1] = 0.2F;
                        }
                        if (conto.p[j].bfase > 30) {
                            if (conto.p[j].hsb[2] < 0.5D) {
                                conto.p[j].hsb[2] = 0.5F;
                            }
                            if (conto.p[j].hsb[1] > 0.10000000000000001D) {
                                conto.p[j].hsb[1] = 0.1F;
                            }
                        }
                        if (conto.p[j].bfase > 40) {
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if (conto.p[j].bfase > 50) {
                            if (conto.p[j].hsb[2] > 0.80000000000000004D) {
                                conto.p[j].hsb[2] = 0.8F;
                            }
                            conto.p[j].hsb[0] = 0.075F;
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if (conto.p[j].bfase > 60) {
                            conto.p[j].hsb[0] = 0.05F;
                        }
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if (conto.p[j].glass) {
                        conto.p[j].gr += Math.abs(f1 * 1.5D);
                    }
                }
            }

        }
    }

    public void play(ContO conto, Madness madness, int i, int j) {
        conto.x = x[j][i];
        conto.y = y[j][i];
        conto.z = z[j][i];
        conto.zy = zy[j][i];
        conto.xy = xy[j][i];
        conto.xz = xz[j][i];
        conto.wxz = wxz[j][i];
        conto.wzy = wzy[j][i];
        if (i == 0) {
            Medium.checkpoint = checkpoint[j];
            Medium.lastcheck = lastcheck[j];
        }
        if (j == 0) {
            cntdest[i] = 0;
        }
        if (dest[i] == j) {
            cntdest[i] = 7;
        }
        if (j == 0 && dest[i] < -1) {
            for (int k = 0; k < conto.npl; k++) {
                if (conto.p[k].wz == 0 || conto.p[k].gr == -17 || conto.p[k].gr == -16) {
                    conto.p[k].embos = 13;
                }
            }

        }
        if (cntdest[i] != 0) {
            for (int l = 0; l < conto.npl; l++) {
                if (conto.p[l].wz == 0 || conto.p[l].gr == -17 || conto.p[l].gr == -16) {
                    conto.p[l].embos = 1;
                }
            }

            cntdest[i]--;
        }
        int i1 = 0;
        do {
            int j1 = 0;
            do {
                if (sspark[i][i1][j1] == j) {
                    conto.stg[i1] = 1;
                    conto.dov[i1] = -1;
                    conto.sx[i1] = sx[i][i1][j1];
                    conto.sy[i1] = sy[i][i1][j1];
                    conto.sz[i1] = sz[i][i1][j1];
                    conto.smag[i1] = smag[i][i1][j1];
                    conto.scx[i1] = scx[i][i1][j1];
                    conto.scz[i1] = scz[i][i1][j1];
                    conto.fulls[i1] = fulls[i][i1][j1];
                }
            } while (++j1 < 30);
            j1 = 0;
            do {
                if (ry[i][i1][j1] == j) {
                    regy(i1, magy[i][i1][j1], mtouch[i][j1], conto, madness);
                }
                if (rx[i][i1][j1] == j) {
                    regx(i1, magx[i][i1][j1], conto, madness);
                }
                if (rz[i][i1][j1] == j) {
                    regz(i1, magz[i][i1][j1], conto, madness);
                }
            } while (++j1 < 7);
        } while (++i1 < 4);
    }

    public void rec(ContO conto, int i, int j, int k, int l) {
        if (i == 0) {
            caught++;
        }
        if (cntf == 50) {
            int i1 = 0;
            do {
                car[i1][i] = new ContO(car[i1 + 1][i], 0, 0, 0, 0);
                squash[i1][i] = squash[i1 + 1][i];
            } while (++i1 < 5);
            car[5][i] = new ContO(conto, 0, 0, 0, 0);
            squash[5][i] = j;
            cntf = 0;
        } else {
            cntf++;
        }
        fix[i]--;
        if (l != 0) {
            dest[i]--;
        }
        if (dest[i] == 230) {
            if (i == 0) {
                cotchinow(0);
                whenwasted = 229;
            } else if (k != 0) {
                cotchinow(i);
                whenwasted = 165 + k;
            }
        }
        int j1 = 0;
        do {
            x[j1][i] = x[j1 + 1][i];
            y[j1][i] = y[j1 + 1][i];
            z[j1][i] = z[j1 + 1][i];
            zy[j1][i] = zy[j1 + 1][i];
            xy[j1][i] = xy[j1 + 1][i];
            xz[j1][i] = xz[j1 + 1][i];
            wxz[j1][i] = wxz[j1 + 1][i];
            wzy[j1][i] = wzy[j1 + 1][i];
        } while (++j1 < 299);
        x[299][i] = conto.x;
        y[299][i] = conto.y;
        z[299][i] = conto.z;
        xy[299][i] = conto.xy;
        zy[299][i] = conto.zy;
        xz[299][i] = conto.xz;
        wxz[299][i] = conto.wxz;
        wzy[299][i] = conto.wzy;
        if (i == 0) {
            j1 = 0;
            do {
                checkpoint[j1] = checkpoint[j1 + 1];
                lastcheck[j1] = lastcheck[j1 + 1];
            } while (++j1 < 299);
            checkpoint[299] = Medium.checkpoint;
            lastcheck[299] = Medium.lastcheck;
        }
        j1 = 0;
        do {
            if (conto.stg[j1] == 1) {
                sspark[i][j1][ns[i][j1]] = 300;
                sx[i][j1][ns[i][j1]] = conto.sx[j1];
                sy[i][j1][ns[i][j1]] = conto.sy[j1];
                sz[i][j1][ns[i][j1]] = conto.sz[j1];
                smag[i][j1][ns[i][j1]] = conto.smag[j1];
                scx[i][j1][ns[i][j1]] = conto.scx[j1];
                scz[i][j1][ns[i][j1]] = conto.scz[j1];
                fulls[i][j1][ns[i][j1]] = conto.fulls[j1];
                ns[i][j1]++;
                if (ns[i][j1] == 30) {
                    ns[i][j1] = 0;
                }
            }
            int k1 = 0;
            do {
                sspark[i][j1][k1]--;
            } while (++k1 < 30);
            k1 = 0;
            do {
                ry[i][j1][k1]--;
                rx[i][j1][k1]--;
                rz[i][j1][k1]--;
            } while (++k1 < 7);
        } while (++j1 < 4);
    }

    public void recx(int i, float f, int j) {
        rx[j][i][nry[j][i]] = 300;
        magx[j][i][nry[j][i]] = (int) f;
        nrx[j][i]++;
        if (nrx[j][i] == 7) {
            nrx[j][i] = 0;
        }
    }

    public void recy(int i, float f, boolean flag, int j) {
        ry[j][i][nry[j][i]] = 300;
        magy[j][i][nry[j][i]] = (int) f;
        mtouch[j][nry[j][i]] = flag;
        nry[j][i]++;
        if (nry[j][i] == 7) {
            nry[j][i] = 0;
        }
    }

    public void cotchinow(int i) {
        if (caught >= 300) {
            wasted = i;
            int j = 0;
            do {
                starcar[j] = new ContO(car[0][j], 0, 0, 0, 0);
                hsquash[j] = squash[0][j];
                hfix[j] = fix[j];
                hdest[j] = dest[j];
            } while(++j < 51);
            j = 0;
            do {
                int k = 0;
                do {
                    hx[j][k] = x[j][k];
                    hy[j][k] = y[j][k];
                    hz[j][k] = z[j][k];
                    hxy[j][k] = xy[j][k];
                    hzy[j][k] = zy[j][k];
                    hxz[j][k] = xz[j][k];
                    hwxz[j][k] = wxz[j][k];
                    hwzy[j][k] = wzy[j][k];
                } while(++k < 51);
                hcheckpoint[j] = checkpoint[j];
                hlastcheck[j] = lastcheck[j];
            } while (++j < 300);
            j = 0;
            do {
                int l = 0;
                do {
                    hns[j][l] = ns[j][l];
                    int k1 = 0;
                    do {
                        hsspark[j][l][k1] = sspark[j][l][k1];
                        hsx[j][l][k1] = sx[j][l][k1];
                        hsy[j][l][k1] = sy[j][l][k1];
                        hsz[j][l][k1] = sz[j][l][k1];
                        hsmag[j][l][k1] = smag[j][l][k1];
                        hscx[j][l][k1] = scx[j][l][k1];
                        hscz[j][l][k1] = scz[j][l][k1];
                        hfulls[j][l][k1] = fulls[j][l][k1];
                    } while (++k1 < 30);
                } while (++l < 4);
            } while(++j < 51);
            j = 0;
            do {
                int i1 = 0;
                do {
                    hnry[j][i1] = nry[j][i1];
                    hnrx[j][i1] = nrx[j][i1];
                    hnrz[j][i1] = nrz[j][i1];
                    int l1 = 0;
                    do {
                        hry[j][i1][l1] = ry[j][i1][l1];
                        hmagy[j][i1][l1] = magy[j][i1][l1];
                        hrx[j][i1][l1] = rx[j][i1][l1];
                        hmagx[j][i1][l1] = magx[j][i1][l1];
                        hrz[j][i1][l1] = rz[j][i1][l1];
                        hmagz[j][i1][l1] = magz[j][i1][l1];
                    } while (++l1 < 7);
                } while (++i1 < 4);
            } while(++j < 51);
            j = 0;
            do {
                int j1 = 0;
                do {
                    hmtouch[j][j1] = mtouch[j][j1];
                } while (++j1 < 7);
            } while(++j < 51);
            hcaught = true;
        }
    }

    private void chipx(int i, float f, ContO conto, Madness madness) {
        if (Math.abs(f) > 100F) {
            if (f > 100F) {
                f -= 100F;
            }
            if (f < -100F) {
                f += 100F;
            }
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++) {
                    if (conto.p[j].wz == 0 && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i],
                            conto.p[j].oz[k]) < madness.stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                    }
                }

                if (f1 != 0.0F && Math.abs(f1) >= 1.0F) {
                    conto.p[j].chip = 1;
                    conto.p[j].ctmag = f1;
                }
            }

        }
    }

    private void regx(int i, float f, ContO conto, Madness madness) {
        if (Math.abs(f) > 100F) {
            if (f > 100F) {
                f -= 100F;
            }
            if (f < -100F) {
                f += 100F;
            }
            for (int j = 0; j < conto.npl; j++) {
                float f1 = 0.0F;
                for (int k = 0; k < conto.p[j].n; k++) {
                    if (conto.p[j].wz == 0 && Utility.py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i],
                            conto.p[j].oz[k]) < madness.stat.clrad) {
                        f1 = (f / 20F) * Medium.random();
                        conto.p[j].oz[k] -= f1 * RadicalMath.sin(conto.xz) * RadicalMath.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * RadicalMath.cos(conto.xz) * RadicalMath.cos(conto.xy);
                    }
                }

                if (f1 != 0.0F) {
                    if (Math.abs(f1) >= 1.0F) {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if (!conto.p[j].nocol && !conto.p[j].glass) {
                        if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.20000000000000001D) {
                            conto.p[j].hsb[1] = 0.2F;
                        }
                        if (conto.p[j].bfase > 30) {
                            if (conto.p[j].hsb[2] < 0.5D) {
                                conto.p[j].hsb[2] = 0.5F;
                            }
                            if (conto.p[j].hsb[1] > 0.10000000000000001D) {
                                conto.p[j].hsb[1] = 0.1F;
                            }
                        }
                        if (conto.p[j].bfase > 40) {
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if (conto.p[j].bfase > 50) {
                            if (conto.p[j].hsb[2] > 0.80000000000000004D) {
                                conto.p[j].hsb[2] = 0.8F;
                            }
                            conto.p[j].hsb[0] = 0.075F;
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if (conto.p[j].bfase > 60) {
                            conto.p[j].hsb[0] = 0.05F;
                        }
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if (conto.p[j].glass) {
                        conto.p[j].gr += Math.abs(f1 * 1.5D);
                    }
                }
            }

        }
    }

    public void recz(int i, float f, int j) {
        rz[j][i][nry[j][i]] = 300;
        magz[j][i][nry[j][i]] = (int) f;
        nrz[j][i]++;
        if (nrz[j][i] == 7) {
            nrz[j][i] = 0;
        }
    }
}
