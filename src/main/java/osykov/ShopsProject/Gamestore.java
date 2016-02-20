package osykov.ShopsProject;

public class Gamestore extends Store {
	private static Gamestore instance = null;
	
	private Gamestore(int id) {
		this.id = id;
	}
	
	public static synchronized Gamestore getInstance() {
		if (instance == null) {
			instance = new Gamestore(2);
		} 
		
		return instance;
	}
}
