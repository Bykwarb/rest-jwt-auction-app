package com.example.demo.entity.lot;

import com.example.demo.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bid_value")
    private Double bidValue;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @OneToOne
    private User bidder;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Lot.class)
    private Lot lot;

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBidValue() {
        return bidValue;
    }

    public void setBidValue(Double bidValue) {
        this.bidValue = bidValue;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bidValue=" + bidValue +
                ", dateTime=" + dateTime +
                ", bidder=" + bidder.getId() +
                ", lot=" + lot.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Objects.equals(id, bid.id) && Objects.equals(bidValue, bid.bidValue) && Objects.equals(dateTime, bid.dateTime) && Objects.equals(bidder, bid.bidder) && Objects.equals(lot, bid.lot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bidValue, dateTime, bidder, lot);
    }
}
