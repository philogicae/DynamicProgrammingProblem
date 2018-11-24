/* DrawArea are used to display images of packings.
   Use addTile and addPart to add elements to display.
 */

import geometry.Coordinate;
import geometry.Part;
import geometry.Tile;

import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.Math;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class DrawArea extends JPanel {

  public static final Color
    VerticalTileColor = new Color(0.2f, 0.2f, 0.9f),
    HorizontalTileColor = new Color(0.9f, 0.2f, 0.2f),
    VerticalTileSquareColor = new Color(0.5f, 0.5f, 0.9f),
    HorizontalTileSquareColor = new Color(0.9f, 0.5f, 0.5f);


	public DrawArea(int width, int height) {
		parts = new ArrayList<Part>();
		tiles = new ArrayList<Tile>();
		setSize(new Dimension(width,height));
		setPreferredSize(new Dimension(600,600));
	}


	public void addTile(Tile t) {
		tiles.add(t);
	}

	public void addPart(Part part) {
		parts.add(part);
	}


	public void clear() {
		tiles = new ArrayList<Tile>();
		parts = new ArrayList<Part>();
	}


  public void setSize(Dimension dim) {
    this.side =
      Math.max(20,
               Math.min(600 / Math.max(1,dim.width),
                        600 / Math.max(1,dim.height)));
  }


  private static final int
    ellMargin = 0,
    tileMargin = 2;

  private int side;
  private ArrayList<Part> parts;
  private ArrayList<Tile> tiles;


  private Coordinate toCanvasPosition(Coordinate coord) {
	  return new Coordinate(
	    coord.x == 0 ? ellMargin : side * coord.x - ellMargin,
      coord.y == 0 ? ellMargin : side * coord.y - ellMargin);
  }


	private void drawEll(Graphics g, Part part) {
		List<Coordinate> coordinates =
			part.shape.asPolygon()
      .map(this::toCanvasPosition)
      .map(coord -> coord.rotateBy(part.rotation))
      .map(coord -> coord.translateBy(part.position))
      .collect(Collectors.toList());
		g.fillPolygon(coordinates.stream().mapToInt(coord -> coord.x).toArray(),
                  coordinates.stream().mapToInt(coord -> coord.y).toArray(),
                  coordinates.size());
	}


	private void drawTile(Graphics g, Tile tile) {
    g.setColor(tile.getHeight() > tile.getWidth() ?
                 VerticalTileColor :
                 HorizontalTileColor);
		g.fillRect(
			side * tile.getCornerX() + tileMargin, 
			side * tile.getCornerY() + tileMargin, 
			side * tile.getWidth() - 2 * tileMargin,
			side * tile.getHeight()- 2 * tileMargin
		);

    g.setColor( tile.getHeight() > tile.getWidth() ?
                  VerticalTileSquareColor :
                  HorizontalTileSquareColor);
    int innerMargin = Math.max(2, side/20);
		for (int w = 0; w < tile.getWidth(); w++) {
			for (int h = 0; h < tile.getHeight(); h++) {
				g.fillRect (
				  side * (tile.getCornerX() + w) + 4 * innerMargin,
				  side * (tile.getCornerY() + h) + 4 * innerMargin,
				  side - 8 * innerMargin,
				  side - 8 * innerMargin
				);
			};
		};
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0.1f,0.1f,0.1f));
		for (Part part : parts) {
			drawEll(g, part);
		};
		for (Tile tile : tiles) {
			drawTile(g,tile);
		}
	}
	
}
