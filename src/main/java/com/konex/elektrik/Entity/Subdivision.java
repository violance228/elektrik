package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subdivisions")
public class Subdivision {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "subdivision_id")
    private Long id;

    @Column(name = "name")
    private Long name;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subdivisions")
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subdivisions")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subdivisions")
    private Set<Assignment> assignments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subdivisions")
    private Set<Counter> counters;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subdivisions")
    private Set<Stuff> stuffs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cities")
    private City cities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeSubdivisions")
    private TypeSubdivision typeSubdivisions;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public Set<Counter> getCounters() {
        return counters;
    }

    public void setCounters(Set<Counter> counters) {
        this.counters = counters;
    }

    public Set<Stuff> getStuffs() {
        return stuffs;
    }

    public void setStuffs(Set<Stuff> stuffs) {
        this.stuffs = stuffs;
    }

    public City getCities() { return cities; }

    public void setCities(City cities) { this.cities = cities; }

    public TypeSubdivision getTypeSubdivisions() { return typeSubdivisions; }

    public void setTypeSubdivisions(TypeSubdivision typeSubdivisions) { this.typeSubdivisions = typeSubdivisions; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subdivision that = (Subdivision) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(cities, that.cities) &&
                Objects.equals(typeSubdivisions, that.typeSubdivisions);

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, telephone, cities, typeSubdivisions);
    }

    @Override
    public String toString() {
        return "Subdivision{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cities='" + cities + '\'' +
                ", typeSubdivisions='" + typeSubdivisions + '\'' +
                '}';
    }
}