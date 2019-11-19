
/** Exception for when a class does not implement TestClass interface
 * @author Jonatan
 *
 */
@SuppressWarnings("serial")
public class ClassNotTestClassException extends Exception {
	
	/** Constructor for Exception
	 * @param errorMessage message to be added to Exception
	 */
	public ClassNotTestClassException(String errorMessage) {
		super(errorMessage);
	}

}
