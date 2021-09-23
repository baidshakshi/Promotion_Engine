package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.demo.PromotionEngine.Product;
import com.demo.PromotionEngine.ProductRepository;
import com.demo.PromotionEngine.Promotion;
import com.demo.PromotionEngine.PromotionRepository;

public class PromotionResourceTest {
	PromotionRepository promotionRepo = new PromotionRepository();
	ProductRepository productRepo = new ProductRepository();

	List<Promotion> promotions = new ArrayList<Promotion>();

	public void createPromotion() {
		Promotion p = new Promotion();
		Product prod = new Product();
		prod.setPrice(90.0);
		prod.setProductName("TestProduct");
		prod.setProductId(productRepo.createProduct(prod));
		p.setProductId(prod.getProductId());
		p.setPromotionType("Flat");
		p.setDiscount(30);
		p.setQuantity(2);

		promotions.add(p);
		p.setPromotionId(promotionRepo.createPromotion(promotions));
		Assert.assertFalse(p.getPromotionId() == 0);

	}

	public void getPromotionById() {
		promotions = promotionRepo.findById(promotions.get(0).getPromotionId());
		Assert.assertNotNull(promotions);
	}

	@Test(expected = AssertionError.class)
	public void getPromotionByInvalidId() {
		List<Promotion> promo = promotionRepo.findById(-1);
		Assert.assertNotNull(promo);
	}

	public void deletePromotion() {

		promotions = promotionRepo.deletePromotion(promotions.get(0).getPromotionId());
		Assert.assertNotNull(promotions);
	}

	@Test(expected = AssertionError.class)
	public void deletePromotionByInvalidId() {
		List<Promotion> promo = promotionRepo.deletePromotion(-1);
		Assert.assertNotNull(promo);
	}

	@Test
	public void CrudTest() {
		createPromotion();
		getPromotionById();
		deletePromotion();
		productRepo.deleteByName("TestProduct");
	}

}
