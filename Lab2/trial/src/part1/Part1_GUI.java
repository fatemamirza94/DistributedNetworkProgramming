package part1;

//import required libraries
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Part1_GUI {

	private JFrame frame;
	private JLabel lblInputCommands;
	private JLabel lblOutput;
	public static JTextArea InputText;
	public static JTextArea OutputText;

	
	/* Launch the application */
	public static void main(String[] args) {
		// Causes runnable to have its run method called in the dispatch thread of the system EventQueue
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Part1_GUI window = new Part1_GUI(); // create an object
					window.frame.setVisible(true); // making the frame visible
				} catch (Exception e) {
					e.printStackTrace(); // printStackTrace() will pinpoint the exact line in which a method raised the exception.
				}
			}
		});
	}

	/* Create the application */
	public Part1_GUI() {
		initialize();
	}

	
	/* Initialize the contents of the frame */
	private void initialize() {
		
		// create a frame using JFrame constructor by setting the frame title
		frame = new JFrame("UnixCommand");
		frame.setBounds(100, 100, 650, 500); // set size and location of the frame 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE operation exits the program when the user closes the frame
		frame.getContentPane().setLayout(null); // using absolute layout
		
		
		// Create a button with a caption
		JButton btnUnix = new JButton("Execute Unix (Single Time)");
		btnUnix.setFont(new java.awt.Font("Arial", Font.BOLD, 14)); // font style
		
		//Adding an event listener using anonymous class
		btnUnix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unix R1 = new Unix("Thread-1");
			    R1.start();
				
			}
		});
		btnUnix.setBounds(23, 215, 250, 25); // set size and location of the button
		frame.getContentPane().add(btnUnix); // Adding the Button to the frame
		
		
		// Create a JLabel object (for input commands) with some text
		lblInputCommands = new JLabel("Input Commands");
		lblInputCommands.setBounds(80, 50, 129, 15); // set size and location of the label
		frame.getContentPane().add(lblInputCommands); // Adding the label to the frame
		
		
		// Create a JLabel object (for output) with some text
		lblOutput = new JLabel("Output");
		lblOutput.setBounds(400, 52, 70, 15); // set size and location of the label
		frame.getContentPane().add(lblOutput); // Adding the label to the frame
		
		
		// Create a JScrollPane object to make scrollable view of input
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 90, 250, 99); // set size and location of the scrollpane
		frame.getContentPane().add(scrollPane); // Adding the scrollpane to the frame
		
		
		// create a JTextArea object for input
		InputText = new JTextArea();
		scrollPane.setViewportView(InputText); // specifying InputText area that's going to be displayed in the scrollpane
		
		
		// Create a JScrollPane object to make scrollable view of output
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(300, 90, 270, 99); // set size and location of the scrollpane
		frame.getContentPane().add(scrollPane_1); // Adding the scrollpane to the frame
		
		
		// create a JTextArea object for output
		OutputText = new JTextArea();
		scrollPane_1.setViewportView(OutputText); // specifying OutputText area that's going to be displayed in the scrollpane
		
		// Create another button with a caption
		JButton btnUnix2 = new JButton("Execute Unix (Multiple Times)");
		btnUnix2.setFont(new java.awt.Font("Arial", Font.BOLD, 14)); // font style
		
		//Adding an event listener using anonymous class
		btnUnix2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Loop R2 = new Loop("Thread-2");
			    R2.start();
			     
			}
		});
		btnUnix2.setBounds(300, 215, 270, 25); // set size and location of the button
		frame.getContentPane().add(btnUnix2); // Adding the label to the frame
	}
}
