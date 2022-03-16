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
import dao.RestaurantReviewDAO;
/**
 * Servlet implementation class AllowReview
 */
@WebServlet("/changeReviewStatus")
public class ChangeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isAdmin(loggedInUser) && !Middleware.isSeller(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti ulogovani korisnik kako biste mogli da odobrite ocenu");
		}
		else {
			int restaurantID = Integer.parseInt(request.getParameter("restaurant_id"));
			String username = request.getParameter("username");
			RestaurantReview review = RestaurantReviewDAO.getReview(restaurantID,username);
			
		}
	}

}
