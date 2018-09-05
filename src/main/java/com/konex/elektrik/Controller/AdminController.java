package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Assignment.AssignmentService;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.City.CityService;
import com.konex.elektrik.Service.ConnectionLog.ConnectionLogService;
import com.konex.elektrik.Service.Counter.CounterService;
import com.konex.elektrik.Service.Indicators.IndicatorsService;
import com.konex.elektrik.Service.Order.OrderService;
import com.konex.elektrik.Service.Role.RoleService;
import com.konex.elektrik.Service.Status.StatusService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.TypeSubdivision.TypeSubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import com.konex.elektrik.filter.ConnectionLogFilter;
import com.konex.elektrik.filter.OrderFilter;
import com.konex.elektrik.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private IndicatorsService indicatorsService;
//    @Autowired
//    private ManufacturerService manufacturerService;
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
    @Autowired
    private ConnectionLogService connectionLogService;
    @Autowired
    private TypeSubdivisionService typeSubdivisionService;
    @Autowired
    private CityService cityService;
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());

//*************************************************************************************
//*************************************USER*******************************************
//*************************************************************************************

    @RequestMapping( value = "/user/registration", method = RequestMethod.GET)
    public String registration(Model model) {

        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.DESC, "name")));
        model.addAttribute("roles", roleService.getAll());
        return "/user/registration";
    }

    @RequestMapping( value = "/user/registration", method = RequestMethod.POST)
    public String saveRegistration(@ModelAttribute("user") User user, Model model,
                                   @RequestParam(value = "subdivision") Long subdivisionId,
                                   @RequestParam(value = "role") Long roleId) {

        user.setSubdivisions(subdivisionService.getById(subdivisionId));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getById(roleId));
        user.setRoles(roles);
        try {
            if (userService.findByUsername(user.getUsername()) == null && userService.findUserByTelephone(user.getTelephone()) == null && user.getSubdivisions() != null && user.getRoles() != null) {
                Subdivision subdivisions = subdivisionService.getById(subdivisionId);
                userService.addUser(user, subdivisions, roleId);
            } else {
                model.addAttribute("err", "Логін/телефон занятий");
            }
        }
        catch (Exception e) {
            System.err.println("Err registration");
            model.addAttribute("err", "Логін/телефон занятий");
        }
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.DESC, "name")));
        model.addAttribute("roles", roleService.getAll());
        return "/user/registration";
    }

    @RequestMapping(value = "/user/trackAll", method = RequestMethod.GET)
    public String trackUserAllAdmin(Model model, UserFilter userFilter, HttpSession session) {

        Date date = new Date();
        System.err.println("------------------------------------"+date);
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Date date1 = new Date();
        System.err.println("------------------------------------"+date1);
        model.addAttribute("h1name", "Переглянути всіх користувачів");
        model.addAttribute("users", userService.getAll(new Sort(Sort.Direction.ASC, "surname")));
        Date date2 = new Date();
        System.err.println("------------------------------------"+date2);
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        Date date3 = new Date();
        System.err.println("------------------------------------"+date3);
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("users", userService.findByCriteriaQuery(userFilter));
        Date date4 = new Date();
        System.err.println("------------------------------------"+date4);

        return "/user/trackAll";
    }


    @RequestMapping(value = "/user/trackAll", method = RequestMethod.POST)
    public String findByParamUsers(Model model, UserFilter userFilter, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всіх користувачів");
        model.addAttribute("users", userService.findByCriteriaQuery(userFilter));
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("users", userService.findByCriteriaQuery(userFilter));
        return "/user/trackAll";
    }

    @RequestMapping( value = "/user/editFromAll", method = RequestMethod.GET)
    public String editUserGetFromTrackAll(Model model, HttpSession session,
                                          @RequestParam("id") Long userId) {
        System.out.println("====== editUserGetFromTrackAll ======");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати коритсувачів");
        model.addAttribute("users", userService.getById(userId));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("roles", roleService.getAll());
        Set<Role> roleUser = user.getRoles();
        model.addAttribute("roleUser", roleUser);

        return "/user/editByAdmin";
    }

    @RequestMapping( value = "/user/editFromAll", method = RequestMethod.POST)
    public String editUserPostFromTrackAll(Model model,User user, HttpSession session,
                                           @RequestParam("id") Long userId,
                                           @RequestParam("role") Long roleId,
                                           @RequestParam("subdivision") Long subdivisionId) {
        System.out.println("====== editUserPostFromTrackAll ======");
        Role role = roleService.getById(roleId);
        Subdivision subdivision = subdivisionService.getById(subdivisionId);

            userService.editUserByAdmin(user, subdivision, role);
//        } else {
//            model.addAttribute("err", "Логін/телефон занятий");
//        }

        return "redirect:/admin/user/trackAll";
    }


