package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
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
@RequestMapping("/subdivision")
public class SubdivisionController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    SubdivisionService subdivisionService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/trackAll", method = RequestMethod.GET)
    public String trackSubdivision(Subdivision subdivision, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Всі підрозділи");
        model.addAttribute("active", "active");
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        log.info("trackAll");
        return "/subdivision/trackAll";
    }
}
