package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.ConnectionLog;
import com.konex.elektrik.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConnectionLogRepository extends JpaRepository<ConnectionLog, Long>, JpaSpecificationExecutor<ConnectionLog> {

    ConnectionLog findConnectionLogByUsers(User user);
}
