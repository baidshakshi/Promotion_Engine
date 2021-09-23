package com.demo.PromotionEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
	Connection con = null;

	public ProductRepository() {

		String url = "jdbc:mysql://localhost:3306/promotion_engine";
		String username = "root";
		String password = "root123";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public int createProduct(Product p) {
		String query = "insert into product(productname,price) values(?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, p.getProductName());
			ps.setDouble(2, p.getPrice());
			ps.executeUpdate();
			query="select productid from product where productname=? and price=?";
			ps = con.prepareStatement(query);
			ps.setString(1, p.getProductName());
			ps.setDouble(2, p.getPrice());
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Product findByName(String pName) {
		// TODO Auto-generated method stub
		Product p = null;
		String query = "select * from product where productname=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, pName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				p = new Product();
				p.setProductId(rs.getInt(1));
				p.setProductName(rs.getString(2));
				p.setPrice(rs.getDouble(3));
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public Product deleteByName(String pName) {
		// TODO Auto-generated method stub
		Product p = null;
		try {
			p = findByName(pName);
			if (p != null) {
				String query = "delete from product where productname=?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, pName);
				ps.executeUpdate();
				query = "select count(*) from product where productname=?";
				ps = con.prepareStatement(query);
				ps.setString(1, pName);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return new Product();
				}
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public Product updateProductName(String pName, String pNewName) {
		Product p = null;
		p = findByName(pName);
		try {
			if (p != null) {
				String query = "update product set productname=? where productname=?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, pNewName);
				ps.setString(2, pName);
				ps.executeUpdate();
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		List<Product> products = new ArrayList<Product>();
		String query = "select * from product";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setProductId(rs.getInt(1));
				p.setProductName(rs.getString(2));
				p.setPrice(rs.getDouble(3));
				products.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
}