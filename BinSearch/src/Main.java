/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * You do not have to understand the whole source code to finish this task.
 * It is necessary to take a look on the attributes of the class Main
 * (e.g., <code>array</code>, the second argument is not important for this task).
 * 
 * The Java project also contains tests, in Eclipse you can run them by left-clicking:
 * test/(default package)/JUnitTest.java and selecting Run as -> JUnitTest.java.
 * 
 * The tests will show you if they passed/failed. In case of fail, you can exceptions
 * if the code did not finished, or the difference between your output and expected output.
 * The test names should help you to explain what is being tested. You can also see the content
 * of the tests, for the format of the input, see <code>read</code> method.
 * 
 * The output format is described in the comment of <code>output</code> method.
 */


//You are forbidden to add any other imports
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

class Main {
	private static int[] array; // sorted array of integers as input
	private static int[] values_to_search; // values to search in the array (do not access this array)

	/**
	 * TODO: implement this method for finding the index of an item <code>value</code>
	 * in a sorted array <code>array</code>. However, the <code>value</code> may occur several times.
	 * In this case, you have to return the right-most index such that <code>array[index] == value</code>.
	 * If the item is not in the <code>array</code>, return -1.
	 * You can write your own auxiliary methods (e.g., a recursive method with different arguments). 
	 * 
	 * @param value
	 * @return the largest index for which the array contains the parameter <code>value</code>. 
	 * 		   If <code>value</code> is not in the class <code>array</code>, return -1.
	 */
	
	private static int binary_search(int value) {
		int l, r, m, result=-1;
		l=0;
		r = array.length-1;
		while(l<=r) {
			m=(l+r)/2;
			
			if(array[m]==value) {
				result=m;
				l=m+1;
			}
			else if (array[m]>value) {
				r=m-1;
			}
			else {
				l=m+1;
			}
		}
		return result;
	}
	
	///////////////////////////////////////////////////////////////////////
	// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
	///////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses input in form:
	 * <length of input array = array_len>
	 * <array_len elements of the array, separated by comma>
	 * <length of searched values>
	 * <values_len values to search, separated by comma>
	 */
	private static void read(InputStream in) {
		FastReader s = new FastReader(in);
		int array_len = s.nextInt();
		array = new int[array_len];

		for (int idx = 0; idx < array_len; idx++) {
			array[idx] = s.nextInt();
		}

		int values_len = s.nextInt();
		values_to_search = new int[values_len];

		for (int idx = 0; idx < values_len; idx++) {
			values_to_search[idx] = s.nextInt();
		}
	}

	/**
	 * Output file has format:
	 * <space separated array of found indices for values_to_search>
	 * 
	 * @param out stream where to write
	 * @param index position of the found value, -1 if not found
	 */
	private static void output(PrintStream out, int index) {
		out.println(index);
	}

	/**
	 * Parse the input and for each value, call the binary search.
	 */
	public static void read_and_solve(InputStream in, PrintStream out) {
		read(in);

		for (int value : values_to_search) {
			output(out, binary_search(value));
		}
	}

	public static void main(String[] args) {
		read_and_solve(System.in, System.out);
	}
	
	/**
	 * Ignore this class please. It is used for input parsing. Source:
	 * https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
	 */
	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
