package pb.tps.model;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

public class Links<T extends Named> {

	protected final TreeMultimap<String, T> links = TreeMultimap.create(
			Ordering.natural(), new Ordering<T>() {
				@Override
				public int compare(T left, T right) {
					String n1 = left.getName();
					String n2 = right.getName();
					return n1.compareTo(n2);
				}
			});

	public TreeMultimap<String, T> getLinks() {
		return links;
	}

	protected void add(T vl) {
		getLinks().put(getAlpha(vl), vl);
	}

	public String getAlpha(T vl) {
		return vl.getName().toUpperCase().substring(0, 1);
	}

	// protected void dump(String s) {
	// UnicodeBlock block = UnicodeBlock.of(s.charAt(0));
	// if (block != UnicodeBlock.BASIC_LATIN) {
	// for (int i = 0; i < s.length(); i++) {
	// int cp = s.codePointAt(i);
	// // System.out.print(Integer.toHexString(cp) + " ");
	// }
	// // System.out.println();
	// }
	// }

}