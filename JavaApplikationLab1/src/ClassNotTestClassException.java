
/** Exception for when a class doesnt implement TestClass interface
 * @author Jonatan
 *
 */
public class ClassNotTestClassException extends Exception {
	


	/** Constructor for Exception
	 * @param errorMessage message to be added to Exception
	 */
	public ClassNotTestClassException(String errorMessage) {
		super(errorMessage);
	}

}
