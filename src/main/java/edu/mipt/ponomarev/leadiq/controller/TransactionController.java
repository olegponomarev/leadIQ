package edu.mipt.ponomarev.leadiq.controller;

import edu.mipt.ponomarev.leadiq.dto.SumDto;
import edu.mipt.ponomarev.leadiq.dto.TransactionDto;
import edu.mipt.ponomarev.leadiq.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("transactionservice")
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	//IMHO, the server is to assign the id, not a client. But I have to maintain this API
	@PutMapping(value = "transaction/{id}", consumes = APPLICATION_JSON_VALUE)
	public void putTransaction(@PathVariable long id, @RequestBody TransactionDto transaction) {
		transactionService.putTransaction(id, transaction);
	}

	@GetMapping(value = "transaction/{id}", produces = APPLICATION_JSON_VALUE)
	public TransactionDto getTransaction(@PathVariable long id) {
		return transactionService.getTransaction(id);
	}

	@GetMapping(value = "types/{type}", produces = APPLICATION_JSON_VALUE)
	public Collection<Long> getTransactionIdsByType(@PathVariable String type) {
		return transactionService.getTransactionIdsByType(type);
	}

	@GetMapping(value = "sum/{id}", produces = APPLICATION_JSON_VALUE)
	public SumDto getSumByParentTransactionId(@PathVariable long id) {
		return transactionService.getSumByParentTransactionId(id);
	}
}
