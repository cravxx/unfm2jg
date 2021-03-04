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

    public int clrad = 0;

    public float comprad = 0F;

    public float dammult = 0F;

    public int flipy = 0;

    public float grip = 0F;

    public int handb = 0;

    public int lift = 0;

    public int maxmag = 0;

    public float moment = 0;

    public int msquash = 0;

    private float outdam = 0;

    public int powerloss = 0;

    public int push = 0;

    public int revlift = 0;

    public int revpush = 0;

    public float simag = 0F;

    public int swits[] = new int[3];

    public int turn = 0;

    public int engine = 0;

    public float proba = 0F;

    public float dishandle = 0F;

    /**
     * set up a new stat
     *
     * @param car the car
     */
    public Stat(final int car) {
        acelf = StatList.acelf[car].clone();
        swits = StatList.swits[car].clone();
        airc = StatList.airc[car];
        airs = StatList.airs[car];
        bounce = StatList.bounce[car];
        clrad = StatList.clrad[car];
        comprad = StatList.comprad[car];
        dammult = StatList.dammult[car];
        flipy = StatList.flipy[car];
        grip = StatList.grip[car];
        handb = StatList.handb[car];
        lift = StatList.lift[car];
        maxmag = StatList.maxmag[car];
        moment = StatList.moment[car];
        msquash = StatList.msquash[car];
        powerloss = StatList.powerloss[car];
        push = StatList.push[car];
        revlift = StatList.revlift[car];
        revpush = StatList.revpush[car];
        turn = StatList.turn[car];
        simag = StatList.simag[car];
        engine = StatList.engine[car];
        outdam = StatList.outdam[car];
        proba = StatList.proba[car];
        dishandle = StatList.dishandle[car];
    }

    public Stat(){
    }

}
