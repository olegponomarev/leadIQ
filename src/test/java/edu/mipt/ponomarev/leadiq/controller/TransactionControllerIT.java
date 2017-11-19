package edu.mipt.ponomarev.leadiq.controller;

import com.google.gson.Gson;
import edu.mipt.ponomarev.leadiq.dto.SumDto;
import edu.mipt.ponomarev.leadiq.dto.TransactionDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionControllerIT {
	private static final String url = "http://localhost:8080/transactionservice";

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Autowired
	private Gson gson;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	private void create(TransactionDto dto, long id) throws Exception {
		mvc.perform(
				put(String.format("%s/%s/%s", url, "transaction", id))
						.content(gson.toJson(dto))
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
	}

	@Test
	public void putAndGet() throws Exception {
		TransactionDto dto = new TransactionDto();
		dto.setType("test");
		dto.setAmount(BigDecimal.TEN);
		final int id = 1;
		create(dto, id);

		final MvcResult mvcResult = mvc.perform(
				get(String.format("%s/%s/%s", url, "transaction", id))
		).andReturn();

		final TransactionDto result = gson.fromJson(mvcResult.getResponse().getContentAsString(), TransactionDto.class);

		assertNotNull(result);
		assertEquals(dto.getAmount(), result.getAmount());
	}

	@Test
	public void getByType() throws Exception {
		TransactionDto dto = new TransactionDto();
		dto.setType("test");
		dto.setAmount(BigDecimal.TEN);
		final long id = -1;
		create(dto, id);

		final MvcResult mvcResult = mvc.perform(
				get(String.format("%s/%s/%s", url, "types", "test"))
		).andReturn();

		final Collection result = gson
				.fromJson(mvcResult.getResponse().getContentAsString(), Collection.class);
		assertFalse(result.isEmpty());
		assertTrue(result.contains((double) id));
	}

	@Test
	public void getSum() throws Exception {
		TransactionDto dto1 = new TransactionDto();
		final BigDecimal amount1 = new BigDecimal("5000");
		dto1.setAmount(amount1);
		dto1.setType("cars");
		create(dto1, 10);

		TransactionDto dto2 = new TransactionDto();
		final BigDecimal amount2 = new BigDecimal("10000");
		dto2.setAmount(amount2);
		dto2.setType("shopping");
		dto2.setParentId(10L);
		create(dto2, 11);

		final MvcResult mvcResult1 = mvc.perform(
				get(String.format("%s/%s/%s", url, "sum", 10))
		).andReturn();
		final SumDto sumDto1 = gson.fromJson(mvcResult1.getResponse().getContentAsString(), SumDto.class);
		assertTrue(sumDto1.getSum().compareTo(amount1.add(amount2)) == 0);

		final MvcResult mvcResult2 = mvc.perform(
				get(String.format("%s/%s/%s", url, "sum", 11))
		).andReturn();
		final SumDto sumDto2 = gson.fromJson(mvcResult2.getResponse().getContentAsString(), SumDto.class);
		assertTrue(sumDto2.getSum().compareTo(amount2) == 0);
	}

}