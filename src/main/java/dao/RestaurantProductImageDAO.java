package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.RestaurantProduct;
import bean.RestaurantProductImage;

public class RestaurantProductImageDAO {
	public static Path table = Paths.get(new ProjectConfig().getRestaurantProductImagesTable());
	public static String table_string = table.toString();

	public static String rowAsString(RestaurantProductImage ri) {
		return ri.getProductID() + "," + ri.getImagePath();
	}

	public static int save(RestaurantProductImage ri) {
		String dataRow = RestaurantProductImageDAO.rowAsString(ri);
		try (FileWriter fw = new FileWriter(table_string, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(dataRow);
			return 1;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static int update(RestaurantProductImage ri) {

		try {
			List<String> fileContent = (Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				RestaurantProductImage productImage = new RestaurantProductImage(fileContent.get(i));
				if (productImage.getProductID() == ri.getProductID()) {
					productImage.setImagePath(ri.getImagePath());
					fileContent.set(i, RestaurantProductImageDAO.rowAsString(productImage));
					break;
				}
			}
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static ArrayList<RestaurantProductImage> getAllProductImages() {
		ArrayList<RestaurantProductImage> allProductImages = new ArrayList<RestaurantProductImage>();
		try {
			List<String> fileContent = (Files.readAllLines(table, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
				RestaurantProductImage productImage = new RestaurantProductImage(fileContent.get(i));
				allProductImages.add(productImage);
			}
			return allProductImages;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<RestaurantProductImage> getProductImages(ArrayList<RestaurantProduct> products) {
		ArrayList<RestaurantProductImage> images = new ArrayList<RestaurantProductImage>();
		ArrayList<RestaurantProductImage> allImages = RestaurantProductImageDAO.getAllProductImages();
		for (RestaurantProduct rp : products) {
			for (RestaurantProductImage ri : allImages) {
				if (rp.getID() == ri.getProductID()) {
					images.add(ri);
				}
			}
		}
		return images;
	}

	public static RestaurantProductImage getProductImage(RestaurantProduct rp) {
		for (RestaurantProductImage ri : RestaurantProductImageDAO.getAllProductImages()) {
			if (rp.getID() == ri.getProductID()) {
				return ri;
			}
		}
		return null;
	}

	public static String toImage(RestaurantProductImage image) {
		File file = new File(image.getImagePath());
		String imageData = "";
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(file.toPath());
			byte[] encodeBase64 = Base64.getEncoder().encode(fileContent);
			imageData = new String(encodeBase64, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		}
		return imageData;
	}

	public static int delete(RestaurantProductImage image) {
		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			Iterator<String> itr = fileContent.iterator();
			while (itr.hasNext()) {
				String dataRow = itr.next();
				RestaurantProductImage rowImage= new RestaurantProductImage(dataRow);
				if(rowImage.getProductID() == image.getProductID()) {
					itr.remove();
				}
			}
			// for(int i = 0; i < fileContent.size(); i++) {
//				RestaurantProductImage rowImage= new RestaurantProductImage(fileContent.get(i));
//				if(rowImage.getProductID() == image.getProductID()) {
//					fileContent.remove(i);
//				}
//			}
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return 1;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static RestaurantProductImage getImageFromID(int id) {
		for (RestaurantProductImage image : RestaurantProductImageDAO.getAllProductImages()) {
			if (image.getProductID() == id) {
				return image;
			}
		}
		return null;
	}
}
