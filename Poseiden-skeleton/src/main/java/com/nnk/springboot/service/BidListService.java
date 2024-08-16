package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

@Service
public class BidListService {
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public BidListDTO displayBidListById(Integer id){
        BidList bidList = bidListRepository.findByBidListId(id)
                .orElseThrow(()->new RuntimeException("this id is not corresponding to a bidList in database "));
//        return bidList;
        BidListDTO bidListDTO = new BidListDTO();
        bidListDTO.setId(bidList.getBidListId());
        bidListDTO.setType(bidList.getType());
        bidListDTO.setAccount(bidList.getAccount());
        bidListDTO.setBidQuantity(bidListDTO.getBidQuantity());
        return bidListDTO;
    }

    public List<BidListDTO> displayAllBidList(){

        return bidListRepository.findAll().stream()
                .map(bidList -> {
                    return new BidListDTO(bidList.getBidListId()
                            ,bidList.getAccount()
                            ,bidList.getType()
                            ,bidList.getBidQuantity());
                })
                .collect(toList());


//        List<BidList> bidLists = bidListRepository.findAll();
//        List<BidListDTO> bidListDTOS = new LinkedList<>();
//        for (BidList bidList:bidLists){
//            BidListDTO bidListDTO = new BidListDTO();
//            bidListDTO.setId(bidList.getBidListId());
//            bidListDTO.setAccount(bidList.getAccount());
//            bidListDTO.setType(bidList.getType());
//            bidListDTO.setBidQuantity(bidList.getBidQuantity());
//            bidListDTOS.add(bidListDTO);
//        }
//        return bidListDTOS;




// TO DO : IF IT CAN BE WRITTEN BY STREAMS
        //        return bidLists;
//      return   bidLists
//              .stream()
//              .map(bidList -> {
//                    new BidListDTO(bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
//                }
//                .collect(Collectors.toList()));




    }
    public BidList addBidList(BidListDTO bidListDTO){
//        BidList bidList = new BidList(bidListDTO.getAccount(),bidListDTO.getType(),bidListDTO.getBidQuantity());
        BidList bidList = new BidList();
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(Double.valueOf(bidListDTO.getBidQuantity()));

        return bidListRepository.save(bidList);
    }
    public void deleteBidList(int id){
        BidList bidList = bidListRepository.findByBidListId(id)
                .orElseThrow(()->new RuntimeException("can't find this id"));
        bidListRepository.delete(bidList);
    }


    public Boolean isExist(Integer id){
       return bidListRepository.existsById(id);
    }

    public BidList updateBidList(Integer id,BidListDTO bidListDTO) {
        BidList bidList = bidListRepository.findByBidListId(id)
                .orElseThrow(()->new RuntimeException("no BidList found by this id"));
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());
        return bidListRepository.save(bidList);
    }
}
