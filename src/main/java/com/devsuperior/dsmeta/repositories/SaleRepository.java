package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(nativeQuery = true, value = "select seller.name as sellerName, SUM(sale.amount) as total  "
			+ "from tb_sales sale "
			+ "inner join tb_seller seller "
			+ "on sale.seller_id = seller.id "
			+ "where sale.date between :dataInicial and :dataFinal group by seller.name")
	List<SummaryProjection> getSummary(LocalDate dataInicial, LocalDate dataFinal);
	
	
	@Query(nativeQuery = true, value = "SELECT seller.id, sale.date, sale.amount, seller.name AS sellerName FROM TB_SALES sale "
			+ "INNER JOIN TB_SELLER seller "
			+ "ON sale.seller_id = seller.id "
			+ "where sale.date between :dataInicial and :dataFinal and seller.name like CONCAT('%', :name, '%') "
			+ "group by sale.date, sale.amount, seller.id")
	Page<ReportProjection> getReport(LocalDate dataInicial, LocalDate dataFinal, String name, Pageable pageable);
	
	
	
	@Query(nativeQuery = true, value = "SELECT seller.id, sale.date, sale.amount, seller.name AS sellerName FROM TB_SALES sale "
			+ "INNER JOIN TB_SELLER seller "
			+ "ON sale.seller_id = seller.id "
			+ "where sale.date between '2024-12-27' and '2025-12-27' "
			+ "group by sale.date, sale.amount, seller.id")
	Page<ReportProjection> getReport(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);
}
