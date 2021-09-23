package com.demo.PromotionEngine;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("checkout")
public class CartResource {

	CartRepository cartRepo = new CartRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getPrice(List<Cart> c) {
		double price=cartRepo.testCalculatePrice(c);
		if(price==0.0)
			return "Price could not be calculated. Please check your cart!";
		else
			return "Total Price= "+price;
		
	}

}
