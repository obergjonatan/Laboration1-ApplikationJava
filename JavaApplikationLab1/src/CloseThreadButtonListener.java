import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** ActionListener for notifying testUnitController that threads should be closed
 * @author Jonatan
 *
 */
public class CloseThreadButtonListener implements ActionListener {
	
	TestUnitController testUnitController;
	/**
	 * @param testUnitController testUnitController which should be notified 
	 */
	public CloseThreadButtonListener(TestUnitController testUnitController) {
		this.testUnitController=testUnitController;
	}
	/** Calls CloseThreadButtonPressed in testUnitController
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		testUnitController.CloseThreadButtonPressed(e);
		
	}

}
