package com.delivery.RestaurantController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Restaurant;
import bean.RestaurantProduct;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantProductDAO;
import dao.RestaurantTypeDAO;

/**
 * Servlet implementation class RestaurantSearch
 */
@WebServlet("/restaurantSearch")
public class RestaurantSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RestaurantSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isLoggedIn(loggedInUser)) {
			Middleware.createGuest(request.getSession());
		}
		String restaurant_query = request.getParameter("restaurantSearch");
		String location_query = request.getParameter("locationSearch");
		int typeSelect = (request.getParameter("typeSelect") != null) ? Integer.parseInt(request.getParameter("typeSelect")) : -1;
		int minRate = (!request.getParameter("minRate").isEmpty() && !request.getParameter("minRate").isBlank())
				? Integer.parseInt(request.getParameter("minRate"))
				: 0;
		ArrayList<Restaurant> allRestaurants = RestaurantDAO.getAllRestaurants();
		if (minRate > 0) {
			Iterator<Restaurant> iterator = allRestaurants.iterator();
			while (iterator.hasNext()) {
				Restaurant restaurantInstance = iterator.next();
				Float avgRate = Float.parseFloat(RestaurantDAO.getAverageReview(restaurantInstance.getID()));
				if (avgRate < minRate) {
					iterator.remove();
				}
			}
		}

		if (!restaurant_query.isBlank() && !restaurant_query.isEmpty()) {
			Iterator<Restaurant> iterator = allRestaurants.iterator();
			while (iterator.hasNext()) {
				Restaurant restaurantInstance = iterator.next();
				String name = restaurantInstance.getName().toLowerCase();
				if (!name.contains(restaurant_query)) {
					iterator.remove();
				}
			}
		}
		if (!location_query.isBlank() && !location_query.isEmpty()) {
			Iterator<Restaurant> iterator = allRestaurants.iterator();
			while (iterator.hasNext()) {
				Restaurant restaurantInstance = iterator.next();
				String city = restaurantInstance.getCity().toLowerCase();
				String address = restaurantInstance.getAddress().toLowerCase();
				String zcode = restaurantInstance.getZipCode().toLowerCase();
				if (!city.contains(location_query) && !address.contains(location_query)
						&& !zcode.contains(location_query)) {
					iterator.remove();
				}
			}
		}
		if (typeSelect != -1) {
			Iterator<Restaurant> iterator = allRestaurants.iterator();
			while (iterator.hasNext()) {
				Restaurant restaurantInstance = iterator.next();
				if(restaurantInstance.getType() != typeSelect) {
					iterator.remove();
				}
				
			}
		}
		request.setAttribute("restaurantSearch", restaurant_query);
		request.setAttribute("locationSearch", location_query);
		request.setAttribute("minRate", minRate);
		request.setAttribute("typeSelect", typeSelect);
		request.setAttribute("restaurants", allRestaurants);
		request.setAttribute("restaurantDAO", new RestaurantDAO());
		request.setAttribute("restaurantTypes", RestaurantTypeDAO.getAllTypes());
		request.setAttribute("restaurantTypeDAO", new RestaurantTypeDAO());
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
