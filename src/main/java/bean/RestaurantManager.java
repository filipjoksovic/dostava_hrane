package bean;

public class RestaurantManager {
	private int restaurant_id;
	private String username;
	
	public RestaurantManager(int id, String username) {
		this.restaurant_id = id;
		this.username = username;
	}
	
	public int getRestaurantID() {
		return this.restaurant_id;
	}
	public String getUsername() {
		return this.username;
	}
	public void setRestaurantID(int id) {
		this.restaurant_id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
