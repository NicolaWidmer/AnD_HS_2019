// ==================================================================================================================
// Exercise   : AD18E01.Structure
// Submission : https://judge.inf.ethz.ch/team/websubmit.php?cid=28777&problem=AD18E01
// Author     : 
// ==================================================================================================================

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

class Main {

	public static class MaxHeap {
		//
		// We assume that the heap will not exceed MAX_HEAPSIZE length
		//
		final static int MAX_HEAPSIZE = 100000;		
		//
		// This field describes the number of elements that the heap holds
		//
		private int N = 0;
		//
		// The values of the heap are stored in this array
		//
		private int values[] = new int [MAX_HEAPSIZE];		
		//
		// Default empty constructor
		//
		public MaxHeap () { 
			// do nothing ...
		}		
		//
		// Helper function that provides comparison.
		//
		private boolean cmp(int a, int b) {
			return a < b;
		}
		//
		// Helper function that swaps the i-th & the j-th element of the heap
		//
		private void swap (int i, int j) {
			int tmp = values[i];
			values[i] = values[j];
			values[j] = tmp;
		}
		//
		// For a given scanner, the heap will read the values. In this function, 
		// we assume that the values have partial order that satisfies the heap
		// condition.
		//
		public void readHeap (Scanner scanner) {			
			N = scanner.nextInt();
			for (int i = 0; i < N; i += 1) {
				values[i] = scanner.nextInt();
			}
		}
		//
		// Helper function that is used in printing the state of the heap.
		//
		public void writeHeap (PrintStream out) {
			if (N > 0) {
				out.print(values[0]);
				for (int i = 1; i < N; i += 1) {
					out.print(" " + values[i]);
				}	
			}		
			out.println();
		}		

		// ====================================================================================================================
		// Complete the methods below. Feel free to add additional methods / fields if needed.
		// ====================================================================================================================

		//
		// We assume that values are already stored in the values[] array, but they
		// do not hold the heap condition and have arbitrary order. We need to 
		// restore the heap condition using the method below.
		//
		public void buildHeap () {
			//
			// Your code goes here ...
			//
			for (int i=(N-1)/2; i>=0;i--) {
				shiftdown(i);
			}
		}		
		
		//
		// Inserts a value in the heap, and places it on the right positions such
		// that the heap condition holds.
		//
		public void insert(int value) {			 			
			//
			// Your code goes here ...
			//
			values[N]=value;
			shiftup(N);
			N++;
		}
		
		//
		// Pops the first value from the heap, restoring the heap condition
		//
		public void deleteMax () {				
			//
			// Your code goes here ...
			//
			N--;
			swap(0,N);
			values[N]=0;
			shiftdown(0);
		}
		public void shiftup(int i) {
			if(i==0)return;
			int p=parent(i);
			if(values[p]<values[i]) {
				swap(p,i);
				shiftup(p);
			}
		}
		public void shiftdown(int i) {
			int l=left(i);
			int r= right(i);
			if(l>=N) {
				
			}
			else if(r>=N) {
				if(values[i]<values[r]) {
					swap(i,r);
				}
			}
			else {
				if(!(values[i]>values[r]&&values[i]>values[l])) {
					if(values[r]<values[l]) {
						swap(i,l);
						shiftdown(l);
					}
					else {
						swap(i,r);
						shiftdown(r);
					}
				}
			}
		}
		public int parent(int i) {
			return (i-1)/2;
		}
		public int left(int i) {
			return i*2+1;
		}
		public int right(int i) {
			return i*2+2;
		}
		
		
		// ====================================================================================================================
		// End of implementation
		// ====================================================================================================================
	}

	//
	// Please, do not modify the read_and_solve method
	//
	public static void read_and_solve(InputStream in, PrintStream out) {
		//
		// Define a scanner that will read the input
		//
		Scanner scanner = new Scanner(in);
		//
		// Define a heap that will be used for the testing
		//
		MaxHeap heap = new MaxHeap();
		//
		// Read the number of test cases, and start executing
		//
		int T = scanner.nextInt();
		for (int test = 0; test < T; test += 1) {
			//
			// Read the command from the input
			//
			int command = scanner.nextInt();
			if (command == 1) {
				//
				// If command is '1' then, we must read an array from the input
				// then, build a heap out of the array, using the 'buildHeap'
				// method, and finally, output the heap on the screen.				
				//
				heap.readHeap(scanner);
				heap.buildHeap();
				heap.writeHeap(out);
			} else if (command == 2) {
				//
				// if the command is set to '2', then we read the heap values,
				// which already have partial order that satisfies the heap
				// property. Then we read we insert M elements, reading the 
				// M number first.
				//
				heap.readHeap(scanner);				
				int M = scanner.nextInt();
				for (int i = 0; i < M; i += 1) {					
					heap.insert(scanner.nextInt());
				}				
				heap.writeHeap(out);				
			} else if (command == 3) {
				//
				// if the command is set to '3', then we read the heap values,
				// which already have partial order that satisfies the heap
				// property. Then we delete the maximum elements M times, 
				// after reading the M number first.
				//
				heap.readHeap(scanner);				
				int M = scanner.nextInt();
				for (int i = 0; i < M; i += 1) {
					heap.deleteMax();
				}
				heap.writeHeap(out);
			}
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