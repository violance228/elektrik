package com.konex.elektrik.Repository;

import com.konex.elektrik.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
    User findBySurname(String surname);
    List<User> findAllByOrderByIdAsc();
    User findUserByTelephone(String telephone);
    User findUserByTelegramChatId(Long chatId);
}
