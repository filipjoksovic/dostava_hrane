package com.delivery.ProductController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.RestaurantProduct;
import bean.RestaurantProductImage;
import bean.User;
import dao.ProductTypeDAO;
import dao.RestaurantProductDAO;
import dao.RestaurantProductImageDAO;

/**
 * Servlet implementation class AddProduct
 */
@WebServlet("/addProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("productTypes", ProductTypeDAO.getAllTypes());
		request.getRequestDispatcher("WEB-INF/createproduct.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User loggedInUser = (User) request.getSession().getAttribute("user");
		int id = RestaurantProductDAO.getInsertID();
		String product_name = request.getParameter("pname");
		int product_price = Integer.parseInt(request.getParameter("price"));
		int type_id = Integer.parseInt(request.getParameter("type"));
		int restaurant_id = loggedInUser.getRestaurantID();
		String quantity = request.getParameter("quantity") + request.getParameter("unit");
		String description = request.getParameter("description");

		Part filePart = request.getPart("product_image"); // Retrieves <input type="file" name="file">
		System.out.println(getSubmittedFileName(filePart));
		String product_image_name = getSubmittedFileName(filePart);
		
		InputStream fileContent = filePart.getInputStream();

		RestaurantProduct product = new RestaurantProduct(id, product_name, product_price, type_id, restaurant_id,
				quantity, description);
		int result = RestaurantProductDAO.save(product);
		if (result == 1) {
			try {
				File image = new File("C:\\dostava_data\\product_images\\" + product_image_name);
				Files.copy(fileContent, image.toPath());
			} catch (java.nio.file.FileAlreadyExistsException e) {
				System.out.println("Nema potrebe za dodavanjem slike, slika vec postoji");
			}
			RestaurantProductImage pi = new RestaurantProductImage(id,
					"C:\\dostava_data\\product_images\\" + product_image_name);
			result = RestaurantProductImageDAO.save(pi);
			if (result == 1) {
				request.getSession().setAttribute("message", "Uspesno kreiran novi proizvod");
				response.sendRedirect(request.getContextPath() + "/seller");
			} else {
				request.getSession().setAttribute("error", "Greska prilikom kreiranja proizvoda");
				response.sendRedirect(request.getContextPath() + "/seller");
			}
		} else {
			request.getSession().setAttribute("error", "Proizvod sa ovim imenom vec postoji u Vasem restoranu");
			response.sendRedirect(request.getHeader("referer"));
		}
	}

}
