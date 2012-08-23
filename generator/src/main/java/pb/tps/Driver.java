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
	
	public Driver(String bands, String venLocs) {
		this.bands = bands;
		this.venLocs = venLocs;
	}

	public void bands(String outp, int def) {
		BandXLSReader rd = new BandXLSReader(bands, def);
		rd.run();
		ItemHTMLWriter wr = new ItemHTMLWriter(outp, rd.getLinks());
		wr.run(3);
	}

	public TreeMultimap<String, ItemLink> venues(String outp, int def, int cols) {
		VenueXLSReader rd = new VenueXLSReader(venLocs, def);
		rd.run();
		TreeMultimap<String, ItemLink> data = rd.getLinks();
		ItemHTMLWriter wr = new ItemHTMLWriter(outp, data);
		wr.run(cols);
		return data;
	}

	public void locations(TreeMultimap<String, ItemLink> venues, String inp,
			int cols) {
		LocationsXLSReader rd = new LocationsXLSReader(venLocs);
		rd.run();
		rd.addLinks(venues);
		LocationsHTMLWriter wr = new LocationsHTMLWriter(rd, inp);
		wr.run(cols);
	}



}
