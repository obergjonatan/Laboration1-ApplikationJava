import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class RunTestButtonLitsener implements ActionListener {
	Controller controller;
	JCheckBox runTestsInOrder;

	public RunTestButtonLitsener(Controller controller,JCheckBox runTestsInOrder) {
		this.controller=controller;
		this.runTestsInOrder=runTestsInOrder;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Call Controller to check that tests actually started. 
		controller.RunTestButtonPressed(e,runTestsInOrder.isSelected());
	}

}
