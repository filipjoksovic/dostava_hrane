package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.User;
import bean.UserType;

public class UserTypeDAO {
	private static Path table = Paths.get(new ProjectConfig().getUserTypesTable());

	public static ArrayList<UserType> getAllUserTypes() {
		ArrayList<UserType> types = new ArrayList<UserType>();
		String fileName = table.toString();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {

			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				String[] typeData = line.split(",");

				// create a new User object based on a row from our file;
				UserType type = new UserType(typeData[0], Integer.parseInt(typeData[1]), Integer.parseInt(typeData[2]));
				types.add(type);

			}
		} catch (IOException e) {
			return null;
		}
		return types;
	}

	public static String getTypeTitleFromPoints(int points) {
		String title = "Standardni";
		for (UserType ut : UserTypeDAO.getAllUserTypes()) {
			if(ut.getNeededPoints() <= points) {
				title = ut.getTitle();
				return title;
			}
		}
			return title;
	}
	public static UserType getUserType(User u) {
		ArrayList<UserType> allTypes = UserTypeDAO.getAllUserTypes();
		for(UserType tinstance : allTypes) {
			if(tinstance.getTitle().equals(u.getUserType())) {
				return tinstance;
			}
		}
		return null;
	} 
}
