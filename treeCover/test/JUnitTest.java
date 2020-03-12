import static org.junit.Assert.*;
import org.junit.Test;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
			ReadAndWrite rw = new ReadAndWrite();
			rw.readAndSolve(in, out);
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
	public void testDeserialize() {
		BinaryTree bt = new BinaryTree();
		bt.deserialize(new int[] {-1, -1, 13, -1, -1, 4, -1, 5, 12});
		
		assertEquals(bt.root.value, 12);
		assertEquals(bt.root.left.value, 13);
		assertEquals(bt.root.left.left, null);
		assertEquals(bt.root.left.right, null);
		assertEquals(bt.root.right.value, 5);
		assertEquals(bt.root.right.left.value, 4);
		assertEquals(bt.root.right.left.left, null);
		assertEquals(bt.root.right.left.right, null);
		assertEquals(bt.root.right.right, null);
	}
	
	@Test
	public void testGetMaximum() {
		BinaryTree bt = new BinaryTree();
		bt.deserialize(new int[] {-1, -1, 13, -1, -1, 4, -1, 5, 12});
		
		int r = bt.getMaximum(); 
		
		assertEquals(18, r);
		
		bt = new BinaryTree();
		bt.deserialize(new int[] {-1,-1,12});
		
		r = bt.getMaximum(); 

		assertEquals(12, r);
	}
	
	@Test
	public void testMini() {
		checkEquality("mini");
	}
	
	@Test
	public void testSmall() {
		checkEquality("small");
	}
	
	@Test
	public void testLarge() {
		// reference solution runs in 0.5 s on i7-8650U
		// and 0.9 s on Judge
		checkEquality("large");
	}
	

	@Test
	public void testSingle() {
		/**
		 * Note that this test will always pass.
		 * Use this test to debug a single wrong tree.
		 * 
		 * Copy the line representing some tree from the input file to string
		 * <code>stringTreeToShow</code>. The test will run your method for checking
		 * if the graph is two colorable, and it will generate GraphViz format
		 * representation of the input. You can find this graphviz file in the directory
		 * with test files, as graph.dot.
		 * 
		 * To visualize the file, use either some online tool for graphviz visualization:
		 * e.g., http://www.webgraphviz.com/, or install a local viewer:
		 * https://www.graphviz.org/download/
		 */
		String stringTreeToShow = "-1 -1 2 -1 -1 1 5 -1 -1 8 -1 -1 6 15 10 -1 -1 13 -1 3 -1 -1 9 -1 -1 12 14 11 7";
		String[] strArr = stringTreeToShow.split(" ");
		int[] intArr = new int[strArr.length];
		for (int i = 0; i < strArr.length; ++i) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}
		
		BinaryTree bt = new BinaryTree();
		bt.deserialize(intArr);
		
		int r = bt.getMaximum(); 
		System.out.println(r);
		String filename = pathPrefix + "/tree.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("graph Tree {\n");
	    	
	    	toGraphvizRec(bt.root, writer);

			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void toGraphvizRec(Node node, PrintWriter writer) {
	    writer.print("\"" + node.value + "\"\n");
	    if(node.left != null) {
	        writer.print("\"" + node.value + "\" -- \"" +  node.left.value + "\"\n");
	        toGraphvizRec(node.left, writer);
	    }
	    if(node.right != null) {
	        writer.print("\"" + node.value + "\" -- \"" +  node.right.value + "\"\n");
	        toGraphvizRec(node.right, writer);
	    }
	}
}







