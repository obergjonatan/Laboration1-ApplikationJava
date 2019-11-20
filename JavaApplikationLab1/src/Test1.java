
public class Test1 implements TestClass {
	private MyInt myInt;
	
	public Test1() {
	}

	public void setUp() {
	   myInt=new MyInt();
	}

	public void tearDown() {
	  myInt=null;
	}

	//Test that should succeed
	public boolean testInitialisation() {
	   return myInt.value()==0;
	}

	//Test that should succeed
	public boolean testIncrement() {
	   myInt.increment();
	   myInt.increment();
	   return myInt.value()==2;

	}
	
	public boolean testSleep() throws InterruptedException {
			Thread.sleep(10000);
		return true;
		
	}
	
	public boolean testSleep2() throws InterruptedException {
			Thread.sleep(10000);
		return true;
	}
	
	public boolean testSleep3() throws InterruptedException {
			Thread.sleep(10000);
		
		return true;
	}
	
	//Test that should succeed
	public boolean testDecrement() {
		   myInt.increment();
		   myInt.decrement();
		   return myInt.value()==0;
	}
	
	//Test that should fail
	public boolean testFailingByException() {
		myInt=null;
		myInt.decrement();
		return true;
		
	}
	
	
	//Test that should fail
	public boolean testFailing() {
		return false;
		
	}
	
	public void testVoidMethod() {
		
	}
	
	public boolean testParameterMethod(String s) {
		return true;
	}


}