package geometry;

/* This class encode the geometric shape of an L container.
   All L containers have a corner at point (0,0) and are extending
   in the positive quadrant. An L may be degenerate when it is actually
   a rectangle.

   An L-container may be tiled by rectangle, using a greedy algorithm
   packing all tiles with the same orientation (methods
   packGreedilyHorizontally, packGreedilyVertically).

   An L-container may be subdivided into two disjoint L-container in multiple
   ways. Hence there is an iterator over every possible subdivision.

   Only modify the methods marked as stubs.
   TODO: implement getArea
   TODO: implement getPackingUpperBound
   TODO: implement packGreedilyHorizontally
   TODO: implement packGreedilyVertically
   TODO: implement packGreedily
   TODO: implement equals (ask Eclipse and simplify)
   TODO: implement hashCode (ask Eclipse and simplify)
 */

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EllShape {

	/*
	 * This is an L-shape: (new EllShape(14,7,8,4))
	 * 
	 * ----------------- height-1 > | | | | | | | | | ----------------- | | | | | |
	 * | | | ----------------- iC.y > | | | | | | | | | < insideCorner (iC)
	 * ----------------------------- | | | | | | | | | | | | | | |
	 * ----------------------------- | | | | | | | | | | | | | | |
	 * ----------------------------- | | | | | | | | | | | | | | |
	 * ----------------------------- 0 > | | | | | | | | | | | | | | |
	 * ----------------------------- ^ ^ ^ 0 iC.x width-1
	 * 
	 * One difficulty is that in the degenerate case when the L-shape is a
	 * rectangle, this representation allows any value for the inside corner having
	 * at least one coordinate at x==width or y==height, or to have the inside
	 * corner on x==0 or y==0, allowing arbitrary high values for height and width.
	 * We take care of all these bad cases in the constructor. We ensure that if the
	 * shape is a rectangle, the insideCorner is the top-right corner of the
	 * rectangle.
	 */

	public final int width;
	public final int height;
	public final Coordinate insideCorner;

	public EllShape(int width, int height, Coordinate insideCorner) {
		assert (width >= 0 && height >= 0);
		int cornerX = Math.min(insideCorner.x, width);
		int cornerY = Math.min(insideCorner.y, height);
		boolean isEmpty = width == 0 || height == 0 || (cornerX == 0 && cornerY == 0);
		if (isEmpty) {
			this.width = 0;
			this.height = 0;
			this.insideCorner = new Coordinate(0, 0);
			return;
		}
		this.width = cornerY == 0 ? cornerX : width;
		this.height = cornerX == 0 ? cornerY : height;
		boolean isRectangle = cornerX == this.width || cornerY == this.height;
		this.insideCorner = (isRectangle) ? new Coordinate(this.width, this.height) : new Coordinate(cornerX, cornerY);
	}

	// TODO: DONE.
	public int getArea() {
		return (width * insideCorner.y) + (height * insideCorner.x) - (insideCorner.x * insideCorner.y);
	}

	public boolean contains(Coordinate coord) {
		return 0 <= coord.x && coord.x <= width && 0 <= coord.y && coord.y <= height
				&& (coord.x <= insideCorner.x || coord.y <= insideCorner.y);
	}

	// TODO: DONE.
	public int getPackingUpperBound(Tile basicTile) {
		return getArea() / basicTile.getArea();
	}

	// TODO: DONE.
	public Packing packGreedilyHorizontally(Tile basicTile) {
		Packing pack = new Packing(); 
		Tile tile = new Tile(basicTile.getWidth(), basicTile.getHeight());
		if (tile.getWidth() < tile.getHeight())
			tile.flip();
		
		// 3ème Version : Programmation fonctionnelle (plus rapide)
		range(1, width/tile.getWidth()+1)
		.flatMap(x -> IntStream.rangeClosed(1, height/tile.getHeight())
								.mapToObj(y -> new Coordinate(x*tile.getWidth(), y*tile.getHeight())))
		.filter(coord -> contains(coord))
		.forEach(pos -> pack.addTile(new Tile(tile.getWidth(), tile.getHeight(), pos.x - tile.getWidth(), pos.y - tile.getHeight())));

		return pack;
		
		/* 1ère Version codée (lente et laborieuse)
		Coordinate topRightCorner = new Coordinate(tile.getWidth(), tile.getHeight());
		for (;;) {
			if (contains(topRightCorner)) {
				pack.addTile(tile.clone());
				tile.translateBy(tile.getWidth(), 0);
				topRightCorner = topRightCorner.translateBy(tile.getWidth(), 0);
			} else {
				tile.translateBy(-tile.getCornerX(), tile.getHeight());
				topRightCorner = topRightCorner.translateBy(-tile.getCornerX(), tile.getHeight());
				if (contains(topRightCorner)) {
					pack.addTile(tile.clone());
					tile.translateBy(tile.getWidth(), 0);
					topRightCorner = topRightCorner.translateBy(tile.getWidth(), 0);
				} else
					return pack;
			}
		} */
		
		/* 2ème Version : Classique avec 2 boucles while imbriquées (plus rapide)
		int x = tile.getWidth(), y = tile.getHeight();
		while (y <= height) {
			while ((x <= width && y <= insideCorner.y) || (y <= height && x <= insideCorner.x)) {
				pack.addTile(new Tile(tile.getWidth(), tile.getHeight(), x - tile.getWidth(), y - tile.getHeight()));
				x += tile.getWidth();
			}
			y += tile.getHeight();
		} 
		return pack; */
	}

	// TODO: DONE.
	public Packing packGreedilyVertically(Tile basicTile) {
		Packing pack = new Packing(); 
		Tile tile = new Tile(basicTile.getWidth(), basicTile.getHeight());
		if (tile.getWidth() > tile.getHeight())
			tile.flip();
		
		// 3ème Version : Programmation fonctionnelle (plus rapide)
		range(1, width/tile.getWidth()+1)
		.flatMap(x -> IntStream.rangeClosed(1, height/tile.getHeight())
								.mapToObj(y -> new Coordinate(x*tile.getWidth(), y*tile.getHeight())))
		.filter(coord -> contains(coord))
		.forEach(pos -> pack.addTile(new Tile(tile.getWidth(), tile.getHeight(), pos.x - tile.getWidth(), pos.y - tile.getHeight())));

		return pack;
		
		/* 1ère Version codée (lente et laborieuse)
		Coordinate topRightCorner = new Coordinate(tile.getWidth(), tile.getHeight());
		for (;;) {
			if (contains(topRightCorner)) {
				pack.addTile(tile.clone());
				tile.translateBy(tile.getWidth(), 0);
				topRightCorner = topRightCorner.translateBy(tile.getWidth(), 0);
			} else {
				tile.translateBy(-tile.getCornerX(), tile.getHeight());
				topRightCorner = topRightCorner.translateBy(-tile.getCornerX(), tile.getHeight());
				if (contains(topRightCorner)) {
					pack.addTile(tile.clone());
					tile.translateBy(tile.getWidth(), 0);
					topRightCorner = topRightCorner.translateBy(tile.getWidth(), 0);
				} else
					return pack;
			}
		} */
		
		/* 2ème Version : Classique avec 2 boucles while imbriquées (plus rapide)
		int x = tile.getWidth(), y = tile.getHeight();
		while (y <= height) {
			while ((x <= width && y <= insideCorner.y) || (y <= height && x <= insideCorner.x)) {
				pack.addTile(new Tile(tile.getWidth(), tile.getHeight(), x - tile.getWidth(), y - tile.getHeight()));
				x += tile.getWidth();
			}
			y += tile.getHeight();
		} 
		return pack; */
	}

	// TODO: DONE.
	public Packing packGreedily(Tile basicTile) {
		Packing packHor = packGreedilyHorizontally(basicTile);
		Packing packVer = packGreedilyVertically(basicTile);
		return (packHor.size() >= packVer.size()) ? packHor : packVer;
	}

	public boolean isRectangle() {
		return insideCorner.x == width || insideCorner.y == height;
	}

	// TODO: DONE.
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + insideCorner.x;
		result = prime * result + insideCorner.y;
		return result;
	}

	// TODO: DONE.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EllShape other = (EllShape) obj;
		if (height != other.height || width != other.width)
			return false;
		if (insideCorner.x != other.insideCorner.x || insideCorner.y != other.insideCorner.y)
			return false;
		return true;
	}

	public Stream<Subdivision> subdivisions() {
		return isRectangle() ? rectangleSubdivisions() : ellSubdivisions();
	}

	public Stream<Coordinate> asPolygon() {
		return (isRectangle())
				? Stream.of(new Coordinate(0, 0), new Coordinate(width, 0), new Coordinate(width, height),
						new Coordinate(0, height))
				: Stream.of(new Coordinate(0, 0), new Coordinate(width, 0), new Coordinate(width, insideCorner.y),
						new Coordinate(insideCorner.x, insideCorner.y), new Coordinate(insideCorner.x, height),
						new Coordinate(0, height));
	}

	/*
	 * The rest of the files contains the methods to create all the possible
	 * subdivisions of a L-shape into 2 smaller L-shapes. This part is fairly long
	 * because there are many ways to create such subdivisions.
	 * 
	 * Subdivisions are given as Stream, so they can be built lazily. This means
	 * Java 8 or greater is required to compile it.
	 * 
	 * Do not modify anything below this line!
	 */

	private Stream<Subdivision> rectangleSubdivisions() {
		Stream<Subdivision> horizontalCuts = range(1, this.height / 2 + 1)
				.map(y1 -> new Subdivision(PartBuilder.rectangle(width, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.rectangle(width, height - y1).withPosition(0, y1, Rotation.ID)));
		Stream<Subdivision> verticalCuts = range(1, width / 2 + 1)
				.map(x1 -> new Subdivision(PartBuilder.rectangle(x1, height).withPosition(0, 0, Rotation.ID),
						PartBuilder.rectangle(width - x1, height).withPosition(x1, 0, Rotation.ID)));

		Stream<Subdivision> singleCorner = range(1, width).flatMap(x1 -> range(1, height)
				.map(y1 -> new Subdivision(PartBuilder.rectangle(x1, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width, height).withCorner(width - x1, height - y1).withPosition(width, height,
								Rotation.HALF))));

		Stream<Subdivision> doubleCornersVertically = range(1, width)
				.flatMap(x1 -> range(x1 + 1, width).flatMap(x2 -> range(1, height / 2 + 1).map(y1 -> new Subdivision(
						PartBuilder.ell(height, x2).withCorner(height - y1, x1).withPosition(0, height,
								Rotation.THREEQUARTER),
						PartBuilder.ell(height, width - x1).withCorner(y1, width - x2).withPosition(width, 0,
								Rotation.QUARTER)))));

		Stream<Subdivision> doubleCornersHorizontally = range(1, width / 2 + 1)
				.flatMap(x1 -> range(1, height).flatMap(y1 -> range(y1 + 1, height).map(y2 -> new Subdivision(
						PartBuilder.ell(y2, width).withCorner(y1, width - x1).withPosition(width, 0, Rotation.QUARTER),
						PartBuilder.ell(height - y1, width).withCorner(height - y2, x1).withPosition(0, height,
								Rotation.THREEQUARTER)))));

		return Stream.of(horizontalCuts, verticalCuts, singleCorner, doubleCornersHorizontally, doubleCornersVertically)
				.flatMap(x -> x);
	}

	private Stream<Subdivision> ellSubdivisions() {
		Stream<Subdivision> topLeftSubdivisions = range(1, insideCorner.x)
				.flatMap(x1 -> range(insideCorner.y + 1, height).flatMap(y1 -> Stream.of(
						new Subdivision(
								PartBuilder.ell(width - x1, y1).withCorner(insideCorner.x - x1, insideCorner.y)
										.withPosition(x1, 0, Rotation.ID),
								PartBuilder.ell(height, insideCorner.x).withCorner(height - y1, x1).withPosition(0,
										height, Rotation.THREEQUARTER)),
						new Subdivision(
								PartBuilder.ell(width, y1).withCorner(x1, insideCorner.y).withPosition(0, 0,
										Rotation.ID),
								PartBuilder.ell(insideCorner.x, height - insideCorner.y)
										.withCorner(insideCorner.x - x1, this.height - y1)
										.withPosition(insideCorner.x, height, Rotation.HALF)))));

		Stream<Subdivision> bottomRightSubdivisions = range(insideCorner.x + 1, width)
				.flatMap(x1 -> range(1, insideCorner.y).flatMap(y1 -> Stream.of(new Subdivision(
						PartBuilder.ell(x1, height).withCorner(insideCorner.x, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width - insideCorner.x, insideCorner.y)
								.withCorner(width - x1, insideCorner.y - y1)
								.withPosition(width, insideCorner.y, Rotation.HALF)),
						new Subdivision(
								PartBuilder.ell(insideCorner.y, width).withCorner(y1, width - x1).withPosition(width, 0,
										Rotation.QUARTER),
								PartBuilder.ell(x1, height - y1).withCorner(insideCorner.x, insideCorner.y - y1)
										.withPosition(0, y1, Rotation.ID)))));

		Stream<Subdivision> bottomLeftSubdivisions = range(1, insideCorner.x)
				.flatMap(x1 -> range(1, insideCorner.y).flatMap(y1 -> Stream.of(new Subdivision(
						PartBuilder.ell(width, height).withCorner(x1, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width - x1, height - y1).withCorner(insideCorner.x - x1, insideCorner.y - y1)
								.withPosition(x1, y1, Rotation.ID)),
						new Subdivision(
								PartBuilder.ell(insideCorner.y, width).withCorner(y1, width - x1).withPosition(width, 0,
										Rotation.QUARTER),
								PartBuilder.ell(height - y1, insideCorner.x).withCorner(height - insideCorner.y, x1)
										.withPosition(0, height, Rotation.THREEQUARTER)

						),
						new Subdivision(
								PartBuilder.ell(insideCorner.y, width - x1).withCorner(y1, width - insideCorner.x)
										.withPosition(width, 0, Rotation.QUARTER),
								PartBuilder.ell(height, insideCorner.x).withCorner(height - y1, x1).withPosition(0,
										height, Rotation.THREEQUARTER)))));

		Stream<Subdivision> cornerYLineSubdivisions = range(1, insideCorner.x).flatMap(x1 -> Stream.of(
				new Subdivision(PartBuilder.rectangle(width - x1, insideCorner.y).withPosition(x1, 0, Rotation.ID),
						PartBuilder.ell(height, insideCorner.x).withCorner(height - insideCorner.y, x1).withPosition(0,
								height, Rotation.THREEQUARTER)),
				new Subdivision(
						PartBuilder.ell(width, height).withCorner(x1, insideCorner.y).withPosition(0, 0, Rotation.ID),
						PartBuilder.rectangle(insideCorner.x - x1, height - insideCorner.y).withPosition(x1,
								insideCorner.y, Rotation.ID)),
				new Subdivision(PartBuilder.rectangle(x1, height).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width - x1, height).withCorner(insideCorner.x - x1, insideCorner.y)
								.withPosition(x1, 0, Rotation.ID))));

		Stream<Subdivision> cornerXLineSubdivisions = range(1, insideCorner.y).flatMap(y1 -> Stream.of(
				new Subdivision(
						PartBuilder.ell(width, height).withCorner(insideCorner.x, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width - insideCorner.x, insideCorner.y - y1)
								.withCorner(width - insideCorner.x, insideCorner.y - y1)
								.withPosition(insideCorner.x, y1, Rotation.ID)),
				new Subdivision(
						PartBuilder.ell(insideCorner.y, width).withCorner(y1, width - insideCorner.x)
								.withPosition(width, 0, Rotation.QUARTER),
						PartBuilder.ell(insideCorner.x, height - y1).withCorner(insideCorner.x, height - y1)
								.withPosition(0, y1, Rotation.ID)),
				new Subdivision(PartBuilder.rectangle(width, y1).withPosition(0, 0, Rotation.ID),
						PartBuilder.ell(width, height - y1).withCorner(insideCorner.x, insideCorner.y - y1)
								.withPosition(0, y1, Rotation.ID))));

		Stream<Subdivision> horizontalSubdivisions = range(insideCorner.y, height).map(y1 -> new Subdivision(
				PartBuilder.ell(width, y1).withCorner(insideCorner.x, insideCorner.y).withPosition(0, 0, Rotation.ID),
				PartBuilder.rectangle(insideCorner.x, height - y1).withPosition(0, y1, Rotation.ID)));

		Stream<Subdivision> verticalSubdivisions = range(insideCorner.x, width).map(x1 -> new Subdivision(
				PartBuilder.ell(x1, height).withCorner(insideCorner.x, insideCorner.y).withPosition(0, 0, Rotation.ID),
				PartBuilder.rectangle(width - x1, insideCorner.y).withPosition(x1, 0, Rotation.ID)));

		return Stream.of(topLeftSubdivisions, bottomLeftSubdivisions, bottomRightSubdivisions, cornerXLineSubdivisions,
				cornerYLineSubdivisions, verticalSubdivisions, horizontalSubdivisions).flatMap(x -> x);
	}

	private static Stream<Integer> range(int startInclusive, int endExclusive) {
		return IntStream.range(startInclusive, endExclusive).boxed();
	}

}
