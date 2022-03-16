package com.delivery.CartController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.User;
import dao.CartDAO;
import bean.CartItem;
/**
 * Servlet implementation class RemoveFromCart
 */
@WebServlet("/removeFromCart")
public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser) || !Middleware.isUser(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti ulogovani kao korisnik kako biste izvrsiti ovu radnju");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			CartDAO sessionCart = (CartDAO) request.getSession().getAttribute("cart");
			int productID = Integer.parseInt(request.getParameter("product_id"));
			String quantityParameter = request.getParameter("quantity");
			if(quantityParameter!=null) {
				int quantity = Integer.parseInt(quantityParameter);
				sessionCart.removeFromCart(productID,quantity);
				request.getSession().setAttribute("message", "Uspesno umanjena kolicina proizvoda u korpi");
				response.sendRedirect(request.getHeader("referer"));
			}
			else {
				sessionCart.removeFromCart(productID);
				request.getSession().setAttribute("message", "Uspesno uklonjen proizvod iz korpe");
				response.sendRedirect(request.getHeader("referer"));
			}
		}
		
	}

}
