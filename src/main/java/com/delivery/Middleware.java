package com.delivery;

import javax.servlet.http.HttpSession;

import bean.User;
import dao.CartDAO;

public class Middleware {
	public static boolean isLoggedIn(User u) {
		if (u != null) {
			return true;
		}
		return false;
	}

	public static boolean isAdmin(User u) {
		if (!Middleware.isLoggedIn(u))
			return false;
		if (u.getRoleID() == 1) {
			return true;
		}
		return false;
	}

	public static boolean isUser(User u) {
		if (!Middleware.isLoggedIn(u))
			return false;

		if (u.getRoleID() == 0 && u.getUsername()!=null) {
			return true;
		}
		return false;
	}

	public static boolean isSeller(User u) {
		if (!Middleware.isLoggedIn(u))
			return false;

		if (u.getRoleID() == 2) {
			return true;
		}
		return false;
	}

	public static boolean isDeliverer(User u) {
		if(!Middleware.isLoggedIn(u)) {
			return false;
		}
		if(u.getRoleID() == 3) {
			return true;
		}
		return false;
	}

	public static void createGuest(HttpSession httpSession) {
		// TODO Auto-generated method stub
		httpSession.setAttribute("user", new User());
		httpSession.setAttribute("cart", new CartDAO());
	}

	public static User getLoggedInUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

}
