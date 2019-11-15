import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JTextArea;


/**
 * @author Jonatan
 *
 */

public class TestUnitViewFrame {
	JFrame mainFrame;
	JButton runTestsButton;
	JButton closeThreadButton;
	JButton clearTextButton;
	HintTextField inputTextField;
	JTextPane testOutputTextPane;
	JTextArea testSuccessOrFailTextArea;
	JCheckBox runTestsInSequence;
	JScrollPane scrollableTextArea;
	JProgressBar testProgressBar;
	JPanel upperPanelRunning;
	JPanel upperPanel;
	CardLayout cards;
	Boolean inputPanelShowing=true;
	Boolean runTestInSequence;
	
	private Color backgroundColor= new Color(18,18,18);
	
	
	/** Generates a TestUnit GUI
	 * @param windowName name of the window created
	 */
	public TestUnitViewFrame(String windowName) {
		
		mainFrame = new JFrame(windowName);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		
		JPanel upperPanel = createUpperPanel();
		JPanel middlePanel = createMiddlePanel();

		
		
		mainFrame.add(upperPanel,BorderLayout.NORTH);
		mainFrame.add(middlePanel,BorderLayout.CENTER);
		
		
	

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = createMenu();
		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);
		
		mainFrame.setFont(new Font("Lucida Console",Font.BOLD,16));
		ChangeFont.changeFont(mainFrame, "NSimSun",
							  ChangeFont.CHANGE_UNDERLAYING_COMPONENTS);
		
