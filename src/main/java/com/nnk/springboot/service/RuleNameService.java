package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public void delete(Integer id) {
        RuleName ruleName = findById(id);
        ruleNameRepository.delete(ruleName);
    }
}