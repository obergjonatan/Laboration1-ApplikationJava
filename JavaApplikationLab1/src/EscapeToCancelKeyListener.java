import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** KeyListener so UnitTester can cancel tests with escape button
 * @author Jonatan
 *
 */
public class EscapeToCancelKeyListener implements KeyListener {
	TestUnitController testUnitController;
	
	/** Constructor 
	 * @param testUnitController Sets TestUnitController
	 */
	public EscapeToCancelKeyListener(TestUnitController testUnitController) {
		this.testUnitController=testUnitController;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/** Calls controller to cancel tests.
	 *
	 */
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
