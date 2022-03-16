package com.delivery.UserController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.CartDAO;
import dao.UserDAO;

/**
 * Servlet implementation class LoginUser
 */
@WebServlet("/login")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User result = UserDAO.tryLogin(username, password);
		System.out.println(result);
		if (result != null) {
			if (result.getBlocked()) {
				request.getSession().setAttribute("error",
						"Vas nalog je blokiran od strane administratora i ne mozete se prijaviti na njega.");
				response.sendRedirect(request.getHeader("referer"));
				return;
			}
			request.getSession().setAttribute("user", result);
			request.getSession().setAttribute("message", "Uspesno prijavljivanje na sajt. Dobrodosli, " + username);
			System.out.println("Role id: " + result.getRoleID());
			if (result.getRoleID() == 0) {
				CartDAO userCart = new CartDAO();
				request.setAttribute("cart", userCart);
				response.sendRedirect(request.getContextPath() + "/");
			}
			if (result.getRoleID() == 1) {
				response.sendRedirect(request.getContextPath() + "/admin");
			}
			if (result.getRoleID() == 2) {
				response.sendRedirect(request.getContextPath() + "/seller");
			}
			if (result.getRoleID() == 3) {
				response.sendRedirect(request.getContextPath() + "/delivery");
			}
		} else {
			request.getSession().setAttribute("error",
					"Greska prilikom prijavljivanja na stranicu. Proverite Vase podatke i pokusajte ponovo");
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
		}
	}

}
