package com.example.sylwia.myapplication.MyPurchases;

import java.io.Serializable;

/**
 * Created by Sylwia on 5/31/2018.
 */

public class PurchasedCompOverview implements Serializable {

    private Integer id;
    private String symbol;
    private String companyName;
    private  Double purchasePrice;
    private  Double latestPrice;
    private Integer amount;
    private  Double difference;
    private  Double totalDifference;

    public PurchasedCompOverview() {
    }



    public PurchasedCompOverview(Integer id, String symbol, Double purchasePrice, Integer amount) {
        this.id= id;
        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.amount=amount;
        this.latestPrice=0.0;
        this.difference=0.0;
        this.totalDifference=0.0;
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

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
        totalDifference= difference*amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getTotalDifference() {
        return totalDifference;
    }

    public void setTotalDifference(Double totalDifference) {
        this.totalDifference = totalDifference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
