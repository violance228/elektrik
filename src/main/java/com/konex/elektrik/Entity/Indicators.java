package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "indicators")
public class Indicators {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "indicators_id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "indicator")
    private int indicator;

    @Column(name = "consumption")
    private int consumption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "counters")
    private Counter counters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public Counter getCounters() {
        return counters;
    }

    public void setCounters(Counter counters) {
        this.counters = counters;
    }

    @Override
    public String toString() {
        return "Indicators{" +
                "id=" + id +
                ", date=" + date +
                ", indicator=" + indicator +
                ", consumption=" + consumption +
                ", counters=" + counters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indicators that = (Indicators) o;
        return indicator == that.indicator &&
                consumption == that.consumption &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(counters, that.counters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, indicator, consumption, counters);
    }
}