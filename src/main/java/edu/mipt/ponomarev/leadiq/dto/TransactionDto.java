package edu.mipt.ponomarev.leadiq.dto;

import edu.mipt.ponomarev.leadiq.model.Transaction;

import java.math.BigDecimal;

public class TransactionDto {
	private BigDecimal amount;
	private String type; // enum is better since we'd like to control types
	private Long parentId;

	public TransactionDto() {
	}

	public TransactionDto(Transaction transaction) {
		this.amount = transaction.getAmount();
		this.type = transaction.getType();
		this.parentId = transaction.getParentId();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
