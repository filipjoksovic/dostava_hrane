package com.delivery;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ProjectConfig {
	private Properties config = new Properties();

	public ProjectConfig() {
		this.getProperties();
	}

	public void getProperties() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = classLoader.getResourceAsStream("config.properties");
		try {
			config.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAllProperties() {
		this.getProperties();
		config.forEach((key, value) -> System.out.println("Key : " + key + ", Value : " + value));
	}

	public String getProductsTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantProductsTable");
	}

	public String getRequestsTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("deliveryRequestsTable");
	}

	public String getGendersTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("gendersTable");
	}

	public String getOrderStatusTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("orderStatusTable");
	}

	public String getProductTypesTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("productTypesTable");
	}

	public String getRestaurantProductImagesTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantProductImages");
	}

	public String getRestaurantProductsTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantProductsTable");
	}

	public String getRestaurantTypesTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantTypesTable");
	}

	public String getRestaurantsTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantsTable");
	}

	public String getUserOrdersTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("userOrdersTable");
	}

	public String getUserPointsTable() {

		return this.config.getProperty("corePath") + this.config.getProperty("userPointsTable");
	}

	public String getUserRolesTable() {

		return this.config.getProperty("corePath") + this.config.getProperty("userRolesTable");
	}

	public String getUserTypesTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("userTypesTable");
	}

	public String getUsersTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("usersTable");
	}
	public String getRestaurantReviewsTable() {
		return this.config.getProperty("corePath") + this.config.getProperty("restaurantReviewsTable");
	}

}
