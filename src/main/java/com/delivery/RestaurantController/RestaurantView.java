package com.delivery.RestaurantController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.RestaurantProduct;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantProductDAO;
import dao.RestaurantReviewDAO;

/**
 * Servlet implementation class RestaurantView
 */
@WebServlet("/restaurant")
public class RestaurantView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			Middleware.createGuest(request.getSession());
		}
		int restaurantID = Integer.parseInt(request.getParameter("id"));
		ArrayList<RestaurantProduct> restaurantProducts = RestaurantProductDAO.getProductsFromRestaurant(restaurantID);
		request.setAttribute("products", restaurantProducts);
		request.setAttribute("avgReview",RestaurantDAO.getAverageReview(restaurantID));
		request.setAttribute("restaurantDAO",new RestaurantDAO());
		request.setAttribute("restaurantReviews", RestaurantReviewDAO.getRestaurantReviews(restaurantID,true));
		request.setAttribute("restaurant", RestaurantDAO.getRestaurantByID(restaurantID));

		request.getRequestDispatcher("WEB-INF/restaurant_view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
