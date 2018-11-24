package dynamicProg;

/* DynamicProgrammingSolver is the class implementing the dynamic program.

  TODO: add structure to memoise computed values.
  TODO: complete class BestSolution.
  TODO: encode recursion using stream of subdivisions (computeOptimalSolution).
  TODO: retrieveOptimalSolution (access memoised value or compute solution)
  TODO: solve
 */

// Réalisé par Idriss Lopes Sanches et Arnaud Soulier

import geometry.EllShape;
import geometry.Packing;
import geometry.Subdivision;
import geometry.Tile;
import java.util.HashMap;
import java.util.Iterator;

public class DynamicProgrammingSolver {
	public final EllShape ground;
	public final Tile basicTile;
	private HashMap<EllShape, Packing> table;

	public DynamicProgrammingSolver(EllShape ground, Tile basicTile) {
		this.ground = ground;
		this.basicTile = basicTile;
		this.table = new HashMap<EllShape, Packing>();
	}

	private Packing computeOptimalSolution(EllShape ell) {
		BestSolution solution = new BestSolution(ell, basicTile, this);
		
		if (solution.isValid() && !solution.isOptimal()) {
			Iterator<Subdivision> iter = ell.subdivisions().iterator();
			while (iter.hasNext() && !solution.isOptimal()) {
				Subdivision sub = iter.next();
				solution.accept(sub);
			}
		}
		table.put(ell, solution.best());
//		System.out.println("add : " + ell.width + " " + ell.height + " " + ell.insideCorner.x + " " + ell.insideCorner.y);
		return solution.best();
	}

	public Packing retrieveOptimalSolution(EllShape ell) {
		return (table.containsKey(ell)) ? table.get(ell) : computeOptimalSolution(ell);
	}

	public Packing solve() {
		System.out.println("\nTILE : " + basicTile.getWidth() + " " + basicTile.getHeight());
		System.out.println("SHAPE : " + ground.width + " " + ground.height);
		long startTime = System.nanoTime();
		System.out.println("Processing...");
		Packing pack = retrieveOptimalSolution(ground);
		System.out.println("Solution trouvée : " + pack.size() + " tuiles");
//		Iterator<Tile> iter = pack.iterator();
//		while (iter.hasNext()) {
//			Tile tile = iter.next();
//			System.out.println(
//					tile.getWidth() + ", " + tile.getHeight() + " : " + tile.getCornerX() + ", " + tile.getCornerY());
//		}
		System.out.println("Runtime : " + (System.nanoTime() - startTime) / 1000000000.0 + " second(s).");
		return pack;
	}
}
