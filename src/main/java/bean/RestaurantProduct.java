package bean;

public class RestaurantProduct {
	private int id;
	private String name;
	private int price;
	private int type_id;
	private int restaurant_id;
	private String quantity;
	private String description;

	public RestaurantProduct(int id, String name, int price, int type_id, int restaurant_id, String quantity,
			String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.type_id = type_id;
		this.restaurant_id = restaurant_id;
		this.quantity = quantity;
		this.description = description;
	}

	public RestaurantProduct(String dataRow) {
		String[] fields = dataRow.split(",");
		if (fields.length > 0) {
			this.id = Integer.parseInt(fields[0]);
			this.name = fields[1];
			this.price = Integer.parseInt(fields[2]);
			this.type_id = Integer.parseInt(fields[3]);
			this.restaurant_id = Integer.parseInt(fields[4]);
			this.quantity = fields[5];
			this.description = fields[6];
		}
	}

	public RestaurantProduct(int productID, String product_name, int product_price, int type_id, String quantity, String description) {
		this.id = productID;
		this.name = product_name;
		this.price = product_price;
		this.type_id = type_id;
		this.quantity = quantity;
		this.description = description;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getTypeID() {
		return this.type_id;
	}

	public int getRestaurantID() {
		return this.restaurant_id;
	}

	public int getPrice() {
		return this.price;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public String getDescription() {
		return this.description;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setTypeID(int type_id) {
		this.type_id = type_id;
	}

	public void setRestaurantID(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
