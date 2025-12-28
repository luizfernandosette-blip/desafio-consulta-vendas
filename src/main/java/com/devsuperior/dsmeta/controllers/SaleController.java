package com.devsuperior.dsmeta.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	
	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummaryDTO>> getSummary(@RequestParam(value="minDate" , required = false) String minDate,
			@RequestParam(value="maxDate" , required = false) String maxDate) {
		
		if (minDate != null && maxDate != null) {
			try {
				LocalDate dataInicial = LocalDate.parse(minDate);
				LocalDate dataFinal = LocalDate.parse(maxDate);
				
				List<SummaryDTO> listDTO = service.getSummary(dataInicial, dataFinal);
				return ResponseEntity.ok(listDTO);
						
			}catch(DateTimeParseException e) {
				return ResponseEntity.badRequest().build();
			}
		}else {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate minusOneYear = today.minusYears(1L);
			List<SummaryDTO> listDTO = service.getSummary(minusOneYear, today);
			return ResponseEntity.ok(listDTO);
		}
		
			
	}
		
	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportDTO>> getReport(@RequestParam(value="minDate" , required = false) String minDate,
			@RequestParam(value="maxDate" , required = false) String maxDate,
			@RequestParam(value="name" , required = false) String name, Pageable pageable){
			
		if (minDate != null && maxDate != null) {
			try {
				LocalDate dataInicial = LocalDate.parse(minDate);
				LocalDate dataFinal = LocalDate.parse(maxDate);
				if (name == null) { name = "";}
				Page<ReportDTO> listDTO = service.getReport(dataInicial, dataFinal, name, pageable);
				return ResponseEntity.ok(listDTO);
						
			}catch(DateTimeParseException e) {
				return ResponseEntity.badRequest().build();
			}
		}else {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate minusOneYear = today.minusYears(1L);
			Page<ReportDTO> reportDTO = service.getReport(minusOneYear, today, "", pageable);
			return ResponseEntity.ok(reportDTO);
		}
		
			
	}
}
