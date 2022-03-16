package com.delivery.DeliveryController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.Middleware;

import bean.DeliveryRequest;
import bean.User;
import dao.DeliveryRequestDAO;
import dao.GroupedOrderDAO;

/**
 * Servlet implementation class SendDeliveryRequest
 */
@WebServlet("/sendDeliveryRequest")
public class SendDeliveryRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendDeliveryRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		if(!Middleware.isDeliverer(loggedInUser)) {
			request.getSession().setAttribute("error", "Morate biti ulogovani kao dostavljac kako biste mogli da pristupite ovom delu sajta");
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			String order_id = request.getParameter("order_id");
			DeliveryRequest drequest = new DeliveryRequest(order_id,loggedInUser.getUsername());
			DeliveryRequestDAO.save(drequest);
			request.getSession().setAttribute("message","Uspesno kreiran zahtev za dostavu porudzbine. Kada menadzer odobri Vas zahtev, mozete doci po porudzbinu.");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

}
