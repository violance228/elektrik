package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "status_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<Order> orders;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
//    private Set<Assignment> assignments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() { return orders; }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

//    public Set<Assignment> getAssignments() { return assignments; }
//
//    public void setAssignments(Set<Assignment> assignments) { this.assignments = assignments; }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", orders=" + orders +
//                ", assignments=" + assignments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(id, status.id) &&
                Objects.equals(name, status.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
