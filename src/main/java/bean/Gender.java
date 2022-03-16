package bean;

public class Gender {
	private String gender_id;
	private String gender_name;
	
	public Gender(String gender_id,String gender_name) {
		this.gender_id = gender_id;
		this.gender_name = gender_name;
	}
	
	public String getGenderID() {
		return gender_id;
	}
	public String getGenderName() {
		return gender_name;
	}
	public void setGenderID(String gender_id) {
		this.gender_id = gender_id;
	}
	public void setGenderName(String gender_name) {
		this.gender_name = gender_name;
	}
	
}
