import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

public class TestChecker {
	private Class<?> testClass;
	
	public TestChecker(String className) throws ClassNotFoundException, ClassNotTestClassException {
		testClass = Class.forName(className);
		if(testClass.isAssignableFrom(TestClass.class)) {
			throw new ClassNotTestClassException("Class must implement interface TestClass");
		}
		
	}	
	public Iterable<Method> getTestMethods() {
		Stack<Method> testMethods = new Stack<Method>();
		Method allMethods[] = testClass.getClass().getDeclaredMethods();
		for(Method m:allMethods) {
			String methodName=m.getName();
			if(methodName.substring(0,4).equals("test")) {
				testMethods.add(m);
			}
			
		}
		return testMethods;
		
	}
	
	public Iterable<String> runTestMethods(Iterable<Method> testMethods) throws
		IllegalAccessException, IllegalArgumentException, InvocationTargetException,
		InstantiationException, NoSuchMethodException, SecurityException {
			Object obj = testClass.getDeclaredConstructor().newInstance();
			Stack<String> testOutputStack = new Stack<String>();
			for(Method m:testMethods) {
				Boolean methodBool = (Boolean)m.invoke(obj);
				String testOutput = ""+methodBool+" "+m.getName();
				testOutputStack.add(testOutput);
				
			}
		return testOutputStack;
	}

}
