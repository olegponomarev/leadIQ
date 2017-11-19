package edu.mipt.ponomarev.leadiq.dto;

import java.math.BigDecimal;

public class SumDto {
	private BigDecimal sum;

	public SumDto(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getSum() {
		return sum;
	}
}
