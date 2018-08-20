package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Cars {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "cars_id")
    private Long id;

    @Column(name = "carNumber")
    private String carNumber;

    @Column(name = "vehicleCategory")
    private String vehicleCategory;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cars")
    private Set<TransportOrder> transportOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<TransportOrder> getTransportOrders() {
        return transportOrders;
    }

    public void setTransportOrders(Set<TransportOrder> transportOrders) {
        this.transportOrders = transportOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return Objects.equals(id, cars.id) &&
                Objects.equals(carNumber, cars.carNumber) &&
                Objects.equals(vehicleCategory, cars.vehicleCategory) &&
                Objects.equals(brand, cars.brand) &&
                Objects.equals(model, cars.model);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, carNumber, vehicleCategory, brand, model);
    }

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", carNumber='" + carNumber + '\'' +
                ", vehicleCategory='" + vehicleCategory + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
