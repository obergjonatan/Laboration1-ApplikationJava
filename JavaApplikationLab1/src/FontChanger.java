import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/** Class for changing font of components
 *  
 * @author Jonatan
 *
 */
public class FontChanger {
	public static final int CHANGE_UNDERLAYING_COMPONENTS=1;
	public static final int ONLY_CHANGE_THIS_COMPONENT=0;

	/** Function to change font of component
	 *  Function has option to change font of underlying components
	 *  (this includes the font of border titles) if component given
	 *  is a container
	 * @param comp component that font of should be changed
	 * @param fontName string with name of Font
	 * @param change integer that specifies if underlying components fonts
	 * should be changed as well. class has static variables for this
	 * which are: CHANGE_UNDERLYING_COMPONENTS and 
	 * ONLY_CHANGE_THIS_COMPONENT.
	 */
	public static void changeFont(Component comp,String fontName,int change) {
		comp.setFont(new Font(
				fontName,comp.getFont().getStyle(),comp.getFont().getSize()));
		if(change==1) {
			if(comp instanceof JComponent) {
				Border border=((JComponent) comp).getBorder();
				if(border instanceof TitledBorder) {
					TitledBorder titledBorder = (TitledBorder)border;
					titledBorder.setTitleFont(new Font(fontName,
							titledBorder.getTitleFont().getStyle(),
							titledBorder.getTitleFont().getSize()));
				}
			}
			if(comp instanceof Container) {
				for(Component c:((Container)comp).getComponents()) {
					changeFont(c,fontName,1);
				}
			}
		}
	}
}

