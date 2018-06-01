package com.example.sylwia.myapplication;

/**
 * Created by Sylwia on 5/31/2018.
 */

public class CompOverview {

    private String symbol;
    private String companyName;
    private  Double latestPrice;
    private Double change;

    public CompOverview() {
    }

    public CompOverview(String symbol, String companyName, Double latestPrice, Double change) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.latestPrice = latestPrice;
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}
