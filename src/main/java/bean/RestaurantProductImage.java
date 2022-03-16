package bean;

public class RestaurantProductImage {
	private int product_id;
	private String imagePath;

	public RestaurantProductImage(int product_id, String imagePath) {
		this.product_id = product_id;
		this.imagePath = imagePath;
	}

	public RestaurantProductImage(String dataRow) {
		String[] fields = dataRow.split(",");
		this.product_id = Integer.parseInt(fields[0]);
		this.imagePath = fields[1];
	}

	public int getProductID() {
		return this.product_id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setProductID(int product_id) {
		this.product_id = product_id;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
