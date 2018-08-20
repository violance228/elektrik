package com.konex.elektrik.filter;

public class CounterFilter {

    private String counterType;
    private String manufacturerId;
    private String subdivisionId;
    private String dateOfWithdrawalOfIndicators;

    public String getCounterType() {
        return counterType;
    }

    public void setCounterType(String counterType) {
        this.counterType = counterType;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(String subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public String getDateOfWithdrawalOfIndicators() {
        return dateOfWithdrawalOfIndicators;
    }

    public void setDateOfWithdrawalOfIndicators(String dateOfWithdrawalOfIndicators) {
        this.dateOfWithdrawalOfIndicators = dateOfWithdrawalOfIndicators;
    }
}
