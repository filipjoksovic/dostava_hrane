package com.delivery.RestaurantController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.DeliveryRequest;
import bean.User;
import dao.DeliveryRequestDAO;
/**
 * Servlet implementation class GetDeliveryRequests
 */
@WebServlet(name = "RestaurantDeliveryRequests", urlPatterns = { "/getDeliveryRequests" })
public class GetDeliveryRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDeliveryRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti menadzer ovog restorana kako biste pristupili zahtevima za dostavu");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String order_id = request.getParameter("id");
			ArrayList<DeliveryRequest> deliveryRequests = DeliveryRequestDAO.getDeliveryRequestsForOrder(order_id);
			request.setAttribute("requests",deliveryRequests);
			request.setAttribute("orderID",order_id);
			request.getRequestDispatcher("WEB-INF/restaurant_requests.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isLoggedIn(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti menadzer ovog restorana kako biste pristupili zahtevima za dostavu");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String order_id = request.getParameter("order_id");
			String username = request.getParameter("username");
			DeliveryRequest dr = new DeliveryRequest(order_id,username);
			dr.setAccepted(true);
			DeliveryRequestDAO.grantPickup(dr);
			request.getSession().setAttribute("message", "Uspesno odabran dostavljac za datu porudzbinu");
			response.sendRedirect(request.getContextPath() + "/viewOrders");
		}
	}

}
