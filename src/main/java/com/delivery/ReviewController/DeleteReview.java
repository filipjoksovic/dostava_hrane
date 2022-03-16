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
 * Servlet implementation class deleteReview
 */
@WebServlet("/deleteReview")
public class DeleteReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReview() {
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
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isAdmin(loggedInUser) && !Middleware.isUser(loggedInUser)) {
			request.getSession().setAttribute("error","Morate biti ulogovani kao korisnik ili administrator kako biste imali prisutp ovoj funkciji");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			int restaurant_id = Integer.parseInt(request.getParameter("restaurant_id"));
			String username = loggedInUser.getUsername();
			System.out.println(username);
			System.out.println(restaurant_id);
			RestaurantReview reviewInstance = RestaurantReviewDAO.getReview(restaurant_id, username);
			if(!reviewInstance.getUsername().equals(loggedInUser.getUsername())) {
				request.getSession().setAttribute("error","Morate biti vlasnik ocene kako biste mogli da je uklonite");
				response.sendRedirect(request.getContextPath() + "/");
			}
			else {
				RestaurantReviewDAO.delete(reviewInstance);
				request.getSession().setAttribute("message", "Uspesno uklonjena ocena");
				response.sendRedirect(request.getContextPath() + "/account");
			}
		}
	}

}
