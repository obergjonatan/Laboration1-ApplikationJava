


import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

/** JUnit tests of TestChecker
 * @author Jonatan
 *
 */
class TestCheckerUniTests {
	
	ClassLoader classLoader= ClassLoader.getSystemClassLoader();
	
	@Test 
	void testExceptions(){
		assertThrows(ClassNotFoundException.class,() -> {
			@SuppressWarnings("unused")
			TestChecker testChecker1 = new TestChecker(
					"Not A Class",classLoader);
		});
		assertThrows(ClassNotTestClassException.class,() -> {
			@SuppressWarnings("unused")
			TestChecker testChecker1 = new TestChecker(
					"NotATestClass",classLoader);
		});
		assertThrows(ClassDoesNotHaveZeroArgConstructor.class,() -> {
			@SuppressWarnings("unused")
			TestChecker testChecker2 = new TestChecker(
					"NoZeroArgConstructorTestClass",classLoader);
		});
	}
	
		
	
	

	@Test
	void testGetTestMethods() throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, ClassNotTestClassException,
			ClassDoesNotHaveZeroArgConstructor {
		Test1 test1 = new Test1();
		ArrayList<Method> test1Methods = new ArrayList<>();
		test1Methods.add(test1.getClass().getMethod("testInitialisation"));
		test1Methods.add(test1.getClass().getMethod("testIncrement"));
		test1Methods.add(test1.getClass().getMethod("testDecrement"));
		test1Methods.add(test1.getClass().getMethod("testFailingByException"));
		test1Methods.add(test1.getClass().getMethod("testFailing"));
		test1Methods.add(test1.getClass().getMethod("testSleep"));
		test1Methods.add(test1.getClass().getMethod("testSleep2"));
		test1Methods.add(test1.getClass().getMethod("testSleep3"));
		TestChecker testChecker = new TestChecker("Test1",classLoader);
		Collection<Method> testCheckerTestMethods=testChecker.getTestMethods();
		for(Method m:test1Methods) {
			assertTrue(testCheckerTestMethods.contains(m),
					"Should contain testMethods");
		}
		assertTrue(test1Methods.size()==testCheckerTestMethods.size(),
				"Size should be equal");
		
	
		
	}

	@Test
	void testGetSetUp() throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, ClassNotTestClassException,
			ClassDoesNotHaveZeroArgConstructor {
		Test1 test1 = new Test1();
		Method test1SetUp = test1.getClass().getMethod("setUp");
		TestChecker testChecker = new TestChecker("Test1",classLoader);
		assertTrue(test1SetUp.equals(testChecker.getSetUp()),
				"setUp methods should be equal");
	}

	@Test
	void testGetTearDown() throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, ClassNotTestClassException,
			ClassDoesNotHaveZeroArgConstructor {
		Test1 test1 = new Test1();
		Method test1SetUp = test1.getClass().getMethod("tearDown");
		TestChecker testChecker = new TestChecker("Test1",classLoader);
		assertTrue(test1SetUp.equals(testChecker.getTearDown()),
				"setUp methods should be equal");
	}

	@Test
	void testGetTestClass() throws ClassNotFoundException, 
			ClassNotTestClassException, ClassDoesNotHaveZeroArgConstructor {	
		Test1 test1 = new Test1();
		TestChecker testChecker = new TestChecker("Test1",classLoader);
		assertTrue(test1.getClass().equals(testChecker.getTestClass()));
		
	}

}
