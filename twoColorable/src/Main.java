/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * In this task, you will work with undirected graph given by an adjacency matrix.
 * Your task is to test, if it is possible to label the graph using two colors,
 * such that no two adjacent nodes have the same color.
 *
 * Find the detailed description by the methods of class Graph.
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
 * You can use data structures we provide to you by imports, by our definitions (even
 * implicitly used classes of packages, such as methods of Object those are inherited with
 * every class declaration, Array, or String), or data structures that you write on your own.
 * Usage of common arrays (<code>type[] variable</code>) is not restricted.
 * 
 * Note that Judge is not enforcing this policy. So if you violate this policy, you may lose the
 * bonus points even if Judge accepts your submission.
 * 
 * The general exceptions out of this policy is the package Math. The exceptions that are specific
 * to a given template are written down by its imports.
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
		rw.readAndSolve(System.in, System.out);
	}
}

class Graph {
	int[][] graph; // undirected graph represented by an adjacency matrix
	int[] color;
	boolean[] handled;
	// feel free to add any other attributes

	/**
	 * The class is instantiated once for every graph.
	 */
	Graph(int[][] g) {
		graph = g;
		color=new int[g.length];
		handled=new boolean[g.length];
		color[0]=1;
		
		// initialize other attributes, if you declared any
	}
	
	/**
	 * TODO: Tests, if it is possible the given graph <code>graph</code>
	 * color using two colors. For example, graph representing a square
	 * is 2 colorable, while graph representing a triangle is not.
	 * 
	 * @return <code>true</code> when graph is two colorable,
	 *         <code>false</code> when it is not.
	 */
	boolean isTwoColorable() {
		int l=graph.length;
		boolean ans=true;
		int k=0;
		int col;
		
		for(int i=0;i<l&&ans;i++) {
			
			col=(color[k]==1)?2:1;
			handled[k]=true;
			for(int j=0;j<l&&ans;j++) {
				if(graph[k][j]==1) {
					if(color[j]!=col&&color[j]!=0) {
						ans=false;
					}
					else {
						color[j]=col;
						
					}
					
				}
				
			}
			k=getK();
			
		}
		return ans; // TODO
	}
	
	int getK() {
		int ans=-1;
		int l=handled.length;
		for(int i=0;i<l;i++) {
			if(color[i]!=0&&!handled[i])
			{
				ans=i;
				break;
			}
			
		}
		if(ans==-1) {
			for(int i=0;i<l;i++) {
				if(!handled[i]) {
					ans=i;
					break;
				}
			}
		}
		return ans;
	}
	
	
	
	/**
	 * This function is not tested by Judge or JUnitTest, it serves
	 * only for debugging - visualization by testSingle. Find the
	 * description how to use it in JUnitTest file.
	 * 
	 * @return string representation of color for given vertex
	 * <code>i</code>.
	 */
	String getNodeColor(int i) {
		// return 2 colors that you wish, e.g., "blue", "red"
		return "white";
	}
}

///////////////////////////////////////////////////////////////////////
// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
// WE MAY USE LANGUAGE CONSTRUCTS THAT YOU MAY HAVE NOT SEEN SO FAR
///////////////////////////////////////////////////////////////////////

class ReadAndWrite {
	/**
	 * Parses input in form: <number of elements = n> 
	 * <n graphs in graph6 format>
	 * 
	 */
	void readAndSolve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);

		int n = s.nextInt();
		
		for (int i = 0; i < n; i++) {
			String line = s.next();
			
			Graph g = new Graph(parseGraph(line));
			
			int res = g.isTwoColorable() ? 1 : 0;
			out.println(res);
		}
	}
	
	static int[][] parseGraph(String line) {
        ByteReader6 br6 = new ByteReader6(line);
        int n = br6.get_number();

        int[][] matrix = new int[n][n];
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
            	matrix[i][j] = 0;
            }
        }

        for (int j = 1; j < n; ++j) {
            for (int i = 0; i < j; ++i) {
                int e = br6.get_bit();
                if (e != 0) {
                	matrix[i][j] = 1;
                	matrix[j][i] = 1;
                }
            }
        }
        return matrix;
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

/**
 * Ignore this class please. It is used for input parsing. Source:
 * https://github.com/bingmann/BispanningGame/blob/master/src/net/panthema/BispanningGame/Graph6.java
 */
class ByteReader6
{
    private byte[] mBytes;
    private int mSize, mPos, mBit;

    public ByteReader6(String s6) {
        mBytes = s6.getBytes();
        mSize = s6.length();
        mPos = mBit = 0;
    }

    // ! whether k bits are available
    boolean have_bits(int k) {
        return (mPos + (mBit + k - 1) / 6) < mSize;
    }

    // ! return the next integer encoded in graph6
    // fixed by Karel Kubicek
    int get_number() {
        assert (mPos == 0);
        assert (mSize > 0);
        
    	int n = 0;
    	int nBytes;
    	byte c;
    	
    	if (mBytes[0] < 126) { // 0 <= n <= 62
    		nBytes = 1;
    	} else if (mBytes[1] < 126) { // 63 <= n <= 258047
    		nBytes = 3;
    		++mPos;
    	} else { // 258048 <= n <= 68719476735
    		nBytes = 6;
    		mPos += 2;
    	}

        
    	for (int i = 0; i < nBytes; ++i) {
    		n *= 64;
        	c = mBytes[mPos];
        	++mPos;
        	
        	assert (c >= 63);
	        c -= 63;
	        assert (c <= 63);
	        n += c;
    	}
        return n;
        
        // ignore that for 2^31 <= n <= 2^36
        // it does not fit into integer, as we cannot allocate so much memory in Java anyway
    }

    // ! return the next bit encoded in graph6
    int get_bit() {
        assert (mPos < mSize);

        byte c = mBytes[mPos];
        assert (c >= 63);
        c -= 63;
        c >>= (5 - mBit);

        mBit++;
        if (mBit == 6) {
            mPos++;
            mBit = 0;
        }

        return (c & 0x01);
    }

    // ! return the next bits as an integer
    int get_bits(int k) {
        int v = 0;

        for (int i = 0; i < k; ++i) {
            v *= 2;
            v += get_bit();
        }

        return v;
    }
}
