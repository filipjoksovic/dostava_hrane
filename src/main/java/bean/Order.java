package bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private String id;
	private String username;
	private int product_id;
	private int product_quantity;
	private String user_first_name;
	private String user_last_name;
	private String address;
	private String city;
	private String country;
	private int status;
	Timestamp timestamp;

	@Override
	public String toString() {
		return this.id + "," + this.username + "," + this.product_id + "," + this.product_quantity + ","
				+ this.user_first_name + "," + this.user_last_name + "," + address + "," + city + "," + this.country
				+ "," + this.status + "," + this.timestamp;
	}

	// create an order instance from CreateOrder servlet
	public Order(String username, int product_id, int product_quantity, String user_first_name, String user_last_name,
			String address, String city, String country) {
		this.username = username;
		this.product_id = product_id;
		this.product_quantity = product_quantity;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.status = 0;
		this.timestamp = new Timestamp(new Date().getTime());
	}

	// create an order instance with uniqueID
	public Order(String id, String username, int product_id, int product_quantity, String user_first_name,
			String user_last_name, String address, String city, String country) {
		this.id = id;
		this.username = username;
		this.product_id = product_id;
		this.product_quantity = product_quantity;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.status = 0;
		this.timestamp = new Timestamp(new Date().getTime());
	}

	// create an order with all properties defined
	public Order(String id, String username, int product_id, int product_quantity, String user_first_name,
			String user_last_name, String address, String city, String country, int status) {
		this.id = id;
		this.username = username;
		this.product_id = product_id;
		this.product_quantity = product_quantity;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.status = status;
		this.timestamp = new Timestamp(new Date().getTime());
	}

	public Order(String[] fields) {
		this.id = fields[0];
		this.username = fields[1];
		this.product_id = Integer.parseInt(fields[2]);
		this.product_quantity = Integer.parseInt(fields[3]);
		this.user_first_name = fields[4];
		this.user_last_name = fields[5];
		this.address = fields[6];
		this.city = fields[7];
		this.country = fields[8];
		this.status = Integer.parseInt(fields[9]);
		this.timestamp = Timestamp.valueOf(fields[10]);
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getProductID() {
		return product_id;
	}

	public int getProductQuantity() {
		return product_quantity;
	}

	public String getUserFirstName() {
		return user_first_name;
	}

	public String getUserLastName() {
		return user_last_name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public int getStatus() {
		return status;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setProductID(int product_id) {
		this.product_id = product_id;
	}

	public void setProductQuantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public void setUserFirstName(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	public void setUserLastName(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
