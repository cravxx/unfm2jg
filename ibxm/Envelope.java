package ibxm;

public class Envelope {
    public boolean enabled = false;
    public boolean sustain = false;
    public boolean looped = false;
    public int sustainTick = 0;
    public int loopStartTick = 0;
    public int loopEndTick = 0;
    public int numPoints = 1;
    public int[] pointsTick = new int[1];
    public int[] pointsAmpl = new int[1];

    public int nextTick(int tick, final boolean keyOn) {
        ++tick;
        if (looped && tick >= loopEndTick) {
            tick = loopStartTick;
        }

        if (sustain && keyOn && tick >= sustainTick) {
            tick = sustainTick;
        }

        return tick;
    }

    public int calculateAmpl(final int tick) {
        int ampl = pointsAmpl[numPoints - 1];
        if (tick < pointsTick[numPoints - 1]) {
            int point = 0;

            int dt;
            for (dt = 1; dt < numPoints; ++dt) {
                if (pointsTick[dt] <= tick) {
                    point = dt;
                }
            }

            dt = pointsTick[point + 1] - pointsTick[point];
            final int da = pointsAmpl[point + 1] - pointsAmpl[point];
            ampl = pointsAmpl[point];
            ampl += (da << 24) / dt * (tick - pointsTick[point]) >> 24;
        }

        return ampl;
    }

    public void toStringBuffer(final StringBuffer out) {
        out.append("Enabled: ").append(enabled).append('\n');
        out.append("Sustain: ").append(sustain).append('\n');
        out.append("Looped: ").append(looped).append('\n');
        out.append("Sustain Tick: ").append(sustainTick).append('\n');
        out.append("Loop Start Tick: ").append(loopStartTick).append('\n');
        out.append("Loop End Tick: ").append(loopEndTick).append('\n');
        out.append("Num Points: ").append(numPoints).append('\n');
        out.append("Points: ");

        for (int point = 0; point < numPoints; ++point) {
            out.append("(").append(pointsTick[point]).append(", ").append(pointsAmpl[point]).append("), ");
        }

        out.append('\n');
    }
}
