package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "transportOrders")
public class TransportOrder {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "transportOrders_id")
    private Long id;

    @Column(name = "dateOfCreation")
    private Date dateOfCreation; //дата створення

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cars")
    private Cars cars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assignments")
    private Assignment assignments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transportOrders")
    private Set<Passenger> passengers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Assignment getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignment assignment) {
        this.assignments = assignment;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportOrder that = (TransportOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateOfCreation, that.dateOfCreation) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(cars, that.cars) &&
                Objects.equals(users, that.users);
//                Objects.equals(assignments, that.assignments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateOfCreation, comment, cars, users);
    }

    @Override
    public String toString() {
        return "TransportOrder{" +
                "id=" + id +
                ", dateOfCreation=" + dateOfCreation +
                ", comment='" + comment + '\'' +
                ", cars=" + cars +
                ", users=" + users +
//                ", assignments=" + assignments +
                '}';
    }
}
