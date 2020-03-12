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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;


class Silhouette {
	/*
	 * For efficiency reasons, silhouettes are represented by ArrayList,
	 * a data structure with in average constant append operation 
	 * (adding an element to the end of array). Unfortunately, in Java it
	 * has a different API from a normal array.
	 * 
	 * Instead of access by <code>array[index]</code>, you have to use 
	 * <code>array.get(index)</code> method.
	 * 
	 * To append a new item, you can use <code>array.add(number)</code>
	 * to insert it to the end (in average constant time complexity),
	 * or <code>array.add(index, number)</code> which inserts to given index,
	 * at the cost of moving all succeeding elements, therefore linear complexity.
	 * 
	 * To change item at given position, use <code>array.set(index, number)</code>.
	 * 
	 * And to get the length of the array, instead of <code>array.length</code>,
	 * use <code>array.size()</code>.
	 * 
	 * The last drawback of ArrayList is that it stores object of Integer class.
	 * You can create these objects simply by passing int. Comparing two integers
	 * can be done by <, <=, >=, > operators, or for equality you have to use
	 * method equals. For example for testing if the beginning and end of a building
	 * are the same, you have to write
	 * <code>silhouette.get(0).equals(silhouette.get(2))</code> instead of a simple
	 * <code>silhouette.get(0) == silhouette.get(2)</code>.
	 */
	public ArrayList<Integer> silhouette;
	
	public Silhouette(int[] arr) {
		silhouette = new ArrayList<Integer>();
		for(int x : arr) {
	         silhouette.add(x);
	    }
	}
	
	public Silhouette(ArrayList<Integer> arr) {
		silhouette = arr;
	}
	
	public void append(Silhouette other) {
		int cur_height = silhouette.get(silhouette.size() - 2);
		int cur_end = silhouette.get(silhouette.size() - 1);
		
		int new_beg = other.silhouette.get(0);
		int new_height = other.silhouette.get(1);
		int new_end = other.silhouette.get(2);
		
		if (new_beg > cur_end) { // no overlap
			silhouette.add(0);
			silhouette.add(new_beg);
			silhouette.add(new_height);
			silhouette.add(new_end);
		} else if (new_beg == cur_end) { // end is new beginning
			if (cur_height == new_height) { // same height overwrite
				silhouette.set(silhouette.size() - 1, Math.max(new_end, cur_end)); // change the end
			} else {
				silhouette.add(new_height);
				silhouette.add(new_end);
			}
		} else { // overlap
			int i = silhouette.size() - 1;
			while (silhouette.get(i) > new_beg
				   && (i > silhouette.size() - 2 ||
					   new_height > silhouette.get(i+1))) {
				i -= 2;
			}
			i += 2;
			if (i >= silhouette.size() - 1 && new_height <= silhouette.get(i-1)) { // we are on the end, append
				if (new_end > cur_end) { // new is longer
					if (cur_height == new_height) { // same height overwrite
						silhouette.set(silhouette.size() - 1, Math.max(new_end, cur_end)); // change the end
					} else {
						silhouette.add(new_height);
						silhouette.add(new_end);
					}
				} // else ignore
			} else {
				if (new_height > silhouette.get(i-1)) {
					if (new_beg < silhouette.get(i-2)) {
						silhouette.set(i, new_beg); // change the beg
					} else { // splitting into 2 parts by inserting one building inside
						silhouette.add(i, new_beg);
						silhouette.add(i+1, silhouette.get(i-1));
					}
				}
				while (i+2 < silhouette.size() && silhouette.get(i+2) <= new_end) {
					silhouette.remove(i+1);
					silhouette.remove(i+1);
				}
				if (new_end > silhouette.get(i)) {
					if (silhouette.get(i-1).equals(new_height)) {
						silhouette.set(i, new_end); // change the end
					} else {
						// insert a building inside
						silhouette.add(i+1, new_height); // change the height
						silhouette.add(i+2, new_end); // change the end
					}
				}
			}
		}
	}
	
	public Silhouette merge(Silhouette other) {
		int i = 0;
		int j = 0;
		
		if (silhouette.size() < 3) {
			return other;
		}
		if (other.silhouette.size() < 3) {
			return this;
		}
		
		// select the silhouette which we take first
		boolean isThis = this.silhouette.get(0) < other.silhouette.get(0)
				|| this.silhouette.get(0).equals(other.silhouette.get(0))
					&& this.silhouette.get(1) > other.silhouette.get(1)
				|| this.silhouette.get(0).equals(other.silhouette.get(0))
					&& this.silhouette.get(1).equals(other.silhouette.get(1))
					&& this.silhouette.get(2) < other.silhouette.get(2);
		Silhouette first = null;
		if (isThis) {
			first = this;
			i = 2;
		} else {
			first = other;
			j = 2;
		}
		
		Silhouette out = new Silhouette(new int[] {first.silhouette.get(0), first.silhouette.get(1), first.silhouette.get(2)});
		
		while (i+1 < this.silhouette.size() && j+1 < other.silhouette.size()) {
			if (this.silhouette.get(i) < other.silhouette.get(j)
				|| (this.silhouette.get(i).equals(other.silhouette.get(j))
					&& this.silhouette.get(i+1) > other.silhouette.get(j+1))
				|| (this.silhouette.get(i).equals(other.silhouette.get(j))
					&& this.silhouette.get(i+1).equals(other.silhouette.get(j+1))
					&& this.silhouette.get(i+2) < other.silhouette.get(j+2))) { // this
				out.append(new Silhouette(new int[] { this.silhouette.get(i), this.silhouette.get(i+1), this.silhouette.get(i+2) }));
				i += 2;
			} else {
				out.append(new Silhouette(new int[] { other.silhouette.get(j), other.silhouette.get(j+1), other.silhouette.get(j+2) }));
				j += 2;
			}
		}
		
		
		while (i+1 < this.silhouette.size()) {
			out.append(new Silhouette(new int[] { this.silhouette.get(i), this.silhouette.get(i+1), this.silhouette.get(i+2) }));
			i += 2;
		}
		
		while (j+1 < other.silhouette.size()) {
			out.append(new Silhouette(new int[] { other.silhouette.get(j), other.silhouette.get(j+1), other.silhouette.get(j+2) }));
			j += 2;
		}
		
		return out;
	}
}

