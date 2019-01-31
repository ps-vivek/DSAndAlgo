package algorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * @Date 01/28/2019
 * 
 * Boundary-fill Algorithm. This is an area filling algorithm. ... If we have a specified boundary in a single color, then the fill algorithm proceeds pixel by pixel until the boundary color is encountered.
 * 
 *  Input:
 *  1 1 1 1 1
 *  1 1 1 1 1
 *  1 1 2 1 1
 *  1 2 2 2 1
 *  2 2 2 2 2
 *  
 *  Position of pixel specified for color change: [2,2]
 *  
 *  
 *  Output:
 *  1 1 1 1 1
 *  1 1 1 1 1
 *  1 1 3 1 1
 *  1 3 3 3 1
 *  3 3 3 3 3
 */
public class FloodFillAlgorithm {

	private int[][] image = null;

	private int[][] visited = new int[5][5]; // 5X5 matrix

	private List<List<Integer>> pixelBoundaries = null;

	private static final Integer INCREASE_ROW_COUNT = 1;

	private static final Integer DECREASE_ROW_COUNT = -1;

	private static final Integer INCREASE_COLUMN_COUNT = 1;

	private static final Integer DECREASE_COLUMN_COUNT = -1;

	private static final Integer STATIC_POSITION = 0;

	/*
	 * Read an actual image into a 2d array.
	 * 
	 */
	private void loadPixelBoundaries() {
		pixelBoundaries = new LinkedList<>();
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, DECREASE_COLUMN_COUNT));// left upper diagonal
		pixelBoundaries.add(Arrays.asList(STATIC_POSITION, DECREASE_COLUMN_COUNT));// left
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, DECREASE_COLUMN_COUNT));// left lower diagonal
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, STATIC_POSITION));// down
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, INCREASE_COLUMN_COUNT));// right lower diagonal
		pixelBoundaries.add(Arrays.asList(STATIC_POSITION, INCREASE_COLUMN_COUNT));// right
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, INCREASE_COLUMN_COUNT));// right upper diagonal
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, STATIC_POSITION));// up

	}

	private void loadImage() {
		image = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 2, 1, 1 }, { 1, 2, 2, 2, 1 },
				{ 2, 2, 2, 2, 2 } };
		for (int[] vr : visited) {
			Arrays.fill(vr, 0);
		}
	}

	private void displayImage() {
		int count = image.length;
		System.out.println("Count:" + count);
		for (int[] row : image) {

			for (int col : row) {
				System.out.print(col + " ");
			}
			System.out.println();
		}
	}

	private List<List<Integer>> identifyNeighbors(int rowPixel, int colPixel, int currentColor) {
		// Identify the neighboring coordinates, not the values

		int rowLen = image.length - 1;
		int colLen = image[0].length - 1;
		List<List<Integer>> neighbors = new ArrayList<>();
		for (List<Integer> pixelBoundary : pixelBoundaries) {
			if ((rowPixel + pixelBoundary.get(0)) > rowLen || (rowPixel + pixelBoundary.get(0)) < 0) {
				continue;
			} else if ((colPixel + pixelBoundary.get(1)) > colLen || (colPixel + pixelBoundary.get(1)) < 0) {
				continue;
			} else {
				if (visited[rowPixel + pixelBoundary.get(0)][colPixel + pixelBoundary.get(1)] == 0
						&& image[rowPixel + pixelBoundary.get(0)][colPixel + pixelBoundary.get(1)] == currentColor) {
					neighbors.add(Arrays.asList(rowPixel + pixelBoundary.get(0), colPixel + pixelBoundary.get(1)));
				}

			}
		}

		return neighbors;

	}

	private void performFillColor(int rowPixel, int colPixel, int colorToBeChanged) {
		if (image == null) {
			System.out.println("No image present to perform the algorithm");
			return;
		}
		if (rowPixel < 0 || colPixel < 0) {
			System.out.println("Pixel position should be non-negative");
			return;
		}

		Stack<List<Integer>> neighbors = new Stack<>();
		neighbors.push(Arrays.asList(rowPixel, colPixel));
		int currentColor = image[rowPixel][colPixel];
		while (!neighbors.isEmpty()) {

			List<Integer> neighbor = neighbors.pop();
			int r = neighbor.get(0);
			int c = neighbor.get(1);
			List<List<Integer>> newNeighbors = identifyNeighbors(r, c, currentColor);
			for (List<Integer> l : newNeighbors) {
				neighbors.push(l);
			}
			if (visited[r][c] == 0 && image[r][c] == currentColor) {
				visited[r][c] = 1;
				image[r][c] = colorToBeChanged;
			}
			System.out.println("updated stack");
			for (int i = 0; i < neighbors.size(); i++) {
				System.out.println(neighbors.get(i));
			}
		}
		System.out.println(neighbors.isEmpty());

	}

	public static void main(String[] args) {
		FloodFillAlgorithm ffA = new FloodFillAlgorithm();
		ffA.loadPixelBoundaries();
		ffA.loadImage();
		ffA.displayImage();
		ffA.performFillColor(2, 2, 3);
		ffA.displayImage();
	}

}