
public class Control
{    
    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean handb;
    int lookback;
    boolean enter;
    boolean arrace;
    boolean mutem;
    boolean mutes;
    Medium m;
    int pan;
    int attack;
    int acr;
    boolean afta;
    int fpnt[];
    int trfix;
    boolean forget;
    boolean bulistc;
    int runbul;
    int acuracy;
    int upwait;
    boolean agressed;
    float skiplev;
    int clrnce;
    int rampp;
    int turntyp;
    float aim;
    int saftey;
    boolean perfection;
    float mustland;
    boolean usebounce;
    float trickprf;
    int stuntf;
    boolean zyinv;
    boolean lastl;
    boolean wlastl;
    int hold;
    int wall;
    int lwall;
    int stcnt;
    int statusque;
    int turncnt;
    int randtcnt;
    int upcnt;
    int trickfase;
    int swat;
    boolean udcomp;
    boolean lrcomp;
    boolean udbare;
    boolean lrbare;
    boolean onceu;
    boolean onced;
    boolean oncel;
    boolean oncer;
    int lrdirect;
    int uddirect;
    int lrstart;
    int udstart;
    int oxy;
    int ozy;
    int flycnt;
    boolean lrswt;
    boolean udswt;
    boolean gowait;
    int actwait;
    int cntrn;
    int revstart;
    int oupnt;
    int wtz;
    int wtx;
    int frx;
    int frz;
    int frad;
    int apunch;
    boolean exitattack;
    int avoidnlev;

