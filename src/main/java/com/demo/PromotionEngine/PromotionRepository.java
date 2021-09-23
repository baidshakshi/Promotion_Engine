package com.demo.PromotionEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {

	// TODO Auto-generated method stub
	Connection con = null;

	public PromotionRepository() {

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

	public int getNumberOfPromotions() {
		String query = "select count(*) from promotion";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int createPromotion(List<Promotion> promotions) {
		if (promotions.size() > 0) {
			try {
				int id = getNumberOfPromotions();
				if (id != -1) {

					for (int j = 0; j < promotions.size(); j++) {
						Promotion p = promotions.get(j);
						p.setPromotionId(id + 1);
						String query = "insert into promotion(productid,ptype,discount,quantity,promotionId) values(?,?,?,?,?)";
						PreparedStatement ps = con.prepareStatement(query);
						ps.setInt(1, p.getProductId());
						ps.setString(2, p.getPromotionType());
						ps.setDouble(3, p.getDiscount());
						ps.setInt(4, p.getQuantity());
						ps.setInt(5, p.getPromotionId());
						ps.executeUpdate();
					}
					return id + 1;
//					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;

	}

	public List<Promotion> deletePromotion(int promotionId) {
		List<Promotion> p = findById(promotionId);
		if (p != null) {
			try {
				String query = "delete from promotion where promotionid=?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, promotionId);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public List<Promotion> findById(int promotionId) {
		List<Promotion> promotion = new ArrayList<Promotion>();
		String query = "select * from promotion where promotionid=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, promotionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				do {
					Promotion p = new Promotion();
					p.setPromotionId(rs.getInt(1));
					p.setProductId(rs.getInt(2));
					p.setPromotionType(rs.getString(3));
					p.setDiscount(rs.getDouble(4));
					p.setQuantity(rs.getInt(5));
					promotion.add(p);
				} while (rs.next());
			} else {
				promotion = null;
			}
			return promotion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promotion;
	}

	public List<Promotion> getPromotions() {
		List<Promotion> promo = new ArrayList<Promotion>();
		String query = "select * from promotion";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Promotion p = new Promotion();
				p.setPromotionId(rs.getInt(1));
				p.setProductId(rs.getInt(2));
				p.setPromotionType(rs.getString(3));
				p.setDiscount(rs.getDouble(4));
				p.setQuantity(rs.getInt(5));
				promo.add(p);
			}
			return promo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promo;
	}

}