package com.test;

import org.junit.Assert;
import org.junit.Test;
import com.demo.PromotionEngine.Product;
import com.demo.PromotionEngine.ProductRepository;

public class ProductResourceTest {
	ProductRepository productRepo = new ProductRepository();

	// TODO Auto-generated method stub

	public void createProduct() {
		Product p = new Product();
		p.setPrice(90.0);
		p.setProductName("TestProduct");
		p.setProductId(productRepo.createProduct(p));
		Assert.assertFalse(p.getProductId()==0);
	}

	public void getProductByName() {
		Product p = new Product();
		String pName = "TestProduct";
		p = productRepo.findByName(pName);
		Assert.assertEquals(pName, p.getProductName());
	}

	@Test(expected = NullPointerException.class)
	public void getProductByInvalidName() {
		Product p = new Product();
		String pName = "TestInvalidProduct";
		p = productRepo.findByName(pName);
		Assert.assertEquals(pName, p.getProductName());
	}

	public void deleteProductByName() {
		Product p = new Product();
		String pName = "TestProducts";
		p = productRepo.deleteByName(pName);
		Assert.assertNotNull(p);
	}

	public void updateProductName() {
		Product p = new Product();
		String pName = "TestProduct";
		String pNewName = "TestProducts";
		p = productRepo.updateProductName(pName, pNewName);
		Assert.assertNotNull(p);
	}

	@Test(expected = AssertionError.class)
	public void updateInvalidProductName() {
		Product p = new Product();
		String pName = "TestInvalidProduct";
		String pNewName = "TestProduct";
		p = productRepo.updateProductName(pName, pNewName);
		Assert.assertNotNull(p);
	}

	@Test(expected = AssertionError.class)
	public void deleteProductByInvalidName() {
		Product p = new Product();
		String pName = "TestInvalidProduct";
		p = productRepo.deleteByName(pName);
		Assert.assertNotNull(p);
	}

	@Test
	public void CrudTest() {
		createProduct();
		getProductByName();
		updateProductName();
		deleteProductByName();
	}
}
