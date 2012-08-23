package pb.tps.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import pb.tps.model.Book;
import pb.tps.model.ItemLink;
import pb.tps.model.Links;

import com.google.common.collect.TreeMultimap;

public class VenueXLSReader extends Links<ItemLink> {

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

			String venue = row.getCell(0).toString();
			int pageId = 0;

			if (row.getCell(1) != null) {
				Double page = row.getCell(1).getNumericCellValue();
				pageId = page.intValue();
			}

			String location = row.getCell(2).getStringCellValue();
			String url = "";
			if (row.getCell(4) != null) {
				url = row.getCell(4).getStringCellValue();
			}

			// System.out.println(rowIndex + " venue: " + venue + ", page: "
			// + pageId + ", location: " + location + ", url: " + url);

			if (isAll || pageId > 0) {
				if (pageId == 0) {
					pageId = defPageId;
				}
				this.add(new ItemLink(venue, location, pageId, url));
			} else {
				ItemLink vl = new ItemLink(venue, 0);
				vl.setIsEmpty(true);
				this.add(vl);
			}

			rowIndex++;
		}
		return getLinks();
	}

}