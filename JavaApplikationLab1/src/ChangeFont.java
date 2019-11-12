import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

public class ChangeFont {

	public static void changeFont(Component comp,String f) {
		comp.setFont(new Font(
				f,comp.getFont().getStyle(),comp.getFont().getSize()));
		if(comp instanceof Container) {
			for(Component c:((Container)comp).getComponents()) {
				changeFont(c,f);
			}
		}
	}
}
