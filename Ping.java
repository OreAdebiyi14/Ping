package ca2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ping 
{
// Oreoluwa Adebiyi C21360871
//	Q1.
//	Using ProcessBuilder and either the ProcessBuilder constructor or its command() method,
//	write a Java program that run a command (in Windows) or a shell script (Mac, Linux) and
//	ping a website of choice 10 times. Using BufferedReader, write the output (ping response) to
//	the console.
	
//	For Mac
//	processBuilder.command("bash", "-c", "<ping command> -c <number of times to ping> <website>");
	
	/**public static void main(String[] args) throws Exception 
	 {
	        
	        // Choose a website to ping - apple website
	        // Create the ping command depending on the operating system
		 	// i put an if statement to first try it but i will comment it out
		 	//it should work if its on a mac or windows
		 	
	       
//	        if (System.getProperty("os.name").startsWith("Windows")) {
//	            command = new String[] {"cmd.exe", "/c", "ping", "-n", "10", "www.apple.com"};
//	        } else {
	        String[] command = new String[] {"ping", "-c", "10", "www.apple.com"};
	        
	        
	        // Creating the ProcessBuilder and start the process
	        ProcessBuilder builder = new ProcessBuilder(command);
	        Process process = builder.start();
	        
	        // Reading the output of the process
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        // creates a new BufferedReader object that reads the 
	        // output from the shell script executed by the ProcessBuilder.
	        String sentence;
	        while ((sentence = reader.readLine()) != null) 
	        {
	            System.out.println(sentence);//this sentence will be repeated 10 times with how long it took
	        }
	    }
}*/

	//Q2
	/*
	The issue with the solution to Q1 is that the call to get the InputStream is blocking. Update
	the code so that a new Thread is started to handle the data from the InputStream. Using the
	Java ExecutorService, Java Future and Callable are all required in your solution.Useful hints for solving Q2:
	The main() method should:
	1. Create a ExecutorService as follws:
	ExecutorService servicePool = Executors.newSingleThreadExecutor();
	2. Create a ProcessBuilder, pass the command and start it – same as Q1.
	3. Instantiate a class which implements Callable and has a call method which reads the
	content in the InputStream and returns the content as a List of Strings – You need to
	implement this class.
	4. Call submit on servicePool which will return a Future object
	5. Read the results from the Future object calling one of the Future.get() method and
	print it out to the console.
	*/
	
	 public static void main(String[] args) throws Exception {
	        
		 String[] command = new String[] {"ping", "-c", "10", "www.google.com"};
	        
	        
	        // Creating the ProcessBuilder and start the process
	        ProcessBuilder builder = new ProcessBuilder(command);
	        Process process = builder.start();
	        
	        // Reading the output of the process
	        BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        // creates a new BufferedReader object that reads the 
	        // output from the shell script executed by the ProcessBuilder.
	        
	       
	        // Create an ExecutorService to handle the data from the InputStream in a new thread
	        ExecutorService servicePool = Executors.newSingleThreadExecutor();
	       // ExecutorService is shutdown after the result is received
	        
	        Future<List<String>> futureResult = servicePool.submit(new InputStreamHandler(process.getInputStream()));
	        
	        // Wait for the thread to finish and get the result
	        List<String> result = futureResult.get();
	        
	        // Print the result to the console
	        for (String r : result) {
	            System.out.println(r);
	        }
	        
	        // Shutdown the ExecutorService
	        servicePool.shutdown();
	    }
	    
	    /**
	     * A Callable that reads the content in an InputStream and returns the content as a List of Strings.
	     */
	    private static class InputStreamHandler implements Callable<List<String>> {
	        
	        private InputStream inputStream;
	        
	        public InputStreamHandler(InputStream inputStream) {
	            this.inputStream = inputStream;
	        }

	        @Override
	        public List<String> call() throws Exception {
	            List<String> result = new ArrayList<>();
	            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
	            String sentence;
	            while ((sentence = read.readLine()) != null) {
	                result.add(sentence);
	                //Result is then printed out
	            }
	            return result;
	        }
	    }
	

}
