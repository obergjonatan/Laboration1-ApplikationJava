import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JTextArea;

public class ViewFrame {
	JFrame mainFrame;
	JButton runTestsButton;
	JButton closeThreadButton;
	JButton clearTextButton;
	HintTextField inputTextField;
	JTextPane testOutputTextPane;
	JTextArea testSuccessOrFailTextArea;
	JCheckBox hideTestOutputCheckBox;
	JScrollPane scrollableTextArea;
	JProgressBar testProgressBar;
	JPanel upperPanelRunning;
	JPanel upperPanel;
	CardLayout cards;
	Boolean inputPanelShowing=true;
	
	
	// OBSERVE: Should only be called from EDT.
	
	public ViewFrame(String windowName) {
		mainFrame = new JFrame(windowName);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = createMenu();
		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);
		
		JPanel upperPanel = createUpperPanel();
		JPanel middlePanel = createMiddlePanel();
		JPanel lowerPanel = createLowerPanel();
		
		mainFrame.add(upperPanel,BorderLayout.NORTH);
		mainFrame.add(middlePanel,BorderLayout.CENTER);
		mainFrame.add(lowerPanel,BorderLayout.SOUTH);
		
		mainFrame.setFont(new Font("Lucida Console",Font.BOLD,16));
		ChangeFont.changeFont(mainFrame, "Arial");
		
		mainFrame.setPreferredSize(new Dimension(500,500));
		mainFrame.pack();
		middlePanel.requestFocusInWindow();
		
		
		
	}
	
	private JMenu createMenu() {
		JMenu menu=new JMenu("Settings");
		JMenu fontsMenu=new FontMenu("Change Fonts",mainFrame);
		MenuScroller menuScroller=MenuScroller.setScrollerFor(fontsMenu);
		menu.add(fontsMenu);
		return menu;
	}

	private JPanel createUpperPanel() {
		upperPanel = new JPanel();
		cards = new CardLayout();
		upperPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		upperPanel.setLayout(cards);
		
		
		JPanel upperPanelInput = createInputPanel((CardLayout)upperPanel.getLayout(),upperPanel);
		upperPanelRunning = createRunningPanel((CardLayout)upperPanel.getLayout(),upperPanel);
		
		 
		
		upperPanel.add(upperPanelInput);
		upperPanel.add(upperPanelRunning);
		
		
		return upperPanel;
		
	}
	
	private JPanel createMiddlePanel() {
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createTitledBorder("Output"));
		middlePanel.setLayout(new BorderLayout());
		testOutputTextPane = new JTextPane();
		testOutputTextPane.setBorder(BorderFactory.createTitledBorder("Test Output"));
		testOutputTextPane.setEditable(false);
		scrollableTextArea = new JScrollPane(testOutputTextPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		testSuccessOrFailTextArea = new JTextArea(4,1);
		testSuccessOrFailTextArea.setBorder(BorderFactory.createTitledBorder("Test Summary"));
		testSuccessOrFailTextArea.setEditable(false);
		//middlePanel.add(testOutputTextPane,BorderLayout.CENTER);
		middlePanel.add(scrollableTextArea,BorderLayout.CENTER);
		middlePanel.add(testSuccessOrFailTextArea,BorderLayout.SOUTH);
		middlePanel.setVisible(true);
		return middlePanel;
	}
	
	private JPanel createLowerPanel() {
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		hideTestOutputCheckBox = new JCheckBox("Hide Test Output");
		hideTestOutputCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean enabled =
							e.getStateChange() == ItemEvent.DESELECTED;
				testOutputTextPane.setVisible(enabled);
			}
		});
		lowerPanel.add(hideTestOutputCheckBox);
		
		return lowerPanel;
	}
	
	private JPanel createInputPanel(CardLayout cards, JPanel parentPanel) {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		inputTextField = new HintTextField();
		inputTextField.setFont(new Font("Lucida Console",Font.PLAIN,16));		
		leftPanel.add(inputTextField);
		
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout());
		runTestsButton = new JButton("Run Tests");
		rightPanel.add(runTestsButton);
		
		
		inputPanel.add(leftPanel,BorderLayout.CENTER);
		inputPanel.add(rightPanel,BorderLayout.EAST);
		
		return inputPanel;
	}
	
	private JPanel createRunningPanel(CardLayout cards, JPanel parentPanel) {
		JPanel runningPanel = new JPanel();
		runningPanel.setLayout(new FlowLayout());
		closeThreadButton = new JButton("Cancel Tests");
		testProgressBar = new JProgressBar();
		testProgressBar.setStringPainted(true);
		runningPanel.add(testProgressBar);
		runningPanel.add(closeThreadButton);
		
		return runningPanel;
	}
	
	
	public void show() {
		mainFrame.setVisible(true);
	}

	public void addToOutputTextField(String s,AttributeSet as) {
		Document doc = testOutputTextPane.getDocument();
		try {
			doc.insertString(doc.getLength(), s, as);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void setSuccessAndFails(int successes,int fails) {
		if(fails==0) {
			testSuccessOrFailTextArea.setText("You passed all "+successes+ " tests! Congratulations!");
		}else {
			testSuccessOrFailTextArea.setText("Number of tests passed: " +successes +"\n"+
											"Number of tests failed: "+fails +"\n");
		}
	}
	
	public void setLitseners(Controller controller) {
		runTestsButton.addActionListener(new RunTestButtonLitsener(controller));
		closeThreadButton.addActionListener(new closeThreadButtonLitsener(controller));
		inputTextField.addKeyListener(new MyOwnKeyListener(controller));
		upperPanelRunning.addKeyListener(new MyOtherKeyListener(controller));
		
	}
	
	public String getInput() {
		return inputTextField.getText();
	}

	public void switchUpperPanels() {
		inputPanelShowing=!inputPanelShowing;
		cards.next(upperPanel);
		if(!inputPanelShowing) {
			upperPanelRunning.requestFocusInWindow();
		}else {
			inputTextField.requestFocusInWindow();
		}
		
	}
	
	public void popupError(String errorMessage) {
		JOptionPane.showMessageDialog(mainFrame,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
	}

	public void clearOutputTextField() {
		testOutputTextPane.setText(null);
	}

	public void updateProgressBar(Integer nmbrOfFinishedTests, Integer nmbrOfTestMethods) {
		testProgressBar.setValue(nmbrOfFinishedTests);
		testProgressBar.setString(""+nmbrOfFinishedTests+"/"+nmbrOfTestMethods+" Tests completed");
	}

	public void clearSuccesOrFailTextField() {
		testSuccessOrFailTextArea.setText(null);
		
	}
	
	public void setMinMaxProgressBar(int min, int max) {
		testProgressBar.setMinimum(min);
		testProgressBar.setMaximum(max);
	}
	
	
	
}
