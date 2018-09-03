package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "cities_id")
    private Long id;

    @Column(name = "city")
    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cities")
    private Set<Subdivision> subdivisions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cities")
    private Set<CitiesTravel> citiesTravels;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cities")
    private Set<Passenger> passengers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Set<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Set<CitiesTravel> getCitiesTravels() {
        return citiesTravels;
    }

    public void setCitiesTravels(Set<CitiesTravel> citiesTravels) {
        this.citiesTravels = citiesTravels;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return Objects.equals(id, city1.id) &&
                Objects.equals(city, city1.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, city);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
