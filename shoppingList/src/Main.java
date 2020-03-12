/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * Please find the description by the class <code>ShoppingList</code>. Also note the changed
 * imports policy below.
 * 
 * The Java project also contains tests, in Eclipse you can run them by left-clicking:
 * test/(default package)/JUnitTest.java and selecting Run as -> JUnitTest.java.
 * 
 * The tests will show you if they passed/failed. In case of fail, you can see an exception
 * if the code did not finished, or the difference between your output and expected output.
 * The test names should help you to explain what is being tested. You can also see the content
 * of the tests, for the format of the input, see <code>read</code> method.
 * 
 * The output format is described in the comment of <code>output</code> method.
 */

/**
 * This imports policy applies to all programming assignments and the exam.
 * 
 * You are forbidden to add any other import statements. Make sure that you do not leave any
 * imports commented out, or that your IDE does not add any automatically. You can recognize
 * allowed imports by the <code>// allowed import</code> comment.
 * Calls by the fully qualified name are treated as imports as well. Those are, for example,
 * <code>java.util.Arrays.sort</code>, or <code>java.util.Arrays.binarySearch</code>.
 * You can use only data structures we provide to you by imports, by our definitions (even
 * implicitly used classes of packages, such as methods of Object those are inherited with
 * every class declaration, Array, or String), or data structures that you write on your own.
 * 
 * Note that Judge is not enforcing this policy. So if you violate this policy, you may lose the
 * bonus points even if Judge accepts your submission.
 * 
 * The general exceptions out of this policy is the package Math. The exceptions that are specific
 * to a given template are written down by its imports.
 * 
 * 
 * Regarding rounding functions, please consider this suggestion from our experience: 
 * Using the floating-point rounding functions for indexing, i.e., integer arithmetic, can be
 * a double-edged sword. Using ceil and floor often comes from the direct translation of
 * the pseudocode. However, such direct translation does not work in general. For example,
 * in pseudocode, we index from 1, Java indexes from 0. When you use such direct translation
 * of pseudocode, you anyway have to think about the content, so you can also substitute rounding
 * functions by integer division. Integer division is generally easier to debug, and it is also
 * more efficient. Please see some examples of equivalent codes from your codes:
 * 
 * index = (int)Math.ceil((double)index/2)-1;
 * index = (index+1)/2-1;
 * // this parent indexing for heap has a clear intuition from pseudocode: first add 1 to translate 
 * // to indexing from 1, and eventually reduce 1 to translate it back to 0-indexing.
 * 
 * int middle = Math.floor((right+left)/2);
 * int middle = (right+left)/2;
 * 
 * int middle = (int) Math.ceil((start+end) / 2.0);
 * int middle = (start+end+1) / 2;
 * 
 * A very technical note, you do not have to understand this in our course: last two implementations
 * are prone to integer overflow, the ideal implementation is the following:
 * 
 * int middle = right + (left-right)/2;
 */
import java.util.StringTokenizer; // allowed import
import java.io.BufferedReader; // allowed import
import java.io.IOException; // allowed import
import java.io.InputStream; // allowed import
import java.io.InputStreamReader; // allowed import
import java.io.PrintStream; // allowed import
import java.lang.Math; // allowed import

class Main {
	public static void main(String[] args) {
		ReadAndWrite rw = new ReadAndWrite();
		rw.read_and_solve(System.in, System.out);
	}
}

class ShoppingList {
	// k is the number of shops, it is either 2 or 3
	int k;
	// n is the number of products, it is always multiple of k
	int n;
	// prices is a matrix of dimension k*n.
	// prices[i][j] represents the price for which you can buy
	// the j-th product in the shop i
	int[][] prices;

	/**
	 * Constructor passes the prices and initializes k and n.
	 */
	ShoppingList(int[][] prices) {
		this.prices = prices;
		k = prices.length;
		n = prices[0].length;
	}

