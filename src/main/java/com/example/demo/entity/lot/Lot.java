package com.example.demo.entity.lot;

import com.example.demo.entity.User;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Bid.class, mappedBy = "lot", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bid> bid = new ArrayList<>();

    @Column(name = "starting_price")
    private Double startingPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Image.class, mappedBy = "lot", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Image> images = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private LotCategory category;
    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = User.class)
    private User creator;

    public Lot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bid> getBid() {
        return bid;
    }

    public void setBid(List<Bid> bid) {
        this.bid = bid;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LotCategory getCategory() {
        return category;
    }

    public void setCategory(LotCategory category) {
        this.category = category;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingPrice=" + startingPrice +
                ", purchasePrice=" + purchasePrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", creator=" + creator.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return Objects.equals(id, lot.id) && Objects.equals(name, lot.name) && Objects.equals(bid, lot.bid) && Objects.equals(startingPrice, lot.startingPrice) && Objects.equals(purchasePrice, lot.purchasePrice) && Objects.equals(startDate, lot.startDate) && Objects.equals(endDate, lot.endDate) && Objects.equals(images, lot.images) && category == lot.category && status == lot.status && Objects.equals(creator, lot.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bid, startingPrice, purchasePrice, startDate, endDate, images, category, status, creator);
    }
}
