package pb.tps.writer;

import java.util.List;

import org.apache.velocity.VelocityContext;

import pb.tps.model.ItemLink;
import pb.tps.model.VeloAware;

import com.google.common.collect.Lists;
import com.google.common.collect.TreeMultimap;

public class ItemHTMLWriter extends VeloAware {
	private TreeMultimap<String, ItemLink> data = null;
	private int tablew;
	private int tdw;

	public ItemHTMLWriter(String fileName, TreeMultimap<String, ItemLink> data,
			int tablew, int tdw) {
		super(fileName);
		this.data = data;
		this.tablew = tablew;
		this.tdw = tdw;
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

		acc.append(this.runVelo("items_contents.vm", context));
	}

	public void run(int columns) {
		StringBuilder acc = new StringBuilder();
		List<String> letters = Lists.newArrayList(data.keySet());

		try {
			createTOC(letters, acc);
			for (String letter : letters) {
				List<ItemLink> bands = Lists.newArrayList(data.get(letter));
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
