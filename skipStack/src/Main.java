/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * Your task is to implement a slightly modified version of stack for storing integers.
 * That means choosing your representation of the data structure, and implementing the 
 * methods push and pop.
 *
 * Find the detailed description by the methods of class SkipStack. As in this task you
 * have to select the class attributes on your own, please read the comments how to do so
 * and please note the changed imports policy below.
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
		rw.readAndSolve(System.in, System.out);
	}
}
class Node{
	Node next;
	int v;
	Node(int value){
		v=value;
	}
	
}

class SkipStack {
	/**
	 * Please declare all the class attributes you need here.
	 * The only API restrictions of the task are:
	 * Constructor without arguments, push(int) and int pop().
	 */
	
	/**
	 * TODO: Initialize the structure as you need.
	 */
	Node first;
	int size=0;
	SkipStack() {
		
		// TODO: initialize the stack variables
	}
	
	
	/**
	 * TODO: Removes an item from the stack and returns its value
	 * As item for removal, the larger out of the top two items is selected.
	 * If the stack contains only a single item, the method works the same as
	 * for normal stack.
	 * If the stack is empty, return -1.
	 * 
	 * Example (stack top is on the right):
	 * 5 3 8 4 5
	 * pop() return 5 and produces the following stack:
	 * 5 3 8 4
	 * pop() return 8 and produces the following stack:
	 * 5 3 4
	 * pop() return 4 and produces the following stack:
	 * 5 3
	 * pop() return 5 and produces the following stack:
	 * 3
	 * pop() return 3 and leaves the stack empty
	 * 
	 * pop() return -1 and leaves the stack empty
	 */
	int pop() {
		if(size==0)return -1;
		int ans=this.first.v;
		
		if(size==1) {
			this.first=null;
			size--;
			return ans;
			
		}
		
		if(this.first.next.v<ans) {
			this.first=this.first.next;
		}
		
		else {
			ans=this.first.next.v;
			this.first.next=this.first.next.next;
		}
		size--;
		return ans;
	}
	
	/**
	 * TODO:
	 * Inserts the passed <code>item</code> to the stack, to one the two highest positions.
	 * If the value <code>item</code> is at least as high as the highest item of the stack,
	 * then it is inserted on top, otherwise <code>item</code> is inserted below the top.
	 * In case of an empty stack, <code>push</code> works as for a normal stack.
	 *
	 * Example (stack top is on the right, starting with an empty stack):
	 * push(5)
	 * 5
	 * push(4)
	 * 4 5
	 * push(6)
	 * 4 5 6
	 * push(2)
	 * 4 5 2 6
	 * push(1)
	 * 4 5 2 1 6
	 */
	void push(int item) {
		if (size==0)this.first= new Node(item);
		else {
			if(this.first.v>item) {
				Node current=this.first.next;
				this.first.next= new Node(item);
				this.first.next.next=current;
			}
			else {
				Node current=this.first;
				this.first= new Node(item);
				this.first.next=current;
			}
		}
		size++;
	}
}

///////////////////////////////////////////////////////////////////////
// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
// WE MAY USE LANGUAGE CONSTRUCTS THAT YOU MAY HAVE NOT SEEN SO FAR
///////////////////////////////////////////////////////////////////////

class ReadAndWrite {
	/**
	 * Parses input in form: <number of elements = n> 
	 * <n elements, separated by space, -1 calls pop,
	 * otherwise the number is pushed to the SkipStack>
	 */
	void readAndSolve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);
		SkipStack ss = new SkipStack();

		int n = s.nextInt();
		
		for (int i = 0; i < n; i++) {
			int x = s.nextInt();
			if (x == -1) {
				out.print(ss.pop() + " ");
			} else {
				ss.push(x);
			}
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
