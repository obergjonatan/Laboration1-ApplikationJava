import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class TestChecker extends SwingWorker<TestResult, Integer> {
	private Class<?> testClass;
	private Method setUp;
	private Method tearDown;
	private Stack<Method> testMethods = new Stack<Method>();
	private Integer currentTestMethod=0;
	private Stack<TestResult> testResults = new Stack<TestResult>();
	
	public TestChecker(String className) throws ClassNotFoundException, ClassNotTestClassException {
		testClass = Class.forName(className);
		if(testClass.isAssignableFrom(TestClass.class)) {
			throw new ClassNotTestClassException("Class must implement interface TestClass");
		}
		
	}
	
	
	public Iterable<Method> getTestMethods() {
		Method allMethods[] = testClass.getMethods();
		for(Method m:allMethods) {
			String methodName=m.getName();
			if(methodName.equals("setUp")) {
				setUp=m;
			}else if(methodName.equals("tearDown")) {
				tearDown=m;
			}else if(methodName.substring(0,4).equals("test")) {
				testMethods.add(m);
			}
			
		}
		return testMethods;
		
	}
	
	public void runTestMethods() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			
			for(int i=0; i<testMethods.size();i++) {
				currentTestMethod=i;
				this.execute();
			}
		
		/*
			Object obj = testClass.getDeclaredConstructor().newInstance();
			Stack<String> testOutputStack = new Stack<String>();
			TestResult testResult = new TestResult(testOutputStack);
			
			
			for(Method m:testMethods) {
				if(setUp!=null) {
					setUp.invoke(obj);
				}
				Boolean methodBool;
				try {
					methodBool = (Boolean)m.invoke(obj);
					if(methodBool) {
						String testOutput = ""+ m.getName()+":SUCCEEDED \n";
						testResult.addToCollection(testOutput);
						testResult.incrementSuccesses();
					}else  {
						String testOutput = ""+m.getName() +": FAILED \n";
						testResult.addToCollection(testOutput);
						testResult.incrementFails();
					} 
				} catch (IllegalAccessException e) {
					String testOutput = ""+m.getName()+": FAILED! Threw IlleagalAccessException \n";
					testResult.addToCollection(testOutput);
					testResult.incrementFails();
				} catch (IllegalArgumentException e) {
					String testOutput = ""+m.getName()+": FAILED! Threw IllegalArgumentException \n";
					testResult.addToCollection(testOutput);
					testResult.incrementFails();
				} catch (InvocationTargetException e) {
					String testOutput = ""+m.getName()+": FAILED! Threw "+e.getTargetException().toString() +"\n";
					testResult.addToCollection(testOutput);
					testResult.incrementFails();
				}
				if(tearDown!=null) {
					tearDown.invoke(obj);
				}	
				
			}
			
		return testResult; */
	}


	@Override
	protected TestResult doInBackground() throws Exception {
		Object obj = testClass.getDeclaredConstructor().newInstance();
		Stack<String> testOutputStack = new Stack<String>();
		TestResult testResult = new TestResult(testOutputStack);
		Method m = testMethods.pop();
		
		if(setUp!=null) {
			setUp.invoke(obj);	
		}
		Boolean methodBool;
		try {
			methodBool = (Boolean)m.invoke(obj);
			if(methodBool) {
				String testOutput = ""+ m.getName()+":SUCCEEDED \n";
				testResult.addToCollection(testOutput);
				testResult.incrementSuccesses();
			}else  {
				String testOutput = ""+m.getName() +": FAILED \n";
				testResult.addToCollection(testOutput);
				testResult.incrementFails();
			} 
		} catch (IllegalAccessException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw IlleagalAccessException \n";
			testResult.addToCollection(testOutput);
			testResult.incrementFails();
		} catch (IllegalArgumentException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw IllegalArgumentException \n";
			testResult.addToCollection(testOutput);
			testResult.incrementFails();
		} catch (InvocationTargetException e) {
			String testOutput = ""+m.getName()+": FAILED! Threw "+e.getTargetException().toString() +"\n";
			testResult.addToCollection(testOutput);
			testResult.incrementFails();
		}
		if(tearDown!=null) {
			tearDown.invoke(obj);
		}	
		return testResult;
	}
	
	@Override
	protected void done() {
		try {
			testResults.add(get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
