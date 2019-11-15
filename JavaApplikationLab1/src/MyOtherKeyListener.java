import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyOtherKeyListener implements KeyListener {
	TestUnitController testUnitController;
	
	public MyOtherKeyListener(TestUnitController testUnitController) {
		this.testUnitController=testUnitController;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			testUnitController.CloseThreadButtonPressed(e);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
