import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/** DocumentFilter used to assist HintTextField class
 * @author Jonatan
 *
 */
public class HintTextFieldDocFilter extends DocumentFilter{
	
	String hint;
	HintTextField hintTextField;
	
	/**
	 * @param hintTextField hintTextField that is being assisted
	 */
	public HintTextFieldDocFilter(HintTextField hintTextField) {
		this.hintTextField=hintTextField;
		this.hint=hintTextField.getHint();
	}
	

	
	/** Removes hint if input is given
	 *
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length,
						String text, AttributeSet attr)
								throws BadLocationException {
		if(hint.equals(
				fb.getDocument().getText(0, fb.getDocument().getLength()))){
			hintTextField.setForeground(null);
			hintTextField.setShowingHint(false);
			super.replace(fb,0,fb.getDocument().getLength(),text,attr);
		}else {
			super.replace(fb,offset,length,text,attr);
		}
	}
}