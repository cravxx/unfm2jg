import java.awt.Color;

public class Record
{

    Medium m;
    int caught;
    boolean hcaught;
    boolean prepit;
    ContO ocar[];
    int cntf;
    ContO car[][];
    int squash[][];
    int fix[];
    int dest[];
    int x[][];
    int y[][];
    int z[][];
    int xy[][];
    int zy[][];
    int xz[][];
    int wxz[][];
    int wzy[][];
    int ns[][];
    int sspark[][][];
    int sx[][][];
    int sy[][][];
    int sz[][][];
    float smag[][][];
    int scx[][][];
    int scz[][][];
    boolean fulls[][][];
    int nry[][];
    int ry[][][];
    int magy[][][];
    boolean mtouch[][];
    int nrx[][];
    int rx[][][];
    int magx[][][];
    int nrz[][];
    int rz[][][];
    int magz[][][];
    int checkpoint[];
    boolean lastcheck[];
    int wasted;
    int whenwasted;
    int powered;
    int closefinish;
    ContO starcar[];
    int hsquash[];
    int hfix[] = {
        -1, -1, -1, -1, -1, -1, -1
    };
    int hdest[] = {
        -1, -1, -1, -1, -1, -1, -1
    };
    int hx[][];
    int hy[][];
    int hz[][];
    int hxy[][];
    int hzy[][];
    int hxz[][];
    int hwxz[][];
    int hwzy[][];
    int hns[][];
    int hsspark[][][];
    int hsx[][][];
    int hsy[][][];
    int hsz[][][];
    float hsmag[][][];
    int hscx[][][];
    int hscz[][][];
    boolean hfulls[][][];
    int hnry[][];
    int hry[][][];
    int hmagy[][][];
    int hnrx[][];
    int hrx[][][];
    int hmagx[][][];
    int hnrz[][];
    int hrz[][][];
    int hmagz[][][];
    boolean hmtouch[][];
    int hcheckpoint[];
    boolean hlastcheck[];
    int cntdest[];
    int lastfr;

