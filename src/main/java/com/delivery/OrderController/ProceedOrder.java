package com.delivery.OrderController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.User;
import dao.OrderStatusDAO;
/**
 * Servlet implementation class ProceedOrder
 */
@WebServlet("/orderProceed")
public class ProceedOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProceedOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error","Morate biti ulogovani prodavac kako biste mogli da koristite ovu funkcionalnost");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String orderID = request.getParameter("order_id");
			OrderStatusDAO.setNextStatus(orderID);
			request.getSession().setAttribute("message", "Uspesno promenjen status porudzine");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

}
