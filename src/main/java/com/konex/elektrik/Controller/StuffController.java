/*
 * Copyright (c) 2018. month -- 5. day -- 4.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Stuff.StuffService;
import com.konex.elektrik.Service.StuffComment.StuffCommentService;
import com.konex.elektrik.Service.StuffType.StuffTypeService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

//import com.konex.elektrik.Service.Amount.AmountService;

@Controller
@RequestMapping("/stuff")
public class StuffController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ButtonsService buttonsService;
//    @Autowired
//    private AmountService amountService;
    @Autowired
    private StuffService stuffService;
    @Autowired
    private StuffTypeService stuffTypeService;
    @Autowired
    private StuffCommentService stuffCommentService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Date docDate = null;

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addStuffGet(@ModelAttribute("stuff") Stuff stuff, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Предмет");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("stuffTypes", stuffTypeService.getAll(new Sort(Sort.Direction.ASC, "id")));
        log.info("stuffCreateGet");
        return "/stuff/create";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addStuffPost(@ModelAttribute("stuff") Stuff stuff, Model model,
                               @RequestParam(value = "subdivisions") Long subdivisionId,
                               @RequestParam(value = "stuffTypes") Long stuffTypeId,
                               @RequestParam("dateOfManufactu") String dateOfManufacture,
                               @RequestParam("dateOfReceiv") String dateOfReceiv,
                               HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Предмет");
        model.addAttribute("active", "active");
        StuffComment stuffComment = new StuffComment();
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        try {
            stuff.setDateOfManufacture(docDate = format.parse(dateOfManufacture));
            stuff.setDateOfReceiving(docDate = format.parse(dateOfReceiv));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String transferComment = "****Предмет: " + stuff.getNumber() + ", отримав "+
                stuff.getSubdivisions().getName() +" відділ"+ stuff.getDateOfReceiving()+"******";
        stuffComment.setComment(transferComment);
        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        StuffType stuffType = stuffTypeService.getById(stuffTypeId);
        try {
            stuffService.addStuff(stuff, stuffType, subdivision);
            log.info("stuffCreatePostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("stuffCreatePostFail");
        }
        try {
            stuffCommentService.addStuffComment(stuffComment, stuff, user);
            log.info("stuffCreateStuffCommentPostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("stuffCreateStuffCommentPostFail");
        }
        StuffType type = stuffTypeService.getById(stuffTypeId);
        type.setCount(stuffService.countAllStuffByStuffType(type));
        try {
            stuffTypeService.editStuffType(type);
            log.info("stuffEditStuffTypePostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("stuffEditStuffTypePostOkFail");
        }

        return "redirect:/stuff/create";
    }

    @RequestMapping( value = "/edit/{stuff.id}", method = RequestMethod.GET)
    public String editStuffGet(Model model,
                                @PathVariable("stuff.id") Long stuffId,
                               HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати предмет");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("stuffTypes", stuffTypeService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("stuffs", stuffService.getById(stuffId));
        log.info("stuffEditGet");
        return "/stuff/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editStuffPost(Stuff stuff, Model model,
                                @RequestParam("dateOfManufactu") String dateOfManufacture,
                                @RequestParam("dateOfReceiv") String dateOfReceiv,
                                @RequestParam("comment") String comment,
                                HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати предмет");
        model.addAttribute("active", "active");
        StuffComment stuffComment = new StuffComment();
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        try {
            stuff.setDateOfManufacture(docDate = format.parse(dateOfManufacture));
            stuff.setDateOfReceiving(docDate = format.parse(dateOfReceiv));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Stuff stuf = stuffService.getById(stuff.getId());
        if (stuff.getSubdivisions() != stuf.getSubdivisions()) {
            String transferComment = comment + " **** Предмет премістили з відділа: " +
                    stuf.getSubdivisions().getName() +
                    " в відділ " + stuff.getSubdivisions().getName() + ", Дата преміщення: " +
                    stuff.getDateOfReceiving() + ". Перемістив: " +
                    user.getSurname() +" " +user.getName() + " **** ";

            stuffComment.setComment(transferComment);
            try {
                stuffCommentService.addStuffComment(stuffComment, stuff, user);
                log.info("stuffCommentAddPostOk");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("stuffEditPostFail");
            }
        }

        return "redirect:/stuff/track/" + stuffService.editStuff(stuff).getId();
    }

    @RequestMapping( value = "/track/{stuff.id}", method = RequestMethod.GET)
    public String trackStuff(Model model, @PathVariable("stuff.id") Long stuffId, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі предмети");
        model.addAttribute("active", "active");
        model.addAttribute("stuffs", stuffService.getById(stuffId));
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());

        return "/stuff/track";
    }

    @RequestMapping( value = "/trackAllStuffBySubdivision/{subdivision.id}", method = RequestMethod.GET)
    public String trackAllStuffBySubdivision(Model model,
                                             @PathVariable("subdivision.id") Long subdivisionId,
                                             HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        model.addAttribute("h1name", "Переглянути всі предмети для відділа - "+ subdivision.getName());
        model.addAttribute("stuffBySubdv",stuffService.findAllStuffBySubdivision(subdivision));
        return "/stuff/trackAllBySubdivision";
    }

    @RequestMapping( value = "/trackAllStuff", method = RequestMethod.GET)
    public String trackAllStuff(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі предмети");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("stuffBySubdv",stuffService.getAll(new Sort(Sort.Direction.DESC, "dateOfReceiving")));
        return "/stuff/trackAll";
    }

    @RequestMapping( value = "/trackAllStuffByStuffTypes/{stuffType.id}", method = RequestMethod.GET)
    public String trackAllStuffByStuffTypes(Model model,
                                             @PathVariable("stuffType.id") Long stuffTypeId,
                                            HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        StuffType stuffType = stuffTypeService.getById(stuffTypeId);
        model.addAttribute("h1name", "Переглянути всі  - "+ stuffType.getType());
        model.addAttribute("stuffBySubdv",stuffService.findAllStuffByStuffType(stuffType));

        return "/stuff/trackAllBySubdivision";
    }
}
