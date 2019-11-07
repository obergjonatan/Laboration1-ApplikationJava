import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class MyDocFilter extends DocumentFilter{
	String hint;
	HintTextField hintTextField;
	
	public MyDocFilter(HintTextField hintTextField) {
		
		hint=GlobalVariables.hintMessage;
		this.hintTextField=hintTextField;
	}
	

	
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
		if(hint.equals(fb.getDocument().getText(0, fb.getDocument().getLength()))){
			hintTextField.setForeground(null);
			hintTextField.setShowingHint(false);
			super.replace(fb,0,fb.getDocument().getLength(),text,attr);
		}else {
			super.replace(fb,offset,length,text,attr);
		}
	}
}