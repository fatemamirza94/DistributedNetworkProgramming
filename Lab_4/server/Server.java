import java.net.*; // need this for InetAddress, Socket, ServerSocket
import java.io.*; // need this for I/O stuff

public class Server extends Thread {

    static final int BUFSIZE = 1024;
    private Socket s;

    public Server(Socket s) {
        this.s = s;
    }

    public static void main(String[] args) {
        try {
            // initialize the server
            ServerSocket sever = new ServerSocket(7000);
            System.out.println("Server is listening on port " + "7000" + "\n");

            for (;;) {
                Socket client = sever.accept();
                Thread server = new Server(client);
                server.start();
            }
        } catch (IOException e) {
            System.out.println("Problem Server: " + e.toString());
        }
    }

    public void run() {
        try {
            handleClient(s);
        } catch (IOException e) {
            System.out.println("Problem Server: " + e.toString());
        }
    }

    static void handleClient(Socket s) throws IOException {

        byte[] buff = new byte[BUFSIZE];

        // Set up input streams to get the input from the gui
        InputStream in = s.getInputStream();
        // write the output to client
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

        // outputofcalc for reading and calculations
        while (in.read(buff) != -1) {
            String outputofcalc = new String(buff).trim();
            String result;

            // calculations
            result = fibonacci(Long.parseLong(outputofcalc));

            // responding to client
            writer.println(result);
            writer.flush();

            System.out.println("Port:" + s.getPort() + "outputofcalc:" + outputofcalc + " Reply:" + result);
            s.close();
            buff = new byte[BUFSIZE];

            Thread t1 = currentThread();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                System.out.println("Problem Server: " + ex.toString());
            }
        }
    }

	
public static String fibonacci(Long count) {  
		Long i = new Long(2);
		Long n1 = new Long(1);
        Long n2 = new Long(0);
        Long n3 = new Long(0);

        for (i = new Long(2); i< count; ++i) {
            n3=n1+n2;    
			n1=n2;    
			n2=n3;  
        }
        return Long.toString(n3);
    }

}
