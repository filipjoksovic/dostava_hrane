package com.delivery.OrderController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.Order;
import bean.User;
import dao.GroupedOrderDAO;
import dao.OrderDAO;

/**
 * Servlet implementation class CancelOrder
 */
@WebServlet("/cancelOrder")
public class CancelOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error","Morate biti prijavljeni korisnik kako biste mogli da otkazete porudzbinu");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String order_id = request.getParameter("id");
			GroupedOrderDAO orderInstance = new GroupedOrderDAO(order_id);
			if(!orderInstance.getUsername().equals(loggedInUser.getUsername())) {
				request.getSession().setAttribute("error","Morate biti vlasnik ove porudzine kako biste mogli da je otkazete");
				response.sendRedirect(request.getContextPath() + "/");
			}
			else {
				Order order = orderInstance.getOrderItems().get(0);
				if(OrderDAO.canCancel(orderInstance.getOrderItems())) {
					loggedInUser = OrderDAO.cancel(order);
					request.getSession().setAttribute("user", loggedInUser);
					request.getSession().setAttribute("message", "Uspesno otkazana porudzbina");
					response.sendRedirect(request.getContextPath() + "/account");
				}
				else {
					request.getSession().setAttribute("error","Vasa porudzbina ne moze biti otkazana");
					response.sendRedirect(request.getContextPath() + "/account");
				}
			}
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
