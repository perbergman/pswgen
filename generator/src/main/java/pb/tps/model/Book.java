package pb.tps.model;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Book {
	XSSFWorkbook book = null;

	public Book(String fileName) {
		try {
			InputStream inp = new FileInputStream(fileName);
			book = new XSSFWorkbook(inp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sheet getSheet(int no) {
		return book.getSheetAt(no);
	}

	public void close() {
	}

}
