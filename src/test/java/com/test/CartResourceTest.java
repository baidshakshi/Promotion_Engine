package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.demo.PromotionEngine.Cart;
import com.demo.PromotionEngine.CartRepository;

public class CartResourceTest {

	CartRepository cartRepo = new CartRepository();
/*Assumption: 
 * The products with their prices:
 * Unit price for SKU IDs
	A 50
	B 30
	C 20
	D 15
 * The promotions:
	
	3 of A's for 130
	2 of B's for 45
	C & D for 30
	
	are already added in DB.
	ProductId ProductName
	4			A
	5			B
	6			C
	7			D
	*/
	
	
	
	//7 A in cart
	@Test
	public void getPriceApplyingPromotionForA() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(4);
		c.setQuantity(7);
		carts.add(c);
		Assert.assertTrue(cartRepo.calculatePrice(carts)==310.0);
	}
	//7 B in cart
	@Test
	public void getPriceApplyingPromotionForB() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(5);
		c.setQuantity(7);
		carts.add(c);
		Assert.assertTrue(cartRepo.calculatePrice(carts)==165.0);
	}
	
	// 1 C and 1 D in cart
	@Test
	public void getPriceApplyingPromotionForCAndD() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(6);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		c.setProductId(7);
		c.setQuantity(1);
		carts.add(c);
		Assert.assertTrue(cartRepo.calculatePrice(carts)==30.0);
	}
	
	//1 A, 1 B and 1 C in cart
	@Test
	public void getPriceCombination1() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(6);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		c.setProductId(5);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		c.setProductId(4);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		Assert.assertTrue(cartRepo.calculatePrice(carts)==100.0);
	}
	//5 A, 5 B and 1 C in cart
	@Test
	public void getPriceCombination2() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(6);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		c.setProductId(5);
		c.setQuantity(5);
		carts.add(c);
		c=new Cart();
		c.setProductId(4);
		c.setQuantity(5);
		carts.add(c);
		c=new Cart();
		Assert.assertTrue(cartRepo.calculatePrice(carts)==370.0);
	}
	//3 A, 5 B, 1 C and 1 D in cart
	@Test
	public void getPriceCombination3() {
		List<Cart> carts = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setProductId(6);
		c.setQuantity(1);
		carts.add(c);
		c=new Cart();
		c.setProductId(5);
		c.setQuantity(5);
		carts.add(c);
		c=new Cart();
		c.setProductId(4);
		c.setQuantity(3);
		carts.add(c);
		c=new Cart();
		c.setProductId(7);
		c.setQuantity(1);
		carts.add(c);
		Assert.assertTrue(cartRepo.calculatePrice(carts)==280.0);
	}

}
