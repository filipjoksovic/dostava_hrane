package com.delivery.UserController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.User;
import dao.UserDAO;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/editAccount")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUser() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error",
					"Morate biti ulogovani kao korisnik kako biste mogli da izmenite ovaj nalog");
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			String username = request.getParameter("username");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String gender = request.getParameter("gender");
			String dob = request.getParameter("dob");
			loggedInUser.setFirstName(fname);
			loggedInUser.setLastName(lname);
			loggedInUser.setGender(gender);
			loggedInUser.setDateOfBirth(dob);
			loggedInUser.setUsername(username);
			System.out.println(loggedInUser.getFirstName());
			System.out.println(loggedInUser.getLastName());
			System.out.println(loggedInUser.getGender());
			System.out.println(loggedInUser.getDateOfBirth());
			System.out.println(UserDAO.update(loggedInUser));
			request.getSession().setAttribute("message", "Uspesno izmenjen nalog");
			request.getSession().setAttribute("user", UserDAO.getUserByUsername(username));
			response.sendRedirect(request.getContextPath() + "/account");
		}
	}

}