	/**
	 * TODO: Your task is to find the minimal price for buying all products
	 * from your list (positions 0 to n-1), such that you buy equal number
	 * of products (n/k) in every of the given k shops.
	 * That means that for each of the products j, you have to select the price
	 * prices[i][j] for i in range of k shops, such that the sum of all the
	 * prices is minimal.
	 * 
	 * @return minimal price for the given prices.
	 */
	int solve() {
		if(k==2) {
		int[][] ans=new int[n/k+1][n+1];
		for(int j=1;j<n+1;j++) {
			ans[0][j]=prices[0][j-1]+ans[0][j-1];
		}
		for (int i=1;i<n/k+1;i++) {
			for(int j=1;j<n+1;j++) {
					if(j==i) {
						ans[i][j]=ans[i-1][j-1]+prices[1][j-1];
					}
					else {
					ans[i][j]=min(ans[i-1][j-1]+prices[1][j-1],ans[i][j-1]+prices[0][j-1]);
					}
				}
			}
		return ans[n/k][n]; // TODO
		}
		
		else {
			int[][][] ans=new int[n/k+1][n/k+1][n+1];
			for(int j=1;j<n+1;j++) {
				ans[0][0][j]=prices[0][j-1]+ans[0][0][j-1];
			}
			for(int l=0;l<n/k+1;l++) {
				for (int i=0;i<n/k+1;i++) {
					for(int j=1;j<n+1;j++) {
						if(i==0&&l==0||(l+i)>j) {
						}
						else if(l==0&&i==j) {
							ans[l][i][j]=ans[l][i-1][j-1]+prices[1][j-1];
						}
						else if(i==0&&l==j) {
							ans[l][i][j]=ans[l-1][i][j-1]+prices[2][j-1];
						}
						else if(l==0) {
							ans[l][i][j]=min(ans[l][i-1][j-1]+prices[1][j-1],ans[l][i][j-1]+prices[0][j-1]);
						}
						else if(i==0) {
							ans[l][i][j]=min(ans[l-1][i][j-1]+prices[2][j-1],ans[l][i][j-1]+prices[0][j-1]);	
						}
						else if(l+i==j) {
							ans[l][i][j]=min(ans[l-1][i][j-1]+prices[2][j-1],ans[l][i-1][j-1]+prices[1][j-1]);
						}
						else {
							ans[l][i][j]=min(ans[l-1][i][j-1]+prices[2][j-1],ans[l][i-1][j-1]+prices[1][j-1],ans[l][i][j-1]+prices[0][j-1]);
						}	
					}	
				}
			}
			return ans[n/k][n/k][n];
		}
	}

	/**
	 * If you wish, you can use the following functions for getting the minimum
	 * of all arguments.
	 */
	int min(int a, int b) {
		return a < b ? a : b;
	}

	int min(int a, int b, int c) {
		if (a < b) {
			return a < c ? a : c;
		} else {
			return b < c ? b : c;
		}
	}
}

///////////////////////////////////////////////////////////////////////
// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
// WE MAY USE LANGUAGE CONSTRUCTS THAT YOU MAY HAVE NOT SEEN SO FAR
///////////////////////////////////////////////////////////////////////

class ReadAndWrite {
	/**
	 * Parses input in the form:
	 * <i>
	 * <
	 * <number of shops = 2 or 3>
	 * <array_len>
	 * <array_len elements of the array, separated by space>
	 * repreated i times>
	 */
	int[][] read(FastReader s) {
		int n = s.nextInt();
		int k = s.nextInt();
		int[][] prices = new int[n][k];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				prices[i][j] = s.nextInt();
			}
		}

		return prices;
	}

	/**
	 * Parse the input and call the solution.
	 */
	void read_and_solve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);

		int instances = s.nextInt();

		for (int i = 0; i < instances; ++i) {
			int[][] prices = read(s);

			ShoppingList sl = new ShoppingList(prices);

			out.println(sl.solve());
		}

	}
}

/**
 * Ignore this class please. It is used for input parsing. Source:
 * https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
 */
class FastReader {
	BufferedReader br;
	StringTokenizer st;

	FastReader(InputStream in) {
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
