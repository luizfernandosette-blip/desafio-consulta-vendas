package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.projections.SummaryProjection;

public class SummaryDTO {
 
	private String sellerName;
	private Double total;
	
	
	public SummaryDTO() {
		
	}
	
	
	public SummaryDTO(String sellerName, Double total) {
		super();
		this.sellerName = sellerName;
		this.total = total;
	}



	public SummaryDTO(SummaryProjection projection) {
		total = projection.getTotal();
		sellerName = projection.getSellerName();
	}


	public String getSellerName() {
		return sellerName;
	}


	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
