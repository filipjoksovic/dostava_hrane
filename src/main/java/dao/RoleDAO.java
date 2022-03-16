package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.delivery.ProjectConfig;

import bean.Role;


public class RoleDAO {
	public static Path table = Paths.get(new ProjectConfig().getUserRolesTable());
	public static ArrayList<Role> getAllRoles(){
		ArrayList<Role> roles= new ArrayList<Role>();
		String fileName = table.toString();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {

			String line;
			System.out.println("printing user roles");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] roleData = line.split(",");
				
				// create a new User object based on a row from our file;
				Role role = new Role(Integer.parseInt(roleData[0]), roleData[1]);
				roles.add(role);

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return roles;
	}
	public static String getRoleTitleFromID(int id) {
		String role = "";
		ArrayList<Role> roles = RoleDAO.getAllRoles();
		System.out.println("Getting user roles");
		for(Role r : roles) {
			System.out.println("ID uloge : " + r.getRoleID());
			if(r.getRoleID() == id) {
				role = r.getRoleTitle();
			}
		}
		return role;
	}
}
