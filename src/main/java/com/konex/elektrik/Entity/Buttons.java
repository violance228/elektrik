package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="buttons")
public class Buttons {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "buttons_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "engName")
    private String engName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    @Override
    public String toString() {
        return "Buttons{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url=" + url +
                ", parentId=" + parentId +
                ", engName='" + engName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buttons buttons = (Buttons) o;
        return Objects.equals(id, buttons.id) &&
                Objects.equals(name, buttons.name) &&
                Objects.equals(url, buttons.url) &&
                Objects.equals(parentId, buttons.parentId) &&
                Objects.equals(engName, buttons.engName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, url, parentId, engName);
    }

}
