package dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.delivery.ProjectConfig;

import bean.Gender;

public class GenderDAO {
	public static Path table = Paths.get(new ProjectConfig().getGendersTable());
	public static ArrayList<Gender> getAllGenders(){
		ArrayList<Gender> genders = new ArrayList<Gender>();
		try (BufferedReader br = new BufferedReader(new FileReader(table.toString(), StandardCharsets.UTF_8))) {

			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				String[] genderData = line.split(",");
				// create a new User object based on a row from our file;
				Gender gender = new Gender(genderData[0], genderData[1]);
				genders.add(gender);

			}
		} catch (IOException e) {
			return null;
		}

		return genders;
	}
	public static String getGenderTitle(String gender_id) {
		String genderTitle = "";
		ArrayList<Gender> genders = GenderDAO.getAllGenders();
		for(Gender g : genders) {
			if(g.getGenderID().equals(gender_id)) {
				genderTitle = g.getGenderName();
				break;
			}
		}
		return genderTitle;
	}
}
