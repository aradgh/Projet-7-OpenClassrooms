package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	private Trade createTrade(String account, String type) {
		Trade trade = new Trade(account, type);
		trade.setBuyQuantity(BigDecimal.valueOf(100.00));
		return tradeRepository.save(trade);
	}

	@Test
	public void shouldSaveTrade() {
		Trade trade = createTrade("Trade Account", "Type");

		assertThat(trade.getAccount()).isEqualTo("Trade Account");
	}

	@Test
	public void shouldUpdateTrade() {
		Trade trade = createTrade("Trade Account", "Type");
		trade.setAccount("Updated Account");

		Trade updated = tradeRepository.save(trade);

		assertThat(updated.getAccount()).isEqualTo("Updated Account");
	}

	@Test
	public void shouldFindTradeById() {
		Trade trade = createTrade("Trade Account", "Type");

		Optional<Trade> result = tradeRepository.findById(trade.getTradeId());

		assertThat(result).isPresent();
		assertThat(result.get().getAccount()).isEqualTo("Trade Account");
	}

	@Test
	public void shouldDeleteTrade() {
		Trade trade = createTrade("Trade Account", "Type");
		int id = trade.getTradeId();

		tradeRepository.delete(trade);
		Optional<Trade> result = tradeRepository.findById(id);

		assertThat(result).isNotPresent();
	}
}
