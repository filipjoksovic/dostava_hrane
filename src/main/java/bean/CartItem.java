package bean;

public class CartItem {
	private int product_id;
	private int product_quantity;
	
	public CartItem(int product_id, int product_quantity) {
		this.product_id = product_id;
		this.product_quantity = product_quantity;
	}
	public CartItem(RestaurantProduct product) {
		this.product_id = product.getID();
		this.product_quantity = 1;
	}
	public int getProductID() {
		return this.product_id;
	}
	public int getProductQuantity() {
		return this.product_quantity;
	}
	public void setProductID(int product_id) {
		this.product_id = product_id;
	}
	public void setProductQuantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}
}
