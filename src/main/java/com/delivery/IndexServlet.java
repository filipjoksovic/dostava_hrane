package com.delivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.Restaurant;
import bean.RestaurantImages;
import bean.RestaurantType;

import dao.UserDAO;
import dao.CartDAO;
import dao.RestaurantDAO;
import dao.RestaurantProductImageDAO;
import dao.RestaurantTypeDAO;

import com.delivery.Middleware;


/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!Middleware.isLoggedIn((User)request.getSession().getAttribute("user"))) {
			request.getSession().setAttribute("user", new User());
			request.getSession().setAttribute("cart", new CartDAO());
		}
		ArrayList<Restaurant> allRestaurants = RestaurantDAO.getAllRestaurants();
		allRestaurants.sort(new Comparator<Restaurant>() {
			@Override
			public int compare (Restaurant r1,Restaurant r2) {
				return Boolean.compare(r2.getActive(),r1.getActive());
			}
		});
		request.setAttribute("restaurants", allRestaurants);
		request.setAttribute("restaurantDAO", new RestaurantDAO());
		request.setAttribute("restaurantTypeDAO", new RestaurantTypeDAO());
		request.setAttribute("restaurantTypes",RestaurantTypeDAO.getAllTypes());
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
