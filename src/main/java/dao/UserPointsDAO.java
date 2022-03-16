package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bean.Order;
import bean.User;

import com.delivery.ProjectConfig;

import bean.UserPoints;

public class UserPointsDAO {
	private static Path table = Paths.get(new ProjectConfig().getUserPointsTable());

	public static List<String> getTableContent() {
		try {
			List<String> fileContent = Files.readAllLines(table);
			return fileContent;
		} catch (IOException e) {
			return null;
		}
	}

	public static ArrayList<UserPoints> getAllPoints() {

		ArrayList<UserPoints> userPoints = new ArrayList<UserPoints>();
		List<String> fileContent = UserPointsDAO.getTableContent();
		for (String dataRow : fileContent) {
			UserPoints up = new UserPoints(dataRow.split(","));
			userPoints.add(up);
		}
		return userPoints;
	}

	public static ArrayList<UserPoints> listUserPoints(String username) {
		ArrayList<UserPoints> userPoints = UserPointsDAO.getAllPoints();
		ArrayList<UserPoints> specificUserPoints = new ArrayList<UserPoints>();
		for (UserPoints up : userPoints) {
			if (up.getUsername().equals(username)) {
				specificUserPoints.add(up);
			}
		}
		return specificUserPoints;
	}

	public static int getUserPoints(String username) {
		ArrayList<UserPoints> userPoints = UserPointsDAO.listUserPoints(username);
		int sum = 0;
		for (UserPoints up : userPoints) {
			sum += up.getNumberOfPoints();
		}
		return sum;
	}

	public static String toString(UserPoints op) {
		return op.getUsername() + "," + op.getOrderID() + "," + op.getNumberOfPoints();
	}

	public static boolean save(UserPoints op) {
		List<String> tableContent = UserPointsDAO.getTableContent();
		tableContent.add(UserPointsDAO.toString(op));
		try {
			Files.write(table, tableContent, StandardCharsets.UTF_8);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static void delete(User user) {
		List<String> fileContent = UserPointsDAO.getTableContent();
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

	public static User update(Order order) { 
		User toUpdate = new User();
		List<String> fileContent = UserPointsDAO.getTableContent();
		GroupedOrderDAO groupedOrder = new GroupedOrderDAO(order.getId());
		for(int i = 0; i < fileContent.size(); i++) {
			String dataRow = fileContent.get(i);
			if(dataRow.contains(groupedOrder.getID())) {
				UserPoints pointsInstance = new UserPoints(dataRow.split(","));
				int orderPrice = groupedOrder.getOrderPrice();
				int lostPoints = Math.round((float)orderPrice / 1000 * 133)*4;
				pointsInstance.setNumberOfPoints(pointsInstance.getNumberOfPoints() - lostPoints);
				fileContent.set(i, pointsInstance.toString());
				User userInstance = UserDAO.getUserByUsername(order.getUsername());
				userInstance.setUserType(UserTypeDAO.getTypeTitleFromPoints(pointsInstance.getNumberOfPoints()));
				toUpdate = userInstance;
				break;
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return toUpdate;
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
