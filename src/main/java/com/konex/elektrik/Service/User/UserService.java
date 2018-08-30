package com.konex.elektrik.Service.User;

import com.konex.elektrik.Entity.Role;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.filter.UserFilter;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    User addUser(User user, Subdivision subdivisions, Long roleId);
    void delete(Long id);
    User getById(Long id);
    User findByUsername(String username);
    User editUserByAdmin(User user, Subdivision subdivisions, Role roles);
    User editUser(User user, HttpSession session);
    User findUserBySurname(String surname);
    List<User> getAll(Sort sort);
    List<User> findByCriteriaQuery(UserFilter userFilter);
    User findUserByTelephone(String telephone);
    User addToUserChatId(User user);
    User findUserByChatId(Long chatId);
    User editParsePasswords(User user);
    List<User> getAllByTelephone(String username);
    List<User> getAllByUsername(String username);
}
