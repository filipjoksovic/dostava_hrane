package com.delivery.RestaurantController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Restaurant;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantProductDAO;

import com.delivery.Middleware;

/**
 * Servlet implementation class DeleteRestaurant
 */
@WebServlet("/deleteRestaurant")
public class DeleteRestaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteRestaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// first,check if user is admin
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (Middleware.isLoggedIn(loggedInUser)) {
			if (!Middleware.isAdmin(loggedInUser)) {
				request.getSession().setAttribute("error",
						"Morate biti administrator kako biste mogli izvrsiti ovu operaciju");
			}
			else {
				int restaurantID = Integer.parseInt(request.getParameter("id"));
				Restaurant r = RestaurantDAO.getRestaurantByID(restaurantID);
				int deleteStatus = RestaurantDAO.delete(r);
				if(deleteStatus == 1) {
					request.getSession().setAttribute("message", "Uspesno obrisan restoran");
				}
				else {
					request.getSession().setAttribute("message", "Greska prilikom brisanja restorana");
				}
				response.sendRedirect(request.getContextPath() + "/viewRestaurants");
			}
		}
		else {
			request.getSession().setAttribute("error", "Morate biti ulogovani kako biste mogli izvrsiti ovu operaciju");
			response.sendRedirect(request.getContextPath() + "/");
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
