package com.delivery.OrderController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.CartItem;
import bean.Order;
import bean.User;
import bean.UserPoints;
import dao.CartDAO;
import dao.OrderDAO;
import dao.UserDAO;
import dao.UserPointsDAO;

import org.apache.commons.lang3.*;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateOrder() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (!Middleware.isLoggedIn((User) request.getSession().getAttribute("user"))) {
			Middleware.createGuest(request.getSession());
			request.getSession().setAttribute("error", "Morate biti korisnik kako biste mogli da kreirate porudzbinu");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			User loggedInUser = Middleware.getLoggedInUser(request.getSession());
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String country = request.getParameter("country");
			String orderID = RandomStringUtils.randomAlphanumeric(8);
			boolean valid = OrderDAO.validate(fname, lname, address, city, country);
			if (valid) {
				System.out.println(orderID);
				CartDAO sessionCart = (CartDAO) request.getSession().getAttribute("cart");
				ArrayList<CartItem> cartItems = sessionCart.getCartItems();
				for (CartItem citem : cartItems) {
					Order o = new Order(orderID, loggedInUser.getUsername(), citem.getProductID(),
							citem.getProductQuantity(), fname, lname, address, city, country);
					boolean saved = OrderDAO.save(o);
					if (!saved) {
						request.getSession().setAttribute("error", "Doslo je do kreske prilikom kreiranja porudzbine");
						response.sendRedirect(request.getContextPath() + "/");
						return;
					}

				}
				UserPoints up = new UserPoints(loggedInUser.getUsername(), orderID, sessionCart.getCartPoints());
				UserPointsDAO.save(up);
				UserDAO.rankUpUser(loggedInUser);
				sessionCart.emptyCart();
				request.getSession().setAttribute("message",
						"Uspesno kreirana porudzbina. Status Vasih porudzbina mozete pogledati u odeljku 'Porudzbine' u kontrolnoj tabli Vaseg naloga");
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				request.getSession().setAttribute("error",
						"Uneti podaci nisu ispravni. Popunite ispravno formu pa onda kreirajte porudzbinu");
				response.sendRedirect(request.getHeader("referer"));
			}

		}
	}

}
