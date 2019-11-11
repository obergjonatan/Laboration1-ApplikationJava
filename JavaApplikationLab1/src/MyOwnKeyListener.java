import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;

public class MyOwnKeyListener implements KeyListener {
	Controller controller;
	JCheckBox runTestsInOrder;
	
	public MyOwnKeyListener(Controller controller,JCheckBox runTestsInOrder) {
		this.controller=controller;
		this.runTestsInOrder=runTestsInOrder;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			controller.RunTestButtonPressed(e,runTestsInOrder.isSelected());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
