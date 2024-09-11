package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.*;
import java.sql.*;
import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-prod.properties")
//public class CurvePointTests {
//
//	@Autowired
//	private CurvePointRepository curvePointRepository;
//
//	@Test
//	public void curvePointTest() {
//		Timestamp asOfDate = new Timestamp(1);
//		Timestamp creationDate = new Timestamp(2);
////		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
//		CurvePoint curvePoint = new CurvePoint(10,10,asOfDate,
//				12d,14d,creationDate);
//
//		// Save
//		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertNotNull(curvePoint.getId());
//		Assert.assertTrue(curvePoint.getCurveId() == 10);
//
//		// Update
//		curvePoint.setCurveId(20);
//		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertTrue(curvePoint.getCurveId() == 20);
//
//		// Find
//		List<CurvePoint> listResult = curvePointRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = curvePoint.getId();
//		curvePointRepository.delete(curvePoint);
//		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
//		Assert.assertFalse(curvePointList.isPresent());
//	}
//
//}
