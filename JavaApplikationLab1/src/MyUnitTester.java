import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


/** Main class of UnitTester program.
 * @author Jonatan
 *
 */
public class MyUnitTester {

	/** Constructs and connects TestUnitViewFrame and TestUnitController
	 * @param args
	 */
	public static void main(String[] args) {
			
		SwingUtilities.invokeLater(new Runnable() {
			
			
			@Override
			public void run() {
				 UIManager.put( "control", new Color( 64, 64, 64) );
				  UIManager.put( "info", new Color(64,64,64) );
				  UIManager.put( "nimbusBase", new Color( 10, 25, 20) );
				  UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
				  UIManager.put( "nimbusDisabledText", new Color( 64, 64, 64) );
				  UIManager.put( "nimbusFocus", new Color(115,164,209) );
				  UIManager.put( "nimbusGreen", new Color(176,179,50) );
				  UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
				  UIManager.put( "nimbusLightBackground", new Color( 40, 40, 40) );
				  UIManager.put( "nimbusOrange", new Color(191,98,4) );
				  UIManager.put( "nimbusRed", new Color(169,46,34) );
				  UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
				  UIManager.put( "nimbusSelectionBackground", new Color( 64, 64, 64) );
				  UIManager.put( "text", new Color( 230, 230, 230) );
				  UIManager.put( "textBackground", new Color(64,64,64) );
				  
				  try {
				    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				      if ("Nimbus".equals(info.getName())) {
				          javax.swing.UIManager.setLookAndFeel(info.getClassName());
				          break;
				      }
				    }
				  } catch (ClassNotFoundException e) {
				    e.printStackTrace();
				  } catch (InstantiationException e) {
				    e.printStackTrace();
				  } catch (IllegalAccessException e) {
				    e.printStackTrace();
				  } catch (javax.swing.UnsupportedLookAndFeelException e) {
				    e.printStackTrace();
				  } catch (Exception e) {
				    e.printStackTrace();
				  }
				 TestUnitViewFrame gui = new TestUnitViewFrame("UnitTester");
				 TestUnitController testUnitController = new TestUnitController(gui);
				 gui.setListeners(testUnitController);
				 gui.show();
			}
		});

	}

}
