package part3;

//import required libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Unix {
	
	String reply = "";
	int count;
	String inp;
	
	public String execute(String command) {
		
		StringBuffer output = new StringBuffer();

		Process p;
		
		try {
			
			// check if the last characters in a string are numeric
			if (command.matches(".*\\d+")) {
				
	            String lastChar = command.substring(command.lastIndexOf(" ")+1); // taking last characters after spaces from last index
	            count=Integer.parseInt(lastChar);
	            
	            inp = command.substring(0, (command.lastIndexOf(" ")+1)); // taking all characters except last characters after spaces from last index
	            
	            for (int i = 0; i<count; i++) {
	            	
	            	String[] cmd1 =  {"/bin/bash", "-c", inp};
	    			p = Runtime.getRuntime().exec(cmd1); // create a process and execute cmd1 Array
	    			
	    			p.waitFor(); //Causes the current thread to wait, if necessary, until the process represented by this Process object has terminated. This method returns immediately if the subprocess has already terminated. If the subprocess has not yet terminated, the calling thread will be blocked until the subprocess exits.
	    			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

	    			String line = "";
	    			while ((line = reader.readLine())!= null) {
	    				output.append(line + "\n");
	    			}		    		
		    	}
	        }
			
	        else {
	        	
	        	String[] cmd1 =  {"/bin/bash", "-c", command};
				p = Runtime.getRuntime().exec(cmd1); // create a process and execute cmd1 Array
				
				p.waitFor(); //Causes the current thread to wait, if necessary, until the process represented by this Process object has terminated. This method returns immediately if the subprocess has already terminated. If the subprocess has not yet terminated, the calling thread will be blocked until the subprocess exits.
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line = "";
				while ((line = reader.readLine())!= null) {
					output.append(line + "\n");
				}
	        }
			
			reply = output.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reply;
	}
}