    public void regy(int i, float f, boolean flag, ContO conto, Madness madness)
    {
        if(f > 100F)
        {
            f -= 100F;
            byte byte0 = 0;
            byte byte1 = 0;
            int j = conto.zy;
            int k = conto.xy;
            for(; j < 360; j += 360) { }
            for(; j > 360; j -= 360) { }
            if(j < 210 && j > 150)
            {
                byte0 = -1;
            }
            if(j > 330 || j < 30)
            {
                byte0 = 1;
            }
            for(; k < 360; k += 360) { }
            for(; k > 360; k -= 360) { }
            if(k < 210 && k > 150)
            {
                byte1 = -1;
            }
            if(k > 330 || k < 30)
            {
                byte1 = 1;
            }
            if(byte1 * byte0 == 0 || flag)
            {
                for(int l = 0; l < conto.npl; l++)
                {
                    float f1 = 0.0F;
                    for(int k1 = 0; k1 < conto.p[l].n; k1++)
                    {
                        if(conto.p[l].wz == 0 && py(conto.keyx[i], conto.p[l].ox[k1], conto.keyz[i], conto.p[l].oz[k1]) < madness.clrad[madness.cn])
                        {
                            f1 = (f / 20F) * m.random();
                            conto.p[l].oz[k1] += f1 * m.sin(j);
                            conto.p[l].ox[k1] -= f1 * m.sin(k);
                        }
                    }

                    if(f1 != 0.0F)
                    {
                        if(Math.abs(f1) >= 1.0F)
                        {
                            conto.p[l].chip = 1;
                            conto.p[l].ctmag = f1;
                        }
                        if(!conto.p[l].nocol && !conto.p[l].glass)
                        {
                            if(conto.p[l].bfase > 20 && (double)conto.p[l].hsb[1] > 0.20000000000000001D)
                            {
                                conto.p[l].hsb[1] = 0.2F;
                            }
                            if(conto.p[l].bfase > 30)
                            {
                                if((double)conto.p[l].hsb[2] < 0.5D)
                                {
                                    conto.p[l].hsb[2] = 0.5F;
                                }
                                if((double)conto.p[l].hsb[1] > 0.10000000000000001D)
                                {
                                    conto.p[l].hsb[1] = 0.1F;
                                }
                            }
                            if(conto.p[l].bfase > 40)
                            {
                                conto.p[l].hsb[1] = 0.05F;
                            }
                            if(conto.p[l].bfase > 50)
                            {
                                if((double)conto.p[l].hsb[2] > 0.80000000000000004D)
                                {
                                    conto.p[l].hsb[2] = 0.8F;
                                }
                                conto.p[l].hsb[0] = 0.075F;
                                conto.p[l].hsb[1] = 0.05F;
                            }
                            if(conto.p[l].bfase > 60)
                            {
                                conto.p[l].hsb[0] = 0.05F;
                            }
                            conto.p[l].bfase += f1;
                            new Color(conto.p[l].c[0], conto.p[l].c[1], conto.p[l].c[2]);
                            Color color = Color.getHSBColor(conto.p[l].hsb[0], conto.p[l].hsb[1], conto.p[l].hsb[2]);
                            conto.p[l].c[0] = color.getRed();
                            conto.p[l].c[1] = color.getGreen();
                            conto.p[l].c[2] = color.getBlue();
                        }
                        if(conto.p[l].glass)
                        {
                            conto.p[l].gr += Math.abs((double)f1 * 1.5D);
                        }
                    }
                }

            }
            if(byte1 * byte0 == -1)
            {
                int i1 = 0;
                int j1 = 1;
                for(int l1 = 0; l1 < conto.npl; l1++)
                {
                    float f2 = 0.0F;
                    for(int i2 = 0; i2 < conto.p[l1].n; i2++)
                    {
                        if(conto.p[l1].wz == 0)
                        {
                            f2 = (f / 15F) * m.random();
                            if((Math.abs(conto.p[l1].oy[i2] - madness.flipy[madness.cn] - squash[0][madness.im]) < madness.msquash[madness.cn] * 3 || conto.p[l1].oy[i2] < madness.flipy[madness.cn] + squash[0][madness.im]) && squash[0][madness.im] < madness.msquash[madness.cn])
                            {
                                conto.p[l1].oy[i2] += f2;
                                i1 = (int)((float)i1 + f2);
                                j1++;
                            }
                        }
                    }

                    if(conto.p[l1].glass)
                    {
                        conto.p[l1].gr += 5;
                    } else
                    if(f2 != 0.0F)
                    {
                        conto.p[l1].bfase += f2;
                    }
                    if(Math.abs(f2) >= 1.0F)
                    {
                        conto.p[l1].chip = 1;
                        conto.p[l1].ctmag = f2;
                    }
                }

                squash[0][madness.im] += i1 / j1;
            }
        }
    }

    public void reset(ContO aconto[])
    {
        caught = 0;
        hcaught = false;
        wasted = 0;
        whenwasted = 0;
        closefinish = 0;
        powered = 0;
        int i = 0;
        do
        {
            if(prepit)
            {
                starcar[i] = new ContO(aconto[i], 0, 0, 0, 0);
            }
            fix[i] = -1;
            dest[i] = -1;
            cntdest[i] = 0;
        } while(++i < 7);
        i = 0;
        do
        {
            int j = 0;
            do
            {
                car[i][j] = new ContO(aconto[j], 0, 0, 0, 0);
                squash[i][j] = 0;
            } while(++j < 7);
        } while(++i < 6);
        i = 0;
        do
        {
            int k = 0;
            do
            {
                int l = 0;
                do
                {
                    sspark[i][k][l] = -1;
                    ns[i][k] = 0;
                } while(++l < 30);
                l = 0;
                do
                {
                    ry[i][k][l] = -1;
                    nry[i][k] = 0;
                    rx[i][k][l] = -1;
                    nrx[i][k] = 0;
                    rz[i][k][l] = -1;
                    nrz[i][k] = 0;
                } while(++l < 7);
            } while(++k < 4);
        } while(++i < 7);
        prepit = false;
    }

