package com.example.demo.dto;

public class LotDto {
    private String id;
    private String name;
    private String lastBid;
    private String purchasePrice;
    private String startDate;
    private String endDate;
    private String previewImage;

    public LotDto() {
    }

    public LotDto(String id, String name, String lastBid, String purchasePrice, String startDate, String endDate, String previewImage) {
        this.id = id;
        this.name = name;
        this.lastBid = lastBid;
        this.purchasePrice = purchasePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.previewImage = previewImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastBid() {
        return lastBid;
    }

    public void setLastBid(String lastBid) {
        this.lastBid = lastBid;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }
}
