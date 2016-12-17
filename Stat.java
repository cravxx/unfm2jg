/**
 * contrary to popular belief, this class was not jacked from src, but developed alongside it, for different, yet resemblant, cases.
 *
 * @author eli
 */
public class Stat {

    /**
     * the stats
     */

    public float acelf[] = new float[3];

    public int airc = 0;

    public float airs = 0F;

    public float bounce = 0F;

    public int cclass = 0;

    public int clrad = 0;

    public float comprad = 0F;

    public float dammult = 0F;

    public float dishandle = 0F;

    public int enginsignature = 0;

    public int flipy = 0;

    public float grip = 0F;

    public int handb = 0;

    public int lift = 0;

    public int maxmag = 0;

    public float moment = 0;

    public int msquash = 0;

    public String names;

    public float outdam = 0;

    public int powerloss = 0;

    public int push = 0;

    public int revlift = 0;

    public int revpush = 0;

    public float simag = 0F;

    public int swits[] = new int[3];

    public int turn = 0;

    public int im = 0;

    /**
     * set up a new stat
     *
     * @param car the car
     */
    public Stat(final int car) {
        im = car;
        acelf = StatList.acelf[im].clone();
        swits = StatList.swits[im].clone();
        airc = StatList.airc[im];
        airs = StatList.airs[im];
        bounce = StatList.bounce[im];
        cclass = StatList.cclass[im];
        clrad = StatList.clrad[im];
        comprad = StatList.comprad[im];
        dammult = StatList.dammult[im];
        dishandle = StatList.dishandle[im];
        enginsignature = StatList.enginsignature[im];
        flipy = StatList.flipy[im];
        grip = StatList.grip[im];
        handb = StatList.handb[im];
        lift = StatList.lift[im];
        maxmag = StatList.maxmag[im];
        moment = StatList.moment[im];
        msquash = StatList.msquash[im];
        names = StatList.names[im];
        outdam = StatList.outdam[im];
        powerloss = StatList.powerloss[im];
        push = StatList.push[im];
        revlift = StatList.revlift[im];
        revpush = StatList.revpush[im];
        turn = StatList.turn[im];
        simag = StatList.simag[im];
    }

}
