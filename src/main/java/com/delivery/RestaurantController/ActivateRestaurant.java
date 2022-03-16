package com.delivery.RestaurantController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Restaurant;
import dao.RestaurantDAO;

/**
 * Servlet implementation class ActivateRestaurant
 */
@WebServlet("/activateRestaurant")
public class ActivateRestaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivateRestaurant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Activating the restaurant");
		int restaurantID = Integer.parseInt(request.getParameter("id"));
		System.out.println("restaurant ID " + restaurantID);
		Restaurant restaurant = RestaurantDAO.getRestaurantByID(restaurantID);
		int result = RestaurantDAO.activateRestaurant(restaurant);
		if(result == 1){
			request.getSession().setAttribute("message","Uspesno aktiviran restoran");
			response.sendRedirect(request.getContextPath() + "/viewRestaurants");
		}
		else {
			request.getSession().setAttribute("error","Greska prilikom aktiviranja restorana");
			response.sendRedirect(request.getContextPath() + "/viewRestaurants");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
