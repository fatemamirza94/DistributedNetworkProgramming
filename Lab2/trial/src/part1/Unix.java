package part1;

//import required libraries

import java.io.BufferedReader;
import java.io.InputStreamReader;

//this class implements Runnable interface so that it can be passed as a Runnable target while creating a Thread.
public class Unix implements Runnable {
	public String input;
	private Thread t;
	private String threadName;
	
	Unix(String name) {
		threadName = name;
		System.out.println("Creating " +  threadName );
	}
	
	public void run() {
		System.out.println("Running " +  threadName );
		try {
			input=Part1_GUI.InputText.getText();
			String[] cmd = {"/bin/bash","-c",input};
			Process p= Runtime.getRuntime().exec(cmd); // create a process and execute cmd Array
			
			String s;
		    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    Part1_GUI.OutputText.setText(null);
		    while((s=stdInput.readLine())!=null){
		    	System.out.println(s);
		    	Part1_GUI.OutputText.append(s+ "\n");  
		    }
		}catch (Exception e) {
			System.out.println("Thread " +  threadName + " interrupted.");
		}
		
		System.out.println("Thread " +  threadName + " exiting.");
	}
	
	public void start () {
		System.out.println("Starting " +  threadName );
		if (t == null) {
			t = new Thread (this, threadName);
			t.start ();
		}
	}
}
