package com.delivery.UserController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Restaurant;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantTypeDAO;
/**
 * Servlet implementation class SellerHome
 */
@WebServlet("/seller")
public class SellerHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellerHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti prodavac kako biste mogli da pristupite ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			Restaurant managersRestaurant = RestaurantDAO.getRestaurantByID(loggedInUser.getRestaurantID());
			request.setAttribute("restaurant",managersRestaurant);
			request.setAttribute("restaurantTypeDAO",new RestaurantTypeDAO());
			request.setAttribute("restaurantLogo",RestaurantDAO.getRestaurantLogo(managersRestaurant));
			request.setAttribute("restaurantType",RestaurantTypeDAO.getTypeFromID(managersRestaurant));
			request.getRequestDispatcher("WEB-INF/seller.jsp").forward(request, response);;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
