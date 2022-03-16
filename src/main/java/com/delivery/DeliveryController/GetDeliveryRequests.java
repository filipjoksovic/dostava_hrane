package com.delivery.DeliveryController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.User;
import dao.DeliveryRequestDAO;
import dao.GroupedOrderDAO;
import dao.OrderDAO;
import dao.OrderStatusDAO;
import dao.RestaurantProductDAO;

/**
 * Servlet implementation class GetDeliveryRequests
 */
@WebServlet("/delivery")
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
		if(!Middleware.isDeliverer(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti ulogovani kao dostavljac da biste pristupili ovom delu naloga");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			ArrayList<GroupedOrderDAO> groupedOrders = new ArrayList<GroupedOrderDAO>();
			for(String order_id : OrderDAO.getUniqueOrderIDS()) {
				GroupedOrderDAO grouped_order = new GroupedOrderDAO(order_id);
				groupedOrders.add(grouped_order);
			}
			request.setAttribute("groupedOrders", groupedOrders);
			request.setAttribute("productDAO", new RestaurantProductDAO());
			request.setAttribute("deliveryRequestDAO",new DeliveryRequestDAO());
			request.setAttribute("orderStatusDAO",new OrderStatusDAO());

			request.getRequestDispatcher("WEB-INF/delivery_requests.jsp").forward(request, response);
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
