package se.ltu.netprog.javaprog.sockets;

//Requires a single command line arg - the port number
import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.nio.charset.StandardCharsets;
import java.io.*;	// need this for I/O stuff

public class TCPEchoServer {
	// define a constant used as size of buffer 
	static final int BUFSIZE=1024;
	// main starts things rolling
	static public void main(String args[]) { 
	// set argument for port from execution time	
		if (args.length != 1){
			throw new IllegalArgumentException("Must specify a port!");
		}
		
		int port = Integer.parseInt(args[0]);
		try { 
			// Create Server Socket (passive socket) 
			ServerSocket ss = new ServerSocket(port);
			
			while (true) { 
				Socket s = ss.accept();
				handleClient(s);
				
			} 
			
		} catch (IOException e) {
			System.out.println("Fatal I/O Error !"); 
			System.exit(0);
			
		}
		
	}
	
	//this method handles one client
	// declared as throwing IOException - this means it throws 
	// up to the calling method (who must handle it!)
	//try taking out the "throws IOException" and compiling, 
	// the compiler will tell us we need to deal with this!
	
	static void handleClient(Socket s) throws IOException 
	{ 
		byte[] buff = new byte[BUFSIZE];
		int bytesread = 0;
		//print out client's address
		System.out.println("Connection from " + s.getInetAddress().getHostAddress());
		
		//Set up streams, printwriter and bufferedreaders
		InputStream in = s.getInputStream(); 
		OutputStream out = s.getOutputStream();
		PrintWriter writer = new PrintWriter(out, true);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		//read/write loop 
	
//Modify your code here so that it sends back your name in addition to the echoed symbols
//get the data from the client side
		String data;
		
		do {
			  data = reader.readLine();
			  data =  " Fatema & Jamil " + data;
			  
			  //send data back to client after adding our name
			  writer.println("Server: " + data);
		} while(!data.equals("quit"));
			
		System.out.println("Client has left\n"); 
		
		s.close();
		
	}
	
}