package pb.tps.model;

public class ItemLink implements Named {
	private String name;
	private String location;
	private int page = 0;
	private String url;
	private boolean isEmpty = false;

	public ItemLink() {
	}

	public ItemLink(String name, String location, int page, String url) {
		this(name, location, page);

	}

	public ItemLink(String name, String location, int page) {
		super();
		this.name = name;
		this.location = location;
		this.page = page;
	}

	public ItemLink(String name, int page) {
		super();
		this.name = name;
		this.page = page;
	}

	public ItemLink(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAlpha() {
		return getName().toUpperCase().substring(0, 1);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemLink [name=").append(name).append(", location=")
				.append(location).append(", page=").append(page)
				.append(", url=").append(url).append(", isEmpty=")
				.append(isEmpty).append("]");
		return builder.toString();
	}

}