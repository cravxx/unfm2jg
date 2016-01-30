import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConsoleFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5259700796854880162L;

	static boolean frameVisible;
	
	static Thread consoleThread;
	
	static JFrame frame;
	static JPanel panel;
	static JTextComponent consoleHere;
    static JScrollPane scrolly;
    static MessageConsole console;
    
	public ConsoleFrame() {		
		panel = new JPanel();
		consoleHere = new JTextPane();
		scrolly = new JScrollPane(consoleHere);
		console = new MessageConsole(consoleHere);
		////
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		
	    frame = new JFrame("UNFM2 Console");
	    
	    frame.setSize(((screenSize.width / 2) - 335) - 10, screenSize.height - scnMax.bottom);	       
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        frame.getContentPane().add(panel);
        
        frame.getContentPane().add(scrolly);
        console.redirectOut();
        console.redirectErr(Color.RED, null);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosed(WindowEvent arg0) {
	    		frameVisible = false;
	    		System.out.println("debugWindow disabled");
	    	}
	    });
	}
	
	public static void showUNFM2Console(){		
		frameVisible = true;
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        frame.setVisible(true);
			System.out.println("debugWindow enabled");
	      }
	    });
	}

	public static void hideUNFM2Console(){
		frameVisible = false;
		frame.setVisible(false);
		frame.dispose();
	}

}
