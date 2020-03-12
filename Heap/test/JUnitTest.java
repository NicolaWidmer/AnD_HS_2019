import static org.junit.Assert.*;
import org.junit.Test;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

public class JUnitTest {

	final String newLine = System.getProperty("line.separator");
	String pathPrefix = null;
	
	public JUnitTest () {
		Path path = FileSystems.getDefault().getPath("./test/files/").toAbsolutePath();		
		pathPrefix = path.toString();
	}
	
	private static String normalizeLineEnds(String s) {
	    return s.replace("\r\n", "\n").replace('\r', '\n');
	}
	
	private void checkEquality(String testFileName) {		
		try {
			//	
			// Define the input and the output files.
			//
			File inputFile  = new File (pathPrefix + File.separator + testFileName + ".in.txt");
			File outputFile = new File (pathPrefix + File.separator + testFileName + ".out.txt");
			//
			// Read the expected output
			//
			String expectedResult = new String(java.nio.file.Files.readAllBytes(outputFile.toPath()));
			//
			// Define the input and output streams
			// 
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream in = new FileInputStream(inputFile);		
			PrintStream out = new PrintStream(baos);			
			//
			// Perform the test
			//		
			Main.read_and_solve(in, out);
			//
			// Flush the output and obtain the data
			//
			out.flush();
			out.close();
			String result = baos.toString();
			//
			// Now check for the actual assertion
			//
			assertEquals(normalizeLineEnds(expectedResult), normalizeLineEnds(result));		
		} catch (IOException e) {			
			fail(e.getMessage());
			
		}		
	}
	
	@Test
	public void testIsHeap() {
		int[] array;
		
		array = new int[] {3, 2, 4};
		if (Heap.isMaxHeap(array)) {
			Heap.toGraphviz(pathPrefix + "/isHeap1False.dot", array);
			fail("Array " + Arrays.toString(array) + " is not a max heap, you returned true. See " + pathPrefix + "/isHeap1False.dot for visualisation.");
		}

		array = new int[] {3, 2, 1};
		if (!Heap.isMaxHeap(array)) {
			Heap.toGraphviz(pathPrefix + "/isHeap2True.dot", array);
			fail("Array " + Arrays.toString(array) + " is a max heap, you returned false. See " + pathPrefix + "/isHeap2True.dot for visualisation.");
		}

		array = new int[] {10, 5, 9, 3, 2, 8, 6, 1, 2, 1};
		if (!Heap.isMaxHeap(array)) {
			Heap.toGraphviz(pathPrefix + "isHeap3True.dot", array);
			fail("Array " + Arrays.toString(array) + " is a max heap, you returned false. See " + pathPrefix + "/isHeap3True.dot for visualisation.");
		}

		array = new int[] {10, 5, 9, 3, 6, 8, 6, 1, 2, 1};
		if (Heap.isMaxHeap(array)) {
			Heap.toGraphviz(pathPrefix + "isHeap4False.dot", array);
			fail("Array " + Arrays.toString(array) + " is not a max heap, you returned true. See " + pathPrefix + "/isHeap4False.dot for visualisation.");
		}
	}
	
	private boolean isSorted(int[] array) {
		int curr = array[0];
		
		for (int a: array) {
			if (a < curr) {
				return false;
			}
			curr = a;
		}
		
		return true;
	}
	
