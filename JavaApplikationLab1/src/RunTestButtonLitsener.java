import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunTestButtonLitsener implements ActionListener {
	Controller controller;

	public RunTestButtonLitsener(Controller controller) {
		this.controller=controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Call Controller to check that tests actually started. 
		controller.RunTestButtonPressed(e);
	}

}
