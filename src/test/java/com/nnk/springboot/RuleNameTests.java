package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void testSaveRuleName() {
		RuleName rule = new RuleName("Rule A", "Description A", "Json A", "Template A", "SQL A", "SQL Part A");
		RuleName saved = ruleNameRepository.save(rule);

        assertEquals("Rule A", saved.getName());
	}

	@Test
	public void testUpdateRuleName() {
		RuleName rule = new RuleName("Rule B", "Description B", "Json B", "Template B", "SQL B", "SQL Part B");
		RuleName saved = ruleNameRepository.save(rule);

		saved.setName("Rule B Updated");
		RuleName updated = ruleNameRepository.save(saved);

		assertEquals("Rule B Updated", updated.getName());
	}

	@Test
	public void testFindAllRuleNames() {
		ruleNameRepository.save(new RuleName("Rule C", "Desc C", "Json C", "Template C", "SQL C", "SQL Part C"));
		ruleNameRepository.save(new RuleName("Rule D", "Desc D", "Json D", "Template D", "SQL D", "SQL Part D"));

		List<RuleName> rules = ruleNameRepository.findAll();

		assertTrue(rules.size() >= 2);
	}

	@Test
	public void testDeleteRuleName() {
		RuleName rule = new RuleName("Rule E", "Desc E", "Json E", "Template E", "SQL E", "SQL Part E");
		RuleName saved = ruleNameRepository.save(rule);

		Integer id = saved.getId();
		ruleNameRepository.delete(saved);

		Optional<RuleName> deleted = ruleNameRepository.findById(id);
		assertFalse(deleted.isPresent());
	}
}
