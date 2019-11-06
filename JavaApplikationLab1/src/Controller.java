import java.awt.AWTEvent;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Controller {
	private TestChecker testChecker;
	private ViewFrame viewFrame;
	private Collection<Method> testMethods;
	private Integer nmbrOfTestMethods;
	private Integer nmbrOfFinishedTests;
	private List<TestCheckSwingWorker> workers;
	private Integer nmbrOfSuccesses=0;
	private Integer nmbrOfFails=0;
	
	
	public Controller(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
	}
	
	public void CloseThreads() {
		for(TestCheckSwingWorker worker:workers) {
			worker.cancel(true);
		}
	}
	
	public boolean RunTests(String className) {
		if(this.InitTestChecker(className)) {
			testMethods=testChecker.getTestMethods();
			nmbrOfTestMethods=testMethods.size();
			viewFrame.setMinMaxProgressBar(0, nmbrOfTestMethods);
			nmbrOfFinishedTests=0;
			viewFrame.updateProgressBar(nmbrOfFinishedTests, nmbrOfTestMethods);
			workers = new ArrayList<TestCheckSwingWorker>(nmbrOfTestMethods);
			int i=0;
			for(Method m:testMethods) {
				workers.add(new TestCheckSwingWorker(m,testChecker.getTestClass(),
						testChecker.getSetUp(),testChecker.getTearDown(),this));
				workers.get(i).execute();
				i++;
			}
			return true;
		}
		return false;
		
	}
	
	private boolean InitTestChecker(String className) {
			try {
				testChecker = new TestChecker(className,this);
			} catch (ClassNotFoundException e) {
				viewFrame.popupError("Class name not Found");
				return false;
			} catch (ClassNotTestClassException e) {
				viewFrame.popupError("Class is not a TestClass");
				return false;
			}
			return true;
	}

	public void RunTestButtonPressed(AWTEvent e) {
		
		this.clear();
		String className=viewFrame.getInput();
		if(className == null || className.trim().equals("")) {
			viewFrame.popupError("You must enter a classname");
		}else {
			if(this.RunTests(className)) {
				// Should be called from EDT?
				viewFrame.switchUpperPanels();
			}
		}
		
	}
	

	public void CloseThreadButtonPressed(AWTEvent e) {
		this.CloseThreads();
		this.clear();
		viewFrame.addToOutputTextField("Tests were Cancelled",null);
		// Should be called from EDT?
		viewFrame.switchUpperPanels();
		
	}
	
	public void clear() {
		viewFrame.clearOutputTextField();
		viewFrame.clearSuccesOrFailTextField();
		nmbrOfFinishedTests=0;
		nmbrOfSuccesses=0;
		nmbrOfFails=0;
	}

	public void updateOutput(TestResult testResult) {
		nmbrOfFinishedTests++;
		if(testResult.getResult()) {
			nmbrOfSuccesses++;
		}else {
			nmbrOfFails++;
		}
		viewFrame.addToOutputTextField(testResult.getMethodName()+" :",null);
		String testResultString = (testResult.getResult() ? " PASSED!" : " FAILED!");
		StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, testResult.getResult() ? Color.green : Color.red);
		viewFrame.addToOutputTextField(testResultString,aset);
		if(testResult.getException()!=null) {
			viewFrame.addToOutputTextField(" Because of exception :"+testResult.getException().toString()+ "\n", null);
		}else {
			viewFrame.addToOutputTextField("\n", null);
		}
		if(nmbrOfFinishedTests<nmbrOfTestMethods) {
			viewFrame.updateProgressBar(nmbrOfFinishedTests,nmbrOfTestMethods);	
		}else {
			viewFrame.setSuccessAndFails(nmbrOfSuccesses, nmbrOfFails);
			viewFrame.switchUpperPanels();
			
		}
		
		
	}

}
