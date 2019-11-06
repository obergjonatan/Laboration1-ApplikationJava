

public class TestResult {
	boolean result;
	String methodName;
	Throwable exceptionThrown;
	
	public void setMethodName(String s) {
		methodName=s;
	}
	
	public void setResult(boolean result) {
		this.result=result;
	}
	
	public String getMethodName(){
		return methodName;
	}

	public boolean getResult() {
		return result;
	}
	public void setExceptionThrown(Throwable e) {
		exceptionThrown=e;
	}
	
	public Throwable getException() {
		return exceptionThrown;
	}
}
