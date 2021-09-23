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

@Path("promotion")
public class PromotionResource {
	PromotionRepository promoRepo = new PromotionRepository();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Promotion> getPromotion() {
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions = promoRepo.getPromotions();
		return promotions;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createPromotion(List<Promotion> p) {
		int id=promoRepo.createPromotion(p);
		if(id!=0)
			return "Promotion with promotionId= "+id+" is created!";
		else
			return "Promtion creation failed with an error!";
	}

	@GET
	@Path("/{promotionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Promotion> getPromotionById(@PathParam("promotionId") int promotionId) {
		List<Promotion> p = promoRepo.findById(promotionId);
		return p;
	}
	
	@DELETE
	@Path("/{promotionId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePromotion(@PathParam("promotionId") int promotionId) {
		List<Promotion> p=promoRepo.deletePromotion(promotionId);
		if(p!=null)
			return promotionId+" deleted!";
		else
			return "Invalid PromotionID";
	}

}