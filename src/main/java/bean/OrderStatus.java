package bean;

public class OrderStatus {
	private int status_id;
	private String status_text;
	
	public OrderStatus(String dataRow) {
		String[] fields = dataRow.split(",");
		this.status_id = Integer.parseInt(fields[0]);
		this.status_text = fields[1];
	}
	public int getStatusID() {
		return this.status_id;
	}
	public String getStatusText() {
		return this.status_text;
	}
	public void setStatusID(int status_id) {
		this.status_id = status_id;
	}
	public void setStatusText(String status_text) {
		this.status_text = status_text;
	}
}
