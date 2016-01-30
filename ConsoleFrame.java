import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

public class ConsoleFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5259700796854880162L;

	static boolean frameVisible;
	
	static JFrame frame;
	static JPanel panel;
	static JTextComponent consoleHere;
    static JScrollPane scrolly;
    static MessageConsole console;
    
	public ConsoleFrame() {
		frame = new JFrame("UNFM2 Console");
		panel = new JPanel();
		consoleHere = new JTextPane();
		scrolly = new JScrollPane(consoleHere);
		console = new MessageConsole(consoleHere);
		////
		
		frame.setSize(300, 200);	           
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        frame.getContentPane().add(panel);
        
        frame.getContentPane().add(scrolly);
        console.redirectOut();
        console.redirectErr(Color.RED, null);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

		frameVisible = false;
	}
	
	public static void showUNFM2Console(){		
		frameVisible = true;
		frame.setVisible(true);
	}

	public static void hideUNFM2Console(){
		frameVisible = false;
		frame.setVisible(false);
	}

}