    public Record(Medium medium)
    {
        caught = 0;
        hcaught = false;
        prepit = true;
        ocar = new ContO[7];
        cntf = 50;
        car = new ContO[6][7];
        squash = new int[6][7];
        fix = new int[7];
        dest = new int[7];
        x = new int[300][7];
        y = new int[300][7];
        z = new int[300][7];
        xy = new int[300][7];
        zy = new int[300][7];
        xz = new int[300][7];
        wxz = new int[300][7];
        wzy = new int[300][7];
        ns = new int[7][4];
        sspark = new int[7][4][30];
        sx = new int[7][4][30];
        sy = new int[7][4][30];
        sz = new int[7][4][30];
        smag = new float[7][4][30];
        scx = new int[7][4][30];
        scz = new int[7][4][30];
        fulls = new boolean[7][4][30];
        nry = new int[7][4];
        ry = new int[7][4][7];
        magy = new int[7][4][7];
        mtouch = new boolean[7][7];
        nrx = new int[7][4];
        rx = new int[7][4][7];
        magx = new int[7][4][7];
        nrz = new int[7][4];
        rz = new int[7][4][7];
        magz = new int[7][4][7];
        checkpoint = new int[300];
        lastcheck = new boolean[300];
        wasted = 0;
        whenwasted = 0;
        powered = 0;
        closefinish = 0;
        starcar = new ContO[7];
        hsquash = new int[7];
        hx = new int[300][7];
        hy = new int[300][7];
        hz = new int[300][7];
        hxy = new int[300][7];
        hzy = new int[300][7];
        hxz = new int[300][7];
        hwxz = new int[300][7];
        hwzy = new int[300][7];
        hns = new int[7][4];
        hsspark = new int[7][4][30];
        hsx = new int[7][4][30];
        hsy = new int[7][4][30];
        hsz = new int[7][4][30];
        hsmag = new float[7][4][30];
        hscx = new int[7][4][30];
        hscz = new int[7][4][30];
        hfulls = new boolean[7][4][30];
        hnry = new int[7][4];
        hry = new int[7][4][7];
        hmagy = new int[7][4][7];
        hnrx = new int[7][4];
        hrx = new int[7][4][7];
        hmagx = new int[7][4][7];
        hnrz = new int[7][4];
        hrz = new int[7][4][7];
        hmagz = new int[7][4][7];
        hmtouch = new boolean[7][7];
        hcheckpoint = new int[300];
        hlastcheck = new boolean[300];
        cntdest = new int[7];
        lastfr = 0;
        m = medium;
        caught = 0;
        cotchinow(0);
    }

