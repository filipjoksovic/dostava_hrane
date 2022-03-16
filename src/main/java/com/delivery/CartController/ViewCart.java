package com.delivery.CartController;

import java.io.IOException;

import com.delivery.Middleware;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;
import dao.RestaurantProductDAO;
import dao.UserPointsDAO;
import bean.CartItem;
import bean.RestaurantProduct;
import bean.User;
import bean.UserType;
import dao.UserTypeDAO;

/**
 * Servlet implementation class ViewCart
 */
@WebServlet("/cart")
public class ViewCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User sessionUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isLoggedIn(sessionUser) || !Middleware.isUser(sessionUser)) {
			request.setAttribute("error",
					"Morate biti ulogovani kako biste mogli pristupiti korpi i porucuti proizvod");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			CartDAO sessionCart = (CartDAO) request.getSession().getAttribute("cart");
			ArrayList<CartItem> cartItems = sessionCart.getCartItems();
//	    	for(CartItem citem : cartItems) {
//	    	    System.out.println("[" + citem.getProductID() + "," + citem.getProductQuantity() + "]");
//	    	}
			System.out.println("hello");
			System.out.println(sessionUser.getUserType());
			UserType ut = UserTypeDAO.getUserType(sessionUser);
			System.out.println(ut);
			System.out.println(ut.getTitle());
//
			System.out.println(ut.getDiscount());
			double discountedPrice = sessionCart.getDiscountedPrice((double)ut.getDiscount());
			System.out.println(discountedPrice);
			ArrayList<RestaurantProduct> productsInCart = RestaurantProductDAO.getProductsFromCart(cartItems);
			request.setAttribute("accountPoints", UserPointsDAO.getUserPoints(sessionUser.getUsername()));
			request.setAttribute("restaurantProductDAO", new RestaurantProductDAO());
			request.setAttribute("cartProducts", productsInCart);
			request.setAttribute("discount",ut.getDiscount());
			request.setAttribute("discountedPrice",discountedPrice);
			request.setAttribute("cartDAO", sessionCart);
			request.getRequestDispatcher("WEB-INF/cart.jsp").forward(request, response);
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