    public void preform(Madness madness, ContO conto, CheckPoints checkpoints, Trackers trackers)
    {
        left = false;
        right = false;
        up = false;
        down = false;
        handb = false;
        if(!madness.dest)
        {
            if(madness.mtouch)
            {
                if(stcnt > statusque)
                {
                    acuracy = (7 - checkpoints.pos[madness.im]) * checkpoints.pos[0] * (6 - checkpoints.stage * 2);
                    if(acuracy < 0)
                    {
                        acuracy = 0;
                    }
                    clrnce = 5;
                    if(checkpoints.stage == 6 || checkpoints.stage == 11)
                    {
                        clrnce = 2;
                    }
                    if(checkpoints.stage == 12 && (madness.pcleared == 27 || madness.pcleared == 17))
                    {
                        clrnce = 3;
                    }
                    if(checkpoints.stage == 16 && madness.pcleared == 33)
                    {
                        clrnce = 3;
                    }
                    float f = 0.0F;
                    if(checkpoints.stage == 1)
                    {
                        f = 1.5F;
                    }
                    if(checkpoints.stage == 2)
                    {
                        f = 1.0F;
                    }
                    if(checkpoints.stage == 3 && madness.im != 6)
                    {
                        f = 0.2F;
                    }
                    if(checkpoints.stage == 4)
                    {
                        f = 0.5F;
                    }
                    upwait = (int)((float)((checkpoints.pos[0] - checkpoints.pos[madness.im]) * (checkpoints.pos[0] - checkpoints.pos[madness.im]) * (checkpoints.pos[0] - checkpoints.pos[madness.im])) * f);
                    if(upwait > 80)
                    {
                        upwait = 80;
                    }
                    if(checkpoints.stage == 1 && upwait < 20)
                    {
                        upwait = 20;
                    }
                    f = 0.0F;
                    if(checkpoints.stage == 1 || checkpoints.stage == 2)
                    {
                        f = 1.0F;
                    }
                    if(checkpoints.stage == 3)
                    {
                        f = 0.5F;
                    }
                    if(checkpoints.stage == 4)
                    {
                        f = 0.5F;
                    }
                    if(checkpoints.stage == 5)
                    {
                        f = 0.2F;
                    }
                    if(checkpoints.pos[madness.im] - checkpoints.pos[0] >= -1)
                    {
                        skiplev -= 0.10000000000000001D;
                        if(skiplev < 0.0F)
                        {
                            skiplev = 0.0F;
                        }
                    } else
                    {
                        skiplev += 0.20000000000000001D;
                        if(skiplev > f)
                        {
                            skiplev = f;
                        }
                    }
                    if(checkpoints.stage == 8)
                    {
                        if(madness.pcleared >= 10 && madness.pcleared <= 24)
                        {
                            skiplev = 1.0F;
                        } else
                        {
                            skiplev = 0.0F;
                        }
                    }
                    if(checkpoints.stage == 11)
                    {
                        skiplev = 0.0F;
                        if(madness.pcleared == 5)
                        {
                            skiplev = 1.0F;
                        }
                        if(madness.pcleared == 28 || madness.pcleared == 35)
                        {
                            skiplev = 0.5F;
                        }
                    }
                    if(checkpoints.stage == 13)
                    {
                        skiplev = 0.5F;
                    }
                    if(checkpoints.stage == 14 || checkpoints.stage == 12)
                    {
                        skiplev = 1.0F;
                    }
                    if(checkpoints.stage == 16 || checkpoints.stage == 15 || checkpoints.stage == 10)
                    {
                        skiplev = 0.0F;
                    }
                    rampp = (int)(m.random() * 4F - 2.0F);
                    if(madness.power == 98F)
                    {
                        rampp = -1;
                    }
                    if(madness.power < 75F && rampp == -1)
                    {
                        rampp = 0;
                    }
                    if(madness.power < 60F)
                    {
                        rampp = 1;
                    }
                    if(checkpoints.stage == 8 && madness.pcleared >= 45)
                    {
                        rampp = 2;
                    }
                    if(checkpoints.stage == 12 && madness.pcleared == 17)
                    {
                        rampp = 2;
                    }
                    if(checkpoints.stage == 15 || checkpoints.stage == 16)
                    {
                        rampp = 0;
                    }
                    if(cntrn == 0)
                    {
                        agressed = false;
                        turntyp = (int)(m.random() * 4F);
                        if(checkpoints.stage == 3 && madness.im == 6)
                        {
                            turntyp = 1;
                            if(attack == 0)
                            {
                                agressed = true;
                            }
                        }
                        if(checkpoints.pos[0] - checkpoints.pos[madness.im] < 0)
                        {
                            turntyp = (int)(m.random() * 2.0F);
                        }
                        if(checkpoints.stage == 8)
                        {
                            turntyp = 2;
                        }
                        if(checkpoints.stage == 10)
                        {
                            turntyp = 0;
                        }
                        if(checkpoints.stage == 13)
                        {
                            turntyp = 1;
                        }
                        if(checkpoints.stage == 14)
                        {
                            turntyp = 0;
                        }
                        if(attack != 0)
                        {
                            turntyp = 2;
                            if(checkpoints.stage == 9 || checkpoints.stage == 11 || checkpoints.stage == 13 || checkpoints.stage == 17)
                            {
                                turntyp = (int)(m.random() * 3F);
                            }
                            if(checkpoints.stage == 16 && checkpoints.clear[madness.im] - checkpoints.clear[0] >= 5)
                            {
                                turntyp = 0;
                            }
                        }
                        if(checkpoints.stage == 6 || checkpoints.stage == 7 || checkpoints.stage == 9 || checkpoints.stage == 10 || checkpoints.stage == 11 || checkpoints.stage == 12 || checkpoints.stage == 14 || checkpoints.stage == 16 || checkpoints.stage == 17)
                        {
                            agressed = true;
                        }
                        cntrn = 5;
                    } else
                    {
                        cntrn--;
                    }
                    saftey = (int)((double)((98F - madness.power) / 2.0F) * ((double)(m.random() / 2.0F) + 0.5D));
                    if(saftey > 20)
                    {
                        saftey = 20;
                    }
                    f = 0.0F;
                    if(checkpoints.stage == 1)
                    {
                        f = 0.9F;
                    }
                    if(checkpoints.stage == 2)
                    {
                        f = 0.7F;
                    }
                    if(checkpoints.stage == 4)
                    {
                        f = 0.4F;
                    }
                    mustland = f + (float)((double)(m.random() / 2.0F) - 0.25D);
                    f = 1.0F;
                    if(checkpoints.stage == 1)
                    {
                        f = 5F;
                    }
                    if(checkpoints.stage == 2)
                    {
                        f = 2.0F;
                    }
                    if(checkpoints.stage == 4)
                    {
                        f = 1.5F;
                    }
                    if(madness.power > 50F)
                    {
                        if(checkpoints.pos[0] - checkpoints.pos[madness.im] > 0)
                        {
                            saftey *= f;
                        } else
                        {
                            mustland = 0.0F;
                        }
                    } else
                    {
                        mustland -= 0.5F;
                    }
                    if(checkpoints.stage == 8 || checkpoints.stage == 10 || checkpoints.stage == 12 || checkpoints.stage == 14)
                    {
                        mustland = 0.0F;
                    }
                    stuntf = 0;
                    if(checkpoints.stage == 8 && madness.pcleared == 57)
                    {
                        stuntf = 1;
                    }
                    if(checkpoints.stage == 9 && madness.pcleared == 3)
                    {
                        stuntf = 2;
                    }
                    if(checkpoints.stage == 10)
                    {
                        if(checkpoints.pos[0] < checkpoints.pos[madness.im] || Math.abs(checkpoints.clear[0] - madness.clear) >= 2 || madness.clear < 2)
                        {
                            stuntf = 4;
                            saftey = 10;
                        } else
                        {
                            stuntf = 3;
                        }
                    }
                    if(checkpoints.stage == 11 && madness.pcleared == 21)
                    {
                        stuntf = 1;
                    }
                    if(checkpoints.stage == 14)
                    {
                        saftey = 10;
                        if(madness.pcleared >= 4 && madness.pcleared < 70)
                        {
                            stuntf = 4;
                        } else
                        if(madness.cn == 12 || madness.cn == 8)
                        {
                            stuntf = 2;
                        }
                        if(madness.cn == 14)
                        {
                            stuntf = 6;
                        }
                    }
                    if(checkpoints.stage == 16)
                    {
                        mustland = 0.0F;
                        saftey = 10;
                        if((madness.pcleared == 15 || madness.pcleared == 51) && ((double)m.random() > 0.40000000000000002D || trfix != 0))
                        {
                            stuntf = 7;
                        }
                        if(madness.pcleared == 42)
                        {
                            stuntf = 1;
                        }
                        if(madness.pcleared == 77)
                        {
                            stuntf = 7;
                        }
                        avoidnlev = (int)(2700F * m.random());
                    }
                    trickprf = (madness.power - 38F) / 50F - m.random() / 2.0F;
                    if(madness.power < 60F)
                    {
                        trickprf = -1F;
                    }
                    if(checkpoints.stage == 3 && madness.im == 6 && (double)trickprf > 0.69999999999999996D)
                    {
                        trickprf = 0.7F;
                    }
                    if(checkpoints.stage == 6 && (double)trickprf > 0.29999999999999999D)
                    {
                        trickprf = 0.3F;
                    }
                    if(checkpoints.stage == 8 && (double)trickprf > 0.20000000000000001D)
                    {
                        trickprf = 0.2F;
                    }
                    if(checkpoints.stage == 9)
                    {
                        if((double)trickprf > 0.5D)
                        {
                            trickprf = 0.5F;
                        }
                        if((madness.im == 6 || madness.im == 5) && (double)trickprf > 0.29999999999999999D)
                        {
                            trickprf = 0.3F;
                        }
                    }
                    if(checkpoints.stage == 11 && trickprf != -1F)
                    {
                        trickprf *= 0.75F;
                    }
                    if(checkpoints.stage == 12 && (madness.pcleared == 55 || madness.pcleared == 7))
                    {
                        trickprf = -1F;
                        stuntf = 5;
                    }
                    if(checkpoints.stage == 13 && (double)trickprf > 0.40000000000000002D)
                    {
                        trickprf = 0.4F;
                    }
                    if(checkpoints.stage == 14 && (double)trickprf > 0.5D)
                    {
                        trickprf = 0.5F;
                    }
                    if(checkpoints.stage == 17)
                    {
                        trickprf = -1F;
                    }
                    if(m.random() > madness.power / 100F)
                    {
                        usebounce = true;
                    } else
                    {
                        usebounce = false;
                    }
                    if(checkpoints.stage == 4 || checkpoints.stage == 6)
                    {
                        usebounce = true;
                    }
                    if(checkpoints.stage == 10 || checkpoints.stage == 14)
                    {
                        usebounce = false;
                    }
                    if(m.random() > (float)madness.hitmag / (float)madness.maxmag[madness.cn])
                    {
                        perfection = false;
                    } else
                    {
                        perfection = true;
                    }
                    if((100F * (float)madness.hitmag) / (float)madness.maxmag[madness.cn] > 60F)
                    {
                        perfection = true;
                    }
                    if(checkpoints.stage == 6 || checkpoints.stage == 8 || checkpoints.stage == 9 || checkpoints.stage == 10 || checkpoints.stage == 11 || checkpoints.stage == 12 || checkpoints.stage == 14 || checkpoints.stage == 16 || checkpoints.stage == 17)
                    {
                        perfection = true;
                    }
                    if(attack == 0)
                    {
                        boolean flag1 = true;
                        if(checkpoints.stage == 3 || checkpoints.stage == 1 || checkpoints.stage == 4 || checkpoints.stage == 9 || checkpoints.stage == 13 || checkpoints.stage == 16)
                        {
                            flag1 = afta;
                        }
                        if(checkpoints.stage == 8 || checkpoints.stage == 6 || checkpoints.stage == 10 || checkpoints.stage == 14)
                        {
                            flag1 = false;
                        }
                        boolean flag3 = false;
                        if(checkpoints.stage == 3 && madness.cn == 9)
                        {
                            flag3 = true;
                        }
                        if(checkpoints.stage == 8 && madness.cn == 11)
                        {
                            flag3 = true;
                        }
                        if(checkpoints.stage == 9 && checkpoints.clear[0] >= 20)
                        {
                            flag3 = true;
                        }
                        if(checkpoints.stage == 11 || checkpoints.stage == 12 || checkpoints.stage == 13 || checkpoints.stage == 15 || checkpoints.stage == 16)
                        {
                            flag3 = true;
                        }
                        int j2 = 60;
                        if(checkpoints.stage == 3 || checkpoints.stage == 11 || checkpoints.stage == 17 || checkpoints.stage == 10 || checkpoints.stage == 8)
                        {
                            j2 = 30;
                        }
                        if((checkpoints.stage == 2 || checkpoints.stage == 13) && madness.cn == 13)
                        {
                            j2 = 50;
                        }
                        if(checkpoints.stage == 4)
                        {
                            j2 = 20;
                        }
                        if(checkpoints.stage == 5 && madness.im != 6)
                        {
                            j2 = 40;
                        }
                        if(checkpoints.stage == 7)
                        {
                            j2 = 40;
                        }
                        if(checkpoints.stage == 8 && madness.cn == 11)
                        {
                            j2 = 40;
                        }
                        if(checkpoints.stage == 9 && flag3)
                        {
                            j2 = 30;
                        }
                        if(checkpoints.stage == 11 && bulistc)
                        {
                            j2 = 30;
                        }
                        if(checkpoints.stage == 12)
                        {
                            j2 = 50;
                        }
                        if(checkpoints.stage == 15 && bulistc)
                        {
                            j2 = 40;
                        }
                        if(checkpoints.stage == 16)
                        {
                            if(madness.cn == 11 && checkpoints.clear[0] == 27)
                            {
                                j2 = 0;
                            }
                            if(madness.cn == 15 || madness.cn == 9)
                            {
                                j2 = 50;
                            }
                            if(madness.cn == 11)
                            {
                                j2 = 40;
                            }
                            if(checkpoints.pos[0] > checkpoints.pos[madness.im])
                            {
                                j2 = 80;
                            }
                        }
                        int i4 = 0;
                        do
                        {
                            if(i4 != madness.im && checkpoints.clear[i4] != -1)
                            {
                                int l5 = conto.xz;
                                if(zyinv)
                                {
                                    l5 += 180;
                                }
                                for(; l5 < 0; l5 += 360) { }
                                for(; l5 > 180; l5 -= 360) { }
                                char c4 = '\0';
                                if(checkpoints.opx[i4] - conto.x >= 0)
                                {
                                    c4 = '\264';
                                }
                                int i8;
                                for(i8 = (int)((double)(90 + c4) + Math.atan((double)(checkpoints.opz[i4] - conto.z) / (double)(checkpoints.opx[i4] - conto.x)) / 0.017453292519943295D); i8 < 0; i8 += 360) { }
                                for(; i8 > 180; i8 -= 360) { }
                                int k8 = Math.abs(l5 - i8);
                                if(k8 > 180)
                                {
                                    k8 = Math.abs(k8 - 360);
                                }
                                int l8 = 2000 * (Math.abs(checkpoints.clear[i4] - madness.clear) + 1);
                                if(checkpoints.stage == 3 && madness.cn == 9 && l8 < 12000)
                                {
                                    l8 = 12000;
                                }
                                if(checkpoints.stage == 4 && l8 < 4000)
                                {
                                    l8 = 4000;
                                }
                                if(checkpoints.stage == 8 && madness.cn == 11)
                                {
                                    if(l8 < 12000)
                                    {
                                        l8 = 12000;
                                    }
                                    k8 = 10;
                                }
                                if(checkpoints.stage == 9 && (madness.pcleared == 13 || madness.pcleared == 33 || flag3) && l8 < 12000)
                                {
                                    l8 = 12000;
                                }
                                if(checkpoints.stage == 11)
                                {
                                    if(bulistc)
                                    {
                                        l8 = 8000;
                                        k8 = 10;
                                        afta = true;
                                    } else
                                    if(l8 < 6000)
                                    {
                                        l8 = 6000;
                                    }
                                }
                                if(checkpoints.stage == 12 && bulistc)
                                {
                                    l8 = 6000;
                                    k8 = 10;
                                }
                                if(checkpoints.stage == 13)
                                {
                                    l8 = 21000;
                                }
                                if(checkpoints.stage == 15)
                                {
                                    l8 *= Math.abs(checkpoints.clear[i4] - madness.clear) + 1;
                                    if(bulistc)
                                    {
                                        l8 = 4000 * (Math.abs(checkpoints.clear[i4] - madness.clear) + 1);
                                        k8 = 10;
                                    }
                                }
                                if(checkpoints.stage == 10)
                                {
                                    l8 = 16000;
                                }
                                if(checkpoints.stage == 16)
                                {
                                    if(madness.cn == 13 && bulistc)
                                    {
                                        if(oupnt == 33)
                                        {
                                            l8 = 17000;
                                        }
                                        if(oupnt == 51)
                                        {
                                            l8 = 30000;
                                        }
                                        if(oupnt == 15 && checkpoints.clear[0] >= 14)
                                        {
                                            l8 = 60000;
                                        }
                                        k8 = 10;
                                    }
                                    if(madness.cn == 15 || madness.cn == 9)
                                    {
                                        l8 *= Math.abs(checkpoints.clear[i4] - madness.clear) + 1;
                                    }
                                    if(madness.cn == 11)
                                    {
                                        l8 = 4000 * (Math.abs(checkpoints.clear[i4] - madness.clear) + 1);
                                    }
                                }
                                int i9 = 85 + 15 * (Math.abs(checkpoints.clear[i4] - madness.clear) + 1);
                                if(checkpoints.stage == 13)
                                {
                                    i9 = 45;
                                }
                                if(checkpoints.stage == 16 && (madness.cn == 15 || madness.cn == 9 || madness.cn == 11 || madness.cn == 14))
                                {
                                    i9 = 50 + 70 * Math.abs(checkpoints.clear[i4] - madness.clear);
                                }
                                if(k8 < i9 && py(conto.x / 100, checkpoints.opx[i4] / 100, conto.z / 100, checkpoints.opz[i4] / 100) < l8 && afta && madness.power > (float)j2)
                                {
                                    float f1 = 35 - Math.abs(checkpoints.clear[i4] - madness.clear) * 10;
                                    if(f1 < 1.0F)
                                    {
                                        f1 = 1.0F;
                                    }
                                    float f2 = (float)((checkpoints.pos[madness.im] + 1) * (5 - checkpoints.pos[i4])) / f1;
                                    if(checkpoints.stage != 17 && (double)f2 > 0.69999999999999996D)
                                    {
                                        f2 = 0.7F;
                                    }
                                    if(i4 != 0 && checkpoints.pos[0] < checkpoints.pos[madness.im])
                                    {
                                        f2 = 0.0F;
                                    }
                                    if(i4 != 0 && flag3)
                                    {
                                        f2 = 0.0F;
                                    }
                                    if(checkpoints.stage == 3)
                                    {
                                        if(madness.cn == 9 || madness.cn == 13 && bulistc)
                                        {
                                            f2 *= 2.0F;
                                        } else
                                        {
                                            f2 *= 0.5F;
                                        }
                                    }
                                    if(checkpoints.stage == 6)
                                    {
                                        f2 = 0.0F;
                                    }
                                    if(checkpoints.stage == 7 && madness.im == 6 && i4 == 0)
                                    {
                                        f2 = (float)((double)f2 * 1.5D);
                                    }
                                    if(checkpoints.stage == 8)
                                    {
                                        if(madness.cn == 11 || madness.cn == 13 && bulistc)
                                        {
                                            f2 *= 1.5F;
                                        } else
                                        {
                                            f2 = 0.0F;
                                        }
                                    }
                                    if(checkpoints.stage == 9)
                                    {
                                        if(i4 != 0)
                                        {
                                            f2 = (float)((double)f2 * 0.5D);
                                        }
                                        if(madness.pcleared != 13 && madness.pcleared != 33 && !flag3)
                                        {
                                            f2 *= 0.5F;
                                        }
                                        if((madness.im == 6 || madness.im == 5) && i4 != 0)
                                        {
                                            f2 = 0.0F;
                                        }
                                    }
                                    if(checkpoints.stage == 10 && !bulistc)
                                    {
                                        f2 = 0.0F;
                                    }
                                    if(checkpoints.stage == 11 && bulistc && i4 == 0)
                                    {
                                        f2 = 1.0F;
                                    }
                                    if(checkpoints.stage == 12)
                                    {
                                        if(madness.cn != 11 && madness.cn != 13)
                                        {
                                            f2 = 0.0F;
                                        }
                                        if(madness.cn == 13 && i4 == 0)
                                        {
                                            f2 = 1.0F;
                                        }
                                    }
                                    if(checkpoints.stage == 14)
                                    {
                                        f2 = 0.0F;
                                    }
                                    if(checkpoints.stage == 15)
                                    {
                                        if(checkpoints.pos[madness.im] == 0)
                                        {
                                            f2 = (float)((double)f2 * 0.5D);
                                        }
                                        if(checkpoints.pos[0] < checkpoints.pos[madness.im])
                                        {
                                            f2 *= 2.0F;
                                        }
                                        if(bulistc && i4 == 0)
                                        {
                                            f2 = 1.0F;
                                        }
                                    }
                                    if(checkpoints.stage == 16)
                                    {
                                        if(madness.cn != 14)
                                        {
                                            if(checkpoints.pos[0] < checkpoints.pos[madness.im] && checkpoints.clear[0] - checkpoints.clear[madness.im] != 1)
                                            {
                                                f2 *= 2.0F;
                                            }
                                        } else
                                        {
                                            f2 = (float)((double)f2 * 0.5D);
                                        }
                                        if(madness.cn == 13 && i4 == 0)
                                        {
                                            f2 = 1.0F;
                                        }
                                        if(checkpoints.pos[madness.im] == 0 || checkpoints.pos[madness.im] == 1 && checkpoints.pos[0] == 0)
                                        {
                                            f2 = 0.0F;
                                        }
                                        if(checkpoints.clear[madness.im] - checkpoints.clear[0] >= 5 && i4 == 0)
                                        {
                                            f2 = 1.0F;
                                        }
                                        if(madness.cn == 10 || madness.cn == 12)
                                        {
                                            f2 = 0.0F;
                                        }
                                    }
                                    if(m.random() < f2)
                                    {
                                        attack = 40 * (Math.abs(checkpoints.clear[i4] - madness.clear) + 1);
                                        if(attack > 500)
                                        {
                                            attack = 500;
                                        }
                                        aim = 0.0F;
                                        if(checkpoints.stage == 3 && madness.im == 6 && m.random() > m.random())
                                        {
                                            aim = 1.0F;
                                        }
                                        if(checkpoints.stage == 4)
                                        {
                                            if(i4 == 0 && checkpoints.pos[0] < checkpoints.pos[madness.im])
                                            {
                                                aim = 1.5F;
                                            } else
                                            {
                                                aim = m.random();
                                            }
                                        }
                                        if(checkpoints.stage == 5)
                                        {
                                            aim = m.random() * 1.5F;
                                        }
                                        if(checkpoints.stage == 7 && madness.im != 6 && (m.random() > m.random() || checkpoints.pos[0] < checkpoints.pos[madness.im]))
                                        {
                                            aim = 1.0F;
                                        }
                                        if(checkpoints.stage == 8 && madness.cn == 11 && m.random() > m.random())
                                        {
                                            aim = 0.76F + m.random() * 0.76F;
                                        }
                                        if(checkpoints.stage == 9 && (madness.pcleared == 13 || madness.pcleared == 33))
                                        {
                                            aim = 1.0F;
                                        }
                                        if(checkpoints.stage == 11)
                                        {
                                            if(bulistc)
                                            {
                                                aim = 0.7F;
                                                if(attack > 150)
                                                {
                                                    attack = 150;
                                                }
                                            } else
                                            {
                                                aim = m.random();
                                            }
                                        }
                                        if(checkpoints.stage == 12)
                                        {
                                            if(m.random() > m.random())
                                            {
                                                aim = 0.7F;
                                            }
                                            if(bulistc && attack > 150)
                                            {
                                                attack = 150;
                                            }
                                        }
                                        if(checkpoints.stage == 13 && attack > 60)
                                        {
                                            attack = 60;
                                        }
                                        if(checkpoints.stage == 15)
                                        {
                                            aim = m.random() * 1.5F;
                                            attack = attack / 2;
                                            if(m.random() > m.random())
                                            {
                                                exitattack = true;
                                            } else
                                            {
                                                exitattack = false;
                                            }
                                        }
                                        if(checkpoints.stage == 16)
                                        {
                                            if(madness.cn == 13)
                                            {
                                                aim = 0.76F;
                                                attack = 150;
                                            } else
                                            {
                                                aim = m.random() * 1.5F;
                                                if(Math.abs(checkpoints.clear[i4] - madness.clear) <= 2 || madness.cn == 14)
                                                {
                                                    attack = attack / 3;
                                                }
                                            }
                                        }
                                        acr = i4;
                                        turntyp = (int)(1.0F + m.random() * 2.0F);
                                    }
                                }
                                if(flag1 && k8 > 100 && py(conto.x / 100, checkpoints.opx[i4] / 100, conto.z / 100, checkpoints.opz[i4] / 100) < 300 && (double)m.random() > 0.59999999999999998D - (double)((float)checkpoints.pos[madness.im] / 10F))
                                {
                                    clrnce = 0;
                                    acuracy = 0;
                                }
                            }
                        } while(++i4 < 7);
                    }
                    boolean flag2 = false;
                    if(checkpoints.stage == 6 || checkpoints.stage == 10 || checkpoints.stage == 11 || checkpoints.stage == 17)
                    {
                        flag2 = true;
                    }
                    if(checkpoints.stage == 8 && madness.pcleared != 73)
                    {
                        flag2 = true;
                    }
                    if(trfix != 3)
                    {
                        trfix = 0;
                        int j1 = 50;
                        if(checkpoints.stage == 16)
                        {
                            j1 = 40;
                        }
                        if((100F * (float)madness.hitmag) / (float)madness.maxmag[madness.cn] > (float)j1)
                        {
                            trfix = 1;
                        }
                        if(!flag2)
                        {
                            int k2 = 80;
                            if(checkpoints.stage == 8 && madness.cn != 11)
                            {
                                k2 = 50;
                            }
                            if(checkpoints.stage == 9)
                            {
                                k2 = 70;
                            }
                            if(checkpoints.stage == 15 && madness.pcleared == 91)
                            {
                                k2 = 50;
                            }
                            if(checkpoints.stage == 16 && checkpoints.clear[madness.im] - checkpoints.clear[0] >= 5 && madness.cn != 10 && madness.cn != 12)
                            {
                                k2 = 50;
                            }
                            if((100F * (float)madness.hitmag) / (float)madness.maxmag[madness.cn] > (float)k2)
                            {
                                trfix = 2;
                            }
                        }
                    } else
                    {
                        upwait = 0;
                        acuracy = 0;
                        skiplev = 1.0F;
                        clrnce = 2;
                    }
                    if(!bulistc)
                    {
                        if(checkpoints.stage == 8 && madness.cn == 11 && madness.pcleared == 35)
                        {
                            madness.pcleared = 73;
                            madness.clear = 0;
                            bulistc = true;
                            runbul = (int)(100F * m.random());
                        }
                        if(checkpoints.stage == 11 && madness.cn == 13)
                        {
                            bulistc = true;
                        }
                        if(checkpoints.stage == 12 && madness.cn == 13)
                        {
                            bulistc = true;
                        }
                        if(checkpoints.stage == 15 && checkpoints.clear[0] - madness.clear >= 3 && trfix == 0)
                        {
                            bulistc = true;
                            oupnt = -1;
                        }
                        if(checkpoints.stage == 16)
                        {
                            if(madness.cn == 13 && checkpoints.pcleared == 8)
                            {
                                bulistc = true;
                                attack = 0;
                            }
                            if(madness.cn == 11 && checkpoints.clear[0] - madness.clear >= 2 && trfix == 0)
                            {
                                bulistc = true;
                                oupnt = -1;
                            }
                        }
                        if((checkpoints.stage == 2 || checkpoints.stage == 3 || checkpoints.stage == 4 || checkpoints.stage == 5 || checkpoints.stage == 8 || checkpoints.stage == 10 || checkpoints.stage == 13) && madness.cn == 13 && Math.abs(checkpoints.clear[0] - madness.clear) >= 2)
                        {
                            bulistc = true;
                        }
                    } else
                    if(checkpoints.stage == 8)
                    {
                        runbul--;
                        if(madness.pcleared == 10)
                        {
                            runbul = 0;
                        }
                        if(runbul <= 0)
                        {
                            bulistc = false;
                        }
                    }
                    stcnt = 0;
                    statusque = (int)(20F * m.random());
                } else
                {
                    stcnt++;
                }
            }
            boolean flag = false;
            if(usebounce)
            {
                flag = madness.wtouch;
            } else
            {
                flag = madness.mtouch;
            }
            if(flag)
            {
                if(trickfase != 0)
                {
                    trickfase = 0;
                }
                if(trfix == 2 || trfix == 3)
                {
                    attack = 0;
                }
                if(attack == 0)
                {
                    if(upcnt < 30)
                    {
                        if(revstart <= 0)
                        {
                            up = true;
                        } else
                        {
                            down = true;
                            revstart--;
                        }
                    }
                    if(upcnt < 25 + actwait)
                    {
                        upcnt++;
                    } else
                    {
                        upcnt = 0;
                        actwait = upwait;
                    }
                    int i = madness.point;
                    int k1 = 50;
                    if(checkpoints.stage == 8)
                    {
                        k1 = 20;
                    }
                    if(checkpoints.stage == 15)
                    {
                        k1 = 40;
                    }
                    if(checkpoints.stage == 16)
                    {
                        k1 = 20;
                    }
                    if(!bulistc || trfix == 2 || trfix == 3 || trfix == 4 || madness.power < (float)k1)
                    {
                        if(rampp == 1 && checkpoints.typ[i] <= 0)
                        {
                            int l2 = i + 1;
                            if(l2 == checkpoints.n)
                            {
                                l2 = 0;
                            }
                            if(checkpoints.typ[l2] == -2)
                            {
                                i = l2;
                            }
                        }
                        if(rampp == -1 && checkpoints.typ[i] == -2 && ++i == checkpoints.n)
                        {
                            i = 0;
                        }
                        if(m.random() > skiplev)
                        {
                            int i3 = i;
                            boolean flag5 = false;
                            if(checkpoints.typ[i3] > 0)
                            {
                                int i6 = 0;
                                for(int i7 = 0; i7 < checkpoints.n; i7++)
                                {
                                    if(checkpoints.typ[i7] > 0 && i7 < i3)
                                    {
                                        i6++;
                                    }
                                }

                                flag5 = madness.clear != i6 + madness.nlaps * checkpoints.nsp;
                            }
                            while(checkpoints.typ[i3] == 0 || checkpoints.typ[i3] == -1 || checkpoints.typ[i3] == -3 || flag5) 
                            {
                                i = i3;
                                if(++i3 == checkpoints.n)
                                {
                                    i3 = 0;
                                }
                                flag5 = false;
                                if(checkpoints.typ[i3] > 0)
                                {
                                    int j6 = 0;
                                    for(int j7 = 0; j7 < checkpoints.n; j7++)
                                    {
                                        if(checkpoints.typ[j7] > 0 && j7 < i3)
                                        {
                                            j6++;
                                        }
                                    }

                                    flag5 = madness.clear != j6 + madness.nlaps * checkpoints.nsp;
                                }
                            }
                        } else
                        if(m.random() > skiplev)
                        {
                            while(checkpoints.typ[i] == -1) 
                            {
                                if(++i == checkpoints.n)
                                {
                                    i = 0;
                                }
                            }
                        }
                        if(checkpoints.stage == 8 && madness.pcleared == 73 && trfix == 0 && madness.clear != 0)
                        {
                            i = 10;
                        }
                        if(checkpoints.stage == 9 && madness.pcleared == 18 && trfix == 0)
                        {
                            i = 27;
                        }
                        if(checkpoints.stage == 11)
                        {
                            if(madness.pcleared == 5 && trfix == 0 && madness.power < 70F)
                            {
                                if(i <= 16)
                                {
                                    i = 16;
                                } else
                                {
                                    i = 21;
                                }
                            }
                            if(madness.pcleared == 50)
                            {
                                i = 57;
                            }
                        }
                        if(checkpoints.stage == 12 && (madness.pcleared == 27 || madness.pcleared == 37))
                        {
                            while(checkpoints.typ[i] == -1) 
                            {
                                if(++i == checkpoints.n)
                                {
                                    i = 0;
                                }
                            }
                        }
                        if(checkpoints.stage == 13)
                        {
                            while(checkpoints.typ[i] == -1) 
                            {
                                if(++i == checkpoints.n)
                                {
                                    i = 0;
                                }
                            }
                        }
                        if(checkpoints.stage == 14)
                        {
                            while(checkpoints.typ[i] == -1) 
                            {
                                if(++i == checkpoints.n)
                                {
                                    i = 0;
                                }
                            }
                            if(!madness.gtouch)
                            {
                                while(checkpoints.typ[i] == -2) 
                                {
                                    if(++i == checkpoints.n)
                                    {
                                        i = 0;
                                    }
                                }
                            }
                            if(oupnt >= 68)
                            {
                                i = 70;
                            } else
                            {
                                oupnt = i;
                            }
                        }
                        if(checkpoints.stage == 15)
                        {
                            if(madness.pcleared != 91 && checkpoints.pos[0] < checkpoints.pos[madness.im] && madness.cn != 13 || checkpoints.pos[madness.im] == 0 && (madness.clear == 12 || madness.clear == 20))
                            {
                                while(checkpoints.typ[i] == -4) 
                                {
                                    if(++i == checkpoints.n)
                                    {
                                        i = 0;
                                    }
                                }
                            }
                            if(madness.pcleared == 9)
                            {
                                if(py(conto.x / 100, 297, conto.z / 100, 347) < 400)
                                {
                                    oupnt = 1;
                                }
                                if(oupnt == 1 && i < 22)
                                {
                                    i = 22;
                                }
                            }
                            if(madness.pcleared == 67)
                            {
                                if(py(conto.x / 100, 28, conto.z / 100, 494) < 4000)
                                {
                                    oupnt = 2;
                                }
                                if(oupnt == 2)
                                {
                                    i = 76;
                                }
                            }
                            if(madness.pcleared == 76)
                            {
                                if(py(conto.x / 100, -50, conto.z / 100, 0) < 2000)
                                {
                                    oupnt = 3;
                                }
                                if(oupnt == 3)
                                {
                                    i = 91;
                                } else
                                {
                                    i = 89;
                                }
                            }
                        }
                        if(checkpoints.stage == 16)
                        {
                            if(madness.pcleared == 128)
                            {
                                if(py(conto.x / 100, 0, conto.z / 100, 229) < 1500 || conto.z > 23000)
                                {
                                    oupnt = 128;
                                }
                                if(oupnt != 128)
                                {
                                    i = 3;
                                }
                            }
                            if(madness.pcleared == 8)
                            {
                                if(py(conto.x / 100, -207, conto.z / 100, 549) < 1500 || conto.x < -20700)
                                {
                                    oupnt = 8;
                                }
                                if(oupnt != 8)
                                {
                                    i = 12;
                                }
                            }
                            if(madness.pcleared == 33)
                            {
                                if(py(conto.x / 100, -60, conto.z / 100, 168) < 250 || conto.z > 17000)
                                {
                                    oupnt = 331;
                                }
                                if(py(conto.x / 100, -112, conto.z / 100, 414) < 10000 || conto.z > 40000)
                                {
                                    oupnt = 332;
                                }
                                if(oupnt != 331 && oupnt != 332)
                                {
                                    if(trfix != 1)
                                    {
                                        i = 38;
                                    } else
                                    {
                                        i = 39;
                                    }
                                }
                                if(oupnt == 331)
                                {
                                    i = 71;
                                }
                            }
                            if(madness.pcleared == 42)
                            {
                                if(py(conto.x / 100, -269, conto.z / 100, 493) < 100 || conto.x < -27000)
                                {
                                    oupnt = 142;
                                }
                                if(oupnt != 142)
                                {
                                    i = 47;
                                }
                            }
                            if(madness.pcleared == 51)
                            {
                                if(py(conto.x / 100, -352, conto.z / 100, 260) < 100 || conto.z < 25000)
                                {
                                    oupnt = 511;
                                }
                                if(py(conto.x / 100, -325, conto.z / 100, 10) < 2000 || conto.x > -32000)
                                {
                                    oupnt = 512;
                                }
                                if(oupnt != 511 && oupnt != 512)
                                {
                                    i = 80;
                                }
                                if(oupnt == 511)
                                {
                                    i = 61;
                                }
                            }
                            if(madness.pcleared == 77)
                            {
                                if(py(conto.x / 100, -371, conto.z / 100, 319) < 100 || conto.z < 31000)
                                {
                                    oupnt = 77;
                                }
                                if(oupnt != 77)
                                {
                                    i = 78;
                                    madness.nofocus = true;
                                }
                            }
                            if(madness.pcleared == 105)
                            {
                                if(py(conto.x / 100, -179, conto.z / 100, 10) < 2300 || conto.z < 1050)
                                {
                                    oupnt = 105;
                                }
                                if(oupnt != 105)
                                {
                                    i = 65;
                                } else
                                {
                                    i = 125;
                                }
                            }
                            if(trfix == 3)
                            {
                                if(py(conto.x / 100, -52, conto.z / 100, 448) < 100 || conto.z > 45000)
                                {
                                    oupnt = 176;
                                }
                                if(oupnt != 176)
                                {
                                    i = 41;
                                } else
                                {
                                    i = 43;
                                }
                            }
                            if(checkpoints.clear[madness.im] - checkpoints.clear[0] >= 2 && py(conto.x / 100, checkpoints.opx[0] / 100, conto.z / 100, checkpoints.opz[0] / 100) < 1000 + avoidnlev)
                            {
                                int j4 = conto.xz;
                                if(zyinv)
                                {
                                    j4 += 180;
                                }
                                for(; j4 < 0; j4 += 360) { }
                                for(; j4 > 180; j4 -= 360) { }
                                char c3 = '\0';
                                if(checkpoints.opx[0] - conto.x >= 0)
                                {
                                    c3 = '\264';
                                }
                                int k7;
                                for(k7 = (int)((double)(90 + c3) + Math.atan((double)(checkpoints.opz[0] - conto.z) / (double)(checkpoints.opx[0] - conto.x)) / 0.017453292519943295D); k7 < 0; k7 += 360) { }
                                for(; k7 > 180; k7 -= 360) { }
                                int j8 = Math.abs(j4 - k7);
                                if(j8 > 180)
                                {
                                    j8 = Math.abs(j8 - 360);
                                }
                                if(j8 < 90)
                                {
                                    wall = 0;
                                }
                            }
                        }
                        if(rampp == 2)
                        {
                            int j3 = i + 1;
                            if(j3 == checkpoints.n)
                            {
                                j3 = 0;
                            }
                            if(checkpoints.typ[j3] == -2 && i != madness.point && --i < 0)
                            {
                                i += checkpoints.n;
                            }
                        }
                        if(bulistc)
                        {
                            madness.nofocus = true;
                            if(gowait)
                            {
                                gowait = false;
                            }
                        }
                    } else
                    {
                        if(checkpoints.stage != 15 && checkpoints.stage != 16 || runbul == 0)
                        {
                            if((i -= 2) < 0)
                            {
                                i += checkpoints.n;
                            }
                            while(checkpoints.typ[i] == -4) 
                            {
                                if(--i < 0)
                                {
                                    i += checkpoints.n;
                                }
                            }
                        }
                        if(checkpoints.stage == 11)
                        {
                            if(i >= 14 && i <= 19)
                            {
                                i = 13;
                            }
                            if(oupnt == 72 && i != 56)
                            {
                                i = 57;
                            } else
                            if(oupnt == 54 && i != 52)
                            {
                                i = 53;
                            } else
                            if(oupnt == 39 && i != 37)
                            {
                                i = 38;
                            } else
                            {
                                oupnt = i;
                            }
                        }
                        if(checkpoints.stage == 12)
                        {
                            if(!gowait)
                            {
                                if(checkpoints.clear[0] == 0)
                                {
                                    wtx = -3500;
                                    wtz = 19000;
                                    frx = -3500;
                                    frz = 39000;
                                    frad = 12000;
                                    oupnt = 37;
                                    gowait = true;
                                    afta = false;
                                }
                                if(checkpoints.clear[0] == 7)
                                {
                                    wtx = -44800;
                                    wtz = 40320;
                                    frx = -44800;
                                    frz = 34720;
                                    frad = 30000;
                                    oupnt = 27;
                                    gowait = true;
                                    afta = false;
                                }
                                if(checkpoints.clear[0] == 10)
                                {
                                    wtx = 0;
                                    wtz = 48739;
                                    frx = 0;
                                    frz = 38589;
                                    frad = 0x15f90;
                                    oupnt = 55;
                                    gowait = true;
                                    afta = false;
                                }
                                if(checkpoints.clear[0] == 14)
                                {
                                    wtx = -3500;
                                    wtz = 19000;
                                    frx = -14700;
                                    frz = 39000;
                                    frad = 45000;
                                    oupnt = 37;
                                    gowait = true;
                                    afta = false;
                                }
                                if(checkpoints.clear[0] == 18)
                                {
                                    wtx = -48300;
                                    wtz = -4550;
                                    frx = -48300;
                                    frz = 5600;
                                    frad = 0x15f90;
                                    oupnt = 17;
                                    gowait = true;
                                    afta = false;
                                }
                            }
                            if(gowait)
                            {
                                if(py(conto.x / 100, wtx / 100, conto.z / 100, wtz / 100) < 10000 && madness.speed > 50F)
                                {
                                    up = false;
                                }
                                if(py(conto.x / 100, wtx / 100, conto.z / 100, wtz / 100) < 200)
                                {
                                    up = false;
                                    handb = true;
                                }
                                if(checkpoints.pcleared == oupnt && py(checkpoints.opx[0] / 100, frx / 100, checkpoints.opz[0] / 100, frz / 100) < frad)
                                {
                                    afta = true;
                                    gowait = false;
                                }
                                if(py(conto.x / 100, checkpoints.opx[0] / 100, conto.z / 100, checkpoints.opz[0] / 100) < 25)
                                {
                                    afta = true;
                                    gowait = false;
                                    attack = 200;
                                    acr = 0;
                                }
                            }
                        }
                        if(checkpoints.stage == 15)
                        {
                            if(oupnt == -1)
                            {
                                int k3 = -10;
                                for(int k4 = 0; k4 < checkpoints.n; k4++)
                                {
                                    if((checkpoints.typ[k4] == -2 || checkpoints.typ[k4] == -4) && (k4 < 50 || k4 > 54) && (py(conto.x / 100, checkpoints.x[k4] / 100, conto.z / 100, checkpoints.z[k4] / 100) < k3 || k3 == -10))
                                    {
                                        k3 = py(conto.x / 100, checkpoints.x[k4] / 100, conto.z / 100, checkpoints.z[k4] / 100);
                                        oupnt = k4;
                                    }
                                }

                                oupnt--;
                                if(i < 0)
                                {
                                    oupnt += checkpoints.n;
                                }
                            }
                            if(oupnt >= 0 && oupnt < checkpoints.n)
                            {
                                i = oupnt;
                                if(py(conto.x / 100, checkpoints.x[i] / 100, conto.z / 100, checkpoints.z[i] / 100) < 800)
                                {
                                    oupnt = -(int)(75F + m.random() * 200F);
                                    runbul = (int)(50F + m.random() * 100F);
                                }
                            }
                            if(oupnt < -1)
                            {
                                oupnt++;
                            }
                            if(runbul != 0)
                            {
                                runbul--;
                            }
                        }
                        if(checkpoints.stage == 16)
                        {
                            boolean flag4 = false;
                            if(madness.cn == 13)
                            {
                                if(!gowait)
                                {
                                    if(checkpoints.clear[0] == 1)
                                    {
                                        if((double)m.random() > 0.5D)
                                        {
                                            wtx = -14000;
                                            wtz = 48000;
                                            frx = -5600;
                                            frz = 47600;
                                            frad = 0x157c0;
                                            oupnt = 33;
                                        } else
                                        {
                                            wtx = -5600;
                                            wtz = 8000;
                                            frx = -7350;
                                            frz = -4550;
                                            frad = 22000;
                                            oupnt = 15;
                                        }
                                        gowait = true;
                                        afta = false;
                                    }
                                    if(checkpoints.clear[0] == 4)
                                    {
                                        wtx = -12700;
                                        wtz = 14000;
                                        frx = -31000;
                                        frz = 1050;
                                        frad = 11000;
                                        oupnt = 51;
                                        gowait = true;
                                        afta = false;
                                    }
                                    if(checkpoints.clear[0] == 14)
                                    {
                                        wtx = -35350;
                                        wtz = 6650;
                                        frx = -48300;
                                        frz = 54950;
                                        frad = 11000;
                                        oupnt = 15;
                                        gowait = true;
                                        afta = false;
                                    }
                                    if(checkpoints.clear[0] == 17)
                                    {
                                        wtx = -42700;
                                        wtz = 41000;
                                        frx = -40950;
                                        frz = 49350;
                                        frad = 7000;
                                        oupnt = 42;
                                        gowait = true;
                                        afta = false;
                                    }
                                    if(checkpoints.clear[0] == 21)
                                    {
                                        wtx = -1750;
                                        wtz = -15750;
                                        frx = -25900;
                                        frz = -14000;
                                        frad = 11000;
                                        oupnt = 125;
                                        gowait = true;
                                        afta = false;
                                    }
                                }
                                if(gowait)
                                {
                                    if(py(conto.x / 100, wtx / 100, conto.z / 100, wtz / 100) < 10000 && madness.speed > 50F)
                                    {
                                        up = false;
                                    }
                                    if(py(conto.x / 100, wtx / 100, conto.z / 100, wtz / 100) < 200)
                                    {
                                        up = false;
                                        handb = true;
                                    }
                                    if(checkpoints.pcleared == oupnt && py(checkpoints.opx[0] / 100, frx / 100, checkpoints.opz[0] / 100, frz / 100) < frad)
                                    {
                                        runbul = 0;
                                        afta = true;
                                        gowait = false;
                                    }
                                    if(py(conto.x / 100, checkpoints.opx[0] / 100, conto.z / 100, checkpoints.opz[0] / 100) < 25)
                                    {
                                        afta = true;
                                        gowait = false;
                                        attack = 200;
                                        acr = 0;
                                    }
                                    if(checkpoints.clear[0] == 21 && oupnt != 125)
                                    {
                                        gowait = false;
                                    }
                                }
                                if(checkpoints.clear[0] >= 11 && !gowait || madness.power < 60F && checkpoints.clear[0] < 21)
                                {
                                    flag4 = true;
                                    if(!exitattack)
                                    {
                                        oupnt = -1;
                                        exitattack = true;
                                    }
                                } else
                                if(exitattack)
                                {
                                    exitattack = false;
                                }
                            }
                            if(madness.cn == 11)
                            {
                                flag4 = true;
                            }
                            if(flag4)
                            {
                                if(oupnt == -1)
                                {
                                    int l4 = -10;
                                    for(int k6 = 0; k6 < checkpoints.n; k6++)
                                    {
                                        if(checkpoints.typ[k6] == -4 && (py(conto.x / 100, checkpoints.x[k6] / 100, conto.z / 100, checkpoints.z[k6] / 100) < l4 && (double)m.random() > 0.59999999999999998D || l4 == -10))
                                        {
                                            l4 = py(conto.x / 100, checkpoints.x[k6] / 100, conto.z / 100, checkpoints.z[k6] / 100);
                                            oupnt = k6;
                                        }
                                    }

                                    oupnt--;
                                    if(i < 0)
                                    {
                                        oupnt += checkpoints.n;
                                    }
                                }
                                if(oupnt >= 0 && oupnt < checkpoints.n)
                                {
                                    i = oupnt;
                                    if(py(conto.x / 100, checkpoints.x[i] / 100, conto.z / 100, checkpoints.z[i] / 100) < 800)
                                    {
                                        oupnt = -(int)(75F + m.random() * 200F);
                                        runbul = (int)(50F + m.random() * 100F);
                                    }
                                }
                                if(oupnt < -1)
                                {
                                    oupnt++;
                                }
                                if(runbul != 0)
                                {
                                    runbul--;
                                }
                            }
                        }
                        madness.nofocus = true;
                    }
                    if(checkpoints.stage != 17)
                    {
                        if(checkpoints.stage == 9 || checkpoints.stage == 8 && madness.pcleared == 73 || checkpoints.stage == 16)
                        {
                            forget = true;
                        }
                        if((madness.missedcp == 0 || forget || trfix == 4) && trfix != 0)
                        {
                            byte byte1 = 0;
                            if(checkpoints.stage == 15 || checkpoints.stage == 16)
                            {
                                byte1 = 3;
                            }
                            if(trfix == 2)
                            {
                                int i5 = -10;
                                int l6 = 0;
                                for(int l7 = byte1; l7 < checkpoints.fn; l7++)
                                {
                                    if(py(conto.x / 100, checkpoints.x[fpnt[l7]] / 100, conto.z / 100, checkpoints.z[fpnt[l7]] / 100) < i5 || i5 == -10)
                                    {
                                        i5 = py(conto.x / 100, checkpoints.x[fpnt[l7]] / 100, conto.z / 100, checkpoints.z[fpnt[l7]] / 100);
                                        l6 = l7;
                                    }
                                }

                                if(checkpoints.stage == 8 || checkpoints.stage == 12)
                                {
                                    l6 = 1;
                                }
                                i = fpnt[l6];
                                if(checkpoints.special[l6])
                                {
                                    forget = true;
                                } else
                                {
                                    forget = false;
                                }
                            }
                            for(int j5 = byte1; j5 < checkpoints.fn; j5++)
                            {
                                if(py(conto.x / 100, checkpoints.x[fpnt[j5]] / 100, conto.z / 100, checkpoints.z[fpnt[j5]] / 100) < 2000)
                                {
                                    forget = false;
                                    actwait = 0;
                                    upwait = 0;
                                    turntyp = 2;
                                    randtcnt = -1;
                                    acuracy = 0;
                                    rampp = 0;
                                    trfix = 3;
                                }
                            }

                            if(trfix == 3)
                            {
                                madness.nofocus = true;
                            }
                        }
                    }
                    if(turncnt > randtcnt)
                    {
                        if(!gowait)
                        {
                            char c1 = '\0';
                            if(checkpoints.x[i] - conto.x >= 0)
                            {
                                c1 = '\264';
                            }
                            pan = (int)((double)(90 + c1) + Math.atan((double)(checkpoints.z[i] - conto.z) / (double)(checkpoints.x[i] - conto.x)) / 0.017453292519943295D);
                        } else
                        {
                            char c2 = '\0';
                            if(wtx - conto.x >= 0)
                            {
                                c2 = '\264';
                            }
                            pan = (int)((double)(90 + c2) + Math.atan((double)(wtz - conto.z) / (double)(wtx - conto.x)) / 0.017453292519943295D);
                        }
                        turncnt = 0;
                        randtcnt = (int)((float)acuracy * m.random());
                    } else
                    {
                        turncnt++;
                    }
                } else
                {
                    up = true;
                    char c = '\0';
                    int l1 = (int)(((float)pys(conto.x, checkpoints.opx[acr], conto.z, checkpoints.opz[acr]) / 2.0F) * aim);
                    int l3 = (int)((float)checkpoints.opx[acr] - (float)l1 * m.sin(checkpoints.omxz[acr]));
                    int k5 = (int)((float)checkpoints.opz[acr] + (float)l1 * m.cos(checkpoints.omxz[acr]));
                    if(l3 - conto.x >= 0)
                    {
                        c = '\264';
                    }
                    pan = (int)((double)(90 + c) + Math.atan((double)(k5 - conto.z) / (double)(l3 - conto.x)) / 0.017453292519943295D);
                    attack--;
                    if(attack <= 0)
                    {
                        attack = 0;
                    }
                    if(checkpoints.stage == 15 && exitattack && !bulistc && madness.missedcp != 0)
                    {
                        attack = 0;
                    }
                    if(checkpoints.stage == 16 && madness.cn == 13 && (checkpoints.clear[0] == 4 || checkpoints.clear[0] == 13 || checkpoints.clear[0] == 21))
                    {
                        attack = 0;
                    }
                    if(checkpoints.stage == 16 && madness.missedcp != 0 && (checkpoints.pos[madness.im] == 0 || checkpoints.pos[madness.im] == 1 && checkpoints.pos[0] == 0))
                    {
                        attack = 0;
                    }
                    if(checkpoints.stage == 16 && checkpoints.pos[0] > checkpoints.pos[madness.im] && madness.power < 80F)
                    {
                        attack = 0;
                    }
                }
                int j = conto.xz;
                if(zyinv)
                {
                    j += 180;
                }
                for(; j < 0; j += 360) { }
                for(; j > 180; j -= 360) { }
                for(; pan < 0; pan += 360) { }
                for(; pan > 180; pan -= 360) { }
                if(wall != -1 && hold == 0)
                {
                    clrnce = 0;
                }
                if(hold == 0)
                {
                    if(Math.abs(j - pan) < 180)
                    {
                        if(Math.abs(j - pan) > clrnce)
                        {
                            if(j < pan)
                            {
                                left = true;
                                lastl = true;
                            } else
                            {
                                right = true;
                                lastl = false;
                            }
                            if(Math.abs(j - pan) > 50 && madness.speed > (float)madness.swits[madness.cn][0] && turntyp != 0)
                            {
                                if(turntyp == 1)
                                {
                                    down = true;
                                }
                                if(turntyp == 2)
                                {
                                    handb = true;
                                }
                                if(!agressed)
                                {
                                    up = false;
                                }
                            }
                        }
                    } else
                    if(Math.abs(j - pan) < 360 - clrnce)
                    {
                        if(j < pan)
                        {
                            right = true;
                            lastl = false;
                        } else
                        {
                            left = true;
                            lastl = true;
                        }
                        if(Math.abs(j - pan) < 310 && madness.speed > (float)madness.swits[madness.cn][0] && turntyp != 0)
                        {
                            if(turntyp == 1)
                            {
                                down = true;
                            }
                            if(turntyp == 2)
                            {
                                handb = true;
                            }
                            if(!agressed)
                            {
                                up = false;
                            }
                        }
                    }
                }
                if(checkpoints.stage == 14 && wall != -1)
                {
                    if(trackers.dam[wall] == 0 || madness.pcleared == 45)
                    {
                        wall = -1;
                    }
                    if(madness.pcleared == 58 && checkpoints.opz[madness.im] < 36700)
                    {
                        wall = -1;
                        hold = 0;
                    }
                }
                if(wall != -1)
                {
                    if(lwall != wall)
                    {
                        if(lastl)
                        {
                            left = true;
                        } else
                        {
                            right = true;
                        }
                        wlastl = lastl;
                        lwall = wall;
                    } else
                    if(wlastl)
                    {
                        left = true;
                    } else
                    {
                        right = true;
                    }
                    if(trackers.dam[wall] != 0)
                    {
                        byte byte0 = 1;
                        if(trackers.skd[wall] == 1)
                        {
                            byte0 = 3;
                        }
                        hold += byte0;
                        if(hold > 10 * byte0)
                        {
                            hold = 10 * byte0;
                        }
                    } else
                    {
                        hold = 1;
                    }
                    wall = -1;
                } else
                if(hold != 0)
                {
                    hold--;
                }
            } else
            {
                if(trickfase == 0)
                {
                    int k = (int)(((madness.scy[0] + madness.scy[1] + madness.scy[2] + madness.scy[3]) * (float)(conto.y - 300)) / 4000F);
                    int i2 = 3;
                    if(checkpoints.stage == 15)
                    {
                        i2 = 10;
                    }
                    if(k > 7 && (m.random() > trickprf / (float)i2 || stuntf == 4 || stuntf == 3 || stuntf == 5 || stuntf == 6 || checkpoints.stage == 16))
                    {
                        oxy = madness.pxy;
                        ozy = madness.pzy;
                        flycnt = 0;
                        uddirect = 0;
                        lrdirect = 0;
                        udswt = false;
                        lrswt = false;
                        trickfase = 1;
                        if(k < 16)
                        {
                            if(stuntf != 6)
                            {
                                uddirect = -1;
                                udstart = 0;
                                udswt = false;
                            } else
                            if(oupnt != 70)
                            {
                                uddirect = 1;
                                udstart = 0;
                                udswt = false;
                            }
                        } else
                        if(m.random() > m.random() && stuntf != 1 || stuntf == 4 || stuntf == 6 || stuntf == 7)
                        {
                            if((m.random() > m.random() || stuntf == 2 || stuntf == 7) && stuntf != 4 && stuntf != 6)
                            {
                                uddirect = -1;
                            } else
                            {
                                uddirect = 1;
                            }
                            udstart = (int)(10F * m.random() * trickprf);
                            if(stuntf == 6)
                            {
                                udstart = 0;
                            }
                            if(checkpoints.stage == 16)
                            {
                                udstart = 0;
                            }
                            if(checkpoints.stage == 14 && (oupnt == 68 || oupnt == 69))
                            {
                                apunch = 20;
                                oupnt = 70;
                            }
                            if((double)m.random() > 0.84999999999999998D && stuntf != 4 && stuntf != 3 && stuntf != 6 && checkpoints.stage != 16)
                            {
                                udswt = true;
                            }
                            if(m.random() > trickprf + 0.3F && stuntf != 4 && stuntf != 6)
                            {
                                if(m.random() > m.random())
                                {
                                    lrdirect = -1;
                                } else
                                {
                                    lrdirect = 1;
                                }
                                lrstart = (int)(30F * m.random());
                                if((double)m.random() > 0.75D)
                                {
                                    lrswt = true;
                                }
                            }
                        } else
                        {
                            if(m.random() > m.random())
                            {
                                lrdirect = -1;
                            } else
                            {
                                lrdirect = 1;
                            }
                            lrstart = (int)(10F * m.random() * trickprf);
                            if((double)m.random() > 0.75D && checkpoints.stage != 16)
                            {
                                lrswt = true;
                            }
                            if(m.random() > trickprf + 0.3F)
                            {
                                if(m.random() > m.random())
                                {
                                    uddirect = -1;
                                } else
                                {
                                    uddirect = 1;
                                }
                                udstart = (int)(30F * m.random());
                                if((double)m.random() > 0.84999999999999998D)
                                {
                                    udswt = true;
                                }
                            }
                        }
                        if(trfix == 3 || trfix == 4)
                        {
                            if(checkpoints.stage != 8)
                            {
                                if(checkpoints.stage != 15 && lrdirect == -1)
                                {
                                    if(checkpoints.stage != 9)
                                    {
                                        uddirect = -1;
                                    } else
                                    {
                                        uddirect = 1;
                                    }
                                }
                                lrdirect = 0;
                                if((checkpoints.stage == 9 || checkpoints.stage == 15) && uddirect == -1)
                                {
                                    uddirect = 1;
                                }
                                if(madness.power < 60F)
                                {
                                    uddirect = -1;
                                }
                            } else
                            {
                                if(uddirect != 0)
                                {
                                    uddirect = -1;
                                }
                                lrdirect = 0;
                            }
                            if(checkpoints.stage == 10)
                            {
                                uddirect = 1;
                                lrdirect = 0;
                            }
                            if(checkpoints.stage == 16)
                            {
                                uddirect = -1;
                                lrdirect = 0;
                                if(madness.cn != 11 && madness.cn != 13)
                                {
                                    udstart = 7;
                                    if(madness.cn == 14 && madness.power > 30F)
                                    {
                                        udstart = 14;
                                    }
                                } else
                                {
                                    udstart = 0;
                                }
                                if(madness.cn == 11)
                                {
                                    lrdirect = -1;
                                    lrstart = 0;
                                }
                            }
                        }
                    } else
                    {
                        trickfase = -1;
                    }
                    if(!afta)
                    {
                        afta = true;
                    }
                    if(trfix == 3)
                    {
                        trfix = 4;
                        statusque += 30;
                    }
                }
                if(trickfase == 1)
                {
                    flycnt++;
                    if(lrdirect != 0 && flycnt > lrstart)
                    {
                        if(lrswt && Math.abs(madness.pxy - oxy) > 180)
                        {
                            if(lrdirect == -1)
                            {
                                lrdirect = 1;
                            } else
                            {
                                lrdirect = -1;
                            }
                            lrswt = false;
                        }
                        if(lrdirect == -1)
                        {
                            handb = true;
                            left = true;
                        } else
                        {
                            handb = true;
                            right = true;
                        }
                    }
                    if(uddirect != 0 && flycnt > udstart)
                    {
                        if(udswt && Math.abs(madness.pzy - ozy) > 180)
                        {
                            if(uddirect == -1)
                            {
                                uddirect = 1;
                            } else
                            {
                                uddirect = -1;
                            }
                            udswt = false;
                        }
                        if(uddirect == -1)
                        {
                            handb = true;
                            down = true;
                        } else
                        {
                            handb = true;
                            up = true;
                            if(apunch > 0)
                            {
                                down = true;
                                apunch--;
                            }
                        }
                    }
                    if(((madness.scy[0] + madness.scy[1] + madness.scy[2] + madness.scy[3]) * 100F) / (float)(conto.y - 300) < (float)(-saftey))
                    {
                        onceu = false;
                        onced = false;
                        oncel = false;
                        oncer = false;
                        lrcomp = false;
                        udcomp = false;
                        udbare = false;
                        lrbare = false;
                        trickfase = 2;
                        swat = 0;
                    }
                }
                if(trickfase == 2)
                {
                    if(swat == 0)
                    {
                        if(madness.dcomp != 0.0F || madness.ucomp != 0.0F)
                        {
                            udbare = true;
                        }
                        if(madness.lcomp != 0.0F || madness.rcomp != 0.0F)
                        {
                            lrbare = true;
                        }
                        swat = 1;
                    }
                    if(madness.wtouch)
                    {
                        if(swat == 1)
                        {
                            swat = 2;
                        }
                    } else
                    if(swat == 2)
                    {
                        if(madness.capsized && m.random() > mustland)
                        {
                            if(udbare)
                            {
                                lrbare = true;
                                udbare = false;
                            } else
                            if(lrbare)
                            {
                                udbare = true;
                                lrbare = false;
                            }
                        }
                        swat = 3;
                    }
                    if(udbare)
                    {
                        int l;
                        for(l = madness.pzy + 90; l < 0; l += 360) { }
                        for(; l > 180; l -= 360) { }
                        l = Math.abs(l);
                        if(madness.lcomp - madness.rcomp < 5F && (onced || onceu))
                        {
                            udcomp = true;
                        }
                        if(madness.dcomp > madness.ucomp)
                        {
                            if(madness.capsized)
                            {
                                if(udcomp)
                                {
                                    if(l > 90)
                                    {
                                        up = true;
                                    } else
                                    {
                                        down = true;
                                    }
                                } else
                                if(!onced)
                                {
                                    down = true;
                                }
                            } else
                            {
                                if(udcomp)
                                {
                                    if(perfection && Math.abs(l - 90) > 30)
                                    {
                                        if(l > 90)
                                        {
                                            up = true;
                                        } else
                                        {
                                            down = true;
                                        }
                                    }
                                } else
                                if(m.random() > mustland)
                                {
                                    up = true;
                                }
                                onced = true;
                            }
                        } else
                        if(madness.capsized)
                        {
                            if(udcomp)
                            {
                                if(l > 90)
                                {
                                    up = true;
                                } else
                                {
                                    down = true;
                                }
                            } else
                            if(!onceu)
                            {
                                up = true;
                            }
                        } else
                        {
                            if(udcomp)
                            {
                                if(perfection && Math.abs(l - 90) > 30)
                                {
                                    if(l > 90)
                                    {
                                        up = true;
                                    } else
                                    {
                                        down = true;
                                    }
                                }
                            } else
                            if(m.random() > mustland)
                            {
                                down = true;
                            }
                            onceu = true;
                        }
                    }
                    if(lrbare)
                    {
                        int i1 = madness.pxy + 90;
                        if(zyinv)
                        {
                            i1 += 180;
                        }
                        for(; i1 < 0; i1 += 360) { }
                        for(; i1 > 180; i1 -= 360) { }
                        i1 = Math.abs(i1);
                        if(madness.lcomp - madness.rcomp < 10F && (oncel || oncer))
                        {
                            lrcomp = true;
                        }
                        if(madness.lcomp > madness.rcomp)
                        {
                            if(madness.capsized)
                            {
                                if(lrcomp)
                                {
                                    if(i1 > 90)
                                    {
                                        left = true;
                                    } else
                                    {
                                        right = true;
                                    }
                                } else
                                if(!oncel)
                                {
                                    left = true;
                                }
                            } else
                            {
                                if(lrcomp)
                                {
                                    if(perfection && Math.abs(i1 - 90) > 30)
                                    {
                                        if(i1 > 90)
                                        {
                                            left = true;
                                        } else
                                        {
                                            right = true;
                                        }
                                    }
                                } else
                                if(m.random() > mustland)
                                {
                                    right = true;
                                }
                                oncel = true;
                            }
                        } else
                        if(madness.capsized)
                        {
                            if(lrcomp)
                            {
                                if(i1 > 90)
                                {
                                    left = true;
                                } else
                                {
                                    right = true;
                                }
                            } else
                            if(!oncer)
                            {
                                right = true;
                            }
                        } else
                        {
                            if(lrcomp)
                            {
                                if(perfection && Math.abs(i1 - 90) > 30)
                                {
                                    if(i1 > 90)
                                    {
                                        left = true;
                                    } else
                                    {
                                        right = true;
                                    }
                                }
                            } else
                            if(m.random() > mustland)
                            {
                                left = true;
                            }
                            oncer = true;
                        }
                    }
                }
            }
        }
    }

