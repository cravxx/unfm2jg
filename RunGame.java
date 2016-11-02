import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class RunGame {
    public static JFrame frame;
    public static GameSparker applet;
    
    public static void main(String[] args) {

        System.runFinalizersOnExit(true);
        frame = new JFrame("Need for Madness");
        frame.setBackground(Color.black);
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage("data/icon.png"));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (applet.exwist) {
                    System.gc();
                }
                applet.exwist = true;
            }
        });
        applet = new GameSparker();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowevent) {
                if (applet.exwist) {
                    System.gc();
                }
                applet.exwist = true;
            }
        });
        frame.add("Center", applet);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(930, 586));
        frame.setSize(930, 586);
        try {
            Thread.sleep(1000L);
        } catch (final InterruptedException ignored) {
        }
    }
}
