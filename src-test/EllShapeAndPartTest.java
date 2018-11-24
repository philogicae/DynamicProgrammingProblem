import static org.junit.Assert.*;
import geometry.*;
import org.junit.Test;

/* Test the behavior of Part and EllShape.

   EllShape:
   - normalization in the constructor
   - area
   - detection of rectangles
   - greedy packing
   - upper bound
   Parts:
   - transformation (rotation, translation)
 */

public class EllShapeAndPartTest {

  @Test
  public void testCoord() {
    EllShape ell = new EllShape(5,4,new Coordinate(3,2));
    assertTrue(ell.width == 5);
    assertTrue(ell.height == 4);
    assertTrue(ell.insideCorner.x == 3);
    assertTrue(ell.insideCorner.y == 2);
    ell = new EllShape(8,9,new Coordinate(0,6));
    assertTrue(ell.width == 8);
    assertTrue(ell.height == 6);
    assertTrue(ell.insideCorner.x == 8);
    assertTrue(ell.insideCorner.y == 6);
  }

  @Test
  public void testArea() {
    EllShape ell = new EllShape(7,9,new Coordinate(3,4));
    assertTrue(ell.getArea() == 43);
    ell = new EllShape(11,13,new Coordinate(7,8));
    assertTrue(ell.getArea() == 123);
    ell = new EllShape(12,8,new Coordinate(0, 5));
    assertTrue(ell.getArea() == 60);
  }

  @Test
  public void testIsRectangle() {
    EllShape ell = new EllShape(7,6,new Coordinate(5,4));
    assertFalse(ell.isRectangle());
    ell = new EllShape(11,10,new Coordinate(11,8));
    assertTrue(ell.isRectangle());
    ell = new EllShape(8,6,new Coordinate(8,6));
    assertTrue(ell.isRectangle());
    ell = new EllShape(11,9,new Coordinate(8,0));
    assertTrue(ell.isRectangle());
  }

  @Test
  public void testUpperBound() {
    EllShape ell = new EllShape(7,9,new Coordinate(3,4));
    assertTrue(ell.getPackingUpperBound(new Tile(2,5)) == 4);
    ell = new EllShape(10,8,new Coordinate(3,4));
    assertTrue(ell.getPackingUpperBound(new Tile(1,5)) == 10);
  }


  @Test
  public void testGreedyHorizontal() {
    EllShape ell = new EllShape(7,9,new Coordinate(5,4));
    Packing hor = ell.packGreedilyHorizontally(new Tile(2, 4));
    assertTrue(hor.size() == 4);
    ell = new EllShape(12,8,new Coordinate(6,4));
    hor = ell.packGreedilyHorizontally(new Tile(3, 2));
    assertTrue(hor.size() == 12);
    ell = new EllShape(12,12,new Coordinate(3,2));
    hor = ell.packGreedilyHorizontally(new Tile(4, 3));
    assertTrue(hor.size() == 0);
  }

  @Test
  public void testGreedyVertical() {
    EllShape ell = new EllShape(7,9,new Coordinate(5,4));
    Packing hor = ell.packGreedilyVertically(new Tile(2, 4));
    assertTrue(hor.size() == 5);
    ell = new EllShape(12,8,new Coordinate(6,4));
    hor = ell.packGreedilyVertically(new Tile(3, 2));
    assertTrue(hor.size() == 9);
    ell = new EllShape(12,12,new Coordinate(3,2));
    hor = ell.packGreedilyVertically(new Tile(3, 4));
    assertTrue(hor.size() == 3);
  }

  @Test
  public void testGreedy() {
    EllShape ell = new EllShape(7,9,new Coordinate(5,4));
    Packing pack = ell.packGreedily(new Tile(2, 4));
    assertTrue(pack.size() == 5);
    ell = new EllShape(12,8,new Coordinate(6,4));
    pack = ell.packGreedily(new Tile(3, 2));
    assertTrue(pack.size() == 12);	
    ell = new EllShape(12,12,new Coordinate(3,2));
    pack = ell.packGreedily(new Tile(3, 4));
    assertTrue(pack.size() == 3);
  }


