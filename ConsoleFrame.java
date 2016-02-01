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
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.awt.TextField;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ConsoleFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5259700796854880162L;

	static boolean frameVisible;
	
	static Thread consoleThread;
	
	static JFrame frame;
	static JTextComponent consoleHere;
    static JScrollPane scrolly;
    static MessageConsole console;
    public static TextField textField;
    private JLabel lblNewLabel;
    
	public ConsoleFrame() {
		////
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		
	    frame = new JFrame("UNFM2 Console");
	    
	    frame.setSize(((screenSize.width / 2) - 335) - 10, screenSize.height - scnMax.bottom);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{279, 0};
        gridBagLayout.rowHeights = new int[]{13, 697, 0, 0, 0, 10, 0};
        gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        consoleHere = new JTextPane();
        scrolly = new JScrollPane(consoleHere);
        console = new MessageConsole(consoleHere);
        GridBagConstraints gbc_scrolly = new GridBagConstraints();
        gbc_scrolly.fill = GridBagConstraints.BOTH;
        gbc_scrolly.insets = new Insets(0, 0, 5, 0);
        gbc_scrolly.gridx = 0;
        gbc_scrolly.gridy = 1;
        frame.getContentPane().add(scrolly, gbc_scrolly);
        
        lblNewLabel = new JLabel("command box");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 2;
        frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
        
        textField = new TextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 3;
        frame.getContentPane().add(textField, gbc_textField);
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
