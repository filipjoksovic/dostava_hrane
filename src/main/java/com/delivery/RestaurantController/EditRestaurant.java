package com.delivery.RestaurantController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Restaurant;
import bean.User;
import dao.RestaurantDAO;

/**
 * Servlet implementation class EditRestaurant
 */
@WebServlet("/editRestaurant")
public class EditRestaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRestaurant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isSeller(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti ulogovani kao prodavac kako biste mogli da pristupite ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			int restaurantID = Integer.parseInt(request.getParameter("restaurant_id"));
			if(loggedInUser.getRestaurantID() != restaurantID) {
				request.getSession().setAttribute("error", "Nemate pravo da izmenite ovaj restoran");
				response.sendRedirect(request.getContextPath() + "/");
			}
			else {
				Restaurant restaurantInstance = RestaurantDAO.getRestaurantByID(restaurantID);
				String restaurant_name = request.getParameter("restaurant_name");
				String address = request.getParameter("address");
				String city = request.getParameter("city");
				String zip_code = request.getParameter("zip_code");
				int restaurant_type = Integer.parseInt(request.getParameter("restaurant_type"));
				restaurantInstance.setAddress(address);
				restaurantInstance.setCity(city);
				restaurantInstance.setName(restaurant_name);
				restaurantInstance.setType(restaurant_type);
				restaurantInstance.setZipCode(zip_code);
				if(RestaurantDAO.update(restaurantInstance)) {
					request.getSession().setAttribute("message", "Uspesno izmenjen restoran");
					response.sendRedirect(request.getContextPath() + "/seller");
				}
				else {
					request.setAttribute("error", "Greska prilikom izmene restorana");
					response.sendRedirect(request.getContextPath() + "/seller");
				}
			}
		}
	}

}
