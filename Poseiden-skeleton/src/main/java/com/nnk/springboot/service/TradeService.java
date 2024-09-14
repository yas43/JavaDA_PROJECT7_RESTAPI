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

    /**
     * Find list trade and Convert tradeDto
     * @return the list of tradeDto
     */
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

    /**
     * adding new trade to database , convert tradeDTO to trade
     * @param tradeDTO given trade by user
     * @return accepted trade save in DB
     */
    public Trade addTrade(TradeDTO tradeDTO) {
        Trade trade = new Trade();
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    /**
     * find trade by via id , convert to tradeDTO
     * @param id id of trade entity
     * @return corresponding trade or issue tradeNotFoundException
     */
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

    /**
     * Updates an existing trade object with the provided new values.
     * @param id the ID of the trade object to update.
     * @param tradeDTO bid the trade object containing the new values.
     * @return RuntimeException if the provided ID does not match any object in the database
     */
    public Trade updateTrade(Integer id, TradeDTO tradeDTO) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find trade by given id"));
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        return tradeRepository.save(trade);
    }

    /**
     * Deletes a trade object by its ID.
     * @param id the ID of the trade object to delete.
     */
    public void deleteTrade(Integer id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find trade by this id"));
        tradeRepository.delete(trade);
    }
}
