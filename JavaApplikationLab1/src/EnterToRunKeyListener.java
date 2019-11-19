import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;

/** KeyListener for having the Enter key also call run tests
 * @author Jonatan
 *
 */
public class EnterToRunKeyListener implements KeyListener {
	
	TestUnitController testUnitController;
	JCheckBox runTestsInOrder;
	
	/** Constructor that sets controller and JCheckbox
	 * @param testUnitController
	 * @param runTestsInOrder
	 */
	public EnterToRunKeyListener(TestUnitController testUnitController,
								 JCheckBox runTestsInOrder) {
		this.testUnitController=testUnitController;
		this.runTestsInOrder=runTestsInOrder;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	

	}

	/** calls controller to run tests. 
	 *
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			testUnitController.RunTestButtonPressed(e,runTestsInOrder.isSelected());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
