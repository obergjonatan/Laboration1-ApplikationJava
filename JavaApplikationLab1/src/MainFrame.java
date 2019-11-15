import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;


public class MainFrame {

	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				 TestUnitViewFrame gui = new TestUnitViewFrame("UnitTester");
				 gui.show();
				 TestUnitController testUnitController = new TestUnitController(gui);
				 gui.setLitseners(testUnitController);
			}
		});

	}

}
