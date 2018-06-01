package com.example.sylwia.myapplication;

/**
 * Created by Sylwia on 5/31/2018.
 */

public class PurchasedComp {

    private Integer id;
    private String symbol;
    private  Double purchasePrice;
    private  Integer purchaseAmount;

    public PurchasedComp() {
    }

    public PurchasedComp(String symbol, Double purchasePrice, Integer purchaseAmount) {
        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.purchaseAmount = purchaseAmount;
    }

    public PurchasedComp(Integer id, String symbol, Double purchasePrice, Integer purchaseAmount) {
        this.id = id;
        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.purchaseAmount = purchaseAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Integer purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
}
