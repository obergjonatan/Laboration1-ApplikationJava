import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;

public class MyOwnKeyListener implements KeyListener {
	TestUnitController testUnitController;
	JCheckBox runTestsInOrder;
	
	public MyOwnKeyListener(TestUnitController testUnitController,JCheckBox runTestsInOrder) {
		this.testUnitController=testUnitController;
		this.runTestsInOrder=runTestsInOrder;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	

	}

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
