package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.delivery.ProjectConfig;

import bean.Order;
import bean.OrderStatus;

public class OrderStatusDAO {
	private static Path table = Paths.get(new ProjectConfig().getOrderStatusTable());

	public static String getStatusFromID(int status_id) {
		try {
			List<String> fileContent = Files.readAllLines(table, StandardCharsets.UTF_8);
			for (String dataRow : fileContent) {
				OrderStatus os = new OrderStatus(dataRow);
				if (os.getStatusID() == status_id) {
					return os.getStatusText();
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}

	}
	public static int getOrderStatusID(String orderID) {
		ArrayList<Order> orderItems = OrderDAO.getOrderItemsFromOrderID(orderID);
		int minStatus = (orderItems.size() > 0) ? orderItems.get(0).getStatus() : 0;
		for (Order order : orderItems) {
			if (order.getStatus() < minStatus) {
				minStatus = order.getStatus();
			}
		}
		return minStatus;
	}
	public static int getOrderStatus(String orderID) {
		ArrayList<Order> orderItems = OrderDAO.getOrderItemsFromOrderID(orderID);
		int minStatus = (orderItems.size() > 0) ? orderItems.get(0).getStatus() : 0;
		for (Order order : orderItems) {
			if (order.getStatus() < minStatus) {
				minStatus = order.getStatus();
			}
		}
		return minStatus;
	}
	public static String getNextStatus(String order_id) {
		ArrayList<Order> orderItems = OrderDAO.getOrderItemsFromOrderID(order_id);
		int minStatus = (orderItems.size() > 0) ? orderItems.get(0).getStatus() : 0;
		for (Order order : orderItems) {
			if (order.getStatus() < minStatus) {
				minStatus = order.getStatus();
			}
		}
		if(minStatus < 5) {
			minStatus++;
		}
		return OrderStatusDAO.getStatusFromID(minStatus);
	}
	public static void setNextStatus(String order_id) {
		ArrayList<Order> orderItems = OrderDAO.getOrderItemsFromOrderID(order_id);
		int minStatus = (orderItems.size() > 0) ? orderItems.get(0).getStatus() : 0;
		for (Order order : orderItems) {
			if (order.getStatus() < minStatus) {
				minStatus = order.getStatus();
			}
		}
		if(minStatus < 5) {
			minStatus++;
		}
		OrderDAO.updateGroupedOrder(order_id);
	}

}
