package com.delivery.UserController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.UserType;
import bean.Role;

import dao.UserDAO;
import dao.RoleDAO;
import dao.UserTypeDAO;

@WebServlet("/adminRegister")
public class AdminRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRegister() {
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
		ArrayList<UserType> userTypes = UserTypeDAO.getAllUserTypes();
		ArrayList<Role> roleTypes = RoleDAO.getAllRoles();
		System.out.println(userTypes.size());
		request.setAttribute("userTypes", userTypes);
		request.setAttribute("roleTypes", roleTypes);
		request.getRequestDispatcher("WEB-INF/adminregister.jsp").forward(request, response);
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
		int roleID = Integer.parseInt(request.getParameter("userRole"));
		String userType = request.getParameter("userRank");
		// create a user with a standard user type because he still has no orders;
		HttpSession session = request.getSession(true);
		User user = new User(username, fname, lname, password, gender, date_of_birth, roleID, userType);
		boolean validUser = UserDAO.validateUser(user);
		if (validUser) {
			boolean exists = UserDAO.doesExist(user);
			if (!exists) {
				// store a user within our text file
				int storeResult = UserDAO.storeUser(user);
				session.setAttribute("message", "Uspesno kreiran nalog");
				// redirect user to home page
				response.sendRedirect(request.getContextPath() + "/admin");
			}
		}
		else {
			session.setAttribute("error","Uneseni podaci nisu ispravni. Sva polja su obavezna i moraju biti popunjena");
			response.sendRedirect(request.getContextPath() + "/adminRegister");
		}
	}

}