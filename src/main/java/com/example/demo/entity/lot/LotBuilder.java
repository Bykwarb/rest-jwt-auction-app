package com.example.demo.entity.lot;

import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class LotBuilder{

        private Lot lot;

        public LotBuilder() {
            lot = new Lot();
        }

        public LotBuilder setName(String name){
            lot.setName(name);
            return this;
        }
        public LotBuilder setBid(List<Bid> bid){
            lot.setBid(bid);
            return this;
        }

        public LotBuilder setStartingPrice(Double startingPrice){
            lot.setStartingPrice(startingPrice);
            return this;
        }

        public LotBuilder setPurchasePrice(Double purchasePrice){
            lot.setPurchasePrice(purchasePrice);
            return this;
        }
        public LotBuilder setStartDate(LocalDateTime startDate){
            lot.setStartDate(startDate);
            return this;
        }
        public LotBuilder setEndDate(LocalDateTime endDate){
            lot.setEndDate(endDate);
            return this;
        }
        public LotBuilder setCategory(LotCategory category){
            lot.setCategory(category);
            return this;
        }
        public LotBuilder setStatus(LotStatus status){
            lot.setStatus(status);
            return this;
        }
        public LotBuilder setUser(User user){
            lot.setCreator(user);
            return this;
        }
        public Lot build(){
            return lot;
        }
    }