    public void playh(ContO conto, Madness madness, int i, int j)
    {
        conto.x = hx[j][i];
        conto.y = hy[j][i];
        conto.z = hz[j][i];
        conto.zy = hzy[j][i];
        conto.xy = hxy[j][i];
        conto.xz = hxz[j][i];
        conto.wxz = hwxz[j][i];
        conto.wzy = hwzy[j][i];
        if(i == 0)
        {
            conto.m.checkpoint = hcheckpoint[j];
            conto.m.lastcheck = hlastcheck[j];
        }
        if(j == 0)
        {
            cntdest[i] = 0;
        }
        if(hdest[i] == j)
        {
            cntdest[i] = 7;
        }
        if(j == 0 && hdest[i] < -1)
        {
            for(int k = 0; k < conto.npl; k++)
            {
                if(conto.p[k].wz == 0 || conto.p[k].gr == -17 || conto.p[k].gr == -16)
                {
                    conto.p[k].embos = 13;
                }
            }

        }
        if(cntdest[i] != 0)
        {
            for(int l = 0; l < conto.npl; l++)
            {
                if(conto.p[l].wz == 0 || conto.p[l].gr == -17 || conto.p[l].gr == -16)
                {
                    conto.p[l].embos = 1;
                }
            }

            cntdest[i]--;
        }
        int i1 = 0;
        do
        {
            int j1 = 0;
            do
            {
                if(hsspark[i][i1][j1] == j)
                {
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
            } while(++j1 < 30);
            j1 = 0;
            do
            {
                if(hry[i][i1][j1] == j && lastfr != j)
                {
                    regy(i1, hmagy[i][i1][j1], hmtouch[i][j1], conto, madness);
                }
                if(hrx[i][i1][j1] == j)
                {
                    if(lastfr != j)
                    {
                        regx(i1, hmagx[i][i1][j1], conto, madness);
                    } else
                    {
                        chipx(i1, hmagx[i][i1][j1], conto, madness);
                    }
                }
                if(hrz[i][i1][j1] == j)
                {
                    if(lastfr != j)
                    {
                        regz(i1, hmagz[i][i1][j1], conto, madness);
                    } else
                    {
                        chipz(i1, hmagz[i][i1][j1], conto, madness);
                    }
                }
            } while(++j1 < 7);
        } while(++i1 < 4);
        lastfr = j;
    }

    public void chipz(int i, float f, ContO conto, Madness madness)
    {
        if(Math.abs(f) > 100F)
        {
            if(f > 100F)
            {
                f -= 100F;
            }
            if(f < -100F)
            {
                f += 100F;
            }
            for(int j = 0; j < conto.npl; j++)
            {
                float f1 = 0.0F;
                for(int k = 0; k < conto.p[j].n; k++)
                {
                    if(conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < madness.clrad[madness.cn])
                    {
                        f1 = (f / 20F) * m.random();
                    }
                }

                if(f1 != 0.0F && Math.abs(f1) >= 1.0F)
                {
                    conto.p[j].chip = 1;
                    conto.p[j].ctmag = f1;
                }
            }

        }
    }

    public void regz(int i, float f, ContO conto, Madness madness)
    {
        if(Math.abs(f) > 100F)
        {
            if(f > 100F)
            {
                f -= 100F;
            }
            if(f < -100F)
            {
                f += 100F;
            }
            for(int j = 0; j < conto.npl; j++)
            {
                float f1 = 0.0F;
                for(int k = 0; k < conto.p[j].n; k++)
                {
                    if(conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < madness.clrad[madness.cn])
                    {
                        f1 = (f / 20F) * m.random();
                        conto.p[j].oz[k] += f1 * m.cos(conto.xz) * m.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * m.sin(conto.xz) * m.cos(conto.xy);
                    }
                }

                if(f1 != 0.0F)
                {
                    if(Math.abs(f1) >= 1.0F)
                    {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if(!conto.p[j].nocol && !conto.p[j].glass)
                    {
                        if(conto.p[j].bfase > 20 && (double)conto.p[j].hsb[1] > 0.20000000000000001D)
                        {
                            conto.p[j].hsb[1] = 0.2F;
                        }
                        if(conto.p[j].bfase > 30)
                        {
                            if((double)conto.p[j].hsb[2] < 0.5D)
                            {
                                conto.p[j].hsb[2] = 0.5F;
                            }
                            if((double)conto.p[j].hsb[1] > 0.10000000000000001D)
                            {
                                conto.p[j].hsb[1] = 0.1F;
                            }
                        }
                        if(conto.p[j].bfase > 40)
                        {
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if(conto.p[j].bfase > 50)
                        {
                            if((double)conto.p[j].hsb[2] > 0.80000000000000004D)
                            {
                                conto.p[j].hsb[2] = 0.8F;
                            }
                            conto.p[j].hsb[0] = 0.075F;
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if(conto.p[j].bfase > 60)
                        {
                            conto.p[j].hsb[0] = 0.05F;
                        }
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if(conto.p[j].glass)
                    {
                        conto.p[j].gr += Math.abs((double)f1 * 1.5D);
                    }
                }
            }

        }
    }

    public void play(ContO conto, Madness madness, int i, int j)
    {
        conto.x = x[j][i];
        conto.y = y[j][i];
        conto.z = z[j][i];
        conto.zy = zy[j][i];
        conto.xy = xy[j][i];
        conto.xz = xz[j][i];
        conto.wxz = wxz[j][i];
        conto.wzy = wzy[j][i];
        if(i == 0)
        {
            conto.m.checkpoint = checkpoint[j];
            conto.m.lastcheck = lastcheck[j];
        }
        if(j == 0)
        {
            cntdest[i] = 0;
        }
        if(dest[i] == j)
        {
            cntdest[i] = 7;
        }
        if(j == 0 && dest[i] < -1)
        {
            for(int k = 0; k < conto.npl; k++)
            {
                if(conto.p[k].wz == 0 || conto.p[k].gr == -17 || conto.p[k].gr == -16)
                {
                    conto.p[k].embos = 13;
                }
            }

        }
        if(cntdest[i] != 0)
        {
            for(int l = 0; l < conto.npl; l++)
            {
                if(conto.p[l].wz == 0 || conto.p[l].gr == -17 || conto.p[l].gr == -16)
                {
                    conto.p[l].embos = 1;
                }
            }

            cntdest[i]--;
        }
        int i1 = 0;
        do
        {
            int j1 = 0;
            do
            {
                if(sspark[i][i1][j1] == j)
                {
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
            } while(++j1 < 30);
            j1 = 0;
            do
            {
                if(ry[i][i1][j1] == j)
                {
                    regy(i1, magy[i][i1][j1], mtouch[i][j1], conto, madness);
                }
                if(rx[i][i1][j1] == j)
                {
                    regx(i1, magx[i][i1][j1], conto, madness);
                }
                if(rz[i][i1][j1] == j)
                {
                    regz(i1, magz[i][i1][j1], conto, madness);
                }
            } while(++j1 < 7);
        } while(++i1 < 4);
    }

    public void rec(ContO conto, int i, int j, int k, int l)
    {
        if(i == 0)
        {
            caught++;
        }
        if(cntf == 50)
        {
            int i1 = 0;
            do
            {
                car[i1][i] = new ContO(car[i1 + 1][i], 0, 0, 0, 0);
                squash[i1][i] = squash[i1 + 1][i];
            } while(++i1 < 5);
            car[5][i] = new ContO(conto, 0, 0, 0, 0);
            squash[5][i] = j;
            cntf = 0;
        } else
        {
            cntf++;
        }
        fix[i]--;
        if(l != 0)
        {
            dest[i]--;
        }
        if(dest[i] == 230)
        {
            if(i == 0)
            {
                cotchinow(0);
                whenwasted = 229;
            } else
            if(k != 0)
            {
                cotchinow(i);
                whenwasted = 165 + k;
            }
        }
        int j1 = 0;
        do
        {
            x[j1][i] = x[j1 + 1][i];
            y[j1][i] = y[j1 + 1][i];
            z[j1][i] = z[j1 + 1][i];
            zy[j1][i] = zy[j1 + 1][i];
            xy[j1][i] = xy[j1 + 1][i];
            xz[j1][i] = xz[j1 + 1][i];
            wxz[j1][i] = wxz[j1 + 1][i];
            wzy[j1][i] = wzy[j1 + 1][i];
        } while(++j1 < 299);
        x[299][i] = conto.x;
        y[299][i] = conto.y;
        z[299][i] = conto.z;
        xy[299][i] = conto.xy;
        zy[299][i] = conto.zy;
        xz[299][i] = conto.xz;
        wxz[299][i] = conto.wxz;
        wzy[299][i] = conto.wzy;
        if(i == 0)
        {
            j1 = 0;
            do
            {
                checkpoint[j1] = checkpoint[j1 + 1];
                lastcheck[j1] = lastcheck[j1 + 1];
            } while(++j1 < 299);
            checkpoint[299] = conto.m.checkpoint;
            lastcheck[299] = conto.m.lastcheck;
        }
        j1 = 0;
        do
        {
            if(conto.stg[j1] == 1)
            {
                sspark[i][j1][ns[i][j1]] = 300;
                sx[i][j1][ns[i][j1]] = conto.sx[j1];
                sy[i][j1][ns[i][j1]] = conto.sy[j1];
                sz[i][j1][ns[i][j1]] = conto.sz[j1];
                smag[i][j1][ns[i][j1]] = conto.smag[j1];
                scx[i][j1][ns[i][j1]] = conto.scx[j1];
                scz[i][j1][ns[i][j1]] = conto.scz[j1];
                fulls[i][j1][ns[i][j1]] = conto.fulls[j1];
                ns[i][j1]++;
                if(ns[i][j1] == 30)
                {
                    ns[i][j1] = 0;
                }
            }
            int k1 = 0;
            do
            {
                sspark[i][j1][k1]--;
            } while(++k1 < 30);
            k1 = 0;
            do
            {
                ry[i][j1][k1]--;
                rx[i][j1][k1]--;
                rz[i][j1][k1]--;
            } while(++k1 < 7);
        } while(++j1 < 4);
    }

    public void recx(int i, float f, int j)
    {
        rx[j][i][nry[j][i]] = 300;
        magx[j][i][nry[j][i]] = (int)f;
        nrx[j][i]++;
        if(nrx[j][i] == 7)
        {
            nrx[j][i] = 0;
        }
    }

    public void recy(int i, float f, boolean flag, int j)
    {
        ry[j][i][nry[j][i]] = 300;
        magy[j][i][nry[j][i]] = (int)f;
        mtouch[j][nry[j][i]] = flag;
        nry[j][i]++;
        if(nry[j][i] == 7)
        {
            nry[j][i] = 0;
        }
    }

    public void cotchinow(int i)
    {
        if(caught >= 300)
        {
            wasted = i;
            int j = 0;
            do
            {
                starcar[j] = new ContO(car[0][j], 0, 0, 0, 0);
                hsquash[j] = squash[0][j];
                hfix[j] = fix[j];
                hdest[j] = dest[j];
            } while(++j < 7);
            j = 0;
            do
            {
                int k = 0;
                do
                {
                    hx[j][k] = x[j][k];
                    hy[j][k] = y[j][k];
                    hz[j][k] = z[j][k];
                    hxy[j][k] = xy[j][k];
                    hzy[j][k] = zy[j][k];
                    hxz[j][k] = xz[j][k];
                    hwxz[j][k] = wxz[j][k];
                    hwzy[j][k] = wzy[j][k];
                } while(++k < 7);
                hcheckpoint[j] = checkpoint[j];
                hlastcheck[j] = lastcheck[j];
            } while(++j < 300);
            j = 0;
            do
            {
                int l = 0;
                do
                {
                    hns[j][l] = ns[j][l];
                    int k1 = 0;
                    do
                    {
                        hsspark[j][l][k1] = sspark[j][l][k1];
                        hsx[j][l][k1] = sx[j][l][k1];
                        hsy[j][l][k1] = sy[j][l][k1];
                        hsz[j][l][k1] = sz[j][l][k1];
                        hsmag[j][l][k1] = smag[j][l][k1];
                        hscx[j][l][k1] = scx[j][l][k1];
                        hscz[j][l][k1] = scz[j][l][k1];
                        hfulls[j][l][k1] = fulls[j][l][k1];
                    } while(++k1 < 30);
                } while(++l < 4);
            } while(++j < 7);
            j = 0;
            do
            {
                int i1 = 0;
                do
                {
                    hnry[j][i1] = nry[j][i1];
                    hnrx[j][i1] = nrx[j][i1];
                    hnrz[j][i1] = nrz[j][i1];
                    int l1 = 0;
                    do
                    {
                        hry[j][i1][l1] = ry[j][i1][l1];
                        hmagy[j][i1][l1] = magy[j][i1][l1];
                        hrx[j][i1][l1] = rx[j][i1][l1];
                        hmagx[j][i1][l1] = magx[j][i1][l1];
                        hrz[j][i1][l1] = rz[j][i1][l1];
                        hmagz[j][i1][l1] = magz[j][i1][l1];
                    } while(++l1 < 7);
                } while(++i1 < 4);
            } while(++j < 7);
            j = 0;
            do
            {
                int j1 = 0;
                do
                {
                    hmtouch[j][j1] = mtouch[j][j1];
                } while(++j1 < 7);
            } while(++j < 7);
            hcaught = true;
        }
    }

    public void chipx(int i, float f, ContO conto, Madness madness)
    {
        if(Math.abs(f) > 100F)
        {
            if(f > 100F)
            {
                f -= 100F;
            }
            if(f < -100F)
            {
                f += 100F;
            }
            for(int j = 0; j < conto.npl; j++)
            {
                float f1 = 0.0F;
                for(int k = 0; k < conto.p[j].n; k++)
                {
                    if(conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < madness.clrad[madness.cn])
                    {
                        f1 = (f / 20F) * m.random();
                    }
                }

                if(f1 != 0.0F && Math.abs(f1) >= 1.0F)
                {
                    conto.p[j].chip = 1;
                    conto.p[j].ctmag = f1;
                }
            }

        }
    }

    public void regx(int i, float f, ContO conto, Madness madness)
    {
        if(Math.abs(f) > 100F)
        {
            if(f > 100F)
            {
                f -= 100F;
            }
            if(f < -100F)
            {
                f += 100F;
            }
            for(int j = 0; j < conto.npl; j++)
            {
                float f1 = 0.0F;
                for(int k = 0; k < conto.p[j].n; k++)
                {
                    if(conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < madness.clrad[madness.cn])
                    {
                        f1 = (f / 20F) * m.random();
                        conto.p[j].oz[k] -= f1 * m.sin(conto.xz) * m.cos(conto.zy);
                        conto.p[j].ox[k] += f1 * m.cos(conto.xz) * m.cos(conto.xy);
                    }
                }

                if(f1 != 0.0F)
                {
                    if(Math.abs(f1) >= 1.0F)
                    {
                        conto.p[j].chip = 1;
                        conto.p[j].ctmag = f1;
                    }
                    if(!conto.p[j].nocol && !conto.p[j].glass)
                    {
                        if(conto.p[j].bfase > 20 && (double)conto.p[j].hsb[1] > 0.20000000000000001D)
                        {
                            conto.p[j].hsb[1] = 0.2F;
                        }
                        if(conto.p[j].bfase > 30)
                        {
                            if((double)conto.p[j].hsb[2] < 0.5D)
                            {
                                conto.p[j].hsb[2] = 0.5F;
                            }
                            if((double)conto.p[j].hsb[1] > 0.10000000000000001D)
                            {
                                conto.p[j].hsb[1] = 0.1F;
                            }
                        }
                        if(conto.p[j].bfase > 40)
                        {
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if(conto.p[j].bfase > 50)
                        {
                            if((double)conto.p[j].hsb[2] > 0.80000000000000004D)
                            {
                                conto.p[j].hsb[2] = 0.8F;
                            }
                            conto.p[j].hsb[0] = 0.075F;
                            conto.p[j].hsb[1] = 0.05F;
                        }
                        if(conto.p[j].bfase > 60)
                        {
                            conto.p[j].hsb[0] = 0.05F;
                        }
                        conto.p[j].bfase += Math.abs(f1);
                        new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
                        Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
                        conto.p[j].c[0] = color.getRed();
                        conto.p[j].c[1] = color.getGreen();
                        conto.p[j].c[2] = color.getBlue();
                    }
                    if(conto.p[j].glass)
                    {
                        conto.p[j].gr += Math.abs((double)f1 * 1.5D);
                    }
                }
            }

        }
    }

    public void recz(int i, float f, int j)
    {
        rz[j][i][nry[j][i]] = 300;
        magz[j][i][nry[j][i]] = (int)f;
        nrz[j][i]++;
        if(nrz[j][i] == 7)
        {
            nrz[j][i] = 0;
        }
    }

    public int py(int i, int j, int k, int l)
    {
        return (i - j) * (i - j) + (k - l) * (k - l);
    }
}
