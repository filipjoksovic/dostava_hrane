package com.delivery.UserController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.delivery.Middleware;

import bean.Order;
import bean.Restaurant;
import bean.User;
import dao.GenderDAO;
import dao.OrderDAO;
import dao.OrderStatusDAO;
import dao.RestaurantProductDAO;
import dao.UserDAO;
import dao.UserPointsDAO;
import dao.RestaurantReviewDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountPage
 */
@WebServlet("/account")
public class AccountPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountPage() {
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
		if (!Middleware.isLoggedIn((User) request.getSession().getAttribute("user"))) {
			request.getSession().setAttribute("error", "Morate biti ulogovani korisnik kako biste pristupili ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			User loggedInUser = Middleware.getLoggedInUser(request.getSession());
			//update user
			request.setAttribute("userDAO", new UserDAO());
			request.setAttribute("genderSelect", GenderDAO.getAllGenders());

			if(loggedInUser.getRoleID() == 0) {
				HashMap<String, ArrayList<Order>> orders = OrderDAO.getUserOrders(loggedInUser.getUsername());
				request.setAttribute("groupedOrders",orders);
				request.setAttribute("userDAO", new UserDAO());
				request.setAttribute("orderDAO", new OrderDAO());
				request.setAttribute("orderStatusDAO", new OrderStatusDAO());
				request.setAttribute("productDAO", new RestaurantProductDAO());
				request.setAttribute("pointsCount", UserPointsDAO.getUserPoints(loggedInUser.getUsername()));
				request.setAttribute("reviewableRestaurants", RestaurantReviewDAO.getReviewableRestaurants(loggedInUser.getUsername()));
				request.setAttribute("reviewedRestaurants", RestaurantReviewDAO.getUpdateableReviews(loggedInUser.getUsername()));
			}
			if(loggedInUser.getRoleID() ==  1) {
			}
			if(loggedInUser.getRoleID() == 2) {
			}
			if(loggedInUser.getRoleID() == 3) {
			}
			request.getRequestDispatcher("WEB-INF/account.jsp").forward(request, response);
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
