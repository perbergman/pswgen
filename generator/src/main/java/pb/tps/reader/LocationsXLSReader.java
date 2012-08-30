package pb.tps.reader;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import pb.tps.model.Book;
import pb.tps.model.Group;
import pb.tps.model.ItemLink;
import pb.tps.model.Links;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

public class LocationsXLSReader extends Links<ItemLink> {

	/**
	 * Maps each super to a list of locations
	 */
	private final Multimap<String, String> supersToSubs = HashMultimap.create();

	/**
	 * Each location needs its super, inversion is used
	 */
	private final Multimap<String, String> subToSuper = HashMultimap.create();

	/**
	 * List of groups
	 */
	private List<Group> groups = Lists.newLinkedList();

	private Book book = null;

	public LocationsXLSReader(String fileName) {
		book = new Book(fileName);
	}

	public SortedSet<ItemLink> getForSuper(List<String> supers) {
		SortedSet<ItemLink> all = new TreeSet<ItemLink>(
				new Ordering<ItemLink>() {
					@Override
					public int compare(ItemLink left, ItemLink right) {
						String n1 = left.getSort();
						String n2 = right.getSort();
						return n1.compareTo(n2);
					}
				});
		for (String sup : supers) {
			all.addAll(getLinks().get(sup));
		}
		return all;
	}

	private void addVenue(ItemLink link) {
		String superLoc = this.search(link.getLocation());
		if (superLoc != null) {
			getLinks().put(superLoc, link);
		} else {
			// System.out.println();("DID not find super for venue "
			// + link.getName() + " at " + link.getLocation());
		}
	}

	private String search(String place) {
		Collection<String> found = subToSuper.get(place);
		if (found != null && !found.isEmpty()) {
			return found.iterator().next();
		}
		return null;
	}

	public void run() {
		Group current = null;
		final Sheet sheet = book.getSheet(1);
		for (Row row : sheet) {
			String firstCell = row.getCell(0).toString();
			// System.out.println("firstCell " + firstCell);
			Cell locs = row.getCell(1);

			// if no locs it is a header:
			if (locs == null) {
				if (current != null) { // it is not the first header
					groups.add(current);
				}
				current = new Group();
				current.setHeader(firstCell);
			} else {
				current.getSupers().add(firstCell);
				for (String loc : Splitter.on(',').split(locs.toString())) {
					loc = loc.trim();
					supersToSubs.put(firstCell, loc);
				}
			}
		}
		// System.out.println(groups);
		// System.out.println(supersToSubs);
		Multimaps.invertFrom(supersToSubs, subToSuper);
		// System.out.println(subToSuper);
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addLinks(TreeMultimap<String, ItemLink> links) {
		for (ItemLink link : links.values()) {
			addVenue(link);
		}
	}
}
