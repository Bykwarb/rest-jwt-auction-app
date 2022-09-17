package com.example.demo.service;

import com.example.demo.entity.lot.Lot;
import com.example.demo.entity.lot.LotCategory;
import com.example.demo.entity.lot.LotStatus;

import java.util.List;

public interface LotService {
    void createLot(Lot lot);
    Lot getLotById(Long id);
    void deleteLot(Lot lot);
    void deleteLotById(Long id);
    List<Lot> getAllLots();
    List<Lot> getLotsByCategory(LotCategory category);
    List<Lot> getLotsByStatus(LotStatus status);
    void changePurchasePriceInLotByLot(Lot lot, Double newPriceValue);
    void uploadImageInLotByLot(byte[] content, Lot lot);
    void changePurchasePriceInLotByLotId(Long lotId, Double newPriceValue);
    void uploadImageInLotByLotId(byte[] content, Long lotId);
    List<Lot> getLotsSortedByEndDate();
    List<Lot> getLotsSortedByPurchasePrice();
    List<Lot> getLotsSortedByBidValue();
    List<Lot> sortLotListByEndDate(List<Lot> lots);
    List<Lot> sortLotListByPurchasePrice(List<Lot> lots);
    List<Lot> sortLotListByBidValue(List<Lot> lots);
    List<Lot> setUpStartPage();

    Lot getByIdAndCategory(Long id, LotCategory category);
}
