package se.ltu.netprog.javaprog.sma;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class DNSService implements Deliverable {

    public static final int DNS_SERVICE_MESSAGE = 100;
    public static final int DNS_SERVICE_PORT = 7;
//the code for the DNS part
    public Message send(Message m) {
        try {
            InetAddress a = InetAddress.getByName(m.getParam("hostname"));
            System.out.println(a.getHostAddress());
            System.out.println(a);
            System.out.println("I am here");
            m.setParam("IP", a.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("No address found for ");
        }
        return m;
    }

    public static void main(String args[]) {
        DNSService ds = new DNSService();
        MessageServer ms;
        try {
            ms = new MessageServer(DNS_SERVICE_PORT);
        } catch (Exception e) {
            System.err.println("Could not start service " + e);
            return;
        }
        Thread msThread = new Thread(ms);
        ms.subscribe(DNS_SERVICE_MESSAGE, ds);
        msThread.start();
    }
}
