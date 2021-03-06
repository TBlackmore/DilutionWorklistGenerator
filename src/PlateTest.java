import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

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
		
		int[] testRowCol = {1,1};
		assertTrue(testPrepPlate.rowColToWellNumber(testRowCol) == 1);
		testRowCol[0] = 8;
		testRowCol[1] = 1;
		assertTrue(testPrepPlate.rowColToWellNumber(testRowCol) == 8);
		testRowCol[0] = 8;
		testRowCol[1] = 12;
		assertTrue(testPrepPlate.rowColToWellNumber(testRowCol) == 96);

	}

}
