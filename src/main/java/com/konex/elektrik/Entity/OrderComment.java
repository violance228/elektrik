package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "order_comments")
public class OrderComment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "order_comment_id")
    private Long id;

    @Column(name = "comment")
    private String comment;
    @Column(name = "commentTime")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders")
    private Order orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

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

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderComment that = (OrderComment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(date, that.date) &&
                Objects.equals(orders, that.orders) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, comment, date, orders, users);
    }

    @Override
    public String toString() {
        return "OrderComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", orders=" + orders +
                ", users=" + users +
                '}';
    }

}
