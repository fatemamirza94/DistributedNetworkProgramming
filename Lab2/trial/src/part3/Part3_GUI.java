package part3;

//import required libraries
import java.io.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Socket;


public class Part3_GUI {

	private JFrame frame;
	private JTextArea UrlText;
	private JTextArea InputText;
	private JTextArea OutputText;
	private JButton btnExecute;
	

	/* Launch the application */
	public static void main(String[] args) {
		// Causes runnable to have its run method called in the dispatch thread of the system EventQueue
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Part3_GUI window = new Part3_GUI(); // create an object
					window.frame.setVisible(true); // making the frame visible
				} catch (Exception e) {
					e.printStackTrace(); // printStackTrace() will pinpoint the exact line in which a method raised the exception.
				}
			}
		});
	}

	
	/* Create the application */
	public Part3_GUI() {
		initialize();
	}

	
	/* Initialize the contents of the frame */
	private void initialize() {
		
		// create a frame using JFrame constructor by setting the frame title
		frame = new JFrame("UnixCommand");
		frame.setBounds(100, 100, 650, 500); // set size and location of the frame 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE operation exits the program when the user closes the frame
		frame.getContentPane().setLayout(null); // using absolute layout
		
		
		// Create a JLabel object (for URL) with some text
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(260, 8, 204, 25); // set size and location of the label
		frame.getContentPane().add(lblUrl); // Adding the label to the frame
		
		
		// create a JTextArea object for URL
		UrlText = new JTextArea();
		UrlText.setBounds(45, 45, 500, 35); // set size and location of the UrlText
		frame.getContentPane().add(UrlText); // Adding UrlText to the frame
		
		
		// Create a JLabel object (for input commands) with some text
		JLabel lblInputCommands = new JLabel("<html><p>Input Commands ( Enter the number of times after the command for getting multiple output ( Example: ls 10 ) )</p></html>", SwingConstants.CENTER);
		lblInputCommands.setBounds(45, 100, 500, 45); // set size and location of the label
		frame.getContentPane().add(lblInputCommands); // Adding the label to the frame
		
		
		// create a JTextArea object for input
		InputText = new JTextArea(); 
		InputText.setBounds(45, 150, 500, 37); // set size and location of the InputText
		frame.getContentPane().add(InputText); // Adding InputText to the frame
		
		
		// Create a button with a caption
		btnExecute = new JButton("Execute Unix");
		//Adding an event listener using anonymous class
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try
				{
					String op = linuxExecute(InputText.getText(), UrlText.getText());
					OutputText.setText(op);
				}
				catch(Exception e1)
				{
					e1.printStackTrace(); // printStackTrace() will pinpoint the exact line in which a method raised the exception.
				}
				
			}
		});
		btnExecute.setBounds(145, 215, 250, 25); // set size and location of the button
		frame.getContentPane().add(btnExecute); // Adding the Button to the frame
		
		
		// Create a JLabel object (for output) with some text
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(240, 265, 170, 15); // set size and location of the label
		frame.getContentPane().add(lblOutput); // Adding the label to the frame
				
		
		// Create a JScrollPane object to make scrollable view of output
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 290, 500, 120); // set size and location of the scrollpane
		frame.getContentPane().add(scrollPane); // Adding the scrollpane to the frame
		
		
		// create a JTextArea object for output
		OutputText = new JTextArea();
		scrollPane.setViewportView(OutputText); // specifying OutputText area that's going to be displayed in the scrollpane
	
	}
	
	public String linuxExecute(String command, String host) throws IOException {
		
		String reply = "";
		
		// initialize socket, output streams and buffered reader
		Socket clientSocket = null;
		DataOutputStream outToServer = null;
		BufferedReader inFromServer = null;
		
      try {
      	
      	// establish a connection by providing host and port number
      	clientSocket = new Socket(host, 6400);
          
          // writing to server. getOutputStream() method is used to send the output through the socket.
          outToServer = new DataOutputStream(clientSocket.getOutputStream());
          
          // reading from server
          inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          
          // sending the user input to server
          outToServer.writeBytes(command + "\n");		
          
          // string to read message
			String line = null;
			
			// retrieving the received message from server. keep reading until null.
			while ((line = inFromServer.readLine()) != null )
			{
				if (line.isEmpty()) {
					break;
				}
				reply += line;
				reply +="\n";
			}
			
      }
      catch (IOException e) {
      	e.printStackTrace();
      }
      finally {
      	// close the connection
          try {
              if (outToServer != null) {
              	outToServer.close();
              }
              if (inFromServer != null) {
              	inFromServer.close();
              }
              if (clientSocket != null) {
              	clientSocket.close();
              }
          }
          catch (IOException e) {
              e.printStackTrace();
          }
      }
      
      return reply;
	}
}