package edu.mipt.ponomarev.leadiq.repository;

import edu.mipt.ponomarev.leadiq.dto.TransactionDto;
import edu.mipt.ponomarev.leadiq.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {
	private final ConcurrentHashMap<Long, Transaction> transactions = new ConcurrentHashMap<>();

	public void putTransaction(long id, TransactionDto dto) {
		final Transaction entity = new Transaction(id, dto);
		transactions.put(id, entity);
		if (dto.getParentId() != null) {
			transactions.computeIfPresent(dto.getParentId(), (k, v) -> {
				v.putChild(entity);
				return v;
			});
		}
	}

	public TransactionDto getTransactionById(long id) {
		final Transaction transaction = transactions.get(id);
		return transaction == null ? null : new TransactionDto(transaction);
	}

	public Collection<Long> getTransactionIdsByType(String type) {
		return transactions
				.values()
				.stream()
				.filter(t -> Objects.equals(type, t.getType()))
				.map(Transaction::getId)
				.collect(Collectors.toSet());
	}

	public BigDecimal getSumByParentTransactionId(long id) {
		final Transaction transaction = transactions.get(id);
		if (transaction == null) {
			return null;
		}
		BigDecimal count = transaction.getAmount();
		final List<Transaction> children = transaction.getChildren();
		if (children == null) {
			return count;
		}
		return children
				.stream()
				.map(Transaction::getAmount)
				.reduce(count, BigDecimal::add);
	}
}
