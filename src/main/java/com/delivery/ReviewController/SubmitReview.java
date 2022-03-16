package com.delivery.ReviewController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.RestaurantReview;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantReviewDAO;

/**
 * Servlet implementation class SubmitReview
 */
@WebServlet("/rateRestaurant")
public class SubmitReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitReview() {
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
		if (!Middleware.isUser(loggedInUser)) {
			request.getSession().setAttribute("error",
					"Morate biti prijavljeni korisnik kako biste mogli da ostavite ocenu na restoran");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			int restaurant_id = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("review_exists",
					RestaurantReviewDAO.reviewExists(loggedInUser.getUsername(), restaurant_id));
			request.setAttribute("restaurant", RestaurantDAO.getRestaurantByID(restaurant_id));
			request.setAttribute("restaurantDAO", new RestaurantDAO());
			boolean reviewExists = RestaurantReviewDAO.reviewExists(loggedInUser.getUsername(), restaurant_id);
			request.setAttribute("reviewExists",RestaurantReviewDAO.reviewExists(loggedInUser.getUsername(), restaurant_id));
			if (reviewExists) {
				request.setAttribute("review", RestaurantReviewDAO.getReview(restaurant_id, loggedInUser.getUsername()));
			}
			request.getRequestDispatcher("WEB-INF/submit_review.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isUser(loggedInUser)) {
			request.getSession().setAttribute("error",
					"Morate biti prijavljeni korisnik kako biste mogli da ostavite ocenu na restoran");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			String username = loggedInUser.getUsername();
			int restaurant_id = Integer.parseInt(request.getParameter("restaurant_id"));
			int rate = Integer.parseInt(request.getParameter("rate"));
			String review = request.getParameter("review");
			RestaurantReview reviewInstance = new RestaurantReview(restaurant_id, username, rate, review);
			if(!RestaurantReviewDAO.reviewExists(username, restaurant_id)) {
				RestaurantReviewDAO.save(reviewInstance);				
			}
			else {
				RestaurantReviewDAO.update(reviewInstance);
			}
			request.getSession().setAttribute("message", "Uspesno ocenjen restoran");
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

}
