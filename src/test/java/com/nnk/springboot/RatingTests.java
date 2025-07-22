package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void testSaveRating() {
		Rating rating = new Rating("Moodys A", "SandP A", "Fitch A", 5);
		Rating saved = ratingRepository.save(rating);

        assertEquals(5, saved.getOrderNumber());
	}

	@Test
	public void testUpdateRating() {
		Rating rating = new Rating("Moodys B", "SandP B", "Fitch B", 8);
		Rating saved = ratingRepository.save(rating);

		saved.setOrderNumber(12);
		Rating updated = ratingRepository.save(saved);

		assertEquals(12, updated.getOrderNumber());
	}

	@Test
	public void testFindAllRatings() {
		ratingRepository.save(new Rating("Moodys C", "SandP C", "Fitch C", 3));
		ratingRepository.save(new Rating("Moodys D", "SandP D", "Fitch D", 4));

		List<Rating> ratings = ratingRepository.findAll();

		assertTrue(ratings.size() >= 2);
	}

	@Test
	public void testDeleteRating() {
		Rating rating = new Rating("Moodys E", "SandP E", "Fitch E", 9);
		Rating saved = ratingRepository.save(rating);

		Integer id = saved.getId();
		ratingRepository.delete(saved);

		Optional<Rating> deleted = ratingRepository.findById(id);
		assertFalse(deleted.isPresent());
	}
}
