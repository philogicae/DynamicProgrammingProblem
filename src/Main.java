
/* usage: takes as argument a file containing the instances to solve

 */

import dynamicProg.DynamicProgrammingSolver;
import geometry.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFrame;

public class Main {

	private static Scanner sc;
	private final static JFrame window = new JFrame("Solution");
	private final static DrawArea pict = new DrawArea(13, 13);
	private final static MouseAdapter readAndDrawOnClick = new ReadDrawOnClickHandler();
	private final static MouseAdapter doNothingOnClick = new MouseAdapter() {
	};

	public static void readAndDraw() {
		pict.removeMouseListener(readAndDrawOnClick);
		try {
			pict.addMouseListener(doNothingOnClick);
			int width = sc.nextInt();
			int height = sc.nextInt();
			int w = sc.nextInt();
			int h = sc.nextInt();
			EllShape ground = new EllShape(width, height, new Coordinate(width, height));
			Tile basicTile = new Tile(w, h);
			DynamicProgrammingSolver dyn = new DynamicProgrammingSolver(ground, basicTile);
			Packing sol = dyn.solve();

			pict.clear();
			pict.setSize(new Dimension(width, height));
			pict.addPart(new Part(ground, new Coordinate(0, 0), Rotation.ID));
			for (Tile tile : sol)
				pict.addTile(tile);
			pict.repaint();
			pict.removeMouseListener(doNothingOnClick);
			pict.addMouseListener(readAndDrawOnClick);
		} catch (NoSuchElementException exc) {
			sc.close();
		}
	}

	private static class ReadDrawOnClickHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent ev) {
			readAndDraw();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		System.setIn(new FileInputStream(args[0]));
		sc = new Scanner(System.in);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(pict);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		pict.addMouseListener(readAndDrawOnClick);
		readAndDraw();
	}

}
