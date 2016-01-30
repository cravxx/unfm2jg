import java.applet.Applet;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**Put a menu bar for things
 * 
 * @author Kaffeinated
 */
public class Settings extends Applet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3096969581463346958L;
	/**
	 * no good way to get it automagically
	 */
	public final static int menubarHeight = 21;
	/**
	 * change this is false to hide the bar
	 */
	final static boolean showMenubar = true; 
	public static boolean quickhide = false;
	static MenuBar menubar = new MenuBar();
    static Menu menu = new Menu("File");
    static Menu submenu1 = new Menu("Open");
    static MenuItem item1 = new MenuItem("File");
    static MenuItem item2 = new MenuItem("URL");
    static Menu submenu2 = new Menu("Save As");
    static MenuItem item3 = new MenuItem("text");
    static MenuItem item4 = new MenuItem("html");
	
    static Component c = null;          
    
    public Settings(){
    	submenu1.add(item1);
	    submenu1.add(item2);
	    menu.add(submenu1);
	    submenu2.add(item3);
	    submenu2.add(item4);
	    menu.add(submenu2);
	    menubar.add(menu);
		
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("done");
			}
		});	
    }
    
    public static void initialize(Component instIn){
    	while (instIn != null && !(c instanceof Frame)) {
            c = instIn.getParent();
	    }	    
	}
    
	public static void showMenu(Component instIn){
		if(showMenubar){
			initialize(instIn);
			((Frame)c).setMenuBar(menubar);      		
		}		
	}
	
	public static void hideMenu(Component instIn){
		initialize(instIn);
		((Frame)c).setMenuBar(null);		
	}
}
