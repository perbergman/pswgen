package pb.tps.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Represents one geographical group
 * 
 * @author pbergman
 * 
 */
public class Group {

	private String header;
	private List<String> supers = Lists.newLinkedList();

	public Group() {
	}

	public String getHeader() {
		return header;
	}

	public List<String> getSupers() {
		return supers;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setSupers(List<String> supers) {
		this.supers = supers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [header=").append(header).append(", supers=")
				.append(supers).append("]");
		return builder.toString();
	}

}
