package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public void addBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }

    public void updateBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}