package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "orders_id")
    private Long id;

    @Column(name = "date_of_application")
    private Date dateOfApplication;
    @Column(name = "data_of_completion")
    private Date dateOfCompletion;
    @Column(name = "application_text")
    private String applicationText;
    @Column(name = "surname")
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdivisions")
    private Subdivision subdivisions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users")
    private User users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    private Set<OrderComment> orderComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(Date dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public Date getDateOfCompletion() { return dateOfCompletion; }

    public void setDateOfCompletion(Date dateOfCompletion) { this.dateOfCompletion = dateOfCompletion; }

    public String getApplicationText() {
        return applicationText;
    }

    public void setApplicationText(String applicationText) {
        this.applicationText = applicationText;
    }

    public Subdivision getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Subdivision subdivisions) {
        this.subdivisions = subdivisions;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<OrderComment> getOrderComments() { return orderComments; }

    public void setOrderComments(Set<OrderComment> orderComments) { this.orderComments = orderComments; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateOfApplication=" + dateOfApplication +
                ", dateOfCompletion=" + dateOfCompletion +
                ", applicationText='" + applicationText + '\'' +
                ", surname='" + surname + '\'' +
                ", subdivisions=" + subdivisions +
                ", users=" + users +
                ", orderComments=" + orderComments +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(dateOfApplication, order.dateOfApplication) &&
                Objects.equals(dateOfCompletion, order.dateOfCompletion) &&
                Objects.equals(applicationText, order.applicationText) &&
                Objects.equals(subdivisions, order.subdivisions) &&
                Objects.equals(users, order.users) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateOfApplication, dateOfCompletion, applicationText, subdivisions, users, status);
    }
}