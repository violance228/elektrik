package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Order.OrderService;
import com.konex.elektrik.Service.OrderComment.OrderCommentService;
import com.konex.elektrik.Service.Status.StatusService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.aop.support.AopUtils;
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
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/order")
public class OrderController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private OrderCommentService orderCommentService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Date docDate = null;

    @RequestMapping(value = "/track/{orders.id}", method = RequestMethod.GET)
    public String trackOrder(Order order, Model model, HttpSession session,
                             @PathVariable("orders.id") Long orderId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути замовлення");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);

        order = orderService.getById(orderId);
        if(user.getSubdivisions().getName() == order.getSubdivisions().getName() || user.getId() == order.getUsers().getId()) {
            model.addAttribute("orders", order);
            model.addAttribute("orderComments", orderCommentService.getOrderCommentByOrder(order));
            log.info("orderTrack");
            return "/order/track";
        }
        else {
            log.info("orderTrackFail");
            return "/404";
        }
    }

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addOrderGet(Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити замовлення");
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.DESC, "name")));
        log.info("orderCreateGet");

        return "/order/create";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addOrderPost(@ModelAttribute("order") Order order, HttpSession session,
                               @RequestParam("subdivision")Long id,
                               @RequestParam("executeDate")String executeDate) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        if (executeDate != null && executeDate != "") {
            try {
                order.setExecuteBeforeDate(docDate = format.parse(executeDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Subdivision subdivision = subdivisionService.getById(id);
        try {
            orderService.addOrder(order, subdivision, user);
            log.info("orderCreatePostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("orderCreatePostFail");
        }
        return "redirect:/order/create";
    }

    @RequestMapping( value = "/edit/{userOrder.id}", method = RequestMethod.GET)
    public String editOrderGet(Model model,
                               @PathVariable("userOrder.id") Long orderId, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати замовлення");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order order = orderService.getById(orderId);

        if(AopUtils.isAopProxy(user.getId()) == AopUtils.isAopProxy(order.getUsers().getId())) {
            model.addAttribute("order", order);
            model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.DESC, "name")));
            log.info("orderEditGet");
            return "/order/edit";
        } else {
            log.info("404Edit");
            return "/404";
        }
    }


    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editOrderPost(Model model, Order order, HttpSession session,
                                @RequestParam(name = "subdivision") Long subdivisionId) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order orders = orderService.getById(order.getId());
        if (orders.getUsers().getId() == user.getId()) {
            order.setUsers(user);
            order.setStatus(statusService.getById(3L));
            order.setDateOfApplication(orders.getDateOfApplication());
            order.setOrderComments(orders.getOrderComments());
            order.setSubdivisions(subdivisionService.getById(subdivisionId));

            OrderComment orderComment = new OrderComment();
            Date date = new Date();
            try {
                order.setApplicationText(orderService.getById(order.getId()).getApplicationText() + " -- " + order.getApplicationText());
                orderService.editOrder(order);
                log.info("orderEditPostOk");
            } catch (Exception e){
                e.printStackTrace();
                log.info("orderEditPostFail");
            }
                orderComment.setDate(date);
                orderComment.setComment("замовлення було відредаговане");
            try {
                orderCommentService.addOrderComment(orderComment, user, order);
                log.info("orderAddOrderCommentPostOk");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/order/trackOrdersSubmByMe";
    }

    @RequestMapping( value = "/delete/{orders.id}", method = RequestMethod.GET)
    public String deleteOrderPost(@PathVariable(value="orders.id") Long orderId,
                                                        HttpSession session) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order order = orderService.getById(orderId);
        try {
            if (AopUtils.isAopProxy(order.getUsers().getId()) == AopUtils.isAopProxy(user.getId())) {
                List<OrderComment> commentList = orderCommentService.getOrderCommentByOrder(order);
                for (OrderComment orderComment : commentList) {
                    orderCommentService.delete(orderComment.getId());
                }
                orderService.delete(orderId);
                log.info("orderDeleteOrderPostOk");
//                return "ok";
            } else {
//                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("orderDeleteOrderPostFail");
//            return "error";
        }
        return "redirect:/order/trackOrdersSubmByMe";
    }

    @RequestMapping( value = "/status", method = RequestMethod.POST)
    public @ResponseBody String editOrderStatusPost(HttpSession session,
                               @RequestParam(value="id", required = false) Long id,
                               @RequestParam(value = "status", required = false) Long status) {

        System.out.println(id);
        System.out.println(status);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order order = orderService.getById(id);
        String surname = user.getSurname();
        Status status1 = statusService.getById(status);
        Order order1 = new Order();
        try {
            order1 = orderService.editOrderStatus(order, status1, surname);
            log.info("orderStatusOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("orderStatusFail");
        }
        System.out.println("----"+ order1.getStatus().getName());

        OrderComment orderComment = new OrderComment();
        Date date = new Date();
        orderComment.setDate(date);
        orderComment.setComment("Статус замолення було змінено на " + status1.getName());
        try {
            orderCommentService.addOrderComment(orderComment, user, order1);
            log.info("orderAddOrderCommentPostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("orderAddOrderCommentPostFail");
        }

        return order1.getStatus().getName();
    }

    @RequestMapping( value = "/trackOrdersSubmByMe", method = RequestMethod.GET)
    public String trackAllOrderSubmByMe(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Замовлення поданні мною");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Set<Order> userOrders = orderService.getAllByUser(user);
        model.addAttribute("userOrders", userOrders);
        log.info("ordertrackOrdersSubmByMe");

        return "/order/trackOrdersSubmByMe";
    }

    @RequestMapping( value = "/trackOrdersSubmForMyDivision", method = RequestMethod.GET)
    public String trackAllOrderSubmForMyDivision(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Замовлення для мого відділу");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Set<Order> subDivision = orderService.getAllBySubdivision(user.getSubdivisions());
        model.addAttribute("subDivision", subDivision);

        log.info("ordertrackOrdersSubmForMyDivision");

        return "/order/trackOrdersSubmForMyDivision";
    }

    @RequestMapping( value = "/redirectOrderToSubdivision/{orders.id}", method = RequestMethod.GET)
    public String redirectOrderGet(Model model, HttpSession session,
                                   @PathVariable("orders.id") Long orderId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Перенаправити замовлення");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Order order = orderService.getById(orderId);

        if(user.getSubdivisions().getName() == order.getSubdivisions().getName() || user.getId() == order.getUsers().getId()) {
            model.addAttribute("order", order);
            model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
            log.info("redirectOrderToSubdivisionOk");
            return "/order/redirect";
        }
        else {
            log.info("redirectOrderToSubdivision404");
            return "/404";
        }
    }

    @RequestMapping( value = "/redirectOrderToSubdivision", method = RequestMethod.POST)
    public String redirectOrderPost(OrderComment orderComment, HttpSession session,
                                    @RequestParam("id") Long id,
                                    @RequestParam("subdivision") Long subdivisionId) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        Order order = orderService.getById(id);
        order.setSubdivisions(subdivision);
        try {
            orderService.editOrder(order);
            log.info("redirectOrderToSubdivisionPostOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("redirectOrderToSubdivisionPostFail");
        }
        Date date = new Date();
        orderComment.setDate(date);
        orderComment.setComment("Замовлення було перенаправлено з підрозділу " + user.getSubdivisions().getTypeSubdivisions().getType() + " - "
                + user.getSubdivisions().getName() + " - " + user.getSubdivisions().getCities().getCity());
        orderCommentService.addOrderComment(orderComment, user, order);

        return "redirect:/order/trackOrdersSubmForMyDivision";
    }

    @RequestMapping( value = "/trackOrdersArchiveSubmForMyDivision", method = RequestMethod.GET)
    public String trackAllOrderArchiveSubmForMyDivision(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Замовлення для мого відділу");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Set<Order> subDivision = orderService.getAllBySubdivision(user.getSubdivisions());
        model.addAttribute("subDivision", subDivision);

        log.info("trackOrdersArchiveSubmForMyDivision");

        return "/order/archive";
    }
}
