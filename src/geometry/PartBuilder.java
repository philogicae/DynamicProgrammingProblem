package geometry;

/* Builder for parts, to make the creating of Part more readable.
   To create a part:
   PartBuilder.part(width,height)
     .withCorner(insideCornerX, insideCornerY)
     .withPosition(originX, originY, direction)
 */

public class PartBuilder {

  private EllShape ellShape = new EllShape(0, 0, new Coordinate(0, 0));
  private int width = 0;
  private int height = 0;

  private PartBuilder() {}

  public static PartBuilder ell(int width, int height) {
    PartBuilder builder=  new PartBuilder();
    builder.width = width;
    builder.height = height;
    return builder;
  }

  public static PartBuilder rectangle(int width, int height) {
    return ell(width,height).withCorner(width,height);
  }

  public PartBuilder withCorner(int cornerX, int cornerY) {
    ellShape = new EllShape(width, height, new Coordinate(cornerX,cornerY));
    return this;
  }

  public Part withPosition(int positionX, int positionY, Rotation rotation) {
    return new Part(ellShape, new Coordinate(positionX,positionY), rotation);
  }

}
