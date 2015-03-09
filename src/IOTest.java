import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IOTest {
	
	private IO testIO;
	
	@Before
	public void setUp() throws Exception {
		//testIO = new IO("example_input.csv");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testIO = new IO("example_input.csv");
	}

}
