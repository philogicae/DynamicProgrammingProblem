package geometry;

/* Tiles must be axis-aligned, so we can only rotate them by 0, 90, 180 or 270°.
   This enumeration encodes the possible rotations. Rotations are considered
   counter-clockwise.
 */

public enum Rotation {
  ID,
  QUARTER,
  HALF,
  THREEQUARTER;

  public Rotation negate() {
    switch (this) {
      case ID:
        return ID;
      case QUARTER:
        return THREEQUARTER;
      case HALF:
        return HALF;
      case THREEQUARTER:
        return QUARTER;
    }
    return null;
  }
}
