package com.konex.elektrik.Service.ConnectionLog;

import com.konex.elektrik.Entity.ConnectionLog;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.ConnectionLogRepository;
import com.konex.elektrik.Specification.ConnectionLogSpecs;
import com.konex.elektrik.filter.ConnectionLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ConnectionLogServiceImpl implements ConnectionLogService {

    @Autowired
    private ConnectionLogRepository connectionLogRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public ConnectionLog addConnectionLog(ConnectionLog connectionLog, User user) {

        Date date = new Date();
        connectionLog.setDate(date);
        connectionLog.setUsers(user);
        return connectionLogRepository.save(connectionLog);
    }

    @Transactional(readOnly = true)
    public ConnectionLog getById(Long id) {

        return connectionLogRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<ConnectionLog> getAll(Sort sort) {

        return connectionLogRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public ConnectionLog findByUser(User user) {

        return connectionLogRepository.findConnectionLogByUsers(user);
    }

    @Transactional(readOnly = true)
    public List<ConnectionLog> findConnectionLogByCriteria(ConnectionLogFilter connectionLogFilter, Sort sort) {
        Specification<ConnectionLog> connectionLogSpecs = ConnectionLogSpecs.connectionLogSpecsByFilter(connectionLogFilter);
        return connectionLogRepository.findAll(connectionLogSpecs, sort);
    }

    @Transactional(readOnly = true)
    public List<ConnectionLog> getAllByJdbc() {

        String sql = "select * from connection_log cl order by cl.date desc";
        List<ConnectionLog> connectionLogList = jdbcTemplate.queryForList(sql, ConnectionLog.class);

        return connectionLogList;
    }
}
