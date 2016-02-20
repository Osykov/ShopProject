package osykov.ShopsProject;

public class StoreFactory {
	public Store getStore(String storeType) {
		if (storeType.equalsIgnoreCase("Bookstore")) {
			return Bookstore.getInstance();
		} 
		
		if (storeType.equalsIgnoreCase("Gamestore")) {
			return Gamestore.getInstance();
		}
		
		return null;
	}
}
