import javax.swing.SwingUtilities;


public class MainFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				 ViewFrame gui = new ViewFrame("Namn");
				 gui.show();
			}
		});

	}

}
