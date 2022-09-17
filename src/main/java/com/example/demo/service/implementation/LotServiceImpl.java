package com.example.demo.service.implementation;

import com.example.demo.entity.lot.Image;
import com.example.demo.entity.lot.Lot;
import com.example.demo.entity.lot.LotCategory;
import com.example.demo.entity.lot.LotStatus;
import com.example.demo.exception.LotNotFoundException;
import com.example.demo.repository.lot.LotRepository;
import com.example.demo.service.LotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public void createLot(Lot lot) {
        lotRepository.save(lot);
    }

    @Override
    public Lot getLotById(Long id) {
        Optional<Lot> optionalLot = lotRepository.findById(id);
        if (optionalLot.isPresent()){
            return lotRepository.findById(id).get();
        }else {
            throw new LotNotFoundException("Lot with id: " + id + " not found");
        }
    }

    @Override
    public void deleteLot(Lot lot) {
        lotRepository.delete(lot);
    }

    @Override
    public void deleteLotById(Long id) {
        lotRepository.deleteById(id);
    }

    @Override
    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }

    @Override
    public List<Lot> getLotsByCategory(LotCategory category) {
        return lotRepository.getByCategory(category);
    }

    @Override
    public List<Lot> getLotsByStatus(LotStatus status) {
        return lotRepository.getByStatus(status);
    }

    @Override
    public void changePurchasePriceInLotByLot(Lot lot, Double newPriceValue) {
        lot.setPurchasePrice(newPriceValue);
        lotRepository.save(lot);
    }

    @Override
    public void uploadImageInLotByLot(byte[] content, Lot lot) {
        Image image = new Image();
        image.setFileName("gvalue" +UUID.randomUUID());
        image.setLot(lot);
        image.setContent(content);
        lot.getImages().add(image);
        lotRepository.save(lot);
    }

    @Override
    public void changePurchasePriceInLotByLotId(Long lotId, Double newPriceValue) {
        Optional<Lot> optionalLot = lotRepository.findById(lotId);
        if (optionalLot.isPresent()){
            Lot lot = optionalLot.get();
            lot.setPurchasePrice(newPriceValue);
            lotRepository.save(lot);
        }else {
            throw new LotNotFoundException("Lot with id: " + lotId + " not found");
        }

    }

    @Override
    public void uploadImageInLotByLotId(byte[] content, Long lotId) {
        Optional<Lot> optionalLot = lotRepository.findById(lotId);
        if (optionalLot.isPresent()){
            Lot lot = optionalLot.get();
            Image image = new Image();
            image.setFileName(lot.getId() + "gvalue" +UUID.randomUUID());
            image.setLot(lot);
            image.setContent(content);
            lot.getImages().add(image);
            lotRepository.save(lot);
        }else {
            throw new LotNotFoundException("Lot with id: " + lotId + " not found");
        }

    }

    @Override
    public List<Lot> getLotsSortedByEndDate() {
        List<Lot> lots = lotRepository.findAll();
        lots.sort((o1, o2) -> {
            int diff = o1.getEndDate().compareTo(o2.getEndDate());
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    @Transactional
    public List<Lot> getLotsSortedByPurchasePrice() {
        List<Lot> lots = lotRepository.findAll();
        lots.sort((o1, o2) -> {
            if (o1.getPurchasePrice() > o2.getPurchasePrice()) {
                return 1;
            } else if (o1.getPurchasePrice() < o2.getPurchasePrice()) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    public List<Lot> getLotsSortedByBidValue() {
        List<Lot> lots = lotRepository.findAll();
        lots.sort((o1, o2) -> {
            if (o1.getBid().get(o1.getBid().size() - 1).getBidValue() > o2.getBid().get(o2.getBid().size() - 1).getBidValue()) {
                return 1;
            } else if (o1.getBid().get(o1.getBid().size() - 1).getBidValue() < o2.getBid().get(o2.getBid().size() - 1).getBidValue()) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    public List<Lot> sortLotListByEndDate(List<Lot> lots) {
        lots.sort((o1, o2) -> {
            int diff = o1.getEndDate().compareTo(o2.getEndDate());
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    public List<Lot> sortLotListByPurchasePrice(List<Lot> lots) {
        lots.sort((o1, o2) -> {
            if (o1.getPurchasePrice() > o2.getPurchasePrice()) {
                return 1;
            } else if (o1.getPurchasePrice() < o2.getPurchasePrice()) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    public List<Lot> sortLotListByBidValue(List<Lot> lots) {
        lots.sort((o1, o2) -> {
            if (o1.getBid().get(o1.getBid().size() - 1).getBidValue() > o2.getBid().get(o2.getBid().size() - 1).getBidValue()) {
                return 1;
            } else if (o1.getBid().get(o1.getBid().size() - 1).getBidValue() < o2.getBid().get(o2.getBid().size() - 1).getBidValue()) {
                return -1;
            } else {
                return 0;
            }
        });
        return lots;
    }

    @Override
    public List<Lot> setUpStartPage() {
        return lotRepository.setUpContent();
    }

    @Override
    public Lot getByIdAndCategory(Long id, LotCategory category) {
        return lotRepository.getByIdAndCategory(id, category);
    }
}
