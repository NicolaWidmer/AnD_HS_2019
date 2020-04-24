/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * You do not have to understand the whole source code to finish this task.
 * It is necessary to take a look on the class <code>Complex</code> inside of 
 * class <code>Main</code> (we write this as path <code>Main.Complex</code>).
 *
 * As your task is to implement exponentiation, take a look on the class attributes
 * <code>base</code> and <code>exponent</code>.
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
import java.math.BigInteger;

class Main {
	static class Complex {
		/**
		 * Class <code>Complex</code> represents Gaussian integer, i.e., a complex number
		 * whose real (<code>a</code>) and imaginary (<code>b</code>) parts are both integers.
		 * 
		 * Instead of basic type <code>int</code>, we use <code>BigInteger</code>,
		 * which can represent arbitrary long integer (as the values grow rapidly with the exponentiation).
		 * 
		 * Processing mathematical operations with <code>BigInteger</code> is requiring
		 * to use methods as <code>add</code> and <code>multiply</code>, instead of operators
		 * as + and *, respectively.
		 * 
		 * To illustrate how to work with BigInteger, we provide you exemplary method
		 * <code>add</code>. You won't use this method anywhere, but it demonstrates how to
		 * use methods instead of operators. Please notice that the class <code>Complex</code>
		 * follows the same pattern of usage as <code>BigInteger</code>.
		 */
		public BigInteger a; // real part of the complex number
		public BigInteger b; // imaginary part of the complex number

		/**
		 * To create a complex number, pass it the tuple of <code>BigIntegers</code>.
		 */
		public Complex(BigInteger in_a, BigInteger in_b) {
			a = in_a;
			b = in_b;
		}

		/**
		 * Auxiliary method that creates number 1+0i. It demonstrates how to construct
		 * <code>Complex</code> from tuple of <code>BigInteger</code>, whose are created
		 * from strings.
		 * 
		 * @return Complex(1, 0i)
		 */
		public static Complex unit() {
			return new Complex(new BigInteger("1"), new BigInteger("0"));
		}

		/**
		 * Adds complex number <code>other</code> to <code>this</code>
		 * Used just to illustrate how to add <code>BigIntegers</code>.
		 * Not used for exponentiation.
		 * 
		 * @param other
		 * @return a new complex number <code>this + other</code>
		 */
		public Complex add(Complex other) {
			// a = this.a + other.a
			// b = this.b + other.b
			return new Complex(this.a.add(other.a), this.b.add(other.b));
		}

		/**
		 * TODO: Multiply complex number <code>this</code> by <code>other</code>
		 * Use this method for the exponentiation as:
		 * <code>Complex base = new Complex(...);</code>
		 * <code>Complex base_square = base.multiply(base);</code>
		 * 
		 * @param other
		 * @return a new complex number <code>this * other</code>
		 */
		public Complex multiply(Complex other) {
		BigInteger aa=this.a.multiply(other.a);
		BigInteger bb=this.b.multiply(other.b);
		BigInteger ab=this.a.multiply(other.b);
		BigInteger ba=this.b.multiply(other.a);
		BigInteger r=aa.subtract(bb);
		BigInteger i=ab.add(ba);
			return  new Complex (r, i);
		}
	}

	private static Complex base; // Base for the exponentiation
	private static int exponent; // Exponent for the exponentiation

	/**
	 * TODO: For the given complex number <code>base</code> and integer <code>exponent</code>,
	 * compute the value of <code>base ^ exponent</code>.
	 * 
	 * Please note that the complex numbers for this exercise are represented by class Complex,
	 * with two <code>BigInteger</code> attributes <code>a</code> for real part and <code>b</code>
	 * for the imaginary one.
	 * 
	 * The class <code>Complex</code> defines function <code>multiply</code>, which you should use. 
	 * 
	 * You can write your own auxiliary method (e.g., a recursive method with different arguments).
	 * 
	 * @return <code>base^exponent</code>
	 */
	
	private static Complex pow(int n) {
		Complex temp;
		
		if(n==1) {
			return base;
		}
		else if(n%2==0) {
			n/=2;
			temp=pow(n);
			return temp.multiply(temp);
		}
		else {
			n=n/2;
			temp=pow(n);
			return temp.multiply(temp.multiply(base));
		}
	}
	
	///////////////////////////////////////////////////////////////////////
	// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
	///////////////////////////////////////////////////////////////////////

	/**
	 * Parses input in form:
	 * <real part> <imaginary part>
	 * <exponent>
	 */
	private static void read(InputStream in) {
		FastReader s = new FastReader(in);
		BigInteger base_a = s.nextBigInt();
		BigInteger base_b = s.nextBigInt();

		exponent = s.nextInt();
		base = new Complex(base_a, base_b);
	}

	/**
	 * Outputs result in form:
	 * <real part> <imaginary part>i
	 * 
	 * @param out stream where to write
	 * @param number result of exponentiation of base^exponent
	 */
	private static void output(PrintStream out, Complex number) {
		out.println(number.a.toString() + " " + number.b.toString() + 'i');
	}

	/**
	 * Parse the input and call the exponentiation.
	 */
	public static void read_and_solve(InputStream in, PrintStream out) {
		read(in);

		output(out, pow(exponent));
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

		BigInteger nextBigInt() {
			return new BigInteger(next());
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
