package pb.tps.writer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

import org.apache.velocity.VelocityContext;

import pb.tps.model.ItemLink;
import pb.tps.model.VeloAware;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

public class ItemHTMLWriter extends VeloAware {
	private TreeMultimap<String, ItemLink> data = null;
	private int tablew;
	private int tdw;
	private String url;

	public ItemHTMLWriter(String fileName, TreeMultimap<String, ItemLink> data,
			int tablew, int tdw, String url) {
		super(fileName);
		this.data = data;
		this.tablew = tablew;
		this.tdw = tdw;
		this.url = url;

		TreeMultimap<String, ItemLink> nambas = TreeMultimap.create(
				Ordering.natural(), new Ordering<ItemLink>() {
					@Override
					public int compare(ItemLink left, ItemLink right) {
						String n1 = left.getSort();
						String n2 = right.getSort();
						return n1.compareTo(n2);
					}
				});

		// 0-9 bands shall be put under a new header "0-9"
		for (int c = 0; c < 10; c++) {
			String ch = Integer.toString(c);
			SortedSet<ItemLink> values = data.get(ch);
			if (values.size() > 0) {
				SortedSet<ItemLink> killed = data.removeAll(ch);
				nambas.putAll("0-9", killed);
			}
		}

		data.putAll(nambas);
	}

	public void createTOC(List<String> letters, StringBuilder acc)
			throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("list", letters);
		acc.append(this.runVelo("items_header.vm", context));
	}

	public void createContents(String index, List<ItemLink> bands, int max,
			StringBuilder acc) throws Exception {
		VelocityContext context = new VelocityContext();

		int pad = this.addCells(max, bands.size());
		if (pad > 0) {
			for (int i = 0; i < pad; i++) {
				bands.add(new ItemLink(true));
			}
		}

		context.put("list", bands);
		context.put("index", index);
		context.put("max", max);

		context.put("tablew", tablew);
		context.put("tdw", tdw);

		context.put("url", url);

		acc.append(this.runVelo("items_contents.vm", context));
	}

	public void run(int columns) {
		StringBuilder acc = new StringBuilder();
		List<String> letters = Lists.newArrayList(data.keySet());

		try {
			createTOC(letters, acc);
			for (String letter : letters) {
				List<ItemLink> bands = Lists.newArrayList(data.get(letter));

				Collections.sort(bands, new Comparator<ItemLink>() {

					@Override
					public int compare(ItemLink left, ItemLink right) {
						String n1 = left.getSort().toLowerCase();
						String n2 = right.getSort().toLowerCase();
						if (!left.isLongSort()) {
							n1 = left.getName().toLowerCase();
						}
						if (!right.isLongSort()) {
							n2 = right.getName().toLowerCase();
						}

						return n1.compareTo(n2);
					}
				});

				createContents(letter, bands, columns, acc);
			}
			acc.append("</body>\n</html>\n");
			getWriter().write(acc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			getWriter().flush();
			getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
