package edu.mipt.ponomarev.leadiq.service;

import edu.mipt.ponomarev.leadiq.dto.SumDto;
import edu.mipt.ponomarev.leadiq.dto.TransactionDto;
import edu.mipt.ponomarev.leadiq.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void putTransaction(long id, TransactionDto transaction) {
		transactionRepository.putTransaction(id, transaction);
	}

	public TransactionDto getTransaction(long id) {
		return transactionRepository.getTransactionById(id);
	}

	public Collection<Long> getTransactionIdsByType(String type) {
		return transactionRepository.getTransactionIdsByType(type);
	}

	public SumDto getSumByParentTransactionId(long id) {
		return new SumDto(transactionRepository.getSumByParentTransactionId(id));
	}
}
