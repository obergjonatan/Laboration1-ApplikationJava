import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class TestCheckSwingWorker extends SwingWorker<TestResult, Integer> {

	Method testMethod;
	Class<?> testClass;
	Method setUp;
	Method tearDown;
	Controller controller;
	public TestCheckSwingWorker(Method m,Class<?> c,Method setUp,Method tearDown,Controller controller) {
		this.testMethod=m;
		this.testClass=c;
		this.setUp=setUp;
		this.tearDown=tearDown;
		this.controller=controller;
		
		
	}
	@Override
	protected TestResult doInBackground() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object obj = testClass.getDeclaredConstructor().newInstance();
		TestResult testResult = new TestResult();
		Method m = testMethod;
		
		if(setUp!=null) {
			setUp.invoke(obj);	
		}
		Boolean methodBool;
		try {
			methodBool = (Boolean)m.invoke(obj);
			if(methodBool) {
				String testOutput = ""+ m.getName()+":SUCCEEDED \n";
				testResult.setResultString(testOutput);
				testResult.setResult(methodBool);
			}else  {
				String testOutput = ""+m.getName() +": FAILED \n";
				testResult.setResultString(testOutput);
				testResult.setResult(methodBool);
			} 
		} catch (IllegalAccessException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw IlleagalAccessException \n";
			testResult.setResultString(testOutput);
			testResult.setResult(false);
		} catch (IllegalArgumentException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw IllegalArgumentException \n";
			testResult.setResultString(testOutput);
			testResult.setResult(false);
		} catch (InvocationTargetException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw "+e.getTargetException().toString() +"\n";
			testResult.setResultString(testOutput);
			testResult.setResult(false);
		}
		if(tearDown!=null) {
			tearDown.invoke(obj);
		}	
		return testResult;
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

}
