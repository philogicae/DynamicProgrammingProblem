import static org.junit.Assert.assertTrue;

import org.junit.Test;

import geometry.Rotation;
import geometry.Tile;

/* Tests transformations of tiles (rotation, translation, flip) */

public class TileTest {

	
	public void testTile(Tile t, int h, int w, int x, int y) {
		assertTrue(t.getWidth() == h);
		assertTrue(t.getHeight() == w);
		assertTrue(t.getCornerX() == x);
		assertTrue(t.getCornerY() == y);
		assertTrue(t.getArea() == h * w);
	}
	
	@Test
	public void test() {
		Tile t = new Tile(7, 3);
		testTile(t,7,3,0,0);
		t.flip();
		testTile(t,3,7,0,0);
		t.rotateBy(Rotation.QUARTER);
		testTile(t,7,3,-7,0);
		t.rotateBy(Rotation.HALF);
		testTile(t,7,3,0,-3);
		t.translateBy(4, 5);
		testTile(t,7,3,4,2);
		t.rotateBy(Rotation.THREEQUARTER);
		testTile(t,3,7,2,-11);
		t.rotateBy(Rotation.QUARTER);
		testTile(t,7,3,4,2);
		t.rotateBy(Rotation.HALF);
		testTile(t,7,3,-11,-5);
		Tile cl = t.clone();
		testTile(cl,7,3,-11,-5);
		cl.translateBy(2, 1);
		testTile(cl,7,3,-9,-4);
		testTile(t,7,3,-11,-5);
	}

}

