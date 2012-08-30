package pb.tps.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import pb.tps.model.Book;
import pb.tps.model.ItemLink;
import pb.tps.model.Links;

import com.google.common.collect.TreeMultimap;

public class VenueXLSReader extends Links<ItemLink> {

	private static final int META_COL = 0;
	private static final int NAME_COL = 1;
	private static final int PAGE_COL = 2;
	private static final int LOC_COL = 3;
	// 4 is main location not used here
	private static final int URL_COL = 5;

	private boolean isAll = true;
	private int defPageId = 0;
	private Book book;

	public VenueXLSReader(String fileName, int defPageId) {
		this.book = new Book(fileName);
		this.defPageId = defPageId;
	}

	/**
	 * Creates a multi map with sorted keys and sorted values for each key
	 * 
	 * @return
	 */
	public TreeMultimap<String, ItemLink> run() {
		Sheet sheet = book.getSheet(0);
		int rowIndex = 0;

		for (Row row : sheet) {
			// Skip header
			if (rowIndex == 0) {
				rowIndex++;
				continue;
			}

			String venue = row.getCell(NAME_COL).toString();
			int pageId = 0;

			if (row.getCell(PAGE_COL) != null) {
				Double page = row.getCell(PAGE_COL).getNumericCellValue();
				pageId = page.intValue();
			}

			String location = row.getCell(LOC_COL).getStringCellValue();
			String url = "";
			if (row.getCell(URL_COL) != null) {
				url = row.getCell(URL_COL).getStringCellValue();
			}

			// System.out.println(rowIndex + " venue: " + venue + ", page: "
			// + pageId + ", location: " + location + ", url: " + url);

			String sort = "";
			if (row.getCell(META_COL) != null) {
				sort = row.getCell(META_COL).toString().trim();
			}

			if (isAll || pageId > 0) {
				if (pageId == 0) {
					pageId = defPageId;
				}
				this.add(new ItemLink(sort, venue, location, pageId, url));
			} else {
				ItemLink vl = new ItemLink(sort, venue, 0);
				vl.setIsEmpty(true);
				this.add(vl);
			}

			rowIndex++;
		}
		return getLinks();
	}

}