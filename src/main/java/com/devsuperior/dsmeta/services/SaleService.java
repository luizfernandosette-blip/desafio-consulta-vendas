package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	
	public List<SummaryDTO> getSummary(LocalDate dataInicial, LocalDate dataFinal){
		List<SummaryProjection> list = repository.getSummary(dataInicial, dataFinal);
		List<SummaryDTO> resultDTO = list.stream().map(x -> new SummaryDTO(x)).collect(Collectors.toList());
		return resultDTO;
	}
	
		
	public Page<ReportDTO> getReport(LocalDate dataInicial, LocalDate dataFinal, String name, Pageable pageable){
		
		Page<ReportProjection> report = repository.getReport(dataInicial, dataFinal, name, pageable);
		
		return report.map(x -> new ReportDTO(x));
	}
	
	
		
}
