package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public void createCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> getCurvePointById(Integer id) {
        return curvePointRepository.findById(id);
    }

    public void updateCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}