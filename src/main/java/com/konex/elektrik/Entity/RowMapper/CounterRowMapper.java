package com.konex.elektrik.Entity.RowMapper;

import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CounterRowMapper implements RowMapper<Counter> {
    @Autowired
    SubdivisionService subdivisionService;

    public Counter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Counter counter = new Counter();
        counter.setId(rs.getLong("id"));
        counter.setName(rs.getString("name"));
        counter.setNumber(rs.getString("number"));
        counter.setType(rs.getString("type"));
        counter.setManufacturer(rs.getString("manufacturer"));

        counter.setSubdivisions(subdivisionService.getById((Long)rs.getObject("subdivisions")));
//        counter.setIndicators((Set<Indicators>) rs.getObject("indicators"));
        return counter;
    }
}
