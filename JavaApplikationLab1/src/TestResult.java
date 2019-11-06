import java.util.Collection;

public class TestResult {
	boolean result;
	String resultString;

	
	public void setResultString(String s) {
		resultString=s;
	}
	
	public void setResult(boolean result) {
		this.result=result;
	}
	
	public String getResultString(){
		return resultString;
	}

	public boolean getResult() {
		return result;
	}
}
