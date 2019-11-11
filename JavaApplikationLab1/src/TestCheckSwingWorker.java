import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class TestCheckSwingWorker extends SwingWorker<Collection<TestResult>, TestResult> {

	Iterable<Method> testMethod;
	Class<?> testClass;
	Method setUp;
	Method tearDown;
	Controller controller;
	Boolean runInOrder;
	public TestCheckSwingWorker(Iterable<Method> methods,Class<?> c,Method setUp,Method tearDown,Controller controller) {
		this.testMethod=methods;
		this.testClass=c;
		this.setUp=setUp;
		this.tearDown=tearDown;
		this.controller=controller;
		
		
	}
	@Override
	protected Collection<TestResult> doInBackground() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object obj = testClass.getDeclaredConstructor().newInstance();
		Stack<TestResult> testResults = new Stack();
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
		try {
			controller.updateOutput(get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (CancellationException e) {
			
		}
	}
	
	@Override protected void process(List<TestResult> testResults) {
		controller.updateOutput(testResults);
	}

}
