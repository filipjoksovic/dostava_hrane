package bean;

public class UserType {
	private String title;
	private int points_needed;
	private int discount;

	public UserType(String title, int discount, int points_needed) {
		this.title = title;
		this.points_needed = points_needed;
		this.discount = discount;
	}

	public UserType() {
		this.title = null;
		this.points_needed = 0;
		this.discount = 0;
	}

	public UserType(String[] fields) {
		this.title = fields[0];
		this.discount = Integer.parseInt(fields[1]);
		this.points_needed = Integer.parseInt(fields[2]);
	}

	public String getTitle() {
		return this.title;
	}

	public int getNeededPoints() {
		return this.points_needed;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNeededPoint(int points_needed) {
		this.points_needed = points_needed;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
