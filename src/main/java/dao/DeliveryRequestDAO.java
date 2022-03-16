package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.DeliveryRequest;
import bean.Order;

public class DeliveryRequestDAO {
	public static Path table = Paths.get(new ProjectConfig().getRequestsTable());

	public static List<String> getFileContent() {
		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			return fileContent;
		} catch (IOException e) {
			return null;
		}
	}

	public static ArrayList<DeliveryRequest> getAllRequests() {
		ArrayList<DeliveryRequest> allRequests = new ArrayList<DeliveryRequest>();
		for (String dataRow : DeliveryRequestDAO.getFileContent()) {
			allRequests.add(new DeliveryRequest(dataRow.split(",")));
		}
		return allRequests;
	}

	public static int save(DeliveryRequest request) {
		List<String> fileContent = DeliveryRequestDAO.getFileContent();
		String requestAsString = request.toString();
		fileContent.add(requestAsString);
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
			return 1;
		} catch (IOException e) {
			return -1;
		}
	}

	public static int update(DeliveryRequest request) {

		boolean found = false;
		List<String> fileContent = DeliveryRequestDAO.getFileContent();
		for (int i = 0; i < fileContent.size(); i++) {
			DeliveryRequest fileRequest = new DeliveryRequest(fileContent.get(i).split(","));
			if (fileRequest.getOrderID().equals(request.getOrderID())
					&& fileRequest.getUsername().equals(request.getUsername())) {
				fileContent.set(i, request.toString());
				found = true;
			}
		}
		if (found) {
			try {
				Files.write(table, fileContent, StandardCharsets.UTF_8);
				return 1;
			} catch (IOException e) {
				return -1;
			}
		}
		return -1;
	}
	public static ArrayList<DeliveryRequest> getDeliveryRequests(GroupedOrderDAO o){
		ArrayList<DeliveryRequest> orderRequests = new ArrayList<DeliveryRequest>(); 
		for(DeliveryRequest rq : DeliveryRequestDAO.getAllRequests()) {
			if(o.getID().equals(rq.getOrderID())) {
				orderRequests.add(rq);
			}
		}
		return orderRequests;
	}
	public static boolean requestExists(String order_id, String username) {
		ArrayList<DeliveryRequest> allRq = DeliveryRequestDAO.getAllRequests();

		for (DeliveryRequest rq : allRq) {
			if (rq.getOrderID().equals(order_id) && rq.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	public static boolean canPickup(String order_id, String username) {
		ArrayList<DeliveryRequest> allRq = DeliveryRequestDAO.getAllRequests();

		for (DeliveryRequest rq : allRq) {
			if (rq.getOrderID().equals(order_id) && rq.getUsername().equals(username) && rq.accepted == true) {
				return true;
			}
		}
		return false;
	}
	public static ArrayList<DeliveryRequest> getDeliveryRequestsForOrder(String order_id){
		ArrayList<DeliveryRequest> requests = new ArrayList<DeliveryRequest>();
		for(DeliveryRequest rq : DeliveryRequestDAO.getAllRequests()) {
			if(rq.getOrderID().equals(order_id)) {
				requests.add(rq);
			}
		}
		return requests;
	}
	public static void grantPickup(DeliveryRequest dr) {
		DeliveryRequestDAO.update(dr);
		//delete all other requests related
		List<String> fileContent = DeliveryRequestDAO.getFileContent();
		for(String dataRow : fileContent) {
			DeliveryRequest request = new DeliveryRequest(dataRow.split(","));
			if(!request.getUsername().equals(dr.getUsername()) && request.getOrderID().equals(dr.getOrderID())) {
				fileContent.remove(dataRow);
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isGranted(GroupedOrderDAO order) {
		for(DeliveryRequest dr : DeliveryRequestDAO.getAllRequests()) {
			if(dr.getOrderID().equals(order.getID()) && dr.getAccepted()) {
				return true;
			}
		}
		return false;
	}
	public static String getDeliverer(GroupedOrderDAO order) {
		for(DeliveryRequest dr : DeliveryRequestDAO.getAllRequests()) {
			if(dr.getOrderID().equals(order.getID()) && dr.getAccepted()) {
				return dr.getUsername();
			}
		}
		return null;	
	}
	public static void delete(String parameter) {
		List<String> fileContent = DeliveryRequestDAO.getFileContent();
		Iterator<String> iterator = fileContent.iterator();
		while(iterator.hasNext()) {
			String dataRow = iterator.next();
			if(dataRow.contains(parameter)) {
				iterator.remove();
				
			}
		}
		try {
			Files.write(table, fileContent, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
