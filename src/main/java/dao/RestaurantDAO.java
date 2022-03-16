package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.Restaurant;
import bean.RestaurantProduct;
import bean.RestaurantProductImage;
import bean.RestaurantReview;
import bean.User;

public class RestaurantDAO {
	private static Path table = Paths.get(new ProjectConfig().getRestaurantsTable());
	private static String table_string = table.toString();

	public static int save(Restaurant r) {
		String dataRow = r.getID() + "," + r.getName() + "," + r.getType() + "," + r.getActive() + "," + r.getCity()
				+ "," + r.getAddress() + "," + r.getZipCode() + "," + r.getLogo();
		System.out.println("row: " + dataRow);
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
	public static boolean update(Restaurant r) {
		try {
			List<String> fileContent = Files.readAllLines(table);
			for(int i = 0; i < fileContent.size(); i++) {
				String dataRow = fileContent.get(i);
				Restaurant resInstance = new Restaurant(dataRow.split(","));
				if(resInstance.getID() == r.getID()) {
					fileContent.set(i, r.toString());
					break;
				}
			}
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}

	public static String getRowAsString(Restaurant r) {
		return r.getID() + "," + r.getName() + "," + r.getType() + "," + r.getActive() + "," + r.getCity() + ","
				+ r.getAddress() + "," + r.getZipCode() + "," + r.getLogo();
	}

	public static ArrayList<Restaurant> getAllRestaurants() {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try (BufferedReader br = new BufferedReader(new FileReader(table_string, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] restaurantData = line.split(",");

				// create a new User object based on a row from our file;
				Restaurant r = new Restaurant(Integer.parseInt(restaurantData[0]), restaurantData[1],
						Integer.parseInt(restaurantData[2]), Boolean.parseBoolean(restaurantData[3]), restaurantData[4],
						restaurantData[5], restaurantData[6], restaurantData[7]);
				restaurants.add(r);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ArrayList<Restaurant>(0);
		}
		return restaurants;
	}

	public static int getInsertID() {
		int maxID = 1;
		ArrayList<Restaurant> restaurants = RestaurantDAO.getAllRestaurants();
		System.out.println("Broj restorana: " + restaurants.size());
		if (restaurants.size() == 0) {
			return 1;
		}
		for (Restaurant r : restaurants) {
			if (r.getID() > maxID) {
				maxID = r.getID();
			}
		}
		return maxID + 1;
	}

	public static String getRestaurantLogo(Restaurant r) {
		File file = new File("C:\\dostava_data\\restaurant_logos\\" + r.getLogo());
		System.out.println("getting the logo");
		System.out.println("C:\\dostava_data\\restaurant_logos\\" + r.getLogo());
		String imageData = "";
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(file.toPath());
			byte[] encodeBase64 = Base64.getEncoder().encode(fileContent);
			imageData = new String(encodeBase64, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		}
		return imageData;
	}

	public static Restaurant getRestaurantByID(int restaurantID) {
		Restaurant restaurant;
		ArrayList<Restaurant> restaurants = RestaurantDAO.getAllRestaurants();
		for (Restaurant r : restaurants) {
			if (r.getID() == restaurantID) {
				return r;
			}
		}
		return null;
	}

	public static int activateRestaurant(Restaurant r) {
		System.out.println("Entering activate function");
		try {
			r.setActive(true);
			ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				String[] splitData = fileContent.get(i).split(",");
				if (splitData[0].contains(String.valueOf(r.getID()))) {
					fileContent.set(i, RestaurantDAO.getRowAsString(r));
					break;
				}
			}
			try {
				Files.write(table, fileContent, StandardCharsets.UTF_8);
				return 1;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return -1;
			}
		} catch (IOException e) {
			System.out.println("File not found");
			return -1;
		}

	}

	public static int deactivateRestaurant(Restaurant r) {
		System.out.println("Entering activate function");
		try {
			r.setActive(false);
			ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				String[] splitData = fileContent.get(i).split(",");
				if (splitData[0].contains(String.valueOf(r.getID()))) {
					fileContent.set(i, RestaurantDAO.getRowAsString(r));
					break;
				}
			}
			try {
				Files.write(table, fileContent, StandardCharsets.UTF_8);
				return 1;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return -1;
			}
		} catch (IOException e) {
			System.out.println("File not found");
			return -1;
		}
	}

	public static int delete(Restaurant r) {
		try {
			ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(table, StandardCharsets.UTF_8));
			Iterator<String> itr = fileContent.iterator();
			while (itr.hasNext()) {
				String dataRow = itr.next();
				Restaurant rInstance = new Restaurant(dataRow.split(","));
				if (rInstance.getID() == r.getID()) {
					itr.remove();
				}
			}
			//delete restaurant reviews
			for(RestaurantReview review : RestaurantReviewDAO.getRestaurantReviews(r.getID())) {
				RestaurantReviewDAO.delete(review);
			}
			
			//delete orders of restaurant products
			for(String order_id : OrderDAO.getUniqueOrderIDS()) {
				GroupedOrderDAO groupedOrder = new GroupedOrderDAO(order_id);
				groupedOrder.delete(r.getID());
			}
			
			// delete related products to the restaurant
			ArrayList<RestaurantProduct> restaurantProducts = RestaurantProductDAO.getProductsFromRestaurant(r.getID());
			for (RestaurantProduct rp : restaurantProducts) {
				RestaurantProductDAO.delete(rp);
				// get image of product we are deleting
				
			}
			Files.write(table, fileContent, StandardCharsets.UTF_8);

			
		
				
			
			// finally, dismiss, but DON'T delete the managers
			ArrayList<User> restaurantManagers = UserDAO.getRestaurantManagers(r);
			for (User restaurantManager : restaurantManagers) {
				restaurantManager.setRestaurandID(-1);
				UserDAO.update(restaurantManager);
			}
			return 1;
		} catch (IOException e) {
			System.out.println("File not found");
			return -1;
		}
	}
	public static String getAverageReview(int restaurant_id) {
		ArrayList<RestaurantReview> restaurantReviews = RestaurantReviewDAO.getRestaurantReviews(restaurant_id);
		Iterator<RestaurantReview> iterator = restaurantReviews.iterator();
		while(iterator.hasNext()) {
			RestaurantReview review = iterator.next();
			if(!review.getAllowed()) {
				iterator.remove();
			}
		}
		
		double s = 0;
		for(RestaurantReview review : restaurantReviews) {
			s+=review.getGrade();
		}
		double avg;
		if(restaurantReviews.size() > 0) {
			avg = s/restaurantReviews.size();
		}
		else {
			avg = 0.000;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(avg);
//		return avg;
	}
	public static String getRestaurantManagers(int restaurant_id) {
		ArrayList<User> allUsers = UserDAO.getRestaurantManagers(RestaurantDAO.getRestaurantByID(restaurant_id));
		String userList = "";
		for(User user : allUsers) {
			userList += user.getUsername() + ",";
		}
		return userList.substring(0, userList.length() - 1);  		
	}

}
