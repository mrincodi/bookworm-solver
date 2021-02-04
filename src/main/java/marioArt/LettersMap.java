package marioArt;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LettersMap {

	// TODO: Create constants for number of rows and cols.
	char [][] map;
	public LettersMap(String filePath) throws FileNotFoundException {
		map = new char[8][7];

		// TODO: Validate map.
		// TODO: It's a file, number of lines and rows, etc.
		Scanner s = new Scanner(new FileReader(filePath));

		int row = 0;
		//
		for ( row = 0; row < 7; row++) {
			String linea = s.nextLine();

			for ( int col = 0; col < 7; col++){
				map [ row ][col] = linea.charAt(col);
			}
		}
		// The last one has a special treatment.
		String last = s.nextLine();
		map [ 7 ][ 1 ] = last.charAt(0);
		map [ 7 ][ 3 ] = last.charAt(1);
		map [ 7 ][ 5 ] = last.charAt(2);
	}

	public LettersMap(char [][] map) {
		this.map=map;
	}
	
	public static int getRow(String neighbor) {
		int result = neighbor.charAt(0) - '0';
		return result;
	}

	// TODO: All is again static here. What if there were more than 10 cols?
	public static int getCol(String neighbor) {
		int result = neighbor.charAt(2) - '0';
		return result;
	}

	// TODO: Test THIS.
	public ArrayList<String> getNeighbors (int row, int col){
		ArrayList<String> result = new ArrayList<String>();

		// First, let's deduce for the left border.
		// TODO: I guess this can be optimized. Also moved to using constants.
		if ( col == 0 ){
			if ( row == 0 ){
				result.add("0_1");
				result.add("1_1");
				result.add("1_0");
			}
			else if ( row == 6 ){
				result.add("5_0");
				result.add("6_1");
				result.add("7_1");
			}
			else {
				result.add ( makeWord(row-1,0));
				result.add ( makeWord(row,1));
				result.add ( makeWord(row+1,1));
				result.add ( makeWord(row+1,0));
			}

		} else if (col == 6) {
			if ( row == 0 ){
				result.add("0_5");
				result.add("1_5");
				result.add("1_6");
			}
			else if ( row == 6 ){
				result.add("5_6");
				result.add("6_5");
				result.add("7_5");
			}
			else {
				result.add ( makeWord(row-1,6));
				result.add ( makeWord(row,5));
				result.add ( makeWord(row+1,5));
				result.add ( makeWord(row+1,6));
			}
		}

		//Columns 1, 3 and 5.
		else if (col % 2 == 1){
			if ( row == 0) {
				result.add(makeWord(0, col - 1));
				result.add(makeWord(1, col));
				result.add(makeWord(0, col + 1));
			}
			else if ( row == 7){
				result.add(makeWord(6, col - 1));
				result.add(makeWord(6, col));
				result.add(makeWord(6, col + 1));
			}
			else {
				result.add(makeWord(row - 1, col));
				result.add(makeWord(row - 1, col + 1));
				result.add(makeWord(row, col + 1));
				result.add(makeWord(row + 1, col));
				result.add(makeWord(row, col - 1));
				result.add(makeWord(row -1, col - 1));
			}
		}

		// Col. 2 and 4.
		else {
			if ( row == 0 ){
				result.add(makeWord(0, col - 1));
				result.add(makeWord(1, col - 1));
				result.add(makeWord(1, col));
				result.add(makeWord(1, col + 1));
				result.add(makeWord(0, col + 1));
			}
			else if ( row == 6 ){
				result.add(makeWord(7, col - 1));
				result.add(makeWord(6, col - 1));
				result.add(makeWord(5, col));
				result.add(makeWord(6, col + 1));
				result.add(makeWord(7, col + 1));
			}
			else {
				result.add(makeWord(row - 1, col));
				result.add(makeWord(row, col + 1));
				result.add(makeWord(row + 1, col + 1));
				result.add(makeWord(row + 1, col));
				result.add(makeWord(row + 1, col - 1));
				result.add(makeWord(row, col - 1));
				result.add(makeWord(row - 1, col));
			}

		}
		return result;
	}

	public static String makeWord(int row, int col) {
		return row + "_" + col;
	}

}
