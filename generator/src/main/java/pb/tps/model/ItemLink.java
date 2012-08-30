package pb.tps.model;

import org.apache.commons.lang.StringUtils;

public class ItemLink implements Named {

	private String sort;
	private String name;
	private String location;
	private int page = 0;
	private String url;
	private boolean isEmpty = false;
	private boolean isLongSort = false;

	public ItemLink() {
	}

	public ItemLink(String sort, String name, String location, int page,
			String url) {
		this(sort, name, location, page);

	}

	private void setSort() {
		if (StringUtils.isBlank(sort)) {
			sort = name;
		} else {
			isLongSort = sort.length() > 1;
		}
	}

	public ItemLink(String sort, String name, String location, int page) {
		super();
		this.sort = sort;
		this.name = name;
		this.location = location;
		this.page = page;
		setSort();
	}

	public ItemLink(String sort, String name, int page) {
		super();
		this.sort = sort;
		this.name = name;
		this.page = page;
		setSort();
	}

	public ItemLink(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	@Override
	public String getSort() {
		return sort;
	}

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

	public boolean isLongSort() {
		return isLongSort;
	}

	public void setLongSort(boolean isLongSort) {
		this.isLongSort = isLongSort;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemLink [sort=").append(sort).append(", name=")
				.append(name).append(", location=").append(location)
				.append(", page=").append(page).append(", url=").append(url)
				.append(", isEmpty=").append(isEmpty).append("]");
		return builder.toString();
	}

}