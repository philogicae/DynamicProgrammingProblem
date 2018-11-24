package dynamicProg;

import geometry.EllShape;
import geometry.Packing;
import geometry.Subdivision;
import geometry.Tile;

/* The class BestSolution encodes the best solution found so far
   on a specific L-shape, for a given tile. It has three methods:

   - isOptimal checks whether the current known solution is sure to be optimal,

   - accept takes a subdivision of the L-shape and try to replace the current
     solution by a solution based on the two parts of that subdivision,

   - best returns the current best known solution
   
  It is mostly made to implement the computation of the maximum
  in the recursive formula for the maximum packing.

  TODO: add fields and constructors.
  TODO: use a greedy packing to initialize the solution.
  TODO: implement the three methods.
 */

class BestSolution {
	private final int trivialBound;
	private Packing bestSoFar;
	private DynamicProgrammingSolver solver;

	public BestSolution(EllShape ground, Tile basicTile, DynamicProgrammingSolver solver) {
		trivialBound = ground.getPackingUpperBound(basicTile);
		bestSoFar = ground.packGreedily(basicTile);
		this.solver = solver;
	}

	public boolean isValid() { return bestSoFar.size() > 0; }
	// Car si on ne peut même pas placer une seule tuile, c'est peine perdue.

	public boolean isOptimal() { return bestSoFar.size() == trivialBound; }

	public void accept(Subdivision subdivision) {
		Packing pack1 = solver.retrieveOptimalSolution(subdivision.part1.shape);
		Packing pack2 = solver.retrieveOptimalSolution(subdivision.part2.shape);
		Packing	pack = Packing.concat(pack1.transform(subdivision.part1), pack2.transform(subdivision.part2));
		if (bestSoFar.size() < pack.size())
			bestSoFar = pack;
	}

	public Packing best() { return bestSoFar; }
}
