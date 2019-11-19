import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/** Class for changing fonts of given components
 * Menu that displays available fonts and changes fonts of given
 * components when font is clicked in menu. Can change underlying 
 * components if given component is of type container. Underlying 
 * components is not changed by default.
 * @author Jonatan
 *
 */
@SuppressWarnings("serial")
public class FontMenu extends JMenu {

	private String fonts[];
	private int changeUnderLayingComponents=
			FontChanger.ONLY_CHANGE_THIS_COMPONENT;
	
	/**
	 * @param menuName Name of Menu
	 * @param container which component that fonts should be changed
	 * @param changeUnderLayingComponents if underlying components
	 * also should be changed
	 */
	public FontMenu(String menuName,Component component,
					int changeUnderLayingComponents) {
		this(menuName,component);
		this.changeUnderLayingComponents=changeUnderLayingComponents;
		
	}
	/**
	 * @param menuName Name of Menu
	 * @param components which components that fonts should be changed
	 * in
	 * @param changeUnderLayingComponents if underlying components
	 * of components also should be changed
	 */
	public FontMenu(String menuName,Iterable<Component> components,
					int changeUnderLayingComponents) {
		this(menuName,components);
		this.changeUnderLayingComponents=changeUnderLayingComponents;
	}
	
	/** Does not change underlying components.
	 * @param menuName name of Menu
	 * @param component component that fonts should be changed in
	 */
	public FontMenu(String menuName,Component component) {
		this(menuName,new ArrayList<Component>(Arrays.asList(component)));
	}
	/**
	 * @param menuName name of Menu
	 * @param components components that fonts should be changed in
	 */
	public FontMenu(String menuName,Iterable<Component> components) {
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
					for(Component c:components) {
						FontChanger.changeFont(c,
								((JMenuItem)e.getSource()).getText(),
								changeUnderLayingComponents);
					}
				}
				
			});
			
			this.add(menuItems[i]);
			
		}
		
	}
	

	

	
	

}
