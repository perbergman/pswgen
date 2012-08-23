package pb.tps.reader;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import pb.tps.model.Book;
import pb.tps.model.ItemLink;
import pb.tps.model.Links;

public class BandXLSReader extends Links<ItemLink> implements Runnable {

	private boolean isAll = true;
	private int defPageId = 0;
	private Book book;

	public BandXLSReader(String fileName, int defPageId) {
		this.book = new Book(fileName);
		this.defPageId = defPageId;
	}

	/**
	 * Creates a multi map with sorted keys and sorted values for each key
	 * 
	 * @return
	 */
	@Override
	public void run() {
		Sheet sheet = book.getSheet(0);
		int rowIndex = 0;

		for (Row row : sheet) {
			if (rowIndex == 0) {
				rowIndex++;
				continue;
			}

			if (row.getCell(1) == null) {
				break;
			}

			int pageId = 0;
			String value = row.getCell(1).toString();
			if (!StringUtils.isBlank(value)) {
				Double page = -1d;
				try {
					page = row.getCell(1).getNumericCellValue();
				} catch (Exception e) {
					System.out.println("ROW " + rowIndex + " v " + value);
					e.printStackTrace();
				}
				pageId = page.intValue();
			}

			String band = row.getCell(0).getStringCellValue();
			if (isAll || pageId > 0) {
				if (pageId == 0) {
					pageId = defPageId;
				}
				this.add(new ItemLink(band, pageId));
			} else {
				ItemLink bl = new ItemLink(band, 0);
				bl.setIsEmpty(true);
				this.add(bl);
			}
			rowIndex++;
		}
	}

}