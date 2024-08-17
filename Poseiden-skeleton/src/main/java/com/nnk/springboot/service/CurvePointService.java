package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class CurvePointService {
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public List<CurvePointDTO> displayAllCurvePoint() {

      return curvePointRepository.findAll()
                .stream()
                .map(
                        curvePoint -> {
                           return new CurvePointDTO(curvePoint.getId(),
                                    curvePoint.getTerm(),
                                    curvePoint.getValue());
                        })
               .collect(Collectors.toList());
    }

    public CurvePoint addCurvePoint(CurvePointDTO curvePointDTO) {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());
        return curvePointRepository.save(curvePoint);
    }

    public CurvePointDTO displayCurvePointById(Integer id) {
        CurvePoint  curvePoint = curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find a curve point by this id"));
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(curvePointDTO.getId());
        curvePointDTO.setTerm(curvePoint.getTerm());
        curvePointDTO.setValue(curvePoint.getValue());
        return curvePointDTO;
    }

    public CurvePoint updateCurvePoint(Integer id,CurvePointDTO curvePointDTO){
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find curve point by given id"));
        curvePoint.setCurveId(curvePointDTO.getId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());
        return curvePointRepository.save(curvePoint);
    }

    public void deleteCurvPoint(Integer id) {
        CurvePoint curvePoint =  curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find curve point by this id"));
        curvePointRepository.delete(curvePoint);
    }
}
