package com.delivery.RestaurantController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import bean.Restaurant;
import bean.RestaurantManager;
import bean.RestaurantType;
import bean.User;

import dao.UserDAO;
import dao.RestaurantTypeDAO;
import dao.RestaurantDAO;
import dao.RestaurantManagerDAO;
/**
 * Servlet implementation class CreateRestaurant
 */

@WebServlet("/createRestaurant")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class CreateRestaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRestaurant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<RestaurantType> types = RestaurantTypeDAO.getAllTypes();
		ArrayList<User> availableManagers = UserDAO.getAvailableManagers();
		request.setAttribute("availableManagers", availableManagers);
		request.setAttribute("restaurantTypes", types);
		request.getRequestDispatcher("WEB-INF/create_restaurant.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int type_id = Integer.parseInt(request.getParameter("type"));
		System.out.println("name: " + name);
		boolean active = false;
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		String zip_code = request.getParameter("zip");
	    Part filePart = request.getPart("logo"); // Retrieves <input type="file" name="file">
	    System.out.println(getSubmittedFileName(filePart));
	    String logoName = getSubmittedFileName(filePart);
	    InputStream fileContent = filePart.getInputStream();
	    try {
	    	File image = new File("C:\\dostava_data\\restaurant_logos\\"+logoName);
	    	Files.copy(fileContent, image.toPath());
	    }
	    catch(java.nio.file.FileAlreadyExistsException e) {
	    	System.out.println("Nema potrebe za dodavanjem slike, slika vec postoji");
	    }
//	    for (Part part : request.getParts()) {
//	      part.write("C:\\dostava_data\\restaurant_logos\\" + logoName);
//	    }
		int id = RestaurantDAO.getInsertID();
		Restaurant r = new Restaurant(id,name,type_id,active,city,address,zip_code,logoName);
		int saveResult = RestaurantDAO.save(r);
		if(request.getParameter("createManager") == null) {
			String managerUsername = request.getParameter("manager");
			User manager = UserDAO.getUserByUsername(managerUsername);
			manager.setRestaurandID(id);
			int updateResult = UserDAO.update(manager);
			
			if(updateResult == 1) {
				request.getSession().setAttribute("message", "Uspesno kreiran restoran sa pratecim menadzerom");
				response.sendRedirect(request.getContextPath() + "/admin");
			}
			else {
				request.getSession().setAttribute("error", "Greska prilikom kreiranja prateceg menadzera za restoran");
				response.sendRedirect(request.getContextPath() + "/createRestaurant");
			}
			
		}
		else {
			String username = request.getParameter("uname");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String dob = request.getParameter("dob");
			int role_id = Integer.parseInt(request.getParameter("roleSelect"));
			String rank = request.getParameter("rankSelect");
			User u = new User(username,fname,lname,password,gender,dob,role_id,rank,id);
			if(saveResult == 1) {
				saveResult = UserDAO.storeUser(u);
				if(saveResult == 1) {
					request.getSession().setAttribute("message", "Uspesno kreiran menadzer i restoran");
					response.sendRedirect(request.getContextPath() + "/admin");
				}
				else {
					request.getSession().setAttribute("error", "Greska prilikom kreiranja menadzera i restorana");
					response.sendRedirect(request.getContextPath() + "/createRestaurant");
				}
			}
		}
	}

}
