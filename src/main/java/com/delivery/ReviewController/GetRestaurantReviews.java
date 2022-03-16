package com.delivery.ReviewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Restaurant;
import bean.RestaurantReview;
import bean.User;
import dao.RestaurantReviewDAO;
import dao.RestaurantDAO;
/**
 * Servlet implementation class GetRestaurantReviews
 */
@WebServlet("/restaurant_reviews")
public class GetRestaurantReviews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRestaurantReviews() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isSeller(loggedInUser) && !Middleware.isAdmin(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti registrovani kao menadzer ili administrator kako biste mogli da pregledate ocene restorana");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			ArrayList<RestaurantReview> restaurantReviews = new ArrayList<RestaurantReview>();
			if(loggedInUser.getRoleID() == 1) {
				restaurantReviews = RestaurantReviewDAO.getAllReviews();
				
			}
			else if(loggedInUser.getRoleID() == 2){
				restaurantReviews = RestaurantReviewDAO.getRestaurantReviews(loggedInUser.getRestaurantID());
			}
			restaurantReviews.sort(new Comparator<RestaurantReview>() {
				@Override
				public int compare (RestaurantReview r1,RestaurantReview r2) {
					return Boolean.compare(r1.getAllowed(),r2.getAllowed());
				}
			});
			request.setAttribute("restaurantDAO",new RestaurantDAO());
			request.setAttribute("restaurantReviews", restaurantReviews);
			request.getRequestDispatcher("WEB-INF/restaurant_reviews.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error","Nemate prava pristupa ovoj funkciji");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			int restaurant_id = Integer.parseInt(request.getParameter("restaurant_id"));
			String username = request.getParameter("username");
			RestaurantReview review = RestaurantReviewDAO.getReview(restaurant_id, username);
			System.out.println(review.getRestaurantID());
			System.out.println(review.getUsername());
			System.out.println(review.getAllowed());
			if(review.getAllowed() == true) {
				review.setAllowed(false);
			}
			if(review.getAllowed() == false) {
				review.setAllowed(true);
			}
			RestaurantReviewDAO.update(review);
			request.setAttribute("message", "Uspesno izmenjen status ocene");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

}
