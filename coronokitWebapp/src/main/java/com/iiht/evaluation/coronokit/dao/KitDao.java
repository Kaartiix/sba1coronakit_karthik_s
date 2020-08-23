package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iiht.evaluation.coronokit.model.KitDetail;

public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public static final String INSRT_MY_PROD_QRY = "INSERT INTO kit (id,coronakitId,productID,quantity,amount) VALUES (?,?,?,?,?)";
	//public static final String UPDT_PROD_QRY = "UPDATE products set productname=?,price=?,description=? WHERE productid=?";
	public static final String TOTAL_KIT_QRY = "SELECT MAX(coronakitId) FROM kit;";
	public static final String TOTAL_KIT_ID_QRY = "SELECT MAX(id) FROM kit;";

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public Integer getNextCoronaKitId() throws Exception {
		int nextCoronaKitId = 1;
		try {
			this.connect();
			PreparedStatement ps = jdbcConnection.prepareStatement(TOTAL_KIT_QRY);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nextCoronaKitId = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			this.disconnect();
			throw new Exception("An error occured while fetching you kit id");
		}
		return nextCoronaKitId;
	}

	public int getNextKitId() throws Exception {
		int nextKitId = 1;
		try {
			this.connect();
			PreparedStatement ps = jdbcConnection.prepareStatement(TOTAL_KIT_ID_QRY);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nextKitId = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			this.disconnect();
			throw new Exception("An error occured while fetching you kit details");
		}
		return nextKitId;
	}

	public boolean addKit(KitDetail kitDtails) throws Exception {
		boolean isAdded = false;
		if (kitDtails != null) {
			try {
				this.connect();
				PreparedStatement ps = jdbcConnection.prepareStatement(INSRT_MY_PROD_QRY);
				ps.setInt(1, kitDtails.getId());
				ps.setInt(2, kitDtails.getCoronaKitId());
				ps.setInt(3, kitDtails.getProductId());
				ps.setInt(4, kitDtails.getQuantity());
				ps.setDouble(5, kitDtails.getAmount());
				isAdded = ps.executeUpdate() > 0;

			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Adding kit failed.");
			}
		}
		return isAdded;
	}

}