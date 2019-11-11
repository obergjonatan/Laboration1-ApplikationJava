import java.awt.AWTEvent;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Semaphore;

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
	private ArrayList<String> publishedMethodNames=new ArrayList<>();
	public  Semaphore runPermit= new Semaphore(1);
	public boolean runInOrder;
	
	
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
			Stack<Method> testMethodSingles;
			int i=0;
			if(runInOrder) {
				workers.add(new TestCheckSwingWorker(testMethods,testChecker.getTestClass(),
						testChecker.getSetUp(),testChecker.getTearDown(),this));
				workers.get(i).execute();	
			}else{
				for(Method m:testMethods) {
					testMethodSingles=new Stack<>();
					testMethodSingles.add(m);
					workers.add(new TestCheckSwingWorker(testMethodSingles,testChecker.getTestClass(),
							testChecker.getSetUp(),testChecker.getTearDown(),this));
					workers.get(i).execute();	
					i++;
				}
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

	public void RunTestButtonPressed(AWTEvent e,boolean runInOrder) {
		this.clear();
		this.runInOrder=runInOrder;
		String className=viewFrame.getInput();
		System.out.println(className);
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
		publishedMethodNames=new ArrayList<String>();
	}

	public void updateOutput(Collection<TestResult> testResults) {
		for(TestResult tr:testResults){
			if(!publishedMethodNames.contains(tr.getMethodName())){
				publishedMethodNames.add(tr.getMethodName());
				nmbrOfFinishedTests++;
				if(tr.getResult()) {
					nmbrOfSuccesses++;
				}else {
					nmbrOfFails++;
				}
				viewFrame.addToOutputTextField(tr.getMethodName()+" :",null);
				String testResultString = (tr.getResult() ? " PASSED!" : " FAILED!");
				StyleContext sc = StyleContext.getDefaultStyleContext();
		        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, tr.getResult() ? Color.green : Color.red);
				viewFrame.addToOutputTextField(testResultString,aset);
				if(tr.getException()!=null) {
					viewFrame.addToOutputTextField(" Because of exception :"+tr.getException().toString()+ "\n", null);
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
		
	}

}
