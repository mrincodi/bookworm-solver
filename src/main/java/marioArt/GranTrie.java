package marioArt;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class GranTrie {
	java.util.HashMap<Character, java.util.HashMap> bigMap;

	public GranTrie (String filePath) throws FileNotFoundException {

		bigMap = new java.util.HashMap<Character, java.util.HashMap>();

		// Put the words in an array.
		ArrayList<String> result = new ArrayList<String>();

		Scanner s = new Scanner(new FileReader(filePath));
		while (s.hasNext()) {
			result.add(s.nextLine());
		}

		// Put each word in the trie.
		for ( String w: result) {
			addWord(bigMap, w);
		}
	}

	public void addWord (java.util.HashMap<Character, java.util.HashMap> map, String s) {

		if (s.length() == 0) {
			map.put('$', new HashMap<Character, HashMap>());
			return;
		}

		char c = s.charAt(0);
		String rest = s.substring(1);

		if (!map.containsKey(c)) {
			map.put(c, new HashMap<Character, HashMap>());
		}

		addWord(map.get(c), rest);

	}

	// TODO: Let's be honest: adding the starting position to the return string is very ugly.
	// What would be better is to use a structure that saves row and col of each position.
	// so this later can be automated.
	public ArrayList<String> giveResults(LettersMap m, GranTrie g) {

		ArrayList<String> results = new ArrayList<String>();
		// TODO: Do it for the other positions, obviously!!

		for (int col = 0; col < 7; col++){
			for (int row = 0; row < 7; row++){

				if ( row != 7 || col % 2 == 1) {
					ArrayList<String> partialResults = new ArrayList<String>();
					partialResults.addAll(giveResults(m, g, row, col));

					int kcol = col + 1;
					int krow = row + 1;

					for (int i = 0; i < partialResults.size(); i++) {
						partialResults.set(i, partialResults.get(i) + "\tCol: " + kcol + "\tRow: " + krow);
					}
					results.addAll(partialResults);
				}
			}
		}
//		results.addAll (giveResults (m,g,7,1));
//		results.addAll (giveResults (m,g,7,3));
//		results.addAll (giveResults (m,g,7,5));

		return results;
	}

	private ArrayList<String> giveResults(LettersMap m, GranTrie g, int row, int col) {
		return giveResults(m,g.bigMap,row,col,new HashSet<String>());
	}

	private ArrayList<String> giveResults(LettersMap m, HashMap <Character, HashMap> bigTrie, int row, int col,
	                                      HashSet<String> burnt) {
		// OK.
		// First of all, check if this position is burnt.
		ArrayList<String> results = new ArrayList<String>();
		char c = m.map[row][col];

		// If I haven't stepped on that position of the map, and there is at least a word with that letter...
		if ( !isBurnt (burnt, row, col) && bigTrie.containsKey(c)){
			// Wonderful! First, burn the position.
			burnt.add(LettersMap.makeWord(row,col));

			// Is there a word that ENDS with this letter?
			if (bigTrie.get(c).containsKey('$')){
				results.add("");
			}

			for (String neighbor: m.getNeighbors(row,col)){

				int neighborRow = LettersMap.getRow(neighbor);
				int neighborCol = LettersMap.getCol(neighbor);

				results.addAll (giveResults (m, bigTrie.get(c), neighborRow, neighborCol, burnt));
			}

			// Finally, add the character to all the words, and return.
			for ( int i = 0; i < results.size(); i++ ){
				results.set(i,c + results.get(i));
			}

			burnt.remove(LettersMap.makeWord(row,col));
		}

		return results;
	}

	private boolean isBurnt(HashSet<String> burnt, int row, int col) {
			return burnt.contains(LettersMap.makeWord(row, col));
	}


//
//		ArrayList<String> results = new ArrayList<String>();
//		HashSet<String> burnt = new HashSet<String>();
//		ArrayList<String> neighbors = getNeighbors (row, col);
//		GranTrie pointer;
//
//		burnt.add (makeWord(row,col));
//
//		for ( String neighbor: neighbors){
//			pointer = g;
//
//			if (!burnt.contains(neighbor)){
//				// OK; let's see if there is a word in the trie.
//			}
//
//		}
//
//		return results;
//	}



}
