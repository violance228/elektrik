package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Order.OrderService;
import com.konex.elektrik.Service.Role.RoleService;
import com.konex.elektrik.Service.Status.StatusService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private ButtonsService buttonsService;

    @RequestMapping( value = "/", method = RequestMethod.GET)
    public String index() {

        return "/index";
    }

    @RequestMapping( value = "/login", method = RequestMethod.GET)
    public String loginGet() {

        return "/user/login";
    }

    @RequestMapping( value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied() {

        return "/404";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.GET)
    public String editUserGet(HttpSession session, Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("h1name", "Редагувати користувача");
        model.addAttribute("users", user);

        return "/user/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editUserPost(Model model, User user, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати користувача");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User users = userService.getById(currUserId);
        user.setSubdivisions(users.getSubdivisions());
        user.setRoles(users.getRoles());
        try {
            if ((userService.getAllByUsername(user.getUsername()).size() == 1) && (userService.getAllByTelephone(user.getTelephone()).size() == 1)) {
                log.info(userService.editUser(user, session).toString());
                model.addAttribute("err", "Ви редагували свої данні");
            } else {
                model.addAttribute("err", "Логін/телефон занятий");
                System.err.println("Err registration");
            }
        } catch (Exception e) {
            System.err.println("Err registration");
            model.addAttribute("err", "Логін/телефон занятий");
        }
        model.addAttribute("users", userService.getById(user.getId()));

        return "/user/edit";
    }

    @RequestMapping(value = "/personalInform", method = RequestMethod.GET)
    public String trackUser(HttpSession session , Model modelAtt) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        modelAtt.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        modelAtt.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        modelAtt.addAttribute("users", user);
        Status status = statusService.getById(3L);

        modelAtt.addAttribute("pushStr", orderService.getExecuteBeforeDatePushNotification(user.getSubdivisions(), status));

        return "/user/personalOffice";
    }
}
