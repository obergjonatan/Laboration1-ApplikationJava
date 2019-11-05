import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ViewFrame {
	JFrame mainFrame;
	JButton runTestsButton;
	JButton closeThreadButton;
	JButton clearTextButton;
	JTextField inputTextField;
	JTextArea testInProgress;
	JTextArea testOutputTextArea;
	JTextArea testSuccessOrFailTextArea;
	JCheckBox hideTestOutputCheckBox;
	JPanel upperPanel;
	CardLayout cards;
	
	
	
	// OBSERVE: Should only be called from EDT.
	
	public ViewFrame(String windowName) {
		mainFrame = new JFrame(windowName);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		
		JPanel upperPanel = createUpperPanel();
		JPanel middlePanel = createMiddlePanel();
		JPanel lowerPanel = createLowerPanel();
		
		mainFrame.add(upperPanel,BorderLayout.NORTH);
		mainFrame.add(middlePanel,BorderLayout.CENTER);
		mainFrame.add(lowerPanel,BorderLayout.SOUTH);
		
		
		
		
	}
	
	private JPanel createUpperPanel() {
		upperPanel = new JPanel();
		cards = new CardLayout();
		upperPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		upperPanel.setLayout(cards);
		
		
		JPanel upperPanelInput = createInputPanel((CardLayout)upperPanel.getLayout(),upperPanel);
		JPanel upperPanelRunning = createRunningPanel((CardLayout)upperPanel.getLayout(),upperPanel);
		
		 
		
		upperPanel.add(upperPanelInput);
		upperPanel.add(upperPanelRunning);
		
		
		return upperPanel;
		
	}
	
	private JPanel createMiddlePanel() {
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createTitledBorder("Output"));
		middlePanel.setLayout(new BorderLayout());
		testOutputTextArea = new JTextArea("Output Text Area");
		testOutputTextArea.setBorder(BorderFactory.createTitledBorder("Test Output"));
		testOutputTextArea.setEditable(false);
		testSuccessOrFailTextArea = new JTextArea("Total Number of Tests : \n"
				+ "Number of Fails: \n"
				+ "Number of Successes: \n");
		testSuccessOrFailTextArea.setBorder(BorderFactory.createTitledBorder("Test Summary"));
		testSuccessOrFailTextArea.setEditable(false);
		middlePanel.add(testOutputTextArea,BorderLayout.CENTER);
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
				testOutputTextArea.setVisible(enabled);
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
		inputTextField = new JTextField("Test1");
		
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
		testInProgress = new JTextArea("Tests in Progress");
		runningPanel.add(testInProgress);
		runningPanel.add(closeThreadButton);
		
		return runningPanel;
	}
	
	
	public void show() {
		mainFrame.setVisible(true);
	}

	public void addToOutputTextField(String s) {
		testOutputTextArea.append(s);	
	}
	
	public void setSuccessAndFails(int successes,int fails) {
		testSuccessOrFailTextArea.setText(" Number of tests passed: " +successes +"\n"+
											"Number of tests failed: "+fails +"\n");
	}
	
	public void setLitseners(Controller controller) {
		runTestsButton.addActionListener(new RunTestButtonLitsener(controller));
		closeThreadButton.addActionListener(new closeThreadButtonLitsener(controller));
	}
	
	public String getInput() {
		return inputTextField.getText();
	}

	public void switchUpperPanels() {
		cards.next(upperPanel);
		
	}
	
	public void popupError(String errorMessage) {
		JOptionPane.showMessageDialog(mainFrame,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
	}

	public void clearOutputTextField() {
		testOutputTextArea.setText(null);
	}
	
	
}
