package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

    private final CurvePointService curvePointService;

    public CurvePointController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/list")
    public String home(Model model) {
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoints();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String showAddCurvePointForm(Model model, CurvePoint curvePoint) {
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.createCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
        if (curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get());
            return "curvePoint/update";
        } else {
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.updateCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}