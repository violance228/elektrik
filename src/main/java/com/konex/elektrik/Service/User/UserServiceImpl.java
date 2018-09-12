package com.konex.elektrik.Service.User;

import com.konex.elektrik.Entity.Role;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.UserRepository;
import com.konex.elektrik.Service.Role.RoleService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Specification.UserSpec;
import com.konex.elektrik.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import com.konex.elektrik.Entity.User_;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User addUser(User user, Subdivision subdivisions, Long roleId) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getById(roleId));
        user.setRoles(roles);
        user.setSubdivisions(subdivisions);
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {

        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {

        return userRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public User findUserBySurname(String surname) {

        return userRepository.findBySurname(surname);
    }

    @Transactional
    public User editUserByAdmin(User user, Subdivision subdivision, Role role) {

        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.getById(user.getId()).getPassword());
        } else {
            System.err.println("---------------------------------"+user.getPassword());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        user.setSubdivisions(subdivision);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getById(role.getId()));
        user.setRoles(roles);

        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User editUser(User user, HttpSession session) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User users = userService.getById(currUserId);
        if (user.getPassword().isEmpty()) {
           user.setPassword(users.getPassword());
            System.out.println(user.getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User addToUserChatId(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAll(Sort sort) {

        return userRepository.findAllByOrderByIdAsc();
    }

    @Transactional(readOnly = true)
    public List<User> findByCriteriaQuery(UserFilter userFilter) {
        Specification<User> userSpec = UserSpec.userSpecByFilter(userFilter);
        return userRepository.findAll(userSpec);
    }

    @Transactional(readOnly = true)
    public User findUserByTelephone(String telephone) {

        return userRepository.findUserByTelephone(telephone);
    }

    @Transactional(readOnly = true)
    public User findUserByChatId(Long chatId) {

        return userRepository.findUserByTelegramChatId(chatId);
    }

    public User editParsePasswords(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllByTelephone(String telephone) {

        return userRepository.getAllByTelephone(telephone);
    }

    @Transactional(readOnly = true)
    public List<User> getAllByUsername(String username) {

        return userRepository.getAllByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> findByIdIn(List<Long> idList) {

        return userRepository.findByIdIn(idList);
    }
}
