import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Stack;

public class TestChecker {
	private Class<?> testClass;
	private Method setUp;
	private Method tearDown;
	private Stack<Method> testMethods = new Stack<Method>();
	
	public TestChecker(String className,Controller controller) throws ClassNotFoundException, ClassNotTestClassException {
		testClass = Class.forName(className);
		if(!TestClass.class.isAssignableFrom(testClass)) {
			throw new ClassNotTestClassException("Class must implement interface TestClass");
		}
		
	}
	
	
	public Collection<Method> getTestMethods() {
		Method allMethods[] = testClass.getMethods();
		for(Method m:allMethods) {
			String methodName=m.getName();
			System.out.println(methodName);
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
	
	public Method getSetUp() {
		return setUp;
	}
	
	public Method getTearDown() {
		return tearDown;
		
	}

	
	public Class<?> getTestClass() {
		return testClass;
	}

	
}
