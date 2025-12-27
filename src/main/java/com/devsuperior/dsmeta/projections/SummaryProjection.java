package com.devsuperior.dsmeta.projections;

import java.time.LocalDate;

public interface SummaryProjection {

		//select sale.id, sale.date, sale.amount, seller.name as sellerName
	String getSellerName();
	Double getTotal();
	
	
	
}
