
/**
 * @author Jonatan
 * Exception to throw when class does not implement
 * a zero argument constructor.
 */
@SuppressWarnings("serial")

public class ClassDoesNotHaveZeroArgConstructor extends Exception {
	/** Constructor for Exception
	 * @param errorMessage errorMessage to be added to Exception.
	 */
	public ClassDoesNotHaveZeroArgConstructor(String errorMessage) {
		super(errorMessage);
	}
}