    public void reset(CheckPoints checkpoints, int i)
    {
        pan = 0;
        attack = 0;
        acr = 0;
        afta = false;
        trfix = 0;
        acuracy = 0;
        upwait = 0;
        forget = false;
        bulistc = false;
        runbul = 0;
        revstart = 0;
        oupnt = 0;
        gowait = false;
        apunch = 0;
        exitattack = false;
        if(checkpoints.stage == 6 || checkpoints.stage == 8)
        {
            hold = 50;
        }
        if(checkpoints.stage == 7)
        {
            hold = 10;
        }
        if(checkpoints.stage == 10)
        {
            hold = 30;
        }
        if(checkpoints.stage == 11)
        {
            if(i != 13)
            {
                hold = 35;
                revstart = 25;
            } else
            {
                hold = 5;
            }
            statusque = 0;
        }
        if(checkpoints.stage == 12)
        {
            if(i != 13)
            {
                hold = (int)(20F + 10F * m.random());
                revstart = (int)(10F + 10F * m.random());
            } else
            {
                hold = 5;
            }
            statusque = 0;
        }
        if(checkpoints.stage == 14)
        {
            hold = 30;
            statusque = 0;
            if(i != 14)
            {
                revstart = 1;
            }
        }
        if(checkpoints.stage == 15)
        {
            hold = 40;
        }
        if(checkpoints.stage == 16)
        {
            hold = 20;
        }
        if(checkpoints.stage != 9 && checkpoints.stage != 16)
        {
            for(int j = 0; j < checkpoints.fn; j++)
            {
                int l = -10;
                for(int i1 = 0; i1 < checkpoints.n; i1++)
                {
                    if(py(checkpoints.fx[j] / 100, checkpoints.x[i1] / 100, checkpoints.fz[j] / 100, checkpoints.z[i1] / 100) < l || l == -10)
                    {
                        l = py(checkpoints.fx[j] / 100, checkpoints.x[i1] / 100, checkpoints.fz[j] / 100, checkpoints.z[i1] / 100);
                        fpnt[j] = i1;
                    }
                }

            }

            for(int k = 0; k < checkpoints.fn; k++)
            {
                fpnt[k] -= 4;
                if(fpnt[k] < 0)
                {
                    fpnt[k] += checkpoints.nsp;
                }
            }

        } else
        {
            if(checkpoints.stage == 9)
            {
                fpnt[0] = 14;
                fpnt[1] = 36;
            }
            if(checkpoints.stage == 16)
            {
                fpnt[3] = 39;
            }
        }        
        left = false;
        right = false;
        up = false;
        down = false;
        handb = false;
        lookback = 0;
        arrace = false;
        mutem = false;
        mutes = false;
    }

