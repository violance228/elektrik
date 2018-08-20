package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "typeSubdivisions")
public class TypeSubdivision {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "typeSubdivisions_id")
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "typeSubdivisions")
    private Set<Subdivision> subdivisions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Set<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeSubdivision that = (TypeSubdivision) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "TypeSubdivision{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