		mainFrame.setPreferredSize(new Dimension(600,600));
		mainFrame.pack();
		middlePanel.requestFocusInWindow();
		
		
		
	}
	
	/** Creates a menu
	 * 
	 * @return A JMenu that is suitable to add to a JMenuBar.
	 */
	private JMenu createMenu() {
		JMenu menu=new JMenu("Settings");
		Stack<Container> containers= new Stack<>();
		containers.add(testOutputTextPane);
		containers.add(inputTextField);
		containers.add(testSuccessOrFailTextArea);
		JMenu fontsMenu=new FontMenu("Change Fonts",containers);
		MenuScroller menuScroller=MenuScroller.setScrollerFor(fontsMenu);
		menu.add(fontsMenu);
		return menu;
	}

	/** Creates and returns the upper panel
	 * @return a JPanel which is to be added as a upper panel of main frame.
	 */
	private JPanel createUpperPanel() {
		upperPanel = new JPanel();
		cards = new CardLayout();
		upperPanel.setBorder(BorderFactory.createBevelBorder(
				BevelBorder.RAISED));
		upperPanel.setLayout(cards);
		
		
		JPanel upperPanelInput = 
				createInputPanel();
		upperPanelRunning = createRunningPanel();
		
		 
		
		upperPanel.add(upperPanelInput);
		upperPanel.add(upperPanelRunning);
		
		
		return upperPanel;
		
	}
	
	/** Creates and returns the middle panel 
	 * @return A JPanel which is to be added to the center of main frame.
	 */
	private JPanel createMiddlePanel() {
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

		middlePanel.setLayout(new BorderLayout());
		testOutputTextPane = new JTextPane();
		testOutputTextPane.setBorder(
				BorderFactory.createTitledBorder("Test Output"));
		testOutputTextPane.setEditable(false);
		scrollableTextArea = new JScrollPane(testOutputTextPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		testSuccessOrFailTextArea = new JTextArea(4,1);
		testSuccessOrFailTextArea.setBorder(
				BorderFactory.createTitledBorder("Test Summary"));
		testSuccessOrFailTextArea.setEditable(false);
		//middlePanel.add(testOutputTextPane,BorderLayout.CENTER);
		middlePanel.add(scrollableTextArea,BorderLayout.CENTER);
		middlePanel.add(testSuccessOrFailTextArea,BorderLayout.SOUTH);
		middlePanel.setVisible(true);
		return middlePanel;
	}
	
	/** Creates the panel for input of test
	 * panel contains text field for input of test classes,
	 * also contains a button for running tests and finally 
	 * a checkbox for running tests sequentially.
	 * @return the created JPanel.
	 */
	private JPanel createInputPanel() {
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		inputTextField = new HintTextField(GlobalVariables.hintMessage);
		inputTextField.setFont(new Font("Lucida Console",Font.PLAIN,16));
		leftPanel.add(inputTextField);
		
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(1,2));
		runTestsButton = new JButton("Run Tests");
		runTestsInSequence = new JCheckBox("Run Tests Sequentially");

		
		
		rightPanel.add(runTestsButton);
		rightPanel.add(runTestsInSequence);
		
		
		inputPanel.add(leftPanel,BorderLayout.CENTER);
		inputPanel.add(rightPanel,BorderLayout.EAST);
		
		return inputPanel;
	}
	
	/** Creates the panel which is to be shown while tests are running
	 * panel contains a progressbar and a button for closing tests
	 * @return the created JPanel.
	 */
	private JPanel createRunningPanel() {
		JPanel runningPanel = new JPanel();
		runningPanel.setLayout(new FlowLayout());
		closeThreadButton = new JButton("Cancel Tests");
		testProgressBar = new JProgressBar();
		testProgressBar.setStringPainted(true);
		runningPanel.add(testProgressBar);
		runningPanel.add(closeThreadButton);
		
		return runningPanel;
	}
	
	
	/** calls setVisible(true) on frame.
	 * 
	 */
	public void show() {
		mainFrame.setVisible(true);
	}

	
	/** Appends string with specified attributeset to outputtextpane
	 * @param s String to be appended
	 * @param as Attributeset for string to be appended
	 */
	public void appendToOutputTextField(String s,AttributeSet as) {
		Document doc = testOutputTextPane.getDocument();
		try {
			doc.insertString(doc.getLength(), s, as);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/** Updates testSuccesOrFailTextArea
	 * @param successes number of successes of test
	 * @param fails number of failures of test (without exception)
	 * @param exceptions number of failures with exception
	 */
	public void setSuccessAndFails(int successes,int fails,int exceptions) {
		if(fails==0&&exceptions==0) {
			testSuccessOrFailTextArea.setText("You passed all "+successes+ 
					" tests! Congratulations!");
		}else {
			testSuccessOrFailTextArea.setText("Number of tests passed: "
					+successes +"\n"+ "Number of tests failed: "+fails +"\n"+
					"Number of tests failed with exception: "+exceptions);
		}
	}
	
	
	/** Sets listeners of buttons and textfields
	 * @param testUnitController testUnitController class to be added to listeners
	 */
	public void setLitseners(TestUnitController testUnitController) {
		runTestsButton.addActionListener(
				new RunTestButtonLitsener(testUnitController,runTestsInSequence));
		closeThreadButton.addActionListener(
				new CloseThreadButtonListener(testUnitController));
		inputTextField.addKeyListener(
				new MyOwnKeyListener(testUnitController,runTestsInSequence));
		upperPanelRunning.addKeyListener(new MyOtherKeyListener(testUnitController));
		
	}
	
	
	/** gets Input from input textfield
	 * @return string from input text field
	 */
	public String getInput() {
		return inputTextField.getText();
	}

	/** switches between input and running panel
	 * 
	 */
	public void switchUpperPanels() {
		inputPanelShowing=!inputPanelShowing;
		cards.next(upperPanel);
		if(!inputPanelShowing) {
			upperPanelRunning.requestFocusInWindow();
		}else {
			inputTextField.requestFocusInWindow();
		}
		
	}
	
	/** Displays error message with a JOptionPane
	 * @param errorMessage String to be presented
	 */
	public void popupError(String errorMessage) {
		JOptionPane.showMessageDialog(mainFrame,errorMessage,"Error",
									  JOptionPane.ERROR_MESSAGE);
	}

	/** clears the output textpane of text
	 * 
	 */
	public void clearOutputTextField() {
		testOutputTextPane.setText(null);
	}

	/** Updates progressbar of running panel
	 * @param nmbrOfFinishedTests integer of finished tests
	 * @param nmbrOfTestMethods integer of total tests
	 */
	public void updateProgressBar(Integer nmbrOfFinishedTests,
							      Integer nmbrOfTestMethods) {
		testProgressBar.setValue(nmbrOfFinishedTests);
		testProgressBar.setString(""+nmbrOfFinishedTests+"/"+nmbrOfTestMethods+
								  " Tests completed");
	}

	/** Clears sucessorfail textarea
	 * 
	 */
	public void clearSuccesOrFailTextField() {
		testSuccessOrFailTextArea.setText(null);
		
	}
	
	/** Sets the value of the progressbar
	 * @param min min value of progressbar
	 * @param max max value of progressbar
	 */
	public void setMinMaxProgressBar(int min, int max) {
		testProgressBar.setMinimum(min);
		testProgressBar.setMaximum(max);
	}
	
	
	
	
}