    public Control(Medium medium)
    {        
        left = false;
        right = false;
        up = false;
        down = false;
        handb = false;
        lookback = 0;
        enter = false;
        arrace = false;
        mutem = false;
        mutes = false;
        pan = 0;
        attack = 0;
        acr = 0;
        afta = false;
        fpnt = new int[5];
        trfix = 0;
        forget = false;
        bulistc = false;
        runbul = 0;
        acuracy = 0;
        upwait = 0;
        agressed = false;
        skiplev = 1.0F;
        clrnce = 5;
        rampp = 0;
        turntyp = 0;
        aim = 0.0F;
        saftey = 30;
        perfection = false;
        mustland = 0.5F;
        usebounce = false;
        trickprf = 0.5F;
        stuntf = 0;
        zyinv = false;
        lastl = false;
        wlastl = false;
        hold = 0;
        wall = -1;
        lwall = -1;
        stcnt = 0;
        statusque = 0;
        turncnt = 0;
        randtcnt = 0;
        upcnt = 0;
        trickfase = 0;
        swat = 0;
        udcomp = false;
        lrcomp = false;
        udbare = false;
        lrbare = false;
        onceu = false;
        onced = false;
        oncel = false;
        oncer = false;
        lrdirect = 0;
        uddirect = 0;
        lrstart = 0;
        udstart = 0;
        oxy = 0;
        ozy = 0;
        flycnt = 0;
        lrswt = false;
        udswt = false;
        gowait = false;
        actwait = 0;
        cntrn = 0;
        revstart = 0;
        oupnt = 0;
        wtz = 0;
        wtx = 0;
        frx = 0;
        frz = 0;
        frad = 0;
        apunch = 0;
        exitattack = false;
        avoidnlev = 0;
        m = medium;
    }

    public void falseo()
    {        
        left = false;
        right = false;
        up = false;
        down = false;
        handb = false;
        lookback = 0;
        enter = false;
        arrace = false;
        mutem = false;
        mutes = false;
    }

    public int pys(int i, int j, int k, int l)
    {
        return (int)Math.sqrt((i - j) * (i - j) + (k - l) * (k - l));
    }

    public int py(int i, int j, int k, int l)
    {
        return (i - j) * (i - j) + (k - l) * (k - l);
    }
}