class Main {
	private static Silhouette[] array; // array of building silhouette triples as input
	
	/*
	 * Divide & conquer solution
	 */
	private static Silhouette merge(int from, int to) {
		if (from == to) {
			return array[from];
		}
		// nothing before recursion
		int mid = from + (to - from)/2;
		Silhouette s1 = merge(from, mid);
		Silhouette s2 = merge(mid+1, to);
		//after
		return s1.merge(s2);
	}
	
	/*
	 * This solution is based on sorting the building in the beginning and then
	 * extending the silhouette. Potentially, it can be quadratic if the length
	 * of single building can span over all buildings.
	 * 
	 * For the case of buildings of length 100 (as is the generated input),
	 * the running time is faster than divide & conquer solution.
	 */
	private static Silhouette greedy() {
		Arrays.sort(array, new Comparator<Silhouette>() {
			@Override
			public int compare(Silhouette s1, Silhouette s2) {
				return s1.silhouette.get(0) < s2.silhouette.get(0) ? -1 : s1.silhouette.get(0) > s2.silhouette.get(0) ? 1 : s1.silhouette.get(1) > s2.silhouette.get(1) ? -1 : 1;
			}
		});

		Silhouette out = null;
		for (Silhouette silhouette : array) {
			if (out == null) {
				out = silhouette;
			} else {
				out.append(silhouette);
			}
		}
		return out;
	}
	
	/*
	 * Solution that runs in O(n + max_sihouette_position), so it does not satisfy
	 * the time limit by Judge.
	 * 
	 * This solution is similar to we solve the problem visually.
	 */
	private static Silhouette graphical() {
		int m = 0;
		for (Silhouette s : array) {
			m = Math.max(m, s.silhouette.get(2));
		}
		
		m += 2;
		
		int[] out = new int[m];
		for (int i = 0; i < m; ++i) {
			out[i] = 0;
		}

		for (Silhouette s : array) {
			for (int j = s.silhouette.get(0); j < s.silhouette.get(2); ++j) {
				out[j] = Math.max(out[j], s.silhouette.get(1));
			}
		}
		
		int prev = 0;
		ArrayList<Integer> sOut = new ArrayList<Integer>();
		for (int i = 0; i < m; ++i) {
			if (out[i] != prev) {
				sOut.add(i);
				sOut.add(out[i]);
				prev = out[i];
			}
		}
		
		return new Silhouette(sOut);
	}
	
	private static void clean(Silhouette in) {
		int len_prev = 0;
		
		// remove abundant positions or heights, until fixpoint is reached
		while (in.silhouette.size() != len_prev) {
			len_prev = in.silhouette.size();
			
			Integer prev = in.silhouette.get(0);
			for (int i = 2; i < in.silhouette.size() - 1; i+=2) {
				if (in.silhouette.get(i).equals(prev)) {
					in.silhouette.remove(i-2);
					in.silhouette.remove(i-2);
				}
				prev = in.silhouette.get(i);
			}
			
			prev = in.silhouette.get(1);
			for (int i = 3; i < in.silhouette.size() - 2; i+=2) {
				if (in.silhouette.get(i).equals(prev)) {
					in.silhouette.remove(i-2);
					in.silhouette.remove(i-2);
				}
				prev = in.silhouette.get(i);
			}
		}
		
	}
	/**
	 * TODO: Implement an algorithm to compute the silhouette of a given set of rectangular buildings
	 * in <code>array</code>. Every building is represented as triple (beginning, height, end).
	 * See the visual explanation in the assignment pdf.
	 * 
	 * If you wish every input silhouette in <code>array</code> be terminated with final 0, 
	 * thus having form (beginning, height, end, 0), please search for the following comment in read method:
	 * "// uncomment this if you want trailing 0".
	 * 
	 * The output format is an array consists of pairs of positions where the height of silhouette
	 * changes and the height itself. Every silhouette is therefore terminated by height 0.
	 * 
	 */
	private static Silhouette evaluate() {
		//Silhouette res = merge(0, array.length-1);
		Silhouette res = greedy();
		res.silhouette.add(0);
		clean(res);
		
		//Silhouette res = graphical();
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////
	// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
	///////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses input in form:
	 * <length of input array = array_len>
	 * <array_len Silhouette elements of the array>
	 */
	private static void read(InputStream in) {
		FastReader s = new FastReader(in);
		int array_len = s.nextInt();
		array = new Silhouette[array_len];

		for (int idx = 0; idx < array_len; idx++) {
			int from = s.nextInt();
			int height = s.nextInt();
			int to = s.nextInt();
			array[idx] = new Silhouette(new int[] {from, height, to}); // comment this out if you want trailing 0
			//array[idx] = new Silhouette(new int[] {from, height, to, 0}); // uncomment this if you want trailing 0
		}
	}

	/**
	 * Output file has format:
	 * [<comma separated array of resulting silhouette>]
	 */
	private static void output(PrintStream out, Silhouette silhouette) {
		out.println(silhouette.silhouette.toString());
	}

	/**
	 * Parse the input and for each value, call the <code>evaluate</code>.
	 */
	public static void read_and_solve(InputStream in, PrintStream out) {
		read(in);

		output(out, evaluate());
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
