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
	public void testMini() {
		checkEquality("mini");
	}
	
	@Test
	public void testSmall() {
		checkEquality("small");
	}
	
	@Test
	public void testNormal() {
		checkEquality("normal");
	}
	
	@Test
	public void testLarge() {
		// reference solution runs in 3 s on i7-8650U
		// and 1 s on Judge
		checkEquality("large");
	}
	
	/**
	 * Note that all individual tests are dependent on implementation of
	 * <code>Graph.isPath</code>
	 */
	@Test
	public void testAddSimple() {
		Graph g = new Graph(2);
		g.addEdge(0, 1);
		
		String filename = pathPrefix + "/addTestSimple.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");
	    	
	    	g.graphviz(writer);
			
			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("Either addEdge or isPath does not work", g.isPath(0, 1));
		assertTrue("Either addEdge or isPath does not work", !g.isPath(1, 0));
	}
	
	@Test
	public void testAddMultiple() {
		Graph g = new Graph(2);
		g.addEdge(0, 1);
		g.addEdge(0, 1);
		
		String filename = pathPrefix + "/testAddMultiple.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");
	    	
	    	g.graphviz(writer);
			
			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("Either addEdge or isPath does not work", g.isPath(0, 1));
		assertTrue("Either addEdge or isPath does not work", !g.isPath(1, 0));
	}
	
	@Test
	public void testAddLarger() {
		Graph g = new Graph(5); // cyclic graph
		g.addEdge(1, 0);
		g.addEdge(0, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 0);
		g.addEdge(0, 2);
		g.addEdge(2, 1);
		
		String filename = pathPrefix + "/testAddLarger.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");
	    	
	    	g.graphviz(writer);
			
			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("Either addEdge or isPath does not work", g.isPath(0, 1));
		assertTrue("Either addEdge or isPath does not work", g.isPath(0, 4));
		assertTrue("Either addEdge or isPath does not work", g.isPath(4, 0));
	}
	
	/**
	 * This test is dependent on correct implementation of:
	 * <code>Graph.addEdge()</code> and <code>Graph.isPath()</code>
	 */
	@Test
	public void testRemoveIn() {
		Graph g = new Graph(5); // cyclic graph
		g.addEdge(1, 0);
		g.addEdge(0, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 0);
		g.addEdge(0, 2);
		g.addEdge(2, 1);
		
		g.removeAllIn(0);
		
		String filename = pathPrefix + "/testRemoveIn.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");
	    	
	    	g.graphviz(writer);
			
			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("One of removeAllIn, addEdge or isPath does not work", g.isPath(0, 1));
		assertTrue("One of removeAllIn, addEdge or isPath does not work", g.isPath(0, 4));
		assertTrue("One of removeAllIn, addEdge or isPath does not work", !g.isPath(4, 0));
	}
	
	/**
	 * This test is dependent on correct implementation of:
	 * <code>Graph.addEdge()</code> and <code>Graph.isPath()</code>
	 */
	@Test
	public void testRemoveOut() {
		Graph g = new Graph(5); // cyclic graph
		g.addEdge(1, 0);
		g.addEdge(0, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 0);
		g.addEdge(0, 2);
		g.addEdge(2, 1);
		
		g.removeAllOut(0);
		
		String filename = pathPrefix + "/testRemoveOut.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");
	    	
	    	g.graphviz(writer);
			
			writer.write("}\n");
			
	        writer.close();
	    } catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("One of removeAllIn, addEdge or isPath does not work", g.isPath(3, 4));
		assertTrue("One of removeAllIn, addEdge or isPath does not work", !g.isPath(0, 4));
		assertTrue("One of removeAllIn, addEdge or isPath does not work", g.isPath(4, 0));
	}
}







