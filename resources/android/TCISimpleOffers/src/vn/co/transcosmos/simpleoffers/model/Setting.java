package vn.co.transcosmos.simpleoffers.model;

public class Setting {

	String title;
	boolean isChecked;

	public Setting(String title, boolean isChecked) {
		super();
		this.title = title;
		this.isChecked = isChecked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
