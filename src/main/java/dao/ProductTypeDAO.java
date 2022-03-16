package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.ProductType;
import bean.RestaurantProduct;

public class ProductTypeDAO {
	private static Path table = Paths.get(new ProjectConfig().getProductTypesTable());
	private static String table_string = table.toString();
	
	public static String rowAsString(ProductType pt) {
		return pt.getID() + "," + pt.getName();
	}
	
	public static int save(ProductType ptype) {
		String dataRow = ProductTypeDAO.rowAsString(ptype);
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
	public static ArrayList<ProductType> getAllTypes(){
		ArrayList<ProductType> productTypes = new ArrayList<ProductType>();
		try {
			List<String> fileContent = (Files.readAllLines(table,StandardCharsets.UTF_8));
			for(int i = 0; i < fileContent.size(); i++) {
				ProductType ptype= new ProductType(fileContent.get(i));
				productTypes.add(ptype);
			}
			return productTypes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String getTypeName(int id) {
		ArrayList<ProductType> allTypes = ProductTypeDAO.getAllTypes();
		for(ProductType pt : allTypes) {
			if(pt.getID() == id) {
				return pt.getName();
			}
		}
		return null;
	}
}
