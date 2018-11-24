package geometry;

/* A Packing is a collection of disjoint tiles in the plane.
   A Packing of an L-shape (all tiles are contained into the L-shape) can
   be arranged into a Part having the same L-shape, by using the
   method transform. This is useful when given the optimal packing for
   the L-shape corresponding to some Part, one wants to get the optimal
   packing for that Part. The method concat is used to merge two disjoint
   Packing.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Packing implements Iterable<Tile> {

	List<Tile> listTiles;
	
	public Packing() {
		this.listTiles = new ArrayList<Tile>();
	}
	
	public int size() {
		return listTiles.size();
	}
	
	public void addTile(Tile t) {
		listTiles.add(t);
	}
	
	public Packing transform(Part part) {
		Packing result = new Packing();
		for (Tile tile : this.listTiles) {
			Tile copy = tile.clone();
			copy.rotateBy(part.rotation);
			copy.translateBy(part.position);
			result.addTile(copy);
		}
		return result;		
	}
	
	public static Packing concat(Packing pack1, Packing pack2) {
		Packing result = new Packing();
		for (Tile tile : pack1) {
			result.addTile(tile);
		};
		for (Tile tile : pack2) {
			result.addTile(tile);
		};
		return result;		
	}
	
	public Iterator<Tile> iterator() {
		return listTiles.iterator();
	}
	
}
