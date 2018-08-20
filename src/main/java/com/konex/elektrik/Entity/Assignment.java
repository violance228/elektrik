package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "assignments_id")
    private Long id;

    @Column(name = "dateOfCreating")
    private Date dateOfCreating;

    @Column(name = "date$TimeOfDeparture")
    private Date date$TimeOfDeparture; //дата виїзду

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdivisions")
    private Subdivision subdivisions; //в яке відділення створюється відрядження

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "assignments")
    private TransportOrder transportOrder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignments")
    private Set<CitiesTravel> citiesTravels;//населені пункти які будуть проїжати

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate$TimeOfDeparture() {
        return date$TimeOfDeparture;
    }

    public void setDate$TimeOfDeparture(Date date$TimeOfDeparture) {
        this.date$TimeOfDeparture = date$TimeOfDeparture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Subdivision getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Subdivision subdivisions) {
        this.subdivisions = subdivisions;
    }

    public TransportOrder getTransportOrder() { return transportOrder; }

    public void setTransportOrder(TransportOrder transportOrder) { this.transportOrder = transportOrder; }

    public Set<CitiesTravel> getCitiesTravels() { return citiesTravels; }

    public void setCitiesTravels(Set<CitiesTravel> citiesTravels) { this.citiesTravels = citiesTravels; }

    public Date getDateOfCreating() { return dateOfCreating; }

    public void setDateOfCreating(Date dateOfCreating) { this.dateOfCreating = dateOfCreating; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateOfCreating, that.dateOfCreating) &&
                Objects.equals(date$TimeOfDeparture, that.date$TimeOfDeparture) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(users, that.users) &&
                Objects.equals(subdivisions, that.subdivisions) &&
                Objects.equals(transportOrder, that.transportOrder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateOfCreating, date$TimeOfDeparture, comment, users, subdivisions, transportOrder);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", dateOfCreating=" + dateOfCreating +
                ", date$TimeOfDeparture=" + date$TimeOfDeparture +
                ", comment='" + comment + '\'' +
                ", users=" + users +
                ", subdivisions=" + subdivisions +
                ", transportOrder=" + transportOrder +
                '}';
    }
}