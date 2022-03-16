package dao;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import bean.Restaurant;
import bean.RestaurantProductImage;
import bean.RestaurantReview;
import bean.User;
import bean.UserType;
import com.delivery.ProjectConfig;

public class UserDAO {
	private static Path table = Paths.get(new ProjectConfig().getUsersTable());
	private static String table_string = table.toString();

	public static int storeUser(User user) {
		String UserColumn = user.getUsername() + "," + user.getFirstName() + "," + user.getLastName() + ","
				+ user.getPassword() + "," + user.getGender() + "," + user.getDateOfBirth() + "," + user.getRoleID()
				+ "," + user.getUserType() + "," + user.getRestaurantID() + "," + user.getBlocked();
		try (FileWriter fw = new FileWriter(table_string, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(UserColumn);
			return 1;
		} catch (IOException e) {
			return -1;
		}
	}

	public static int createManager(User user) {
		String UserColumn = user.getUsername() + "," + user.getFirstName() + "," + user.getLastName() + ","
				+ user.getPassword() + "," + user.getGender() + "," + user.getDateOfBirth() + "," + user.getRoleID()
				+ "," + user.getUserType() + "," + user.getRestaurantID();
		try (FileWriter fw = new FileWriter(table_string, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(UserColumn);

			return 1;
		} catch (IOException e) {
			return -1;
		}
	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		String fileName = table_string;

		try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {

			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				String[] userData = line.split(",");

				// create a new User object based on a row from our file;
				User u = new User(userData);
				users.add(u);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return users;
	}

	public static User tryLogin(String username, String password) {
		User matchedUser = null;
		String fileName = table_string;
		ArrayList<User> users = UserDAO.getAllUsers();
		for (User u : users) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				matchedUser = u;
				break;
			}
		}
		return matchedUser;
	}

	public static void blockUser(User u) {
		u.setBlocked(true);
		UserDAO.update(u);
	}

	public static void unblockUser(User u) {
		u.setBlocked(false);
		UserDAO.update(u);
	}

	public static boolean doesExist(User u) {
		ArrayList<User> users = UserDAO.getAllUsers();
		for (User userFromArray : users) {
			if (u.getUsername().equals(userFromArray.getUsername())) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateUser(User u) {
		boolean validUser = true;
		if (u.getFirstName().isEmpty() || u.getFirstName().isBlank()) {
			validUser = false;
		}
		if (u.getLastName().isEmpty() || u.getLastName().isBlank()) {
			validUser = false;
		}
		if (u.getDateOfBirth().isEmpty() || u.getDateOfBirth().isBlank()) {
			validUser = false;
		}
		if (u.getGender().isEmpty() || u.getGender().isBlank()) {
			validUser = false;
		}
		if (u.getPassword().isEmpty() || u.getPassword().isBlank()) {
			validUser = false;
		}

		if (u.getUserType().isEmpty() || u.getUserType().isBlank()) {
			validUser = false;
		}
		if (u.getUsername().isEmpty() || u.getUsername().isBlank()) {
			validUser = false;
		}
		return validUser;
	}

	public static ArrayList<User> getAvailableManagers() {
		ArrayList<User> availableManagers = new ArrayList<User>();
		for (User u : UserDAO.getAllUsers()) {
			if (u.getRoleID() == 2 && u.getRestaurantID() == -1) {
				availableManagers.add(u);
			}
		}
		return availableManagers;
	}

	public static User getUserByUsername(String username) {
		for (User u : UserDAO.getAllUsers()) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public static String getAsString(User user) {
		return user.getUsername() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getPassword()
				+ "," + user.getGender() + "," + user.getDateOfBirth() + "," + user.getRoleID() + ","
				+ user.getUserType() + "," + user.getRestaurantID() + "," + user.getBlocked();
	}

	public static int update(User u) {
		int updateStatus = -1;

		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			for (int i = 0; i < fileContent.size(); i++) {
				String[] dataRow = fileContent.get(i).split(",");
				if (dataRow[0].equals(u.getUsername())) {
					fileContent.set(i, UserDAO.getAsString(u));
					break;
				}
			}
			try {
				Files.write(table, fileContent, StandardCharsets.UTF_8);
				return 1;
			} catch (IOException e) {
				System.out.println("Fajl nije prondadjen");
				return 0;
			}
		} catch (IOException e) {
			System.out.println("Fajl nije pronadjen");
			e.printStackTrace();
			return 0;
		}
	}

	public static List<String> getFileContent() {
		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			return fileContent;
		} catch (IOException e) {
			return null;
		}
	}

	public static void delete(User user) {
		List<String> fileContent = UserDAO.getFileContent();
		Iterator<String> itr = fileContent.iterator();
		while (itr.hasNext()) {
			String dataRow = itr.next();
			User userInstance = new User(dataRow.split(","));
			if (userInstance.getUsername().equals(user.getUsername())) {
				itr.remove();
			}
		}
		UserPointsDAO.delete(user);
		OrderDAO.delete(user);
		RestaurantReviewDAO.delete(user);
		if(user.getRoleID() == 3) {
			DeliveryRequestDAO.delete(user.getUsername());
		}

		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<User> getRestaurantManagers(Restaurant r) {
		ArrayList<User> allUsers = UserDAO.getAllUsers();
		ArrayList<User> managers = new ArrayList<User>();
		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).getRestaurantID() == r.getID()) {
				managers.add(allUsers.get(i));
			}
		}
		return managers;
	}

	public static int rankUpUser(User u) {
		boolean rankedUp = false;
		ArrayList<UserType> allTypes = UserTypeDAO.getAllUserTypes();
		int userPoints = UserPointsDAO.getUserPoints(u.getUsername());
		for (UserType ut : allTypes) {
			if (userPoints > ut.getNeededPoints()) {
				u.setUserType(ut.getTitle());
				rankedUp = true;
				break;
			}
		}
		if (rankedUp) {
			UserDAO.update(u);
		}
		return 1;
	}



}
