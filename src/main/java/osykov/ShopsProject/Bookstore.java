package osykov.ShopsProject;

public class Bookstore extends Store {
	private static Bookstore instance = null;
	
	private Bookstore(int id) {
		this.id = id;
	}
	
	public static synchronized Bookstore getInstance() {
		if (instance == null) {
			instance = new Bookstore(1);
		} 
		
		return instance;
	}
}
