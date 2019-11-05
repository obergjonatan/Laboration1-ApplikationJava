import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

public class Controller {
	private TestChecker testChecker;
	private ViewFrame viewFrame;
	
	
	public Controller(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
		viewFrame.setLitseners(this);
	}
	
	public void CloseThreads() {
		// Close thread that is doing testMethods.
	}
	
	public void RunTests(String className) {
		this.InitTestChecker(className);
		
		Iterable<String> returnStrings=null;
		try {
			// TODO Make tests run on a separate thread
			returnStrings=testChecker.runTestMethods(testChecker.getTestMethods());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		for(String s:returnStrings) {
			// Should be run from EDT?
			viewFrame.addToOutputTextField(s);
			
		}
		
	}
	
	private void InitTestChecker(String className) {
			try {
				testChecker = new TestChecker(className);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotTestClassException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void RunTestButtonPressed(ActionEvent e) {
		String className=viewFrame.getInput();
		if(className == null || className.trim().equals("")) {
			// TODO Make error appear in View
		}else {
			this.RunTests(className);
			// Should be called from EDT?
			viewFrame.switchUpperPanels();
		}
		
	}

	public void CloseThreadButtonPressed(ActionEvent e) {
		// TODO Check if thread is still running, if so close it
		
		// Should be called from EDT?
		viewFrame.switchUpperPanels();
		
	}

}
