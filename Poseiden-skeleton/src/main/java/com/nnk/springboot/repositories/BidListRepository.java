package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {
    Optional<BidList>findByBidListId(int id);


}
