package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "telegramChatId")
    private Long telegramChatId;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subdivisions")
    private Subdivision subdivisions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Assignment> assignments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<OrderComment> orderComments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<ConnectionLog> connectionLogs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<StuffComment> stuffComments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Passenger> passengers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<TransportOrder> transportOrders;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
//    private Set<Amount> amounts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Subdivision getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Subdivision subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

    public Set<OrderComment> getOrderComments() {
        return orderComments;
    }

    public void setOrderComments(Set<OrderComment> orderComments) {
        this.orderComments = orderComments;
    }

    public Set<StuffComment> getStuffComments() { return stuffComments; }

    public void setStuffComments(Set<StuffComment> stuffComments) { this.stuffComments = stuffComments; }

    public Long getTelegramChatId() { return telegramChatId; }

    public void setTelegramChatId(Long telegramChatId) { this.telegramChatId = telegramChatId; }

    public Set<Passenger> getPassengers() { return passengers; }

    public void setPassengers(Set<Passenger> passengers) { this.passengers = passengers; }

    public Set<TransportOrder> getTransportOrders() { return transportOrders; }

    public void setTransportOrders(Set<TransportOrder> transportOrders) { this.transportOrders = transportOrders; }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(telegramChatId, user.telegramChatId) &&
                Objects.equals(latitude, user.latitude) &&
                Objects.equals(longitude, user.longitude) &&
                Objects.equals(subdivisions, user.subdivisions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, password, name, surname, telephone, telegramChatId, latitude, longitude, subdivisions);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", telegramChatId=" + telegramChatId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", subdivisions=" + subdivisions +
                '}';
    }

    public Set<ConnectionLog> getConnectionLogs() {
        return connectionLogs;
    }

    public void setConnectionLogs(Set<ConnectionLog> connectionLogs) {
        this.connectionLogs = connectionLogs;
    }
}