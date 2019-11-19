import java.awt.AWTEvent;
import java.awt.Color;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/** Part Controller part model class for UnitTester program.
 * Take cares of communication between TestUnitViewFrame and
 * TestChecker and TestCheckerSwingWorker. 
 * @author Jonatan
 *
 */
public class TestUnitController {
	
	private boolean runInOrder;
	private TestChecker testChecker;
	private TestUnitViewFrame testUnitViewFrame;
	private Collection<Method> testMethods;
	private Integer nmbrOfTestMethods;
	private Integer nmbrOfFinishedTests;
	private List<TestCheckerSwingWorker> workers;
	private Integer nmbrOfSuccesses=0;
	private Integer nmbrOfFails=0;
	private Integer nmbrOfExceptionFails=0;
	private URLClassLoader classLoader;
	private URLClassLoader standardClassLoader;
	
	
	/**
	 * @param testUnitViewFrame gui of TestUnit program
	 */
	public TestUnitController(TestUnitViewFrame testUnitViewFrame) {
		
		this.testUnitViewFrame=testUnitViewFrame;
		File dir = new File(".");
		  try {
			standardClassLoader = URLClassLoader.newInstance(
					  new URL[] { dir.toURI().toURL() });
			this.classLoader=standardClassLoader;
		  } catch (MalformedURLException e) {
			testUnitViewFrame.displayPopupError(e.getMessage());
		  }
		
	}
	
	/** cancels all TestCheckerSwingWorkers
	 * 
	 */
	public void CloseThreads() {
		for(TestCheckerSwingWorker worker:workers) {
			worker.cancel(true);
		}
	}
	
	/** Creates Swingworkers to run tests
	 * @param className name of Class to be tested
	 * @return true if tests where initiated false if they could not start
	 */
	private boolean RunTests(String className) {
		if(this.InitTestChecker(className)) {
			testMethods=testChecker.getTestMethods();
			nmbrOfTestMethods=testMethods.size();
			testUnitViewFrame.setMinMaxProgressBar(0, nmbrOfTestMethods);
			nmbrOfFinishedTests=0;
			testUnitViewFrame.updateProgressBar(nmbrOfFinishedTests, nmbrOfTestMethods);
			workers = new ArrayList<TestCheckerSwingWorker>(nmbrOfTestMethods);
			Stack<Method> testMethodSingles;
			int i=0;
			if(runInOrder) {
				workers.add(new TestCheckerSwingWorker(testMethods,
						testChecker.getTestClass(),testChecker.getSetUp(),
						testChecker.getTearDown(),this));
				workers.get(i).execute();	
			}else{
				for(Method m:testMethods) {
					testMethodSingles=new Stack<>();
					testMethodSingles.add(m);
					workers.add(new TestCheckerSwingWorker(testMethodSingles,
							testChecker.getTestClass(),testChecker.getSetUp(),
							testChecker.getTearDown(),this));
					workers.get(i).execute();	
					i++;
				}
			}
			return true;
		}
		return false;
	}
		

	
	/** creates TestChecker 
	 * @param className name of class to be created within TestChecker
	 * @return true if TestChecker successfully created false otherwise
	 */
	private boolean InitTestChecker(String className) {
			try {
				testChecker = new TestChecker(className,classLoader);
			} catch (ClassNotFoundException e) {
				testUnitViewFrame.displayPopupError("Class name not Found");
				return false;
			} catch (ClassNotTestClassException e) {
				testUnitViewFrame.displayPopupError("Class is not a TestClass");
				return false;
			} catch (ClassDoesNotHaveZeroArgConstructor e) {
				testUnitViewFrame.displayPopupError("Class needs to have constructor "
											 + "with zero arguments");
				
			}
			return true;
	}

	/** Method to be called from View if tests should be run
	 * @param e event that called function
	 * @param runInOrder whether tests should be ran sequentially or not
	 */
	public void RunTestButtonPressed(AWTEvent e,boolean runInOrder) {
		this.clear();
		this.runInOrder=runInOrder;
		String className=testUnitViewFrame.getInput();
		if(className == null || className.trim().equals("")) {
			testUnitViewFrame.displayPopupError("You must enter a classname");
		}else {
			if(this.RunTests(className)) {
				// Should be called from EDT?
				testUnitViewFrame.switchUpperPanels();
			}
		}
		
	}
	

	/** Method called from view when tests should be cancelled
	 * @param e Event which called function
	 */
	public void CloseThreadButtonPressed(AWTEvent e) {
		this.CloseThreads();
		this.clear();
		testUnitViewFrame.appendToTestOutput("Tests were Cancelled",null);
		// Should be called from EDT?
		testUnitViewFrame.switchUpperPanels();
		
	}
	
	/** Clears TestUnitViewFrame of testoutput and testsummary
	 * resets all counts of tests in controller.
	 * 
	 */
	private void clear() {
		testUnitViewFrame.clearTestOutput();
		testUnitViewFrame.clearTestSummary();
		nmbrOfFinishedTests=0;
		nmbrOfSuccesses=0;
		nmbrOfFails=0;
		nmbrOfExceptionFails=0;
	}
 
	/** Updates View with TestResults gathered from SwingWorkerws
	 * @param testResults results which should be presented in view
	 */
	public void updateOutput(Collection<TestResult> testResults) {
		for(TestResult tr:testResults){
				nmbrOfFinishedTests++;
			if(tr.getResult()) {
				nmbrOfSuccesses++;
			}else if(tr.getException()==null){
				nmbrOfFails++;
			}else {
				nmbrOfExceptionFails++;
			}
			testUnitViewFrame.appendToTestOutput(tr.getMethodName()+" :",null);
			String testResultString = 
					(tr.getResult() ? " PASSED!" : " FAILED!");
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
		        	StyleConstants.Foreground,
		        	tr.getResult() ? Color.green : Color.red);
			testUnitViewFrame.appendToTestOutput(testResultString,aset);
			if(tr.getException()!=null) {
				testUnitViewFrame.appendToTestOutput(" Because of exception :"
							+tr.getException().toString()+ "\n", null);
			}else {
				testUnitViewFrame.appendToTestOutput("\n", null);
			}
				if(nmbrOfFinishedTests<nmbrOfTestMethods) {	
					testUnitViewFrame.updateProgressBar(nmbrOfFinishedTests,
														nmbrOfTestMethods);	
			}else{
				testUnitViewFrame.updateTestSummary(nmbrOfSuccesses,
						nmbrOfFails,nmbrOfExceptionFails);
				testUnitViewFrame.switchUpperPanels();				
			}
		}
	}

	/** Change class path from where test classes are loaded
	 * @param classPath class path from where classes should be loaded
	 */
	public void setClassPath(String classPath) {
		if(classPath==null) {
			
		}
		else if(classPath.equals("")) {
			 classLoader=standardClassLoader; 
		}else {
			File dir= new File(classPath);
			try {
				classLoader= URLClassLoader.newInstance(
						  new URL[] { dir.toURI().toURL() });
			} catch (MalformedURLException e) {
				testUnitViewFrame.displayPopupError(e.getMessage());
			}
		}
		
		
	}
		
	
}
