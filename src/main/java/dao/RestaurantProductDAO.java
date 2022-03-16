package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.delivery.ProjectConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import bean.CartItem;
import bean.Order;
import bean.RestaurantProduct;
import bean.RestaurantProductImage;

public class RestaurantProductDAO {
	public static Path table = Paths.get(new ProjectConfig().getRestaurantProductsTable());
	public static String table_string = table.toString();
	
	public static String rowAsString(RestaurantProduct rp) {
		return rp.getID() + "," + rp.getName() + "," + rp.getPrice() + "," + rp.getTypeID() + "," + rp.getRestaurantID()
				+ "," + rp.getQuantity() + "," + rp.getDescription();
	}

	public static boolean exists(RestaurantProduct product) {
		try {
			List<String> fileContent = Files.readAllLines(table);
			for (String dataRow : fileContent) {
				RestaurantProduct rp = new RestaurantProduct(dataRow);
				if (rp.getName().equals(product.getName()) && rp.getRestaurantID() == product.getRestaurantID()) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static int save(RestaurantProduct rp) {
		if (!RestaurantProductDAO.exists(rp)) {
			String dataRow = RestaurantProductDAO.rowAsString(rp);
			try (FileWriter fw = new FileWriter(table_string, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {
				out.println(dataRow);
				return 1;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return -1;
			}
		}
		return -1;
	}

	public static int update(RestaurantProduct rp) {

		try {
			List<String> fileContent = (Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				RestaurantProduct product = new RestaurantProduct(fileContent.get(i));
				if (product.getID() == rp.getID()) {
					product.setName(rp.getName());
					product.setDescription(rp.getDescription());
					product.setPrice(rp.getPrice());
					product.setQuantity(rp.getQuantity());
					product.setTypeID(rp.getTypeID());
					fileContent.set(i, RestaurantProductDAO.rowAsString(product));
					break;
				}
			}
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static ArrayList<RestaurantProduct> getAllProducts() {
		ArrayList<RestaurantProduct> allProducts = new ArrayList<RestaurantProduct>();
		try {
			List<String> fileContent = (Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				RestaurantProduct product = new RestaurantProduct(fileContent.get(i));
				allProducts.add(product);
			}
			return allProducts;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static int getInsertID() {
		ArrayList<RestaurantProduct> allProducts = RestaurantProductDAO.getAllProducts();
		int maxID = 0;
		for (RestaurantProduct rp : allProducts) {
			if (rp.getID() > maxID) {
				maxID = rp.getID();
			}
		}
		return maxID + 1;
	}

	public static ArrayList<RestaurantProduct> getProductsFromRestaurant(int restaurant_id) {
		ArrayList<RestaurantProduct> products = new ArrayList<RestaurantProduct>();
		for (RestaurantProduct rp : RestaurantProductDAO.getAllProducts()) {
			if (rp.getRestaurantID() == restaurant_id) {
				products.add(rp);
			}
		}
		return products;
	}

	public static String getProductImage(RestaurantProduct rp) {
		RestaurantProductImage image = RestaurantProductImageDAO.getProductImage(rp);
		String imageString = RestaurantProductImageDAO.toImage(image);
		System.out.println(imageString);
		return RestaurantProductImageDAO.toImage(image);
	}

	public static RestaurantProduct getProductByID(int productID) {
		ArrayList<RestaurantProduct> allProducts = RestaurantProductDAO.getAllProducts();
		for (RestaurantProduct rp : allProducts) {
			if (rp.getID() == productID) {
				return rp;
			}
		}
		return null;
	}

	public static int delete(RestaurantProduct rp) {
		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			Iterator<String> itr = fileContent.iterator();
			while (itr.hasNext()) {
				String dataRow = itr.next();
				RestaurantProduct product = new RestaurantProduct(dataRow);
				if (product.getID() == rp.getID()) {
					itr.remove();
				}
			}
			RestaurantProductImageDAO.delete(RestaurantProductImageDAO.getProductImage(rp));
			ArrayList<Order> productOrders = OrderDAO.getItemOrders(rp);
			for (Order order : productOrders) {
				OrderDAO.deleteOrder(order);
			}

			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return 1;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static ArrayList<RestaurantProduct> getProductsFromCart(ArrayList<CartItem> cItems) {
		ArrayList<RestaurantProduct> products = new ArrayList<RestaurantProduct>();
		for (CartItem citem : cItems) {
			products.add(RestaurantProductDAO.getProductByID(citem.getProductID()));
		}
		return products;
	}
}
