package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.stream.*;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    public Object displayAllTrade() {
        return tradeRepository.findAll()
                .stream()
                .map(
                        trade -> {
                            return new TradeDTO(trade.getTradeId(),
                                    trade.getAccount(),
                                    trade.getType(),
                                    trade.getBuyQuantity());
                        })
                .collect(Collectors.toList());
    }

    public Trade addTrade(TradeDTO tradeDTO) {
        Trade trade = new Trade();
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    public TradeDTO displayTradeById(Integer id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find a trade by this id"));
        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setTradeId(trade.getTradeId());
        tradeDTO.setType(trade.getType());
        tradeDTO.setAccount(trade.getAccount());
        tradeDTO.setBuyQuantity(trade.getBuyQuantity());
        return tradeDTO;
    }

    public Trade updateTrade(Integer id, TradeDTO tradeDTO) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find trade by given id"));
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    public void deleteTrade(Integer id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find trade by this id"));
        tradeRepository.delete(trade);
    }
}
