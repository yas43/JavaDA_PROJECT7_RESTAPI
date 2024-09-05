package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.time.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-prod.properties")
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {

		Timestamp bidListDate = new Timestamp(1);
		Timestamp creation = new Timestamp(2);
		Timestamp revisionDate = new Timestamp(3);
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
		BidList bidList = new BidList(1,"testAccount","testType",10d,2d,4d,8d,"testBenchMark"
		,bidListDate,"commentaryTest","securityTest","statusTest","traderTest","bookTest","creationNameTest"
		,creation,"revisionTest",revisionDate,"dealNameTest","dealTypeTest","sourceListIdTest","sideTest");

		// Save
	 bidList = bidListRepository.save(bidList);
		Assert.assertNotNull(bidList.getBidListId());
		Assert.assertEquals(bidList.getBidQuantity(), 10d, 1d);

		// Update
		bidList.setBidQuantity(20d);
		BidList bidList2 = bidListRepository.save(bidList);
		Assert.assertEquals(bidList2.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bidList.getBidListId();
		bidListRepository.delete(bidList);
		Optional<BidList> bidList3 = bidListRepository.findById(id);
		Assert.assertEquals(Optional.empty(),bidList3);
	}
}
