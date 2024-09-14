package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

@Service
public class CurvePointService {
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Find list CurvePoint and Convert curvePointDto
     * @return the list of curvePoint
     */
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

    /**
     * adding new CurvePoint to database , convert curvePointDto to CurvePoint
     * @param curvePointDTO given bidList by user
     * @return accepted CurvePoint save in DB
     */
    public CurvePoint addCurvePoint(CurvePointDTO curvePointDTO) {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return curvePointRepository.save(curvePoint);
    }

    /**
     * find bidList by via id , convert to curvePointDto
     * @param id id
     * @return the  corresponding CurvePoint  or issue CurvePointNotFoundException
     */
    public CurvePointDTO displayCurvePointById(Integer id) {
        CurvePoint  curvePoint = curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find a curve point by this id"));
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(curvePoint.getId());
        curvePointDTO.setTerm(curvePoint.getTerm());
        curvePointDTO.setValue(curvePoint.getValue());
        return curvePointDTO;
    }

    /**
     * Updates an existing CurvePoint object with the provided new values.
     * @param id the ID of the CurvePoint object to update.
     * @param curvePointDTO bid the CurvePoint object containing the new values.
     * @return RuntimeException if the provided ID does not match any object in the database
     */
    public CurvePoint updateCurvePoint(Integer id,CurvePointDTO curvePointDTO){
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find curve point by given id"));
        curvePoint.setCurveId(curvePointDTO.getId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Deletes a CurvePoint object by its ID
     * @param id the ID of the CurvePoint object to delete.
     */
    public void deleteCurvPoint(Integer id) {
        CurvePoint curvePoint =  curvePointRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find curve point by this id"));
        curvePointRepository.delete(curvePoint);
    }
}
