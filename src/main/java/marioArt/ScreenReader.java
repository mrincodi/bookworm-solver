package marioArt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.sikuli.script.*;

public class ScreenReader {

	static int SQUARE_HALF = 30;
	static int SQUARE_SIDE = SQUARE_HALF * 2;
	static int BOOKWORM_LOGO_WIDTH_HALF = 158;
	static int BOOKWORM_LOGO_HEIGHT_HALF = 53;

	static String WORK_DIR = "/Users/mario.rincon/src/personal/bookworm-solver/src/main/resources/";
	static String IMAGE_PATH = WORK_DIR + "img/";
	static String LETTERS_IMAGE_PATH = IMAGE_PATH + "letters/";
	static String DICTIONARY_PATH = WORK_DIR + "engmix.txt";

	public static void takePictures() throws FindFailed, InterruptedException, IOException {

		Screen s = new Screen();

		System.out.println("Taking some pictures!!");

		// Look for the bookworm logo.
		String logo = IMAGE_PATH + "bw.png";
		Location l = s.find(logo).getTarget();
		int x = l.getX();
		int y = l.getY();

		// Move to the first letter.
		x += BOOKWORM_LOGO_WIDTH_HALF;
		y += BOOKWORM_LOGO_HEIGHT_HALF;
		int x_start = x;

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {

//				System.out.println("xx Y vamos por " + i + "," + j);
//				System.out.println("x: " + x + ", y: " + y);

				// Borders are removed because these are separations between letters,
				// and left and down ones can be black on the borders.
				// Region reg = new Region(x - SQUARE_HALF + 1, y - SQUARE_HALF + 1, SQUARE_SIDE
				// - 3, SQUARE_SIDE - 3);
				Region reg = new Region(x - SQUARE_HALF, y - SQUARE_HALF, SQUARE_SIDE - 2, SQUARE_SIDE - 2);

				// Take picture!!
				String letterPath = IMAGE_PATH + "p" + i + "_" + j + ".png";
				ImageIO.write(s.capture(reg).getImage(), "png", new File(letterPath));

				// If it's even, move the pointer up, else down.
				x += SQUARE_SIDE;

				int sign = j % 2 == 0 ? -1 : 1;

				y += sign * SQUARE_HALF;
			}
			y += SQUARE_SIDE + SQUARE_HALF;
			x = x_start;
		}

	}

	public static void play() throws FindFailed, InterruptedException, IOException {
		ArrayList<String> mapAL = getLetters();

		// Put all this dance inside LettersMap.
		char [][] map = new char [8][7];
		
		for ( int row = 0; row < 7; row++) {
			String thisLine = mapAL.get(row);
			int col = 0;
			for ( char c: thisLine.toCharArray()) {
				map [row][col]=c;
				col++;
			}
		}
		map[7][1]= mapAL.get(7).charAt(0);
		map[7][3]= mapAL.get(7).charAt(1);
		map[7][5]= mapAL.get(7).charAt(2);
		
		for (String line : mapAL) {
			System.out.println(line);
		}
		
		GranTrie g = new GranTrie(DICTIONARY_PATH);
		LettersMap m = new LettersMap (map);
		ArrayList<String> results = g.giveResults(m,g);
		Misc.showResultsInNiceFormat(results);
	}

	public static ArrayList<String> getLetters() throws FindFailed, InterruptedException, IOException {

		ArrayList<String> result = new ArrayList<String>();

		Screen s = new Screen();

		System.out.println("Reading the letters!!");

		// Look for the bookworm logo.
		String logo = IMAGE_PATH + "bw.png";
		Location l = s.find(logo).getTarget();
		int x = l.getX();
		int y = l.getY();

		// Move to the first letter.
		x += BOOKWORM_LOGO_WIDTH_HALF;
		y += BOOKWORM_LOGO_HEIGHT_HALF;
		int x_start = x;
		int y_start = y;

		for (int i = 0; i < 7; i++) {
			String thisLine = "";
			for (int j = 0; j < 7; j++) {

//				System.out.println("xx Y vamos por " + i + "," + j);
//				System.out.println("x: " + x + ", y: " + y);

				// Borders are removed because these are separations between letters,
				// and left and down ones can be black on the borders.
				// Region reg = new Region(x - SQUARE_HALF + 1, y - SQUARE_HALF + 1, SQUARE_SIDE
				// - 3, SQUARE_SIDE - 3);
				Region reg = new Region(x - SQUARE_HALF, y - SQUARE_HALF, SQUARE_SIDE - 2, SQUARE_SIDE - 2);

				// Match it!
				char c = bestMatch(reg);
				// System.out.println("Got a " + c);
				thisLine += c;

				// If it's even, move the pointer up, else down.
				x += SQUARE_SIDE;

				int sign = j % 2 == 0 ? -1 : 1;

				y += sign * SQUARE_HALF;
			}
			y += SQUARE_SIDE + SQUARE_HALF;
			x = x_start;
			result.add(thisLine);
		}

		// Now, get the last 3 letters.

		x = x_start + SQUARE_SIDE;
		y = y_start + SQUARE_SIDE * 6 + SQUARE_HALF;
		String thisLine = "";
		for (int j = 0; j < 3; j++) {
			Region reg = new Region(x - SQUARE_HALF, y - SQUARE_HALF, SQUARE_SIDE - 2, SQUARE_SIDE - 2);

			// Match it!
			char c = bestMatch(reg);
			thisLine += c;
			// System.out.println("FOR THE END, I got a " + c);

			x += 2 * SQUARE_SIDE;
		}
		result.add(thisLine);

		return result;
	}

	private static char bestMatch(Region reg) {

		char result = 'X';
		// System.out.println("Matching!!");
		// Go over all the letters.
		File folder = new File(LETTERS_IMAGE_PATH);
		File[] listOfFiles = folder.listFiles();

		double bestScore = 0;
		for (int i = 0; i < listOfFiles.length; i++) {

			String filePath = listOfFiles[i].getAbsolutePath();
			String fileName = listOfFiles[i].getName();
			char letter = fileName.charAt(0);

			Match m = reg.exists(filePath, 0);
			if (m != null) {
				double thisScore = m.getScore();

				// System.out.println(filePath + " " + thisScore);

				if (thisScore > bestScore) {
					bestScore = thisScore;
					result = letter;
				}
			} else {
				// System.out.println("No match with " + letter);
			}
		}

		if (result == 'X') {
			System.out.println("WHAT? I DIDN'T FIND IT!");
		}
		return result;
	}

	/*
	 * public static void testing() throws FindFailed, InterruptedException,
	 * IOException { Screen s = new Screen(); // s.find("pause.png"); //identify
	 * pause button // s.click("pause.png"); //click pause button //
	 * System.out.println("pause button clicked"); //
	 * 
	 * System.out.println("Hey!!"); // // Look for the bookworm logo. String logo =
	 * IMAGE_PATH + "bw.png"; Location l = s.find(logo).getTarget(); // identify
	 * play button int x = l.getX(); int y = l.getY();
	 * 
	 * // // Locate the first letter. // Location l1 = new Location(x + 158, y +
	 * 53); //// s.click(l1); //// Thread.sleep(1000); //// s.click(l1); ////
	 * Thread.sleep(1000); //// s.click(l1); // // // Let's get that screen //
	 * Region r = new Region(l1.getX() - SQUARE_HALF, l1.getY() - SQUARE_HALF,
	 * SQUARE_SIDE, SQUARE_SIDE); // // String u = IMAGE_PATH + "u.png"; // Match m
	 * = r.find(u); // // double score = m.getScore(); // //
	 * System.out.println("Y esssss..." + score);
	 * 
	 * // Location pointer = new Location(x + 158, y + 53);
	 * 
	 * x += 158; y += 53;
	 * 
	 * for (int i = 0; i < 7; i++) { int j = 0;
	 * 
	 * System.out.println("Y vamos por " + i + "," + j); Region reg = new Region(x -
	 * SQUARE_HALF, y - SQUARE_HALF, SQUARE_SIDE, SQUARE_SIDE);
	 * 
	 * // Take picture!! String letterPath = IMAGE_PATH + "p" + i + "_" + j +
	 * ".png"; ImageIO.write(s.capture(reg).getImage(), "png", new
	 * File(letterPath));
	 * 
	 * // If it's even, move the pointer up, else down. x += SQUARE_SIDE;
	 * 
	 * int sign = i % 2 == 0 ? -1 : 1;
	 * 
	 * y += sign * SQUARE_HALF; }
	 * 
	 * // Screen s1 = new Screen(); // ImageIO.write(s.capture(r).getImage(), "png",
	 * new File(IMAGE_PATH + "veamos.png")); // // String aver = r.text(); //// //
	 * System.out.println ("A VEEEER.... " + aver); // //
	 * 
	 * //// System.out.println () // s.click(logo); // click play button //
	 * System.out.println("A veeer"); // // Location ll = new Location(5,200); //
	 * s.click(ll);
	 * 
	 * // Thread.sleep(1000); //// s.click(
	 * "/Users/mario.rincon/eclipse-workspace/TestSikuliArt/src/main/resources/play.png"
	 * ); //click play button //// System.out.println("A veeer"); ////
	 * Thread.sleep(1000); //// s.click(
	 * "/Users/mario.rincon/eclipse-workspace/TestSikuliArt/src/main/resources/play.png"
	 * ); //click play button // // System.out.println("A veeer"); }
	 */

}
