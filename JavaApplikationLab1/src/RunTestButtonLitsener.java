import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

/** ActionListener for run test button in UnitTester
 * @author Jonatan
 *
 */
public class RunTestButtonLitsener implements ActionListener {
	
	TestUnitController testUnitController;
	JCheckBox runTestsInOrder;

	/** 
	 * @param testUnitController controller to call when action is
	 * performed.
	 * @param runTestsInOrder JCheckBox which selected status is sent 
	 * to the controller
	 */
	public RunTestButtonLitsener(TestUnitController testUnitController,
							     JCheckBox runTestsInOrder) {
		this.testUnitController=testUnitController;
		this.runTestsInOrder=runTestsInOrder;
		
	}

	/** Notifies controller that tests should be ran, sends 
	 * boolean from JCheckBox that tells controller whether tests
	 * should be run sequentially or not
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Call TestUnitController to check that tests actually started. 
		testUnitController.RunTestButtonPressed(e,runTestsInOrder.isSelected());
	}

}
