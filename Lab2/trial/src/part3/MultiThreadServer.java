package part3;

//import required libraries
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//this class implements Runnable interface so that it can be passed as a Runnable target while creating a new Thread.
public class MultiThreadServer implements Runnable {
	
	Socket csocket;
	
	// A Logger object is used to log messages during the execution of a program 
	// Create a Logger with class name. getLogger() method of a Logger class is used to find or create a logger.
	private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
	
	static FileHandler fh; 

	// the constructor of this class takes socket as a parameter, which can uniquely identify any incoming request.
	MultiThreadServer(Socket csocket) {
		this.csocket = csocket;
	}

	// Inside the run() method of this class, it reads the client’s message and replies.
	public void run() {
		
		DataOutputStream outToClient = null;
		BufferedReader inFromClient = null;
		
		try {
			
			// writing to client. getOutputStream() method is used to send the output through the socket.
			outToClient = new DataOutputStream(csocket.getOutputStream());
			
			// takes input from the client socket (reading from client)
			inFromClient = new BufferedReader(
					new InputStreamReader(csocket.getInputStream()));

			String cmd = inFromClient.readLine(); // received message from client
			
			Unix unix = new Unix();
			String reply = unix.execute(cmd);
			
			
			outToClient.writeBytes(reply + "\n");  // sending the response to client
			
			// The log levels define the severity of a message. 
			// The Level class is used to define which messages should be written to the log.
			// it sets the logger to the info level, which means all messages with severe, warning and info will be logged.
			LOGGER.setLevel(Level.INFO);
			
			// Call info method
			LOGGER.info("TCP Connection Closed!");
			
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.setLevel(Level.SEVERE); // Severe occurs when some critical or terrible errors shown by the application. In such cases, the application is not able to continue further. 
			LOGGER.severe("IO Exception detected!");
			LOGGER.warning("Exception occured!"); // warning occurs due to user mistake. If a user inputs the wrong credentials, the application shows a warning.
		}
		finally {
          try {
              if (outToClient != null) {
              	outToClient.close();
              }
              if (inFromClient != null) {
              	inFromClient.close();
              	csocket.close();
              }
          }
          catch (IOException e) {
              e.printStackTrace();
          }
      }
	}
	
	public static void main(String args[]) throws Exception { 
		
		// Initialize a FileHandler to write to the given filename, with optional append.
		fh = new FileHandler("src/part3/log_TCP.log",true);  
		LOGGER.addHandler(fh); // Add a log Handler to receive logging messages.
		
		// create a TXT formatter
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);
		
		// initialize server socket
		ServerSocket server = null;
		
		try {
			  
          // server is listening on port 6400
          server = new ServerSocket(6400);
  		System.out.println("Listening");
  		
  		// The log levels define the severity of a message. 
  		// The Level class is used to define which messages should be written to the log.
  		// it sets the logger to the info level, which means all messages with severe, warning and info will be logged.
  		LOGGER.setLevel(Level.INFO);
  		
  		// Call info method
  		LOGGER.info("Server is Listening to port: 6400");

          // running infinite loop for getting client request
          while (true) {

              // socket object to receive incoming client requests
              Socket client = server.accept();

              // Displaying that new client is connected to server
              System.out.println("New client connected: " + client.getInetAddress().getHostAddress());

              LOGGER.setLevel(Level.INFO);
  			LOGGER.info("New TCP connection established");

              // the start() method is invoked on newly created thread object. This thread will handle the client separately
  			new Thread(new MultiThreadServer(client)).start();
  		}
      }
      catch (IOException e) {
          e.printStackTrace();
      }
      finally {
          if (server != null) {
              try {
                  server.close();
              }
              catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
	}
}