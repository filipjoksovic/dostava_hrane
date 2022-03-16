package com.delivery.CartController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;
import dao.RestaurantProductDAO;
import bean.CartItem;
import bean.RestaurantProduct;
import bean.User;
import bean.RestaurantProduct;
import com.delivery.Middleware;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User sessionUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isUser(sessionUser)) {
			request.getSession().setAttribute("error",
					"Morate biti ulogovani kao korisnik kako biste mogli da dodate proizvod u korpu");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			CartDAO sessionCart = (CartDAO) request.getSession().getAttribute("cart");
			int productID = Integer.parseInt(request.getParameter("product_id"));
			String productQuantityParam = request.getParameter("quantity");
			if (productQuantityParam == null) {
				RestaurantProduct product = RestaurantProductDAO.getProductByID(productID);
				CartItem item = new CartItem(product);
				sessionCart.addToCart(item);
				request.getSession().setAttribute("message", "Proizvod uspesno dodat u korpu");
				response.sendRedirect(request.getHeader("referer"));
			} else {
				int quantity = Integer.parseInt(productQuantityParam);
				RestaurantProduct product = RestaurantProductDAO.getProductByID(productID);
				CartItem item = new CartItem(product);
				item.setProductQuantity(quantity);
				sessionCart.addToCart(item);
				request.getSession().setAttribute("message", "Proizvod uspesno dodat u korpu");
				response.sendRedirect(request.getHeader("referer"));
			}
		}
	}

}
