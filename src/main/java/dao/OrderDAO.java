package dao;

import java.util.List;

import bean.User;

import com.delivery.ProjectConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Order;
import bean.RestaurantProduct;

public class OrderDAO {
	private static Path table = Paths.get(new ProjectConfig().getUserOrdersTable());
	
	public static String toString(Order o) {
		return o.getId() + "," + o.getUsername() + "," + o.getProductID() + "," + o.getProductQuantity() + ","
				+ o.getUserFirstName() + "," + o.getUserLastName() + "," + o.getAddress() + "," + o.getCity() + ","
				+ o.getCountry() + "," + o.getStatus() + "," + o.getTimestamp().toString();
	}

	public static ArrayList<Order> getAllOrders() {
		try {
			List<String> fileContent = Files.readAllLines(table);
			ArrayList<Order> allOrders = new ArrayList<Order>();
			for (String dataRow : fileContent) {
				String[] dataArray = dataRow.split(",");
				Order order = new Order(dataArray);
				allOrders.add(order);
			}
			return allOrders;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Fajl nije pronadjen");
			e.printStackTrace();
		}
		return null;

	}

	public static int updateGroupedOrder(String order_id) {
		try {
			List<String> fileContent = Files.readAllLines(table);
			ArrayList<Order> allOrders = new ArrayList<Order>();
			for (int i = 0; i < fileContent.size(); i++) {
				String[] dataArray = fileContent.get(i).split(",");
				Order order = new Order(dataArray);
				if (order.getStatus() < 5 && order.getId().equals(order_id)) {
					order.setStatus(order.getStatus() + 1);
					fileContent.set(i, OrderDAO.toString(order));
				}
				Files.write(table, fileContent, StandardCharsets.UTF_8);
			}
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Fajl nije pronadjen");
			e.printStackTrace();
		}
		return -1;
	}

	public static void update(Order o) {
		List<String> fileContent = OrderDAO.getFileContent();
		for (int i = 0; i < fileContent.size(); i++) {
			String dataRow = fileContent.get(i);
			if (dataRow.contains(o.getId())) {
				Order orderInstance = new Order(dataRow.split(","));
				orderInstance.setAddress(o.getAddress());
				orderInstance.setCity(o.getCity());
				orderInstance.setCountry(o.getCountry());
				orderInstance.setStatus(o.getStatus());
				fileContent.set(i, orderInstance.toString());
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getFileContent() {
		try {
			List<String> fileContent = Files.readAllLines(table);
			return fileContent;
		} catch (IOException e) {
			return null;
		}
	}

	public static void delete(User user) {
		List<String> fileContent = OrderDAO.getFileContent();
		Iterator<String> iterator = fileContent.iterator();
		while (iterator.hasNext()) {
			String dataRow = iterator.next();
			if (dataRow.contains(user.getUsername())) {
				iterator.remove();
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void deleteOrder(Order orderItem) {
		List<String>fileContent = OrderDAO.getFileContent();
		Iterator<String> iterator = fileContent.iterator();
		while(iterator.hasNext()) {
			String dataRow = iterator.next();
			if(dataRow.equals(orderItem.toString())) {
				iterator.remove();
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	public static User cancel(Order order) {
		List<String> fileContent = OrderDAO.getFileContent();
		Iterator<String> iterator = fileContent.iterator();
		while (iterator.hasNext()) {
			String dataRow = iterator.next();
			if (dataRow.contains(order.getId())) {
				Order orderInstance = new Order(dataRow.split(","));
				orderInstance.setStatus(5);
				OrderDAO.update(orderInstance);
			}
		}
		return UserPointsDAO.update(order);
	}

	public static boolean save(Order o) {
		try {
			List<String> fileContent = Files.readAllLines(table);
			fileContent.add(o.toString());
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static HashMap<String, ArrayList<Order>> getUserOrders(String username) {
		ArrayList<Order> allOrders = OrderDAO.getAllOrders();
		ArrayList<Order> userOrders = new ArrayList<Order>();

		for (Order o : allOrders) {
			if (o.getUsername().equals(username)) {
				userOrders.add(o);
			}
		}
		return OrderDAO.groupOrders(userOrders);
	}

	public static HashMap<String, ArrayList<Order>> getRestaurantOrders(int restaurantID) {
		ArrayList<Order> allOrders = OrderDAO.getAllOrders();
		ArrayList<Order> restaurantOrders = new ArrayList<Order>();
		for (Order o : allOrders) {
			RestaurantProduct rp = RestaurantProductDAO.getProductByID(o.getProductID());
			if (rp.getRestaurantID() == restaurantID) {
				restaurantOrders.add(o);
			}
		}
		return OrderDAO.groupOrders(restaurantOrders);
	}

	public static HashMap<String, ArrayList<Order>> groupOrders(ArrayList<Order> orders) {
		HashMap<String, ArrayList<Order>> groupedOrders = new HashMap<String, ArrayList<Order>>();
		for (Order o : orders) {
			if (groupedOrders.containsKey(o.getId())) {
				groupedOrders.get(o.getId()).add(o);
			} else {
				ArrayList<Order> keyOrders = new ArrayList<Order>();
				keyOrders.add(o);
				groupedOrders.put(o.getId(), keyOrders);
			}
		}

		return groupedOrders;
	}

	public static boolean validate(String fname, String lname, String address, String city, String country) {

		if (fname.isEmpty() || fname.isBlank()) {
			return false;
		}
		if (lname.isEmpty() || lname.isBlank()) {
			return false;
		}
		if (address.isEmpty() || address.isBlank()) {
			return false;
		}
		if (city.isEmpty() || city.isBlank()) {
			return false;
		}
		if (country.isEmpty() || country.isBlank()) {
			return false;
		}
		return true;
	}

	public static boolean canCancel(ArrayList<Order> orderItems) {
		for (Order o : orderItems) {
			if (o.getStatus() != 0) {
				return false;
			}
		}
		return true;
	}

	public static ArrayList<Order> getOrderItemsFromOrderID(String order_id) {
		ArrayList<Order> allOrders = OrderDAO.getAllOrders();
		ArrayList<Order> filterOrders = new ArrayList<Order>();
		for (Order order : allOrders) {
			if (order.getId().equals(order_id)) {
				filterOrders.add(order);
			}
		}
		return filterOrders;
	}

	public static ArrayList<String> getUniqueOrderIDS() {
		ArrayList<String> uniqueIDS = new ArrayList<String>();
		for (Order orderItem : OrderDAO.getAllOrders()) {
			if (!uniqueIDS.contains(orderItem.getId())) {
				uniqueIDS.add(orderItem.getId());
			}
		}
		return uniqueIDS;
	}

	public static ArrayList<String> getUniqueOrderIDS(int restaurant_id) {
		ArrayList<String> uniqueIDS = new ArrayList<String>();
		for (Order orderItem : OrderDAO.getAllOrders()) {
			RestaurantProduct rp = RestaurantProductDAO.getProductByID(orderItem.getProductID());
			if (!uniqueIDS.contains(orderItem.getId()) && rp.getRestaurantID() == restaurant_id) {
				uniqueIDS.add(orderItem.getId());
			}
		}
		return uniqueIDS;
	}
	public static ArrayList<Order> getItemOrders(RestaurantProduct rp){
		ArrayList<Order> itemOrders = new ArrayList<Order>();
		for(Order order : OrderDAO.getAllOrders()) {
			if(order.getProductID() == rp.getID()) {
				itemOrders.add(order);
			}
		}
		return itemOrders;
	}

}
