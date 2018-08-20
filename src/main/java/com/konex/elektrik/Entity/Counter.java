package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "counters")
public class Counter {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "counter_id")
    private Long id;

    @Column(name = "number")
    private String number;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "manufacturer")
    private String manufacturer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "counters")
    private Set<Indicators> indicators;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subdivisions")
    private Subdivision subdivisions;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manufacturers")
//    private Manufacturer manufacturers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Indicators> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<Indicators> indicators) {
        this.indicators = indicators;
    }

    public Subdivision getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Subdivision subdivisions) {
        this.subdivisions = subdivisions;
    }

//    public Manufacturer getManufacturers() {
//        return manufacturers;
//    }
//
//    public void setManufacturers(Manufacturer manufacturers) {
//        this.manufacturers = manufacturers;
//    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
//                ", indicators=" + indicators +
                ", subdivisions=" + subdivisions +
//                ", manufacturers=" + manufacturers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counter counter = (Counter) o;
        return Objects.equals(id, counter.id) &&
                Objects.equals(number, counter.number) &&
                Objects.equals(name, counter.name) &&
                Objects.equals(type, counter.type) &&
                Objects.equals(manufacturer, counter.manufacturer) &&
                Objects.equals(subdivisions, counter.subdivisions)
//                && Objects.equals(manufacturers, counter.manufacturers)
    ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, name, type, subdivisions, manufacturer
//                ,manufacturers
        );
    }
}