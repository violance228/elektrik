package com.konex.elektrik.filter;

public class OrderFilter {

    private String userId;
    private String username;
    private String subdivisionId;
    private String statusId;
    private String dateOfApplication;
    private String dateOfApplication_day;
    private String dateOfApplication_month;
    private String dateOfApplication_year;
    private String dateOfCompletion;

    public String getStatusId() { return statusId; }

    public void setStatusId(String statusId) { this.statusId = statusId; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(String subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(String dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }
}
