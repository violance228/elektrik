package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Role.RoleService;
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
    private RoleService roleService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private ButtonsService buttonsService;

    @RequestMapping( value = "/login", method = RequestMethod.GET)
    public String loginGet() {

        return "/user/login";
    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping( value = "/edit", method = RequestMethod.GET)
    public String editUserGet(HttpSession session, Model model) {
//        System.out.println("====== editUserGet ======");

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("h1name", "редагувати користувача");
        model.addAttribute("active", "active");
        model.addAttribute("users", user);


        return "/user/edit";
    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editUserPost(Model model, User user, HttpSession session) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User users = userService.getById(currUserId);
        user.setSubdivisions(users.getSubdivisions());
        user.setRoles(users.getRoles());
        try {
            if (userService.findByUsername(user.getUsername()) == null && userService.findUserByTelephone(user.getTelephone()) == null) {
                log.info(userService.editUser(user, session).toString());
            } else {
                model.addAttribute("err", "Логін/телефон занятий");
            }
        }
        catch (Exception e) {
            System.err.println("Err registration");
            model.addAttribute("err", "Логін/телефон занятий");
        }


        return "redirect:/user/personalInform";
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/personalInform", method = RequestMethod.GET)
    public String trackUser(HttpSession session , Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Особистий кабінет");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("users", user);
        model.addAttribute("userLogo", user.getName());
        return "/user/personalOffice";
    }
}