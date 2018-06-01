package com.example.sylwia.myapplication;

/**
 * Created by Sylwia on 5/31/2018.
 */

public class PurchasedCompOverview {

    private Integer id;
    private String symbol;
    private String companyName;
    private  Double purchasePrice;
    private  Double latestPrice;
    private  Double difference;

    public PurchasedCompOverview() {
    }

    public PurchasedCompOverview(String symbol, String companyName, Double purchasePrice, Double latestPrice) {
        this.symbol = symbol;
        this.companyName= companyName;
        this.purchasePrice = purchasePrice;
        this.latestPrice = latestPrice;
        this.difference=0.0;
    }

    public PurchasedCompOverview(Integer id, String symbol, Double purchasePrice) {
        this.id= id;
        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.latestPrice=0.0;
        this.difference=0.0;
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
    }
}
