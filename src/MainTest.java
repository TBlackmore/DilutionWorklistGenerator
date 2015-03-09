import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Tim
 *
 */
public class MainTest extends Main{
	
	//The type of prep plate to be used
	private Plate testPrepPlate = new Plate(8,12,"500uL masterblock", 400, 20);
	//the type of target plate to be used
	private Plate testTargetPlate = new Plate(16,24,"200 uL masterblock", 180, 20);

	public void tearDown() {
		Plate testPrepPlate = null;
		Plate testTargetPlate = null;
	}
	
	@Test
	public void test() {
		assertTrue(wellRow(1, testPrepPlate) == 1);
		assertTrue(wellCol(1, testPrepPlate) == 1);
		assertTrue(wellRow(96, testPrepPlate) == 8);
		assertTrue(wellCol(96, testPrepPlate) == 12);
		assertTrue(wellRow(1, testTargetPlate) == 1);
		assertTrue(wellCol(1, testTargetPlate) == 1);
		assertTrue(wellRow(384, testTargetPlate) == 16);
		assertTrue(wellCol(384, testTargetPlate) == 24);
	}

}
