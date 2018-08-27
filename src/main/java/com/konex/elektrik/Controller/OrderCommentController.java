package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.OrderComment;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Order.OrderService;
import com.konex.elektrik.Service.OrderComment.OrderCommentService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/orderComment")
@Controller
public class OrderCommentController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private OrderCommentService orderCommentService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ButtonsService buttonsService;

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addOrderCommentGet(Model model,
                                     Long id, HttpSession session) {
        
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Добавити коментар");
        model.addAttribute("active", "active");
        model.addAttribute("orderId", id);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        return "/orderComment/create";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addOrderCommentPost(@ModelAttribute("orderComment") OrderComment orderComment,
                                      Model model, HttpSession session,
                                      @RequestParam("orderId") Long ordersId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Добавити коментар");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order order = orderService.getById(ordersId);
        Date date = new Date();
        orderComment.setDate(date);
        model.addAttribute("userLogo", user.getName());
        System.out.println("-------------------------------" + order.getId());
        System.out.println("-------------------------------" + user.getId());
        orderCommentService.addOrderComment(orderComment, user, order);
        return "redirect:/order/track/"+order.getId();
    }

    @RequestMapping( value = "/edit", method = RequestMethod.GET)
    public String editOrderCommentGet(OrderComment orderComment, Model model,
                                      HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Редагувати коментарій");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("id", orderComment.getId());
        model.addAttribute("comment", orderComment.getComment());
        model.addAttribute("subdivision",orderComment.getOrders().getId());
        model.addAttribute("user",orderComment.getUsers().getSurname());

        return "/orderComment/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editOrderCommentPost(OrderComment orderComment, Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Редагувати коментарій");
        model.addAttribute("active", "active");
        orderCommentService.editOrderComment(orderComment);
        return "redirect:/orderComment/edit";
    }

    @RequestMapping( value = "/addCommentToOrder/{order.id}", method = RequestMethod.GET)
    public String addCommentToOrder(Model model,
                                           HttpSession session,
                                           @PathVariable("order.id") Long ordersId) {

        model.addAttribute("h1name", "Додати каментарій до замовлення");
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("active", "active");
        model.addAttribute("orderId", ordersId);
        return "/orderComment/create";
    }

    @RequestMapping( value = "/trackSingleComment", method = RequestMethod.GET)
    public String trackSingleComment(Model model, HttpSession session,
                                     @RequestParam(value = "orderId", required = false) Long ordersId) {
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Переглянути один коментарій");
        model.addAttribute("active", "active");
        Order order = orderService.getById(ordersId);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        List<OrderComment> orderComments = orderCommentService.getOrderCommentByOrder(order);
        model.addAttribute("order", orderComments);
        return "/orderComment/track";
    }
}
