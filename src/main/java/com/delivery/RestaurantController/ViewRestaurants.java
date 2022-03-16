package com.delivery.RestaurantController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Restaurant;
import bean.RestaurantType;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantManagerDAO;
import dao.RestaurantTypeDAO;

/**
 * Servlet implementation class ViewRestaurants
 */
@WebServlet("/viewRestaurants")
public class ViewRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewRestaurants() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error",
					"Morate biti ulogovani kao administrator kako biste mogli pristupiti listi restorana");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			ArrayList<Restaurant> restaurants = RestaurantDAO.getAllRestaurants();
			request.setAttribute("restaurants", restaurants);
			RestaurantDAO rd = new RestaurantDAO();
			request.setAttribute("restaurants", restaurants);
			request.setAttribute("restaurantDAO", rd);
			request.getRequestDispatcher("WEB-INF/restaurants.jsp").forward(request, response);
		}
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
