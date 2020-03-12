import static org.junit.Assert.*;
import org.junit.Test;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
			rw.read_and_solve(in, out);
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
	public void testTwoSmall() {
		checkEquality("twoSmall");
	}
	
	@Test
	public void testTwoLarge() {
		// reference solution runs in 4.5 s on i7-8650U
		// and 0.35 s on Judge
		checkEquality("twoLarge");
	}
	
	@Test
	public void testThreeSmall() {
		checkEquality("threeSmall");
	}
	
	@Test
	public void testThreeLarge() {
		// reference solution runs in 1.9 s on i7-8650U
		// and 1.3 s on Judge
		checkEquality("threeLarge");
	}
}







