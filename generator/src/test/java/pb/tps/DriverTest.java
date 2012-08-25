package pb.tps;

import org.junit.Test;

import pb.tps.model.ItemLink;

import com.google.common.collect.TreeMultimap;

public class DriverTest {

	private static final String VL = "/Users/perbergman/Google Drive/PSW/VenuesLocations_003.xlsx";
	private static final String BANDS = "/Users/perbergman/Google Drive/PSW/Band_Artist_List_008.xlsx";

	private static final String url = "https://www.thepinkspiderweb.com";
	private static final String mapUrl = "https://www.thepinkspiderweb.com/wp-content/uploads/2012/06/tokyo_static_map.png";

	private static final int BAND_DEFAULT = 914;
	private static final int VENUE_DEFAULT = 1073;
	private static final int COLS = 3;

	@Test
	public void testGen() {
		Driver m = new Driver(BANDS, VL);
		m.bands("out/bands.html", BAND_DEFAULT, url);
		String venuesFile = "out/venues.html";
		int cols = COLS;
		;
		TreeMultimap<String, ItemLink> venues = m.venues(venuesFile,
				VENUE_DEFAULT, cols, url);
		m.locations(venues, "out/locations.html", cols, url, mapUrl);
	}
}
