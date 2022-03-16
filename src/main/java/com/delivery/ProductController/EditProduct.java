package com.delivery.ProductController;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.RestaurantProduct;
import dao.ProductTypeDAO;
import dao.RestaurantProductDAO;

/**
 * Servlet implementation class EditProduct
 */
@WebServlet("/editProduct")
public class EditProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productID = Integer.parseInt(request.getParameter("id"));
		
		RestaurantProduct product = RestaurantProductDAO.getProductByID(productID);
		
		request.setAttribute("product",product);
		request.setAttribute("productTypes", ProductTypeDAO.getAllTypes());
		request.getRequestDispatcher("WEB-INF/editproduct.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productID = Integer.parseInt(request.getParameter("product_id"));
		System.out.println(productID);
		String product_name = request.getParameter("pname");
		System.out.println(product_name);
		int product_price = Integer.parseInt(request.getParameter("price"));
		System.out.println(product_price);
		int type_id = Integer.parseInt(request.getParameter("type"));
		System.out.println(type_id);
		String quantity = request.getParameter("quantity") + request.getParameter("unit");
		System.out.println(quantity);
		String description = request.getParameter("description");
		System.out.println(description);
		RestaurantProduct productToEdit = new RestaurantProduct(productID,product_name,product_price,type_id,quantity,description);
		if(RestaurantProductDAO.update(productToEdit) == 1) {
			request.getSession().setAttribute("message", "Uspesno izmenjen proizvod");
			response.sendRedirect(request.getContextPath() + "/viewProducts");
		}
		else {
			request.getSession().setAttribute("error", "Greska prilikom izmene proizvoda");
			response.sendRedirect(request.getContextPath()  + "/viewProducts");
		}
	}

}
