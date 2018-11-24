package geometry;

/* A Tile is a small rectangular shape positioned into the plane,
   with axis-parallel sides and integral coordinates. A tile may be moved
   and rotated (as long as it stays axis-parallel and integral).
 */

public class Tile {

	private Coordinate corner;
	private int width;
	private int height;

	public Tile(int width, int height) {
		assert (width > 0 && height > 0);
		this.width = width;
		this.height = height;
		corner = new Coordinate(0, 0);
	}
	
	public Tile(int width, int height, Coordinate corner) {
		assert (width > 0 && height > 0);
		this.width = width;
		this.height = height;
		this.corner = corner;
	}
	
	public Tile(int width, int height, int x, int y) {
		assert (width > 0 && height > 0);
		this.width = width;
		this.height = height;
		this.corner = new Coordinate(x, y);
	}

	public int getCornerX() {
		return corner.x;
	}

	public int getCornerY() {
		return corner.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getArea() {
		return width * height;
	}

	public void flip() {
		int buffer = this.height;
		this.height = this.width;
		this.width = buffer;
	}

	public void translateBy(Coordinate vec) {
		corner = corner.translateBy(vec);
	}

	public void translateBy(int x, int y) {
		translateBy(new Coordinate(x, y));
	}

	// It would be incorrect to simply rotate to bottom-left insideCorner
	// coordinates. Indeed after rotation, the bottom-left-insideCorner is
	// transformed into an other insideCorner if the rectangle, so we need
	// to adjust.
	public void rotateBy(Rotation r) {
		corner = corner.rotateBy(r);
		switch (r) {
		case ID:
			break;
		case QUARTER:
			corner = corner.translateBy(new Coordinate(-height, 0));
			flip();
			break;
		case HALF:
			corner = corner.translateBy(new Coordinate(-width, -height));
			break;
		case THREEQUARTER:
			corner = corner.translateBy(new Coordinate(0, -width));
			flip();
			break;
		}
	}

	public Tile clone() {
		Tile res = new Tile(this.width, this.height);
		res.translateBy(this.corner);
		return res;
	}

}
