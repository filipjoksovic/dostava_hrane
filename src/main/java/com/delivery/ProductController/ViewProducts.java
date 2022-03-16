package com.delivery.ProductController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.RestaurantProduct;
import bean.RestaurantProductImage;
import bean.User;
import dao.RestaurantDAO;
import dao.RestaurantProductDAO;
import dao.RestaurantProductImageDAO;

/**
 * Servlet implementation class ViewProducts
 */
@WebServlet("/viewProducts")
public class ViewProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(Middleware.isSeller(loggedInUser)) {
			int restaurantID = loggedInUser.getRestaurantID();
			System.out.println(restaurantID);
			ArrayList<RestaurantProduct> restaurantProducts = RestaurantProductDAO.getProductsFromRestaurant(restaurantID);
			System.out.println(restaurantProducts.size());
//			ArrayList<String> productImages = new ArrayList<String>();
			ArrayList<RestaurantProductImage> productImages = new ArrayList<RestaurantProductImage>();
			
			for(RestaurantProduct rp : restaurantProducts) {
				RestaurantProductImage ri = RestaurantProductImageDAO.getProductImage(rp);
				productImages.add(ri);
			}
			request.setAttribute("products", restaurantProducts);
			request.setAttribute("productImages", productImages);
			request.getRequestDispatcher("WEB-INF/restaurant_products.jsp").forward(request, response);
		}
		else {
			request.getSession().setAttribute("error", "Morate biti ulogovani kao prodavac da biste pristupili ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
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
