package com.demo.PromotionEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartRepository {
	Connection con = null;
	PromotionRepository promoRepo = new PromotionRepository();
	ProductRepository productRepo = new ProductRepository();

	public CartRepository() {

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

	public double calculatePrice(List<Cart> carts) {
		double price = 0.0;
		List<Product> products = productRepo.getProducts();
		Map<Integer, Double> productPrice = new HashMap<Integer, Double>();
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			productPrice.put(p.getProductId(), p.getPrice());
		}
		try {
			for (int i = 0; i < carts.size(); i++) {
				int cartQuantity = carts.get(i).getQuantity();
				int cartProductId = carts.get(i).getProductId();
				String query = "select count(*) from promotion where promotionid in (select promotionid from promotion where productid=?)";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, cartProductId);
				ResultSet rsCount = ps.executeQuery();
				double promotionPrice = 0.0;
				if (rsCount.next()) {
					query = "select * from promotion where promotionid in (select promotionid from promotion where productid=?)";
					ps = con.prepareStatement(query);
					ps.setInt(1, cartProductId);
					ResultSet rs = ps.executeQuery();
					boolean flag = false;
					int quantity[] = new int[rsCount.getInt(1)];
					int productId[] = new int[rsCount.getInt(1)];
					int count = 0;
					while (rs.next()) {
						promotionPrice += rs.getDouble(4);
						flag = false;
						quantity[count] = rs.getInt(5);
						productId[count] = rs.getInt(2);
						for (int k = i; k < carts.size(); k++) {
							if (productId[count] == carts.get(k).getProductId()
									&& quantity[count] <= carts.get(k).getQuantity()) {
								flag = true;
								break;
							}

						}
						if (flag == false)
							break;
						count++;
					}
					if (flag) {
						for (int k = 0; k < rsCount.getInt(1); k++) {
							for (int j = i; j < carts.size(); j++) {
								int pQuantity = quantity[k];
								int cQuantity = carts.get(j).getQuantity();
								Double actualPrice = productPrice.get(productId[k]);
								if (carts.get(j).getProductId() == productId[k] && cQuantity >= quantity[k]) {
									price+=promotionPrice;
									carts.get(j).setQuantity(cQuantity-pQuantity);
									promotionPrice=0.0;
									break;
									
								}
							}
						}
						i--;

					} else {
						price += cartQuantity * productPrice.get(cartProductId);

					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return price;
	}

}
