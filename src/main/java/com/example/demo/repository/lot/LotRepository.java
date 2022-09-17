package com.example.demo.repository.lot;

import com.example.demo.entity.lot.Lot;
import com.example.demo.entity.lot.LotCategory;
import com.example.demo.entity.lot.LotStatus;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends CrudRepository<Lot, Long> {

    List<Lot> getByCreator(User creator);

    List<Lot> getByCategory(LotCategory category);

    List<Lot> getByStatus(LotStatus status);

    @Query("SELECT l FROM Lot l where l.category = ?1 and l.bid <= ?2")
    List<Lot> getByCategoryAndBid(String category, Long bid);

    @Query("SELECT l from Lot  l where l.category=?1 and l.purchasePrice <= ?2")
    List<Lot> getByCategoryAndPurchasePrice(String category, Long purchasePrice);

    List<Lot> findAll();

    @Query("SELECT l " +
            "from Lot l " +
            "where l.status= com.example.demo.entity.lot.LotStatus.STATUS_ANNOUNCED " +
            "or l.status=com.example.demo.entity.lot.LotStatus.STATUS_STARTED " +
            "order by l.purchasePrice")
    List<Lot> setUpContent();

    Lot getByIdAndCategory(Long id, LotCategory category);
}
