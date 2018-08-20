/*
 * Copyright (c) 2018. month -- 5. day -- 3.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stuffs")
public class Stuff {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "stuffs_id")
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "date_of_manufacture")
    private Date dateOfManufacture;

    @Column(name = "dateOfReceiving")
    private Date dateOfReceiving;

    @Column(name = "model")
    private String model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stuffs")
    private Set<StuffComment> stuffComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdivisions")
    private Subdivision subdivisions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stuffTypes")
    private StuffType stuffTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }
//
//    public Amount getAmounts() {
//        return amounts;
//    }
//
//    public void setAmounts(Amount amounts) {
//        this.amounts = amounts;
//    }

    public Date getDateOfReceiving() {
        return dateOfReceiving;
    }

    public void setDateOfReceiving(Date dateOfReceiving) {
        this.dateOfReceiving = dateOfReceiving;
    }

    public StuffType getStuffTypes() {
        return stuffTypes;
    }

    public void setStuffTypes(StuffType stuffTypes) {
        this.stuffTypes = stuffTypes;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Subdivision getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Subdivision subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Set<StuffComment> getStuffComments() { return stuffComments; }

    public void setStuffComments(Set<StuffComment> stuffComments) { this.stuffComments = stuffComments; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stuff stuff = (Stuff) o;
        return number == stuff.number &&
                Objects.equals(id, stuff.id) &&
                Objects.equals(dateOfManufacture, stuff.dateOfManufacture) &&
                Objects.equals(dateOfReceiving, stuff.dateOfReceiving) &&
                Objects.equals(model, stuff.model) &&
                Objects.equals(subdivisions, stuff.subdivisions) &&
                Objects.equals(stuffTypes, stuff.stuffTypes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, dateOfManufacture, dateOfReceiving, model, subdivisions, stuffTypes);
    }

    @Override
    public String toString() {
        return "Stuff{" +
                "id=" + id +
                ", number=" + number +
                ", dateOfManufacture=" + dateOfManufacture +
                ", dateOfReceiving=" + dateOfReceiving +
                ", model='" + model + '\'' +
                ", subdivisions=" + subdivisions +
                ", stuffTypes=" + stuffTypes +
                '}';
    }
}
