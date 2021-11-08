package se.ltu.netprog.javaprog.sockets;


import java.net.*;// need this for InetAddress, Socket, ServerSocket 
import java.io.*;// need this for I/O stuff


public class UDPEchoServer {
//Define buffer size
    static final int BUFSIZE = 1024;

    static public void main(String args[]) throws SocketException {
//get arguements at execution time for port
        if (args.length != 1) {
            throw new IllegalArgumentException("Must specify a port!");
        }
        int port = Integer.parseInt(args[0]);
//create buffer
        byte[] buff = new byte[BUFSIZE];
  //create datagram socket and packet    
        DatagramSocket s_server = new DatagramSocket(port);
        DatagramPacket dp_server;

        try {
            while (!s_server.isClosed()) {
            	//recieve packet from the client
                dp_server = new DatagramPacket(buff, BUFSIZE);
                s_server.receive(dp_server);
                
                // print out client's address 
                System.out.println("Message from " + dp_server.getAddress().getHostAddress());
                
                //convert packet data to string
                String data = new String(dp_server.getData(), "UTF-8").trim();
                
                if(data.equals("bye")){
                s_server.close();
                break;
                }
              
                
                // Add name to data packets
                data =  " Fatema & Jamil " + data;
                
                //make datagram packet to send back to the client
                dp_server = new DatagramPacket(data.getBytes(), data.getBytes().length, dp_server.getAddress(), dp_server.getPort());
                
                //send to th client
                s_server.send(dp_server);
                
                buff = new byte[BUFSIZE];
     
            }
        } catch (IOException e) {
            System.out.println("Fatal I/O Error !");
            System.exit(0);

        }

    }
}
