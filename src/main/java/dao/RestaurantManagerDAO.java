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

import bean.Restaurant;
import bean.RestaurantManager;

public class RestaurantManagerDAO {
	
	private static Path table = Paths.get("C:\\dostava_data\\restaurant_managers.txt");
	private RestaurantManager restaurantManager;

	public RestaurantManagerDAO(RestaurantManager rm) {
		this.restaurantManager = rm;
	}
	public static int save(RestaurantManager r) {
		int saveResult = 0;
		String ManagerColumn = r.getRestaurantID() + "," + r.getUsername();
		try (FileWriter fw = new FileWriter(table.toString(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(ManagerColumn);
			return 1;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static int update(RestaurantManager rm) {
		try {
			ArrayList<String> fileContent = (ArrayList<String>) Files
					.readAllLines(table, StandardCharsets.UTF_8);
			for (int i = 0; i < fileContent.size(); i++) {
				String[] dataRow = fileContent.get(i).split(",");
				if (dataRow[1].contains(rm.getUsername())) {
					fileContent.set(i, RestaurantManagerDAO.getRowAsString(rm));
					break;
				}
			}
			try {
				Files.write(table, fileContent, StandardCharsets.UTF_8);
				return 1;
			}
			catch(IOException e) {
				System.out.println("Fajl nije pronadjen");
				return -1;
			}
		} catch (IOException e) {
			System.out.println("Fajl nije pronadjen");
			e.printStackTrace();
			return -1;
		}
	}

	public static String getRowAsString(RestaurantManager rm) {
		return rm.getRestaurantID() + "," + rm.getUsername();
	}

	public static ArrayList<RestaurantManager> getAllManagers() {
		ArrayList<RestaurantManager> managers = new ArrayList<RestaurantManager>();

		try (BufferedReader br = new BufferedReader(new FileReader(table.toString(), StandardCharsets.UTF_8))) {

			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				String[] managerData = line.split(",");

				// create a new User object based on a row from our file;
				RestaurantManager rm = new RestaurantManager(Integer.parseInt(managerData[0]), managerData[1]);
				managers.add(rm);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return managers;
	}

	public static ArrayList<RestaurantManager> getAvailableManagers() {
		ArrayList<RestaurantManager> managers = RestaurantManagerDAO.getAllManagers();
		ArrayList<RestaurantManager> availableManagers = new ArrayList<RestaurantManager>();
		for (RestaurantManager manager : managers) {
			if (manager.getRestaurantID() == -1) {
				availableManagers.add(manager);
			}
		}
		return availableManagers;
	}

	public static String test() {
		return "test";
	}

	public static String getRestaurantManager(Restaurant r) {
		System.out.println("Hello");
		ArrayList<RestaurantManager> managers = getAllManagers();
		for (RestaurantManager rm : managers) {
			if (rm.getRestaurantID() == r.getID()) {
				return rm.getUsername();
			}
		}
		return null;
	}

}
