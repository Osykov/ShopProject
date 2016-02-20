package osykov.ShopsProject;

public class Product {
	private final int id;
	private String title;
	private double price;
	private int categoryId;
	private Status status;
	
	public Product(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title =title;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public Status getStatus() {
		return status;
	}
	
	public int getId() {
		return id;
	}
}
