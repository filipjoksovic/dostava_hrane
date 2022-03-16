package bean;

public class Role {
	private int role_id;
	private String role_title;
	
	public Role(int role_id, String role_title) {
		this.role_id = role_id;
		this.role_title = role_title;
	}
	public int getRoleID() {
		return this.role_id;
	}
	public String getRoleTitle() {
		return this.role_title;
	}
	public void setRoleID(int role_id) {
		this.role_id = role_id;
	}
	public void setRoleTitle(String role_title) {
		this.role_title = role_title;
	}
}
