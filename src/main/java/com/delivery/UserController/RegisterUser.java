package com.delivery.UserController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/register")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
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
		request.getRequestDispatcher("WEB-INF/register.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username = request.getParameter("uname");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String password = request.getParameter("pword");
		String gender = request.getParameter("gender");
		String date_of_birth = request.getParameter("dob");
		// create a user with a standard user type because he still has no orders;
		HttpSession session = request.getSession(true);
		User user = new User(username, fname, lname, password, gender, date_of_birth, "Standardni", -1);
		boolean validUser = UserDAO.validateUser(user);
		if (validUser) {
			boolean exists = UserDAO.doesExist(user);
			if (!exists) {
				// store a user within our text file
				int storeResult = UserDAO.storeUser(user);
				System.out.println(storeResult);
				// set user within session scope
				session.setAttribute("username", user.getUsername());
				session.setAttribute("role", user.getRoleID());
				session.setAttribute("message", "Uspesno kreiran nalog. Dobrodosli, " + username);
				// redirect user to home page
				if (user.getRoleID() == 0) {
					response.sendRedirect(request.getContextPath() + "/");
				}
				if (user.getRoleID() == 1) {
					response.sendRedirect(request.getContextPath() + "/admin");
				}
				if (user.getRoleID() == 2) {
					response.sendRedirect(request.getContextPath() + "/seller");
				}
				if (user.getRoleID() == 3) {
					response.sendRedirect(request.getContextPath() + "/delivery");
				}
				
			} else {
				session.setAttribute("error",
						"Korisnik sa ovim korisnickim imenom vec postoji. Promenite podatke i pokusajte ponovo");
				response.sendRedirect(request.getHeader("referer"));
			}
		} else {
			session.setAttribute("error",
					"Uneseni podaci nisu ispravni. Sva polja su obavezna i moraju biti popunjena");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

}
