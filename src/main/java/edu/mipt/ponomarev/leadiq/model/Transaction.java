package edu.mipt.ponomarev.leadiq.model;

import edu.mipt.ponomarev.leadiq.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//could be marked as entity
public class Transaction {
	private long id;
	private BigDecimal amount;
	private String type; // enum is better since we'd like to control types
	private Long parentId;
	private volatile List<Transaction> children;

	public Transaction(long id, TransactionDto dto) {
		this.id = id;
		this.amount = dto.getAmount();
		this.type = dto.getType();
		this.parentId = dto.getParentId();
	}

	public long getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void putChild(Transaction transaction) {
		if (!Objects.equals(this.id, transaction.parentId)) {
			throw new RuntimeException();
		}
		synchronized (this) {
			if (children == null) {
				children = new ArrayList<>();
			}
			children.add(transaction);
		}
	}

	public List<Transaction> getChildren() {
		return children == null ? null : new ArrayList<>(children);
	}
}
