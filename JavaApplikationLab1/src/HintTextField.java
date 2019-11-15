import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;


/** JTextField that allows the text field to show a hint
 *  Allows text field to present a hint when no input to textfield
 *  has been given. Hint disappears when user inputs anything 
 *  to the textfield. If text field loses focus and has no text 
 *  in it hint is shown again. 
 * @author Jonatan
 *
 */
public class HintTextField extends JTextField implements FocusListener {
	
	private String hint;
	private boolean showingHint;
	public HintTextField(String hint) {
		super(GlobalVariables.hintMessage);
		this.setForeground(Color.LIGHT_GRAY);
		this.hint=hint;
		super.addFocusListener(this);
		this.showingHint=true;
		((AbstractDocument) this.getDocument()).setDocumentFilter(
				new MyDocFilter(this));
	}

	/** Updates textfield to show hint if textfield is empty
	 *
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			this.setText(hint);
			this.setForeground(Color.LIGHT_GRAY);
			showingHint=true;
		}
		
	}
	/** Returns text in textfield, returns empty string if hint is showing
	 * @return String of textfield, string is empty if hint is showing
	 */
	@Override
	public String getText(){
		if(showingHint) {
			return "";
		}else {
			return super.getText();
		}
	}
		
	/** sets boolean showinghint
	 * @param b what showinghint should be set to. 
	 */
	public void setShowingHint(boolean b) {
		this.showingHint=b;
	}

	/** Does nothing. 
	 *
	 */
	@Override
	public void focusGained(FocusEvent e) {
		
	}
		
		
}
	
	

