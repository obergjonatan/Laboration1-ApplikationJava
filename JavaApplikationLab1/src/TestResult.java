import java.util.Collection;

public class TestResult {
	int numberOfSuccesses=0;
	int numberOfFails=0;
	Collection<String> resultStringCollection;
	public TestResult(Collection<String> resultString) {
		this.resultStringCollection=resultString;
	}
	
	public void addToCollection(String s)  {
		resultStringCollection.add(s);
	}
	public void incrementSuccesses() {
		numberOfSuccesses++;
	}
	
	public void incrementFails() {
		numberOfFails++;
	}
	
	public Collection<String> getCollection(){
		return resultStringCollection;
	}

	public int getSuccesses() {
		return numberOfSuccesses;
	}
	
	public int getFails() {
		return numberOfFails;
	}
}
