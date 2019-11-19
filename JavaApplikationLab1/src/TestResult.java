

/** Datastructure for test results 
 * @author Jonatan
 *
 */
public class TestResult {
	
	private boolean result;
	private String methodName;
	private Throwable exceptionThrown;
	
	/**
	 * @param s name of method for which result is being saved
	 */
	public void setMethodName(String s) {
		methodName=s;
	}
	
	/**
	 * @param result result of test in form of boolean
	 */
	public void setResult(boolean result) {
		this.result=result;
	}
	
	/**
	 * @return name of method for which result has been saved
	 */
	public String getMethodName(){
		return methodName;
	}

	/**
	 * @return result of test
	 */
	public boolean getResult() {
		
		return result;
	}
	/**
	 * @param e exception that was thrown when test ran
	 */
	public void setExceptionThrown(Throwable e) {
		exceptionThrown=e;
	}
	
	/**
	 * @return exception that was thrown when test ran
	 */
	public Throwable getException() {
		return exceptionThrown;
	}
}
