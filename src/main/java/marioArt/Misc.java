package marioArt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class StringLengthComparator implements Comparator<String> {
	public int compare(String x, String y) {
		return x.length() - y.length();
//		// Assume neither string is null. Real code should
//		// probably be more robust
//		// You could also just return x.length() - y.length(),
//		// which would be more efficient.
//		if (x.length() < y.length()) {
//			return -1;
//		}
//		if (x.length() > y.length()) {
//			return 1;
//		}
//		return 0;
	}
}


public class Misc {

	public static void showResultsInNiceFormat ( ArrayList< String > words){
		sortBySize (words);
	}

	public static void sortBySize ( ArrayList< String > words){
		Comparator<String> comparator = new StringLengthComparator();
		PriorityQueue<String> heap = new PriorityQueue<String>(100,comparator);
		for ( String w: words){
			heap.add(w);
		}
		while (heap.size() > 0 ){
			System.out.println(heap.remove());
		}

	}

}
