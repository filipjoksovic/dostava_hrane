package bean;

import java.util.Date;
import java.sql.Timestamp;

public class RestaurantReview {
	private int restaurant_id;
	private String username;
	private int grade;
	private String comment;
	private boolean allowed;
	private Timestamp date_created;

	public RestaurantReview(int restaurant_id, String username, int grade, String comment, boolean allowed) {
		this.restaurant_id = restaurant_id;
		this.username = username;
		this.grade = grade;
		this.comment = comment;
		this.allowed = allowed;
		this.date_created = new Timestamp(new Date().getTime());
	}

	public RestaurantReview(int restaurant_id, String username, int grade, String comment) {
		this.restaurant_id = restaurant_id;
		this.username = username;
		this.grade = grade;
		this.comment = comment;
		this.allowed = false;
		this.date_created = new Timestamp(new Date().getTime());
	}

	public RestaurantReview(String[] fields) {
		this.restaurant_id = Integer.parseInt(fields[0]);
		this.username = fields[1];
		this.grade = Integer.parseInt(fields[2]);
		this.comment = fields[3];
		this.allowed = Boolean.valueOf(fields[4]);
		this.date_created = Timestamp.valueOf(fields[5]);
	}

	@Override
	public String toString() {
		return this.restaurant_id + "," + this.username + "," + this.grade + "," + this.comment + "," + this.allowed
				+ "," + this.date_created.toString();
	}

	public int getRestaurantID() {
		return this.restaurant_id;
	}

	public String getUsername() {
		return this.username;
	}

	public int getGrade() {
		return this.grade;
	}

	public String getComment() {
		return this.comment;
	}

	public boolean getAllowed() {
		return this.allowed;
	}
	public String getTimestamp() {
		return this.date_created.toString();
	}

	public void setRestaurantID(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.date_created = timestamp;
	}
}
