package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "citiesTravels")
public class CitiesTravel {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "citiesTravels_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignments")
    private Assignment assignments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cities")
    private City cities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assignment getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignment assignments) {
        this.assignments = assignments;
    }

    public City getCities() {
        return cities;
    }

    public void setCities(City cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitiesTravel that = (CitiesTravel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(assignments, that.assignments) &&
                Objects.equals(cities, that.cities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, assignments, cities);
    }

    @Override
    public String toString() {
        return "CitiesTravel{" +
                "id=" + id +
                ", assignments=" + assignments +
                ", cities=" + cities +
                '}';
    }
}
