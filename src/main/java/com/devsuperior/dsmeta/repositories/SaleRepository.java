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
	
	
	@Query(nativeQuery = true, value = 
	"SELECT s.id, s.date, s.amount, sel.name AS sellerName FROM TB_SALES s "
	+ "INNER JOIN TB_SELLER sel ON s.seller_id = sel.id WHERE s.date "
	+ "BETWEEN :dataInicial AND :dataFinal AND sel.name ILIKE (CONCAT('%',:name,'%'))",
	countQuery = "SELECT count(s.id) FROM TB_SALES s inner join tb_Seller sel on s.seller_id = sel.id WHERE s.date "
			+ "BETWEEN :dataInicial AND :dataFinal AND sel.name ILIKE (CONCAT('%',:name,'%'))")
	Page<ReportProjection> getReport(LocalDate dataInicial, LocalDate dataFinal, String name, Pageable pageable);
	
		
}
