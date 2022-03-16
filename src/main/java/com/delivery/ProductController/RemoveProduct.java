package com.delivery.ProductController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RestaurantProduct;
import bean.RestaurantProductImage;
import bean.User;
import dao.RestaurantProductDAO;
import dao.RestaurantProductImageDAO;

/**
 * Servlet implementation class RemoveProduct
 */
@WebServlet("/removeProduct")
public class RemoveProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveProduct() {
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
		User loggedInUser = (User) request.getSession().getAttribute("user");
		int productID = Integer.parseInt(request.getParameter("id"));
		RestaurantProduct productToDelete = RestaurantProductDAO.getProductByID(productID);
		if (loggedInUser.getRestaurantID() != productToDelete.getRestaurantID()) {
			request.getSession().setAttribute("error", "Nemate pristup izmeni ovog proizvoda");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			int deleteResult = RestaurantProductDAO.delete(productToDelete);
			if (deleteResult == 1) {

				request.getSession().setAttribute("message", "Uspesno uklonjen proizvod");
			} else {
				request.getSession().setAttribute("error", "Greska prilikom uklanjanja proizvoda");
			}
			response.sendRedirect(request.getContextPath() + "/viewProducts");
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
