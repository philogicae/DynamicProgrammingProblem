package geometry;

/* This class encodes a subdivided part of a container. A part is described
   by its shape (an L), and its position characterized by the position of
   the main corner of the L, as well as the orientation of the L
   (among the four possible rotations).

   We can test whether a specific square is in this part. This is useful
   for unit testing. Beware that when rotating a square, the reference corner
   changes (cf rotateCorner).
 */

public class Part {

  public final EllShape shape;
  public final Coordinate position;
  public final Rotation rotation;

  public Part(EllShape shape, Coordinate position, Rotation rotation) {
    this.shape = shape;
    this.position = position;
    this.rotation = rotation;
  }


  public boolean contains(Coordinate square) {
    Coordinate relative =
      rotateCorner(
        square.backTranslateBy(position).backRotateBy(rotation),
        rotation.negate());
    return (relative.x >= 0 && relative.x < shape.width
        && relative.y >= 0 && relative.y < shape.height
        && (relative.x < shape.insideCorner.x
           || relative.y < shape.insideCorner.y));
  }

  private static Coordinate rotateCorner(Coordinate square, Rotation r) {
    switch (r) {
      case ID: return square;
      case QUARTER: return square.translateBy(new Coordinate(-1, 0));
      case HALF: return square.translateBy(new Coordinate(-1, -1));
      case THREEQUARTER: return square.translateBy(new Coordinate(0, -1));
    }
    return null;
  }

}
