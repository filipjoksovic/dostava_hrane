package bean;

public class ProductType {
	private int id;
	private String name;
	
	public ProductType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public ProductType(String dataRow) {
		String[] fields = dataRow.split(",");
		this.id = Integer.parseInt(fields[0]);
		this.name = fields[1];
	}
	public int getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
}
