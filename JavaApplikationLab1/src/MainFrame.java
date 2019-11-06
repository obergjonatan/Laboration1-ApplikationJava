import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class MainFrame {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				 ViewFrame gui = new ViewFrame("Namn");
				 gui.show();
				 Controller controller = new Controller(gui);
				 gui.setLitseners(controller);
			}
		});

	}

}
