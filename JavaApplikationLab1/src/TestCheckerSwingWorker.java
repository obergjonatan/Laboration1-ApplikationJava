import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import javax.swing.SwingWorker;

/** SwingWorker that executes test methods of test class
 * @author Jonatan
 *
 */
public class TestCheckerSwingWorker extends SwingWorker<Collection<TestResult>,
													  TestResult> {

	private Iterable<Method> testMethod;
	private Class<?> testClass;
	Method setUp;
	Method tearDown;
	TestUnitController testUnitController;
	Boolean runInOrder;
	/**
	 * @param methods methods to be executed
	 * @param testClass class from which to execute methods
	 * @param setUp method to be called before each test
	 * @param tearDown method to be called after each test
	 * @param testUnitController controller to report TestResults back to
	 */
	public TestCheckerSwingWorker(Iterable<Method> methods,Class<?> testClass,
			Method setUp,Method tearDown,TestUnitController testUnitController) {
		this.testMethod=methods;
		this.testClass=testClass;
		this.setUp=setUp;
		this.tearDown=tearDown;
		this.testUnitController=testUnitController;
		
		
	}
	/** Executes test methods and creates TestResult for each test
	 * publishes TestResult after they've been generated.
	 */
	@Override
	protected Collection<TestResult> doInBackground() 
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Object obj = testClass.getDeclaredConstructor().newInstance();
		Stack<TestResult> testResults = new Stack<>();
		for(Method m:testMethod) {
			
			TestResult testResult= new TestResult();
			if(setUp!=null) {
				setUp.invoke(obj);	
			}
			Boolean methodBool;
			try {
				testResult.setMethodName(m.getName());
				methodBool = (Boolean)m.invoke(obj);
				testResult.setResult(methodBool);
			} catch (IllegalAccessException e) {
				testResult.setResult(false);
				testResult.setExceptionThrown(e);
			} catch (IllegalArgumentException e) {
				testResult.setResult(false);
				testResult.setExceptionThrown(e);
			} catch (InvocationTargetException e) { 
				testResult.setResult(false);
				testResult.setExceptionThrown(e.getTargetException());
			}
			if(tearDown!=null) {
				tearDown.invoke(obj);
			}	
			publish(testResult);
			testResults.add(testResult);
		}
		return testResults;
	}
	
	@Override
	
	protected void done() {

	}
	
	/** sends List of TestResults to controller
	 *
	 */
	@Override protected void process(List<TestResult> testResults) {
		if(!this.isCancelled()) {
			testUnitController.updateOutput(testResults);
		}
	}

}
