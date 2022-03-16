package bean;

public class Restaurant {
	private int restaurant_id;
	private String name;
	private int type_id;
	private boolean active;
	private String city;
	private String address;
	private String zip_code;
	private String logo_path;

	public Restaurant(int restaurant_id, String name, int type_id, boolean active, String city, String address,
			String zip_code, String logo_path) {
		this.restaurant_id = restaurant_id;
		this.name = name;
		this.type_id = type_id;
		this.active = active;
		this.city = city;
		this.address = address;
		this.zip_code = zip_code;
		this.logo_path = logo_path;

	}

	public Restaurant(String[] fields) {
		this.restaurant_id = Integer.parseInt(fields[0]);
		this.name = fields[1];
		this.type_id = Integer.parseInt(fields[2]);
		this.active = Boolean.valueOf(fields[3]);
		this.city = fields[4];
		this.address = fields[5];
		this.zip_code = fields[6];
		this.logo_path = fields[7];
	}

	public int getID() {
		return this.restaurant_id;
	}

	public String getName() {
		return this.name;
	}

	public int getType() {
		return this.type_id;
	}

	public boolean getActive() {
		return this.active;
	}

	public String getCity() {
		return this.city;
	}

	public String getAddress() {
		return this.address;
	}

	public String getLogo() {
		return this.logo_path;
	}

	public String getZipCode() {
		return this.zip_code;
	}

	
	public void setID(int id) {
		this.restaurant_id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(int type) {
		this.type_id = type;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLogo(String logo) {
		this.logo_path = logo;
	}

	public void setZipCode(String zip_code) {
		this.zip_code = zip_code;
	}

	

	@Override
	  public boolean equals(Object v) {
	        boolean retVal = false;

	        if (v instanceof Restaurant){
	            retVal = ((Restaurant) v).getID() == ((Restaurant) this).getID();
	        }

	     return retVal;
	  }
	@Override 
	public String toString() {
		return this.getID() + "," + this.getName() + "," + this.getType() + "," + this.getActive() + "," + this.getCity() + ","
				+ this.getAddress() + "," + this.getZipCode() + "," + this.getLogo();
	}
}
