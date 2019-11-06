import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class closeThreadButtonLitsener implements ActionListener {
	Controller controller;
	public closeThreadButtonLitsener(Controller controller) {
		this.controller=controller;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.CloseThreadButtonPressed(e);
		
	}

}
