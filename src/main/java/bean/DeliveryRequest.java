package bean;

public class DeliveryRequest {
	public String order_id;
	public String username;
	public boolean accepted;
	
	public DeliveryRequest(String order_id,String username) {
		this.order_id = order_id;
		this.username = username;
		this.accepted = false;
	}
	public DeliveryRequest(String[] fields) {
		this.order_id = fields[0];
		this.username = fields[1];
		this.accepted = Boolean.valueOf(fields[2]);
	}
	public String getOrderID() {
		return this.order_id;
	}
	public String getUsername() {
		return this.username;
	}
	public boolean getAccepted() {
		return this.accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	@Override
	public String toString() {
		return this.order_id + "," + this.username + "," + this.accepted;
	}
}
