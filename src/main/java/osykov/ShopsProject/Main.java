package osykov.ShopsProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main 
{	
    public static void main( String[] args ) throws SQLException, InterruptedException, ExecutionException
    {
    	StoreFactory storeFactory = new  StoreFactory();
    	final Store bookstore = storeFactory.getStore("Bookstore");
    	final Store gamestore = storeFactory.getStore("Gamestore");   	
    	Callable<String> callable1 = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				manipulateWithStore(bookstore);
				return "Bookstore thread is finished";
			}
		};
		
		Callable<String> callable2 = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				manipulateWithStore(gamestore);
				return "Gamestore thread is finished";
			}
		};
		ExecutorService service = Executors.newFixedThreadPool(2);
		List<Future<String>> futures = new ArrayList<Future<String>>();
		
		List<String> results = new ArrayList<>();
		try {
			futures.add(service.submit(callable1));
			Thread.sleep(10000);
			futures.add(service.submit(callable2));
			for (Future<String> future : futures) {
				results.add(future.get());
			}
		} finally {
			service.shutdown();
		}
		
		for (String res : results) {
			System.out.println(res);
		}
    }
    
    public static void manipulateWithStore(Store store) throws SQLException {
    	for (Integer category : store.getCategoryIds()) {
    		for (int i = 0; i < 4; i++) {
    			Product p = new Product(1);
    			p.setTitle("Product" + i);
    			p.setPrice(4. * (i + 1));
    			p.setStatus(Status.AVAILABLE);
    			p.setCategoryId(category);
    			store.addProduct(p);
    		}
    	}
    	
    	List<Product> products = store.getProducts();
    	Boolean prevChanged = false;
    	
    	for (int i = 0; i < products.size(); i++) {
    		if (products.get(i).getCategoryId() == 1) {
    			store.setStatusToProduct(products.get(i).getId(), Status.ABSENT);
    		} else if (!prevChanged) {
    			store.setStatusToProduct(products.get(i).getId(), Status.EXPECTED);
    			prevChanged = true;
    		} else {
    			prevChanged = false;
    		}
    	}
    	
    	products = store.getProducts();
    	
    	for (Product p : products) {
    		if (p.getStatus() == Status.AVAILABLE) {
    			p.setPrice(p.getPrice() * 1.2);
    		}
    	}
    }
}
