import static org.junit.Assert.*;

<<<<<<< HEAD
=======
import org.junit.After;
import org.junit.Before;
>>>>>>> ClassCull
import org.junit.Test;

/**
 * 
 */

/**
<<<<<<< HEAD
 * @author Tim
 *
 */
public class PlateTest{
	
	//The type of prep plate to be used
	private Plate testPrepPlate = new Plate(8,12,"testPrep","500uL masterblock", 400, 20);
	//the type of target plate to be used
	private Plate testTargetPlate = new Plate(16,24,"testTarget","200 uL masterblock", 180, 20);

	public void tearDown() {
		Plate testPrepPlate = null;
		Plate testTargetPlate = null;
	}
	
	@Test
	public void test() {
		assertTrue(testPrepPlate.wellRow  (1  ) == 0 );
		assertTrue(testPrepPlate.wellCol  (1  ) == 0 );
		assertTrue(testPrepPlate.wellRow  (96 ) == 7 );
		assertTrue(testPrepPlate.wellCol  (96 ) == 11);
		assertTrue(testPrepPlate.wellRow  (2  ) == 1 );
		assertTrue(testPrepPlate.wellCol  (2  ) == 0 );
		assertTrue(testPrepPlate.wellRow  (9  ) == 0 );
		assertTrue(testPrepPlate.wellCol  (9  ) == 1 );
		
		assertTrue(testTargetPlate.wellRow(1  ) == 0 );
		assertTrue(testTargetPlate.wellCol(1  ) == 0 );
		assertTrue(testTargetPlate.wellRow(2  ) == 1 );
		assertTrue(testTargetPlate.wellCol(2  ) == 0 );
		assertTrue(testTargetPlate.wellRow(17 ) == 0 );
		assertTrue(testTargetPlate.wellCol(17 ) == 1 );
		assertTrue(testTargetPlate.wellRow(384) == 15);
		assertTrue(testTargetPlate.wellCol(384) == 23);
=======
 * @author tim
 *
 */
public class PlateTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Plate testPlate1 = new Plate (8,12, "96 well plate", 200, 20);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
>>>>>>> ClassCull
	}

}
