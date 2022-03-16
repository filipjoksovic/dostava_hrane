package com.delivery.RestaurantController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.delivery.Middleware;

import bean.Order;
import bean.User;
import dao.DeliveryRequestDAO;
import dao.GenderDAO;
import dao.GroupedOrderDAO;
import dao.OrderDAO;
import dao.OrderStatusDAO;
import dao.RestaurantProductDAO;
import dao.UserDAO;
import dao.UserPointsDAO;
/**
 * Servlet implementation class ViewOrders
 */
@WebServlet("/viewOrders")
public class ViewOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOrders() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isSeller(loggedInUser)) {
			request.getSession().setAttribute("error","Morate biti ulogovani prodavac kako biste mogli pristupiti ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			ArrayList<GroupedOrderDAO> groupedOrders = new ArrayList<GroupedOrderDAO>();
			for(String order_id : OrderDAO.getUniqueOrderIDS(loggedInUser.getRestaurantID())) {
				groupedOrders.add(new GroupedOrderDAO(order_id));
			}
			HashMap<String,ArrayList<Order>> orders = OrderDAO.getRestaurantOrders(loggedInUser.getRestaurantID());
			request.setAttribute("groupedOrders",groupedOrders);
			request.setAttribute("orderDAO", new OrderDAO());
			request.setAttribute("orderStatusDAO", new OrderStatusDAO());
			request.setAttribute("productDAO", new RestaurantProductDAO());
			request.setAttribute("userDAO",new UserDAO());
			request.setAttribute("deliveryRequestDAO",new DeliveryRequestDAO());
			request.getRequestDispatcher("WEB-INF/restaurant_orders.jsp").forward(request, response);
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
