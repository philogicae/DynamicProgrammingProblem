package geometry;

/* A Coordinate is an integral point of the plane (as such, it is immutable).
   A Coordinate can be translated, or rotated around the center by 0, 90,
   180 or 270°, which gives an other Coordinate.
*/

public class Coordinate {
	public final int x;
	public final int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate rotateBy(Rotation r) {
		switch (r) {
		case ID:
			return this;
		case QUARTER:
			return new Coordinate(-y, x);
		case HALF:
			return new Coordinate(-x, -y);
		case THREEQUARTER:
			return new Coordinate(y, -x);
		}
		return null;
	}

	public Coordinate backRotateBy(Rotation r) {
		return this.rotateBy(r.negate());
	}

	public Coordinate translateBy(Coordinate vec) {
		return new Coordinate(this.x + vec.x, this.y + vec.y);
	}
	
	public Coordinate translateBy(int x, int y) {
		return new Coordinate(this.x + x, this.y + y);
	}

	public Coordinate backTranslateBy(Coordinate vec) {
		return new Coordinate(this.x - vec.x, this.y - vec.y);
	}

}
