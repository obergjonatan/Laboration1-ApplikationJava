import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** ActionListener for a menu for changing class path of UnitTester
 * @author Jonatan
 *
 */
public class ClassPathMenuActionListener implements ActionListener {
	TestUnitController controller;
	JFrame mainFrame;
	
	/**
	 * @param mainFrame Jframe that JOptionPane InputDialog 
	 * will pop up in
	 * @param controller TestUnitController which classPath string
	 * will be sent to for changing of ClassLoader of TestUnit program
	 */
	public ClassPathMenuActionListener(JFrame mainFrame,
									   TestUnitController controller) {
		this.mainFrame=mainFrame;
		this.controller=controller;
	}
	/** Gives user a pane for input of class path
	 * Takes input for classpath and sends it to controller for 
	 * change of class path.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String classPath=JOptionPane.showInputDialog(mainFrame,
				"input class path (If no input classpath is set to same directory"
				+ "as jar file of program)");
		controller.setClassPath(classPath);
	}

}
