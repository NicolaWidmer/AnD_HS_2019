/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * In this task, you work with adirected weighted complete graph (i.e., every pair
 * of vertices is connected by an edge). Your task is to find the shortest path from
 * u to all vertices of the graph using Dijkstra's algorithm.
 * 
 * You can define new classes and methods and extend the existing. Just do not change the API.
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
// importing a comparator is allowed for this task
import java.util.Arrays;
import java.util.PriorityQueue; // allowed import
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
class Node implements Comparable<Node>{
	int key;
	int value;
	Node(int key,int value){
		this.key=key;
		this.value=value;
	}
	
	public int compareTo(Node n) {
		if(this.value==n.value)return 0;
		else if(this.value<n.value)return -1;
		return 1;
	}
	
}

class Graph {
    int[][] matrix; // directed graph represented by a distance matrix
    // if you need, you can store other attributes, for each graph,
    // method <code>dijkstra</code> is called just once

	/**
	 * The class is instantiated once for every graph.
	 * 
	 * Each graph contains 0 on the diagonal (distance u to u is 0)
	 * and <code>Integer.MAX_VALUE</code> states for missing edge.
	 * 
	 * All edge lengths are positive.
	 */
	Graph(int n) {
        matrix = new int[n][n];
        
        for(int i = 0; i < n; i++){
            for(int  j = 0; j < n; j++){
                if(i == j){
                    matrix[i][j] = 0;
                }else{
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
	}
	
	/**
     * Inserts an edge from u to v with given weight.
     */
    public void addEdge(int u, int v, int weight){
         if((u < 0 || u >= matrix.length) || (v < 0 || v >= matrix.length)) {
        	 return;
         }
         matrix[u][v] = weight;
    }
    
   /**
    * TODO: Return an array of the shortest paths from vertex
    * <code>from</code> to all vertices. That means that the
    * the returned array will on position [i] contain distance
    * from <code>from</code> to i.
    * 
    * If some vertex is not reachable, return <code>Integer.MAX_VALUE</code>
    * for that vertex.
    */
    int[] dijkstra(int from) {
    	PriorityQueue<Node> prioq= new PriorityQueue<Node>();
    	int[] ans =new int[matrix.length];
    	int l=ans.length;
    	
    	for(int i=0;i<l;i++) {
    		ans[i]=Integer.MAX_VALUE;
    	}
    	
    	Node[] spt =new Node[l];
    	
    	Node[] inq=new Node[l];
    	
    	Node K=new Node(from,0);
    	prioq.add(K);
    	inq[from]=K;
    	
    	while(prioq.size()>0) {
    		Node n=prioq.remove();
    		int node=n.key;
    		spt[node]=n;
    		ans[node]=n.value;
    		int[] nachbaren =matrix[node];
    		inq[node]=null;
    		
    		for(int i=0;i<l;i++) {
    			if(spt[i]==null&&nachbaren[i]!=Integer.MAX_VALUE) {
    				if(inq[i]==null) {
    					if(ans[i]>ans[node]+nachbaren[i]) {
    						ans[i]=ans[node]+nachbaren[i];
    						Node J=new Node(i,ans[i]);
    						inq[i]=J;
    						prioq.add(J);
    					}
    				}
    				else if(ans[i]>ans[node]+nachbaren[i]) {
						ans[i]=ans[node]+nachbaren[i];
						inq[i].value=ans[i];
						prioq.remove(inq[i]);
						prioq.add(inq[i]);
					}
    			}
    			
    		}
    		
    	}
    	
        return ans;
    }
}

///////////////////////////////////////////////////////////////////////
// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
// WE MAY USE LANGUAGE CONSTRUCTS THAT YOU MAY HAVE NOT SEEN SO FAR
///////////////////////////////////////////////////////////////////////

class ReadAndWrite {
	/**
	 * Parses input of n distance matrices
	 */
	void readAndSolve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);

		int n = s.nextInt();
		
		for (int i = 0; i < n; i++) {
			int vertices = s.nextInt();
			
			Graph g = new Graph(vertices);

			int w;
			for (int u = 0; u < vertices; u++) {
				for (int v = 0; v < vertices; v++) {
					w = s.nextInt();
					if (w != -1) {
						g.addEdge(u, v, w);
					}
				}
			}
			
			out.println(java.util.Arrays.toString(g.dijkstra(0)));
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

	char nextChar() {
		return next().charAt(0);
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
