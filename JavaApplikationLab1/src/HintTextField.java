import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;


public class HintTextField extends JTextField implements FocusListener {
	private String hint;
	private boolean showingHint;
	public HintTextField(String hint) {
		super(hint);
		this.setForeground(Color.LIGHT_GRAY);
		this.hint=hint;
		super.addFocusListener(this);
		this.showingHint=true;
		
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			this.setForeground(null);
			this.setText("");
			showingHint=false;
			
		}
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			this.setText(hint);
			this.setForeground(Color.LIGHT_GRAY);
			showingHint=true;
		}
		
	}
	@Override
	public String getText(){
		if(showingHint) {
			return "";
		}else {
			return super.getText();
		}
		
	}
}
