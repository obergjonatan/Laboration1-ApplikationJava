import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FontMenu extends JMenu {
	private String fonts[];
	private int changeUnderLayingComponents=0;
	
	public FontMenu(String menuName,Container container,
					int changeUnderLayingComponents) {
		this(menuName,container);
		this.changeUnderLayingComponents=changeUnderLayingComponents;
		
	}
	public FontMenu(String menuName,Iterable<Container> containers,
					int changeUnderLayingComponents) {
		this(menuName,containers);
		this.changeUnderLayingComponents=changeUnderLayingComponents;
	}
	
	public FontMenu(String menuName,Container container) {
		this(menuName,new ArrayList<Container>(Arrays.asList(container)));
	}
	public FontMenu(String menuName,Iterable<Container> containers) {
		super(menuName);
		fonts= GraphicsEnvironment.getLocalGraphicsEnvironment().
				getAvailableFontFamilyNames();
		JMenuItem menuItems[] = new JMenuItem[fonts.length];
		for(int i=0 ; i<fonts.length ; i++) {
			Font font = new Font(fonts[i],Font.PLAIN,16);
			menuItems[i]= new JMenuItem(fonts[i]);
			menuItems[i].setFont(font);
			menuItems[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(Container c:containers) {
						ChangeFont.changeFont(c,
								((JMenuItem)e.getSource()).getText(),
								changeUnderLayingComponents);
					}
				}
				
			});
			
			this.add(menuItems[i]);
			
		}
		
	}
	

	

	
	

}
