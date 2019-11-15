import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class RunTestButtonLitsener implements ActionListener {
	TestUnitController testUnitController;
	JCheckBox runTestsInOrder;

	public RunTestButtonLitsener(TestUnitController testUnitController,
							     JCheckBox runTestsInOrder) {
		this.testUnitController=testUnitController;
		this.runTestsInOrder=runTestsInOrder;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Call TestUnitController to check that tests actually started. 
		testUnitController.RunTestButtonPressed(e,runTestsInOrder.isSelected());
	}

}
