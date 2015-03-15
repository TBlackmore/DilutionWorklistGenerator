import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */

/**
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
		assertTrue(testPrepPlate.wellRow(1) == 1);
		assertTrue(testPrepPlate.wellCol(1) == 1);
		assertTrue(testPrepPlate.wellRow(96) == 8);
		assertTrue(testPrepPlate.wellCol(96) == 12);
		assertTrue(testTargetPlate.wellRow(1) == 1);
		assertTrue(testTargetPlate.wellCol(1) == 1);
		assertTrue(testTargetPlate.wellRow(384) == 16);
		assertTrue(testTargetPlate.wellCol(384) == 24);
	}

}
