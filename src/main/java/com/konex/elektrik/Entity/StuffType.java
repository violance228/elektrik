/*
 * Copyright (c) 2018. month -- 5. day -- 4.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stuffTypes")
public class StuffType {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "stuff_types_id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "counts")
    private int count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stuffTypes")
    private Set<Stuff> stuffs;

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

    public Set<Stuff> getStuffs() {
        return stuffs;
    }

    public void setStuffs(Set<Stuff> stuffs) {
        this.stuffs = stuffs;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StuffType stuffType = (StuffType) o;
        return count == stuffType.count &&
                Objects.equals(id, stuffType.id) &&
                Objects.equals(type, stuffType.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, count);
    }

    @Override
    public String toString() {
        return "StuffType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", count=" + count +
                '}';
    }
}
