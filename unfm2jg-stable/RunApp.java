import fallk.logmaster.HLogger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class RunApp extends Panel {
    /**
     *
     */
    private static final long serialVersionUID = -8590687589434803725L;

    private static Frame frame;
    private static GameSparker applet;
    private static ArrayList<Image> icons;

    /**
     * Fetches icons of 16, 32 and 48 pixels from the 'data' folder.
     *
     * @return icons - ArrayList of icon locations
     */
    private static ArrayList<Image> getIcons() {
        if (icons == null) {
            icons = new ArrayList<>();
            int[] resols = {
                    16, 32, 48
            };
            for (int res : resols) {
                icons.add(Toolkit.getDefaultToolkit().createImage("data/ico_" + res + ".png"));
            }
        }
        return icons;
    }

    public static void main(String[] strings) {
        System.runFinalizersOnExit(true);
        HLogger.info("UNFM2 Console"); // Change this to the message of your preference
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            HLogger.warn("Could not setup System Look&Feel: " + ex.toString());
        }
        startup();
    }

    private static void startup() {
        frame = new Frame("UNFM2");// Change this to the name of your preference
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        frame.setIconImages(getIcons());

        applet = new GameSparker();

        applet.setStub(new DesktopStub());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
                exitSequence();
            }
        });
        applet.setPreferredSize(new Dimension(900, 500));// The resolution of your game goes here
        frame.add("Center", applet);
        frame.setResizable(false);// If you plan to make you game support changes in resolution, you can comment out this line.
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        applet.init();
        applet.start();
    }

    private static void exitSequence() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(200L);
        } catch (Exception exception) {
        }
        applet.destroy();
        applet = null;
        System.exit(0);
    }
}