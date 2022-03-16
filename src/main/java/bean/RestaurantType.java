package bean;

public class RestaurantType {
	private int id;
	private String title;
	
	public RestaurantType(int id, String title) {
		this.id = id;
		this.title = title;
	}
	public RestaurantType(String[] fields) {
		this.id = Integer.parseInt(fields[0]);
		this.title = fields[1];
	}
	public int getID() {
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
