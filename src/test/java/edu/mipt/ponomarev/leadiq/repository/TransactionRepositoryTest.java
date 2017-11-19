package edu.mipt.ponomarev.leadiq.repository;

import edu.mipt.ponomarev.leadiq.dto.TransactionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {
	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void putTransaction() throws Exception {
		TransactionDto dto = new TransactionDto();
		dto.setAmount(BigDecimal.TEN);
		dto.setType("test");
		transactionRepository.putTransaction(1, dto);
		dto.setParentId(1L);
		dto.setType("child");
		transactionRepository.putTransaction(2, dto);
	}

}