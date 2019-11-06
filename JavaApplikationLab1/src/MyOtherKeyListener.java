import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyOtherKeyListener implements KeyListener {
	Controller controller;
	
	public MyOtherKeyListener(Controller controller) {
		this.controller=controller;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			controller.CloseThreadButtonPressed(e);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
