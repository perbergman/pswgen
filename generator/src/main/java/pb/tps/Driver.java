package pb.tps;

import pb.tps.model.ItemLink;
import pb.tps.reader.BandXLSReader;
import pb.tps.reader.LocationsXLSReader;
import pb.tps.reader.VenueXLSReader;
import pb.tps.writer.ItemHTMLWriter;
import pb.tps.writer.LocationsHTMLWriter;

import com.google.common.collect.TreeMultimap;

public class Driver {

	private String bands;
	private String venLocs;
	private int tablew = 600;
	private int tdw = 200;

	public Driver(String bands, String venLocs) {
		this.bands = bands;
		this.venLocs = venLocs;
	}

	public void bands(String outp, int def, String url, int cols) {
		BandXLSReader rd = new BandXLSReader(bands, def);
		rd.run();

		ItemHTMLWriter wr = new ItemHTMLWriter(outp, rd.getLinks(), tablew,
				tdw, url);
		wr.run(cols);
	}

	public TreeMultimap<String, ItemLink> venues(String outp, int def,
			int cols, String url) {
		VenueXLSReader rd = new VenueXLSReader(venLocs, def);
		rd.run();
		TreeMultimap<String, ItemLink> data = rd.getLinks();
		ItemHTMLWriter wr = new ItemHTMLWriter(outp, data, tablew, tdw, url);
		wr.run(cols);
		return data;
	}

	public void locations(TreeMultimap<String, ItemLink> venues, String inp,
			int cols, String url, String mapUrl) {
		LocationsXLSReader rd = new LocationsXLSReader(venLocs);
		rd.run();
		rd.addLinks(venues);
		LocationsHTMLWriter wr = new LocationsHTMLWriter(rd, inp, tablew, tdw,
				url, mapUrl);
		wr.run(cols);
	}

}