//*************************************************************************************
//*************************************SUBDIVISION*************************************
//*************************************************************************************

    @RequestMapping(value = "/subdivision/createSubdivision", method = RequestMethod.GET)
    public String createSubdivisionGet(Subdivision subdivision, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити підрозділ");
        model.addAttribute("typeSubdivisions", typeSubdivisionService.getAll(new Sort(Sort.Direction.ASC, "type")));
        model.addAttribute("cities", cityService.getAll(new Sort(Sort.Direction.ASC, "city")));

        return "/subdivision/create";
    }

    @RequestMapping(value = "/subdivision/createSubdivision", method = RequestMethod.POST)
    public String createSubdivisionPost(Subdivision subdivision, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);

        subdivisionService.addSubdivision(subdivision);

        return "redirect:/subdivision/create";
    }

    @RequestMapping(value = "/subdivision/edit/{subdivision.id}", method = RequestMethod.GET)
    public String editSubdivisionGet(Model model,
                                     @PathVariable("subdivision.id")Long id,
                                     HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати підрозділи");
        model.addAttribute("subdivision", subdivisionService.getById(id));
        model.addAttribute("typeSubdivisions", typeSubdivisionService.getAll(new Sort(Sort.Direction.ASC, "type")));
        model.addAttribute("cities", cityService.getAll(new Sort(Sort.Direction.ASC, "city")));

        return "/subdivision/edit";
    }

    @RequestMapping(value = "/subdivision/edit", method = RequestMethod.POST)
    public String editSubdivisionPost(Subdivision subdivision, Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати підрозділи");
        subdivision = subdivisionService.editSubdivision(subdivision);

        return "redirect:/subdivision/track/" + subdivision.getId();
    }

    @RequestMapping(value = "/subdivision/track/{subdivision.id}", method = RequestMethod.GET)
    public String trackSubdivision(Model model, HttpSession session,
                                   @PathVariable("subdivision.id") Long subdivisionId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Відділ - "+subdivisionService.getById(subdivisionId).getName());
        model.addAttribute("subdivision", subdivisionService.getById(subdivisionId));

        return "/subdivision/track";
    }

//*************************************************************************************
//*************************************ORDER*******************************************
//*************************************************************************************

    @RequestMapping(value = "/order/trackAll", method = RequestMethod.GET)
    public String trackOrderAll(Order order, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі замовлення ");
        model.addAttribute("orders", orderService.getAll(new Sort(Sort.Direction.DESC, "dateOfApplication")));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("status", statusService.getAll());

        return "/order/trackAll";
    }

    @RequestMapping( value = "/order/trackAll", method = RequestMethod.POST)
    public String findOrderParamPost(OrderFilter orderFilter, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Переглянути всі замовлення ");
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        try {
            orderFilter.setUsername(userService.findByUsername(orderFilter.getUsername()).getId().toString());
        } catch (NullPointerException e) {
            System.out.println("error");
        }
        List<Order> orders = orderService.getByCriteria(orderFilter, new Sort(Sort.Direction.DESC, "dateOfApplication"));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("orders", orders);
        model.addAttribute("status", statusService.getAll());

        return "/order/trackAll";
    }

//*************************************************************************************
//*************************************ASSIGNMENTS*******************************************
//*************************************************************************************

    @RequestMapping( value = "/assignment/trackAll", method = RequestMethod.GET)
    public String trackAssignmentAll(Assignment assignment, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі відрядження");
        model.addAttribute("assignments", assignmentService.getAll(new Sort(Sort.Direction.DESC, "id")));

        return "/assignment/trackAll";
    }

//*************************************************************************************
//*************************************ConnectionLog*******************************************
//*************************************************************************************

    @RequestMapping( value = "/connectionLog", method = RequestMethod.GET)
    public String trackConnectionLogAll(ConnectionLog connectionLog, Model model, HttpSession session) {

        Date date2 = new Date();
        System.err.println("------------------------------------222"+date2);
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Date date3 = new Date();
        System.err.println("------------------------------------333"+date3);
        model.addAttribute("h1name", "Лог підключень");
        Date date4 = new Date();
        System.err.println("------------------------------------444"+date4);
        Date date = new Date();
        System.err.println("------------------------------------55"+date);
        model.addAttribute("connectionLogs", connectionLogService.getAll(new Sort(Sort.Direction.DESC, "date")));
        Date date1 = new Date();
        System.err.println("------------------------------------66"+date1);
        return "/connectionLog";
    }

    @RequestMapping( value = "/connectionLog", method = RequestMethod.POST)
    public String trackConnectionLogAllByCriteria(ConnectionLogFilter connectionLogFilter, HttpSession session,
                                                  Model model) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Лог підключень");
        model.addAttribute("active", "active");
        model.addAttribute("connectionLogs", connectionLogService.findConnectionLogByCriteria(connectionLogFilter, new Sort(Sort.Direction.DESC, "date")));

        return "/connectionLog";

    }

//************************************************************************************************************************************************
//*************************************************PORT_LISTENER**********************************************************************************
//************************************************************************************************************************************************

    @RequestMapping( value = "/portListener", method = RequestMethod.GET)
    public void portListener() {

    }
}
