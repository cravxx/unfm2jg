import java.awt.*;

public class Plane
{
    Medium m;
    Trackers t;
    int ox[];
    int oy[];
    int oz[];
    int n;
    int c[];
    int oc[];
    float hsb[];
    boolean glass;
    int gr;
    int fs;
    int disline;
    boolean road;
    int light;
    int master;
    int wx;
    int wz;
    int wy;
    float deltaf;
    float projf;
    int av;
    int bfase;
    boolean nocol;
    int chip;
    float ctmag;
    int cxz;
    int cxy;
    int czy;
    int cox[];
    int coz[];
    int coy[];
    int dx;
    int dy;
    int dz;
    int vx;
    int vy;
    int vz;
    int embos;
    int typ;
    int pa;
    int pb;
    int flx;

    public void loadprojf()
    {
        projf = 1.0F;
        int i = 0;
        do
        {
            int j = 0;
            do
            {
                if(j != i)
                {
                    projf *= (float)(Math.sqrt((ox[i] - ox[j]) * (ox[i] - ox[j]) + (oz[i] - oz[j]) * (oz[i] - oz[j])) / 100D);
                }
            } while(++j < 3);
        } while(++i < 3);
        projf = projf / 3F;
    }

    public int ys(int i, int j)
    {
        if(j < m.cz)
        {
            j = m.cz;
        }
        return ((j - m.focus_point) * (m.cy - i)) / j + i;
    }

    public Plane(Medium medium, Trackers trackers, int ai[], int ai1[], int ai2[], int i, int ai3[], 
            boolean flag, int j, int k, int l, int i1, int j1, int k1, 
            int l1, boolean flag1, int i2)
    {
        c = new int[3];
        oc = new int[3];
        hsb = new float[3];
        glass = false;
        gr = 0;
        fs = 0;
        disline = 7;
        road = false;
        light = 0;
        master = 0;
        wx = 0;
        wz = 0;
        wy = 0;
        deltaf = 1.0F;
        projf = 1.0F;
        av = 0;
        bfase = 0;
        nocol = false;
        chip = 0;
        ctmag = 0.0F;
        cxz = 0;
        cxy = 0;
        czy = 0;
        cox = new int[3];
        coz = new int[3];
        coy = new int[3];
        dx = 0;
        dy = 0;
        dz = 0;
        vx = 0;
        vy = 0;
        vz = 0;
        embos = 0;
        typ = 0;
        pa = 0;
        pb = 0;
        flx = 0;
        m = medium;
        t = trackers;
        n = i;
        ox = new int[n];
        oz = new int[n];
        oy = new int[n];
        for(int j2 = 0; j2 < n; j2++)
        {
            ox[j2] = ai[j2];
            oy[j2] = ai2[j2];
            oz[j2] = ai1[j2];
        }

        int k2 = Math.abs(ox[2] - ox[1]);
        int l2 = Math.abs(oy[2] - oy[1]);
        int i3 = Math.abs(oz[2] - oz[1]);
        if(l2 <= k2 && l2 <= i3)
        {
            typ = 2;
        }
        if(k2 <= l2 && k2 <= i3)
        {
            typ = 1;
        }
        if(i3 <= k2 && i3 <= l2)
        {
            typ = 3;
        }
        int j3 = 0;
        do
        {
            oc[j3] = ai3[j3];
        } while(++j3 < 3);
        if(j == -15)
        {
            j3 = (int)(185D + Math.random() * 30D);
            ai3[0] = (217 + j3) / 2;
            ai3[1] = (189 + j3) / 2;
            ai3[2] = (132 + j3) / 2;
            for(int k3 = 0; k3 < n; k3++)
            {
                if(Math.random() > Math.random())
                {
                    ox[k3] += (int)(8D * Math.random() - 4D);
                }
                if(Math.random() > Math.random())
                {
                    oy[k3] += (int)(8D * Math.random() - 4D);
                }
                if(Math.random() > Math.random())
                {
                    oz[k3] += (int)(8D * Math.random() - 4D);
                }
            }

        }
        if(ai3[0] == ai3[1] && ai3[1] == ai3[2])
        {
            nocol = true;
        }
        if(!flag)
        {
            j3 = 0;
            do
            {
                c[j3] = (int)((float)ai3[j3] + (float)ai3[j3] * ((float)m.snap[j3] / 100F));
                if(c[j3] > 255)
                {
                    c[j3] = 255;
                }
                if(c[j3] < 0)
                {
                    c[j3] = 0;
                }
            } while(++j3 < 3);
        }
        if(flag)
        {
            j3 = 0;
            do
            {
                c[j3] = (m.csky[j3] * m.fade[0] * 2 + m.cfade[j3] * 3000) / (m.fade[0] * 2 + 3000);
            } while(++j3 < 3);
        }
        disline = k1;
        bfase = l1;
        glass = flag;
        Color.RGBtoHSB(c[0], c[1], c[2], hsb);
        if(!nocol && !glass)
        {
            if(bfase > 20 && (double)hsb[1] > 0.25D)
            {
                hsb[1] = 0.25F;
            }
            if(bfase > 25 && (double)hsb[2] > 0.69999999999999996D)
            {
                hsb[2] = 0.7F;
            }
            if(bfase > 30 && (double)hsb[1] > 0.14999999999999999D)
            {
                hsb[1] = 0.15F;
            }
            if(bfase > 35 && (double)hsb[2] > 0.59999999999999998D)
            {
                hsb[2] = 0.6F;
            }
            if(bfase > 40)
            {
                hsb[0] = 0.075F;
            }
            if(bfase > 50 && (double)hsb[2] > 0.5D)
            {
                hsb[2] = 0.5F;
            }
            if(bfase > 60)
            {
                hsb[0] = 0.05F;
            }
        }
        road = flag1;
        light = i2;
        gr = j;
        fs = k;
        wx = l;
        wy = i1;
        wz = j1;
        j3 = 0;
        do
        {
            int l3 = 0;
            do
            {
                if(l3 != j3)
                {
                    deltaf *= (float)(Math.sqrt((ox[l3] - ox[j3]) * (ox[l3] - ox[j3]) + (oy[l3] - oy[j3]) * (oy[l3] - oy[j3]) + (oz[l3] - oz[j3]) * (oz[l3] - oz[j3])) / 100D);
                }
            } while(++l3 < 3);
        } while(++j3 < 3);
        deltaf = deltaf / 3F;
    }

