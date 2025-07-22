package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@BeforeEach
	void cleanUp() {
		bidListRepository.deleteAll();
	}

	private BidList createBid(String account, String type, BigDecimal quantity) {
		return new BidList(account, type, quantity);
	}

	@Test
	@DisplayName("Save a new BidList entity")
	void testSaveBidList() {
		BidList bid = createBid("Account Test", "Type Test", BigDecimal.valueOf(10));
		BidList savedBid = bidListRepository.save(bid);

        assertEquals(0, savedBid.getBidQuantity().compareTo(BigDecimal.valueOf(10)));
	}

	@Test
	@DisplayName("Update existing BidList entity")
	void testUpdateBidList() {
		BidList bid = bidListRepository.save(createBid("Account Test", "Type Test", BigDecimal.valueOf(10)));

		bid.setBidQuantity(BigDecimal.valueOf(20));
		BidList updatedBid = bidListRepository.save(bid);

		assertEquals(0, updatedBid.getBidQuantity().compareTo(BigDecimal.valueOf(20)));
	}

	@Test
	@DisplayName("Find all BidList entities")
	void testFindAllBidLists() {
		bidListRepository.save(createBid("Account Test 1", "Type Test 1", BigDecimal.valueOf(10)));
		bidListRepository.save(createBid("Account Test 2", "Type Test 2", BigDecimal.valueOf(15)));

		List<BidList> bids = bidListRepository.findAll();
		assertFalse(bids.isEmpty());
		assertTrue(bids.size() >= 2);
	}

	@Test
	@DisplayName("Delete a BidList entity")
	void testDeleteBidList() {
		BidList bid = bidListRepository.save(createBid("Account Test", "Type Test", BigDecimal.valueOf(10)));
		Integer id = bid.getBidListId();

		bidListRepository.delete(bid);
		Optional<BidList> deletedBid = bidListRepository.findById(id);
		assertFalse(deletedBid.isPresent());
	}
}
