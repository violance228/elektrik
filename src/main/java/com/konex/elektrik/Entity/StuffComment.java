/*
 * Copyright (c) 2018. month - 5. day - 7.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stuff_comments")
public class StuffComment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "stuff_comment_id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stuffs")
    private Stuff stuffs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Stuff getStuffs() {
        return stuffs;
    }

    public void setStuffs(Stuff stuffs) {
        this.stuffs = stuffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StuffComment that = (StuffComment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(stuffs, that.stuffs) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, comment, stuffs, users);
    }

    @Override
    public String toString() {
        return "StuffComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", stuffs=" + stuffs +
                ", users=" + users +
                '}';
    }

}