  @Test
  public void testContainsCoordinate0() {
    EllShape ellShape = new EllShape(4,5,new Coordinate(2,3));
    Part part = new Part(ellShape, new Coordinate(10, 7), Rotation.ID);
    assertTrue(part.contains(new Coordinate(10, 7)));
    assertTrue(part.contains(new Coordinate(13, 9)));
    assertTrue(part.contains(new Coordinate(13, 9)));
    assertTrue(part.contains(new Coordinate(10, 11)));
    assertTrue(part.contains(new Coordinate(11, 11)));
    assertFalse(part.contains(new Coordinate(10, 6)));
    assertFalse(part.contains(new Coordinate(9, 7)));
    assertFalse(part.contains(new Coordinate(12, 10)));
    assertFalse(part.contains(new Coordinate(14, 9)));
    assertFalse(part.contains(new Coordinate(10, 12)));
  }

  @Test
  public void testContainsCoordinate1() {
    EllShape ellShape = new EllShape(4,5,new Coordinate(2,3));
    Part part = new Part(ellShape, new Coordinate(10, 7), Rotation.QUARTER);
    assertTrue(part.contains(new Coordinate(9, 7)));
    assertTrue(part.contains(new Coordinate(9, 10)));
    assertTrue(part.contains(new Coordinate(7, 10)));
    assertTrue(part.contains(new Coordinate(5, 8)));
    assertTrue(part.contains(new Coordinate(5, 7)));
    assertFalse(part.contains(new Coordinate(10, 7)));
    assertFalse(part.contains(new Coordinate(9, 6)));
    assertFalse(part.contains(new Coordinate(6, 9)));
    assertFalse(part.contains(new Coordinate(9, 11)));
    assertFalse(part.contains(new Coordinate(4, 7)));
  }

  @Test
  public void testContainsCoordinate2() {
    EllShape ellShape = new EllShape(4,5,new Coordinate(2,3));
    Part part = new Part(ellShape, new Coordinate(10,7), Rotation.HALF);
    assertTrue(part.contains(new Coordinate(9, 6)));
    assertTrue(part.contains(new Coordinate(6, 6)));
    assertTrue(part.contains(new Coordinate(6, 4)));
    assertTrue(part.contains(new Coordinate(8, 2)));
    assertTrue(part.contains(new Coordinate(9, 2)));
    assertFalse(part.contains(new Coordinate(10, 6)));
    assertFalse(part.contains(new Coordinate(9, 7)));
    assertFalse(part.contains(new Coordinate(7, 3)));
    assertFalse(part.contains(new Coordinate(5, 6)));
    assertFalse(part.contains(new Coordinate(9, 1)));
  }

  @Test
  public void testContainsCoordinate3() {
    EllShape ellShape = new EllShape(4, 5, new Coordinate(2, 3));
    Part part = new Part(ellShape, new Coordinate(10, 7), Rotation.THREEQUARTER);
    assertTrue(part.contains(new Coordinate(10, 6)));
    assertTrue(part.contains(new Coordinate(10, 3)));
    assertTrue(part.contains(new Coordinate(12, 3)));
    assertTrue(part.contains(new Coordinate(14, 6)));
    assertTrue(part.contains(new Coordinate(14, 5)));
    assertFalse(part.contains(new Coordinate(10, 7)));
    assertFalse(part.contains(new Coordinate(9, 6)));
    assertFalse(part.contains(new Coordinate(10, 2)));
    assertFalse(part.contains(new Coordinate(15, 6)));
    assertFalse(part.contains(new Coordinate(13, 4)));
  }
  
  @Test
  public void testEquals() {
    EllShape ellShape1 = new EllShape(4, 5, new Coordinate(2, 3));
    EllShape ellShape2 = new EllShape(4, 5, new Coordinate(2, 3));
    EllShape ellShape3 = new EllShape(4, 5, new Coordinate(3, 2));
    assertTrue(ellShape1.equals(ellShape2));
    assertFalse(ellShape1.equals(ellShape3));
  }

}























