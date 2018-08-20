package com.konex.elektrik.Entity;//package com.konex.elektrik.Entity;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.Objects;
//import java.util.Set;
//
//@Entity
//@Table(name = "manufacturers")
//public class Manufacturer {
//
//    @Id
//    @GeneratedValue(generator = "increment")
//    @GenericGenerator(name="increment", strategy = "increment")
//    @Column(name = "manufacturer_id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacturers")
//    private Set<Counter> counters;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Set<Counter> getCounters() {
//        return counters;
//    }
//
//    public void setCounters(Set<Counter> counters) {
//        this.counters = counters;
//    }
//
//    @Override
//    public String toString() {
//        return "Manufacturer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
////                ", counters=" + counters +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Manufacturer that = (Manufacturer) o;
//        return Objects.equals(id, that.id) &&
//                Objects.equals(name, that.name);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(id, name);
//    }
//}
