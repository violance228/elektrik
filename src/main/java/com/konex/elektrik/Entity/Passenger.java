package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "passengers_id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cities")
    private City cities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportOrders")
    private TransportOrder transportOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public City getCities() {
        return cities;
    }

    public void setCities(City cities) {
        this.cities = cities;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public TransportOrder getTransportOrders() {
        return transportOrders;
    }

    public void setTransportOrders(TransportOrder transportOrder) {
        this.transportOrders = transportOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) &&
                Objects.equals(comment, passenger.comment) &&
                Objects.equals(cities, passenger.cities) &&
                Objects.equals(users, passenger.users) &&
                Objects.equals(transportOrders, passenger.transportOrders);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, comment, cities, users, transportOrders);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", cities=" + cities +
                ", users=" + users +
                ", transportOrders=" + transportOrders +
                '}';
    }
}
