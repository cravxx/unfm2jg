class ModTrackInfo {

    public byte samples[];
    public int position;
    public int length;
    public int repeat;
    public int replen;
    public int volume;
    public int error;
    public int pitch;
    public int old_position;
    public int start_period;
    public int period;
    public int step;
    public int effect;
    public int portto;
    public int vibpos;
    public int trempos;
    public int oldsampofs;
    public final int[] arp;
    public int arpindex;
    public int vol_slide;
    public int port_inc;
    public int port_up;
    public int port_down;
    public int vib_rate;
    public int vib_depth;
    public int trem_rate;
    public int trem_depth;
    public byte retrig;
    public int finetune_rate;
    public int period_low_limit;
    public int period_high_limit;

    ModTrackInfo() {
        arp = new int[3];
    }
}
