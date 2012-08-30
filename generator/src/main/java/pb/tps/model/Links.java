package pb.tps.model;

import java.lang.Character.UnicodeBlock;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

public class Links<T extends Named> {

	protected final TreeMultimap<String, T> links = TreeMultimap.create(
			Ordering.natural(), new Ordering<T>() {
				@Override
				public int compare(T left, T right) {
					String n1 = left.getName().toLowerCase();
					String n2 = right.getName().toLowerCase();
					return n1.compareTo(n2);
				}
			});

	public TreeMultimap<String, T> getLinks() {
		return links;
	}

	protected void add(T vl) {
		String key = getSortField(vl);
		getLinks().put(key, vl);
	}

	public String getSortField(T vl) {
		String sortCol = vl.getSort();
		return sortCol.toUpperCase().substring(0, 1);
	}

	protected void dump(String s, T vl) {
		UnicodeBlock block = UnicodeBlock.of(s.charAt(0));
		if (block != UnicodeBlock.BASIC_LATIN) {
			System.out.println(vl);
			for (int i = 0; i < s.length(); i++) {
				int cp = s.codePointAt(i);
				System.out.print(Integer.toHexString(cp) + " ");
			}
			System.out.println();
		}
	}

}