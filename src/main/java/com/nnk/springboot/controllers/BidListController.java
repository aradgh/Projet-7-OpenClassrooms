package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bidList")
public class BidListController {
    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/list")
    public String home(Model model) {
        List<BidList> bidLists = bidListService.findAll();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @RequestMapping("/add")
    public String showAddBidForm(@ModelAttribute BidList bidList) {
        System.out.println("showAddBidForm : " + bidList);
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.addBidList(bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.findById(id);
        if (bidList.isEmpty()) {
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", bidList.get());
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.updateBidList(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}