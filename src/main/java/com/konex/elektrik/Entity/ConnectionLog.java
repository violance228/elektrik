package com.konex.elektrik.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "connection_log")
public class ConnectionLog {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "connection_log_id")
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionLog that = (ConnectionLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(date, that.date) &&
                Objects.equals(users, that.users);
    }

    @Override
    public String toString() {
        return "ConnectionLog{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                ", users=" + users +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ip, date, users);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
