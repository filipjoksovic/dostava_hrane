package com.delivery.UserController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Role;
import bean.User;
import bean.UserType;
import dao.UserDAO;
import dao.UserPointsDAO;
import dao.UserTypeDAO;
import dao.GenderDAO;
import dao.RoleDAO;

/**
 * Servlet implementation class ListUsers
 */
@WebServlet("/listUsers")
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if (!Middleware.isAdmin(loggedInUser)) {
			request.getSession().setAttribute("error",
					"Morate biti administrator kako biste imali pregled svih korisnika");
			response.sendRedirect(request.getContextPath() + "/");
		} else {

			ArrayList<User> users = UserDAO.getAllUsers();
			for (User u : users) {
				u.setGender(GenderDAO.getGenderTitle(u.getGender()));
				u.setRoleTitle(RoleDAO.getRoleTitleFromID(u.getRoleID()));
			}
			if (request.getParameter("search_text") != null) {
				System.out.println("Here");
				String searchText = request.getParameter("search_text");
				request.setAttribute("search_text", searchText);
				Iterator<User> iterator = users.iterator();
				while (iterator.hasNext()) {
					User user = iterator.next();
					if (!user.getUsername().contains(searchText) && !user.getFirstName().contains(searchText)
							&& !user.getLastName().contains(searchText)) {
						iterator.remove();
					}
				}

			}
			if (request.getParameterValues("user_types") != null) {
				List<String> checkedUserTypes = Arrays.asList(request.getParameterValues("user_types"));
				Iterator<User> iterator = users.iterator();
				iterator = users.iterator();
				while (iterator.hasNext()) {
					User user = iterator.next();
					if (!checkedUserTypes.contains(user.getUserType())) {
						iterator.remove();
					}
				}
				request.setAttribute("checkedTypes", checkedUserTypes);
			}
			if (request.getParameterValues("user_roles") != null) {
				List<String> checkedUserRoles = Arrays.asList(request.getParameterValues("user_roles"));
				Iterator<User> iterator = users.iterator();
				iterator = users.iterator();
				while (iterator.hasNext()) {
					User user = iterator.next();
					if (!checkedUserRoles.contains(String.valueOf(user.getRoleID()))) {
						iterator.remove();
					}
				}
				request.setAttribute("checkedRoles", checkedUserRoles);
			}
			if(request.getParameter("sort_filter") !=null && request.getParameter("sort_order") != null) {
				String sortParameter = request.getParameter("sort_filter");
				String sortOrder = request.getParameter("sort_order");
				
				if(sortParameter.equals("fname")) {
					users.sort(new Comparator<User>() {
						@Override
						public int compare(User u1, User u2) {
							return u1.getFirstName().compareTo(u2.getFirstName());
						}
					});
				}
				if(sortParameter.equals("lname")) {
					users.sort(new Comparator<User>() {
						@Override
						public int compare(User u1, User u2) {
							return u1.getLastName().compareTo(u2.getLastName());
						}
					});
				}
				if(sortParameter.equals("uname")) {
					users.sort(new Comparator<User>() {
						@Override
						public int compare(User u1, User u2) {
							return u1.getUsername().compareTo(u2.getUsername());
						}
					});
				}
				if(sortOrder.equals("desc")) {
					Collections.reverse(users);
				}
				request.setAttribute("sOrder", sortOrder);
				request.setAttribute("sFilter", sortParameter);
			}
			ArrayList<Role> allRoles = RoleDAO.getAllRoles();
			ArrayList<UserType> allTypes = UserTypeDAO.getAllUserTypes();
			request.setAttribute("user_roles", allRoles);
			request.setAttribute("user_types", allTypes);
			request.setAttribute("userPointsDAO", new UserPointsDAO());
			request.setAttribute("users", users);
			request.getRequestDispatcher("WEB-INF/users.jsp").forward(request, response);
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
