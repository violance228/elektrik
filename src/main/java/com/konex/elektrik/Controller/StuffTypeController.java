/*
 * Copyright (c) 2018. month - 5. day - 5.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.StuffType;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.StuffType.StuffTypeService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/stuffType")
@Controller
public class StuffTypeController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private StuffTypeService stuffTypeService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private UserService userService;

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addStuffTypeGet(Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        log.info("stuffTypeCreateGet");
        return "/stuffType/create";
    }

    @RequestMapping( value = "/edit/{stuffs.id}", method = RequestMethod.GET)
    public String editStuffTypeGet(Model model,
                                  @PathVariable(name = "stuffs.id") Long stuffTypeId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("stuffType", stuffTypeService.getById(stuffTypeId));
        log.info("stuffTypeCreateGet");

        return "/stuffType/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editStuffTypePost(Model model, StuffType stuffType) {

        try {
            stuffType.setCount(stuffTypeService.getById(stuffType.getId()).getCount());
            stuffType.setStuffs(stuffTypeService.getById(stuffType.getId()).getStuffs());
            stuffTypeService.editStuffType(stuffType);
            log.info("stuffTypeEditPostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("stuffTypeEditPostFail");
        }

        return "redirect:/stuffType/trackAll";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addStuffTypePost(Model model, StuffType stuffType,
                                   HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        try {
            stuffTypeService.addStuffType(stuffType);
            log.info("stuffTypeCreatePostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("stuffTypeCreatePostFail");
        }

        return "/stuffType/create";
    }

    @RequestMapping( value = "/trackAll", method = RequestMethod.GET)
    public String trackAllStuffTypeGET(Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("stuffTypes", stuffTypeService.getAll(new Sort(Sort.Direction.ASC, "id")));

        log.info("stuffTypeTrackAllGet");

        return "/stuffType/trackAll";
    }
}
