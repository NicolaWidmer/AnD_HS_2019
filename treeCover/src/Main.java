/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * In this exercise, you are given a binary tree that stores integers (it is not a search tree,
 * so the values stored in the tree and branches' height have no special property).
 * 
 * Your task is to implement method <code>getMaximum</code> that returns the maximum sum
 * of nodes (by nodes we mean their values) that satisfy the following property:
 * No two adjacent nodes are included in the sum.
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

class Node {
	Node left; // left child
	Node right; // right child
	int value; // stored value
	
	Node next;
	
	boolean visited;
	
	int maxmit;
	int maxohne;
	
	public Node(Node l, Node r, int v) {
		left = l;
		right = r;
		value = v;
	}
	
}

class Stack{
	Node first;
	void add(Node n) {
		n.next=first;
		first=n;
	}
	Node remove() {
		Node ans=first;
		first=ans.next;
		return ans;
	}
	
}

class BinaryTree {
	Node root; // root of the binary tree
	Stack s;

	/**
	 * Constructor creates empty tree, we use <code>deserialize</code>
	 * to load the tree.
	 * 
	 * If you need, you can store additional attributes and initialize them here.
	 */
	BinaryTree() {
		root = null;
		s=new Stack();
	}
	
	/**
	 * TODO: Implement this method that finds the maximal sum of node values,
	 * such that no two adjacent nodes are included in the sum.
	 */
	int getMaximum() {
		
		if(root==null) {
			return 0;
		}
		makemaxs(root);
		//dfs(root);
		return root.maxmit;
	}
	
	void makemaxs(Node root) {
		if(root.left==null&&root.right==null) {
			root.maxmit=root.value;
			root.maxohne=0;
		}
		else if(root.left==null) {
			makemaxs(root.right);
			root.maxohne=max(0,root.right.maxmit);
			root.maxmit=max(0,root.maxohne,root.right.maxohne+root.value);
		}
		else if(root.right==null) {
			makemaxs(root.left);
			root.maxohne=max(0,root.left.maxmit);
			root.maxmit=max(0,root.maxohne,root.left.maxohne+root.value);
		}
		else {
			makemaxs(root.right);
			makemaxs(root.left);
			root.maxohne=max(0,root.left.maxmit+root.right.maxmit);
			root.maxmit=max(0,root.maxohne,root.value+root.left.maxohne+root.right.maxohne);
		}
	}
	
	/*void dfs(Node root) {
		s.add(root);
		root.visited=true;
		Node l=root.left;
		Node r=root.right;
		
		if(l!=null&&!l.visited) {
			dfs(l);
			if(r==null) {
				root.maxohne=l.maxmit;
				root.maxmit=max(root.maxohne,l.maxohne+root.value);
				s.remove();
				dfs(s.first);
			}
		}
		else if(r!=null&&!r.visited) {
			dfs(r);
			if(l==null) {
				root.maxohne=r.maxmit;
				root.maxmit=max(root.maxohne,r.maxohne+root.value);
				s.remove();
				dfs(s.first);
			}
		}
		else {
			Node n=s.remove();
			n.maxohne=l.maxmit+r.maxmit;
			n.maxmit=max(root.maxohne,root.value+l.maxohne+r.maxohne);
			dfs(s.first);
		}
	}*/
	
	
	/**
	 * If you wish, you can use the following functions for getting the maximum
	 * of all arguments.
	 */
	int max(int a, int b) {
		return a > b ? a : b;
	}

	int max(int a, int b, int c) {
		if (a > b) {
			return a > c ? a : c;
		} else {
			return b > c ? b : c;
		}
	}

	int max(int a, int b, int c, int d) {
		return max(max(a, b), max(c, d));
	}
	
	/**
	 * You can ignore this method, it is used for loading the tests.
	 * 
	 * If you need to debug the code and look on the input,
	 * take a look on <code>testSingle</code> method in
	 * <code>JUnitTest</code>.
	 */
	void deserialize(int[] serializedTree) {
		Node[] stack = new Node[(serializedTree.length / 2) + 1];
		int top = -1;
		for (int num : serializedTree) {
			if (num == -1) {
				stack[++top] = null;
			} else {
				Node r = stack[top--];
				Node l = stack[top--];
				stack[++top] = new Node(l, r, num);
			}
		}
		
		root = stack[top];
	}
}

///////////////////////////////////////////////////////////////////////
// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
// WE MAY USE LANGUAGE CONSTRUCTS THAT YOU MAY HAVE NOT SEEN SO FAR
///////////////////////////////////////////////////////////////////////

class ReadAndWrite {
	/**
	 * Parses input in form: <number of instances> 
	 * <post-fix traversal of tree of n nodes,
	 * where -1 marks nil node (leaf), positive
	 * number marks normal node with the given value>
	 * 
	 * If you need to debug the code and look on the input,
	 * take a look on <code>testSingle</code> method in
	 * <code>JUnitTest</code>.
	 */
	void readAndSolve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);

		int instances = s.nextInt();
		
		for (int i = 0; i < instances; i++) {
			int n = s.nextInt();
			int[] array = new int[n];
			
			for (int j = 0; j < n; j++) {
				array[j] = s.nextInt();
			}

			BinaryTree bt = new BinaryTree();
			bt.deserialize(array);
			out.println(bt.getMaximum());
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
