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
		// reference solution runs in 2.3 s on i7-8650U
		// and 1 s on Judge
		checkEquality("large");
	}

	@Test
	public void testDistance() {
		Graph g = new Graph(5);
        g.addEdge(0, 1, 4);
        g.addEdge(1, 0, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(0, 3, 1);
        g.addEdge(3, 0, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 1, 1);
        g.addEdge(1, 5, 2);
        g.addEdge(5, 1, 2);
        g.addEdge(2, 3, 2);
        g.addEdge(3, 2, 2);
        g.addEdge(2, 4, 3);
        g.addEdge(4, 2, 3);
        g.addEdge(3, 4, 4);
        g.addEdge(4, 3, 4);
        g.addEdge(4, 5, 1);
        g.addEdge(5, 4, 1);
		
		String filename = pathPrefix + "/testDistance.dot";
		
		int[] distances = g.dijkstra(0);
		
		graphviz(g, distances, filename);
		
		assertArrayEquals(new int[] {0, 2, 1, 1, 4}, distances);
	}
	
	@Test
	public void testSingle() {
		/**
		 * Use this test to debug a single wrong graph.
		 * 
		 * Copy the distance matrix to string <code>distanceMatrix</code>, set the
		 * right graph size, and compare the output in the last line of code,
		 * <code>assertArrayEquals</code>. The test will run your implementation
		 * of Dijkstra's algorithm, and it will generate GraphViz format
		 * representation of the input. In red, you can see your distances by nodes.
		 * You can find this graphviz file in the directory with test files, as testSingle.dot.
		 * 
		 * To visualize the file, use either some online tool for graphviz visualization:
		 * e.g., http://www.webgraphviz.com/, or install a local viewer:
		 * https://www.graphviz.org/download/
		 */
		int graphSize = 4;
		String distanceMatrix = "0 4 1 4 \n" + 
				"-1 0 -1 -1 \n" + 
				"2 3 0 3 \n" + 
				"1 2 4 0 ";
		String[] strLineArr = distanceMatrix.split("\n");
		Graph g = new Graph(graphSize);
		for (int u = 0; u < graphSize; ++u) {
			String[] strArr = strLineArr[u].split(" ");
			for (int v = 0; v < graphSize; ++v) {
				int w = Integer.parseInt(strArr[v]);
				if (w != -1) {
					g.addEdge(u, v, w);
				}
			}
		}
		
		String filename = pathPrefix + "/testSingle.dot";
		
		int[] distances = g.dijkstra(0);
		
		graphviz(g, distances, filename);
		
		assertArrayEquals(new int[] {0, 4, 1, 4}, distances);
	}

	private void graphviz(Graph g, int[] distances, String filename) {
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
	    	writer.write("digraph G {\n");

			for (int i = 0; i < g.matrix.length; i++) {
				writer.write("\"" + i + "\" [xlabel=<<font color=\"red\">" + distances[i] + "</font>>];\n");
			}

			for (int i = 0; i < g.matrix.length; i++) {
				for (int j = 0; j < g.matrix.length; j++) {
					if (i != j && g.matrix[i][j] != Integer.MAX_VALUE) {
						writer.write("\"" + i + "\" -> \"" + j + "\" [label=\"" + g.matrix[i][j] + "\"];\n");
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







