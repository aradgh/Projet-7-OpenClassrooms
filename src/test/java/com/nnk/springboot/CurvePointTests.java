package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.*;
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
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@BeforeEach
	void cleanUp() {
		curvePointRepository.deleteAll();
	}

	@Test
	@DisplayName("Save a new CurvePoint")
	void testSaveCurvePoint() {
		CurvePoint curvePoint = new CurvePoint(10, new BigDecimal("10"), new BigDecimal("30"));
		CurvePoint saved = curvePointRepository.save(curvePoint);

		assertNotNull(saved.getId());
		assertEquals(10, saved.getCurveId());
		assertEquals(0, saved.getTerm().compareTo(new BigDecimal("10")));
		assertEquals(0, saved.getCurveValue().compareTo(new BigDecimal("30")));
	}

	@Test
	@DisplayName("Update an existing CurvePoint")
	void testUpdateCurvePoint() {
		CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(10, new BigDecimal("10"), new BigDecimal("30")));

		curvePoint.setCurveId(20);
		CurvePoint updated = curvePointRepository.save(curvePoint);

		assertEquals(20, updated.getCurveId());
	}

	@Test
	@DisplayName("Find all CurvePoints")
	void testFindAllCurvePoints() {
		curvePointRepository.save(new CurvePoint(10, new BigDecimal("10"), new BigDecimal("30")));
		List<CurvePoint> listResult = curvePointRepository.findAll();

		assertFalse(listResult.isEmpty());
	}

	@Test
	@DisplayName("Delete a CurvePoint")
	void testDeleteCurvePoint() {
		CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(10, new BigDecimal("10"), new BigDecimal("30")));
		Integer id = curvePoint.getId();

		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> deleted = curvePointRepository.findById(id);

		assertFalse(deleted.isPresent());
	}
}
