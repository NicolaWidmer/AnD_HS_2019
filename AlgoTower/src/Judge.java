import static org.junit.Assert.*;

import java.io.*;
import java.util.Scanner;

public class Judge 
{

	public static void diff(InputStream expected, InputStream actual)
	{
		Scanner actualScanner = new Scanner(actual);
		Scanner expectedScanner = new Scanner(expected);
		
		while(expectedScanner.hasNext())
		{
			assertTrue(actualScanner.hasNext());
			assertEquals(expectedScanner.next(), actualScanner.next());
		}
		
		assertFalse(actualScanner.hasNext());
		
		expectedScanner.close();
		actualScanner.close();
	}
	
	public static void testOne(String basename) throws IOException
	{
		InputStream input = new FileInputStream(basename+".in.txt");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		Main.read_and_solve(input, new PrintStream(output));
		
		InputStream actual = new ByteArrayInputStream(output.toByteArray());
		InputStream expected = new FileInputStream(basename+".out.txt");
		diff(expected, actual);
		
		actual.close();
		expected.close();
		
		input.close();
		output.close();
	}
	

	@org.junit.Test
	public void testExample() throws IOException
	{
		testOne("test/example");
	}

	@org.junit.Test
	public void testSubtask1() throws IOException
	{
		testOne("test/subtask1");
	}
	
	@org.junit.Test
	public void testSubtask2() throws IOException
	{
		testOne("test/subtask2");
	}

	@org.junit.Test
	public void testGeneral() throws IOException
	{
		testOne("test/general");
	}

}
