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
	public void testSmall() {
		checkEquality("small");
	}
	
	@Test
	public void testLarge() {
		// reference solution runs in 0.65 s on i7-8650U
		// and 0.4 s on Judge
		checkEquality("large");
	}
	
	@Test
	public void testSingle() {
		/**
		 * Note that this test will always pass.
		 * Use this test to debug a single wrong graph.
		 * 
		 * Copy the line representing some graph from the input to string
		 * <code>stringGraphToShow</code>. The test will run your method for checking
		 * if the graph is two colorable, and it will generate GraphViz format
		 * representation of the input. You can find this graphviz file in the directory
		 * with test files, as graph.dot.
		 * 
		 * To visualize the file, use either some online tool for graphviz visualization:
		 * e.g., http://www.webgraphviz.com/, or install a local viewer:
		 * https://www.graphviz.org/download/
		 */
		String stringGraphToShow = "EJ\\w";
		
		Graph g = new Graph(ReadAndWrite.parseGraph(stringGraphToShow));
		g.isTwoColorable();
		String filename = pathPrefix + "/graph.dot"; 
		
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("graph G {\n");
	    	
	    	writer.write("{\n node [style=filled]\n");
			for (int i = 0; i < g.graph.length; ++i) {
				writer.write("\"" + i + "\" [fillcolor=" + g.getNodeColor(i) + "]\n");
			}
	    	writer.write("}\n");
	    	
	    	
			for (int i = 0; i < g.graph.length; ++i) {
				for (int j = 0; j < g.graph.length; ++j) {
					if (g.graph[i][j] == 1) {
						writer.write("\"" + i + "\" -- \"" + j + "\"\n");
					}
				}
			}
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
}