	@Test
	public void testHeapify() {
		int[] array;
		Heap h = new Heap(0);
		
		array = new int[] {3, 2, 4};
		h.heapify(array);
		if (!Heap.isMaxHeap(h.array)) {
			Heap.toGraphviz(pathPrefix + "/testHeapify1.dot", array);
			fail("Array that you created by heapify " + Arrays.toString(h.array) + " is not a max heap. See " + pathPrefix + "/isHeap1False.dot for visualisation.");
		}
		if (isSorted(h.array)) {
			fail("This is cheating, you sorted the array, it is a valid heap, but you have not used the proper heapify algorithm!");
		}
		
		array = new int[] {1, 54, 13, 95, 51, 30, 32, 89, 18, 4};
		h.heapify(array);
		if (!Heap.isMaxHeap(h.array)) {
			Heap.toGraphviz(pathPrefix + "/testHeapify2.dot", array);
			fail("Array that you created by heapify " + Arrays.toString(h.array) + " is not a max heap, you returned true. See " + pathPrefix + "/testHeapify2.dot for visualisation.");
		}
		if (isSorted(h.array)) {
			fail("This is cheating, you sorted the array, it is a valid heap, but you have not used the proper heapify algorithm!");
		}
		
		array = new int[] {204, 257, 960, 471, 815, 230, 189, 807, 545, 702, 291, 421, 269, 254, 15, 359, 228, 334, 812, 368, 954, 565, 110, 418, 486, 563, 554, 899, 339, 614, 957, 83, 377, 140, 99, 463, 0, 980, 929, 319, 989, 377, 701, 608, 274, 905, 919, 94, 951, 224, 276, 785, 332, 562, 769, 167, 992, 252, 594, 259, 803, 666, 980, 178, 899, 704, 902, 153, 665, 245, 642, 802, 5, 164, 23, 400, 756, 733, 639, 469, 938, 629, 443, 743, 182, 476, 376, 755, 983, 380, 438, 898, 681, 713, 143, 128, 884, 183, 238, 207};
		h.heapify(array);
		if (!Heap.isMaxHeap(h.array)) {
			Heap.toGraphviz(pathPrefix + "/testHeapify3.dot", array);
			fail("Array that you created by heapify " + Arrays.toString(h.array) + " is not a max heap, you returned true. See " + pathPrefix + "/testHeapify3.dot for visualisation.");
		}
		if (isSorted(h.array)) {
			fail("This is cheating, you sorted the array, it is a valid heap, but you have not used the proper heapify algorithm!");
		}
	}
	
	
	@Test
	public void testSort() {
		int[] array;
		Heap h = new Heap(0);
		
		array = new int[] {3, 2, 4};
		h.heapify(array);
		h.heapSort();
		if (!isSorted(h.array)) {
			fail("Your incorrectly sorted array. Input was " + Arrays.toString(array) + ", your output: " + Arrays.toString(h.array));
		}
		
		array = new int[] {1, 54, 13, 95, 51, 30, 32, 89, 18, 4};
		h.heapify(array);
		if (isSorted(h.array)) {
			fail("Your incorrectly sorted array. Input was " + Arrays.toString(array) + ", your output: " + Arrays.toString(h.array));
		}
		
		array = new int[] {204, 257, 960, 471, 815, 230, 189, 807, 545, 702, 291, 421, 269, 254, 15, 359, 228, 334, 812, 368, 954, 565, 110, 418, 486, 563, 554, 899, 339, 614, 957, 83, 377, 140, 99, 463, 0, 980, 929, 319, 989, 377, 701, 608, 274, 905, 919, 94, 951, 224, 276, 785, 332, 562, 769, 167, 992, 252, 594, 259, 803, 666, 980, 178, 899, 704, 902, 153, 665, 245, 642, 802, 5, 164, 23, 400, 756, 733, 639, 469, 938, 629, 443, 743, 182, 476, 376, 755, 983, 380, 438, 898, 681, 713, 143, 128, 884, 183, 238, 207};
		h.heapify(array);
		if (isSorted(h.array)) {
			fail("Your incorrectly sorted array. Input was " + Arrays.toString(array) + ", your output: " + Arrays.toString(h.array));
		}
	}
	
	
	
	
	@Test
	public void testSmall() {
		checkEquality("small");
	}
	
	@Test
	public void testLarge() {
		checkEquality("large");
	}
	
	@Test
	public void testComplex() {
		checkEquality("basic");
	}
}







