package osykov.ShopsProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Store {
	protected int id;
	protected int name;
	protected List<Product> products;
	List<Integer> categoryIds;
	
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/shopsdatabase";
	private static final String LOGIN = "test";
	private static final String PASSWORD = "test";
	
	private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Products WHERE category_id IN (SELECT category_id FROM Categories WHERE shop_id = ?)";	
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO Products (title, price, category_id, status_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_STATUS_SQL = "UPDATE Products SET status_id = ? WHERE product_id = ?";
	private static final String UPDATE_PRICE_SQL = "UPDATE Products SET price = ? WHERE product_id = ?";
	private static final String SELECT_ALL_CATEGORIES = "SELECT category_id FROM Categories WHERE shop_id = ?";

	static {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find class " + DRIVER_CLASS_NAME);
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL, LOGIN, PASSWORD);
	}
	
	public List<Product> getProducts() throws SQLException {
		Connection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			st = conn.prepareStatement(SELECT_ALL_PRODUCTS);
			st.setInt(1, id);
			rs = st.executeQuery();
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("product_id");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				Integer categoryId = rs.getInt("category_id");
				Status status = Status.values()[rs.getInt("status_id") - 1];
				Product product = new Product(id);
				product.setTitle(title);
				product.setPrice(price);
				product.setCategoryId(categoryId);
				product.setStatus(status);
				list.add(product);
			}
			conn.commit();
			return list;
		} catch (SQLException ex) {
			JdbcUtils.rollbackQuietly(conn);
			throw ex;
		} finally {
			JdbcUtils.closeQuietly(rs);
			JdbcUtils.closeQuietly(st);
			JdbcUtils.closeQuietly(conn);
		}
	}
	
	public void addProduct(Product product) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_PRODUCT_SQL);
			ps.setString(1, product.getTitle());
			ps.setDouble(2, product.getPrice());
			ps.setInt(3, product.getCategoryId());
			ps.setInt(4, product.getStatus().getCode());
			ps.executeUpdate();
			
			conn.commit();
		} catch(SQLException e) {
			JdbcUtils.rollbackQuietly(conn);
			throw e;
		} finally {
			JdbcUtils.closeQuietly(rs);
			JdbcUtils.closeQuietly(ps);
			JdbcUtils.closeQuietly(conn);
		}
	}
	
	public void setStatusToProduct(Integer productId, Status status) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_STATUS_SQL);
			ps.setInt(1, status.getCode());
			ps.setInt(2, productId);
			ps.executeUpdate();
			
			conn.commit();
		} catch(SQLException e) {
			JdbcUtils.rollbackQuietly(conn);
		} finally {
			JdbcUtils.closeQuietly(rs);
			JdbcUtils.closeQuietly(ps);
			JdbcUtils.closeQuietly(conn);
		}
	}
	
	public void setPriceToProduct(Integer productId, Double price) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_PRICE_SQL);
			ps.setDouble(1, price);
			ps.setInt(2, productId);
			ps.executeUpdate();
			
			conn.commit();
		} catch(SQLException e) {
			JdbcUtils.rollbackQuietly(conn);
		} finally {
			JdbcUtils.closeQuietly(rs);
			JdbcUtils.closeQuietly(ps);
			JdbcUtils.closeQuietly(conn);
		}
	}

	public List<Integer> getCategoryIds() throws SQLException {
		Connection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			st = conn.prepareStatement(SELECT_ALL_CATEGORIES);
			st.setInt(1, id);
			rs = st.executeQuery();
			List<Integer> list = new ArrayList<>();
			while (rs.next()) {
				int id_category = rs.getInt("category_id");
				list.add(id_category);
			}
			conn.commit();
			return list;
		} catch (SQLException ex) {
			JdbcUtils.rollbackQuietly(conn);
			throw ex;
		} finally {
			JdbcUtils.closeQuietly(rs);
			JdbcUtils.closeQuietly(st);
			JdbcUtils.closeQuietly(conn);
		}
	}

}
