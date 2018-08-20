package com.konex.elektrik.filter;

public class UserFilter {

    private String userSurname;
    private String rolesId;
    private String subdivisionId;
    private String userName;

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getRolesId() {
        return rolesId;
    }

    public void setRolesId(String rolesId) {
        this.rolesId = rolesId;
    }

    public String getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(String subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
