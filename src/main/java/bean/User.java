package bean;


public class User{
	private String username;
	private String first_name;
	private String last_name;
	private String password;
	private String gender;
	private String date_of_birth;
	private int role_id;
	private String role_title;
	private String user_type;
	private int restaurant_id;
	private boolean blocked;
	
	//creates a guest user
	public User() {
		this.username = null;
		this.first_name = null;
		this.last_name = null;
		this.password = null;
		this.gender = null;
		this.date_of_birth = null;
		this.role_id = -1;
		this.user_type = null;
		this.blocked = false;
	}
	
	//creates a buyer by default
	public User(String username,String first_name, String last_name, String password,String gender, String date_of_birth, String user_type,int restaurant_id) {
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.date_of_birth = date_of_birth;
		this.gender = gender;
		this.role_id = 0;
		this.user_type = user_type;
		this.restaurant_id = restaurant_id;
		this.blocked = false;
	}
	//creates a user from a role that admin specifies
	public User(String username,String first_name, String last_name, String password,String gender, String date_of_birth, int role_id,String user_type) {
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.date_of_birth = date_of_birth;
		this.gender = gender;
		this.role_id = role_id;
		this.user_type = user_type;
		this.restaurant_id = -1;
		this.blocked = false;
	}
	//creates a user based on row from a file
	public User(String username, String first_name, String last_name, String password, String gender, String dob, int role_id, String user_type, int restaurant_id) {
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.gender = gender;
		this.date_of_birth = dob;
		this.role_id = role_id;
		this.user_type = user_type;
		this.restaurant_id = restaurant_id;
		this.blocked = false;
	}
	//creates a user from array fields
	public User(String[] fields) {
		this.username = fields[0];
		this.first_name = fields[1];
		this.last_name = fields[2];
		this.password = fields[3];
		this.gender = fields[4];
		this.date_of_birth = fields[5];
		this.role_id = Integer.parseInt(fields[6]);
		this.user_type = fields[7];
		this.restaurant_id = Integer.parseInt(fields[8]);
		this.blocked = Boolean.valueOf(fields[9]);
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getFirstName() {
		return this.first_name;
	}
	public String getLastName() {
		return this.last_name;
	}
	public String getDateOfBirth() {
		return this.date_of_birth;
	}
	public int getRoleID() {
		return this.role_id;
	}
	public String getGender() {
		return this.gender;
	}
	public String getPassword() {
		return this.password;
	}
	public String getUserType() {
		return this.user_type;
	}
	public String getRoleTitle() {
		return this.role_title;
	}
	public int getRestaurantID() {
		return this.restaurant_id;
	}
	public boolean getBlocked() {
		return this.blocked;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	public void setDateOfBirth(String dob) {
		this.date_of_birth = dob;
	}
	public void setRoleId(int role_id) {
		this.role_id = role_id;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setPassword(String password) {
		this.gender = password;
	}
	public void setUserType(String user_type) {
		this.user_type = user_type;
	}
	public void setRoleTitle(String role_title) {
		this.role_title = role_title;
	}
	public void setRestaurandID(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}