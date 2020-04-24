/**
 * Using this template, your task is to implement functions denoted by TODO in the comments.
 * 
 * You do not have to understand the whole source code to finish this task.
 * It is necessary to take a look on the class <code>Heap</code>.
 *
 * As your task is to implement several methods for working with max heap.
 * 1. <code>isHeap</code> that tests if the input is max heap.
 * 2. Functions for fixing the heap property <code>siftDown</code> and <code>siftUp</code>.
 *    These functions are not tested by Judge, but we provide JUnitTests for them.
 * 3. <code>heapSort</code> for sorting the array using heap.
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


//You are forbidden to add any other imports
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


class Heap {
	public int[] array; // array where you store the heap
	// current size of the heap (it can differ from array.length during heapSort)
    public int heapSize;
    
    public Heap(int maxSize) {
		array = new int[maxSize];
	}
    
	/**
	 * TODO: For the given <code>heapArray</code>, check if it is a correct max heap.
	 * 
	 * As this method is <code>static</code>, it is independent of the attributes of
	 * class <code>Heap</code>. I.e., you should check the heap property only for
	 * the input <code>heapArray</code>, not for the the <code>array</code>.
	 * 
	 * @return <code>true</code> if the <code>heapArray</code> is a correct max heap,
	 * <code>false</code> otherwise.
	 */
    public static boolean isMaxHeap(int[] heapArray) {
    	int bound = heapArray.length;
    	for (int i =0 ; i<bound ; i++) {
    		if (i*2+1 < bound) { 
    			if (heapArray[i]<heapArray[i*2+1]) 
    			{ return false;
    				}
    		}
    		if (i*2+2 < bound) {
    			{if (heapArray[i]<heapArray[i*2+2])
    				return false;
    			}
    		}
    		
    	}
    	return true; // TODO change
    }

	/**
	 * TODO: Create a heap out of given elements in <code>array</code>.
	 */
	public void heapify(int[] Array) {
		array = Array;
		heapSize = Array.length;
		
		for (int i=(heapSize-1)/2; i>=0;i--)siftDown(i,heapSize);
		
		
		// TODO
	}
	
    /**
     * The following 6 functions can help you to navigate through the heap.
     * You do not have to use them, but they help readability of your code.
     * 
     * Warning: they do not check the bounds of the heap.
     */
	private static int parentIndex(int index) {
        return ((index + 1)/2) - 1;
    }

    private static int leftIndex(int index) {
        return ((index + 1)*2) - 1;
    }

    private static int rightIndex(int index) {
        return (index + 1)*2;
    }
    
    private int getParent(int index) {
        return array[parentIndex(index)];
    }

    private int getLeft(int index) {
        return array[leftIndex(index)];
    }

    private int getRight(int index) {
        return array[rightIndex(index)];
    }
	
	/**
	 * TODO (voluntary, not tested by Judge, but helps with <code>heapify</code>):
	 * Move a node down in the tree, as long as needed. Used to restore heap
	 * condition of element that is lower than its children. The position
	 * of such element is <code>index</code>, the rest of the heap is
	 * valid max heap.
	 */
    public void siftDown(int index, int bis) {
    	int r =rightIndex(index);
    	int l =leftIndex(index);
    	int lenght=array.length;
    	if (l<bis) {
    		int max= l;
    		if (r<bis) {
    			if (getLeft(index)<getRight(index)) max=r;
    			
    		}
    		if (array[index]<array[max]) { 
				swap(max, index);
				siftDown(max,bis);
			}
    	}
    	
        // TODO
    }
	
	/**
	 * TODO (voluntary, not tested by Judge, not needed):
	 * Move a node up in the tree, as long as needed. Used to restore heap
	 * condition of element that is higher than its parent. The position
	 * of such element is <code>index</code>, the rest of the heap is
	 * valid max heap.
	 * 
	 * Called "sift" because node moves up the tree until it reaches
	 * the correct level, as in a sieve.
	 */
    public void siftUp(int index) {
    	int i =parentIndex(index);
    	if (i>=0) {
    		if (array[index]>array[i]) {
    		swap(index, i);
    		siftUp(i);
    		}
    	}
        // TODO
    }
	
	/**
	 * TODO: Sort the array <code>array</code> (attribute of this class)
	 * using heap sort. Return the sorted <code>array</code> as output.
	 */
    public int[] heapSort() {
		// TODO sort before return
    	
    	array=this.array;
    	heapSize--;
    	while (heapSize >= 0) {
    		
    		swap(heapSize, 0);
    		
    		siftDown(0,heapSize);
    		heapSize--;
    		
    	}
    	
		return array;
    }
	
	private void swap(int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
	
	///////////////////////////////////////////////////////////////////////
	// DO NOT MODIFY THE FOLLOWING CODE, YOU CAN COMPLETELY IGNORE IT
	///////////////////////////////////////////////////////////////////////
	
	/**
	 * Recursive implementation of generation of graphviz visualization.
	 */
	public static void toGraphvizRec(int index, PrintWriter writer, int[] array) {
	    writer.print("\"" + array[index] + "\"\n");      
	    if(leftIndex(index) < array.length) {
	        writer.print("\"" + array[index] + "\" -> \"" +  array[leftIndex(index)] + "\"\n");
	        toGraphvizRec(leftIndex(index), writer, array);
	    }
	    if(rightIndex(index) < array.length) {
	        writer.print("\"" + array[index] + "\" -> \"" +  array[rightIndex(index)] + "\"\n");
	        toGraphvizRec(rightIndex(index), writer, array);
	    }
	}
	
	/**
	 * This function visualizes heap from <code>array</code> as tree. It is used for
	 * debugging convenience.
	 */
	public static void toGraphviz(String filename, int[] array) {
	    try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
	        writer.print("digraph Heap {\n");
	        writer.print("node [color=lightblue2, style=filled];\n");
	        toGraphvizRec(0, writer, array);
	        writer.print("}\n");
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class Main {
	private static Heap heap; // instance of heap
	private static int[] array; // array of integers as input

	/**
	 * Parses input in form:
	 * <number of instances = n>
	 * <n iterations of: length of input array = array_len> 
	 * <array_len elements of the array, separated by space>
	 */
	private static void read(FastReader s) {
		int array_len = s.nextInt();
		array = new int[array_len];

		for (int idx = 0; idx < array_len; idx++) {
			array[idx] = s.nextInt();
		}
	}

	/**
	 * Outputs result in form:
	 * true/false (isHeap)
	 * sorted array in square brackets, comma separated integers
	 */
	private static void outputArray(PrintStream out, int[] array) {
		out.print("[");
		for (int i = 0; i < array.length; ++i) {
			out.print(array[i]);
			if (i < array.length - 1)
				out.print(", ");
		}
		out.print("]");
	}

	/**
	 * Parse the input and call the exponentiation.
	 */
	public static void read_and_solve(InputStream in, PrintStream out) {
		FastReader s = new FastReader(in);

		int instances = s.nextInt();
		
		for (int i = 0; i < instances; ++i) {
			read(s);
			
			out.println(Heap.isMaxHeap(array));
			
			heap = new Heap(array.length);
			heap.heapify(array);
			outputArray(out, heap.heapSort());
			out.println();
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
