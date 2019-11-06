
public class Test2 implements TestClass {

	public boolean testSleep1000() throws InterruptedException {
		Thread.sleep(1000);
		
		return true;
	}
	
	public boolean testSleep2000() throws InterruptedException {
		Thread.sleep(2000);
		
		return true;
	}
	
	public boolean testSleep3000() throws InterruptedException {
		Thread.sleep(3000);
		
		return true;
	}
}
