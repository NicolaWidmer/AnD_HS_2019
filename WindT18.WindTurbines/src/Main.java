// =======================================================================================================
// Test Exam    : WindT18.WindTurbines
// Author       :  
// =======================================================================================================

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

class Main {
	
	//
	// Parameters include:
	//
	// n    - number of possible positions
	// D    - the minimal distance between two turbines
	// d[i] - position of the i-th turbine
	// e[i] - energy of the it-ht turbine
	//
	static int solve(int n, int D, int[] d, int[] e){
		//
		// Provide your solution here
		//
		// as few lines as posible
		//
		int[] mit=new int[n+1];
  		for(int i=1,ohne=mit[i-1];i<=n;ohne=mit[i++]) {	
  			while(++mit[0]<=i&&d[i]-d[mit[0]]>=D);
  			mit[i]=mit[0]-->i?0:ohne<mit[mit[0]]+e[i]?mit[mit[0]]+e[i]:ohne;}
		return mit[n];
		
	}
	
	
	
	
	static int solve2(int n, int D, int[] d, int[] e){
		  		//
		  		// Provide your solution here
		  		//
				// normal solution 
				//
		
		  		int[] mit=new int[n+1];
		  		int[] ohne=new int[n+1];
		  		int firstcheck=0;

		  		d[0]=-D-1;
		  		   	
		    		for(int i=1;i<=n;i++) {		 
		    			ohne[i]=mit[i-1];
		    			for(int j=firstcheck;j<=i;j++) {
		  		 		if(d[i]-d[j]<D) {
		  		 			if(ohne[i]<mit[j-1]+e[i]) {
		  		 				mit[i]=mit[j-1]+e[i];
		  		 			}
		  		 			else {
		  		 				mit[i]=ohne[i];
		  		 			}
		  		 			firstcheck=j;
		  					break;
		  				}
		  			}
		    			
		  	  	}
		  		return mit[n];
		  	}
	
	
	public static void read_and_solve(InputStream in, PrintStream out) 
	{
		Scanner scanner = new Scanner(in);

		int cases = scanner.nextInt();
		for (int t = 0; t < cases; t += 1) {
			//
			// Scan the input sizes i.e number of possible positions
			// as well as the minimal distance between two turbines
			//
			int n = scanner.nextInt();
			int D = scanner.nextInt();
			//
			// Allocate space for position & energy
			//
			int d[] = new int[n+1];
			int e[] = new int[n+1];
			//
			// Perform the scans for position and energy
			//
			for (int i = 1; i <= n; i += 1) d[i] = scanner.nextInt();
			for (int i = 1; i <= n; i += 1) e[i] = scanner.nextInt();

			out.println(solve(n, D, d, e));
		}

		scanner.close();
	}

	//
	// Do not modify the main method, and keep the method read_and_solve
	// 
	public static void main(String[] args) {	
		read_and_solve(System.in, System.out);		
	}
}