    public void d(Graphics2D rd, int i, int j, int k, int l, int i1, int j1, 
            int k1, int l1, boolean flag, int i2)
    {
        if(master != 0)
        {
            if(av > 1500)
            {
                n = 8;
            } else
            {
                n = 16;
            }
        }
        int ai[] = new int[n];
        int ai1[] = new int[n];
        int ai2[] = new int[n];
        if(embos == 0)
        {
            for(int j2 = 0; j2 < n; j2++)
            {
                ai[j2] = ox[j2] + i;
                ai2[j2] = oy[j2] + j;
                ai1[j2] = oz[j2] + k;
            }

            if((gr == -11 || gr == -13) && m.lastmaf == 1)
            {
                for(int k2 = 0; k2 < n; k2++)
                {
                    ai[k2] = -ox[k2] + i;
                    ai2[k2] = oy[k2] + j;
                    ai1[k2] = -oz[k2] + k;
                }

            }
        } else
        {
            if(embos <= 11 && (double)m.random() > 0.5D && !glass)
            {
                for(int l2 = 0; l2 < n; l2++)
                {
                    ai[l2] = (int)((float)(ox[l2] + i) + (15F - m.random() * 30F));
                    ai2[l2] = (int)((float)(oy[l2] + j) + (15F - m.random() * 30F));
                    ai1[l2] = (int)((float)(oz[l2] + k) + (15F - m.random() * 30F));
                }

                rot(ai, ai2, i, j, i1, n);
                rot(ai2, ai1, j, k, j1, n);
                rot(ai, ai1, i, k, l, n);
                rot(ai, ai1, m.cx, m.cz, m.xz, n);
                rot(ai2, ai1, m.cy, m.cz, m.zy, n);
                int ai3[] = new int[n];
                int ai5[] = new int[n];
                for(int i4 = 0; i4 < n; i4++)
                {
                    ai3[i4] = xs(ai[i4], ai1[i4]);
                    ai5[i4] = ys(ai2[i4], ai1[i4]);
                }

                rd.setColor(new Color(230, 230, 230));
                rd.fillPolygon(ai3, ai5, n);
            }
            float f = 1.0F;
            if(embos <= 4)/// explode outward a little + white flame
            {
                f = 1.0F + m.random() / 5F;
            }
            if(embos > 4 && embos <= 7)/// explode outward a little + white flame a bit bigger than previous
            {
                f = 1.0F + m.random() / 4F;
            }
            if(embos > 7 && embos <= 9)/////// /// explode outward a little + white flame a bit bigger than previous + polys start to darken
            {
                f = 1.0F + m.random() / 3F;
                if((double)hsb[2] > 0.69999999999999996D)
                {
                    hsb[2] = 0.7F;
                }
            }
            if(embos > 9 && embos <= 10)/////// /// explode outward a little + white flame a bit bigger than previous + maor darkening
            {
                f = 1.0F + m.random() / 2.0F;
                if((double)hsb[2] > 0.59999999999999998D)
                {
                    hsb[2] = 0.6F;
                }
            }
            if(embos > 10 && embos <= 12)/////// /// explode outward a little + white flame a bit bigger than previous + more darkkkk liek coffee beans
            {
                f = 1.0F + m.random() / 1.0F;
                if((double)hsb[2] > 0.5D)
                {
                    hsb[2] = 0.5F;
                }
            }
            if(embos == 12)///////polys explode outward
            {
                chip = 1;
                ctmag = 2.0F;
                bfase = -7;
            }
            if(embos == 13)/////// dark polys
            {
                hsb[1] = 0.2F;
                hsb[2] = 0.4F;
            }
            if(embos == 16)//// flames
            {
                pa = (int)(m.random() * (float)n);
                for(pb = (int)(m.random() * (float)n); pa == pb; pb = (int)(m.random() * (float)n)) { }
            }
            if(embos >= 16) /////flames as well
            {
                byte byte0 = 1;
                byte byte1 = 1;
                int j4;
                for(j4 = Math.abs(j1); j4 > 270; j4 -= 360) { }
                j4 = Math.abs(j4);
                if(j4 > 90)
                {
                    byte0 = -1;
                }
                int i5;
                for(i5 = Math.abs(i1); i5 > 270; i5 -= 360) { }
                i5 = Math.abs(i5);
                if(i5 > 90)
                {
                    byte1 = -1;
                }
                int ai12[] = new int[3];
                int ai13[] = new int[3];
                ai[0] = ox[pa] + i;
                ai2[0] = oy[pa] + j;
                ai1[0] = oz[pa] + k;
                ai[1] = ox[pb] + i;
                ai2[1] = oy[pb] + j;
                for(ai1[1] = oz[pb] + k; Math.abs(ai[0] - ai[1]) > 100;)
                {
                    if(ai[1] > ai[0])
                    {
                        ai[1] -= 30;
                    } else
                    {
                        ai[1] += 30;
                    }
                }

                while(Math.abs(ai1[0] - ai1[1]) > 100) 
                {
                    if(ai1[1] > ai1[0])
                    {
                        ai1[1] -= 30;
                    } else
                    {
                        ai1[1] += 30;
                    }
                }
                int i7 = (int)((double)(Math.abs(ai[0] - ai[1]) / 3) * (0.5D - (double)m.random()));
                int l7 = (int)((double)(Math.abs(ai1[0] - ai1[1]) / 3) * (0.5D - (double)m.random()));
                ai[2] = (ai[0] + ai[1]) / 2 + i7;
                ai1[2] = (ai1[0] + ai1[1]) / 2 + l7;
                int i8 = (int)(((double)(Math.abs(ai[0] - ai[1]) + Math.abs(ai1[0] - ai1[1])) / 1.5D) * ((double)(m.random() / 2.0F) + 0.5D));
                ai2[2] = (ai2[0] + ai2[1]) / 2 - byte0 * byte1 * i8;
                rot(ai, ai2, i, j, i1, 3);
                rot(ai2, ai1, j, k, j1, 3);
                rot(ai, ai1, i, k, l, 3);
                rot(ai, ai1, m.cx, m.cz, m.xz, 3);
                rot(ai2, ai1, m.cy, m.cz, m.zy, 3);
                int k8 = 0;
                do
                {
                    ai12[k8] = xs(ai[k8], ai1[k8]);
                    ai13[k8] = ys(ai2[k8], ai1[k8]);
                } while(++k8 < 3);
                 //Flame color. Outer Flame Red Value
                k8 = (int)(255F + 255F * ((float)m.snap[0] / 400F));
                if(k8 > 255)
                {
                    k8 = 255;
                }
                if(k8 < 0)
                {
                    k8 = 0;
                }
                //Flame color. Outer Flame Green Value
                int i9 = (int)(169F + 169F * ((float)m.snap[1] / 300F));
                if(i9 > 255)
                {
                    i9 = 255;
                }
                if(i9 < 0)
                {
                    i9 = 0;
                }
                //Flame color. Outer Flame Blue Value
                int k9 = (int)(89F + 89F * ((float)m.snap[2] / 200F));
                if(k9 > 255)
                {
                    k9 = 255;
                }
                if(k9 < 0)
                {
                    k9 = 0;
                }
                rd.setColor(new Color(k8, i9, k9));
                rd.fillPolygon(ai12, ai13, 3);
                ai[0] = ox[pa] + i;
                ai2[0] = oy[pa] + j;
                ai1[0] = oz[pa] + k;
                ai[1] = ox[pb] + i;
                ai2[1] = oy[pb] + j;
                for(ai1[1] = oz[pb] + k; Math.abs(ai[0] - ai[1]) > 100;)
                {
                    if(ai[1] > ai[0])
                    {
                        ai[1] -= 30;
                    } else
                    {
                        ai[1] += 30;
                    }
                }

                while(Math.abs(ai1[0] - ai1[1]) > 100) 
                {
                    if(ai1[1] > ai1[0])
                    {
                        ai1[1] -= 30;
                    } else
                    {
                        ai1[1] += 30;
                    }
                }
                ai[2] = (ai[0] + ai[1]) / 2 + i7;
                ai1[2] = (ai1[0] + ai1[1]) / 2 + l7;
                i8 = (int)((double)i8 * 0.80000000000000004D);
                ai2[2] = (ai2[0] + ai2[1]) / 2 - byte0 * byte1 * i8;
                rot(ai, ai2, i, j, i1, 3);
                rot(ai2, ai1, j, k, j1, 3);
                rot(ai, ai1, i, k, l, 3);
                rot(ai, ai1, m.cx, m.cz, m.xz, 3);
                rot(ai2, ai1, m.cy, m.cz, m.zy, 3);
                int i10 = 0;
                do
                {
                    ai12[i10] = xs(ai[i10], ai1[i10]);
                    ai13[i10] = ys(ai2[i10], ai1[i10]);
                } while(++i10 < 3);
                //Flame color. Inner Flame Red Value
                k8 = (int)(255F + 255F * ((float)m.snap[0] / 400F));
                if(k8 > 255)
                {
                    k8 = 255;
                }
                if(k8 < 0)
                {
                    k8 = 0;
                }
                //Flame color. Inner Flame Green Value
                i9 = (int)(207F + 207F * ((float)m.snap[1] / 300F));
                if(i9 > 255)
                {
                    i9 = 255;
                }
                if(i9 < 0)
                {
                    i9 = 0;
                }
                //Flame color. Inner Flame Blue Value
                k9 = (int)(136F + 136F * ((float)m.snap[2] / 200F));
                if(k9 > 255)
                {
                    k9 = 255;
                }
                if(k9 < 0)
                {
                    k9 = 0;
                }
                rd.setColor(new Color(k8, i9, k9));
                rd.fillPolygon(ai12, ai13, 3);
            }
            for(int k3 = 0; k3 < n; k3++)
            {
                if(typ == 1)
                {
                    ai[k3] = (int)((float)ox[k3] * f + (float)i);
                } else
                {
                    ai[k3] = ox[k3] + i;
                }
                if(typ == 2)
                {
                    ai2[k3] = (int)((float)oy[k3] * f + (float)j);
                } else
                {
                    ai2[k3] = oy[k3] + j;
                }
                if(typ == 3)
                {
                    ai1[k3] = (int)((float)oz[k3] * f + (float)k);
                } else
                {
                    ai1[k3] = oz[k3] + k;
                }
            }

            if(embos != 70)
            {
                embos++;
            } else
            {
                embos = 16;
            }
        }
        if(wz != 0)
        {
            rot(ai2, ai1, wy + j, wz + k, l1, n);
        }
        if(wx != 0)
        {
            rot(ai, ai1, wx + i, wz + k, k1, n);
        }
        if(chip == 1 && ((double)m.random() > 0.59999999999999998D || bfase == 0))
        {
            chip = 0;
            if(bfase == 0 && nocol)
            {
                bfase = 1;
            }
        }
        if(chip != 0)
        {
            if(chip == 1)
            {
                cxz = l;
                cxy = i1;
                czy = j1;
                int i3 = (int)(m.random() * (float)n);
                cox[0] = ox[i3];
                coz[0] = oz[i3];
                coy[0] = oy[i3];
                if(ctmag > 3F)
                {
                    ctmag = 3F;
                }
                if(ctmag < -3F)
                {
                    ctmag = -3F;
                }
                cox[1] = (int)((float)cox[0] + ctmag * (10F - m.random() * 20F));
                cox[2] = (int)((float)cox[0] + ctmag * (10F - m.random() * 20F));
                coy[1] = (int)((float)coy[0] + ctmag * (10F - m.random() * 20F));
                coy[2] = (int)((float)coy[0] + ctmag * (10F - m.random() * 20F));
                coz[1] = (int)((float)coz[0] + ctmag * (10F - m.random() * 20F));
                coz[2] = (int)((float)coz[0] + ctmag * (10F - m.random() * 20F));
                dx = 0;
                dy = 0;
                dz = 0;
                if(bfase != -7)
                {
                    vx = (int)(ctmag * (30F - m.random() * 60F));
                    vz = (int)(ctmag * (30F - m.random() * 60F));
                    vy = (int)(ctmag * (30F - m.random() * 60F));
                } else
                {
                    vx = (int)(ctmag * (10F - m.random() * 20F));
                    vz = (int)(ctmag * (10F - m.random() * 20F));
                    vy = (int)(ctmag * (10F - m.random() * 20F));
                }
                chip = 2;
            }
            int ai4[] = new int[3];
            int ai6[] = new int[3];
            int ai8[] = new int[3];
            int k4 = 0;
            do
            {
                ai4[k4] = cox[k4] + i;
                ai8[k4] = coy[k4] + j;
                ai6[k4] = coz[k4] + k;
            } while(++k4 < 3);
            rot(ai4, ai8, i, j, cxy, 3);
            rot(ai8, ai6, j, k, czy, 3);
            rot(ai4, ai6, i, k, cxz, 3);
            k4 = 0;
            do
            {
                ai4[k4] += dx;
                ai8[k4] += dy;
                ai6[k4] += dz;
            } while(++k4 < 3);
            dx += vx;
            dz += vz;
            dy += vy;
            vy += 7;
            if(ai8[0] > m.ground)
            {
                chip = 19;
            }
            rot(ai4, ai6, m.cx, m.cz, m.xz, 3);
            rot(ai8, ai6, m.cy, m.cz, m.zy, 3);
            int ai10[] = new int[3];
            int ai11[] = new int[3];
            int l5 = 0;
            do
            {
                ai10[l5] = xs(ai4[l5], ai6[l5]);
                ai11[l5] = ys(ai8[l5], ai6[l5]);
                if(ai11[l5] < 45 && m.flex != 0)
                {
                    m.flex = 0;
                }
            } while(++l5 < 3);
            l5 = (int)(m.random() * 3F);
            if(bfase != -7)
            {
                if(l5 == 0)
                {
                    rd.setColor((new Color(c[0], c[1], c[2])).darker());
                }
                if(l5 == 1)
                {
                    rd.setColor(new Color(c[0], c[1], c[2]));
                }
                if(l5 == 2)
                {
                    rd.setColor((new Color(c[0], c[1], c[2])).brighter());
                }
            } else
            {
                rd.setColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
            }
            rd.fillPolygon(ai10, ai11, 3);
            chip++;
            if(chip == 20)
            {
                chip = 0;
            }
        }
        rot(ai, ai2, i, j, i1, n);
        rot(ai2, ai1, j, k, j1, n);
        rot(ai, ai1, i, k, l, n);
        if(i1 != 0 || j1 != 0 || l != 0)
        {
            projf = 1.0F;
            int j3 = 0;
            do
            {
                int l3 = 0;
                do
                {
                    if(l3 != j3)
                    {
                        projf *= (float)(Math.sqrt((ai[j3] - ai[l3]) * (ai[j3] - ai[l3]) + (ai1[j3] - ai1[l3]) * (ai1[j3] - ai1[l3])) / 100D);
                    }
                } while(++l3 < 3);
            } while(++j3 < 3);
            projf = projf / 3F;
        }
        rot(ai, ai1, m.cx, m.cz, m.xz, n);
        boolean flag1 = false;
        int ai7[] = new int[n];
        int ai9[] = new int[n];
        int l4 = 500;
        for(int j5 = 0; j5 < n; j5++)
        {
            ai7[j5] = xs(ai[j5], ai1[j5]);
            ai9[j5] = ys(ai2[j5], ai1[j5]);
        }

        int k5 = 0;
        int i6 = 1;
        for(int j6 = 0; j6 < n; j6++)
        {
            for(int j7 = 0; j7 < n; j7++)
            {
                if(j6 != j7 && Math.abs(ai7[j6] - ai7[j7]) - Math.abs(ai9[j6] - ai9[j7]) < l4)
                {
                    i6 = j6;
                    k5 = j7;
                    l4 = Math.abs(ai7[j6] - ai7[j7]) - Math.abs(ai9[j6] - ai9[j7]);
                }
            }

        }

        if(ai9[k5] < ai9[i6])
        {
            int k6 = k5;
            k5 = i6;
            i6 = k6;
        }
        if(spy(ai[k5], ai1[k5]) > spy(ai[i6], ai1[i6]))
        {
            flag1 = true;
            int l6 = 0;
            for(int k7 = 0; k7 < n; k7++)
            {
                if(ai1[k7] < 50 && ai2[k7] > m.cy)
                {
                    flag1 = false;
                } else
                if(ai2[k7] == ai2[0])
                {
                    l6++;
                }
            }

            if(l6 == n && ai2[0] > m.cy)
            {
                flag1 = false;
            }
        }
        rot(ai2, ai1, m.cy, m.cz, m.zy, n);
        boolean flag2 = true;
        int ai14[] = new int[n];
        int ai15[] = new int[n];
        int j8 = 0;
        int l8 = 0;
        int j9 = 0;
        int l9 = 0;
        int j10 = 0;
        for(int k10 = 0; k10 < n; k10++)
        {
            ai14[k10] = xs(ai[k10], ai1[k10]);
            ai15[k10] = ys(ai2[k10], ai1[k10]);
            if(ai15[k10] < 0 || ai1[k10] < 10)
            {
                j8++;
            }
            if(ai15[k10] > m.h || ai1[k10] < 10)
            {
                l8++;
            }
            if(ai14[k10] < 0 || ai1[k10] < 10)
            {
                j9++;
            }
            if(ai14[k10] > m.w || ai1[k10] < 10)
            {
                l9++;
            }
            if(ai15[k10] < 45 && m.flex != 0)
            {
                m.flex = 0;
            }
            if(ai1[k10] < 10)
            {
                j10++;
            }
        }

        if(i2 != -1)
        {
            int l10 = 0;
            int j11 = 0;
            for(int k11 = 0; k11 < n; k11++)
            {
                for(int l12 = k11; l12 < n; l12++)
                {
                    if(k11 != l12)
                    {
                        if(Math.abs(ai14[k11] - ai14[l12]) > l10)
                        {
                            l10 = Math.abs(ai14[k11] - ai14[l12]);
                        }
                        if(Math.abs(ai15[k11] - ai15[l12]) > j11)
                        {
                            j11 = Math.abs(ai15[k11] - ai15[l12]);
                        }
                    }
                }

            }

            if(l10 == 0 || j11 == 0)
            {
                flag2 = false;
            } else
            if(l10 < 3 && j11 < 3 && i2 / l10 > 15 && i2 / j11 > 15)
            {
                flag2 = false;
            }
        }
        if(j9 == n || j8 == n || l8 == n || l9 == n)
        {
            flag2 = false;
        }
        if(m.trk && (j9 != 0 || j8 != 0 || l8 != 0 || l9 != 0))
        {
            flag2 = false;
        }
        if(j10 != 0)
        {
            flag = true;
        }
        if(flag2)
        {
            int i11 = 1;
            byte byte2 = 1;
            byte byte3 = 1;
            if(Math.abs(ai15[0] - ai15[1]) > Math.abs(ai15[2] - ai15[1]))
            {
                byte2 = 0;
                byte3 = 2;
            } else
            {
                byte2 = 2;
                byte3 = 0;
                i11 *= -1;
            }
            if(ai15[1] > ai15[byte2])
            {
                i11 *= -1;
            }
            if(ai14[1] > ai14[byte3])
            {
                i11 *= -1;
            }
            int i13 = gr;
            if(i13 < 0 && i13 >= -17)
            {
                i13 = 0;
            }
            if(gr == -11)
            {
                i13 = -90;
            }
            if(gr == -14 || gr == -15)
            {
                i13 = -50;
            }
            if(gr == -16)
            {
                i13 = 35;
            }
            if(fs != 0)
            {
                i11 *= fs;
                if(i11 == -1)
                {
                    i13 += 40;
                    if(!road)
                    {
                        i11 = -111;
                    }
                }
            }
            if(m.lightson && light == 2)
            {
                i13 -= 40;
            }
            int j14 = 0;
            int k15 = 0;
            int k16 = 0;
            int l16 = 0;
            int i17 = 0;
            int j17 = 0;
            for(int k17 = 0; k17 < n; k17++)
            {
                int i18 = 0;
                int k18 = 0;
                int i19 = 0;
                int j19 = 0;
                int k19 = 0;
                int l19 = 0;
                for(int i20 = 0; i20 < n; i20++)
                {
                    if(ai2[k17] >= ai2[i20])
                    {
                        i18++;
                    }
                    if(ai2[k17] <= ai2[i20])
                    {
                        k18++;
                    }
                    if(ai[k17] >= ai[i20])
                    {
                        i19++;
                    }
                    if(ai[k17] <= ai[i20])
                    {
                        j19++;
                    }
                    if(ai1[k17] >= ai1[i20])
                    {
                        k19++;
                    }
                    if(ai1[k17] <= ai1[i20])
                    {
                        l19++;
                    }
                }

                if(i18 == n)
                {
                    j14 = ai2[k17];
                }
                if(k18 == n)
                {
                    k15 = ai2[k17];
                }
                if(i19 == n)
                {
                    k16 = ai[k17];
                }
                if(j19 == n)
                {
                    l16 = ai[k17];
                }
                if(k19 == n)
                {
                    i17 = ai1[k17];
                }
                if(l19 == n)
                {
                    j17 = ai1[k17];
                }
            }

            int l17 = (j14 + k15) / 2;
            int j18 = (k16 + l16) / 2;
            int l18 = (i17 + j17) / 2;
            av = (int)Math.sqrt((m.cy - l17) * (m.cy - l17) + (m.cx - j18) * (m.cx - j18) + l18 * l18 + i13 * i13 * i13);
            if(!m.trk && (av > m.fade[disline] || av == 0))
            {
                flag2 = false;
            }
            if(i11 == -111 && av > 4500)
            {
                flag2 = false;
            }
            if(i11 == -111 && av > 1500)
            {
                flag = true;
            }
            if(av > 3000 && m.adv <= 900)
            {
                flag = true;
            }
            if(gr == -12 && av < 11200)
            {
                m.lastmaf = i11;
            }
            if(gr == -13 && (!m.lastcheck || i2 != -1))
            {
                flag2 = false;
            }
            if(gr == -16 && av > 1500)
            {
                flag2 = false;
            }
            if(flx != 0 && (double)m.random() > 0.29999999999999999D)
            {
                flag2 = false;
            }
        }
        if(flag2)
        {
            float f1 = (float)((double)(projf / deltaf) + 0.29999999999999999D);
            if(flag)
            {
                boolean flag3 = false;
                if(f1 > 1.0F)
                {
                    if((double)f1 >= 1.27D)
                    {
                        flag3 = true;
                    }
                    f1 = 1.0F;
                }
                if(flag3)
                {
                    f1 = (float)((double)f1 * 0.89000000000000001D);
                } else
                {
                    f1 = (float)((double)f1 * 0.85999999999999999D);
                }
                if((double)f1 < 0.37D)
                {
                    f1 = 0.37F;
                }
                if(gr == -9)
                {
                    f1 = 0.7F;
                }
                if(gr == -4)
                {
                    f1 = 0.74F;
                }
                if(gr != -7 && flag1)
                {
                    f1 = 0.32F;
                }
                if(gr == -8 || gr == -14 || gr == -15)
                {
                    f1 = 1.0F;
                }
                if(gr == -11)
                {
                    f1 = 0.67F;
                    if(i2 == -1)
                    {
                        if(m.cpflik || m.nochekflk && !m.lastcheck)
                        {
                            f1 = 1.0F;
                        } else
                        {
                            f1 = 0.76F;
                        }
                    }
                }
                if(gr == -13 && i2 == -1)
                {
                    if(m.cpflik)
                    {
                        f1 = 0.0F;
                    } else
                    {
                        f1 = 0.76F;
                    }
                }
                if(gr == -6)
                {
                    f1 = 0.62F;
                }
                if(gr == -5)
                {
                    f1 = 0.55F;
                }
            } else
            {
                if(f1 > 1.0F)
                {
                    f1 = 1.0F;
                }
                if((double)f1 < 0.59999999999999998D || flag1)
                {
                    f1 = 0.6F;
                }
            }
            Color color;
            if(!m.trk)
            {
                color = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * f1);
            } else
            {
                float af[] = new float[3];
                Color.RGBtoHSB(oc[0], oc[1], oc[2], af);
                color = Color.getHSBColor(0.0F, 0.0F, af[2] * f1);
            }
            int l11 = color.getRed();
            int j13 = color.getGreen();
            int k14 = color.getBlue();
            if(m.lightson && light != 0)
            {
                l11 = oc[0];
                if(l11 > 255)
                {
                    l11 = 255;
                }
                if(l11 < 0)
                {
                    l11 = 0;
                }
                j13 = oc[1];
                if(j13 > 255)
                {
                    j13 = 255;
                }
                if(j13 < 0)
                {
                    j13 = 0;
                }
                k14 = oc[2];
                if(k14 > 255)
                {
                    k14 = 255;
                }
                if(k14 < 0)
                {
                    k14 = 0;
                }           
            }            
            if(!m.trk)
            {
                int l15 = 0;
                do
                {
                    if(av > m.fade[l15])
                    {
                        l11 = (l11 * m.fogd + m.cfade[0]) / (m.fogd + 1);
                        j13 = (j13 * m.fogd + m.cfade[1]) / (m.fogd + 1);
                        k14 = (k14 * m.fogd + m.cfade[2]) / (m.fogd + 1);
                    }
                } while(++l15 < 8);
            }
            rd.setColor(new Color(l11, j13, k14));
            rd.fillPolygon(ai14, ai15, n);
            if(m.trk && gr == -10)
            {
                flag = false;
            }
            if(!flag)
            {
                if(flx == 0)
                {
                    l11 = 0;
                    j13 = 0;
                    k14 = 0;
                    if(m.lightson && light != 0)
                    {
                        l11 = oc[0] / 2;
                        if(l11 > 255)
                        {
                            l11 = 255;
                        }
                        if(l11 < 0)
                        {
                            l11 = 0;
                        }
                        j13 = oc[1] / 2;
                        if(j13 > 255)
                        {
                            j13 = 255;
                        }
                        if(j13 < 0)
                        {
                            j13 = 0;
                        }
                        k14 = oc[2] / 2;
                        if(k14 > 255)
                        {
                            k14 = 255;
                        }
                        if(k14 < 0)
                        {
                            k14 = 0;
                        }
                    }
                    rd.setColor(new Color(l11, j13, k14));
                    rd.drawPolygon(ai14, ai15, n);
                } else
                {
                    if(flx == 2)
                    {
                        rd.setColor(new Color(0, 0, 0));
                        rd.drawPolygon(ai14, ai15, n);
                    }
                    if(flx == 1)
                    {
                        l11 = 0;
                        j13 = (int)(223F + 223F * ((float)m.snap[1] / 100F));
                        if(j13 > 255)
                        {
                            j13 = 255;
                        }
                        if(j13 < 0)
                        {
                            j13 = 0;
                        }
                        k14 = (int)(255F + 255F * ((float)m.snap[2] / 100F));
                        if(k14 > 255)
                        {
                            k14 = 255;
                        }
                        if(k14 < 0)
                        {
                            k14 = 0;
                        }
                        rd.setColor(new Color(l11, j13, k14));
                        rd.drawPolygon(ai14, ai15, n);
                        flx = 2;
                    }
                    if(flx == 3)
                    {
                        l11 = 0;
                        j13 = (int)(255F + 255F * ((float)m.snap[1] / 100F));
                        if(j13 > 255)
                        {
                            j13 = 255;
                        }
                        if(j13 < 0)
                        {
                            j13 = 0;
                        }
                        k14 = (int)(223F + 223F * ((float)m.snap[2] / 100F));
                        if(k14 > 255)
                        {
                            k14 = 255;
                        }
                        if(k14 < 0)
                        {
                            k14 = 0;
                        }
                        rd.setColor(new Color(l11, j13, k14));
                        rd.drawPolygon(ai14, ai15, n);
                        flx = 2;
                    }
                }
            } else
            if(road && av <= 3000 && !m.trk && m.fade[0] > 4000)
            {
                if((l11 -= 10) < 0)
                {
                    l11 = 0;
                }
                if((j13 -= 10) < 0)
                {
                    j13 = 0;
                }
                if((k14 -= 10) < 0)
                {
                    k14 = 0;
                }
                rd.setColor(new Color(l11, j13, k14));
                rd.drawPolygon(ai14, ai15, n);
            }
            if(gr == -10)
            {
                if(!m.trk)
                {
                    int i12 = c[0];
                    int k13 = c[1];
                    int l14 = c[2];
                    if(i2 == -1)
                    {
                        if(m.nochekflk && !m.lastcheck)
                        {
                            i12 = (int)((double)i12 * 1.25D);
                            if(i12 > 255)
                            {
                                i12 = 255;
                            }
                            k13 = (int)((double)k13 * 1.25D);
                            if(k13 > 255)
                            {
                                k13 = 255;
                            }
                            l14 = (int)((double)l14 * 1.25D);
                            if(l14 > 255)
                            {
                                l14 = 255;
                            }
                        } else
                        if(m.cpflik)
                        {
                            i12 = (int)((double)i12 * 1.5D);
                            if(i12 > 255)
                            {
                                i12 = 255;
                            }
                            k13 = (int)((double)k13 * 1.5D);
                            if(k13 > 255)
                            {
                                k13 = 255;
                            }
                            l14 = (int)((double)l14 * 1.5D);
                            if(l14 > 255)
                            {
                                l14 = 255;
                            }
                        }
                    }
                    int i16 = 0;
                    do
                    {
                        if(av > m.fade[i16])
                        {
                            i12 = (i12 * m.fogd + m.cfade[0]) / (m.fogd + 1);
                            k13 = (k13 * m.fogd + m.cfade[1]) / (m.fogd + 1);
                            l14 = (l14 * m.fogd + m.cfade[2]) / (m.fogd + 1);
                        }
                    } while(++i16 < 8);
                    rd.setColor(new Color(i12, k13, l14));
                    rd.drawPolygon(ai14, ai15, n);
                } else
                if(m.cpflik && m.hit == 5000)
                {
                    int l13 = (int)(Math.random() * 115D);
                    int j12 = l13 * 2 - 54;
                    if(j12 < 0)
                    {
                        j12 = 0;
                    }
                    if(j12 > 255)
                    {
                        j12 = 255;
                    }
                    int i15 = 202 + l13 * 2;
                    if(i15 < 0)
                    {
                        i15 = 0;
                    }
                    if(i15 > 255)
                    {
                        i15 = 255;
                    }
                    if((l13 += 101) < 0)
                    {
                        l13 = 0;
                    }
                    if(l13 > 255)
                    {
                        l13 = 255;
                    }
                    rd.setColor(new Color(j12, l13, i15));
                    rd.drawPolygon(ai14, ai15, n);
                }
            }
            if(gr == -18 && !m.trk)
            {
                int k12 = c[0];
                int i14 = c[1];
                int j15 = c[2];
                if(m.cpflik && m.elecr >= 0.0F)
                {
                    k12 = (int)(25.5F * m.elecr);
                    if(k12 > 255)
                    {
                        k12 = 255;
                    }
                    i14 = (int)(128F + 12.8F * m.elecr);
                    if(i14 > 255)
                    {
                        i14 = 255;
                    }
                    j15 = 255;
                }
                int j16 = 0;
                do
                {
                    if(av > m.fade[j16])
                    {
                        k12 = (k12 * m.fogd + m.cfade[0]) / (m.fogd + 1);
                        i14 = (i14 * m.fogd + m.cfade[1]) / (m.fogd + 1);
                        j15 = (j15 * m.fogd + m.cfade[2]) / (m.fogd + 1);
                    }
                } while(++j16 < 8);
                rd.setColor(new Color(k12, i14, j15));
                rd.drawPolygon(ai14, ai15, n);
            }
        }
    }

    public void rot(int ai[], int ai1[], int i, int j, int k, int l)
    {
        if(k != 0)
        {
            for(int i1 = 0; i1 < l; i1++)
            {
                int j1 = ai[i1];
                int k1 = ai1[i1];
                ai[i1] = i + (int)((float)(j1 - i) * m.cos(k) - (float)(k1 - j) * m.sin(k));
                ai1[i1] = j + (int)((float)(j1 - i) * m.sin(k) + (float)(k1 - j) * m.cos(k));
            }

        }
    }

    public int xs(int i, int j)
    {
        if(j < m.cz)
        {
            j = m.cz;
        }
        return ((j - m.focus_point) * (m.cx - i)) / j + i;
    }

    public void s(Graphics2D rd, int i, int j, int k, int l, int i1, int j1, 
            int k1)
    {
        int ai[] = new int[n];
        int ai1[] = new int[n];
        int ai2[] = new int[n];
        for(int l1 = 0; l1 < n; l1++)
        {
            ai[l1] = ox[l1] + i;
            ai2[l1] = oy[l1] + j;
            ai1[l1] = oz[l1] + k;
        }

        rot(ai, ai2, i, j, i1, n);
        rot(ai2, ai1, j, k, j1, n);
        rot(ai, ai1, i, k, l, n);
        int i2 = (int)((double)(float)m.cgrnd[0] / 1.5D);
        int j2 = (int)((double)(float)m.cgrnd[1] / 1.5D);
        int k2 = (int)((double)(float)m.cgrnd[2] / 1.5D);
        for(int l2 = 0; l2 < n; l2++)
        {
            ai2[l2] = m.ground;
        }

        if(k1 == 0)
        {
            int i3 = 0;
            int j3 = 0;
            int k3 = 0;
            int l3 = 0;
            for(int l4 = 0; l4 < n; l4++)
            {
                int l5 = 0;
                int k6 = 0;
                int j7 = 0;
                int i8 = 0;
                for(int k8 = 0; k8 < n; k8++)
                {
                    if(ai[l4] >= ai[k8])
                    {
                        l5++;
                    }
                    if(ai[l4] <= ai[k8])
                    {
                        k6++;
                    }
                    if(ai1[l4] >= ai1[k8])
                    {
                        j7++;
                    }
                    if(ai1[l4] <= ai1[k8])
                    {
                        i8++;
                    }
                }

                if(l5 == n)
                {
                    i3 = ai[l4];
                }
                if(k6 == n)
                {
                    j3 = ai[l4];
                }
                if(j7 == n)
                {
                    k3 = ai1[l4];
                }
                if(i8 == n)
                {
                    l3 = ai1[l4];
                }
            }

            int i5 = (i3 + j3) / 2;
            int i6 = (k3 + l3) / 2;
            for(int l6 = t.nt - 1; l6 >= 0; l6--)
            {
                int k7 = 0;
                if(Math.abs(t.zy[l6]) != 90 && Math.abs(t.xy[l6]) != 90 && Math.abs(i5 - (t.x[l6] - m.x)) < t.radx[l6] && Math.abs(i6 - (t.z[l6] - m.z)) < t.radz[l6])
                {
                    k7++;
                }
                if(k7 == 0)
                {
                    continue;
                }
                for(int j8 = 0; j8 < n; j8++)
                {
                    ai2[j8] = t.y[l6] - m.y;
                    if(t.zy[l6] != 0)
                    {
                        ai2[j8] += ((float)(ai1[j8] - (t.z[l6] - m.z - t.radz[l6])) * m.sin(t.zy[l6])) / m.sin(90 - t.zy[l6]) - ((float)t.radz[l6] * m.sin(t.zy[l6])) / m.sin(90 - t.zy[l6]);
                    }
                    if(t.xy[l6] != 0)
                    {
                        ai2[j8] += ((float)(ai[j8] - (t.x[l6] - m.x - t.radx[l6])) * m.sin(t.xy[l6])) / m.sin(90 - t.xy[l6]) - ((float)t.radx[l6] * m.sin(t.xy[l6])) / m.sin(90 - t.xy[l6]);
                    }
                }

                i2 = (int)((double)(float)t.c[l6][0] / 1.5D);
                j2 = (int)((double)(float)t.c[l6][1] / 1.5D);
                k2 = (int)((double)(float)t.c[l6][2] / 1.5D);
                break;
            }

        }
        boolean flag = true;
        int ai3[] = new int[n];
        int ai4[] = new int[n];
        if(k1 == 2)
        {
            i2 = 80;
            j2 = 80;
            k2 = 80;
        } else
        {
            for(int i4 = 0; i4 < m.nsp; i4++)
            {
                for(int j5 = 0; j5 < n; j5++)
                {
                    if(Math.abs(ai[j5] - m.spx[i4]) < m.sprad[i4] && Math.abs(ai1[j5] - m.spz[i4]) < m.sprad[i4])
                    {
                        flag = false;
                    }
                }

            }

        }
        if(flag)
        {
            rot(ai, ai1, m.cx, m.cz, m.xz, n);
            rot(ai2, ai1, m.cy, m.cz, m.zy, n);
            int j4 = 0;
            int k5 = 0;
            int j6 = 0;
            int i7 = 0;
            for(int l7 = 0; l7 < n; l7++)
            {
                ai3[l7] = xs(ai[l7], ai1[l7]);
                ai4[l7] = ys(ai2[l7], ai1[l7]);
                if(ai4[l7] < 0 || ai1[l7] < 10)
                {
                    j4++;
                }
                if(ai4[l7] > m.h || ai1[l7] < 10)
                {
                    k5++;
                }
                if(ai3[l7] < 0 || ai1[l7] < 10)
                {
                    j6++;
                }
                if(ai3[l7] > m.w || ai1[l7] < 10)
                {
                    i7++;
                }
            }

            if(j6 == n || j4 == n || k5 == n || i7 == n)
            {
                flag = false;
            }
        }
        if(flag)
        {
            int k4 = 0;
            do
            {
                if(av > m.fade[k4])
                {
                    i2 = (i2 * m.fogd + m.cfade[0]) / (m.fogd + 1);
                    j2 = (j2 * m.fogd + m.cfade[1]) / (m.fogd + 1);
                    k2 = (k2 * m.fogd + m.cfade[2]) / (m.fogd + 1);
                }
            } while(++k4 < 8);
            rd.setColor(new Color(i2, j2, k2));
            rd.fillPolygon(ai3, ai4, n);
        }
    }

    public int spy(int i, int j)
    {
        return (int)Math.sqrt((i - m.cx) * (i - m.cx) + j * j);
    }
}
