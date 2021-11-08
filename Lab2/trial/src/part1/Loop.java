package part1;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Loop implements Runnable {
	
	private Thread t;
	private String threadName;
	private String inp;
	private int count;
	
	Loop(String name) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	}	

	
	public void run() {
		System.out.println("Running " +  threadName );
	
		try {
			
			String input=Part1_GUI.InputText.getText();
			
			String lastChar = input.substring(input.lastIndexOf(" ")+1); // taking last characters after spaces from last index
	    	count=Integer.parseInt(lastChar);
	    	
	    	inp = input.substring(0, (input.lastIndexOf(" ")+1)); // taking all characters except last characters after spaces from last index
	    	
	    	Part1_GUI.OutputText.setText(null);
	    	
	    	for (int i = 0; i<count; i++) {
	    		String[] cmd = {"/bin/bash","-c",inp};			
				Process p= Runtime.getRuntime().exec(cmd); // create a process and execute cmd Array
				
				String s;
	 		    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 		    
	 		    while((s=stdInput.readLine())!=null){
	 		    	System.out.println(s);
	 		    	Part1_GUI.OutputText.append(s+ "\n");  
	 		    }
	    	}
 		}
		
		catch (Exception e) {
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