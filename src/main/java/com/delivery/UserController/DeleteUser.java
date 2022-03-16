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
 * Servlet implementation class DeleteUser
 */
@WebServlet("/deleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isAdmin(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti administrator kako biste koristili ovu funkciju");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String username = request.getParameter("username");
			User userInstance = UserDAO.getUserByUsername(username);
			UserDAO.delete(userInstance);
			request.getSession().setAttribute("message","Korisnik " + username + " je uspesno uklonjen iz sistema");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
