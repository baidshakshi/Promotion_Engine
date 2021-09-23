	package com.demo.PromotionEngine;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("product")
public class ProductResource {
	ProductRepository productRepo = new ProductRepository();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProduct() {
		List<Product> products = new ArrayList<Product>();
		products = productRepo.getProducts();
		return products;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProduct(Product p) {
		int id=productRepo.createProduct(p);
		return "Product with productID= "+id+" is created!";
	}

	@GET
	@Path("/{productName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProductByName(@PathParam("productName") String productName) {
		Product p = productRepo.findByName(productName);
		return p;
	}
	
	@DELETE
	@Path("/{productName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProductByName(@PathParam("productName") String productName) {
		Product p=productRepo.deleteByName(productName);
		if(p!=null)
			return productName+" deleted!";
		else
			return "Invalid ProductName";
	}

}
