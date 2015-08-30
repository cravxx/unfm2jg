
public class Trackers
{

    String sequ[] = {
        "Access Denied !", "This game will not run under this http:/ loaction:", "Please contact radicalplay.com for details."
    };
    int x[];
    int y[];
    int z[];
    int xy[];
    int zy[];
    int skd[];
    int dam[];
    boolean notwall[];
    boolean tracksReady;
    int c[][];
    int radx[];
    int radz[];
    int rady[];
    int nt;

    public Trackers()
    {
        x = new int[870];
        y = new int[870];
        z = new int[870];
        xy = new int[870];
        zy = new int[870];
        skd = new int[870];
        dam = new int[870];
        notwall = new boolean[870];
        tracksReady = false;
        c = new int[870][3];
        radx = new int[870];
        radz = new int[870];
        rady = new int[870];
        nt = 0;
    }
}
