package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.Order;
import bean.Restaurant;
import bean.RestaurantProductImage;
import bean.RestaurantReview;
import bean.User;

public class RestaurantReviewDAO {
	private static Path table = Paths.get(new ProjectConfig().getRestaurantReviewsTable());
	
	public static List<String> getFileContent(){
		try {
			List<String> fileContent = Files.readAllLines(table);
			return fileContent;
		}
		catch(IOException e) {
			System.out.println("Fajl nije pronadjen");
			return null;
		}
	}
	public static ArrayList<RestaurantReview> getAllReviews() {
		ArrayList<RestaurantReview> allReviews = new ArrayList<RestaurantReview>();
		
		for(String dataRow : RestaurantReviewDAO.getFileContent()) {
			String[] fields = dataRow.split(",");
			RestaurantReview review = new RestaurantReview(fields);
			allReviews.add(review);
		}
		return allReviews;
	}
	
	public static boolean save(RestaurantReview rw) {
		List<String> fileContent = RestaurantReviewDAO.getFileContent();
		fileContent.add(rw.toString());
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return true;
		}
		catch(IOException e) {
			System.out.println("Fajl nije pronadjen");
			return false;
		}
	}
	public static boolean update(RestaurantReview rw) {
		List<String> fileContent = RestaurantReviewDAO.getFileContent();
		for(int i = 0;i<fileContent.size(); i++) {
			String dataRow = fileContent.get(i);
			RestaurantReview review = new RestaurantReview(dataRow.split(","));
			if(review.getRestaurantID() == rw.getRestaurantID() && review.getUsername().equals(rw.getUsername())) {
				fileContent.set(i, rw.toString());
				break;
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return true;
		}
		catch(IOException e) {
			System.out.println("Fajl nije pronadjen");
			return false;
		}
	}
	public static boolean delete(RestaurantReview rw) {
		List<String> fileContent = RestaurantReviewDAO.getFileContent();
		
		Iterator<String> itr = fileContent.iterator();
		while (itr.hasNext()) {
			String dataRow = itr.next();
			RestaurantReview review = new RestaurantReview(dataRow.split(","));
			if(review.getRestaurantID() == rw.getRestaurantID() && review.getUsername().equals(rw.getUsername())) {
				itr.remove();
				break;
			}
		}
		
//		for(int i = 0;i<fileContent.size(); i++) {
//			String dataRow = fileContent.get(i);
//			RestaurantReview review = new RestaurantReview(dataRow.split(","));
//			if(review.getRestaurantID() == rw.getRestaurantID() && review.getUsername().equals(rw.getUsername())) {
//				fileContent.remove(i);
//				break;
//			}
//		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return true;
		}
		catch(IOException e) {
			System.out.println("Fajl nije pronadjen");
			return false;
		}
	}
	public static void delete(User user) {
		List<String> fileContent = RestaurantReviewDAO.getFileContent();
		
		Iterator<String> itr = fileContent.iterator();
		while(itr.hasNext()) {
			String dataRow = itr.next();
			if(dataRow.contains(user.getUsername())) {
				itr.remove();
			}
		}
		try {
			Files.write(table,fileContent,StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<RestaurantReview> getRestaurantReviews(int restaurant_id) {
		ArrayList<RestaurantReview> allReviews = RestaurantReviewDAO.getAllReviews();
		ArrayList<RestaurantReview> filteredReviews = new ArrayList<RestaurantReview>();
		
		for(RestaurantReview review : allReviews) {
			if(review.getRestaurantID() == restaurant_id) {
				filteredReviews.add(review);
			}
		}
		return filteredReviews;
	}
	public static ArrayList<RestaurantReview> getRestaurantReviews(int restaurant_id, boolean allowed) {
		ArrayList<RestaurantReview> allReviews = RestaurantReviewDAO.getAllReviews();
		ArrayList<RestaurantReview> filteredReviews = new ArrayList<RestaurantReview>();
		
		for(RestaurantReview review : allReviews) {
			if(review.getRestaurantID() == restaurant_id && review.getAllowed() == allowed) {
				filteredReviews.add(review);
			}
		}
		return filteredReviews;
	}
	public static boolean allowReview(RestaurantReview review) {
		review.setAllowed(true);
		RestaurantReviewDAO.update(review);
		return true;
	}
	public static boolean reviewExists(String username, int restaurant_id) {
		for(RestaurantReview review : RestaurantReviewDAO.getAllReviews()) {
			if(review.getRestaurantID() == restaurant_id && review.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	public static RestaurantReview getReview(int restaurantID, String username) {
		ArrayList<RestaurantReview> allReviews = RestaurantReviewDAO.getAllReviews();
		RestaurantReview foundReview = null;
		for(RestaurantReview review : allReviews){
			if(review.getRestaurantID() == restaurantID && review.getUsername().equals(username)) {
				foundReview = review;
				break;
			}
		}
		return foundReview;
	}
	public static RestaurantReview changeReviewStatus(RestaurantReview review) {
		if(!review.getAllowed()) {
			review.setAllowed(true);
		}
		if(review.getAllowed()) {
			review.setAllowed(false);
		}
		return review;
	}
	
	public static ArrayList<Restaurant> getReviewableRestaurants(String username){
		ArrayList<Restaurant> reviewableRestaurants = new ArrayList<Restaurant>();
		ArrayList<Order> allOrders = OrderDAO.getAllOrders();
		for(Order order : allOrders) {
			Restaurant restaurant = RestaurantDAO.getRestaurantByID(RestaurantProductDAO.getProductByID(order.getProductID()).getRestaurantID());
			if(order.getUsername().equals(username) && order.getStatus() == 4 && !RestaurantReviewDAO.reviewExists(username,restaurant.getID()) && !reviewableRestaurants.contains(restaurant)) {
				reviewableRestaurants.add(restaurant);
			}
		}
		return reviewableRestaurants;
	}
	public static ArrayList<Restaurant> getUpdateableReviews(String username){
		ArrayList<RestaurantReview> allReviews = RestaurantReviewDAO.getAllReviews();
		ArrayList<Restaurant> reviewedRestaurants = new ArrayList<Restaurant>();
		for(RestaurantReview review : allReviews) {
			Restaurant restaurant = RestaurantDAO.getRestaurantByID(review.getRestaurantID());
			if(!reviewedRestaurants.contains(restaurant)) {
				reviewedRestaurants.add(restaurant);
			}
		}
		return reviewedRestaurants;
	}
    
	
}
