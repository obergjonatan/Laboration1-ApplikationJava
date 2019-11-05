import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class Controller {
	private TestChecker testChecker;
	private ViewFrame viewFrame;
	
	
	public Controller(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
	}
	
	public void CloseThreads() {
		// Close thread that is doing testMethods.
	}
	
	public boolean RunTests(String className) {
		if(this.InitTestChecker(className)) {
			
			TestResult testResults=null;
			Collection<String> returnStrings=null;
			try {
				// TODO Make tests run on a separate thread
				testResults=testChecker.runTestMethods(testChecker.getTestMethods());
				returnStrings=testResults.getCollection();
				
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
			viewFrame.clearOutputTextField();
			for(String s:returnStrings) {
				// Should be run from EDT?
				viewFrame.addToOutputTextField(s);
			}
			viewFrame.setSuccessAndFails(testResults.getSuccesses(), testResults.getFails());
			
			return true;
		}
		return false;
		
	}
	
	private boolean InitTestChecker(String className) {
			try {
				testChecker = new TestChecker(className);
			} catch (ClassNotFoundException e) {
				viewFrame.popupError("Class name not Found");
				return false;
			} catch (ClassNotTestClassException e) {
				viewFrame.popupError("Class is not a TestClass");
				return false;
			}
			return true;
	}

	public void RunTestButtonPressed(ActionEvent e) {
		String className=viewFrame.getInput();
		if(className == null || className.trim().equals("")) {
			// TODO Make error appear in View
		}else {
			if(this.RunTests(className)) {
				// Should be called from EDT?
				viewFrame.switchUpperPanels();
			}
		}
		
	}

	public void CloseThreadButtonPressed(ActionEvent e) {
		// TODO Check if thread is still running, if so close it
		
		// Should be called from EDT?
		viewFrame.switchUpperPanels();
		
	}

}
