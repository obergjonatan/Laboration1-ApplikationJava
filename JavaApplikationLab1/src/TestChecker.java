import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Stack;

/** Class to generate Test Class and its  test methods
 * @author Jonatan
 *
 */
public class TestChecker {
	
	private Class<?> testClass;
	private Method setUp;
	private Method tearDown;
	private Stack<Method> testMethods = new Stack<Method>();
	

	/** Creates a TestChecker. 
	 * @param className name of Class to be created
	 * @param classLoader URLClassLoader which is used to create class
	 * @throws ClassNotFoundException exception thrown when no 
	 * class with name className could be found in class path
	 * @throws ClassNotTestClassException exception thrown when
	 * created class does not implement interface TestClass
	 * @throws ClassDoesNotHaveZeroArgConstructor exception thrown
	 * when created class does not have a zero argument constructor
	 */
	public TestChecker(String className,ClassLoader classLoader) 
			throws ClassNotFoundException, ClassNotTestClassException, 
				   ClassDoesNotHaveZeroArgConstructor {
		boolean hasZeroArgConstructor=false;
		testClass = Class.forName(className,true,classLoader);
		if(!TestClass.class.isAssignableFrom(testClass)) {
			throw new ClassNotTestClassException("Doesnt implement TestClass");
		}
		Constructor<?>[] constructors = testClass.getConstructors();
		for(Constructor<?> c:constructors) {
			if(c.getParameterCount()==0) {
				hasZeroArgConstructor=true;
			}
		}
		if(!hasZeroArgConstructor) {
			throw new ClassDoesNotHaveZeroArgConstructor("Class needs to have "
														 + "constructor with 0 "
														 + "arguments");
		}
		initTestChecker();
		
		
	}
	
	/** Gathers all test methods from created class
	 * also gathers setUp and tearDown methods.
	 * 
	 */
	private void initTestChecker() {
		Method allMethods[] = testClass.getMethods();
		for(Method m:allMethods) {
			String methodName=m.getName();
			if(methodName.equals("setUp")) {
				setUp=m;
			}else if(methodName.equals("tearDown")) {
				tearDown=m;
			}else if(methodName.substring(0,4).equals("test")) {
				if(m.getReturnType().equals(Boolean.TYPE) 
						&& m.getParameterCount()==0) {
					testMethods.add(m);
				}
			}
			
		}
		
	}
	
	/**
	 * @return test methods of test class if it has any
	 */
	public Collection<Method> getTestMethods() {
		return testMethods;
	}
	
	
	/**
	 * @return setUp method of test class if it has one
	 */
	public Method getSetUp() {
		return setUp;
	}
	
	/**
	 * @return tearDown method if test class has one
	 */
	public Method getTearDown() {
		return tearDown;
	}
	/**
	 * @return test class
	 */
	public Class<?> getTestClass() {
		return testClass;
	}

	
}
