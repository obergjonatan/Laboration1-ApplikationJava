
public class Test2 implements TestClass {

	public boolean testSleep100() throws InterruptedException {
		Thread.sleep(100);
		
		return true;
	}
	
	public boolean testSleep1000() throws InterruptedException {
		Thread.sleep(1000);
		
		return true;
	}
	
	public boolean testSleep2000() throws InterruptedException {
		Thread.sleep(2000);
		
		return true;
	}
}