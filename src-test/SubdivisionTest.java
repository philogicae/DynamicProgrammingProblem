import geometry.Coordinate;
import geometry.EllShape;
import geometry.Subdivision;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/* Tests subdivisions of L-shapes into 2 parts.
   - is a subdivision (disjoint and covering everything)
   - exhaustiveness of subdivisions.
 */

public class SubdivisionTest {


	private EllShape ell;
	

	public void checkSubdivision(Subdivision sub) {
		int ar1, ar2, ar;
		ar = ell.getArea();
		ar1 = sub.part1.shape.getArea();
		ar2 = sub.part2.shape.getArea();
		assertEquals("subdivision has correct area (" + sub + ")", ar, ar1 + ar2);
		assertTrue("first subdivided area is positive (" + sub + ")", ar1 > 0);
		assertTrue("second subdivided area is positive (" + sub + ")", ar2 > 0);
		for (int i = 0; i < ell.width; i++) {
			for (int j = 0; j < ell.height; j++) {
				assertTrue("geometry.Subdivision covers every cell ("
                     + i + ", " + j + ", " + sub + ")",
					(i >= ell.insideCorner.x && j >= ell.insideCorner.y)
						|| sub.part1.contains(new Coordinate(i, j))
						|| sub.part2.contains(new Coordinate(i, j))
				);
			}
		}
	}

	@Test
	public void testEllSubdiv() {
		ell = new EllShape(4,4,new Coordinate(2,2));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 17);
	}
	
	@Test
	public void testBigEllSubdiv() {
		ell = new EllShape(6,5,new Coordinate(4,3));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 47);
	}
	
	@Test
	public void testBigBigEllSubdiv() {
		ell = new EllShape(17,15,new Coordinate(11,9));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 486);
	}
	
	
	@Test
	public void testRectSubdiv() {
		ell = new EllShape(3,3,new Coordinate(3,3));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 8);
	}
	
	
	@Test
	public void testBigRectSubdiv() {
		ell = new EllShape(3,5,new Coordinate(3, 5));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 19);
	}

	@Test
	public void testBigBigRectSubdiv() {
		ell = new EllShape(13,15,new Coordinate(13,15));
		ell.subdivisions().forEach(this::checkSubdivision);
		assertEquals(ell.subdivisions().count(), 1189);
	}

}
