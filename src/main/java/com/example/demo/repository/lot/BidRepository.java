package com.example.demo.repository.lot;

import com.example.demo.entity.lot.Bid;
import com.example.demo.entity.lot.Lot;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> getByLot(Lot lot);
    Bid getBidByLotAndBidder(Lot lot, User bidder);
}
