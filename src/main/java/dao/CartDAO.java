package dao;

import java.util.ArrayList;

import bean.CartItem;
import bean.RestaurantProduct;

public class CartDAO {
	private ArrayList<CartItem> cartItems;
	
//	creates an initial cartItems arrayList
	public CartDAO() {
		this.cartItems = new ArrayList<CartItem>();
	}
//	creates an cartItems arrayList with existing items in cart, used for updating
	public CartDAO(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	public ArrayList<CartItem> getCartItems() {
		return this.cartItems;
	}
	public CartItem getCartItem(int product_id) {
		for(CartItem citem : this.cartItems) {
			if(citem.getProductID() == product_id) {
				return citem;
			}
		}
		return null;
	}
	public int getCartItemQuantity(int product_id) {
		for(CartItem citem : this.cartItems) {
			if(citem.getProductID() == product_id) {
				return citem.getProductQuantity();
			}
		}
		return 0;
	}
	public int getCartCount() {
		int sum = 0;
		for(CartItem item : this.cartItems) {
			sum+= item.getProductQuantity();
		}
		return sum;
	}
	//add one item to cart
	public int addToCart(CartItem item) {
		boolean found = false;
		for(CartItem cItem : this.cartItems) {
			if(cItem.getProductID() == item.getProductID()) {
				cItem.setProductQuantity(cItem.getProductQuantity() + item.getProductQuantity());
				found = true;
			}
		}
		if(!found) {
			this.cartItems.add(item);
		}
		return this.getCartCount();
	}
	//add items to cart with specified quantity
	public int addToCart(CartItem item,int quantity) {
		boolean found = false;
		for(CartItem cItem : this.cartItems) {
			if(cItem.getProductID() == item.getProductID()) {
				cItem.setProductQuantity(cItem.getProductQuantity() + quantity);
				found = true;
			}
		}
		if(!found) {
			item.setProductQuantity(quantity);
			this.cartItems.add(item);
		}
		return this.getCartCount();
	}
	//remove item from cart completely
	public int removeFromCart(int productID) {
		for(CartItem cItem : this.cartItems) {
			if(cItem.getProductID() == productID) {
				this.cartItems.remove(cItem);
				break;
			}
		}
		return this.getCartCount();
	}
	//remove item from cart by quantity;
	public int removeFromCart(int productID, int quantity) {
		for(int i = 0; i < this.cartItems.size(); i++) {
			if(this.cartItems.get(i).getProductID() == productID) {
				if(this.cartItems.get(i).getProductQuantity() <= quantity) {
					this.cartItems.remove(this.cartItems.get(i));
					break;
				}
				else {
					this.cartItems.get(i).setProductQuantity(this.cartItems.get(i).getProductQuantity() - quantity);
					break;
				}
			}
		}
		return this.getCartCount();
	}
	public int emptyCart() {
		this.cartItems = new ArrayList<CartItem>();
		return 0;
	}
	public int getCartPrice() {
		int sum = 0;
		for(CartItem item : this.cartItems) {
			RestaurantProduct product = RestaurantProductDAO.getProductByID(item.getProductID());
			sum+=product.getPrice()*item.getProductQuantity();
		}
		return sum;
	}
	public double getDiscountedPrice(double discount) {
		double cartPrice = (double) this.getCartPrice();
		cartPrice = (cartPrice - (cartPrice * (discount * 0.01)));
		return cartPrice;
//		return this.getCartPrice();

	}
	public int getCartPoints() {
		int cartPrice = this.getCartPrice();
		int cartPoints = Math.round((float)cartPrice / 1000 * 133);
		return cartPoints;
	}
}
