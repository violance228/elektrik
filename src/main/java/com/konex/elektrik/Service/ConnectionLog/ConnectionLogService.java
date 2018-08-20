package com.konex.elektrik.Service.ConnectionLog;

import com.konex.elektrik.Entity.ConnectionLog;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.filter.ConnectionLogFilter;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ConnectionLogService {

    ConnectionLog addConnectionLog(ConnectionLog connectionLog, User user);
    ConnectionLog getById(Long id);
    List<ConnectionLog> getAll(Sort sort);
    ConnectionLog findByUser(User user);
    List<ConnectionLog> findConnectionLogByCriteria(ConnectionLogFilter connectionLogFilter, Sort sort);
}
