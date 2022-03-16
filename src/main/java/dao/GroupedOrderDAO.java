package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bean.Order;
import bean.RestaurantProduct;
import bean.User;

public class GroupedOrderDAO {
	private String order_id;
	private ArrayList<Order> order_items;
	private String username;
	private String first_name;
	private String last_name;
	private String city;
	private String address;
	private String country;
	private String status;
	private int status_id;
	private Timestamp timestamp;
	public GroupedOrderDAO(String orderID) {
		this.order_id = orderID;
		this.order_items = OrderDAO.getOrderItemsFromOrderID(orderID);
		this.username = this.order_items.get(0).getUsername();
		this.first_name = this.order_items.get(0).getUserFirstName();
		this.last_name = this.order_items.get(0).getUserLastName();
		this.address = this.order_items.get(0).getAddress();
		this.city = this.order_items.get(0).getCity();
		this.country = this.order_items.get(0).getCountry();
		this.status = OrderStatusDAO.getStatusFromID(OrderStatusDAO.getOrderStatusID(orderID));
		this.status_id = OrderStatusDAO.getOrderStatusID(orderID);
		this.timestamp = this.order_items.get(0).getTimestamp();
	}
	public String getID() {
		return this.order_id;
	}
	public ArrayList<Order> getOrderItems(){
		return this.order_items;
	}
	public String getFirstName() {
		return this.first_name;
	}
	public String getLastName() {
		return this.last_name;
	}
	public String getCity() {
		return this.city;
	}
	public String getAddress() {
		return this.address;
	}
	public String getCountry() {
		return this.country;
	}
	public String getStatus() {
		return this.status;
	}
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	public String getUsername() {
		return this.username;
	}
	public int getStatusID() {
		return this.status_id;
	}
	public int getOrderPrice() {
		int sum = 0;
		for(Order orderItem : this.getOrderItems()) {
			RestaurantProduct rp = RestaurantProductDAO.getProductByID(orderItem.getProductID());
			sum+=rp.getPrice();
		}
		return sum;
	}
	public void delete(int restaurant_id) {
		ArrayList<Order> toBeDeleted = new ArrayList<Order>();
		for(Order orderItem : this.getOrderItems()) {
			RestaurantProduct productInstance = RestaurantProductDAO.getProductByID(orderItem.getProductID());
			if(productInstance.getRestaurantID() == restaurant_id) {
				toBeDeleted.add(orderItem);
			}
		}
		for(Order orderItem : toBeDeleted) {
			OrderDAO.deleteOrder(orderItem);
		}
	}
}
