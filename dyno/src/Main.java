//NB: Do not add a package

//NB: Importing classes in other packages is NOT ALLOWED.
//NB: Using classes in other packages in NOT ALLOWED (with the exception of the classes in java.lang.* that are imported by default)
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

// NB: For the judge to run the program, do not modify the declaration of the class Main or
// method main() below. The class has to be declared as "class Main { ... }"
// and the method as "public static void main(String[] args) { ... }"
class Dyno
{		
	/** L is the length of the desert (positions in the desert are indexed from 0 to L-1) 
	 *  D is the distance Dino can jump, i.e., if Dino is at position p and it jumps, it lands at position p+D.
	 *  C is the number of cacti in the desert
	 *  cacti is an array of C elements containing the cacti positions, in increasing order. **/
	int solve(int L, int D, int C, int[] cacti)
	{
		//TODO: Solve the problem
		int[] res =new int[L];
		Arrays.fill(res, -1);
		int l=cacti.length;
		for(int i=0;i<l;i++) {
			res[cacti[i]]=0;
		}
		res[0]=1;
		l=0;
		int k=0;
		for(int i=1;i<L;i++) {
			
				if(i>=D&&res[i]!=0&&(res[i-1]==1||res[i-D]==1)) {
					res[i]=1;
					l=0;
				}
			
			else if(res[i]!=0&&res[i-1]==1) {
				res[i]=1;
				l=0;
			}
			else {
				l++;
			}
				
			if(l==D) {
				return i-D; 
				
			}
		}
		for(int i=L;i>L-6;i--) {
			if(res[i]==1)return i;
		}
		return 0;
		
	}
}

class Main {
	public static void main(String[] args) {
		ReadAndWrite rw = new ReadAndWrite();
		rw.readAndSolve(System.in, System.out);
	}
}

class ReadAndWrite {
	/**
	 * Parses input in form: <number of elements = n> 
	 * <n elements, separated by space, -1 calls pop,
	 * otherwise the number is pushed to the SkipStack>
	 */
	void readAndSolve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);
		int ntestcases = s.nextInt();
		Dyno d = new Dyno();
		
		for(int testno=0; testno<ntestcases; testno++)
		{
			int L = s.nextInt();
			int D = s.nextInt();
			int C = s.nextInt();
			
			int[] cacti = new int[C];
			for(int j=0; j<C; j++)
				cacti[j] = s.nextInt();

			out.println(d.solve(L, D, C, cacti));
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
