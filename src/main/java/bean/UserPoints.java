package bean;

public class UserPoints {
	private String username;
	private String order_id;
	private int number_of_points;
	
	public UserPoints(String username, String order_id, int number_of_points) {
		this.username = username;
		this.order_id = order_id;
		this.number_of_points = number_of_points;
	}
	public UserPoints(String[] fields) {
		this.username = fields[0];
		this.order_id = fields[1];
		this.number_of_points = Integer.parseInt(fields[2]);
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getOrderID() {
		return order_id;
	}
	public int getNumberOfPoints() {
		return number_of_points;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setOrderID(String order_id) {
		this.order_id = order_id;
	}
	public void setNumberOfPoints(int number_of_points) {
		this.number_of_points = number_of_points;
	}
	@Override
	public String toString() {
		return this.username + "," + this.order_id + "," + this.number_of_points;
	}
}
