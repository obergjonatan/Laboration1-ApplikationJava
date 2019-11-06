import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
			nmbrOfFinishedTests=0;
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

	public void RunTestButtonPressed(ActionEvent e) {
		
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
	

	public void CloseThreadButtonPressed(ActionEvent e) {
		// TODO Check if thread is still running, if so close it
		this.CloseThreads();
		this.clear();
		viewFrame.addToOutputTextField("Tests were Cancelled");
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
		viewFrame.addToOutputTextField(testResult.getResultString());
		if(nmbrOfFinishedTests<nmbrOfTestMethods) {
			viewFrame.updateProgressBar(nmbrOfFinishedTests);	
		}else {
			viewFrame.setSuccessAndFails(nmbrOfSuccesses, nmbrOfFails);
			viewFrame.switchUpperPanels();
			viewFrame.updateProgressBar(0);
			
		}
		
		
	}

}
