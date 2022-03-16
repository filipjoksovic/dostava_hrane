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
import java.util.List;

import com.delivery.ProjectConfig;

import bean.Restaurant;
import bean.RestaurantType;

public class RestaurantTypeDAO {
	public static Path table = Paths.get(new ProjectConfig().getRestaurantTypesTable());
	public static int save(RestaurantType rt) {
		String dataRow = rt.getID() + "," + rt.getTitle();
		try (FileWriter fw = new FileWriter(table.toString(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(dataRow);
			return 1;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static ArrayList<RestaurantType> getAllTypes() {
		ArrayList<RestaurantType> types = new ArrayList<RestaurantType>();
		List<String> fileContent;
		try {
			fileContent = Files.readAllLines(table);
			for(String dataRow : fileContent) {
				types.add(new RestaurantType(dataRow.split(",")));
			}
			return types;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static String getTypeFromID(Restaurant r) {
		ArrayList<RestaurantType> allTypes = RestaurantTypeDAO.getAllTypes();
		for(RestaurantType rt : allTypes) {
			if(r.getType() == rt.getID()) {
				return rt.getTitle();
			}
		}
		return null;
	}
}
