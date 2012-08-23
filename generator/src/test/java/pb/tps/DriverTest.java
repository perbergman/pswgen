package pb.tps;

import org.junit.Test;

import pb.tps.model.ItemLink;

import com.google.common.collect.TreeMultimap;

public class DriverTest {

	private static final String VL = "/Users/perbergman/Google Drive/PSW/VenuesLocations_003.xlsx";
	private static final String BANDS = "/Users/perbergman/Google Drive/PSW/Band_Artist_List_008.xlsx";

	@Test
	public void testGen() {
		Driver m = new Driver(BANDS, VL);
		m.bands("out/bands.html", 914);
		String venuesFile = "out/venues.html";
		int cols = 3;
		TreeMultimap<String, ItemLink> venues = m
				.venues(venuesFile, 1073, cols);
		m.locations(venues, "out/locations.html", cols);
	}
}
